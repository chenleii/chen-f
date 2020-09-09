package com.chen.f.template;

import com.chen.f.admin.configuration.EnableChenFAdmin;
import com.chen.f.common.configuration.EnableChenFCommon;
import com.chen.f.core.configuration.db.EnableChenFEmbeddedDataBase;
import com.chen.f.core.configuration.redis.EnableChenFEmbeddedRedis;
import org.apache.shardingsphere.shardingjdbc.spring.boot.SpringBootConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@EnableChenFEmbeddedDataBase
@EnableChenFEmbeddedRedis
@EnableChenFCommon
@EnableChenFAdmin
@SpringBootApplication(exclude = {SpringBootConfiguration.class})
public class ChenFTemplateApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ChenFTemplateApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(ChenFTemplateApplication.class, args);
    }

}

