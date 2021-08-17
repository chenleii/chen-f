package com.chen.f.common.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.chen.f.common.pojo.enums.SysTimedTaskExecutionStatusEnum;
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
 * 系统定时任务日志表
 * </p>
 *
 * @author chen
 * @since 2020-04-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("`sys_timed_task_log`")
@ApiModel(value="SysTimedTaskLog对象", description="系统定时任务日志表")
public class SysTimedTaskLog extends Model<SysTimedTaskLog> {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "系统定时任务日志ID")
      @TableId(value = "`ID`", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "系统定时任务ID")
    @TableField("`SYS_TIMED_TASK_ID`")
    private String sysTimedTaskId;

    @ApiModelProperty(value = "系统定时任务标识(冗余)")
    @TableField("`CODE`")
    private String code;

    @ApiModelProperty(value = "系统定时任务名称(冗余)")
    @TableField("`NAME`")
    private String name;

    @ApiModelProperty(value = "系统定时任务类名(冗余)")
    @TableField("`CLASS_NAME`")
    private String className;

    @ApiModelProperty(value = "系统定时任务CORN表达式(冗余)")
    @TableField("`CRON_EXPRESSION`")
    private String cronExpression;

    @ApiModelProperty(value = "系统定时任务数据(JSON格式)(冗余)")
    @TableField("`DATA`")
    private String data;

    @ApiModelProperty(value = "系统定时任务类型(SYSTEM:系统定时任务;)(冗余)")
    @TableField("`TYPE`")
    private SysTimedTaskTypeEnum type;

    @ApiModelProperty(value = "系统定时任务执行状态(NON:未执行;EXECUTING:执行中;SUCCESS:执行成功;FAILURE:执行失败;EXCEPTION:执行异常;REJECTION;执行拒绝;)")
    @TableField("`EXECUTION_STATUS`")
    private SysTimedTaskExecutionStatusEnum executionStatus;

    @ApiModelProperty(value = "系统定时任务异常信息")
    @TableField("`EXCEPTION_MESSAGE`")
    private String exceptionMessage;

    @ApiModelProperty(value = "系统定时任务执行开始时间")
    @TableField("`EXECUTION_START_DATE_TIME`")
    private LocalDateTime executionStartDateTime;

    @ApiModelProperty(value = "系统定时任务执行结束时间")
    @TableField("`EXECUTION_END_DATE_TIME`")
    private LocalDateTime executionEndDateTime;

    @ApiModelProperty(value = "系统定时任务执行用时(单位毫秒)")
    @TableField("`EXECUTION_TIME`")
    private Long executionTime;

    @ApiModelProperty(value = "系统定时任务日志备注")
    @TableField("`REMARK`")
    private String remark;

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
