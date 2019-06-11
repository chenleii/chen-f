package com.chen.f.admin.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author chen
 * @since 2019/3/8 18:20.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "设置的系统API", description = "设置的系统APIdto")
public class SysApisInputDTO {

    @ApiModelProperty(value = "sysApiList")
    private List<String> sysApiList;
}
