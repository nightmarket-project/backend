package store.nightmarket.application.appitem.aop;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
@Order(1)
public class DistributedLockAop {

	private final RedissonClient redissonClient;

	@Around("@annotation(store.nightmarket.application.appitem.aop.DistributedLock)")
	public Object lock(final ProceedingJoinPoint joinPoint) throws Throwable {

		MethodSignature signature = (MethodSignature)joinPoint.getSignature();
		Method method = signature.getMethod();
		DistributedLock distributedLock = method.getAnnotation(DistributedLock.class);

		List<String> keys = generateLockKey(
			signature,
			joinPoint,
			distributedLock
		);
		String threadName = Thread.currentThread().getName();
		log.debug("[{}] 락 획득 시도: Key={}", threadName, keys);

		List<RLock> lockList = new ArrayList<>();
		try {
			for (String key : keys) {
				RLock rLock = redissonClient.getLock(key);

				boolean available = rLock.tryLock(
					distributedLock.waitTime(),
					distributedLock.leaseTime(),
					distributedLock.timeUnit()
				);

				if (!available) {
					releaseAllLocks(lockList, threadName);
					return false;
				}

				lockList.add(rLock);
				log.debug("[{}] 락 획득 성공: Key={}", threadName, key);
			}

			log.debug("[{}] 락 획득 성공! 비즈니스 로직 시작: Key={}", threadName, keys);

			return joinPoint.proceed();
		} catch (InterruptedException e) {
			throw new InterruptedException();
		} finally {
			try {
				log.debug("[{}] 락 해제 완료: Key={}", threadName, keys);
				releaseAllLocks(lockList, threadName);
			} catch (IllegalMonitorStateException e) {
				log.debug("Redisson Lock Already UnLock {} {}",
					method.getName(),
					keys
				);
			}
		}
	}

	private void releaseAllLocks(List<RLock> locks, String threadName) {
		for (RLock lock : locks) {
			try {
				if (lock.isHeldByCurrentThread()) {
					lock.unlock();
					log.debug("[{}] 🔓 락 해제: {}", threadName, lock.getName());
				}
			} catch (IllegalMonitorStateException e) {
				log.warn("[{}] ⚠️ 락이 이미 해제됨: {}", threadName, lock.getName());
			}
		}
	}

	private List<String> generateLockKey(
		MethodSignature signature,
		ProceedingJoinPoint joinPoint,
		DistributedLock distributedLock
	) {
		if (distributedLock.keys().length > 0) {
			List<Object> dynamicValue = CustomSpringElpParser.getDynamicValue(
				signature.getParameterNames(),
				joinPoint.getArgs(),
				distributedLock.keys()
			);

			return dynamicValue.stream()
				.map(Object::toString)
				.toList();
		}

		throw new IllegalArgumentException("DistributedLock requires 'keys'");
	}

}
