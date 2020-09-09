package com.chen.f.core.configuration.redis;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;

import java.lang.annotation.*;

/**
 *
 * @author chen
 * @since 2020/9/9 17:36
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@EnableChenFEmbeddedRedis
public @interface AutoConfigureTestRedis {
}
