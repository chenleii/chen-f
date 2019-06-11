package com.chen.f.core.api;

import com.chen.f.core.api.exception.ApiException;
import com.chen.f.core.api.response.error.ErrorResponse;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Map;

/**
 * api断言
 *
 * @author chen
 * @since 2018/11/4 22:42.
 */
public class ApiAssert {
    protected static final Logger logger = LoggerFactory.getLogger(ApiAssert.class);

    /**
     * 断言是true,不为true抛出api异常
     *
     * @param b             b
     * @param errorResponse 错误响应
     */
    public static void isTrue(boolean b, ErrorResponse errorResponse) {
        if (!b) {
            logger.warn(errorResponse.getErrorMsg());
            throw new ApiException(errorResponse);
        }
    }

    public static void isNotTrue(boolean b, ErrorResponse errorResponse) {
        isTrue(!b, errorResponse);
    }

    public static void isNull(Object object, ErrorResponse errorResponse) {
        isTrue(object == null, errorResponse);
    }

    public static void isNotNull(Object object, ErrorResponse errorResponse) {
        isTrue(object != null, errorResponse);
    }

    public static void isEmpty(String string, ErrorResponse errorResponse) {
        isTrue(StringUtils.isEmpty(string), errorResponse);
    }

    public static void isNotEmpty(String string, ErrorResponse errorResponse) {
        isTrue(StringUtils.isNotEmpty(string), errorResponse);
    }

    public static void isEmpty(Map<?, ?> map, ErrorResponse errorResponse) {
        isTrue(MapUtils.isEmpty(map), errorResponse);
    }

    public static void isNotEmpty(Map<?, ?> map, ErrorResponse errorResponse) {
        isTrue(MapUtils.isNotEmpty(map), errorResponse);
    }

    public static void isEmpty(Collection collection, ErrorResponse errorResponse) {
        isTrue(CollectionUtils.isEmpty(collection), errorResponse);
    }

    public static void isNotEmpty(Collection collection, ErrorResponse errorResponse) {
        isTrue(CollectionUtils.isNotEmpty(collection), errorResponse);
    }

    public static void isBlank(String string, ErrorResponse errorResponse) {
        isTrue(StringUtils.isBlank(string), errorResponse);
    }

    public static void isNotBlank(String string, ErrorResponse errorResponse) {
        isTrue(StringUtils.isNotBlank(string), errorResponse);
    }

    public static void isAssignable(Class<?> superType, Class<?> subType, ErrorResponse errorResponse) {
        isNotNull(superType, errorResponse);
        isNotNull(subType, errorResponse);
        isTrue(superType.isAssignableFrom(subType), errorResponse);
    }

    public static void isNotAssignable(Class<?> superType, Class<?> subType, ErrorResponse errorResponse) {
        isNotNull(superType, errorResponse);
        isNotNull(subType, errorResponse);
        isTrue(!superType.isAssignableFrom(subType), errorResponse);
    }

    public static void isInstanceOf(Class<?> type, Object obj, ErrorResponse errorResponse) {
        isNotNull(type, errorResponse);
        isNotNull(obj, errorResponse);
        isTrue(type.isInstance(obj), errorResponse);
    }

    public static void isNotInstanceOf(Class<?> type, Object obj, ErrorResponse errorResponse) {
        isNotNull(type, errorResponse);
        isNotNull(obj, errorResponse);
        isTrue(!type.isInstance(obj), errorResponse);
    }


    public static void isNotEqualToZero(int i, ErrorResponse errorResponse) {
        isTrue(i != 0, errorResponse);
    }

    public static void isEqualToZero(int i, ErrorResponse errorResponse) {
        isTrue(i == 0, errorResponse);
    }

    public static void isNotEqualToOne(int i, ErrorResponse errorResponse) {
        isTrue(i != 1, errorResponse);
    }

    public static void isEqualToOne(int i, ErrorResponse errorResponse) {
        isTrue(i == 1, errorResponse);
    }

    public static void isGreaterThatZero(int i, ErrorResponse errorResponse) {
        isTrue(i > 0, errorResponse);
    }

    public static void isNotEqualTo(int i1, int i2, ErrorResponse errorResponse) {
        isTrue(i1 != i2, errorResponse);
    }

    public static void isEqualTo(int i1, int i2, ErrorResponse errorResponse) {
        isTrue(i1 == i2, errorResponse);
    }

    public static void isGreaterThan(int i1, int i2, ErrorResponse errorResponse) {
        isTrue(i1 > i2, errorResponse);
    }

    public static void isGreaterThanOrEqualTo(int i1, int i2, ErrorResponse errorResponse) {
        isTrue(i1 >= i2, errorResponse);
    }

    public static void isLessThan(int i1, int i2, ErrorResponse errorResponse) {
        isTrue(i1 < i2, errorResponse);
    }

    public static void isLessThanOrEqualTo(int i1, int i2, ErrorResponse errorResponse) {
        isTrue(i1 <= i2, errorResponse);
    }

}
