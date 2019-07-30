package com.chen.f.common.pojo.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.commons.lang3.StringUtils;

/**
 * @author chen
 * @since 2019/1/11 16:08.
 */
public enum SysDictColorEnum {

    //成功
    SUCCESS("success", "成功"),
    //processing
    PROCESSING("processing", "processing"),
    //default
    DEFAULT("default", "default"),
    //error
    ERROR("error", "error"),
    //warning
    WARNING("warning", "warning"),



    //geekblue
    GEEKBLUE("geekblue", "geekblue"),
    //blue
    BLUE("blue", "blue"),
    //purple
    PURPLE("purple", "purple"),
    //red
    RED("red", "red"),
    //orange
    ORANGE("orange", "orange"),
    //gold
    GOLD("gold", "gold"),
    //lime
    LIME("lime", "lime"),
    //green
    GREEN("green", "green"),
    //cyan
    CYAN("cyan", "cyan"),

    ;

    /**
     * 颜色值(冗余字段用户前端展示,需要保证前端可用)
     * (success,processing,default,error,warning)
     * (geekblue,blue,purple,success,red,volcano,orange,gold,lime,green,cyan)
     * (#f50,#ff0)
     * 预设和色值
     */
    @JsonValue
    @EnumValue
    public final String color;

    public final String description;

    SysDictColorEnum(String color, String description) {
        this.color = color;
        this.description = description;
    }

    @JsonCreator
    public static SysDictColorEnum of(String color) {
        SysDictColorEnum[] values = SysDictColorEnum.values();
        for (SysDictColorEnum value : values) {
            if (StringUtils.equalsIgnoreCase(value.color, color)) {
                return value;
            }
        }
        return null;
    }

    public String getColor() {
        return color;
    }}
