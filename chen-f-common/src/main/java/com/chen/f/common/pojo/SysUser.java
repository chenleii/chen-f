package com.chen.f.common.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.chen.f.common.pojo.enums.SysUserStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 系统用户表
 * </p>
 *
 * @author chen
 * @since 2020-04-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("`sys_user`")
@ApiModel(value="SysUser对象", description="系统用户表")
public class SysUser extends Model<SysUser> {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "系统用户ID")
      @TableId(value = "`ID`", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "系统用户用户名")
    @TableField("`USERNAME`")
    private String username;

    @ApiModelProperty(value = "系统用户密码")
    @TableField("`PASSWORD`")
    private String password;

    @ApiModelProperty(value = "系统用户级别(0为超级用户,数值越小级别越大)")
    @TableField("`LEVEL`")
    private Integer level;

    @ApiModelProperty(value = "系统用户最后登录日期时间")
    @TableField("`LAST_LOGIN_DATE_TIME`")
    private LocalDateTime lastLoginDateTime;

    @ApiModelProperty(value = "系统用户备注")
    @TableField("`REMARK`")
    private String remark;

    @ApiModelProperty(value = "系统用户状态(ENABLED:启用;LOCKED:锁定;EXPIRED:过期;DISABLE:禁用;)")
    @TableField("`STATUS`")
    private SysUserStatusEnum status;

    @ApiModelProperty(value = "更新的系统用户ID(空白字符串为初始化创建)")
    @TableField("`UPDATED_SYS_USER_ID`")
    private String updatedSysUserId;

    @ApiModelProperty(value = "创建的系统用户ID(空白字符串为初始化创建)")
    @TableField("`CREATED_SYS_USER_ID`")
    private String createdSysUserId;

    @ApiModelProperty(value = "更新的日期时间")
    @TableField("`UPDATED_DATE_TIME`")
    private LocalDateTime updatedDateTime;

    @ApiModelProperty(value = "创建的日期时间")
    @TableField("`CREATED_DATE_TIME`")
    private LocalDateTime createdDateTime;


    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
