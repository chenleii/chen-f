package com.chen.f.common.pojo.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.commons.lang3.StringUtils;

/**
 * 系统定时任务类型枚举
 *
 * @author chen
 * @since 2018/11/7 16:33.
 */
public enum SysTimedTaskTypeEnum {
    //系统定时任务
    SYSTEM("SYSTEM", "系统定时任务"),
    ;

    /**
     * 定时任务类型(SYSTEM:系统定时任务)
     */
    @JsonValue
    @EnumValue
    public final String type;

    public final String description;

    SysTimedTaskTypeEnum(String type, String description) {
        this.type = type;
        this.description = description;
    }

    @JsonCreator
    public static SysTimedTaskTypeEnum of(String status) {
        SysTimedTaskTypeEnum[] values = SysTimedTaskTypeEnum.values();
        for (SysTimedTaskTypeEnum value : values) {
            if (StringUtils.equalsIgnoreCase(value.type, status)) {
                return value;
            }
        }
        return null;
    }

    public String getType() {
        return type;
    }
}
