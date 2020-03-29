package com.chen.f.common.pojo.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.commons.lang3.StringUtils;

/**
 * 系统组织类型枚举
 *
 * @author chen
 * @since 2020/3/29 15:59.
 */
public enum SysOrganizationTypeEnum {
    //普通组织
    ORDINARY_ORGANIZATION("ORDINARY", "普通组织"),
    //公司
    COMPANY("COMPANY", "公司"),
    //部门
    DEPARTMENT("DEPARTMENT", "部门"),

    ;

    /**
     * 系统组织类型(ORDINARY:普通组织;COMPANY:公司;DEPARTMENT:部门;)
     */
    @JsonValue
    @EnumValue
    public final String type;

    public final String description;

    SysOrganizationTypeEnum(String type, String description) {
        this.type = type;
        this.description = description;
    }

    @JsonCreator
    public static SysOrganizationTypeEnum of(String type) {
        SysOrganizationTypeEnum[] values = SysOrganizationTypeEnum.values();
        for (SysOrganizationTypeEnum value : values) {
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
