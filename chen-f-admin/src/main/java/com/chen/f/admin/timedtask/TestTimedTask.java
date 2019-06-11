package com.chen.f.admin.timedtask;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author chen
 * @since 2019/3/17 0:59.
 */
public class TestTimedTask implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("测试定时任务##################################");
        System.out.println("测试定时任务##################################");
        System.out.println("测试定时任务##################################");

        throw new JobExecutionException("测试定时任务异常");
    }
}
