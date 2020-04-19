package com.chen.f.core.configuration.session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.session.SessionAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.session.web.http.CookieHttpSessionIdResolver;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;
import org.springframework.util.Assert;

/**
 * chen-f会话配置类
 *
 * @author chen
 * @since 2019/11/9 17:45.
 */
@Configuration
@ConditionalOnClass({RedisOperations.class, HttpSessionIdResolver.class})
@AutoConfigureBefore({SessionAutoConfiguration.class})
public class EnableChenFSessionConfiguration implements ImportAware {
    protected static final Logger logger = LoggerFactory.getLogger(EnableChenFSessionConfiguration.class);

    private SessionIdResolutionTypeEnum sessionIdResolutionTypeEnum;
    private String name;

    @Override
    public void setImportMetadata(AnnotationMetadata importMetadata) {
        AnnotationAttributes annotationAttributes = AnnotationAttributes
                .fromMap(importMetadata.getAnnotationAttributes(EnableChenFSession.class.getName()));
        Assert.notNull(annotationAttributes,
                "@EnableChenFSession is not present on importing class " + importMetadata.getClassName());

        this.sessionIdResolutionTypeEnum = annotationAttributes.getEnum("sessionIdResolutionType");
        this.name = annotationAttributes.getString("name");
    }

    @Bean
    @ConditionalOnMissingBean
    public HttpSessionIdResolver httpSessionIdResolver() {
        if (sessionIdResolutionTypeEnum == SessionIdResolutionTypeEnum.COOKIE) {
            final DefaultCookieSerializer defaultCookieSerializer = new DefaultCookieSerializer();
            defaultCookieSerializer.setCookieName(this.name);
            final CookieHttpSessionIdResolver cookieHttpSessionIdResolver = new CookieHttpSessionIdResolver();
            cookieHttpSessionIdResolver.setCookieSerializer(defaultCookieSerializer);
            return cookieHttpSessionIdResolver;
        } else if (sessionIdResolutionTypeEnum == SessionIdResolutionTypeEnum.HEADER) {
            final HeaderHttpSessionIdResolver headerHttpSessionIdResolver = new HeaderHttpSessionIdResolver(this.name);
            return headerHttpSessionIdResolver;
        } else {
            throw new IllegalArgumentException("uncharted sessionIdResolutionTypeEnum ");
        }
    }

}
