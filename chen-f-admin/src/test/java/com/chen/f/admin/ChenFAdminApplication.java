package com.chen.f.admin;

import com.chen.f.admin.configuration.EnableChenFAdmin;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author chen
 * @since 2020/3/5 11:05.
 */
@EnableChenFAdmin
@SpringBootApplication
public class ChenFAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChenFAdminApplication.class, args);
    }
}
