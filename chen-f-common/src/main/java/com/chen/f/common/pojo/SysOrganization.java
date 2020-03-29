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
 * 系统组织机构表
 * </p>
 *
 * @author chen
 * @since 2020-03-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_organization")
@ApiModel(value="SysOrganization对象", description="系统组织机构表")
public class SysOrganization extends Model<SysOrganization> {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键ID")
      @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "上级ID(引用本表ID字段,空白字符串为顶级)")
    @TableField("PARENT_ID")
    private String parentId;

    @ApiModelProperty(value = "名称")
    @TableField("NAME")
    private String name;

    @ApiModelProperty(value = "全称")
    @TableField("FULL_NAME")
    private String fullName;

    @ApiModelProperty(value = "类型")
    @TableField("TYPE")
    private SysOrganizationTypeEnum type;

    @ApiModelProperty(value = "备注")
    @TableField("REMARK")
    private String remark;

    @ApiModelProperty(value = "状态(ENABLED:启用;DISABLE:禁用;)")
    @TableField("STATUS")
    private StatusEnum status;

    @ApiModelProperty(value = "更新系统用户ID(空白字符串为初始化创建)")
    @TableField("UPDATE_SYS_USER_ID")
    private String updateSysUserId;

    @ApiModelProperty(value = "创建系统用户ID(空白字符串为初始化创建)")
    @TableField("CREATE_SYS_USER_ID")
    private String createSysUserId;

    @ApiModelProperty(value = "更新时间")
    @TableField("UPDATE_DATE_TIME")
    private LocalDateTime updateDateTime;

    @ApiModelProperty(value = "创建时间")
    @TableField("CREATE_DATE_TIME")
    private LocalDateTime createDateTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
