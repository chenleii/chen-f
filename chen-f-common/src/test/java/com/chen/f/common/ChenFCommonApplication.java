package com.chen.f.common;

import com.chen.f.common.configuration.EnableChenFCommon;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author chen
 * @since 2020/3/5 11:05.
 */
@EnableChenFCommon
@SpringBootApplication(scanBasePackages = "xxx")
public class ChenFCommonApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChenFCommonApplication.class, args);
    }
}
