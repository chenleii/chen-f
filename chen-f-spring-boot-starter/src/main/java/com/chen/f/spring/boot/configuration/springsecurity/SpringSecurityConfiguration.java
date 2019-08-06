package com.chen.f.spring.boot.configuration.springsecurity;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import com.chen.f.common.mapper.*;
import com.chen.f.common.pojo.SysApiRolePermission;
import com.chen.f.common.pojo.SysPermission;
import com.chen.f.common.pojo.SysRole;
import com.chen.f.spring.boot.configuration.springsecurity.exceptionhandle.SpringSecurityExceptionHandle;
import com.chen.f.spring.boot.configuration.springsecurity.service.DefaultUserDetailsService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.session.SessionAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;

import java.util.List;
import java.util.stream.Collectors;

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
    @ConditionalOnMissingBean
    @ConditionalOnClass({SysUserRolePermissionMapper.class,SysUserMapper.class,SysRoleMapper.class,SysPermissionMapper.class})
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


    @Bean
    public HttpSecurityCustomizer httpSecurityCustomizer(SysApiRolePermissionMapper sysApiRolePermissionMapper) {
        return (httpSecurity) -> {
            if (sysApiRolePermissionMapper != null) {
                List<SysApiRolePermission> sysApiRolePermissionList = sysApiRolePermissionMapper.selectSysApiRolePermissionList();
                if (CollectionUtils.isNotEmpty(sysApiRolePermissionList)) {
                    ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry expressionInterceptUrlRegistry = httpSecurity.authorizeRequests();
                    for (SysApiRolePermission sysApiRolePermission : sysApiRolePermissionList) {
                        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.AuthorizedUrl authorizedUrl = expressionInterceptUrlRegistry.antMatchers(HttpMethod.resolve(sysApiRolePermission.getHttpMethod().httpMethod), sysApiRolePermission.getUrl());
                        //有角色字符串
                        String hasRoleString = null;
                        //有权限字符串
                        String hasAuthorityString = null;
                        if (CollectionUtils.isNotEmpty(sysApiRolePermission.getSysRoleList())) {
                            hasRoleString = sysApiRolePermission.getSysRoleList().stream()
                                    .map(SysRole::getName)
                                    .map((s) -> "'" + s + "'")
                                    .collect(Collectors.joining(",", "hasRole(", ")"));
                        }
                        if (CollectionUtils.isNotEmpty(sysApiRolePermission.getSysPermissionList())) {
                            hasAuthorityString = sysApiRolePermission.getSysPermissionList().stream()
                                    .map(SysPermission::getName)
                                    .map((s) -> "'" + s + "'")
                                    .collect(Collectors.joining(",", "hasAuthority(", ")"));
                        }

                        if (StringUtils.isNotBlank(hasRoleString) && StringUtils.isNotBlank(hasAuthorityString)) {
                            // 有角色字符串 and 有权限字符串
                            authorizedUrl.access(hasRoleString + " and " + hasAuthorityString);
                        }else if (StringUtils.isNotBlank(hasRoleString) ) {
                            // 有角色字符串
                            authorizedUrl.access(hasRoleString );
                        }else if (StringUtils.isNotBlank(hasAuthorityString)) {
                            // 有权限字符串
                            authorizedUrl.access( hasAuthorityString);
                        }else{
                            // "permitAll()"
                            authorizedUrl.access( "permitAll()");
                        }
                    }
                }
            }
        };
    }
}
