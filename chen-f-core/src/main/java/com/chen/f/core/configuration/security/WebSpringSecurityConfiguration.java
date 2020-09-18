package com.chen.f.core.configuration.security;


import com.chen.f.core.configuration.security.handle.SpringSecurityHandle;
import com.chen.f.core.configuration.security.provider.LoginAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.session.security.web.authentication.SpringSessionRememberMeServices;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * web spring security配置
 *
 * @author chen
 * @date 2018/10/22 12:04.
 */
@Order(0)
@Configuration
@EnableWebSecurity
@ConditionalOnClass(WebSecurityConfigurerAdapter.class)
@AutoConfigureAfter({SpringSecurityConfiguration.class,})
public class WebSpringSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    @Autowired
    public WebSpringSecurityConfiguration(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //禁用csrf
        http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
        //配置cors
        http.cors().configurationSource(corsConfigurationSource());
        // 禁用缓存
        //http.headers().cacheControl();

        
        //配置异常处理
        // 未认证（未登录）,认证失败（用户没有权限访问）处理
        http.exceptionHandling()
                .authenticationEntryPoint(SpringSecurityHandle::authenticationEntryPointHandle)
                .accessDeniedHandler(SpringSecurityHandle::accessDeniedHandle);

        //配置记住我登陆
        //http.rememberMe()
        //        .userDetailsService(userDetailsService)
        //        .rememberMeServices(rememberMeServices())
        //        .alwaysRemember(true).tokenValiditySeconds(86400)
        //        .rememberMeParameter("rememberMe");

        //配置session管理
        //http.sessionManagement()
        //        .maximumSessions(2)
        //        .maxSessionsPreventsLogin(true)
        //        .sessionRegistry(this.sessionRegistry)
        //        .invalidSessionStrategy(SpringSecurityHandle::onInvalidSessionDetected)
        //        .expiredSessionStrategy(SpringSecurityHandle::onExpiredSessionDetected);

        //配置自定义权限
        //http.authorizeRequests().withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
        //    @Override
        //    public <O extends FilterSecurityInterceptor> O postProcess(O object) {
        //        //object.setSecurityMetadataSource(null);
        //        return object;
        //    }
        //});

        //配置角色继承
        //http.authorizeRequests().withObjectPostProcessor(new ObjectPostProcessor<DefaultWebSecurityExpressionHandler>() {
        //    @Override
        //    public <O extends DefaultWebSecurityExpressionHandler> O postProcess(O object) {
        //        //角色继承
        //        //object.setRoleHierarchy(null);
        //        return object;
        //    }
        //});
        
    }

    /**
     * cors配置
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(Boolean.TRUE);
        configuration.setMaxAge(Long.MAX_VALUE);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public RememberMeServices rememberMeServices() {
        SpringSessionRememberMeServices rememberMeServices = new SpringSessionRememberMeServices();
        // optionally customize
        rememberMeServices.setAlwaysRemember(true);
        return rememberMeServices;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) {
        authenticationManagerBuilder
                .authenticationProvider(loginAuthenticationProvider())
                .eraseCredentials(true);
    }

    @Bean
    public LoginAuthenticationProvider loginAuthenticationProvider() {
        LoginAuthenticationProvider loginAuthenticationProvider = new LoginAuthenticationProvider();
        loginAuthenticationProvider.setUserDetailsService(userDetailsService);
        loginAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        loginAuthenticationProvider.setHideUserNotFoundExceptions(true);
        return loginAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers("/**/*.css", "/**/*.js", "/**/*.ico", "/**/*.png", "/**/*.jpg", "/**/*.xlsx", "/**/*.gif");
    }
}
