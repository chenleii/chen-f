package com.chen.f.common;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.util.SocketUtils;
import redis.embedded.RedisServer;

import java.util.Objects;

/**
 * @author chen
 * @since 2020/9/3 0:37.
 */
@TestConfiguration
public class TestRedisConfiguration {

    @Bean
    public BeanPostProcessor redisPropertiesBeanPostProcessor() {
        return new BeanPostProcessor() {
            @Override
            public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
                if (bean instanceof RedisProperties) {
                    RedisProperties redisProperties = (RedisProperties) bean;
                    redisProperties.setHost("localhost");
                    redisProperties.setPort(SocketUtils.findAvailableTcpPort());
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
                    .setting("maxheap 51200000")
                    .build();
        }

        public void afterPropertiesSet() throws Exception {
            redisServer.start();
        }

        public void destroy() throws Exception {
            if (Objects.nonNull(redisServer)) {
                redisServer.stop();
            }
        }
    }
}
