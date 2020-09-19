package com.chen.f.core.configuration.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

/**
 * @author chen
 * @since 2020/9/19 1:50.
 */
@FunctionalInterface
public interface ChenFHttpSecurityCustomizer {


    /**
     * Customize the given httpSecurity.
     */
    void customize(HttpSecurity httpSecurity, AuthenticationManager authenticationManager) throws Exception;
}
