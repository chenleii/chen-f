package com.chen.f.common.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.chen.f.common.pojo.enums.StatusEnum;
import com.chen.f.common.pojo.enums.TypeTypeEnum;
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
 * @since 2020-04-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("`sys_dict`")
@ApiModel(value = "SysDict对象", description = "系统字典表")
public class SysDict extends Model<SysDict> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "系统字典ID")
    @TableId(value = "`ID`", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "系统字典标识")
    @TableField("`CODE`")
    private String code;

    @ApiModelProperty(value = "系统字典名称")
    @TableField("`NAME`")
    private String name;

    @ApiModelProperty(value = "系统字典KEY")
    @TableField("`KEY`")
    private String key;

    @ApiModelProperty(value = "系统字典值")
    @TableField("`VALUE`")
    private String value;

    @ApiModelProperty(value = "系统字典备注")
    @TableField("`REMARK`")
    private String remark;

    @ApiModelProperty(value = "系统字典颜色值(冗余字段用于前端展示,例如#ffffff)")
    @TableField("`COLOR`")
    private String color;

    @ApiModelProperty(value = "系统字典值类型(STRING:字符串;BYTE:数字byte;SHORT:数字short;INTEGER:数字integer;LONG:数字long;FLOAT:小数float;DOUBLE:小数double;BOOLEAN:布尔;)")
    @TableField("`TYPE`")
    private TypeTypeEnum type;

    @ApiModelProperty(value = "系统字典显示顺序")
    @TableField("`ORDER`")
    private Integer order;

    @ApiModelProperty(value = "系统字典状态(ENABLED:启用;DISABLED:禁用;)")
    @TableField("`STATUS`")
    private StatusEnum status;

    @ApiModelProperty(value = "更新的系统用户ID(空白字符串为初始化创建)")
    @TableField("`UPDATED_SYS_USER_ID`")
    private String updatedSysUserId;

    @ApiModelProperty(value = "创建的系统用户ID(空白字符串为初始化创建)")
    @TableField("`CREATED_SYS_USER_ID`")
    private String createdSysUserId;

    @ApiModelProperty(value = "更新的日期时间")
    @TableField("`UPDATED_DATE_TIME`")
    private LocalDateTime updatedDateTime;

    @ApiModelProperty(value = "创建的日期时间")
    @TableField("`CREATED_DATE_TIME`")
    private LocalDateTime createdDateTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
