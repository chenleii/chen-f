package com.chen.f.core.configuration.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

/**
 * 验证帮助类
 *
 * @author chen
 * @since 2019/9/18 16:32.
 */
public class ValidatorHelper {
    private static final Logger logger = LoggerFactory.getLogger(ValidatorHelper.class);

    private static Validator validator;

    public ValidatorHelper(Validator validator) {
        ValidatorHelper.validator = validator;
    }

    public static <T> Set<ConstraintViolation<T>> validate(T object, Class<?>... groups) {
        return validator.validate(object, groups);
    }

    public static <T> Set<ConstraintViolation<T>> validateProperty(T object,
                                                                   String propertyName,
                                                                   Class<?>... groups) {
        return validator.validateProperty(object, propertyName, groups);
    }

    public static <T> Set<ConstraintViolation<T>> validateValue(Class<T> beanType,
                                                                String propertyName,
                                                                Object value,
                                                                Class<?>... groups) {
        return validator.validateValue(beanType, propertyName, value, groups);
    }
}
