package com.chen.f.admin.web.dto;

import com.chen.f.core.pojo.enums.StatusEnum;
import com.chen.f.core.pojo.enums.SysApiHttpMethodEnum;
import com.chen.f.core.pojo.enums.SysApiTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author chen
 * @since 2019/3/24 23:31.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="SysApiDTO", description="系统apiDTO")
public class SysApiInputDTO {

    @ApiModelProperty(value = "主键ID")
    private String id;

    @ApiModelProperty(value = "api名称")
    private String name;

    @ApiModelProperty(value = "API路径")
    private String url;

    @ApiModelProperty(value = "HTTP请求方法(GET;HEAD;POST;PUT;PATCH;DELETE;OPTIONS;TRACE;ANY;)")
    private SysApiHttpMethodEnum httpMethod;

    @ApiModelProperty(value = "系统API类型(SYSTEM:系统api;)")
    private SysApiTypeEnum type;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "状态(ENABLED:启用;DISABLE:禁用;)")
    private StatusEnum status;

}
