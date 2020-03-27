package com.chen.f.admin.configuration.security;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import com.chen.f.admin.configuration.helper.SecuritySessionHelper;
import com.chen.f.admin.configuration.helper.SysUserRolePermissionHelper;
import com.chen.f.admin.configuration.security.errorhandle.SpringSecurityExceptionHandle;
import com.chen.f.admin.configuration.security.service.DefaultUserDetailsService;
import com.chen.f.common.mapper.SysApiRolePermissionMapper;
import com.chen.f.common.mapper.SysOrganizationMapper;
import com.chen.f.common.mapper.SysOrganizationRoleMapper;
import com.chen.f.common.mapper.SysOrganizationUserMapper;
import com.chen.f.common.mapper.SysPermissionMapper;
import com.chen.f.common.mapper.SysRoleMapper;
import com.chen.f.common.mapper.SysRolePermissionMapper;
import com.chen.f.common.mapper.SysUserMapper;
import com.chen.f.common.mapper.SysUserRoleMapper;
import com.chen.f.common.mapper.SysUserRolePermissionMapper;
import com.chen.f.common.pojo.SysApiRolePermission;
import com.chen.f.common.pojo.SysPermission;
import com.chen.f.common.pojo.SysRole;
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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    @ConditionalOnClass({SysOrganizationMapper.class, SysOrganizationUserMapper.class, SysOrganizationRoleMapper.class,
            SysUserMapper.class, SysUserRoleMapper.class, SysRoleMapper.class, SysRolePermissionMapper.class, SysPermissionMapper.class, SysUserRolePermissionMapper.class,
    })
    public UserDetailsService userDetailsService(SysOrganizationMapper sysOrganizationMapper,
                                                 SysOrganizationUserMapper sysOrganizationUserMapper,
                                                 SysOrganizationRoleMapper sysOrganizationRoleMapper,
                                                 SysUserMapper sysUserMapper,
                                                 SysUserRoleMapper sysUserRoleMapper,
                                                 SysRoleMapper sysRoleMapper,
                                                 SysRolePermissionMapper sysRolePermissionMapper,
                                                 SysPermissionMapper sysPermissionMapper,
                                                 SysUserRolePermissionMapper sysUserRolePermissionMapper) {
        return new DefaultUserDetailsService(grantedAuthorityDefaults().getRolePrefix(),
                sysOrganizationMapper,
                sysOrganizationUserMapper,
                sysOrganizationRoleMapper,
                sysUserMapper,
                sysUserRoleMapper,
                sysRoleMapper,
                sysRolePermissionMapper,
                sysPermissionMapper);
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

    @Bean
    @ConditionalOnClass({
            SysUserRolePermissionHelper.class,
            PasswordEncoder.class,
            SysUserMapper.class,
            SysRoleMapper.class,
            SysPermissionMapper.class,
            SysUserRoleMapper.class,
            SysRolePermissionMapper.class,
            SysUserRolePermissionMapper.class,
    })
    public SysUserRolePermissionHelper sysUserRolePermissionHelper(PasswordEncoder passwordEncoder,
                                                                   SysUserMapper sysUserMapper, SysRoleMapper sysRoleMapper, SysPermissionMapper sysPermissionMapper,
                                                                   SysUserRoleMapper sysUserRoleMapper, SysRolePermissionMapper sysRolePermissionMapper, SysUserRolePermissionMapper sysUserRolePermissionMapper) {
        return new SysUserRolePermissionHelper(passwordEncoder, sysUserMapper, sysRoleMapper, sysPermissionMapper, sysUserRoleMapper, sysRolePermissionMapper, sysUserRolePermissionMapper);
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
                        } else if (StringUtils.isNotBlank(hasRoleString)) {
                            // 有角色字符串
                            authorizedUrl.access(hasRoleString);
                        } else if (StringUtils.isNotBlank(hasAuthorityString)) {
                            // 有权限字符串
                            authorizedUrl.access(hasAuthorityString);
                        } else {
                            // "permitAll()"
                            authorizedUrl.access("permitAll()");
                        }
                    }
                }
            }
        };
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
