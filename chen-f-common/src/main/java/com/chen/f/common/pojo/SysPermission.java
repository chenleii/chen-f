package com.chen.f.common.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.chen.f.common.pojo.enums.StatusEnum;
import com.chen.f.common.pojo.enums.SysPermissionTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 系统权限表
 * </p>
 *
 * @author chen
 * @since 2020-04-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("`sys_permission`")
@ApiModel(value="SysPermission对象", description="系统权限表")
public class SysPermission extends Model<SysPermission> {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "系统权限ID")
      @TableId(value = "`ID`", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "系统权限标识")
    @TableField("`CODE`")
    private String code;

    @ApiModelProperty(value = "系统权限名称")
    @TableField("`NAME`")
    private String name;

    @ApiModelProperty(value = "系统权限类型(MENU:菜单;API:接口;ELEMENT:页面元素可见性;OPERATION:操作;)")
    @TableField("`TYPE`")
    private SysPermissionTypeEnum type;

    @ApiModelProperty(value = "系统权限备注")
    @TableField("`REMARK`")
    private String remark;

    @ApiModelProperty(value = "系统权限状态(ENABLED:启用;DISABLED:禁用;)")
    @TableField("`STATUS`")
    private StatusEnum status;

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
    protected Serializable pkVal() {
        return this.id;
    }

}
