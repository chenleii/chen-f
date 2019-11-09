package com.chen.f.spring.boot.configuration.quartz;

import com.chen.f.admin.helper.QuartzHelper;
import com.chen.f.common.mapper.SysTimedTaskLogMapper;
import com.chen.f.common.mapper.SysTimedTaskMapper;
import com.chen.f.spring.boot.autoconfigure.mybatisplus.MybatisPlusAutoConfiguration;
import org.quartz.Scheduler;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.quartz.SchedulerFactoryBeanCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * quartz配置
 * <p>
 * 每次执行完任务重新储存任务数据 {@link org.quartz.PersistJobDataAfterExecution}
 * 禁止并发执行 {@link org.quartz.DisallowConcurrentExecution}
 *
 * @author chen
 * @date 2018/10/27 23:09.
 */
@Configuration
@ConditionalOnClass({Scheduler.class, SchedulerFactoryBean.class, PlatformTransactionManager.class})
@AutoConfigureBefore(org.springframework.boot.autoconfigure.quartz.QuartzAutoConfiguration.class)
@AutoConfigureAfter(MybatisPlusAutoConfiguration.class)
public class QuartzConfiguration {


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
    @ConditionalOnClass({QuartzHelper.class, SchedulerFactoryBean.class, SysTimedTaskMapper.class})
    public QuartzHelper quartzHelper(SchedulerFactoryBean schedulerFactoryBean, SysTimedTaskMapper sysTimedTaskMapper) {
        QuartzHelper quartzHelper = new QuartzHelper(schedulerFactoryBean, sysTimedTaskMapper);
        quartzHelper.init();
        return quartzHelper;
    }
}
