package com.chen.f.common.pojo.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.commons.lang3.StringUtils;

/**
 * 系统接口类型枚举
 *
 * @author chen
 * @since 2019/3/5 14:19.
 */
public enum SysApiTypeEnum {
    //系统接口
    SYSTEM("SYSTEM", "系统接口"),

    ;

    /**
     * 系统API类型(SYSTEM:系统接口;)
     */
    @JsonValue
    @EnumValue
    public final String type;

    public final String description;

    SysApiTypeEnum(String type, String description) {
        this.type = type;
        this.description = description;
    }

    @JsonCreator
    public static SysApiTypeEnum of(String type) {
        SysApiTypeEnum[] values = SysApiTypeEnum.values();
        for (SysApiTypeEnum value : values) {
            if (StringUtils.equalsIgnoreCase(value.type, type)) {
                return value;
            }
        }
        return null;
    }

    public String getType() {
        return type;
    }
}
