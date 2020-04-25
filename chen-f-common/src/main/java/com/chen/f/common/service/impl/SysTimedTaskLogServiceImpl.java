package com.chen.f.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.f.common.mapper.SysTimedTaskLogMapper;
import com.chen.f.common.pojo.SysTimedTaskLog;
import com.chen.f.common.pojo.enums.SysTimedTaskExecutionStatusEnum;
import com.chen.f.common.pojo.enums.SysTimedTaskTypeEnum;
import com.chen.f.common.service.ISysTimedTaskLogService;
import com.chen.f.core.api.ApiAssert;
import com.chen.f.core.api.response.error.ErrorResponse;
import com.chen.f.core.page.Page;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * <p>
 * 系统定时任务日志表 服务实现类
 * </p>
 *
 * @author chen
 * @since 2018-12-02
 */
@Service
public class SysTimedTaskLogServiceImpl extends ServiceImpl<SysTimedTaskLogMapper, SysTimedTaskLog> implements ISysTimedTaskLogService {
    protected static final Logger logger = LoggerFactory.getLogger(SysTimedTaskLogServiceImpl.class);

    @Autowired
    private SysTimedTaskLogMapper sysTimedTaskLogMapper;

    @Override
    public Page<SysTimedTaskLog> getSysTimedTaskLogPage(Page<SysTimedTaskLog> page,
                                                        String code, String name, SysTimedTaskTypeEnum type, SysTimedTaskExecutionStatusEnum executionStatus, String remark) {
        LambdaQueryWrapper<SysTimedTaskLog> queryWrapper = Wrappers.<SysTimedTaskLog>lambdaQuery();
        queryWrapper.eq(StringUtils.isNotBlank(code), SysTimedTaskLog::getCode, code);
        queryWrapper.eq(StringUtils.isNotBlank(name), SysTimedTaskLog::getName, name);
        queryWrapper.eq(Objects.nonNull(type), SysTimedTaskLog::getType, type);
        queryWrapper.eq(Objects.nonNull(executionStatus), SysTimedTaskLog::getExecutionStatus, executionStatus);
        queryWrapper.like(StringUtils.isNotBlank(remark), SysTimedTaskLog::getRemark, remark);
        return sysTimedTaskLogMapper.selectPage(page, queryWrapper);
    }

    @Override
    public SysTimedTaskLog getSysTimedTaskLog(String sysTimedTaskLogId) {
        return sysTimedTaskLogMapper.selectById(sysTimedTaskLogId);
    }

    @Override
    public void deleteSysTimedTaskLog(String sysTimedTaskLogId) {
        ApiAssert.isNotBlank(sysTimedTaskLogId, ErrorResponse.create("系统定时任务日志ID"));
        sysTimedTaskLogMapper.deleteById(sysTimedTaskLogId);
    }
}
