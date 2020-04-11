package com.chen.f.common.pojo.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 类型的类型枚举
 *
 * @author chen
 * @since 2019/1/11 16:08.
 */
public enum TypeTypeEnum {

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
     * 类型(STRING:字符串;BYTE:整数BYTE;SHORT:整数SHORT;INTEGER:整数INTEGER;LONG:整数LONG;BIG_INTEGER:大的整数;FLOAT:小数FLOAT;DOUBLE:小数DOUBLE;BIG_DECIMAL:大的小数;BOOLEAN:布尔)
     */
    @JsonValue
    @EnumValue
    public final String type;

    public final String description;

    TypeTypeEnum(String type, String description) {
        this.type = type;
        this.description = description;
    }

    @JsonCreator
    public static TypeTypeEnum of(String type) {
        TypeTypeEnum[] values = TypeTypeEnum.values();
        for (TypeTypeEnum value : values) {
            if (StringUtils.equalsIgnoreCase(value.type, type)) {
                return value;
            }
        }
        return null;
    }

    public String getType() {
        return type;
    }


    public Object to(String value) {
        if (this == TypeTypeEnum.STRING) {
            return value;
        } else if (this == TypeTypeEnum.BYTE) {
            return Byte.parseByte(value);
        } else if (this == TypeTypeEnum.SHORT) {
            return Short.parseShort(value);
        } else if (this == TypeTypeEnum.INTEGER) {
            return Integer.parseInt(value);
        } else if (this == TypeTypeEnum.LONG) {
            return Long.parseLong(value);
        } else if (this == TypeTypeEnum.BIG_INTEGER) {
            return new BigInteger(value);
        } else if (this == TypeTypeEnum.FLOAT) {
            return Float.parseFloat(value);
        } else if (this == TypeTypeEnum.DOUBLE) {
            return Double.parseDouble(value);
        } else if (this == TypeTypeEnum.BIG_DECIMAL) {
            return new BigDecimal(value);
        } else if (this == TypeTypeEnum.BOOLEAN) {
            return Boolean.parseBoolean(value);
        } else {
            return value;
        }
    }
}
