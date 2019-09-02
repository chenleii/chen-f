package com.chen.f.common.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.chen.f.common.pojo.enums.SysUserStatusEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
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
 * @since 2019-02-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("SYS_USER")
@ApiModel(value="SysUser对象", description="系统用户表")
public class SysUser extends Model<SysUser> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "ID", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "用户名")
    @TableField("USERNAME")
    private String username;

    @ApiModelProperty(value = "密码")
    @TableField("PASSWORD")
    private String password;

    @ApiModelProperty(value = "备注")
    @TableField("REMARK")
    private String remark;

    @ApiModelProperty(value = "用户状态(ENABLED:启用;LOCKED:锁定;EXPIRED:过期;DISABLE:禁用;)")
    @TableField("STATUS")
    private SysUserStatusEnum status;

    @ApiModelProperty(value = "级别(0:超级用户;1.数值越小级别越大,2.低级别用户不能修改比自己高级别的用户,3.新创建用户的级别默认比创建用户低一级别,4.高级别用户最高可以修改低级别用户为和自己同一级别)")
    @TableField("LEVEL")
    private Integer level;

    @ApiModelProperty(value = "最后登录时间")
    @TableField("LAST_LOGIN_DATE_TIME")
    private LocalDateTime lastLoginDateTime;

    @ApiModelProperty(value = "更新系统用户id('为初始化创建)")
    @TableField("UPDATE_SYS_USER_ID")
    private String updateSysUserId;

    @ApiModelProperty(value = "创建系统用户id('为初始化创建)")
    @TableField("CREATE_SYS_USER_ID")
    private String createSysUserId;

    @ApiModelProperty(value = "更新时间")
    @TableField("UPDATE_DATE_TIME")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateDateTime;

    @ApiModelProperty(value = "创建时间")
    @TableField("CREATE_DATE_TIME")
    private LocalDateTime createDateTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
