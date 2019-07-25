package com.chen.f.spring.boot.configuration.springsecurity.token;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * 身份认证内容
 *
 * @author chen
 * @since 2018/1/16 9:28.
 */
public class CustomLoginAuthenticationToken extends UsernamePasswordAuthenticationToken {


    public CustomLoginAuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public CustomLoginAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
}
