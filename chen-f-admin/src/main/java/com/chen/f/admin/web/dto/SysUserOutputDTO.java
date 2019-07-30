package com.chen.f.admin.web.dto;

import com.chen.f.common.pojo.SysRole;
import com.chen.f.common.pojo.SysUser;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author chen
 * @since 2019/1/13 20:10.
 */
@Data
@Accessors(chain = true)
public class SysUserOutputDTO extends SysUser {

    @ApiModelProperty(value = "用户角色集合")
    private List<SysRole> sysRoleList;


}
