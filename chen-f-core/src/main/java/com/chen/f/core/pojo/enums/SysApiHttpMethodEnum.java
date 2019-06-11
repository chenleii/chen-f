package com.chen.f.core.pojo.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.commons.lang3.StringUtils;

/**
 * 系统API HTTP请求方法枚举
 *
 * @author chen
 * @since 2019/3/5 14:15.
 */
public enum SysApiHttpMethodEnum {
    //GET请求
    GET("GET", "GET请求"),
    //HEAD请求
    HEAD("HEAD", "HEAD请求"),
    //POST请求
    POST("POST", "POST请求"),
    //PUT请求
    PUT("PUT", "PUT请求"),
    //PATCH请求
    PATCH("PATCH", "PATCH请求"),
    //DELETE请求
    DELETE("DELETE", "DELETE请求"),
    //OPTIONS请求
    OPTIONS("OPTIONS", "OPTIONS请求"),
    //TRACE请求
    TRACE("TRACE", "TRACE请求"),
    //ANY请求
    ANY("ANY", "ANY请求"),

    ;

    /**
     * HTTP请求方法(GET;HEAD;POST;PUT;PATCH;DELETE;OPTIONS;TRACE;ANY;)
     */
    @JsonValue
    @EnumValue
    public final String httpMethod;

    public final String description;

    SysApiHttpMethodEnum(String httpMethod, String description) {
        this.httpMethod = httpMethod;
        this.description = description;
    }

    @JsonCreator
    public static SysApiHttpMethodEnum of(String httpMethod) {
        SysApiHttpMethodEnum[] values = SysApiHttpMethodEnum.values();
        for (SysApiHttpMethodEnum value : values) {
            if (StringUtils.equalsIgnoreCase(value.httpMethod, httpMethod)) {
                return value;
            }
        }
        return null;
    }

    public String getHttpMethod() {
        return httpMethod;
    }}
