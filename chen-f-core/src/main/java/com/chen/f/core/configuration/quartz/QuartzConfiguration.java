package com.chen.f.core.configuration.quartz;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import org.quartz.Scheduler;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * chen-f quartz配置
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


    @Bean
    @ConditionalOnClass({QuartzHelper.class, SchedulerFactoryBean.class})
    public QuartzHelper quartzHelper(SchedulerFactoryBean schedulerFactoryBean) {
        QuartzHelper quartzHelper = new QuartzHelper(schedulerFactoryBean);
        return quartzHelper;
    }
}
