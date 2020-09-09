package com.chen.f.core.configuration.redis;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 开启chen-f 嵌入式 redis配置
 *
 * @author chen
 * @since 2020/9/9 18:13.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({EnableChenFEmbeddedRedisConfiguration.class})
public @interface EnableChenFEmbeddedRedis {
    
}
