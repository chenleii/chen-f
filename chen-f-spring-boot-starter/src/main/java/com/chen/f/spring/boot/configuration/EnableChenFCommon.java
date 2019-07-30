package com.chen.f.spring.boot.configuration;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 开启chen-f核心配置
 *
 * @author chen
 * @since 2019/1/15 0:13.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({
        EnableChenFCommonConfiguration.class,
        EnableChenFCommonConfigurationImportSelector.class,
})
public @interface EnableChenFCommon {
}
