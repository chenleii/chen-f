package com.chen.f.core.configuration.redis;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.SocketUtils;
import redis.embedded.RedisServer;

import java.util.Objects;

/**
 * Embedded Redis Configuration.
 *
 * @author chene
 * @since 2020/9/3 0:37.
 */
@Configuration
public class EnableChenFEmbeddedRedisConfiguration {

    @Bean
    public BeanPostProcessor embeddedRedisPropertiesBeanPostProcessor() {
        return new BeanPostProcessor() {
            @Override
            public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
                if (bean instanceof RedisProperties) {
                    RedisProperties redisProperties = (RedisProperties) bean;
                    redisProperties.setHost("localhost");
                    redisProperties.setPort(SocketUtils.findAvailableTcpPort());
                    redisProperties.setPassword(null);
                }
                return bean;
            }
        };
    }

    @Bean
    public RedisServerBean redisServerBean(RedisProperties redisProperties) {
        return new RedisServerBean(redisProperties);
    }

    static class RedisServerBean implements InitializingBean, DisposableBean {
        private RedisServer redisServer;

        public RedisServerBean(RedisProperties redisProperties) {
            this.redisServer = RedisServer.builder()
                    .bind(redisProperties.getHost())
                    .port(redisProperties.getPort())
                    // .setting("maxheap 51200000")
                    .build();
        }

        @Override
        public void afterPropertiesSet() throws Exception {
            redisServer.start();
        }

        @Override
        public void destroy() throws Exception {
            if (Objects.nonNull(redisServer)) {
                redisServer.stop();
            }
        }
    }
}
