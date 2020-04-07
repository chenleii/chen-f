package com.chen.f.common.pojo.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.commons.lang3.StringUtils;

/**
 * 值类型枚举
 *
 * @author chen
 * @since 2019/1/11 16:08.
 */
public enum ValueTypeEnum {

    //字符串
    STRING("STRING", "字符串"),
    //整数
    BYTE("BYTE", "整数BYTE"),
    //整数
    SHORT("SHORT", "整数SHORT"),
    //整数
    INTEGER("INTEGER", "整数INTEGER"),
    //整数
    LONG("LONG", "整数LONG"),
    //大的整数
    BIG_INTEGER("BIG_INTEGER", "大的整数LONG"),
    //小数
    FLOAT("FLOAT", "小数FLOAT"),
    //小数
    DOUBLE("DOUBLE", "小数DOUBLE"),
    //大的小数
    BIG_DECIMAL("BIG_DECIMAL", "大的小数BIG_DECIMAL"),
    //布尔
    BOOLEAN("BOOLEAN", "布尔"),

    ;

    /**
     * 值类型(STRING:字符串;BYTE:整数BYTE;SHORT:整数SHORT;INTEGER:整数INTEGER;LONG:整数LONG;BIG_INTEGER:大的整数;FLOAT:小数FLOAT;DOUBLE:小数DOUBLE;BIG_DECIMAL:大的小数;BOOLEAN:布尔)
     */
    @JsonValue
    @EnumValue
    public final String type;

    public final String description;

    ValueTypeEnum(String type, String description) {
        this.type = type;
        this.description = description;
    }

    @JsonCreator
    public static ValueTypeEnum of(String type) {
        ValueTypeEnum[] values = ValueTypeEnum.values();
        for (ValueTypeEnum value : values) {
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
