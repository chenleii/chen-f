package com.chen.f.core.configuration.session;

import org.springframework.context.annotation.Import;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 开启chen-f会话配置
 *
 * @author chen
 * @since 2019/1/15 0:13.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EnableRedisHttpSession
@Import({EnableChenFSessionConfiguration.class})
public @interface EnableChenFSession {

    /**
     * 会话ID解析类型
     */
    SessionIdResolutionTypeEnum sessionIdResolutionType() default SessionIdResolutionTypeEnum.COOKIE;
    
    /**
     * 名称
     * <p>
     * COOKIE名称
     * <p>
     * 或
     * <p>
     * HEADER名称
     */
    String name() default "SESSION";

    
}
