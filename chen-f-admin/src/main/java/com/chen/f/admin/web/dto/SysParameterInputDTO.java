package com.chen.f.admin.web.dto;

import com.chen.f.common.pojo.enums.StatusEnum;
import com.chen.f.common.pojo.enums.SysParameterTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author chen
 * @since 2019/3/24 23:29.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="SysParameter对象", description="系统参数表")
public class SysParameterInputDTO {

    @ApiModelProperty(value = "主键ID")
    private String id;

    @ApiModelProperty(value = "参数标识")
    private String code;

    @ApiModelProperty(value = "参数名称")
    private String name;

    @ApiModelProperty(value = "参数值")
    private String value;

    @ApiModelProperty(value = "参数类型(SYSTEM:系统关键参数)")
    private SysParameterTypeEnum type;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "状态(ENABLED:启用;DISABLE:禁用;)")
    private StatusEnum status;

    @ApiModelProperty(value = "乐观锁")
    private Integer version;

}
