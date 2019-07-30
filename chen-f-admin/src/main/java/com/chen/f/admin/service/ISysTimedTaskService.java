package com.chen.f.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chen.f.common.pojo.SysTimedTask;
import com.chen.f.common.pojo.enums.StatusEnum;
import com.chen.f.common.pojo.enums.SysTimedTaskTypeEnum;

import java.util.List;

/**
 * <p>
 * 系统定时任务表 服务类
 * </p>
 *
 * @author chen
 * @since 2018-11-10
 */
public interface ISysTimedTaskService extends IService<SysTimedTask> {


    /**
     * 获取分页的定时任务集合
     *
     * @param pageIndex  页数
     * @param pageNumber 页大小
     * @param code       系统定时任务标识
     * @param name       系统定时任务名称
     * @param className  系统定时任务类名
     * @param type       系统定时任务类型
     * @param remark     系统定时任务备注
     * @param status     系统定时任务状态
     * @return 分页的定时任务集合
     */
    IPage<SysTimedTask> getSysTimedTaskPage(long pageIndex, long pageNumber,
                                            String code, String name, String className, SysTimedTaskTypeEnum type, String remark, StatusEnum status);

    /**
     * 获取启用的系统定时任务列表
     *
     * @return 启用的系统定时任务列表
     */
    List<SysTimedTask> getEnabledSysTimedTaskList();

    /**
     * 获取定时任务
     *
     * @param code 任务标识
     * @return 定时任务
     */
    SysTimedTask getSysTimedTask(String code);

    /**
     * 添加定时任务
     *
     * @param code                 任务标识
     * @param name                 任务名称
     * @param jobClassName         任务classname
     * @param cronExpression       cron表达式
     * @param data                 数据(json字符串)
     * @param sysTimedTaskTypeEnum 任务类型
     * @param remark               任务备注
     * @param statusEnum           任务状态
     * @param operatedSysUserId    操作的系统用户id
     */
    void createSysTimedTask(String code, String name, String jobClassName, String cronExpression, String data,
                            SysTimedTaskTypeEnum sysTimedTaskTypeEnum, String remark, StatusEnum statusEnum, String operatedSysUserId);

    /**
     * 修改定时任务
     *
     * @param code                 任务标识
     * @param name                 任务名称
     * @param jobClassName         任务classname
     * @param cronExpression       cron表达式
     * @param data                 数据(json字符串)
     * @param sysTimedTaskTypeEnum 任务类型
     * @param remark               任务备注
     * @param statusEnum           任务状态
     * @param operatedSysUserId    操作的系统用户id
     */
    void updateSysTimedTask(String code, String name, String jobClassName, String cronExpression, String data,
                            SysTimedTaskTypeEnum sysTimedTaskTypeEnum, String remark, StatusEnum statusEnum, String operatedSysUserId);

    /**
     * 删除定时任务
     *
     * @param code 定时任务标识
     */
    void deleteSysTimedTask(String code);

    /**
     * 启用定时任务
     *
     * @param code              定时任务标识
     * @param operatedSysUserId 操作的系统用户id
     */
    void enabledSysTimedTask(String code, String operatedSysUserId);

    /**
     * 禁用定时任务
     *
     * @param code              定时任务标识
     * @param operatedSysUserId 操作的系统用户id
     */
    void disableSysTimedTask(String code, String operatedSysUserId);

    /**
     * 立即执行一次任务
     *
     * @param code 定时任务标识
     */
    void executionSysTimedTask(String code);

}
