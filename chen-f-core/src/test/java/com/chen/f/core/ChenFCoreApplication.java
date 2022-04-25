package com.chen.f.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author chen
 * @since 2020/9/18 17:32
 */
@SpringBootApplication(scanBasePackages = "xxx")
public class ChenFCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChenFCoreApplication.class, args);
    }
}
