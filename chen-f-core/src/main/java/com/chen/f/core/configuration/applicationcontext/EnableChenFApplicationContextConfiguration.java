package com.chen.f.core.configuration.applicationcontext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.SearchStrategy;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * chen-f应用上下文配置类
 *
 * @author chen
 * @since 2019/11/9 17:45.
 */
@Configuration
@ConditionalOnClass({ApplicationContext.class, ApplicationContextHelper.class})
public class EnableChenFApplicationContextConfiguration {
    protected static final Logger logger = LoggerFactory.getLogger(EnableChenFApplicationContextConfiguration.class);

    @Bean
    @ConditionalOnMissingBean(value = ApplicationContextHelper.class, search = SearchStrategy.CURRENT)
    public ApplicationContextHelper applicationContextHelper(ApplicationContext applicationContext, BeanFactory beanFactory) {
        ApplicationContextHelper applicationContextHelper = new ApplicationContextHelper(applicationContext);
        return applicationContextHelper;
    }

}
