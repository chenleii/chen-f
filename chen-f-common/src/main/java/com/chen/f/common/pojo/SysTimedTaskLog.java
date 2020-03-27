package com.chen.f.common.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.chen.f.common.pojo.enums.ExecutionStatusEnum;
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
 * 定时任务日志表
 * </p>
 *
 * @author chen
 * @since 2018-12-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("SYS_TIMED_TASK_LOG")
@ApiModel(value="SysTimedTaskLog对象", description="系统定时任务日志表")
public class SysTimedTaskLog extends Model<SysTimedTaskLog> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "任务标识")
    @TableField("CODE")
    private String code;

    @ApiModelProperty(value = "任务名称")
    @TableField("NAME")
    private String name;

    @ApiModelProperty(value = "CORN表达式")
    @TableField("CRON_EXPRESSION")
    private String cronExpression;

    @ApiModelProperty(value = "定时任务类型(SYSTEM:系统定时任务)")
    @TableField("TYPE")
    private SysTimedTaskTypeEnum type;

    @ApiModelProperty(value = "执行状态(NON:未执行;EXECUTING:执行中;SUCCESS:执行成功;FAILURE:执行失败;EXCEPTION:执行异常;REJECTION;执行拒绝;)")
    @TableField("EXECUTION_STATUS")
    private ExecutionStatusEnum executionStatus;

    @ApiModelProperty(value = "异常信息")
    @TableField("EXCEPTION_MESSAGE")
    private String exceptionMessage;

    @ApiModelProperty(value = "执行开始时间")
    @TableField("EXECUTION_START_DATE_TIME")
    private LocalDateTime executionStartDateTime;

    @ApiModelProperty(value = "执行结束时间")
    @TableField("EXECUTION_END_DATE_TIME")
    private LocalDateTime executionEndDateTime;

    @ApiModelProperty(value = "执行用时(毫秒)")
    @TableField("EXECUTION_TIME")
    private Long executionTime;

    @ApiModelProperty(value = "备注")
    @TableField("REMARK")
    private String remark;

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
