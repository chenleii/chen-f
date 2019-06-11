package com.chen.f.core.pojo.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.commons.lang3.StringUtils;

/**
 * @author chen
 * @since 2018/11/10 0:29.
 */
public enum ExecutionStatusEnum {

    //成功
    SUCCESS("SUCCESS", "成功"),
    //异常
    EXCEPTION("EXCEPTION", "异常"),
    //失败
    FAILURE("FAILURE", "失败"),
    //未执行
    NON_EXECUTION("NON_EXECUTION", "未执行"),

    ;

    /**
     * 执行状态(SUCCESS:执行成功;EXCEPTION:执行异常;FAILURE:执行失败;NON_EXECUTION;未执行(被否决);)
     */
    @JsonValue
    @EnumValue
    public final String status;

    public final String description;

    ExecutionStatusEnum(String status, String description) {
        this.status = status;
        this.description = description;
    }

    @JsonCreator
    public static ExecutionStatusEnum of(String status) {
        ExecutionStatusEnum[] values = ExecutionStatusEnum.values();
        for (ExecutionStatusEnum value : values) {
            if (StringUtils.equalsIgnoreCase(value.status, status)) {
                return value;
            }
        }
        return null;
    }

    public String getStatus() {
        return status;
    }}
