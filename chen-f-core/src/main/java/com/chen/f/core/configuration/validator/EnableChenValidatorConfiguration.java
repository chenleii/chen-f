package com.chen.f.core.configuration.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.SearchStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.Validator;

/**
 * chen-f验证器配置类
 *
 * @author chen
 * @since 2019/11/9 17:45.
 */
@Configuration
@ConditionalOnClass({Validator.class, ValidatorHelper.class})
public class EnableChenValidatorConfiguration {
    protected static final Logger logger = LoggerFactory.getLogger(EnableChenValidatorConfiguration.class);

    @Bean
    @ConditionalOnMissingBean(value = ValidatorHelper.class, search = SearchStrategy.CURRENT)
    public ValidatorHelper validatorHelper(Validator validator) {
        return new ValidatorHelper(validator);
    }

}
