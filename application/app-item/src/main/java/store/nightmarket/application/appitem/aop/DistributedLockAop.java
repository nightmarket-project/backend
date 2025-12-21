package store.nightmarket.application.appitem.aop;

import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.RedissonMultiLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import store.nightmarket.application.appitem.exception.DistributedLockException;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
@Order(1)
public class DistributedLockAop {

	private final RedissonClient redissonClient;

	@Around("@annotation(distributedLock)")
	public Object lock(
		ProceedingJoinPoint joinPoint,
		DistributedLock distributedLock
	) throws Throwable {
		MethodSignature signature = (MethodSignature)joinPoint.getSignature();

		List<String> lockKeys = generateLockKeys(
			signature,
			joinPoint,
			distributedLock
		);

		List<RLock> locks = lockKeys.stream()
			.distinct()
			.sorted()
			.map(redissonClient::getLock)
			.toList();

		RLock multiLock = new RedissonMultiLock(
			locks.toArray(new RLock[0])
		);

		boolean locked = false;

		try {
			locked = multiLock.tryLock(
				distributedLock.waitTime(),
				distributedLock.leaseTime(),
				distributedLock.timeUnit()
			);

			if (!locked) {
				throw new DistributedLockException("Failed to acquire distributed lock");
			}

			return joinPoint.proceed();
		} catch (Exception e) {
			throw new DistributedLockException(e.getMessage());
		} finally {
			if (locked && multiLock.isHeldByCurrentThread()) {
				try {
					multiLock.unlock();
				} catch (IllegalMonitorStateException e) {
					log.warn("MultiLock already unlocked");
				}
			}
		}
	}

	private List<String> generateLockKeys(
		MethodSignature signature,
		ProceedingJoinPoint joinPoint,
		DistributedLock distributedLock
	) {
		if (distributedLock.keys().length > 0) {
			return CustomSpringElpParser.getDynamicValue(
				signature.getParameterNames(),
				joinPoint.getArgs(),
				distributedLock.keys()
			);
		}

		throw new DistributedLockException("DistributedLock requires 'keys'");
	}

}
