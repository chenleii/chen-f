package com.chen.f.common.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author chen
 * @since 2019/1/12 22:28.
 */
@Data
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(value = "alain tag", description = "")
public class AlainTagOutputDTO {

    @ApiModelProperty(value = "文本")
    private Object text;

    @ApiModelProperty(value = "颜色")
    private String color;


}
