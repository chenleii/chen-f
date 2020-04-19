package com.chen.f.core.configuration.security;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import com.chen.f.core.configuration.security.errorhandle.SpringSecurityExceptionHandle;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.session.SessionAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;

/**
 * spring security配置
 *
 * @author chen
 * @date 2018/10/22 19:43.
 */
@Configuration
@ConditionalOnClass({WebSecurityConfigurerAdapter.class, GlobalMethodSecurityConfiguration.class})
@AutoConfigureBefore({WebSpringSecurityConfiguration.class, ReactiveWebSpringSecurityConfiguration.class})
@AutoConfigureAfter({MybatisPlusAutoConfiguration.class, SessionAutoConfiguration.class})
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, proxyTargetClass = true)
public class SpringSecurityConfiguration extends GlobalMethodSecurityConfiguration {

    private static final String DEFAULT_ROLE_PREFIX = "ROLE_";

    private String rolePrefix = DEFAULT_ROLE_PREFIX;

    public void setRolePrefix(String rolePrefix) {
        this.rolePrefix = rolePrefix;
    }

    /**
     * 修改 "ROLE_" 默认前缀
     */
    @Override
    protected AccessDecisionManager accessDecisionManager() {
        AffirmativeBased accessDecisionManager = (AffirmativeBased) super.accessDecisionManager();
        accessDecisionManager.getDecisionVoters().stream()
                .filter(RoleVoter.class::isInstance)
                .map(RoleVoter.class::cast)
                .forEach(it -> it.setRolePrefix(grantedAuthorityDefaults().getRolePrefix()));
        return accessDecisionManager;
    }


    /**
     * 设置 "ROLE_" 默认前缀
     * <p>
     * GrantedAuthorityDefaults将更改DefaultWebSecurityExpressionHandler和DefaultMethodSecurityExpressionHandler的前缀，
     * 但它不会修改@EnableGlobalMethodSecurity 设置的前缀RoleVoter.rolePrefix
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

    @Configuration
    @ConditionalOnClass({AccessDeniedException.class, AuthenticationException.class})
    public static class SpringSecurityExceptionHandleConfiguration {

        @Bean
        @ConditionalOnMissingBean(SpringSecurityExceptionHandle.class)
        public SpringSecurityExceptionHandle springSecurityExceptionHandle() {
            return new SpringSecurityExceptionHandle();
        }
    }
}
