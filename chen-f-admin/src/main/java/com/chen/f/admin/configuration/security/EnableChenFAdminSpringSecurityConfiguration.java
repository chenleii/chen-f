package com.chen.f.admin.configuration.security;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.chen.f.admin.configuration.security.service.DefaultUserDetailsService;
import com.chen.f.common.mapper.SysApiMapper;
import com.chen.f.common.mapper.SysApiRolePermissionMapper;
import com.chen.f.common.pojo.SysApi;
import com.chen.f.common.pojo.SysApiRolePermission;
import com.chen.f.common.pojo.SysPermission;
import com.chen.f.common.pojo.SysRole;
import com.chen.f.common.pojo.enums.StatusEnum;
import com.chen.f.common.pojo.enums.SysApiTypeEnum;
import com.chen.f.common.service.ISysApiService;
import com.chen.f.common.service.ISysMenuService;
import com.chen.f.common.service.ISysOrganizationService;
import com.chen.f.common.service.ISysPermissionService;
import com.chen.f.common.service.ISysRoleService;
import com.chen.f.common.service.ISysUserService;
import com.chen.f.core.configuration.security.ChenFHttpSecurityCustomizer;
import com.chen.f.core.configuration.security.filter.LoginAuthenticationFilter;
import com.chen.f.core.configuration.security.handle.SpringSecurityHandle;
import lombok.SneakyThrows;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.sql.init.dependency.DependsOnDatabaseInitialization;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.domain.AfterDomainEventPublication;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

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
    @ConditionalOnClass({GrantedAuthorityDefaults.class, ISysOrganizationService.class,
            ISysUserService.class, ISysRoleService.class, ISysPermissionService.class, ISysMenuService.class, ISysApiService.class
    })
    public UserDetailsService userDetailsService(
            GrantedAuthorityDefaults grantedAuthorityDefaults,
            ISysOrganizationService sysOrganizationService,
            ISysUserService sysUserService,
            ISysRoleService sysRoleService,
            ISysPermissionService sysPermissionService,

            ISysMenuService sysMenuService,
            ISysApiService sysApiService) {
        return new DefaultUserDetailsService(grantedAuthorityDefaults.getRolePrefix(),
                sysOrganizationService,
                sysUserService,
                sysRoleService,
                sysPermissionService,
                sysMenuService,
                sysApiService);
    }


    @Bean
    @DependsOnDatabaseInitialization
    public ChenFHttpSecurityCustomizer chenFAdminHttpSecurityCustomizer(GrantedAuthorityDefaults grantedAuthorityDefaults,
                                                                        SysApiMapper sysApiMapper, SysApiRolePermissionMapper sysApiRolePermissionMapper) {
        
        return new ChenFHttpSecurityCustomizer() {

            @Override
            public void customize(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
                final List<SysApi> loginSysApiList = sysApiMapper.selectList(Wrappers.<SysApi>lambdaQuery()
                        .eq(SysApi::getStatus, StatusEnum.ENABLED)
                        .eq(SysApi::getType, SysApiTypeEnum.LOGIN));
                final List<SysApi> logoutSysApiList = sysApiMapper.selectList(Wrappers.<SysApi>lambdaQuery()
                        .eq(SysApi::getStatus, StatusEnum.ENABLED)
                        .eq(SysApi::getType, SysApiTypeEnum.LOGOUT));

                final List<SysApiRolePermission> sysApiRolePermissionList = sysApiRolePermissionMapper.selectSysApiRolePermissionList();

                String loginUrl = "/login";
                String loginMethod = HttpMethod.POST.name();
                String logoutUrl = "/logout";
                String logoutMethod = HttpMethod.POST.name();

                if (CollectionUtils.isNotEmpty(loginSysApiList)) {
                    final SysApi loginSysApi = loginSysApiList.get(0);
                    loginUrl = loginSysApi.getUrl();
                    loginMethod = loginSysApi.getHttpMethod().httpMethod;
                }
                if (CollectionUtils.isNotEmpty(logoutSysApiList)) {
                    final SysApi logoutSysApi = logoutSysApiList.get(0);
                    logoutUrl = logoutSysApi.getUrl();
                    logoutMethod = logoutSysApi.getHttpMethod().httpMethod;
                }


                //使用自定义登陆过滤器，不需要该配置
                //配置登陆
                //http.formLogin()
                //        .authenticationDetailsSource(SpringSecurityHandle::buildDetailsHandle)
                //        .loginProcessingUrl(loginUrl)
                //        .successHandler(SpringSecurityHandle::authenticationSuccessHandle)
                //        .failureHandler(SpringSecurityHandle::authenticationFailureHandle)
                //        .permitAll();

                //添加自定义登录验证
                LoginAuthenticationFilter loginAuthenticationFilter = new LoginAuthenticationFilter();
                loginAuthenticationFilter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(loginUrl, loginMethod));

                loginAuthenticationFilter.setAuthenticationManager(authenticationManager);
                loginAuthenticationFilter.setAuthenticationDetailsSource(SpringSecurityHandle::buildDetailsHandle);
                loginAuthenticationFilter.setAuthenticationSuccessHandler(SpringSecurityHandle::authenticationSuccessHandle);
                loginAuthenticationFilter.setAuthenticationFailureHandler(SpringSecurityHandle::authenticationFailureHandle);
                http.addFilterAt(loginAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

                //配置登出
                http.logout()
                        .logoutRequestMatcher(new AntPathRequestMatcher(logoutUrl, logoutMethod))
                        .logoutSuccessHandler(SpringSecurityHandle::logoutSuccessHandle)
                        .permitAll();

                //配置接口权限
                http.authorizeRequests()
                        .antMatchers("/swagger-ui.html", "/doc.html",
                                "/v2/api-docs", "/webjars/**", "/v2/api-docs-ext", "/swagger-resources/**").permitAll()
                        .antMatchers("/druid/**").permitAll()

                        .antMatchers(HttpMethod.resolve(loginMethod), loginUrl).not().authenticated()
                        .antMatchers(HttpMethod.resolve(logoutMethod), logoutUrl).not().authenticated()

                        //其它url都需要认证
                        .antMatchers("/**").authenticated();


                if (CollectionUtils.isNotEmpty(sysApiRolePermissionList)) {
                    ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry expressionInterceptUrlRegistry = http.authorizeRequests();
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
