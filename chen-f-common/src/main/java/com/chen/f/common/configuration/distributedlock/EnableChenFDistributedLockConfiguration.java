package com.chen.f.common.configuration.distributedlock;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.integration.support.locks.LockRegistry;

/**
 * 分布式锁配置类
 *
 * @author chen
 * @since 2020/2/26 9:27.
 */
@Configuration
@EnableAspectJAutoProxy
@AutoConfigureAfter({RedisAutoConfiguration.class,})
public class EnableChenFDistributedLockConfiguration {

    @Bean
    public LockRegistry redisLockRegistry(RedisConnectionFactory redisConnectionFactory) {
        return new RedisLockRegistry(redisConnectionFactory, "distributed-lock");
    }
    
    @Bean
    public DistributedLockAspect distributedLockAspect(LockRegistry lockRegistry) {
        return new DistributedLockAspect(lockRegistry);
    }
}
