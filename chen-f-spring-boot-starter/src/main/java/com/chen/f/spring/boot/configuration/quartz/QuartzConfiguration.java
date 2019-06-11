package com.chen.f.spring.boot.configuration.quartz;

import com.chen.f.core.mapper.SysTimedTaskLogMapper;
import com.chen.f.core.mapper.SysTimedTaskMapper;
import com.chen.f.admin.quartz.TaskLogJobListener;
import com.chen.f.spring.boot.autoconfigure.mybatisplus.MybatisPlusAutoConfiguration;
import org.quartz.JobListener;
import org.quartz.Scheduler;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.quartz.SchedulerFactoryBeanCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * @author chen
 * @PersistJobDataAfterExecution
 * @DisallowConcurrentExecution
 * @date 2018/10/27 23:09.
 */
@Configuration
@ConditionalOnClass({Scheduler.class, SchedulerFactoryBean.class, PlatformTransactionManager.class})
@AutoConfigureBefore(org.springframework.boot.autoconfigure.quartz.QuartzAutoConfiguration.class)
@AutoConfigureAfter(MybatisPlusAutoConfiguration.class)
public class QuartzConfiguration {

    @ConditionalOnClass({SysTimedTaskLogMapper.class, SysTimedTaskMapper.class})
    @Bean
    public JobListener taskLogJobListener(SysTimedTaskLogMapper sysTimedTaskLogMapper, SysTimedTaskMapper sysTimedTaskMapper) {
        return new TaskLogJobListener(sysTimedTaskLogMapper, sysTimedTaskMapper);
    }

    /**
     * 在 Customizer 中处理自定义SchedulerFactoryBean配置
     */
    @Bean
    public SchedulerFactoryBeanCustomizer schedulerFactoryBeanCustomizer(ObjectProvider<JobListener[]> jobListeners) {
        return (schedulerFactoryBean) -> {
            schedulerFactoryBean.setGlobalJobListeners(jobListeners.getIfAvailable());


        };
    }
}
