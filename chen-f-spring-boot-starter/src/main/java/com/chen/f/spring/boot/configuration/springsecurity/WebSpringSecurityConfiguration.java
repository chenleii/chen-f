package com.chen.f.spring.boot.configuration.springsecurity;


import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import com.chen.f.spring.boot.configuration.springsecurity.filter.CustomLoginAuthenticationFilter;
import com.chen.f.spring.boot.configuration.springsecurity.provider.CustomLoginAuthenticationProvider;
import com.chen.f.spring.boot.configuration.springsecurity.webhandle.SpringSecurityHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
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
@Configuration
@ConditionalOnClass(WebSecurityConfigurerAdapter.class)
@ConditionalOnMissingBean(WebSecurityConfigurerAdapter.class)
@AutoConfigureAfter({MybatisPlusAutoConfiguration.class, SpringSecurityConfiguration.class,})
@ConditionalOnBean({UserDetailsService.class, SessionRegistry.class})
@EnableWebSecurity
public class WebSpringSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final SessionRegistry sessionRegistry;

    @Autowired
    public WebSpringSecurityConfiguration(UserDetailsService userDetailsService, SessionRegistry sessionRegistry) {
        this.userDetailsService = userDetailsService;
        this.sessionRegistry = sessionRegistry;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //禁用csrf
        http.csrf().disable();
        //配置cors
        http.cors().configurationSource(corsConfigurationSource());
        // 基于token，所以不需要session
        //http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // 禁用缓存
        //http.headers().cacheControl();

        http.headers().frameOptions().disable();

        http.authorizeRequests()
                .antMatchers("/test/t11").permitAll()
                .antMatchers("/swagger-ui.html", "/v2/api-docs", "/webjars/**", "/swagger-resources/**").permitAll()
                .antMatchers("/druid/**").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/db/**").access("hasRole('ADMIN') and hasRole('DBA')")
                //除了登陆url其它url都需要认证
                .antMatchers("/**").authenticated()
                .antMatchers("/login").not().authenticated()


                .and()

                //设置登陆页面相关(不使用默认登陆过滤器)
                .formLogin().authenticationDetailsSource(SpringSecurityHandle::buildDetailsHandle)
                .loginProcessingUrl("/login").successHandler(SpringSecurityHandle::authenticationSuccessHandle).permitAll()
                //.failureForwardUrl("/error").failureHandler(SpringSecurityHandle::authenticationFailureHandle).permitAll()
                .and()
                //设置登出相关
                .logout().logoutUrl("/logout").logoutSuccessHandler(SpringSecurityHandle::logoutSuccessHandle).permitAll();

        //添加自定义登录验证
        CustomLoginAuthenticationFilter customLoginAuthenticationFilter = new CustomLoginAuthenticationFilter();
        customLoginAuthenticationFilter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/login", "POST"));
        customLoginAuthenticationFilter.setAuthenticationManager(super.authenticationManager());
        customLoginAuthenticationFilter.setAuthenticationDetailsSource(SpringSecurityHandle::buildDetailsHandle);
        customLoginAuthenticationFilter.setAuthenticationSuccessHandler(SpringSecurityHandle::authenticationSuccessHandle);
        customLoginAuthenticationFilter.setAuthenticationFailureHandler(SpringSecurityHandle::authenticationFailureHandle);
        http.addFilterAt(customLoginAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        //配置未认证（未登录）,认证失败（用户没有权限访问）处理
        http.exceptionHandling()
                .authenticationEntryPoint(SpringSecurityHandle::authenticationEntryPointHandle)
                .accessDeniedHandler(SpringSecurityHandle::accessDeniedHandle);

        //配置记住我
        http.rememberMe().userDetailsService(userDetailsService).rememberMeServices(rememberMeServices())
                .alwaysRemember(true).tokenValiditySeconds(86400)
                .rememberMeParameter("rememberMe");

        http.sessionManagement().maximumSessions(2).maxSessionsPreventsLogin(true).sessionRegistry(this.sessionRegistry);
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
                //不擦除认证密码，擦除会导致TokenBasedRememberMeServices因为找不到Credentials再调用UserDetailsService而抛出UsernameNotFoundException
                .eraseCredentials(false);
    }

    @Bean
    public CustomLoginAuthenticationProvider loginAuthenticationProvider() {
        CustomLoginAuthenticationProvider chenLoginAuthenticationProvider = new CustomLoginAuthenticationProvider();
        chenLoginAuthenticationProvider.setUserDetailsService(userDetailsService);
        chenLoginAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        chenLoginAuthenticationProvider.setHideUserNotFoundExceptions(true);
        return chenLoginAuthenticationProvider;
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


    @Bean
    public ReloadableResourceBundleMessageSource configure() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath*:org/springframework/security/messages_zh_CN");
        return messageSource;
    }
}
