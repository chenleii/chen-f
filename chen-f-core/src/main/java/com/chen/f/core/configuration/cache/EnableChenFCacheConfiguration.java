package com.chen.f.core.configuration.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.SearchStrategy;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * chen-f缓存配置类
 *
 * @author chen
 * @since 2019/11/9 17:45.
 */
@Configuration
@AutoConfigureAfter({CacheAutoConfiguration.class})
@ConditionalOnClass({CacheManager.class, CacheHelper.class})
public class EnableChenFCacheConfiguration {
    protected static final Logger logger = LoggerFactory.getLogger(EnableChenFCacheConfiguration.class);

    @Bean
    @ConditionalOnMissingBean(value = CacheHelper.class, search = SearchStrategy.CURRENT)
    public CacheHelper cacheHelper(CacheManager cacheManager) {
        return new CacheHelper(cacheManager);
    }

}
