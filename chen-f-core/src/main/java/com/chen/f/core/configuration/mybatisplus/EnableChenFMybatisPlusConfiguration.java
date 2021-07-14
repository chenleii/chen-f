package com.chen.f.core.configuration.mybatisplus;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusPropertiesCustomizer;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.chen.f.core.mybatisplus.sqlinjector.ChenSqlInjector;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * chen-f mybatis-plus配置类
 *
 * @author chen
 * @since 2018/2/2 14:26.
 */
@Configuration
@ConditionalOnClass({MybatisPlusAutoConfiguration.class, SqlSessionFactory.class, SqlSessionFactoryBean.class})
@AutoConfigureBefore(com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration.class)
public class EnableChenFMybatisPlusConfiguration {
    protected static final Logger logger = LoggerFactory.getLogger(EnableChenFMybatisPlusConfiguration.class);

    /**
     * 分页插件
     * 乐观锁插件
     */
    @Bean
    @ConditionalOnMissingBean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        mybatisPlusInterceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        return mybatisPlusInterceptor;
    }

    @Bean
    public MybatisPlusPropertiesCustomizer mybatisPlusPropertiesCustomizerCore() {
        return new MybatisPlusPropertiesCustomizer() {
            @Override
            public void customize(MybatisPlusProperties properties) {

                String chenFCoreTypeHandlersPackage = "com.chen.f.core.configuration.mybatisplus.typehandler";
                logger.debug("add chen-f-common type handlers package [{}]", chenFCoreTypeHandlersPackage);
                if (StringUtils.isNotBlank(properties.getTypeHandlersPackage())) {
                    properties.setTypeHandlersPackage(properties.getTypeHandlersPackage() + "," + chenFCoreTypeHandlersPackage);
                } else {
                    properties.setTypeHandlersPackage(chenFCoreTypeHandlersPackage);
                }
            }
        };
    }



    @Bean
    public ChenSqlInjector rrcSqlInjector() {
        return new ChenSqlInjector();
    }


}

