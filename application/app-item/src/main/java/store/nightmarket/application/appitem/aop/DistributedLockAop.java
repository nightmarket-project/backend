package store.nightmarket.application.appitem.aop;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class DistributedLockAop {

	private static final String REDISSON_LOCK_PREFIX = "LOCK:";

	private final RedissonClient redissonClient;
	private final AopForTransaction aopForTransaction;

	@Around("@annotation(store.nightmarket.application.appitem.aop.DistributedLock)")
	public Object lock(final ProceedingJoinPoint joinPoint) throws Throwable {

		MethodSignature signature = (MethodSignature)joinPoint.getSignature();
		Method method = signature.getMethod();
		DistributedLock distributedLock = method.getAnnotation(DistributedLock.class);

		Object dynamicValue = CustomSpringElpParser.getDynamicValue(
			signature.getParameterNames(),
			joinPoint.getArgs(),
			distributedLock.key()
		);

		String key = REDISSON_LOCK_PREFIX + dynamicValue.toString();

		String threadName = Thread.currentThread().getName();
		log.info("[{}] 🚦 락 획득 시도: Key={}", threadName, key);

		RLock rLock = redissonClient.getLock(key);

		try {
			boolean available = rLock.tryLock(
				distributedLock.waitTime(),
				distributedLock.leaseTime(),
				distributedLock.timeUnit()
			);
			if (!available) {
				log.warn("[{}] ❌ 락 획득 실패 (타임아웃): Key={}", threadName, key);
				return false;
			}

			log.info("[{}] ✅ 락 획득 성공! 비즈니스 로직 시작: Key={}", threadName, key);
			return aopForTransaction.proceed(joinPoint);
		} catch (InterruptedException e) {
			throw new InterruptedException();
		} finally {
			try {
				log.info("[{}] 🔓 락 해제 완료: Key={}", threadName, key);
				rLock.unlock();
			} catch (IllegalMonitorStateException e) {
				log.info("Redisson Lock Already UnLock {} {}",
					method.getName(),
					key
				);
			}
		}
	}

}
