package com.chen.f.admin.helper;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.chen.f.core.mapper.SysTimedTaskMapper;
import com.chen.f.core.pojo.SysTimedTask;
import com.chen.f.core.pojo.enums.StatusEnum;
import com.chen.f.core.util.JacksonUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

/**
 * 定时任务帮助类
 *
 * @author chen
 * @date 2018/10/28 0:20.
 */
public class QuartzHelper {
    protected static final Logger logger = LoggerFactory.getLogger(QuartzHelper.class);

    private final SchedulerFactoryBean schedulerFactoryBean;
    private final SysTimedTaskMapper sysTimedTaskMapper;

    public QuartzHelper(SchedulerFactoryBean schedulerFactoryBean, SysTimedTaskMapper sysTimedTaskMapper) {
        this.schedulerFactoryBean = schedulerFactoryBean;
        this.sysTimedTaskMapper = sysTimedTaskMapper;
    }


    /**
     * 初始化定时任务
     * <p>
     * 可手动调用做刷新操作
     */
    @PostConstruct
    public void init() {
        logger.debug("配置定时任务开始...");
        List<SysTimedTask> enabledSysTimedTaskList = sysTimedTaskMapper.selectList(Wrappers.<SysTimedTask>lambdaQuery().eq(SysTimedTask::getStatus, StatusEnum.ENABLED));

        if (CollectionUtils.isNotEmpty(enabledSysTimedTaskList)) {
            logger.debug("查询出 {} 条定时任务", enabledSysTimedTaskList.size());
            enabledSysTimedTaskList.forEach((sysTimedTask -> {
                        try {
                            Class<?> jobClass = Class.forName(sysTimedTask.getClassName());
                            if (!Job.class.isAssignableFrom(jobClass)) {
                                logger.warn("[{}]不是Job的实现类,添加定时任务失败", jobClass);
                                return;
                            }
                            Map dataMap = null;
                            if (StringUtils.isNotBlank(sysTimedTask.getData())) {
                                dataMap = JacksonUtils.parseObject(sysTimedTask.getData(), Map.class);
                            }
                            addJob(sysTimedTask.getCode(), sysTimedTask.getCode(), sysTimedTask.getCode(), sysTimedTask.getCode(),
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

    /**
     * 动态添加定时任务.
     *
     * @param jobName          任务的名称;
     * @param jobGroupName     任务的组名称;
     * @param triggerName      触发器名称
     * @param triggerGroupName 触发器组名称
     * @param jobClass         job实现类.
     * @param cronExpression   定时任务表达式
     * @param dataMap          任务数据
     * @throws SchedulerException 定时任务调度异常
     */
    public void addJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName,
                       Class<? extends Job> jobClass, String cronExpression, Map<?, ?> dataMap) throws SchedulerException {

        //需要获取到任务调度器Scheduler
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        //定义jobDetail;
        JobBuilder jobBuilder = JobBuilder
                .newJob(jobClass)
                .withIdentity(jobName, jobGroupName)
                .storeDurably(true)
                .requestRecovery(true);
        if (MapUtils.isNotEmpty(dataMap)) {
            jobBuilder.setJobData(new JobDataMap(dataMap));
        }
        JobDetail jobDetail = jobBuilder.build();

        //定义trigger;
        Trigger trigger = TriggerBuilder
                .newTrigger()
                .withIdentity(triggerName, triggerGroupName)
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                .build();
        // 使用Scheduler添加任务;
        scheduler.scheduleJob(jobDetail, trigger);
    }

    /**
     * 定时任务删除
     *
     * @param jobName          任务的名称;
     * @param jobGroupName     任务的组名称;
     * @param triggerName      触发器名称
     * @param triggerGroupName 触发器组名称
     * @throws SchedulerException 定时任务调度异常
     */
    public void deleteJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName) throws SchedulerException {
        //  获取到定时任务调度器.
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);

        // 停止触发器.
        scheduler.pauseTrigger(triggerKey);
        //删除触发器.
        scheduler.unscheduleJob(triggerKey);
        // 删除任务.
        JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
        scheduler.deleteJob(jobKey);
    }

    /**
     * 修改定时任务的时间.
     *
     * @param jobName          任务的名称;
     * @param jobGroupName     任务的组名称;
     * @param triggerName      触发器名称
     * @param triggerGroupName 触发器组名称
     * @param dataMap          任务数据
     * @throws SchedulerException 定时任务调度异常
     */
    public void updateJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName,
                          Class<? extends Job> jobClass, String cronExpression, Map<?, ?> dataMap) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();

        //JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
        //JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        deleteJob(jobName, jobGroupName, triggerName, triggerGroupName);
        addJob(jobName, jobGroupName, triggerName, triggerGroupName, jobClass, cronExpression, dataMap);
    }

    /**
     * 修改定时任务的时间.
     * 只修改corn表达式
     *
     * @param jobName          任务的名称;
     * @param jobGroupName     任务的组名称;
     * @param triggerName      触发器名称
     * @param triggerGroupName 触发器组名称
     * @throws SchedulerException 定时任务调度异常
     */
    public void updateJobCornExpression(String jobName, String jobGroupName, String triggerName, String triggerGroupName, String cronExpression) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();

        TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
        Trigger oldTrigger = scheduler.getTrigger(triggerKey);
        String oldCronExpression = ((CronTrigger) oldTrigger).getCronExpression();
        if (StringUtils.equals(oldCronExpression, cronExpression)) {
            return;
        }
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
        Trigger trigger = TriggerBuilder
                .newTrigger()
                .withIdentity(triggerKey)
                .withSchedule(cronScheduleBuilder)
                .build();
        scheduler.rescheduleJob(triggerKey, trigger);
    }

    /**
     * 立刻执行一次job
     *
     * @param jobName      任务的名称;
     * @param jobGroupName 任务的组名称;
     * @throws SchedulerException 定时任务调度异常
     */
    public void triggerJob(String jobName, String jobGroupName) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey key = JobKey.jobKey(jobName, jobGroupName);
        scheduler.triggerJob(key);
    }

    /**
     * 停止job
     *
     * @param jobName      任务的名称;
     * @param jobGroupName 任务的组名称;
     * @throws SchedulerException 定时任务调度异常
     */
    public void pauseJob(String jobName, String jobGroupName) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey key = JobKey.jobKey(jobName, jobGroupName);
        scheduler.pauseJob(key);
    }

    /**
     * 恢复job
     *
     * @param jobName      任务的名称;
     * @param jobGroupName 任务的组名称;
     * @throws SchedulerException 定时任务调度异常
     */
    public void resumeJob(String jobName, String jobGroupName) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey key = JobKey.jobKey(jobName, jobGroupName);
        scheduler.resumeJob(key);
    }


    /**
     * 启动所有的定时任务.
     * 这个是配合standby一起使用的，
     * 当job处于standby模式的时候，调用startJobs可以恢复定时任务.
     *
     * @throws SchedulerException 定时任务调度异常
     */
    public void startJobs() throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        if (!scheduler.isStarted()) {
            scheduler.start();
        }
    }

    /**
     * 将定时任务设置为待机或者备用状态，暂停的模式.
     * 可以使用start进行恢复.
     *
     * @throws SchedulerException 定时任务调度异常
     */
    public void standbyJobs() throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        if (!scheduler.isInStandbyMode()) {
            scheduler.standby();
        }
    }

    /**
     * 关闭所有的定时任务.
     * 注意：一旦被shutdown之后，就不能调用startJobs.
     *
     * @throws SchedulerException 定时任务调度异常
     */
    public void shutdownJobs() throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        if (!scheduler.isShutdown()) {
            scheduler.shutdown();
        }
    }

}
