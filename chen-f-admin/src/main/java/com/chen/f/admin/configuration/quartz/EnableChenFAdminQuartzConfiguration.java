package com.chen.f.admin.configuration.quartz;

import com.chen.f.common.mapper.SysTimedTaskLogMapper;
import com.chen.f.common.mapper.SysTimedTaskMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.quartz.SchedulerFactoryBeanCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author chen
 * @since 2019/1/14 17:00.
 */
@Configuration
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
    

}
