package com.chen.f.template;

import org.apache.shardingsphere.shardingjdbc.spring.boot.SpringBootConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.redis.AutoConfigureDataRedis;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureDataRedis
@ImportAutoConfiguration(exclude = {SpringBootConfiguration.class})
public class ChenFTemplateApplicationTests {

    @Test
    public void contextLoads() {
    }

}

