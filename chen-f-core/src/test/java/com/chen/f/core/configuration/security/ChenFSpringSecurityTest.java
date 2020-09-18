package com.chen.f.core.configuration.security;

import com.chen.f.core.configuration.redis.EnableChenFEmbeddedRedis;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author chen
 * @since 2020/9/18 17:28
 */
@SpringBootTest
@EnableChenFSpringSecurity
@EnableChenFEmbeddedRedis
@ImportAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
class ChenFSpringSecurityTest {

    @MockBean
    private UserDetailsService userDetailsService;

    @BeforeEach
    void setUp() {
    }

    @Test
    public void testSecuritySessionHelper() {

    }
}