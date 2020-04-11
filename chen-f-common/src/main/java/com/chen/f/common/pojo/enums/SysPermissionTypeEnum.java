package com.chen.f.common.pojo.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.commons.lang3.StringUtils;

/**
 * 系统权限类型枚举
 *
 * @author chen
 * @since 2019/9/19 11:37.
 */
public enum SysPermissionTypeEnum {

    //菜单
    MENU("MENU", "菜单"),
    //接口
    API("API", "接口"),
    //页面元素可见性
    ELEMENT("ELEMENT", "页面元素可见性"),
    //操作
    OPERATION("OPERATION", "操作"),
    ;

    /**
     * 系统权限类型(MENU:菜单;API:接口;ELEMENT:页面元素可见性;OPERATION:操作;)
     */
    @JsonValue
    @EnumValue
    public final String type;

    public final String description;

    SysPermissionTypeEnum(String type, String description) {
        this.type = type;
        this.description = description;
    }

    @JsonCreator
    public static SysPermissionTypeEnum of(String type) {
        SysPermissionTypeEnum[] values = SysPermissionTypeEnum.values();
        for (SysPermissionTypeEnum value : values) {
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
