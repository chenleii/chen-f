package com.chen.f.common.distributedlock;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * 分布式锁注解
 *
 * @author chen
 * @since 2020/2/26 10:53.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DistributedLock {

    /**
     * 锁关联的标识
     * 支持spel，可选变量：方法参数（参数名），当前对象（this）。
     * <p>
     * 默认使用方法签名
     */
    String lockKey() default "";

    /**
     * 最大等待时间
     */
    long maximumWaitTime() default 6000L;

    /**
     * 最大等待时间的时间单位
     */
    TimeUnit timeUnit() default TimeUnit.MILLISECONDS;
}
