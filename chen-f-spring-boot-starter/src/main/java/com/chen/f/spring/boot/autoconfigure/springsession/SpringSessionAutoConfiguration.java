package com.chen.f.spring.boot.autoconfigure.springsession;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.session.SessionAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.CookieHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;

/**
 * @author chen
 * @since 2017/12/20 8:41.
 */
@Configuration
@ConditionalOnClass(RedisOperations.class)
@AutoConfigureBefore({SessionAutoConfiguration.class})
@EnableRedisHttpSession
public class SpringSessionAutoConfiguration {

    @Bean
    public HttpSessionIdResolver httpSessionIdResolver() {
        //return HeaderHttpSessionIdResolver.xAuthToken();
        return new CookieHttpSessionIdResolver();
    }

}
