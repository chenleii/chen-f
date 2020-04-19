package com.chen.f.admin.configuration.security;

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
import com.chen.f.common.pojo.SysApiRolePermission;
import com.chen.f.common.pojo.SysPermission;
import com.chen.f.common.pojo.SysRole;
import com.chen.f.core.configuration.security.HttpSecurityCustomizer;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author chen
 * @since 2019/1/14 17:00.
 */
@Configuration
public class EnableChenFAdminSpringSecurityConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(EnableChenFAdminSpringSecurityConfiguration.class);

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnClass({GrantedAuthorityDefaults.class, SysOrganizationMapper.class, SysOrganizationUserMapper.class, SysOrganizationRoleMapper.class,
            SysUserMapper.class, SysUserRoleMapper.class, SysRoleMapper.class, SysRolePermissionMapper.class, SysPermissionMapper.class,
    })
    public UserDetailsService userDetailsService(
            GrantedAuthorityDefaults grantedAuthorityDefaults,
            SysOrganizationMapper sysOrganizationMapper,
            SysOrganizationUserMapper sysOrganizationUserMapper,
            SysOrganizationRoleMapper sysOrganizationRoleMapper,
            SysUserMapper sysUserMapper,
            SysUserRoleMapper sysUserRoleMapper,
            SysRoleMapper sysRoleMapper,
            SysRolePermissionMapper sysRolePermissionMapper,
            SysPermissionMapper sysPermissionMapper) {
        return new DefaultUserDetailsService(grantedAuthorityDefaults.getRolePrefix(),
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
    public HttpSecurityCustomizer httpSecurityCustomizer(
            GrantedAuthorityDefaults grantedAuthorityDefaults, SysApiRolePermissionMapper sysApiRolePermissionMapper) {
        return (httpSecurity) -> {
            if (sysApiRolePermissionMapper != null) {
                List<SysApiRolePermission> sysApiRolePermissionList = sysApiRolePermissionMapper.selectSysApiRolePermissionList();
                if (CollectionUtils.isNotEmpty(sysApiRolePermissionList)) {
                    ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry expressionInterceptUrlRegistry = httpSecurity.authorizeRequests();
                    for (SysApiRolePermission sysApiRolePermission : sysApiRolePermissionList) {
                        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.AuthorizedUrl authorizedUrl = expressionInterceptUrlRegistry.antMatchers(
                                HttpMethod.resolve(sysApiRolePermission.getHttpMethod().httpMethod), sysApiRolePermission.getUrl());
                        //有角色字符串
                        String hasRoleString = null;
                        //有权限字符串
                        String hasAuthorityString = null;
                        if (CollectionUtils.isNotEmpty(sysApiRolePermission.getSysRoleList())) {
                            hasRoleString = sysApiRolePermission.getSysRoleList().stream()
                                    .map(SysRole::getCode)
                                    .map((s) -> "'" + grantedAuthorityDefaults.getRolePrefix() + s + "'")
                                    .collect(Collectors.joining(",", "hasRole(", ")"));
                        }
                        if (CollectionUtils.isNotEmpty(sysApiRolePermission.getSysPermissionList())) {
                            hasAuthorityString = sysApiRolePermission.getSysPermissionList().stream()
                                    .map(SysPermission::getCode)
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

}
