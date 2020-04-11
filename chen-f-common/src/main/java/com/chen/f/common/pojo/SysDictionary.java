package com.chen.f.common.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.chen.f.common.pojo.enums.StatusEnum;
import com.chen.f.common.pojo.enums.SysDictionaryTypeEnum;
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
@TableName("`sys_dictionary`")
@ApiModel(value="SysDictionary对象", description="系统字典表")
public class SysDictionary extends Model<SysDictionary> {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "系统字典ID")
      @TableId(value = "`ID`", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "系统字典编码")
    @TableField("`CODE`")
    private String code;

    @ApiModelProperty(value = "系统字典名称")
    @TableField("`NAME`")
    private String name;

    @ApiModelProperty(value = "系统字典类型(DB_TABLE_COLUMN:数据库表字段;OTHER:其他;)")
    @TableField("`TYPE`")
    private SysDictionaryTypeEnum type;

    @ApiModelProperty(value = "系统字典备注")
    @TableField("`REMARK`")
    private String remark;

    @ApiModelProperty(value = "系统字典状态(ENABLED:启用;DISABLE:禁用;)")
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
