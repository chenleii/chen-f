package com.chen.f.admin.web.controller;


import com.chen.f.common.pojo.SysTimedTaskLog;
import com.chen.f.common.pojo.enums.SysTimedTaskExecutionStatusEnum;
import com.chen.f.common.pojo.enums.SysTimedTaskTypeEnum;
import com.chen.f.common.service.ISysTimedTaskLogService;
import com.chen.f.core.api.response.success.R;
import com.chen.f.core.page.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 系统定时任务日志表 前端控制器
 * </p>
 *
 * @author chen
 * @since 2018-12-02
 */
@Api(tags = "系统定时任务日志接口")
@RestController
@RequestMapping("/chen/admin/sys/timedTask/log")
public class SysTimedTaskLogController {
    protected static final Logger logger = LoggerFactory.getLogger(SysTimedTaskLogController.class);

    @Autowired
    private ISysTimedTaskLogService sysTimedTaskLogService;


    @ApiOperation(value = "获取分页的定时任务日志", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageIndex", value = "当前页数", required = true, dataTypeClass = Long.class, paramType = "query", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", required = true, dataTypeClass = Long.class, paramType = "query", defaultValue = "10"),
            @ApiImplicitParam(name = "sort", value = "排序信息(eg:name1.ascend-name2.descend)", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "code", value = "系统定时任务编码", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "name", value = "系统定时任务名称", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "type", value = "系统定时任务类型", required = false, dataTypeClass = SysTimedTaskTypeEnum.class, paramType = "query"),
            @ApiImplicitParam(name = "executionStatus", value = "系统定时任务执行状态", required = false, dataTypeClass = SysTimedTaskExecutionStatusEnum.class, paramType = "query"),
            @ApiImplicitParam(name = "remark", value = "系统定时任务备注", required = false, dataTypeClass = String.class, paramType = "query"),
    })
    @ApiOperationSupport(ignoreParameters = {"list", "total", "orderList", "optimizeCountSql", "isSearchCount","searchCount", "hitCount",})
    @GetMapping
    public Page<SysTimedTaskLog> getSysTimedTaskLogPage(
            Page<SysTimedTaskLog> page,
            @RequestParam(name = "code", required = false) String code,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "type", required = false) SysTimedTaskTypeEnum type,
            @RequestParam(name = "executionStatus", required = false) SysTimedTaskExecutionStatusEnum executionStatusEnum,
            @RequestParam(name = "remark", required = false) String remark) {
        return sysTimedTaskLogService.getSysTimedTaskLogPage(page, code, name, type, executionStatusEnum, remark);
    }

    @ApiOperation(value = "获取定时任务日志", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysTimedTaskLogId", value = "定时任务日志ID", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @GetMapping("/{sysTimedTaskLogId}")
    @ResponseBody
    public SysTimedTaskLog getSysTimedTaskLog(@PathVariable(name = "sysTimedTaskLogId") String sysTimedTaskLogId) {
        return sysTimedTaskLogService.getSysTimedTaskLog(sysTimedTaskLogId);
    }

    @ApiOperation(value = "删除定时任务日志", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysTimedTaskLogId", value = "定时任务日志ID", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @DeleteMapping("/{sysTimedTaskLogId}")
    @ResponseBody
    public R deleteSysTimedTaskLog(@PathVariable(name = "sysTimedTaskLogId") String sysTimedTaskLogId) {
        sysTimedTaskLogService.deleteSysTimedTaskLog(sysTimedTaskLogId);
        return R.msg("success");
    }

}

