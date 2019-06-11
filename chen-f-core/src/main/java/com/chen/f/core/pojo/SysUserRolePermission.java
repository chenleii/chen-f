package com.chen.f.core.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 系统用户角色权限
 *
 * @author chen
 * @date 2018/10/25 20:34.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "SysUserRolePermission对象", description = "系统用户角色权限")
public class SysUserRolePermission extends SysUser implements Serializable {

    @ApiModelProperty(value = "系统角色权限列表")
    private List<SysRolePermission> sysRolePermissionList;

    @Data
    @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    @ApiModel(value = "SysRolePermission对象", description = "系统角色权限")
    public static class SysRolePermission extends SysRole implements Serializable {

        @ApiModelProperty(value = "系统权限列表")
        private List<SysPermission> sysPermissionList;

    }
}
