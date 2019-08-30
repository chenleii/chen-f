package com.chen.f.common.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.chen.f.common.pojo.enums.StatusEnum;
import com.chen.f.common.pojo.enums.SysDictTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 系统字典表
 * </p>
 *
 * @author chen
 * @since 2019-02-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("SYS_DICT")
@ApiModel(value="SysDict对象", description="系统字典表")
public class SysDict extends Model<SysDict> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "ID", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "字典标识")
    @TableId(value = "CODE")
    private String code;

    @ApiModelProperty(value = "字典key")
    @TableField("`KEY`")
    private String key;

    @ApiModelProperty(value = "字典名称")
    @TableField("NAME")
    private String name;

    @ApiModelProperty(value = "字典值")
    @TableField("VALUE")
    private String value;

    @ApiModelProperty(value = "备注")
    @TableField("REMARK")
    private String remark;

    @ApiModelProperty(value = "颜色值(冗余字段用户前端展示,需要保证前端可用)(success,processing,default,error,warning)(geekblue,blue,purple,success,red,volcano,orange,gold,lime,green,cyan) (#f50,#ff0) 预设和色值")
    @TableField("COLOR")
    private String color;

    @ApiModelProperty(value = "字典值类型(STRING:字符串;BYTE:数字byte;SHORT:数字short;INTEGER:数字integer;LONG:数字long;FLOAT:小数float;DOUBLE:小数double;BOOLEAN:布尔)")
    @TableField("TYPE")
    private SysDictTypeEnum type;

    @ApiModelProperty(value = "显示顺序")
    @TableField("`ORDER`")
    private Integer order;

    @ApiModelProperty(value = "状态(ENABLED:启用;DISABLE:禁用;)")
    @TableField("STATUS")
    private StatusEnum status;

    @ApiModelProperty(value = "更新系统用户id('为初始化创建)")
    @TableField("UPDATE_SYS_USER_ID")
    private String updateSysUserId;

    @ApiModelProperty(value = "创建系统用户id('为初始化创建)")
    @TableField("CREATE_SYS_USER_ID")
    private String createSysUserId;

    @ApiModelProperty(value = "更新时间")
    @TableField("UPDATE_DATE_TIME")
    private LocalDateTime updateDateTime;

    @ApiModelProperty(value = "创建时间")
    @TableField("CREATE_DATE_TIME")
    private LocalDateTime createDateTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
