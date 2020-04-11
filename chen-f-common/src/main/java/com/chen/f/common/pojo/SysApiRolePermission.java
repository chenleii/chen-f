package com.chen.f.common.pojo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 系统接口角色权限关联
 * </p>
 *
 * @author chen
 * @since 2019/7/25 17:19.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "SysApiRolePermission对象", description = "系统接口角色权限")
public class SysApiRolePermission extends SysApi implements Serializable {

    @ApiModelProperty(value = "系统角色列表")
    private List<SysRole> sysRoleList;

    @ApiModelProperty(value = "系统权限列表")
    private List<SysPermission> sysPermissionList;

}
