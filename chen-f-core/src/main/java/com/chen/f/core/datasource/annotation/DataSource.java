package com.chen.f.core.datasource.annotation;

import java.lang.annotation.*;

/**
 * 注解在类上或方法上来切换数据源
 *
 * @author chen
 * @since 2018/12/15
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {

    /**
     * 数据源名称
     *
     * @return 数据源名称
     */
    String value();
}