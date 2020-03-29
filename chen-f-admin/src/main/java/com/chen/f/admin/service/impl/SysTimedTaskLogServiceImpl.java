package com.chen.f.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.f.admin.service.ISysTimedTaskLogService;
import com.chen.f.common.mapper.SysTimedTaskLogMapper;
import com.chen.f.common.pojo.SysTimedTaskLog;
import com.chen.f.common.pojo.enums.SysTimedTaskLogExecutionStatusEnum;
import com.chen.f.common.pojo.enums.SysTimedTaskTypeEnum;
import com.chen.f.common.api.ApiAssert;
import com.chen.f.common.api.response.error.ErrorResponse;
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
    public IPage<SysTimedTaskLog> getSysTimedTaskLogPage(Long pageIndex, Long pageNumber,
                                                         String code, String name, SysTimedTaskTypeEnum type, SysTimedTaskLogExecutionStatusEnum executionStatus, String remark) {
        LambdaQueryWrapper<SysTimedTaskLog> queryWrapper = Wrappers.<SysTimedTaskLog>lambdaQuery();
        if (StringUtils.isNotBlank(code)) {
            queryWrapper.eq(SysTimedTaskLog::getCode, code);
        }
        if (StringUtils.isNotBlank(name)) {
            queryWrapper.eq(SysTimedTaskLog::getName, name);
        }
        if (Objects.nonNull(type)) {
            queryWrapper.eq(SysTimedTaskLog::getType, type);
        }
        if (Objects.nonNull(executionStatus)) {
            queryWrapper.eq(SysTimedTaskLog::getExecutionStatus, executionStatus);
        }
        if (StringUtils.isNotBlank(remark)) {
            queryWrapper.like(SysTimedTaskLog::getRemark, remark);
        }
        return sysTimedTaskLogMapper.selectPage(new Page<>(pageIndex, pageNumber), queryWrapper);
    }

    @Override
    public void deleteSysTimedTaskLog(String sysTimedTaskLogId) {
        ApiAssert.isNotBlank(sysTimedTaskLogId, ErrorResponse.create("系统定时任务记录id"));
        sysTimedTaskLogMapper.deleteById(sysTimedTaskLogId);
    }
}
