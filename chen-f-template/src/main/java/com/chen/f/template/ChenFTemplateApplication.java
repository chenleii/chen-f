package com.chen.f.template;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.chen.f.spring.boot.configuration.EnableChenFAdmin;
import com.chen.f.spring.boot.configuration.EnableChenFCommon;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.PropertySource;


@PropertySource("classpath:jdbc.properties")
@EnableChenFCommon
@EnableChenFAdmin
@SpringBootApplication(exclude = {DruidDataSourceAutoConfigure.class,})
public class ChenFTemplateApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ChenFTemplateApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(ChenFTemplateApplication.class, args);
    }

}

