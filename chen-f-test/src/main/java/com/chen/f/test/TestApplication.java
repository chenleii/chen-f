package com.chen.f.test;

import com.chen.f.admin.configuration.EnableChenFAdmin;
import com.chen.f.common.configuration.EnableChenFCommon;
import com.chen.f.core.configuration.db.EnableChenFEmbeddedDataBase;
import com.chen.f.core.configuration.redis.EnableChenFEmbeddedRedis;
import org.apache.shardingsphere.shardingjdbc.spring.boot.SpringBootConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author chen
 * @since 2021/3/2 21:33.
 */
@EnableChenFEmbeddedRedis
@EnableChenFCommon
@EnableChenFAdmin
@SpringBootApplication
public class TestApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(TestApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }

}