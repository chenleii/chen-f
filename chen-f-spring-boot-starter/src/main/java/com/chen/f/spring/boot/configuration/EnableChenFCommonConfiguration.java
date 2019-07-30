package com.chen.f.spring.boot.configuration;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusPropertiesCustomizer;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * chen-f-common 配置
 *
 * @author chen
 * @since 2019/1/14 17:00.
 */
@Configuration
public class EnableChenFCommonConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(EnableChenFCommonConfiguration.class);

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurerCore() {
        String chenFCoreMapperScannerBasePackage = "com.chen.f.common.mapper";
        logger.debug("add chen-f-common mapper scanner [{}]", chenFCoreMapperScannerBasePackage);
        MapperScannerConfigurer scannerConfigurer = new MapperScannerConfigurer();
        scannerConfigurer.setBasePackage(chenFCoreMapperScannerBasePackage);
        //scannerConfigurer.setMarkerInterface(SupperMapper.class);
        //scannerConfigurer.setAnnotationClass(Mapper.class);
        return scannerConfigurer;
    }


    @Bean
    public MybatisPlusPropertiesCustomizer mybatisPlusPropertiesCustomizerCore() {
        return new MybatisPlusPropertiesCustomizer() {
            @Override
            public void customize(MybatisPlusProperties properties) {

                String chenFCoreTypeHandlersPackage = "com.chen.f.spring.boot.mybatis";
                logger.debug("add chen-f-common type handlers package [{}]", chenFCoreTypeHandlersPackage);
                if (StringUtils.isNotBlank(properties.getTypeHandlersPackage())) {
                    properties.setTypeHandlersPackage(properties.getTypeHandlersPackage() + "," + chenFCoreTypeHandlersPackage);
                } else {
                    properties.setTypeHandlersPackage(chenFCoreTypeHandlersPackage);
                }

                String[] chenFCoreMapperLocations = new String[]{"classpath*:com/chen/f/common/mapper/xml/*"};
                logger.debug("add chen-f-common mapper locations [{}]", (Object[]) chenFCoreMapperLocations);
                properties.setMapperLocations(ArrayUtils.addAll(properties.getMapperLocations(), chenFCoreMapperLocations));

                String chenFCoreTypeEnumsPackage = "com.chen.f.common.pojo.enums";
                logger.debug("add chen-f-common type enums package [{}]", chenFCoreTypeEnumsPackage);
                if (StringUtils.isNotBlank(properties.getTypeEnumsPackage())) {
                    properties.setTypeEnumsPackage(properties.getTypeEnumsPackage() + "," + chenFCoreTypeEnumsPackage);
                } else {
                    properties.setTypeEnumsPackage(chenFCoreTypeEnumsPackage);
                }
            }
        };
    }
}
