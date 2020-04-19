package com.chen.f.admin.configuration;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusPropertiesCustomizer;
import com.chen.f.admin.configuration.quartz.TaskLogJobListener;
import com.chen.f.common.mapper.SysTimedTaskLogMapper;
import com.chen.f.common.mapper.SysTimedTaskMapper;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
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
public class EnableChenFAdminConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(EnableChenFAdminConfiguration.class);

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        String chenFAdminMapperScannerBasePackage = "com.chen.f.admin.mapper";
        logger.debug("add chen-f-admin mapper scanner [{}]", chenFAdminMapperScannerBasePackage);
        MapperScannerConfigurer scannerConfigurer = new MapperScannerConfigurer();
        scannerConfigurer.setBasePackage(chenFAdminMapperScannerBasePackage);
        //scannerConfigurer.setMarkerInterface(SupperMapper.class);
        //scannerConfigurer.setAnnotationClass(Mapper.class);
        return scannerConfigurer;
    }

    @Bean
    public MybatisPlusPropertiesCustomizer mybatisPlusPropertiesCustomizerAdmin() {
        return new MybatisPlusPropertiesCustomizer() {
            @Override
            public void customize(MybatisPlusProperties properties) {
                String[] chenFAdminMapperLocations = new String[]{"classpath*:com/chen/f/admin/mapper/xml/*"};
                logger.debug("add chen-f-admin mapper locations [{}]", (Object[]) chenFAdminMapperLocations);
                properties.setMapperLocations(ArrayUtils.addAll(properties.getMapperLocations(), chenFAdminMapperLocations));

                String chenFAdminTypeEnumsPackage = "com.chen.f.admin.pojo.enums";
                logger.debug("add chen-f-admin type enums package [{}]", chenFAdminTypeEnumsPackage);
                if (StringUtils.isNotBlank(properties.getTypeEnumsPackage())) {
                    properties.setTypeEnumsPackage(properties.getTypeEnumsPackage() + "," + chenFAdminTypeEnumsPackage);
                } else {
                    properties.setTypeEnumsPackage(chenFAdminTypeEnumsPackage);
                }

            }
        };
    }



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
