package com.chen.f.spring.boot.configuration;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusPropertiesCustomizer;
import com.chen.f.admin.helper.QuartzHelper;
import com.chen.f.admin.helper.SysUserRolePermissionHelper;
import com.chen.f.core.mapper.*;
import com.chen.f.core.mybatisplus.SupperMapper;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.security.crypto.password.PasswordEncoder;

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
        scannerConfigurer.setMarkerInterface(SupperMapper.class);
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

    @Bean
    //@ConditionalOnBean({SchedulerFactoryBean.class, SysTimedTaskMapper.class})
    @ConditionalOnClass({QuartzHelper.class, SchedulerFactoryBean.class, SysTimedTaskMapper.class})
    public QuartzHelper quartzHelper(SchedulerFactoryBean schedulerFactoryBean, SysTimedTaskMapper sysTimedTaskMapper) {
        QuartzHelper quartzHelper = new QuartzHelper(schedulerFactoryBean, sysTimedTaskMapper);
        quartzHelper.init();
        return quartzHelper;
    }


    @Bean
    @ConditionalOnClass({
            SysUserRolePermissionHelper.class,
            PasswordEncoder.class,
            SysUserMapper.class,
            SysRoleMapper.class,
            SysPermissionMapper.class,
            SysUserRoleMapper.class,
            SysRolePermissionMapper.class,
            SysUserRolePermissionMapper.class,
    })
    public SysUserRolePermissionHelper sysUserRolePermissionHelper(PasswordEncoder passwordEncoder,
                                                                   SysUserMapper sysUserMapper, SysRoleMapper sysRoleMapper, SysPermissionMapper sysPermissionMapper,
                                                                   SysUserRoleMapper sysUserRoleMapper, SysRolePermissionMapper sysRolePermissionMapper, SysUserRolePermissionMapper sysUserRolePermissionMapper) {
        return new SysUserRolePermissionHelper(passwordEncoder, sysUserMapper, sysRoleMapper, sysPermissionMapper, sysUserRoleMapper, sysRolePermissionMapper, sysUserRolePermissionMapper);
    }
}
