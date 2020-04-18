package com.chen.f.common.pojo.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.commons.lang3.StringUtils;

/**
 * 系统用户状态枚举
 *
 * @author chen
 * @date 2018/10/25 21:02.
 */
public enum SysUserStatusEnum {
    //启用
    ENABLED("ENABLED", "启用"),
    //锁定
    LOCKED("LOCKED", "锁定"),
    //过期
    EXPIRED("EXPIRED", "过期"),
    //禁用
    DISABLED("DISABLED", "禁用"),

    ;

    /**
     * 系统用户状态(ENABLED:正常;LOCKED:锁定;EXPIRED:过期;DISABLED:禁用;)
     */
    @JsonValue
    @EnumValue
    public final String status;

    public final String description;

    SysUserStatusEnum(String status, String description) {
        this.status = status;
        this.description = description;
    }

    @JsonCreator
    public static SysUserStatusEnum of(String status) {
        SysUserStatusEnum[] values = SysUserStatusEnum.values();
        for (SysUserStatusEnum value : values) {
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
