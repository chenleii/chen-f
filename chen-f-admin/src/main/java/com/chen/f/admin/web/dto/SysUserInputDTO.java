package com.chen.f.admin.web.dto;

import com.chen.f.common.pojo.enums.SysUserStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author chen
 * @since 2019/1/12 21:08.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "SysUser对象", description = "系统用户dto")
public class SysUserInputDTO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "用户状态(ENABLED:正常;LOCKED:锁定;EXPIRED:过期;DISABLE:禁用;)")
    private SysUserStatusEnum status;

    @ApiModelProperty(value = "级别(0:超级用户;1.数值越小级别越大,2.低级别用户不能修改比自己高级别的用户,3.新创建用户的级别默认比创建用户低一级别,4.高级别用户最高可以修改低级别用户为和自己同一级别)")
    private Integer level;

    @ApiModelProperty(value = "乐观锁")
    private Integer version;

}
