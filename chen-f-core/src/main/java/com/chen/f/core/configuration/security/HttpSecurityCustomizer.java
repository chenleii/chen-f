package com.chen.f.core.configuration.security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

/**
 * @author chen
 * @since 2019/7/29 16:42.
 */
@FunctionalInterface
public interface HttpSecurityCustomizer {
    /**
     * Customize the {@link HttpSecurity}.
     *
     * @param httpSecurity httpSecurity
     */
    void customize(HttpSecurity httpSecurity) throws Exception;
}
