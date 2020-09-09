package com.chen.f.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.f.common.api.response.error.SysTimedTaskErrorResponses;
import com.chen.f.common.api.response.error.SysUserErrorResponses;
import com.chen.f.common.mapper.SysTimedTaskMapper;
import com.chen.f.common.pojo.SysTimedTask;
import com.chen.f.common.pojo.enums.StatusEnum;
import com.chen.f.common.pojo.enums.SysTimedTaskTypeEnum;
import com.chen.f.common.service.ISysTimedTaskService;
import com.chen.f.core.api.ApiAssert;
import com.chen.f.core.configuration.quartz.QuartzHelper;
import com.chen.f.core.page.Page;
import com.chen.f.core.util.JacksonUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.quartz.Job;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 系统定时任务表 服务实现类
 * </p>
 *
 * @author chen
 * @since 2018-11-10
 */
@Service
public class SysTimedTaskServiceImpl extends ServiceImpl<SysTimedTaskMapper, SysTimedTask> implements ISysTimedTaskService {
    protected static final Logger logger = LoggerFactory.getLogger(SysTimedTaskServiceImpl.class);


    @Autowired
    private SysTimedTaskMapper sysTimedTaskMapper;

    @Override
    public Page<SysTimedTask> getSysTimedTaskPage(Page<SysTimedTask> page,
                                                  String code, String name, String className, SysTimedTaskTypeEnum type, String remark, StatusEnum status) {
        LambdaQueryWrapper<SysTimedTask> queryWrapper = Wrappers.<SysTimedTask>lambdaQuery();
        queryWrapper.eq(StringUtils.isNotBlank(code), SysTimedTask::getCode, code);
        queryWrapper.eq(StringUtils.isNotBlank(name), SysTimedTask::getName, name);
        queryWrapper.eq(StringUtils.isNotBlank(className), SysTimedTask::getClassName, className);
        queryWrapper.eq(Objects.nonNull(type), SysTimedTask::getType, type);
        queryWrapper.like(StringUtils.isNotBlank(remark), SysTimedTask::getRemark, remark);
        queryWrapper.eq(Objects.nonNull(status), SysTimedTask::getStatus, status);
        return sysTimedTaskMapper.selectPage(page, queryWrapper);
    }

    @Override
    public List<SysTimedTask> getEnabledSysTimedTaskList() {
        return sysTimedTaskMapper.selectList(Wrappers.<SysTimedTask>lambdaQuery().eq(SysTimedTask::getStatus, StatusEnum.ENABLED));
    }

    @Override
    public SysTimedTask getSysTimedTask(String sysTimedTaskId) {
        ApiAssert.isNotBlank(sysTimedTaskId, SysTimedTaskErrorResponses.sysTimedTaskIdCanNotNull());
        return sysTimedTaskMapper.selectById(sysTimedTaskId);
    }

    @Override
    public SysTimedTask getSysTimedTaskByCode(String code) {
        ApiAssert.isNotBlank(code, SysTimedTaskErrorResponses.sysTimedTaskCodeCanNotBlank());
        return sysTimedTaskMapper.selectOne(Wrappers.<SysTimedTask>lambdaQuery().eq(SysTimedTask::getCode, code));
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    @SuppressWarnings("unchecked")
    public void createSysTimedTask(String code, String name, String jobClassName, String cronExpression, String data,
                                   SysTimedTaskTypeEnum sysTimedTaskTypeEnum, String remark, StatusEnum statusEnum, String operatedSysUserId) {
        ApiAssert.isNotBlank(code, SysTimedTaskErrorResponses.sysTimedTaskCodeCanNotBlank());
        ApiAssert.isNotBlank(name, SysTimedTaskErrorResponses.sysTimedTaskNameCanNotBlank());
        ApiAssert.isNotBlank(jobClassName, SysTimedTaskErrorResponses.sysTimedTaskJobClassNameCanNotBlank());
        ApiAssert.isNotBlank(cronExpression, SysTimedTaskErrorResponses.sysTimedTaskCronExpressionCanNotBlank());
        ApiAssert.isNotNull(sysTimedTaskTypeEnum, SysTimedTaskErrorResponses.sysTimedTaskTypeCanNotNull());
        ApiAssert.isNotNull(statusEnum, SysTimedTaskErrorResponses.sysTimedTaskStatusCanNotNull());
        ApiAssert.isNotBlank(operatedSysUserId, SysUserErrorResponses.operatedSysUserIdCanNotBlank());

        remark = ObjectUtils.defaultIfNull(remark, "");
        data = ObjectUtils.defaultIfNull(data, "");

        ApiAssert.isClassNameExist(jobClassName, SysTimedTaskErrorResponses.sysTimedTaskJobClassNotExist());
        Class<?> jobClass = null;
        try {
            jobClass = Class.forName(jobClassName);
        } catch (ClassNotFoundException e) {
            ApiAssert.exception(e, SysTimedTaskErrorResponses.sysTimedTaskJobClassNotExist());
        }

        logger.debug("检查jobClass是否是Job.class的子类");
        ApiAssert.isAssignable(Job.class, jobClass, SysTimedTaskErrorResponses.sysTimedTaskJobClassNotImplementJob(jobClass));
        Map<?, ?> dataMap = null;
        if (StringUtils.isNotBlank(data)) {
            dataMap = JacksonUtils.parseObject(data, Map.class);
        }
        if (statusEnum == StatusEnum.ENABLED) {
            logger.debug("添加系统定时任务");
            try {
                QuartzHelper.addJob(code, code, code, code, (Class<? extends Job>) jobClass, cronExpression, dataMap);
            } catch (SchedulerException e) {
                logger.warn("添加系统定时任务失败", e);
                ApiAssert.exception(e, SysTimedTaskErrorResponses.createSysTimedTaskFailure());
            }
        }

        logger.debug("插入系统定时任务到数据库");
        SysTimedTask sysTimedTask = new SysTimedTask();
        sysTimedTask.setCode(code);
        sysTimedTask.setName(name);
        sysTimedTask.setClassName(jobClassName);
        sysTimedTask.setCronExpression(cronExpression);
        sysTimedTask.setData(data);
        sysTimedTask.setType(sysTimedTaskTypeEnum);
        sysTimedTask.setStatus(statusEnum);
        sysTimedTask.setRemark(remark);
        sysTimedTask.setUpdatedSysUserId(operatedSysUserId);
        sysTimedTask.setCreatedSysUserId(operatedSysUserId);
        sysTimedTask.setUpdatedDateTime(LocalDateTime.now());
        sysTimedTask.setCreatedDateTime(LocalDateTime.now());
        final int i = sysTimedTaskMapper.insert(sysTimedTask);
        ApiAssert.isEqualToOne(i, SysTimedTaskErrorResponses.createSysTimedTaskFailure());
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    @SuppressWarnings("unchecked")
    public void updateSysTimedTask(String sysTimedTaskId, String code, String name, String jobClassName, String cronExpression, String data, SysTimedTaskTypeEnum sysTimedTaskTypeEnum, String remark, StatusEnum statusEnum, String operatedSysUserId) {
        ApiAssert.isNotBlank(sysTimedTaskId, SysTimedTaskErrorResponses.sysTimedTaskIdCanNotNull());
        ApiAssert.isNotBlank(code, SysTimedTaskErrorResponses.sysTimedTaskCodeCanNotBlank());
        ApiAssert.isNotBlank(name, SysTimedTaskErrorResponses.sysTimedTaskNameCanNotBlank());
        ApiAssert.isNotBlank(jobClassName, SysTimedTaskErrorResponses.sysTimedTaskJobClassNameCanNotBlank());
        ApiAssert.isNotBlank(cronExpression, SysTimedTaskErrorResponses.sysTimedTaskCronExpressionCanNotBlank());
        ApiAssert.isNotNull(sysTimedTaskTypeEnum, SysTimedTaskErrorResponses.sysTimedTaskTypeCanNotNull());
        ApiAssert.isNotNull(statusEnum, SysTimedTaskErrorResponses.sysTimedTaskStatusCanNotNull());
        ApiAssert.isNotBlank(operatedSysUserId, SysUserErrorResponses.operatedSysUserIdCanNotBlank());

        logger.debug("获取系统定时任务信息");
        SysTimedTask sysTimedTask = sysTimedTaskMapper.selectById(sysTimedTaskId);
        ApiAssert.isNotNull(sysTimedTask, SysTimedTaskErrorResponses.sysTimedTaskNotExist());

        remark = ObjectUtils.defaultIfNull(remark, "");
        data = ObjectUtils.defaultIfNull(data, "");

        ApiAssert.isClassNameExist(jobClassName, SysTimedTaskErrorResponses.sysTimedTaskJobClassNotExist());
        Class<?> jobClass = null;
        try {
            jobClass = Class.forName(jobClassName);
        } catch (ClassNotFoundException e) {
            ApiAssert.exception(e, SysTimedTaskErrorResponses.sysTimedTaskJobClassNotExist());
        }

        logger.debug("检查jobClass是否是Job.class的子类");
        ApiAssert.isAssignable(Job.class, jobClass, SysTimedTaskErrorResponses.sysTimedTaskJobClassNotImplementJob(jobClass));
        Map<?, ?> dataMap = null;
        if (StringUtils.isNotBlank(data)) {
            dataMap = JacksonUtils.parseObject(data, Map.class);
        }

        //原系统定时任务是启用的 并且 修改后的系统定时任务是启用的
        if (sysTimedTask.getStatus() == StatusEnum.ENABLED
                && statusEnum == StatusEnum.ENABLED) {
            logger.debug("修改系统定时任务");
            try {
                QuartzHelper.updateJob(code, code, code, code, (Class<? extends Job>) jobClass, cronExpression, dataMap);
            } catch (SchedulerException e) {
                logger.warn("添加系统定时任务失败", e);
                ApiAssert.exception(e, SysTimedTaskErrorResponses.updateSysTimedTaskFailure());
            }
        }

        if (statusEnum == StatusEnum.DISABLED) {
            this.disableSysTimedTaskByCode(code, operatedSysUserId);
        }

        logger.debug("修改系统定时任务数据库记录");
        sysTimedTask.setCode(code);
        sysTimedTask.setName(name);
        sysTimedTask.setClassName(jobClassName);
        sysTimedTask.setCronExpression(cronExpression);
        sysTimedTask.setData(data);
        sysTimedTask.setType(sysTimedTaskTypeEnum);
        sysTimedTask.setStatus(statusEnum);
        sysTimedTask.setRemark(remark);
        sysTimedTask.setUpdatedSysUserId(operatedSysUserId);
        sysTimedTask.setUpdatedDateTime(LocalDateTime.now());
        final int i = sysTimedTaskMapper.updateById(sysTimedTask);
        ApiAssert.isEqualToOne(i, SysTimedTaskErrorResponses.updateSysTimedTaskFailure());
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void deleteSysTimedTask(String sysTimedTaskId) {
        ApiAssert.isNotBlank(sysTimedTaskId, SysTimedTaskErrorResponses.sysTimedTaskIdCanNotNull());

        logger.debug("检查系统定时任务是否存在");
        SysTimedTask sysTimedTask = sysTimedTaskMapper.selectById(sysTimedTaskId);
        ApiAssert.isNotNull(sysTimedTask, SysTimedTaskErrorResponses.sysTimedTaskNotExist());

        logger.debug("删除系统定时任务");
        try {
            QuartzHelper.deleteJob(sysTimedTask.getCode(), sysTimedTask.getCode(), sysTimedTask.getCode(), sysTimedTask.getCode());
        } catch (SchedulerException e) {
            logger.warn("删除(禁用)系统定时任务失败", e);
            ApiAssert.exception(e, SysTimedTaskErrorResponses.deleteSysTimedTaskFailure());
        }

        logger.debug("删除系统定时任务数据库记录");
        int i = sysTimedTaskMapper.deleteById(sysTimedTaskId);
        ApiAssert.isEqualToOne(i, SysTimedTaskErrorResponses.deleteSysTimedTaskFailure());
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void deleteSysTimedTaskByCode(String code) {
        ApiAssert.isNotBlank(code, SysTimedTaskErrorResponses.sysTimedTaskCodeCanNotBlank());

        logger.debug("检查系统定时任务是否存在");
        SysTimedTask sysTimedTask = sysTimedTaskMapper.selectOne(Wrappers.<SysTimedTask>lambdaQuery().eq(SysTimedTask::getCode, code));
        ApiAssert.isNotNull(sysTimedTask, SysTimedTaskErrorResponses.sysTimedTaskNotExist());

        logger.debug("删除系统定时任务");
        try {
            QuartzHelper.deleteJob(code, code, code, code);
        } catch (SchedulerException e) {
            logger.warn("删除(禁用)系统定时任务失败", e);
            ApiAssert.exception(e, SysTimedTaskErrorResponses.deleteSysTimedTaskFailure());
        }
        logger.debug("删除系统定时任务数据库记录");
        int i = sysTimedTaskMapper.delete(Wrappers.<SysTimedTask>lambdaQuery().eq(SysTimedTask::getCode, code));
        ApiAssert.isEqualToOne(i, SysTimedTaskErrorResponses.deleteSysTimedTaskFailure());
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    @SuppressWarnings("unchecked")
    public void enabledSysTimedTask(String sysTimedTaskId, String operatedSysUserId) {
        ApiAssert.isNotBlank(sysTimedTaskId, SysTimedTaskErrorResponses.sysTimedTaskIdCanNotNull());
        ApiAssert.isNotBlank(operatedSysUserId, SysUserErrorResponses.operatedSysUserIdCanNotBlank());

        logger.debug("获取系统定时任务信息");
        SysTimedTask sysTimedTask = sysTimedTaskMapper.selectById(sysTimedTaskId);
        ApiAssert.isNotNull(sysTimedTask, SysTimedTaskErrorResponses.sysTimedTaskNotExist());

        ApiAssert.isClassNameExist(sysTimedTask.getClassName(), SysTimedTaskErrorResponses.sysTimedTaskJobClassNotExist());
        Class<?> jobClass = null;
        try {
            jobClass = Class.forName(sysTimedTask.getClassName());
        } catch (ClassNotFoundException e) {
            ApiAssert.exception(e, SysTimedTaskErrorResponses.sysTimedTaskJobClassNotExist());
        }

        logger.debug("检查jobClass是否是Job.class的子类");
        ApiAssert.isAssignable(Job.class, jobClass, SysTimedTaskErrorResponses.sysTimedTaskJobClassNotImplementJob(jobClass));
        Map<?, ?> dataMap = null;
        if (StringUtils.isNotBlank(sysTimedTask.getData())) {
            dataMap = JacksonUtils.parseObject(sysTimedTask.getData(), Map.class);
        }
        logger.debug("添加启用的系统定时任务");
        try {
            QuartzHelper.addJob(sysTimedTask.getCode(), sysTimedTask.getCode(), sysTimedTask.getCode(), sysTimedTask.getCode(),
                    (Class<? extends Job>) jobClass, sysTimedTask.getCronExpression(), dataMap);
        } catch (SchedulerException e) {
            logger.warn("添加(启用)系统定时任务失败", e);
            ApiAssert.exception(e, SysTimedTaskErrorResponses.updateSysTimedTaskFailure());
        }
        logger.debug("修改系统定时任务数据库记录为启用");
        sysTimedTask.setUpdatedSysUserId(operatedSysUserId);
        sysTimedTask.setUpdatedDateTime(LocalDateTime.now());
        sysTimedTask.setStatus(StatusEnum.ENABLED);
        int i = sysTimedTaskMapper.updateById(sysTimedTask);
        ApiAssert.isEqualToOne(i, SysTimedTaskErrorResponses.updateSysTimedTaskFailure());
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    @SuppressWarnings("unchecked")
    public void enabledSysTimedTaskByCode(String code, String operatedSysUserId) {
        ApiAssert.isNotBlank(code, SysTimedTaskErrorResponses.sysTimedTaskCodeCanNotBlank());
        ApiAssert.isNotBlank(operatedSysUserId, SysUserErrorResponses.operatedSysUserIdCanNotBlank());

        logger.debug("获取系统定时任务信息");
        SysTimedTask sysTimedTask = sysTimedTaskMapper.selectOne(Wrappers.<SysTimedTask>lambdaQuery().eq(SysTimedTask::getCode, code));
        ApiAssert.isNotNull(sysTimedTask, SysTimedTaskErrorResponses.sysTimedTaskNotExist());

        ApiAssert.isClassNameExist(sysTimedTask.getClassName(), SysTimedTaskErrorResponses.sysTimedTaskJobClassNotExist());
        Class<?> jobClass = null;
        try {
            jobClass = Class.forName(sysTimedTask.getClassName());
        } catch (ClassNotFoundException e) {
            ApiAssert.exception(e, SysTimedTaskErrorResponses.sysTimedTaskJobClassNotExist());
        }

        logger.debug("检查jobClass是否是Job.class的子类");
        ApiAssert.isAssignable(Job.class, jobClass, SysTimedTaskErrorResponses.sysTimedTaskJobClassNotImplementJob(jobClass));
        Map<?, ?> dataMap = null;
        if (StringUtils.isNotBlank(sysTimedTask.getData())) {
            dataMap = JacksonUtils.parseObject(sysTimedTask.getData(), Map.class);
        }
        logger.debug("添加启用的系统定时任务");
        try {
            QuartzHelper.addJob(sysTimedTask.getCode(), sysTimedTask.getCode(), sysTimedTask.getCode(), sysTimedTask.getCode(),
                    (Class<? extends Job>) jobClass, sysTimedTask.getCronExpression(), dataMap);
        } catch (SchedulerException e) {
            logger.warn("添加(启用)系统定时任务失败", e);
            ApiAssert.exception(e, SysTimedTaskErrorResponses.updateSysTimedTaskFailure());
        }
        logger.debug("修改系统定时任务数据库记录为启用");
        sysTimedTask.setUpdatedSysUserId(operatedSysUserId);
        sysTimedTask.setUpdatedDateTime(LocalDateTime.now());
        sysTimedTask.setStatus(StatusEnum.ENABLED);
        int i = sysTimedTaskMapper.updateById(sysTimedTask);
        ApiAssert.isEqualToOne(i, SysTimedTaskErrorResponses.deleteSysTimedTaskFailure());
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void disableSysTimedTask(String sysTimedTaskId, String operatedSysUserId) {
        ApiAssert.isNotBlank(sysTimedTaskId, SysTimedTaskErrorResponses.sysTimedTaskIdCanNotNull());
        ApiAssert.isNotBlank(operatedSysUserId, SysUserErrorResponses.operatedSysUserIdCanNotBlank());

        logger.debug("获取系统定时任务信息");
        SysTimedTask sysTimedTask = sysTimedTaskMapper.selectById(sysTimedTaskId);
        ApiAssert.isNotNull(sysTimedTask, SysTimedTaskErrorResponses.sysTimedTaskNotExist());

        logger.debug("删除禁用的数据库记录");
        try {
            QuartzHelper.deleteJob(sysTimedTask.getCode(), sysTimedTask.getCode(), sysTimedTask.getCode(), sysTimedTask.getCode());
        } catch (SchedulerException e) {
            logger.warn("删除(禁用)系统定时任务失败", e);
            ApiAssert.exception(e, SysTimedTaskErrorResponses.updateSysTimedTaskFailure());
        }
        logger.debug("修改系统定时任务数据库记录为禁用");
        sysTimedTask.setUpdatedSysUserId(operatedSysUserId);
        sysTimedTask.setUpdatedDateTime(LocalDateTime.now());
        sysTimedTask.setStatus(StatusEnum.DISABLED);
        int i = sysTimedTaskMapper.updateById(sysTimedTask);
        ApiAssert.isEqualToOne(i, SysTimedTaskErrorResponses.updateSysTimedTaskFailure());
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void disableSysTimedTaskByCode(String code, String operatedSysUserId) {
        ApiAssert.isNotBlank(code, SysTimedTaskErrorResponses.sysTimedTaskCodeCanNotBlank());
        ApiAssert.isNotBlank(operatedSysUserId, SysUserErrorResponses.operatedSysUserIdCanNotBlank());

        logger.debug("获取系统定时任务信息");
        SysTimedTask sysTimedTask = sysTimedTaskMapper.selectOne(Wrappers.<SysTimedTask>lambdaQuery().eq(SysTimedTask::getCode, code));
        ApiAssert.isNotNull(sysTimedTask, SysTimedTaskErrorResponses.sysTimedTaskNotExist());

        logger.debug("删除禁用的数据库记录");
        try {
            QuartzHelper.deleteJob(code, code, code, code);
        } catch (SchedulerException e) {
            logger.warn("删除(禁用)系统定时任务失败", e);
            ApiAssert.exception(e, SysTimedTaskErrorResponses.updateSysTimedTaskFailure());
        }

        logger.debug("修改系统定时任务数据库记录为禁用");
        sysTimedTask.setUpdatedSysUserId(operatedSysUserId);
        sysTimedTask.setUpdatedDateTime(LocalDateTime.now());
        sysTimedTask.setStatus(StatusEnum.DISABLED);
        int i = sysTimedTaskMapper.updateById(sysTimedTask);
        ApiAssert.isEqualToOne(i, SysTimedTaskErrorResponses.updateSysTimedTaskFailure());
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void executionSysTimedTask(String sysTimedTaskId) {
        ApiAssert.isNotBlank(sysTimedTaskId, SysTimedTaskErrorResponses.sysTimedTaskIdCanNotNull());

        SysTimedTask sysTimedTask = sysTimedTaskMapper.selectById(sysTimedTaskId);
        ApiAssert.isNotNull(sysTimedTask, SysTimedTaskErrorResponses.sysTimedTaskNotExist());

        try {
            logger.debug("执行系统定时任务");
            QuartzHelper.triggerJob(sysTimedTask.getCode(), sysTimedTask.getCode());
        } catch (SchedulerException e) {
            logger.warn("立即执行系统定时任务出错", e);
            ApiAssert.exception(e, SysTimedTaskErrorResponses.executionSysTimedTaskFailure());
        }
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void executionSysTimedTaskByCode(String code) {
        ApiAssert.isNotBlank(code, SysTimedTaskErrorResponses.sysTimedTaskCodeCanNotBlank());

        try {
            logger.debug("执行系统定时任务");
            QuartzHelper.triggerJob(code, code);
        } catch (SchedulerException e) {
            logger.warn("立即执行系统定时任务出错", e);
            ApiAssert.exception(e, SysTimedTaskErrorResponses.executionSysTimedTaskFailure());
        }
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    @SuppressWarnings("unchecked")
    public void initSysTimedTask() {
        logger.debug("配置定时任务开始...");
        List<SysTimedTask> enabledSysTimedTaskList = this.getEnabledSysTimedTaskList();

        if (CollectionUtils.isNotEmpty(enabledSysTimedTaskList)) {
            logger.debug("查询出 {} 条定时任务", enabledSysTimedTaskList.size());
            enabledSysTimedTaskList.forEach((sysTimedTask -> {
                try {
                    Class<?> jobClass = Class.forName(sysTimedTask.getClassName());
                    if (!Job.class.isAssignableFrom(jobClass)) {
                        logger.warn("[{}]不是Job的实现类,添加定时任务失败", jobClass);
                        return;
                    }
                    Map<?, ?> dataMap = null;
                    if (StringUtils.isNotBlank(sysTimedTask.getData())) {
                        dataMap = JacksonUtils.parseObject(sysTimedTask.getData(), Map.class);
                    }
                    QuartzHelper.addJob(sysTimedTask.getCode(), sysTimedTask.getCode(), sysTimedTask.getCode(), sysTimedTask.getCode(),
                            (Class<? extends Job>) jobClass, sysTimedTask.getCronExpression(), dataMap);
                } catch (SchedulerException e) {
                    logger.warn("添加定时任务异常", e);
                } catch (ClassNotFoundException e) {
                    logger.warn("添加定时任务失败,没有找到Job[{}]", sysTimedTask.getClassName());
                } catch (Exception e) {
                    logger.warn("添加定时任务失败,出现异常", e);
                }
            }));

        } else {
            logger.debug("没有定时任务");
        }
        logger.debug("配置定时任务结束...");
    }
}
