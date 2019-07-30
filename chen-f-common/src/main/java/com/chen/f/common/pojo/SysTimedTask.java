package com.chen.f.common.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.chen.f.common.pojo.enums.StatusEnum;
import com.chen.f.common.pojo.enums.SysTimedTaskTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 系统定时任务表
 * </p>
 *
 * @author chen
 * @since 2019-02-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("SYS_TIMED_TASK")
@ApiModel(value="SysTimedTask对象", description="系统定时任务表")
public class SysTimedTask extends Model<SysTimedTask> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "任务标识")
    @TableId(value = "CODE", type = IdType.ID_WORKER_STR)
    private String code;

    @ApiModelProperty(value = "任务名称")
    @TableField("NAME")
    private String name;

    @ApiModelProperty(value = "类名")
    @TableField("CLASS_NAME")
    private String className;

    @ApiModelProperty(value = "corn表达式")
    @TableField("CRON_EXPRESSION")
    private String cronExpression;

    @ApiModelProperty(value = "任务数据(json格式)")
    @TableField("DATA")
    private String data;

    @ApiModelProperty(value = "定时任务类型(SYSTEM:系统定时任务)")
    @TableField("TYPE")
    private SysTimedTaskTypeEnum type;

    @ApiModelProperty(value = "备注")
    @TableField("REMARK")
    private String remark;

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
        return this.code;
    }

}
