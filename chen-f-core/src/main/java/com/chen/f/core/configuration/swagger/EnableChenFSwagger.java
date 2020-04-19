package com.chen.f.core.configuration.swagger;

import org.springframework.context.annotation.Import;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 开启chen-f swagger配置
 *
 * @author chen
 * @since 2019/1/15 0:13.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({EnableChenFSwaggerConfiguration.class})
@EnableSwagger2
public @interface EnableChenFSwagger {

    String title() default "chen-f api";

    String description() default "chen-f api";

    String version() default "v0.0.1";

    String licenseUrl() default "";

    String basePackage() default "com.chen.f";

    String contactName() default "chen";

    String contactUrl() default "";

    String contactEmail() default "1@1.com";
}
