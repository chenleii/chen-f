package com.chen.f.spring.boot.configuration;

import com.chen.f.spring.boot.configuration.quartz.QuartzConfiguration;
import com.chen.f.spring.boot.configuration.springsecurity.ReactiveWebSpringSecurityConfiguration;
import com.chen.f.spring.boot.configuration.springsecurity.SpringSecurityConfiguration;
import com.chen.f.spring.boot.configuration.springsecurity.WebSpringSecurityConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 用于开启后台管理配置
 * (导入管理平台需要的controller,service,mapper)
 *
 * @author chen
 * @since 2019/1/14 14:22.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({
        EnableChenFAdminConfiguration.class,
        EnableChenFAdminConfigurationImportSelector.class,
        SpringSecurityConfiguration.class,
        WebSpringSecurityConfiguration.class,
        ReactiveWebSpringSecurityConfiguration.class,
        QuartzConfiguration.class,
})
@EnableChenFCore
public @interface EnableChenFAdmin {
}
