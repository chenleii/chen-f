package com.chen.f.template;

import com.chen.f.core.configuration.redis.AutoConfigureTestRedis;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest(classes = ChenFTemplateApplication.class)
@AutoConfigureTestRedis
@ImportAutoConfiguration
public class ChenFTemplateApplicationTests {

    @Test
    public void contextLoads() {
    }

}

