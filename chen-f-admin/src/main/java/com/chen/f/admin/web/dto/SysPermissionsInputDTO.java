package com.chen.f.admin.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author chen
 * @since 2019/3/11 17:31.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "设置的系统权限", description = "设置的系统权限dto")
public class SysPermissionsInputDTO {
    @ApiModelProperty(value = "sysPermissionList")
    private List<String> sysPermissionList;
}
