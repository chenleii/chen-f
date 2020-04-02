package com.chen.f.admin.web.dto;

import com.chen.f.common.pojo.enums.StatusEnum;
import com.chen.f.common.pojo.enums.SysOrganizationTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author chen
 * @since 2020/4/2 17:34.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="SysOrganizationDTO", description="系统组织DTO")
public class SysOrganizationInputDTO {

    @ApiModelProperty(value = "主键ID")
    private String id;

    @ApiModelProperty(value = "上级ID(引用本表ID字段,空白字符串为顶级)")
    private String parentId;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "全称")
    private String fullName;

    @ApiModelProperty(value = "类型")
    private SysOrganizationTypeEnum type;

    @ApiModelProperty(value = "备注")
    private String remark;

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

}
