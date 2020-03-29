package com.chen.f.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chen.f.common.pojo.SysTimedTaskLog;
import com.chen.f.common.pojo.enums.SysTimedTaskLogExecutionStatusEnum;
import com.chen.f.common.pojo.enums.SysTimedTaskTypeEnum;

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
     * 获取分页的系统定时任务记录集合
     *
     * @param pageIndex       页数
     * @param pageNumber      页大小
     * @param code            标识
     * @param name            名称
     * @param type            类型
     * @param executionStatus 执行状态
     * @param remark          备注
     * @return 分页的系统定时任务记录集合
     */
    IPage<SysTimedTaskLog> getSysTimedTaskLogPage(long pageIndex, long pageNumber,
                                                  String code, String name, SysTimedTaskTypeEnum type, SysTimedTaskLogExecutionStatusEnum executionStatus, String remark);


    /**
     * 删除定时任务记录
     *
     * @param sysTimedTaskLogId 定时任务记录id
     */
    void deleteSysTimedTaskLog(String sysTimedTaskLogId);
}
