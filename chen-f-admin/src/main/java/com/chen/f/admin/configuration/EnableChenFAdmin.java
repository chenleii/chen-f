package com.chen.f.admin.configuration;

import com.chen.f.core.configuration.quartz.EnableChenFQuartz;
import com.chen.f.admin.configuration.security.EnableChenFSpringSecurity;
import com.chen.f.common.configuration.EnableChenFCommon;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

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
})
@EnableChenFCommon
@EnableChenFQuartz
@EnableChenFSpringSecurity
public @interface EnableChenFAdmin {
}
