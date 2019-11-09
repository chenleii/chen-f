package com.chen.f.spring.boot.configuration;

import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 开启chen-f国际化配置
 *
 * @author chen
 * @since 2019/1/15 0:13.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({EnableChenFI18nConfiguration.class})
public @interface EnableChenFI18n {

    /**
     * 国际化的过滤器优先级
     * <p>
     * 不建议修改
     */
    int i18nFilterOrder() default Ordered.HIGHEST_PRECEDENCE;

    /**
     * 在请求头中获取时区的请求头名称
     */
    String timeZoneHeaderName() default "time-zone";
}
