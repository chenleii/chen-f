package com.chen.f.spring.boot.configuration;

import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.core.enums.IEnum;
import com.baomidou.mybatisplus.extension.handlers.EnumTypeHandler;
import com.baomidou.mybatisplus.extension.toolkit.PackageHelper;
import com.chen.f.core.mybatisplus.SupperMapper;
import com.chen.f.spring.boot.exception.ChenFConfigurationException;
import com.chen.f.spring.boot.mybatis.LocalDateTimeTypeHandler;
import com.chen.f.spring.boot.mybatis.LocalDateTypeHandler;
import com.chen.f.spring.boot.mybatis.LocalTimeTypeHandler;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

/**
 * chen-f-core 配置
 *
 * @author chen
 * @since 2019/1/14 17:00.
 */
@Configuration
public class EnableChenFCoreConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(EnableChenFCoreConfiguration.class);
    private static final String CHEN_F_CORE_BASE_PACKAGE = "com.chen.f.core";
    private static final String CHEN_F_CORE_MAPPER_PACKAGE = ".mapper";
    private static final String CHEN_F_CORE_POJO_ENUMS_PACKAGE = ".pojo.enums";

    private static final String CHEN_F_CORE_BASE_PATH = "classpath*:com/chen/f/core";
    private static final String CHEN_F_CORE_MAPPER_XML_PATH = "/mapper/xml/*";


    @Bean
    public MapperScannerConfigurer mapperScannerConfigurerCore() {
        logger.debug("chen-f-core mapper scanner");
        MapperScannerConfigurer scannerConfigurer = new MapperScannerConfigurer();
        scannerConfigurer.setBasePackage(CHEN_F_CORE_BASE_PACKAGE + CHEN_F_CORE_MAPPER_PACKAGE);
        scannerConfigurer.setMarkerInterface(SupperMapper.class);
        //scannerConfigurer.setAnnotationClass(Mapper.class);
        return scannerConfigurer;
    }

    @Bean
    public ConfigurationCustomizer configurationCustomizerCore() {
        return new ConfigurationCustomizer() {
            @Override
            public void customize(org.apache.ibatis.session.Configuration configuration) {
                // 配置枚举类型转换注册器
                TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();

                // druid 适配 mybatis3.5.1
                // 解决druid 没有实现jdbc4.2问题
                typeHandlerRegistry.register(LocalDateTime.class, LocalDateTimeTypeHandler.class);
                typeHandlerRegistry.register(LocalDate.class, LocalDateTypeHandler.class);
                typeHandlerRegistry.register(LocalTime.class, LocalTimeTypeHandler.class);

                // 扫描枚举包下枚举类
                Set<Class<?>> enumClasseSet = PackageHelper.scanTypePackage(CHEN_F_CORE_BASE_PACKAGE + CHEN_F_CORE_POJO_ENUMS_PACKAGE);
                enumClasseSet.stream()
                        .filter(Class::isEnum)
                        .filter(cls -> IEnum.class.isAssignableFrom(cls) || EnumTypeHandler.dealEnumType(cls).isPresent())
                        .forEach(cls -> typeHandlerRegistry.register(cls, EnumTypeHandler.class));

                // 配置 mapper xml文件
                PathMatchingResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
                Resource[] resources;
                try {
                    resources = resourceResolver.getResources(CHEN_F_CORE_BASE_PATH + CHEN_F_CORE_MAPPER_XML_PATH);
                } catch (IOException e) {
                    throw new ChenFConfigurationException("获取路径: '" + CHEN_F_CORE_BASE_PATH + CHEN_F_CORE_MAPPER_XML_PATH + "' mapper xml文件异常", e);
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
}
