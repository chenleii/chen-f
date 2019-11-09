package com.chen.f.spring.boot.autoconfigure;

import com.chen.f.common.helper.ApplicationContextHelper;
import com.chen.f.common.helper.CacheHelper;
import com.chen.f.common.helper.MybatisPlusHelper;
import com.chen.f.common.helper.SysParameterHelper;
import com.chen.f.common.helper.ValidatorHelper;
import com.chen.f.common.service.ISysParameterService;
import com.chen.f.spring.boot.autoconfigure.mybatisplus.MybatisPlusAutoConfiguration;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.quartz.QuartzAutoConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.Validator;

/**
 * @author chen
 * @since 2018/12/16 20:09.
 */
@Configuration
@AutoConfigureAfter({MybatisPlusAutoConfiguration.class, QuartzAutoConfiguration.class})
public class ChenFAutoConfiguration {

    @Bean
    public ApplicationContextHelper applicationContextHelper(ApplicationContext applicationContext, BeanFactory beanFactory) {
        ApplicationContextHelper applicationContextHelper = new ApplicationContextHelper();
        applicationContextHelper.setApplicationContext(applicationContext);
        return applicationContextHelper;
    }

    @Bean
    public MybatisPlusHelper mybatisPlusHelper() {
        return new MybatisPlusHelper();
    }

    @Bean
    public SysParameterHelper sysParameterHelper(ISysParameterService sysParameterService) {
        SysParameterHelper sysParameterHelper = new SysParameterHelper(sysParameterService);
        sysParameterHelper.init();
        return sysParameterHelper;
    }

    @Bean
    public CacheHelper cacheHelper(CacheManager cacheManager) {
        return new CacheHelper(cacheManager);
    }

    @Bean
    public ValidatorHelper validatorHelper(Validator validator) {
        return new ValidatorHelper(validator);
    }


}
