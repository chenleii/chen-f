package com.chen.f.spring.boot.exception;

import org.springframework.core.NestedRuntimeException;

/**
 * @author chen
 * @since 2019/5/28 12:39.
 */
public class ChenFConfigurationException extends NestedRuntimeException {
    public ChenFConfigurationException(String msg) {
        super(msg);
    }

    public ChenFConfigurationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
