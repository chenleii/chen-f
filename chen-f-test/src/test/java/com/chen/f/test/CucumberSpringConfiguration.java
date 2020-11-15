package com.chen.f.test;

import com.chen.f.core.configuration.redis.AutoConfigureTestRedis;
import com.chen.f.template.ChenFTemplateApplication;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chen
 * @since 2020/11/15 2:14.
 */
@CucumberContextConfiguration
@SpringBootTest(classes = ChenFTemplateApplication.class)
@DirtiesContext
@Transactional
@AutoConfigureMockMvc
@AutoConfigureTestRedis
@AutoConfigureTestDatabase
public class CucumberSpringConfiguration {
}
