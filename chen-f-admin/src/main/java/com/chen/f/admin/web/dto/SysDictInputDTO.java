package com.chen.f.admin.web.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.chen.f.common.pojo.enums.StatusEnum;
import com.chen.f.common.pojo.enums.SysDictTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author chen
 * @since 2019/3/24 23:27.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="SysDictDTO", description="系统字典DTO")
public class SysDictInputDTO {

    @ApiModelProperty(value = "主键ID")
    private String id;

    @ApiModelProperty(value = "字典标识")
    private String code;

    @ApiModelProperty(value = "字典KEY")
    private String key;

    @ApiModelProperty(value = "字典名称")
    private String name;

    @ApiModelProperty(value = "字典值")
    private String value;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "颜色值(冗余字段用户前端展示,需要保证前端可用)(success,processing,default,error,warning)(geekblue,blue,purple,success,red,volcano,orange,gold,lime,green,cyan) (#f50,#ff0) 预设和色值")
    private String color;

    @ApiModelProperty(value = "字典值类型(STRING:字符串;BYTE:数字byte;SHORT:数字short;INTEGER:数字integer;LONG:数字long;FLOAT:小数float;DOUBLE:小数double;BOOLEAN:布尔)")
    private SysDictTypeEnum type;

    @ApiModelProperty(value = "显示顺序")
    private Integer order;

    @ApiModelProperty(value = "状态(ENABLED:启用;DISABLE:禁用;)")
    private StatusEnum status;

    @ApiModelProperty(value = "乐观锁")
    @TableField("VERSION")
    private Integer version;

}
