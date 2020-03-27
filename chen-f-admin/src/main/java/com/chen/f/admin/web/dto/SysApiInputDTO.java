package com.chen.f.admin.web.dto;

import com.chen.f.common.pojo.enums.StatusEnum;
import com.chen.f.common.pojo.enums.SysApiHttpMethodEnum;
import com.chen.f.common.pojo.enums.SysApiTypeEnum;
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
@ApiModel(value="SysApiDTO", description="系统接口DTO")
public class SysApiInputDTO {

    @ApiModelProperty(value = "主键ID")
    private String id;

    @ApiModelProperty(value = "接口名称")
    private String name;

    @ApiModelProperty(value = "接口URL")
    private String url;

    @ApiModelProperty(value = "HTTP请求方法(GET;HEAD;POST;PUT;PATCH;DELETE;OPTIONS;TRACE;ANY;)")
    private SysApiHttpMethodEnum httpMethod;

    @ApiModelProperty(value = "系统接口类型(SYSTEM:系统接口;)")
    private SysApiTypeEnum type;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "状态(ENABLED:启用;DISABLE:禁用;)")
    private StatusEnum status;

}
