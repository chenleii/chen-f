package com.chen.f.common.configuration;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author chen
 * @since 2018/2/2 14:26.
 */
@Configuration
@ConditionalOnClass({MybatisPlusAutoConfiguration.class, SqlSessionFactory.class, SqlSessionFactoryBean.class})
@AutoConfigureBefore(com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration.class)
public class EnableChenFCommonMybatisPlusConfiguration {

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


}

