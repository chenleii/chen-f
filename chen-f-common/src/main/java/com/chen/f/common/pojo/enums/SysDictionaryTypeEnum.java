package com.chen.f.common.pojo.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.commons.lang3.StringUtils;

/**
 * 系统字典类型枚举
 *
 * @author chen
 * @since 2018/11/7 16:33.
 */
public enum SysDictionaryTypeEnum {
    //数据库表字段
    DB_TABLE_COLUMN("DB_TABLE_COLUMN", "数据库表字段"),
    //其他
    OTHER("OTHER", "其他"),

    ;

    /**
     * 系统字典类型(DB_TABLE_COLUMN:数据库表字段;OTHER:其他;)
     */
    @JsonValue
    @EnumValue
    public final String type;

    public final String description;

    SysDictionaryTypeEnum(String type, String description) {
        this.type = type;
        this.description = description;
    }

    @JsonCreator
    public static SysDictionaryTypeEnum of(String type) {
        SysDictionaryTypeEnum[] values = SysDictionaryTypeEnum.values();
        for (SysDictionaryTypeEnum value : values) {
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
