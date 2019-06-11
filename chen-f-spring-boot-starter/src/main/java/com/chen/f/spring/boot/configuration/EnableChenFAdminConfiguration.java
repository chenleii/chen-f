package com.chen.f.spring.boot.configuration;

import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.core.enums.IEnum;
import com.baomidou.mybatisplus.extension.handlers.EnumTypeHandler;
import com.baomidou.mybatisplus.extension.toolkit.PackageHelper;
import com.chen.f.admin.helper.QuartzHelper;
import com.chen.f.admin.helper.SysUserRolePermissionHelper;
import com.chen.f.core.mapper.*;
import com.chen.f.core.mybatisplus.SupperMapper;
import com.chen.f.spring.boot.exception.ChenFConfigurationException;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

/**
 * @author chen
 * @since 2019/1/14 17:00.
 */

@Configuration
public class EnableChenFAdminConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(EnableChenFAdminConfiguration.class);
    private static final String CHEN_F_ADMIN_BASE_PATH = "classpath*:com/chen/f/admin";
    private static final String CHEN_F_ADMIN_MAPPER_XML_PATH = "/mapper/xml/*";
    private static final String CHEN_F_ADMIN_BASE_PACKAGE = "com.chen.f.admin";
    private static final String CHEN_F_ADMIN_MAPPER_PACKAGE = ".mapper";
    private static final String CHEN_F_ADMIN_POJO_ENUMS_PACKAGE = ".pojo.enums";

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        logger.debug("chen-f-admin mapper scanner");
        MapperScannerConfigurer scannerConfigurer = new MapperScannerConfigurer();
        scannerConfigurer.setBasePackage(CHEN_F_ADMIN_BASE_PACKAGE + CHEN_F_ADMIN_MAPPER_PACKAGE);
        scannerConfigurer.setMarkerInterface(SupperMapper.class);
        //scannerConfigurer.setAnnotationClass(Mapper.class);
        return scannerConfigurer;
    }

    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return new ConfigurationCustomizer() {
            @Override
            public void customize(org.apache.ibatis.session.Configuration configuration) {
                // 配置枚举类型转换注册器
                TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
                // 扫描枚举包下枚举类
                Set<Class<?>> enumClasseSet = PackageHelper.scanTypePackage(CHEN_F_ADMIN_BASE_PACKAGE + CHEN_F_ADMIN_POJO_ENUMS_PACKAGE);
                enumClasseSet.stream()
                        .filter(Class::isEnum)
                        .filter(cls -> IEnum.class.isAssignableFrom(cls) || EnumTypeHandler.dealEnumType(cls).isPresent())
                        .forEach(cls -> typeHandlerRegistry.register(cls, EnumTypeHandler.class));

                // 配置 mapper xml文件
                PathMatchingResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
                Resource[] resources;
                try {
                    resources = resourceResolver.getResources(CHEN_F_ADMIN_BASE_PATH + CHEN_F_ADMIN_MAPPER_XML_PATH);
                } catch (IOException e) {
                    throw new ChenFConfigurationException("获取路径: '" + CHEN_F_ADMIN_BASE_PATH + CHEN_F_ADMIN_MAPPER_XML_PATH + "' mapper xml文件异常", e);
                }
                for (Resource resource : resources) {
                    try (InputStream inputStream = resource.getInputStream();) {
                        XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(inputStream,
                                configuration, resource.toString(), configuration.getSqlFragments());
                        xmlMapperBuilder.parse();
                        logger.debug("解析mapper xml文件:[{}]成功 ", resource);
                    } catch (Exception e) {
                        throw new ChenFConfigurationException("解析mapper xml文件: '" + resource + "' 异常", e);
                    } finally {
                        ErrorContext.instance().reset();
                    }
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
