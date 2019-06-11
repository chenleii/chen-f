package com.chen.f.admin.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 验证码验证失败
 *
 * @author chen
 * Time:2018/4/12 18:14
 */
public class CaptchaException extends AuthenticationException {
    public CaptchaException(String msg) {
        super(msg);
    }

    public CaptchaException(String msg, Throwable t) {
        super(msg, t);
    }
}
