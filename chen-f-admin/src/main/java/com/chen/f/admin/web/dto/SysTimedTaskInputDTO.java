package com.chen.f.admin.web.dto;

import com.chen.f.common.pojo.enums.StatusEnum;
import com.chen.f.common.pojo.enums.SysTimedTaskTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author chen
 * @since 2019/3/17 0:49.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="SysTimedTaskInputDTO", description="系统定时任务DTO")
public class SysTimedTaskInputDTO {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    private String id;

    @ApiModelProperty(value = "任务标识")
    private String code;

    @ApiModelProperty(value = "任务名称")
    private String name;

    @ApiModelProperty(value = "类名")
    private String className;

    @ApiModelProperty(value = "CORN表达式")
    private String cronExpression;

    @ApiModelProperty(value = "任务数据(JSON格式)")
    private String data;

    @ApiModelProperty(value = "定时任务类型(SYSTEM:系统定时任务)")
    private SysTimedTaskTypeEnum type;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "状态(ENABLED:启用;DISABLE:禁用;)")
    private StatusEnum status;


}
