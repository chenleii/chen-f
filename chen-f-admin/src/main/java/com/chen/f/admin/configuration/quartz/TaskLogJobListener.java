package com.chen.f.admin.configuration.quartz;

import com.chen.f.common.mapper.SysTimedTaskLogMapper;
import com.chen.f.common.mapper.SysTimedTaskMapper;
import com.chen.f.common.pojo.SysTimedTask;
import com.chen.f.common.pojo.SysTimedTaskLog;
import com.chen.f.common.pojo.enums.SysTimedTaskExecutionStatusEnum;
import org.apache.commons.lang3.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.listeners.JobListenerSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author chen
 * @since 2018/11/10 9:46.
 */
public class TaskLogJobListener extends JobListenerSupport {
    private static final Logger logger = LoggerFactory.getLogger(TaskLogJobListener.class);

    private final SysTimedTaskMapper sysTimedTaskMapper;
    private final SysTimedTaskLogMapper sysTimedTaskLogMapper;

    public TaskLogJobListener(SysTimedTaskLogMapper sysTimedTaskLogMapper, SysTimedTaskMapper sysTimedTaskMapper) {
        this.sysTimedTaskLogMapper = sysTimedTaskLogMapper;
        this.sysTimedTaskMapper = sysTimedTaskMapper;
    }

    @Override
    public String getName() {
        return "task_log";
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        //任务开始时间
        Date startDate = context.getFireTime();
        LocalDateTime startLocalDateTime = LocalDateTime.ofInstant(startDate.toInstant(), ZoneId.systemDefault());
        LocalDateTime endLocalDateTime = LocalDateTime.now();
        //任务执行时间
        long jobRunTime = context.getJobRunTime();
        //获取任务编码
        JobKey jobKey = context.getJobDetail().getKey();
        SysTimedTask sysTimedTask = sysTimedTaskMapper.selectById(jobKey.getName());
        if (sysTimedTask == null) {
            logger.warn("定时任务表[{}]记录不存在", jobKey.getName());
            return;
        }
        //保存任务执行日志
        SysTimedTaskLog sysTimedTaskLog = new SysTimedTaskLog();
        sysTimedTaskLog.setCode(sysTimedTask.getCode());
        sysTimedTaskLog.setName(sysTimedTask.getName());
        sysTimedTaskLog.setCronExpression(sysTimedTask.getCronExpression());
        sysTimedTaskLog.setType(sysTimedTask.getType());
        sysTimedTaskLog.setExecutionStatus(SysTimedTaskExecutionStatusEnum.REJECTION);
        sysTimedTaskLog.setRemark("任务被否决");
        sysTimedTaskLog.setExecutionStartDateTime(startLocalDateTime);
        sysTimedTaskLog.setExecutionEndDateTime(endLocalDateTime);
        sysTimedTaskLog.setExecutionTime(jobRunTime);
        sysTimedTaskLog.setUpdatedDateTime(LocalDateTime.now());
        sysTimedTaskLog.setCreatedDateTime(sysTimedTaskLog.getUpdatedDateTime());
        sysTimedTaskLogMapper.insert(sysTimedTaskLog);
        logger.debug("定时任务日志监听:任务编码:[{}] 任务名称:[{}] 任务执行时间(毫秒数):[{}]", sysTimedTask.getCode(), sysTimedTask.getName(), jobRunTime);

    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        //任务开始时间
        Date startDate = context.getFireTime();
        LocalDateTime startLocalDateTime = LocalDateTime.ofInstant(startDate.toInstant(), ZoneId.systemDefault());
        LocalDateTime endLocalDateTime = LocalDateTime.now();
        //任务执行时间
        long jobRunTime = context.getJobRunTime();
        //获取任务编码
        JobKey jobKey = context.getJobDetail().getKey();
        SysTimedTask sysTimedTask = sysTimedTaskMapper.selectById(jobKey.getName());
        if (sysTimedTask == null) {
            logger.warn("定时任务表[{}]记录不存在", jobKey.getName());
            return;
        }
        //保存任务执行日志
        SysTimedTaskLog sysTimedTaskLog = new SysTimedTaskLog();
        sysTimedTaskLog.setCode(sysTimedTask.getCode());
        sysTimedTaskLog.setName(sysTimedTask.getName());
        sysTimedTaskLog.setCronExpression(sysTimedTask.getCronExpression());
        sysTimedTaskLog.setType(sysTimedTask.getType());
        if (jobException == null) {
            sysTimedTaskLog.setExecutionStatus(SysTimedTaskExecutionStatusEnum.SUCCESS);
        } else {
            sysTimedTaskLog.setExecutionStatus(SysTimedTaskExecutionStatusEnum.EXCEPTION);
            String exceptionMessage = jobException.getMessage();
            if (StringUtils.length(exceptionMessage) > 255) {
                logger.warn("定时任务异常信息超长[{}],截断处理", exceptionMessage);
                exceptionMessage = StringUtils.substring(exceptionMessage, 0, 255);
            }
            sysTimedTaskLog.setExceptionMessage(exceptionMessage);
        }
        sysTimedTaskLog.setExecutionStartDateTime(startLocalDateTime);
        sysTimedTaskLog.setExecutionEndDateTime(endLocalDateTime);
        sysTimedTaskLog.setExecutionTime(jobRunTime);
        sysTimedTaskLog.setUpdatedDateTime(LocalDateTime.now());
        sysTimedTaskLog.setCreatedDateTime(sysTimedTaskLog.getUpdatedDateTime());
        sysTimedTaskLogMapper.insert(sysTimedTaskLog);
        logger.debug("定时任务日志监听:任务编码:[{}] 任务名称:[{}] 任务执行时间(毫秒数):[{}]", sysTimedTask.getCode(), sysTimedTask.getName(), jobRunTime);

    }
}
