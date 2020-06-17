package com.chen.f.core.configuration.security;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;
import org.springframework.util.Assert;

/**
 * spring security配置
 *
 * @author chen
 * @date 2018/10/22 19:43.
 */
@Configuration
@AutoConfigureBefore({WebSpringSecurityConfiguration.class, ReactiveWebSpringSecurityConfiguration.class})
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, proxyTargetClass = true)
public class SpringSecurityConfiguration implements ImportAware {

    private static final String DEFAULT_ROLE_PREFIX = "ROLE_";

    private String rolePrefix = DEFAULT_ROLE_PREFIX;


    @Override
    public void setImportMetadata(AnnotationMetadata importMetadata) {
        AnnotationAttributes annotationAttributes = AnnotationAttributes
                .fromMap(importMetadata.getAnnotationAttributes(EnableChenFSpringSecurity.class.getName()));
        Assert.notNull(annotationAttributes,
                "@EnableChenFSpringSecurity is not present on importing class " + importMetadata.getClassName());

        final String rolePrefix = annotationAttributes.getString("rolePrefix");
        if (StringUtils.isNotBlank(rolePrefix)) {
            this.rolePrefix = rolePrefix;
        }
    }


    /**
     * 授予的权限默认值
     * <p>
     * 默认的角色前缀
     */
    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults(this.rolePrefix);
    }


    @Bean
    @ConditionalOnClass({SessionRegistry.class})
    public SessionRegistry sessionRegistry(FindByIndexNameSessionRepository<?> findByIndexNameSessionRepository) {
        return new SpringSessionBackedSessionRegistry<>(findByIndexNameSessionRepository);
    }

    @Bean
    @ConditionalOnClass({SessionRegistry.class})
    public SecuritySessionHelper springSecurityHelper(SessionRegistry sessionRegistry) {
        return new SecuritySessionHelper(sessionRegistry);
    }

}
