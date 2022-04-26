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

import java.io.IOException;
import java.net.ServerSocket;
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
                    redisProperties.setPort(findAvailableTcpPort());
                    redisProperties.setPassword(null);
                }
                return bean;
            }
        };
    }

    /**
     * 获取一个可用的tcp端口
     *
     * @return tcp端口
     */
    public int findAvailableTcpPort() {
        try (
                ServerSocket serverSocket = new ServerSocket(0);
        ) {
            return serverSocket.getLocalPort();
        } catch (IOException e) {
            throw new RuntimeException("findAvailableTcpPort exception.", e);
        }
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
