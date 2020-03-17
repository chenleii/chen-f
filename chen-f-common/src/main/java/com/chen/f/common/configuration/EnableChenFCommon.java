package com.chen.f.common.configuration;

import com.chen.f.common.configuration.distributedlock.EnableDistributedLock;
import com.chen.f.common.configuration.errorhandle.EnableChenFErrorHandle;
import com.chen.f.common.configuration.i18n.EnableChenFI18n;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

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
        EnableChenFCommonMybatisPlusConfiguration.class,
        EnableChenFCommonRedisConfiguration.class,
        EnableChenFCommonSpringSessionConfiguration.class,
        EnableChenFCommonSwagger2Configuration.class,
        EnableChenFCommonWebMvcConfiguration.class,

        EnableChenFCommonConfiguration.class,
        EnableChenFCommonConfigurationImportSelector.class,
})
@EnableChenFI18n
@EnableChenFErrorHandle
@EnableCaching
@EnableDistributedLock
public @interface EnableChenFCommon {
}
