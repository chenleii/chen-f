package com.chen.f.core.configuration.security;

import com.chen.f.core.configuration.redis.EnableChenFEmbeddedRedis;
import com.chen.f.core.configuration.security.filter.LoginAuthenticationFilter;
import com.chen.f.core.configuration.security.handle.SpringSecurityHandle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.servlet.http.Cookie;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author chen
 * @since 2020/9/18 17:28
 */
@SpringBootTest
@EnableChenFSpringSecurity
@EnableChenFEmbeddedRedis
@AutoConfigureMockMvc
@ImportAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@Import({TestSpringSecurityConfiguration.class})
class ChenFSpringSecurityTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @MockBean
    private UserDetailsService userDetailsService;


    @BeforeEach
    void setUp() {
        final User user = new User("chen", passwordEncoder.encode("chen"), Collections.emptyList());
        BDDMockito.given(userDetailsService.loadUserByUsername(anyString())).willReturn(user);
    }

    @Test
    public void testSecuritySessionHelper() throws Exception {
        final String username = "chen";
        final String password = "chen";
        
        MvcResult mvcResult = mvc.perform(
                post("/login")
                        .param("username", username)
                        .param("password", password)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf())
        )
                .andExpect(status().isOk())
                .andReturn();
        
        final Cookie[] cookies = mvcResult.getResponse().getCookies();
        
        mvc.perform(
                get("/index")
                        .accept(MediaType.APPLICATION_JSON)
                        .cookie(cookies)
                        .with(csrf())
        )
                .andExpect(status().isNotFound())
                .andReturn();


        assertThat(SecuritySessionHelper.isNotExpired(username)).isTrue();
        assertThat(SecuritySessionHelper.isLogin(username)).isTrue();
        
        SecuritySessionHelper.expire(username);
        assertThat(SecuritySessionHelper.isExpired(username)).isTrue();
        assertThat(SecuritySessionHelper.isLogin(username)).isTrue();
        
        SecuritySessionHelper.logout(username);
        assertThat(SecuritySessionHelper.isExpired(username)).isTrue();
        assertThat(SecuritySessionHelper.isNotLogin(username)).isTrue();
        
        mvc.perform(
                get("/index")
                        .accept(MediaType.APPLICATION_JSON)
                        .cookie(cookies)
                        .with(csrf())
        )
                .andExpect(status().isUnauthorized())
                .andReturn();

    }
}


@TestConfiguration
class TestSpringSecurityConfiguration {


    @Bean
    public ChenFHttpSecurityCustomizer chenFCoreHttpSecurityCustomizer() {

        return new ChenFHttpSecurityCustomizer() {
       
            @Override
            public void customize(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
                String loginUrl = "/login";
                String loginMethod = HttpMethod.POST.name();
                String logoutUrl = "/logout";
                String logoutMethod = HttpMethod.POST.name();

                //添加自定义登录验证
                LoginAuthenticationFilter loginAuthenticationFilter = new LoginAuthenticationFilter();
                loginAuthenticationFilter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(loginUrl, loginMethod));
                loginAuthenticationFilter.setAuthenticationManager(authenticationManager);
                loginAuthenticationFilter.setAuthenticationDetailsSource(SpringSecurityHandle::buildDetailsHandle);
                loginAuthenticationFilter.setAuthenticationSuccessHandler(SpringSecurityHandle::authenticationSuccessHandle);
                loginAuthenticationFilter.setAuthenticationFailureHandler(SpringSecurityHandle::authenticationFailureHandle);
                http.addFilterAt(loginAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);


                http.authorizeRequests()
                        .anyRequest().authenticated();

            }
        };


    }


}
