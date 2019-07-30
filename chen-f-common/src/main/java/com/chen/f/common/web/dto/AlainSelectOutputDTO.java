package com.chen.f.common.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author chen
 * @since 2019/1/13 14:34.
 */
@Data
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(value = "alain select", description = "")
public class AlainSelectOutputDTO {

    @ApiModelProperty(value = "名称")
    private String label;

    @ApiModelProperty(value = "值")
    private Object value;
}
