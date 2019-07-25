package com.chen.f.spring.boot.configuration.springsecurity.exceptionhandle;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * spring-security异常处理
 *
 * @author chen
 * @since 2019/1/18 15:09.
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SpringSecurityExceptionHandle {

    /**
     * spring-security的异常(忽略交给spring-security框架处理)
     *
     * @param exception 异常
     * @throws Exception 同样的异常(忽略而已
     */
    @ExceptionHandler(value = {AccessDeniedException.class, AuthenticationException.class})
    public void springSecurityException(Exception exception) throws Exception {
        //此异常交给spring-security处理
        throw exception;
    }
}
