package com.chen.f.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chen.f.common.pojo.SysTimedTaskLog;
import com.chen.f.common.pojo.enums.SysTimedTaskExecutionStatusEnum;
import com.chen.f.common.pojo.enums.SysTimedTaskTypeEnum;
import com.chen.f.core.page.Page;

/**
 * <p>
 * 系统定时任务日志表 服务类
 * </p>
 *
 * @author chen
 * @since 2018-12-02
 */
public interface ISysTimedTaskLogService extends IService<SysTimedTaskLog> {


    /**
     * 获取分页的系统定时任务日志列表
     *
     * @param page            分页信息
     * @param code            系统定时任务日志标识
     * @param name            系统定时任务日志名称
     * @param type            系统定时任务日志类型
     * @param executionStatus 系统定时任务日志执行状态
     * @param remark          系统定时任务日志备注
     * @return 分页的系统定时任务日志列表
     */
    Page<SysTimedTaskLog> getSysTimedTaskLogPage(Page<SysTimedTaskLog> page,
                                                 String code, String name, SysTimedTaskTypeEnum type, SysTimedTaskExecutionStatusEnum executionStatus, String remark);


    /**
     * 获取系统定时任务日志
     *
     * @param sysTimedTaskLogId 系统定时任务日志ID
     * @return 系统定时任务日志
     */
    SysTimedTaskLog getSysTimedTaskLog(String sysTimedTaskLogId);


    /**
     * 删除定时任务日志
     *
     * @param sysTimedTaskLogId 定时任务日志ID
     */
    void deleteSysTimedTaskLog(String sysTimedTaskLogId);
}
