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
 * @since 2019-02-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("SYS_MENU")
@ApiModel(value="SysMenu对象", description="系统菜单表")
public class SysMenu extends Model<SysMenu> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "父级的ID(引用本表ID字段,空白字符串为顶级菜单)")
    @TableField("PARENT_ID")
    private String parentId;

    @ApiModelProperty(value = "名称")
    @TableField("NAME")
    private String name;

    @ApiModelProperty(value = "URL")
    @TableField("URL")
    private String url;

    @ApiModelProperty(value = "图标")
    @TableField("ICON")
    private String icon;

    @ApiModelProperty(value = "类型(GROUP:组;MENU:菜单;LINK:链接;EXTERNAL_LINK:外部链接;)")
    @TableField("TYPE")
    private SysMenuTypeEnum type;

    @ApiModelProperty(value = "备注")
    @TableField("REMARK")
    private String remark;

    @ApiModelProperty(value = "显示顺序")
    @TableField("`ORDER`")
    private Integer order;

    @ApiModelProperty(value = "状态(ENABLED:启用;DISABLE:禁用;)")
    @TableField("STATUS")
    private StatusEnum status;

    @ApiModelProperty(value = "更新系统用户ID('为初始化创建)")
    @TableField("UPDATE_SYS_USER_ID")
    private String updateSysUserId;

    @ApiModelProperty(value = "创建系统用户ID('为初始化创建)")
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
