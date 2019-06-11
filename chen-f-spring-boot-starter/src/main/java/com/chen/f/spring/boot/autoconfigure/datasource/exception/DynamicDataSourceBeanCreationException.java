package com.chen.f.spring.boot.autoconfigure.datasource.exception;

import org.springframework.beans.factory.BeanCreationException;

/**
 * @author chen
 * @since 2018/12/19 16:36.
 */
public class DynamicDataSourceBeanCreationException extends BeanCreationException {

    public DynamicDataSourceBeanCreationException(String message) {
        super(message);
    }

    public DynamicDataSourceBeanCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
