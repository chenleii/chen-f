package com.chen.f.admin.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.chen.f.admin.configuration.helper.SecurityHelper;
import com.chen.f.admin.service.ISysTimedTaskService;
import com.chen.f.admin.web.dto.SysTimedTaskInputDTO;
import com.chen.f.common.pojo.SysTimedTask;
import com.chen.f.common.pojo.enums.StatusEnum;
import com.chen.f.common.pojo.enums.SysTimedTaskTypeEnum;
import com.chen.f.common.api.response.success.R;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 系统定时任务表 前端控制器
 * </p>
 *
 * @author chen
 * @since 2018-12-02
 */
@Api(tags = "系统定时任务接口")
@RestController
@RequestMapping("/chen/admin/sys/timedTask")
public class SysTimedTaskController {
    protected static final Logger logger = LoggerFactory.getLogger(SysTimedTaskController.class);

    @Autowired
    private ISysTimedTaskService sysTimedTaskService;

    @ApiOperation(value = "获取分页的系统定时任务列表", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageIndex", value = "页数", required = true, dataTypeClass = Long.class, paramType = "query", defaultValue = "1"),
            @ApiImplicitParam(name = "pageNumber", value = "页大小", required = true, dataTypeClass = Long.class, paramType = "query", defaultValue = "10"),
            @ApiImplicitParam(name = "code", value = "系统定时任务标识", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "name", value = "系统定时任务名称", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "className", value = "系统定时任务CLASSNAME", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "type", value = "系统定时任务类型", required = false, dataTypeClass = SysTimedTaskTypeEnum.class, paramType = "query"),
            @ApiImplicitParam(name = "remark", value = "系统定时任务备注", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "status", value = "系统定时任务状态", required = false, dataTypeClass = StatusEnum.class, paramType = "query"),
    })
    @GetMapping
    public IPage<SysTimedTask> getSysTimedTaskPage(@RequestParam(name = "pageIndex", defaultValue = "1") Long pageIndex,
                                                   @RequestParam(name = "pageNumber", defaultValue = "10") long pageNumbe,
                                                   @RequestParam(name = "code", required = false) String code,
                                                   @RequestParam(name = "name", required = false) String name,
                                                   @RequestParam(name = "className", required = false) String className,
                                                   @RequestParam(name = "type", required = false) SysTimedTaskTypeEnum type,
                                                   @RequestParam(name = "remark", required = false) String remark,
                                                   @RequestParam(name = "status", required = false) StatusEnum status) {
        return sysTimedTaskService.getSysTimedTaskPage(pageIndex, pageNumbe, code, name, className, type, remark, status);
    }

    @ApiOperation(value = "获取系统定时任务", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysTimedTaskId", value = "系统定时任务ID", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @GetMapping(path = "/{sysTimedTaskId}")
    public SysTimedTask sysTimedTask(@PathVariable(name = "code") String sysTimedTaskId) {
        return sysTimedTaskService.getSysTimedTask(sysTimedTaskId);
    }

    @ApiOperation(value = "获取系统定时任务", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "系统定时任务标识", required = true, dataTypeClass = String.class, paramType = "path")
    })
    @GetMapping(path = "/{code}/byCode")
    public SysTimedTask getSysTimedTaskByCode(@PathVariable(name = "code") String code) {
        return sysTimedTaskService.getSysTimedTaskByCode(code);
    }

    @ApiOperation(value = "添加系统定时任务", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "系统定时任务标识", required = true, dataTypeClass = String.class, paramType = "form"),
            @ApiImplicitParam(name = "name", value = "系统定时任务名称", required = true, dataTypeClass = String.class, paramType = "form"),
            @ApiImplicitParam(name = "jobClassName", value = "系统定时任务CLASSNAME", required = true, dataTypeClass = String.class, paramType = "form"),
            @ApiImplicitParam(name = "cronExpression", value = "系统定时任务CRON表达式", required = true, dataTypeClass = String.class, paramType = "form"),
            @ApiImplicitParam(name = "data", value = "系统定时任务数据参数", required = true, dataTypeClass = String.class, paramType = "form"),
            @ApiImplicitParam(name = "type", value = "系统定时任务类型", required = true, dataTypeClass = SysTimedTaskTypeEnum.class, paramType = "form"),
            @ApiImplicitParam(name = "remark", value = "系统定时任务备注", required = true, dataTypeClass = String.class, paramType = "form"),
            @ApiImplicitParam(name = "status", value = "系统定时任务状态", required = true, dataTypeClass = StatusEnum.class, paramType = "form"),
    })
    @ApiResponse(code = 200, message = "成功", response = R.class)
    @PostMapping
    public R addSysTimedTask(@RequestParam(name = "code") String code,
                             @RequestParam(name = "name") String name,
                             @RequestParam(name = "jobClassName") String jobClassName,
                             @RequestParam(name = "cronExpression") String cronExpression,
                             @RequestParam(name = "data", required = false) String data,
                             @RequestParam(name = "type", required = false) SysTimedTaskTypeEnum sysTimedTaskTypeEnum,
                             @RequestParam(name = "remark", required = false) String remark,
                             @RequestParam(name = "status", required = false) StatusEnum statusEnum) {
        String sysUserId = SecurityHelper.getSysUserId();
        sysTimedTaskService.createSysTimedTask(code, name, jobClassName, cronExpression, data, sysTimedTaskTypeEnum, remark, statusEnum, sysUserId);
        return R.msg("success");
    }

    @ApiOperation(value = "创建系统定时任务", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "SysTimedTaskInputDTO", value = "系统定时任务信息", required = true, dataTypeClass = SysTimedTaskInputDTO.class, paramType = "body"),
    })
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
    public void createSysTimedTask(@RequestBody() SysTimedTaskInputDTO sysTimedTaskInputDTO) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        sysTimedTaskService.createSysTimedTask(sysTimedTaskInputDTO.getCode(), sysTimedTaskInputDTO.getName(), sysTimedTaskInputDTO.getClassName(), sysTimedTaskInputDTO.getCronExpression(),
                sysTimedTaskInputDTO.getData(), sysTimedTaskInputDTO.getType(), sysTimedTaskInputDTO.getRemark(), sysTimedTaskInputDTO.getStatus(), operatedSysUserId);
    }


    @ApiOperation(value = "修改系统定时任务", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysTimedTaskId", value = "系统定时任务ID", required = true, dataTypeClass = String.class, paramType = "path"),
            @ApiImplicitParam(name = "code", value = "系统定时任务标识", required = true, dataTypeClass = String.class, paramType = "form"),
            @ApiImplicitParam(name = "name", value = "系统定时任务名称", required = true, dataTypeClass = String.class, paramType = "form"),
            @ApiImplicitParam(name = "jobClassName", value = "系统定时任务CLASSNAME", required = true, dataTypeClass = String.class, paramType = "form"),
            @ApiImplicitParam(name = "cronExpression", value = "系统定时任务CRON表达式", required = true, dataTypeClass = String.class, paramType = "form"),
            @ApiImplicitParam(name = "data", value = "系统定时任务数据参数", required = true, dataTypeClass = String.class, paramType = "form"),
            @ApiImplicitParam(name = "type", value = "系统定时任务类型", required = true, dataTypeClass = SysTimedTaskTypeEnum.class, paramType = "form"),
            @ApiImplicitParam(name = "remark", value = "系统定时任务备注", required = true, dataTypeClass = String.class, paramType = "form"),
            @ApiImplicitParam(name = "status", value = "系统定时任务状态", required = true, dataTypeClass = StatusEnum.class, paramType = "form"),
    })
    @PutMapping("/{sysTimedTaskId}")
    @ResponseBody
    public R updateSysTimedTask(
            @PathVariable(name = "sysTimedTaskId") String sysTimedTaskId,
            @PathVariable(name = "code") String code,
            @RequestParam(name = "name") String name,
            @RequestParam(name = "jobClassName") String jobClassName,
            @RequestParam(name = "cronExpression") String cronExpression,
            @RequestParam(name = "data", required = false) String data,
            @RequestParam(name = "type", required = false) SysTimedTaskTypeEnum sysTimedTaskTypeEnum,
            @RequestParam(name = "remark", required = false) String remark,
            @RequestParam(name = "status", required = false) StatusEnum statusEnum) {
        String sysUserId = SecurityHelper.getSysUserId();
        sysTimedTaskService.updateSysTimedTask(sysTimedTaskId, code, name, jobClassName, cronExpression, data, sysTimedTaskTypeEnum, remark, statusEnum, sysUserId);
        return R.msg("success");
    }

    @ApiOperation(value = "修改系统定时任务", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysTimedTaskId", value = "系统定时任务ID", required = true, dataTypeClass = String.class, paramType = "path"),
            @ApiImplicitParam(name = "SysTimedTaskInputDTO", value = "系统定时任务信息", required = true, dataTypeClass = SysTimedTaskInputDTO.class, paramType = "body"),
    })
    @PutMapping(path = "/{sysTimedTaskId}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
    public void updateSysTimedTask(@PathVariable("sysTimedTaskId") String sysTimedTaskId,
                                   @RequestBody() SysTimedTaskInputDTO sysTimedTaskInputDTO) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        sysTimedTaskService.updateSysTimedTask(sysTimedTaskId, sysTimedTaskInputDTO.getCode(), sysTimedTaskInputDTO.getName(), sysTimedTaskInputDTO.getClassName(), sysTimedTaskInputDTO.getCronExpression(),
                sysTimedTaskInputDTO.getData(), sysTimedTaskInputDTO.getType(), sysTimedTaskInputDTO.getRemark(), sysTimedTaskInputDTO.getStatus(), operatedSysUserId);
    }

    @ApiOperation(value = "删除系统定时任务", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysTimedTaskId", value = "系统定时任务ID", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @DeleteMapping("/{sysTimedTaskId}")
    @ResponseBody
    public R deleteSysTimedTask(@PathVariable(name = "sysTimedTaskId") String sysTimedTaskId) {
        sysTimedTaskService.deleteSysTimedTask(sysTimedTaskId);
        return R.msg("success");
    }

    @ApiOperation(value = "删除系统定时任务", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "系统定时任务标识", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @DeleteMapping("/{code}/byCode")
    @ResponseBody
    public R deleteSysTimedTaskByCode(@PathVariable(name = "code") String code) {
        sysTimedTaskService.deleteSysTimedTaskByCode(code);
        return R.msg("success");
    }

    @ApiOperation(value = "启用系统定时任务", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysTimedTaskId", value = "系统定时任务ID", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @PostMapping("/{sysTimedTaskId}/enable")
    @ResponseBody
    public R enabledSysTimedTask(@PathVariable(name = "sysTimedTaskId") String sysTimedTaskId) {
        String sysUserId = SecurityHelper.getSysUserId();
        sysTimedTaskService.enabledSysTimedTask(sysTimedTaskId, sysUserId);
        return R.msg("success");
    }

    @ApiOperation(value = "启用系统定时任务", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "系统定时任务标识", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @PostMapping("/{code}/enable/byCode")
    @ResponseBody
    public R enabledSysTimedTaskByCode(@PathVariable(name = "code") String code) {
        String sysUserId = SecurityHelper.getSysUserId();
        sysTimedTaskService.enabledSysTimedTaskByCode(code, sysUserId);
        return R.msg("success");
    }

    @ApiOperation(value = "禁用系统定时任务", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysTimedTaskId", value = "系统定时任务ID", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @PostMapping("/{sysTimedTaskId}/disable")
    @ResponseBody
    public R disableSysTimedTask(@PathVariable(name = "sysTimedTaskId") String sysTimedTaskId) {
        String sysUserId = SecurityHelper.getSysUserId();
        sysTimedTaskService.disableSysTimedTask(sysTimedTaskId, sysUserId);
        return R.msg("success");
    }

    @ApiOperation(value = "禁用系统定时任务", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "系统定时任务标识", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @PostMapping("/{code}/disable/byCode")
    @ResponseBody
    public R disableSysTimedTaskByCode(@PathVariable(name = "code") String code) {
        String sysUserId = SecurityHelper.getSysUserId();
        sysTimedTaskService.disableSysTimedTaskByCode(code, sysUserId);
        return R.msg("success");
    }

    @ApiOperation(value = "执行系统定时任务", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysTimedTaskId", value = "系统定时任务ID", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @PostMapping("/{sysTimedTaskId}/execution")
    @ResponseBody
    public R executionSysTimedTask(@PathVariable(name = "sysTimedTaskId") String sysTimedTaskId) {
        sysTimedTaskService.executionSysTimedTask(sysTimedTaskId);
        return R.msg("success");
    }

    @ApiOperation(value = "执行系统定时任务", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "系统定时任务标识", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @PostMapping("/{code}/execution/byCode")
    @ResponseBody
    public R executionSysTimedTaskByCode(@PathVariable(name = "code") String code) {
        sysTimedTaskService.executionSysTimedTaskByCode(code);
        return R.msg("success");
    }


}

