package com.chen.f.core.pojo.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.commons.lang3.StringUtils;

/**
 * @author chen
 * @since 2018/12/2 15:17.
 */
public enum SysMenuTypeEnum {

    //组
    GROUP("GROUP", "组"),
    //菜单
    MENU("MENU", "菜单"),
    //链接
    LINK("LINK", "链接"),
    //外部链接
    EXTERNAL_LINK("EXTERNAL_LINK", "外部链接"),
    ;

    /**
     * 类型(GROUP:组;MENU:菜单;LINK:链接;EXTERNAL_LINK:外部链接;)
     */
    @JsonValue
    @EnumValue
    public final String status;

    public final String description;

    SysMenuTypeEnum(String status, String description) {
        this.status = status;
        this.description = description;
    }

    @JsonCreator
    public static SysMenuTypeEnum of(String status) {
        SysMenuTypeEnum[] values = SysMenuTypeEnum.values();
        for (SysMenuTypeEnum value : values) {
            if (StringUtils.equalsIgnoreCase(value.status, status)) {
                return value;
            }
        }
        return null;
    }

    public String getStatus() {
        return status;
    }}
