package com.chen.f.common.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.chen.f.common.pojo.enums.StatusEnum;
import com.chen.f.common.pojo.enums.SysOrganizationTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 系统组织表
 * </p>
 *
 * @author chen
 * @since 2020-04-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("`sys_organization`")
@ApiModel(value="SysOrganization对象", description="系统组织表")
public class SysOrganization extends Model<SysOrganization> {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "系统组织ID")
      @TableId(value = "`ID`", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "父级的系统组织ID(引用本表ID字段,空白字符串为顶级)")
    @TableField("`PARENT_ID`")
    private String parentId;

    @ApiModelProperty(value = "系统组织名称")
    @TableField("`NAME`")
    private String name;

    @ApiModelProperty(value = "系统组织全称")
    @TableField("`FULL_NAME`")
    private String fullName;

    @ApiModelProperty(value = "系统组织类型(ORDINARY:普通组织;COMPANY:公司;DEPARTMENT:部门;)")
    @TableField("`TYPE`")
    private SysOrganizationTypeEnum type;

    @ApiModelProperty(value = "系统组织备注")
    @TableField("`REMARK`")
    private String remark;

    @ApiModelProperty(value = "系统组织状态(ENABLED:启用;DISABLE:禁用;)")
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
