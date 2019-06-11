package com.chen.f.spring.boot.configuration.springsecurity;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import com.chen.f.core.mapper.SysPermissionMapper;
import com.chen.f.core.mapper.SysRoleMapper;
import com.chen.f.core.mapper.SysUserMapper;
import com.chen.f.core.mapper.SysUserRolePermissionMapper;
import com.chen.f.admin.security.DefaultUserDetailsService;
import com.chen.f.spring.boot.configuration.web.SpringSecurityExceptionHandle;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.session.SessionAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;

/**
 *
 * @author chen
 * @date 2018/10/22 19:43.
 */
@Configuration
@AutoConfigureAfter({MybatisPlusAutoConfiguration.class, SessionAutoConfiguration.class})
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, proxyTargetClass = true)
public class SpringSecurityConfiguration extends GlobalMethodSecurityConfiguration {

    private static final String DEFAULT_ROLE_PREFIX = "ROLE_";

    private String rolePrefix = DEFAULT_ROLE_PREFIX;

    public void setRolePrefix(String rolePrefix) {
        this.rolePrefix = rolePrefix;
    }

    /**
     * 去除@Secured("ROLE_ADMIN")，ROLE_前缀
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
     * 去除hasRole("ROLE_ADMIN")，ROLE_前缀
     * <p>
     * GrantedAuthorityDefaults将更改DefaultWebSecurityExpressionHandler和DefaultMethodSecurityExpressionHandler的前缀，
     * 但它不会修改@EnableGlobalMethodSecurity 设置的前缀RoleVoter.rolePrefix
     */
    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults(this.rolePrefix);
    }


    @Bean
    @ConditionalOnMissingBean
    //@ConditionalOnBean({SysUserRolePermissionMapper.class,SysUserMapper.class,SysRoleMapper.class,SysPermissionMapper.class})
    @ConditionalOnClass({SysUserRolePermissionMapper.class})
    public UserDetailsService userDetailsService(SysUserRolePermissionMapper sysUserRolePermissionMapper,
                                                 SysUserMapper sysUserMapper, SysRoleMapper sysRoleMapper, SysPermissionMapper sysPermissionMapper) {
        return new DefaultUserDetailsService(sysUserRolePermissionMapper,
                sysUserMapper, sysRoleMapper, sysPermissionMapper,
                grantedAuthorityDefaults().getRolePrefix());
    }

    @Bean
    @ConditionalOnClass({SessionRegistry.class})
    public SessionRegistry sessionRegistry(FindByIndexNameSessionRepository<?> findByIndexNameSessionRepository) {
        return new SpringSessionBackedSessionRegistry<>(findByIndexNameSessionRepository);
    }

    @Bean
    public SpringSecurityExceptionHandle springSecurityExceptionHandle() {
        return new SpringSecurityExceptionHandle();
    }
}
