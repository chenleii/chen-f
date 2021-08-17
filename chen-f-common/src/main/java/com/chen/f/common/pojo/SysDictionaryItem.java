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
 * 系统字典项目表
 * </p>
 *
 * @author chen
 * @since 2020-04-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("`sys_dictionary_item`")
@ApiModel(value="SysDictionaryItem对象", description="系统字典项目表")
public class SysDictionaryItem extends Model<SysDictionaryItem> {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "系统字典项目ID")
      @TableId(value = "`ID`", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "系统字典ID")
    @TableField("`SYS_DICTIONARY_ID`")
    private String sysDictionaryId;

    @ApiModelProperty(value = "系统字典编码(冗余)")
    @TableField("`CODE`")
    private String code;

    @ApiModelProperty(value = "系统字典名称(冗余)")
    @TableField("`NAME`")
    private String name;

    @ApiModelProperty(value = "系统字典项目KEY")
    @TableField("`KEY`")
    private String key;

    @ApiModelProperty(value = "系统字典项目值")
    @TableField("`VALUE`")
    private String value;

    @ApiModelProperty(value = "系统字典项目值的国际化")
    @TableField("`VALUE_I18N`")
    private String valueI18n;

    @ApiModelProperty(value = "系统字典项目KEY类型(STRING:字符串;BYTE:整数BYTE;SHORT:整数SHORT;INTEGER:整数INTEGER;LONG:整数LONG;BIG_INTEGER:大的整数;FLOAT:小数FLOAT;DOUBLE:小数DOUBLE;BIG_DECIMAL:大的小数;BOOLEAN:布尔;)")
    @TableField("`KEY_TYPE`")
    private TypeTypeEnum keyType;

    @ApiModelProperty(value = "系统字典项目值类型(STRING:字符串;BYTE:整数BYTE;SHORT:整数SHORT;INTEGER:整数INTEGER;LONG:整数LONG;BIG_INTEGER:大的整数;FLOAT:小数FLOAT;DOUBLE:小数DOUBLE;BIG_DECIMAL:大的小数;BOOLEAN:布尔;)")
    @TableField("`VALUE_TYPE`")
    private TypeTypeEnum valueType;

    @ApiModelProperty(value = "系统字典项目颜色值(用于展示,例如#ffffff)")
    @TableField("`COLOR`")
    private String color;

    @ApiModelProperty(value = "系统字典项目显示顺序")
    @TableField("`ORDER`")
    private Integer order;

    @ApiModelProperty(value = "系统字典项目备注")
    @TableField("`REMARK`")
    private String remark;

    @ApiModelProperty(value = "系统字典项目状态(ENABLED:启用;DISABLED:禁用;)")
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
    public Serializable pkVal() {
        return this.id;
    }

}
