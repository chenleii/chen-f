package com.chen.f.spring.boot.configuration.distributedlock;

import com.chen.f.common.distributedlock.DistributedLock;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.annotation.Order;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.integration.support.locks.LockRegistry;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

/**
 * 分布式锁aop
 * <p>
 * 在{@link org.springframework.aop.interceptor.ExposeInvocationInterceptor}之后
 *
 * @author chen
 * @since 2020/2/26 11:13.
 */
@Aspect
@Order(HIGHEST_PRECEDENCE + 1)
public class DistributedLockAspect {

    private DefaultParameterNameDiscoverer defaultParameterNameDiscoverer = new DefaultParameterNameDiscoverer();

    private LockRegistry lockRegistry;

    public DistributedLockAspect(LockRegistry lockRegistry) {
        this.lockRegistry = lockRegistry;
    }

    @Around("@annotation(distributedLock)")
    public Object around(ProceedingJoinPoint proceedingJoinPoint, DistributedLock distributedLock) throws Throwable {
        String lockKey = distributedLock.lockKey();
        long maximumWaitTime = distributedLock.maximumWaitTime();
        TimeUnit timeUnit = distributedLock.timeUnit();

        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        if (StringUtils.isNotBlank(lockKey)) {
            ExpressionParser parser = new SpelExpressionParser();

            StandardEvaluationContext context = new StandardEvaluationContext();

            context.setVariable("proceedingJoinPoint", proceedingJoinPoint);
            context.setVariable("this", proceedingJoinPoint.getThis());
            context.setVariable("target", proceedingJoinPoint.getTarget());

            Method method = methodSignature.getMethod();
            context.setVariable("method", method);

            String[] parameterNames = defaultParameterNameDiscoverer.getParameterNames(method);
            Object[] args = proceedingJoinPoint.getArgs();
            if (ArrayUtils.isNotEmpty(parameterNames) && ArrayUtils.isNotEmpty(args)) {
                for (int i = 0; i < parameterNames.length; i++) {
                    context.setVariable(parameterNames[i], args[i]);
                }
            }
            lockKey = parser.parseExpression(lockKey).getValue(context, String.class);
        } else {
            lockKey = methodSignature.toLongString();
        }

        Lock lock = lockRegistry.obtain(lockKey);
        boolean b = false;
        try {
            b = lock.tryLock(maximumWaitTime, timeUnit);
            if (b) {
                return proceedingJoinPoint.proceed();
            } else {
                throw new IllegalThreadStateException("未获取到分布式锁");
            }
        } finally {
            if (b) {
                lock.unlock();
            }
        }
    }
}
