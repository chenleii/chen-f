package com.chen.f.admin.web.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.chen.f.admin.service.ISysTimedTaskLogService;
import com.chen.f.core.api.response.success.R;
import com.chen.f.core.pojo.SysTimedTaskLog;
import com.chen.f.core.pojo.enums.ExecutionStatusEnum;
import com.chen.f.core.pojo.enums.SysTimedTaskTypeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 定时任务日志表 前端控制器
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


    @ApiOperation(value = "获取定时任务记录列表", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageIndex", value = "页数", required = true, dataTypeClass = Long.class, paramType = "query"),
            @ApiImplicitParam(name = "pageNumber", value = "页大小", required = true, dataTypeClass = Long.class, paramType = "query"),
            @ApiImplicitParam(name = "code", value = "系统定时任务标识", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "name", value = "系统定时任务名称", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "type", value = "系统定时任务类型", required = false, dataTypeClass = SysTimedTaskTypeEnum.class, paramType = "query"),
            @ApiImplicitParam(name = "executionStatus", value = "系统定时任务执行状态", required = false, dataTypeClass = ExecutionStatusEnum.class, paramType = "query"),
            @ApiImplicitParam(name = "remark", value = "系统定时任务备注", required = false, dataTypeClass = String.class, paramType = "query"),
    })
    @GetMapping
    public IPage<SysTimedTaskLog> getSysTimedTaskLogPage(@RequestParam("pageIndex") long pageIndex,
                                                         @RequestParam("pageNumber") long pageNumbe,
                                                         @RequestParam(name = "code", required = false) String code,
                                                         @RequestParam(name = "name", required = false) String name,
                                                         @RequestParam(name = "type", required = false) SysTimedTaskTypeEnum type,
                                                         @RequestParam(name = "executionStatus", required = false) ExecutionStatusEnum executionStatusEnum,
                                                         @RequestParam(name = "remark", required = false) String remark) {
        return sysTimedTaskLogService.getSysTimedTaskLogPage(pageIndex, pageNumbe, code, name, type, executionStatusEnum, remark);
    }

    @ApiOperation(value = "删除定时任务记录", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysTimedTaskLogId", value = "定时任务记录id", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @DeleteMapping("/{sysTimedTaskLogId}")
    @ResponseBody
    public R deleteSysTimedTaskLog(@PathVariable(name = "sysTimedTaskLogId") String sysTimedTaskLogId) {
        sysTimedTaskLogService.deleteSysTimedTaskLog(sysTimedTaskLogId);
        return R.msg("success");
    }

}

