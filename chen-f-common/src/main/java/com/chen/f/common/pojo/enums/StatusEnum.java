package com.chen.f.common.pojo.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.commons.lang3.StringUtils;

/**
 * 状态枚举
 *
 * @author chen
 * @since 2018/11/3 15:41.
 */
public enum StatusEnum {
    //启用
    ENABLED("ENABLED", "启用"),
    //禁用
    DISABLE("DISABLED", "禁用"),

    ;

    /**
     * 状态(ENABLED:启用;DISABLED:禁用;)
     */
    @JsonValue
    @EnumValue
    public final String status;

    public final String description;

    StatusEnum(String status, String description) {
        this.status = status;
        this.description = description;
    }

    @JsonCreator
    public static StatusEnum of(String status) {
        StatusEnum[] values = StatusEnum.values();
        for (StatusEnum value : values) {
            if (StringUtils.equalsIgnoreCase(value.status, status)) {
                return value;
            }
        }
        return null;
    }

    public String getStatus() {
        return status;
    }
}
