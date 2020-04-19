package com.chen.f.common.configuration;

import com.chen.f.core.configuration.applicationcontext.EnableChenFApplicationContext;
import com.chen.f.core.configuration.cache.EnableChenFCache;
import com.chen.f.core.configuration.distributedlock.EnableDistributedLock;
import com.chen.f.core.configuration.errorhandle.EnableChenFErrorHandle;
import com.chen.f.core.configuration.i18n.EnableChenFI18n;
import com.chen.f.core.configuration.mybatisplus.EnableChenFMybatisPlus;
import com.chen.f.core.configuration.redis.EnableChenFRedis;
import com.chen.f.core.configuration.session.EnableChenFSession;
import com.chen.f.core.configuration.swagger.EnableChenFSwagger;
import com.chen.f.core.configuration.validator.EnableChenFValidator;
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
        EnableChenFCommonConfiguration.class,
        EnableChenFCommonConfigurationImportSelector.class,
})
@EnableChenFI18n
@EnableChenFErrorHandle
@EnableCaching
@EnableChenFCache
@EnableChenFValidator
@EnableDistributedLock
@EnableChenFRedis
@EnableChenFSession
@EnableChenFApplicationContext
@EnableChenFMybatisPlus
@EnableChenFSwagger
public @interface EnableChenFCommon {
}
