package com.chen.f.core.configuration.applicationcontext;

import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 开启chen-f应用上下文配置
 *
 * @author chen
 * @since 2019/1/15 0:13.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({EnableChenFApplicationContextConfiguration.class})
public @interface EnableChenFApplicationContext {
    
}
