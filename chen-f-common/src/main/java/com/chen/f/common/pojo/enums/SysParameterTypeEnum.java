package com.chen.f.common.pojo.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.commons.lang3.StringUtils;

/**
 * @author chen
 * @since 2018/11/7 16:33.
 */
public enum SysParameterTypeEnum {
    //系统关键参数
    SYSTEM("SYSTEM", "系统定时任务"),
    ;

    /**
     * 参数类型(SYSTEM:系统关键参数)
     */
    @JsonValue
    @EnumValue
    public final String type;

    public final String description;

    SysParameterTypeEnum(String type, String description) {
        this.type = type;
        this.description = description;
    }

    @JsonCreator
    public static SysParameterTypeEnum of(String type) {
        SysParameterTypeEnum[] values = SysParameterTypeEnum.values();
        for (SysParameterTypeEnum value : values) {
            if (StringUtils.equalsIgnoreCase(value.type, type)) {
                return value;
            }
        }
        return null;
    }

    public String getType() {
        return type;
    }}
