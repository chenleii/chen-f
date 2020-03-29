package com.chen.f.common.pojo.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.commons.lang3.StringUtils;

/**
 * 系统字典类型枚举
 *
 * @author chen
 * @since 2019/1/11 16:08.
 */
public enum SysDictTypeEnum {

    //字符串
    STRING("STRING", "字符串"),
    //数字
    BYTE("BYTE", "数字byte"),
    //数字
    SHORT("SHORT", "数字short"),
    //数字
    INTEGER("INTEGER", "数字integer"),
    //数字
    LONG("LONG", "数字long"),
    //小数
    FLOAT("FLOAT", "小数float"),
    //小数
    DOUBLE("DOUBLE", "小数double"),
    //布尔
    BOOLEAN("BOOLEAN", "布尔"),

    ;

    /**
     * 字典值类型(STRING:字符串;BYTE:数字byte;SHORT:数字short;INTEGER:数字integer;LONG:数字long;FLOAT:小数float;DOUBLE:小数double;BOOLEAN:布尔)
     */
    @JsonValue
    @EnumValue
    public final String type;

    public final String description;

    SysDictTypeEnum(String type, String description) {
        this.type = type;
        this.description = description;
    }

    @JsonCreator
    public static SysDictTypeEnum of(String type) {
        SysDictTypeEnum[] values = SysDictTypeEnum.values();
        for (SysDictTypeEnum value : values) {
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
