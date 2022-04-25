package com.chen.f.admin.configuration.quartz;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import com.chen.f.common.mapper.SysTimedTaskLogMapper;
import com.chen.f.common.mapper.SysTimedTaskMapper;
import com.chen.f.common.pojo.SysTimedTask;
import com.chen.f.common.service.ISysTimedTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.quartz.SchedulerFactoryBeanCustomizer;
import org.springframework.boot.sql.init.dependency.DependsOnDatabaseInitialization;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author chen
 * @since 2019/1/14 17:00.
 */
@Configuration
@AutoConfigureAfter({MybatisPlusAutoConfiguration.class})
public class EnableChenFAdminQuartzConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(EnableChenFAdminQuartzConfiguration.class);

    /**
     * 在 Customizer 中处理自定义SchedulerFactoryBean配置
     */
    @ConditionalOnClass({SysTimedTaskLogMapper.class, SysTimedTaskMapper.class})
    @Bean
    public SchedulerFactoryBeanCustomizer schedulerFactoryBeanCustomizer(SysTimedTaskLogMapper sysTimedTaskLogMapper, SysTimedTaskMapper sysTimedTaskMapper) {
        return (schedulerFactoryBean) -> {
            schedulerFactoryBean.setGlobalJobListeners(new TaskLogJobListener(sysTimedTaskLogMapper, sysTimedTaskMapper));

        };
    }

    @Bean
    @DependsOnDatabaseInitialization
    CommandLineRunner initSysTimedTask(ISysTimedTaskService sysTimedTaskService) {
        return (args) -> {
            sysTimedTaskService.initSysTimedTask();
        };
    }

}
