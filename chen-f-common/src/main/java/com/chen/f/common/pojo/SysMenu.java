package com.chen.f.common.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.chen.f.common.pojo.enums.StatusEnum;
import com.chen.f.common.pojo.enums.SysMenuTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 系统菜单表
 * </p>
 *
 * @author chen
 * @since 2020-04-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("`sys_menu`")
@ApiModel(value="SysMenu对象", description="系统菜单表")
public class SysMenu extends Model<SysMenu> {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "系统菜单ID")
      @TableId(value = "`ID`", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "父级的系统菜单ID(引用本表ID字段,空白字符串为顶级)")
    @TableField("`PARENT_ID`")
    private String parentId;

    @ApiModelProperty(value = "系统菜单名称")
    @TableField("`NAME`")
    private String name;
    
    @ApiModelProperty(value = "系统字典名称的国际化")
    @TableField("`NAME_I18N`")
    private String nameI18n;

    @ApiModelProperty(value = "系统菜单URL")
    @TableField("`URL`")
    private String url;

    @ApiModelProperty(value = "系统菜单图标")
    @TableField("`ICON`")
    private String icon;

    @ApiModelProperty(value = "系统菜单类型(GROUP:组;MENU:菜单;LINK:链接;EXTERNAL_LINK:外部链接;)")
    @TableField("`TYPE`")
    private SysMenuTypeEnum type;

    @ApiModelProperty(value = "系统菜单显示顺序")
    @TableField("`ORDER`")
    private Integer order;

    @ApiModelProperty(value = "系统菜单备注")
    @TableField("`REMARK`")
    private String remark;

    @ApiModelProperty(value = "系统菜单状态(ENABLED:启用;DISABLED:禁用;)")
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
    public Serializable pkVal() {
        return this.id;
    }

}
