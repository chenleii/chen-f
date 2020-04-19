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
     * 获取分页的系统定时任务集合
     *
     * @param pageIndex  页数
     * @param pageNumber 页大小
     * @param code       系统定时任务标识
     * @param name       系统定时任务名称
     * @param className  系统定时任务类名
     * @param type       系统定时任务类型
     * @param remark     系统定时任务备注
     * @param status     系统定时任务状态
     * @return 分页的系统定时任务集合
     */
    IPage<SysTimedTask> getSysTimedTaskPage(Long pageIndex, Long pageNumber,
                                            String code, String name, String className, SysTimedTaskTypeEnum type, String remark, StatusEnum status);

    /**
     * 获取启用的系统定时任务列表
     *
     * @return 启用的系统定时任务列表
     */
    List<SysTimedTask> getEnabledSysTimedTaskList();

    /**
     * 获取系统定时任务
     *
     * @param sysTimedTaskId 系统定时任务ID
     * @return 系统定时任务
     */
    SysTimedTask getSysTimedTask(String sysTimedTaskId);

    /**
     * 获取系统定时任务
     *
     * @param code 系统定时任务标识
     * @return 系统定时任务
     */
    SysTimedTask getSysTimedTaskByCode(String code);

    /**
     * 添加系统定时任务
     *
     * @param code                 系统定时任务标识
     * @param name                 系统定时任务名称
     * @param jobClassName         系统定时任务CLASSNAME
     * @param cronExpression       系统定时任务CRON表达式
     * @param data                 系统定时任务数据(JSON字符串)
     * @param sysTimedTaskTypeEnum 系统定时任务类型
     * @param remark               系统定时任务备注
     * @param statusEnum           系统定时任务状态
     * @param operatedSysUserId    操作的系统用户ID
     */
    void createSysTimedTask(String code, String name, String jobClassName, String cronExpression, String data,
                            SysTimedTaskTypeEnum sysTimedTaskTypeEnum, String remark, StatusEnum statusEnum, String operatedSysUserId);

    /**
     * 修改系统定时任务
     *
     * @param sysTimedTaskId       系统定时任务ID
     * @param code                 系统定时任务标识
     * @param name                 系统定时任务名称
     * @param jobClassName         系统定时任务CLASSNAME
     * @param cronExpression       系统定时任务CRON表达式
     * @param data                 系统定时任务数据(JSON字符串)
     * @param sysTimedTaskTypeEnum 系统定时任务类型
     * @param remark               系统定时任务备注
     * @param statusEnum           系统定时任务状态
     * @param operatedSysUserId    操作的系统用户ID
     */
    void updateSysTimedTask(String sysTimedTaskId, String code, String name, String jobClassName, String cronExpression, String data,
                            SysTimedTaskTypeEnum sysTimedTaskTypeEnum, String remark, StatusEnum statusEnum, String operatedSysUserId);

    /**
     * 删除系统定时任务
     *
     * @param sysTimedTaskId 系统定时任务ID
     */
    void deleteSysTimedTask(String sysTimedTaskId);

    /**
     * 删除系统定时任务
     *
     * @param code 系统定时任务标识
     */
    void deleteSysTimedTaskByCode(String code);

    /**
     * 启用系统定时任务
     *
     * @param sysTimedTaskId    系统定时任务ID
     * @param operatedSysUserId 操作的系统用户ID
     */
    void enabledSysTimedTask(String sysTimedTaskId, String operatedSysUserId);

    /**
     * 启用系统定时任务
     *
     * @param code              系统定时任务标识
     * @param operatedSysUserId 操作的系统用户ID
     */
    void enabledSysTimedTaskByCode(String code, String operatedSysUserId);

    /**
     * 禁用系统定时任务
     *
     * @param sysTimedTaskId    系统定时任务ID
     * @param operatedSysUserId 操作的系统用户ID
     */
    void disableSysTimedTask(String sysTimedTaskId, String operatedSysUserId);

    /**
     * 禁用系统定时任务
     *
     * @param code              系统定时任务标识
     * @param operatedSysUserId 操作的系统用户ID
     */
    void disableSysTimedTaskByCode(String code, String operatedSysUserId);

    /**
     * 立即执行一次系统定时任务
     *
     * @param sysTimedTaskId 系统定时任务ID
     */
    void executionSysTimedTask(String sysTimedTaskId);

    /**
     * 立即执行一次系统定时任务
     *
     * @param code 系统定时任务标识
     */
    void executionSysTimedTaskByCode(String code);

    /**
     * 初始化系统定时任务
     */
    void initSysTimedTask();

}
