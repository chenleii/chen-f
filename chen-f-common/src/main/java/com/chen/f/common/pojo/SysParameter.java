package com.chen.f.common.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.chen.f.common.pojo.enums.StatusEnum;
import com.chen.f.common.pojo.enums.SysParameterTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 系统参数表
 * </p>
 *
 * @author chen
 * @since 2019-02-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("SYS_PARAMETER")
@ApiModel(value="SysParameter对象", description="系统参数表")
public class SysParameter extends Model<SysParameter> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "参数标识")
    @TableId(value = "CODE")
    private String code;

    @ApiModelProperty(value = "参数名称")
    @TableField("NAME")
    private String name;

    @ApiModelProperty(value = "参数值")
    @TableField("VALUE")
    private String value;

    @ApiModelProperty(value = "参数类型(SYSTEM:系统关键参数)")
    @TableField("TYPE")
    private SysParameterTypeEnum type;

    @ApiModelProperty(value = "备注")
    @TableField("REMARK")
    private String remark;

    @ApiModelProperty(value = "状态(ENABLED:启用;DISABLE:禁用;)")
    @TableField("STATUS")
    private StatusEnum status;

    @ApiModelProperty(value = "更新系统用户ID(空白字符串为初始化创建)")
    @TableField("UPDATE_SYS_USER_ID")
    private String updateSysUserId;

    @ApiModelProperty(value = "创建系统用户ID(空白字符串为初始化创建)")
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
        return this.code;
    }

}
