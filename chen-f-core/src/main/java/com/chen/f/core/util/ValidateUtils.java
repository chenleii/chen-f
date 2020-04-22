package com.chen.f.core.util;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * hibernate-validate 验证工具类
 *
 * @author chen
 * @since 2017/11/13 10:19.
 */
public class ValidateUtils {

//    private static final ValidatorFactory VALIDATOR_FACTORY = Validation.buildDefaultValidatorFactory();
//    private static final Validator VALIDATOR = VALIDATOR_FACTORY.getValidator();

    /**
     * 验证对象
     *
     * @param object 验证的对象
     * @param groups 组  对象实现的接口
     * @param <T>    验证的对象
     * @return 验证的错误列表
     */
    public static <T> Set<ConstraintViolation<T>> validate(T object, Class<?>... groups) {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<T>> validate = validator.validate(object, groups);
        validatorFactory.close();
        return validate;
    }

    /**
     * 验证对象
     *
     * @param object 验证的对象
     * @param groups 组  对象实现的接口
     * @param <T>    验证的对象
     * @return 验证的错误的一条信息
     */
    public static <T> String validateToMessage(T object, Class<?>... groups) {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<T>> validate = validator.validate(object, groups);
        validatorFactory.close();
        return validate.iterator().next().getMessage();
    }
}
