package com.popjub.reservationservice.global.annotation;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Aspect
@RequiredArgsConstructor
public class DistributedLockAspect {

	private final RedissonClient redissonClient;
	private final ExpressionParser parser = new SpelExpressionParser();

	@Around("@annotation(com.popjub.reservationservice.global.annotation.DistributedLock)")
	public Object lock(ProceedingJoinPoint joinPoint) throws Throwable {
		MethodSignature signature = (MethodSignature)joinPoint.getSignature();
		Method method = signature.getMethod();
		DistributedLock distributedLock = method.getAnnotation(DistributedLock.class);

		String key = parseKey(distributedLock.key(), signature, joinPoint.getArgs());
		RLock lock = redissonClient.getLock(key);

		try {
			boolean available = lock.tryLock(
				distributedLock.waitTime(),
				distributedLock.leaseTime(),
				distributedLock.timeUnit()
			);
			if (!available) {
				log.warn("락을 습득하는데 실패했습니다. lock: {}", key);
			}
			log.info("락 습득 lock: {}", key);
			return joinPoint.proceed();
		} finally {
			if (lock.isHeldByCurrentThread()) {
				lock.unlock();
				log.info("락 해제 lock: {}", key);
			}
		}
	}

	private String parseKey(String key, MethodSignature signature, Object[] args) {
		if (!key.contains("#")) {
			return key;
		}

		StandardEvaluationContext context = new StandardEvaluationContext();
		String[] parameterNames = signature.getParameterNames();
		for (int i = 0; i < parameterNames.length; i++) {
			context.setVariable(parameterNames[i], args[i]);
		}
		return parser.parseExpression(key).getValue(context, String.class);
	}
}
