package com.chen.f.spring.boot.autoconfigure.mybatisplus;

import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author chen
 * @since 2018/2/2 14:26.
 */
@Configuration
@ConditionalOnClass({MybatisPlusAutoConfiguration.class, SqlSessionFactory.class, SqlSessionFactoryBean.class})
@AutoConfigureBefore(com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration.class)
public class MybatisPlusAutoConfiguration {


    ///**
    // * 性能分析插件
    // * 设置 beta 环境开启
    // */
    //@Profile({"beta"})
    //@Bean
    //public PerformanceInterceptor performanceInterceptor() {
    //    PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
    //    performanceInterceptor.setFormat(true);
    //    performanceInterceptor.setWriteInLog(true);
    //    return performanceInterceptor;
    //}

    ///**
    // * 逻辑删除
    // */
    //@Bean
    //public ISqlInjector sqlInjector() {
    //    return new LogicSqlInjector();
    //}

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    /**
     * 乐观锁
     */
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }


}

