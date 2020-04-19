package com.chen.f.core.configuration.mybatisplus;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusPropertiesCustomizer;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
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

/**chen-f mybatis-plus配置类
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
     */
    @Bean
    @ConditionalOnMissingBean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    /**
     * 乐观锁
     */
    @Bean
    @ConditionalOnMissingBean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
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

}

