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
					releaseAllLocks(lockList);
					return false;
				}

				lockList.add(rLock);
			}

			return joinPoint.proceed();
		} catch (InterruptedException e) {
			throw new InterruptedException(); // TODO : custom exception 생성하면 좋음
		} finally {
			try {
				releaseAllLocks(lockList);
			} catch (IllegalMonitorStateException e) {
				log.debug("Redisson Lock Already UnLock {} {}",
					method.getName(),
					keys
				);
			}
		}
	}

	private void releaseAllLocks(List<RLock> locks) {
		for (RLock lock : locks) {
			try {
				if (lock.isHeldByCurrentThread()) {
					lock.unlock();
				}
			} catch (IllegalMonitorStateException e) {
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
