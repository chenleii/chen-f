package com.chen.f.admin.web.dto;

import com.chen.f.common.pojo.enums.StatusEnum;
import com.chen.f.common.pojo.enums.SysMenuTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author chen
 * @since 2019/5/31 11:41.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="SysMenu对象", description="系统菜单表")
public class SysMenuTreeOutputDTO {

    @ApiModelProperty(value = "主键ID")
    private String id;

    @ApiModelProperty(value = "父级的ID(引用本表ID字段,空白字符串为顶级菜单)")
    private String parentId;

    @ApiModelProperty(value = "菜单名称")
    private String name;

    @ApiModelProperty(value = "菜单URL")
    private String url;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "类型(GROUP:组;MENU:菜单;LINK:链接;EXTERNAL_LINK:外部链接;)")
    private SysMenuTypeEnum type;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "显示顺序")
    private Integer order;

    @ApiModelProperty(value = "状态(ENABLED:启用;DISABLE:禁用;)")
    private StatusEnum status;

    @ApiModelProperty(value = "更新系统用户ID(空白字符串为初始化创建)")
    private String updateSysUserId;

    @ApiModelProperty(value = "创建系统用户ID(空白字符串为初始化创建)")
    private String createSysUserId;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateDateTime;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createDateTime;

    @ApiModelProperty(value = "子菜单")
    private List<SysMenuTreeOutputDTO> submenuList;


}
