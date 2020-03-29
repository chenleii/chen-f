package com.chen.f.admin.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.chen.f.admin.configuration.helper.SecurityHelper;
import com.chen.f.admin.service.ISysPermissionService;
import com.chen.f.admin.web.dto.SysApisInputDTO;
import com.chen.f.admin.web.dto.SysPermissionInputDTO;
import com.chen.f.common.pojo.SysPermission;
import com.chen.f.common.pojo.enums.StatusEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 权限表 前端控制器
 * </p>
 *
 * @author chen
 * @since 2018-12-02
 */
@Api(tags = "系统权限接口")
@RestController
@RequestMapping("/chen/admin/sys/permission")
public class SysPermissionController {
    protected static final Logger logger = LoggerFactory.getLogger(SysPermissionController.class);

    @Autowired
    private ISysPermissionService sysPermissionService;

    @ApiOperation(value = "获取分页的系统权限", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageIndex", value = "页数", required = true, dataTypeClass = Long.class, paramType = "query", defaultValue = "1"),
            @ApiImplicitParam(name = "pageNumber", value = "页大小", required = true, dataTypeClass = Long.class, paramType = "query", defaultValue = "10"),
            @ApiImplicitParam(name = "name", value = "系统权限名称", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "remark", value = "系统权限备注", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "status", value = "系统权限状态", required = false, dataTypeClass = StatusEnum.class, paramType = "query"),
    })
    @GetMapping
    public IPage<SysPermission> getSysPermissionPage(@RequestParam(name = "pageIndex", defaultValue = "1") Long pageIndex,
                                                     @RequestParam(name = "pageNumber", defaultValue = "10") Long pageNumber,
                                                     @RequestParam(name = "name", required = false) String name,
                                                     @RequestParam(name = "remark", required = false) String remark,
                                                     @RequestParam(name = "status", required = false) StatusEnum status) {
        return sysPermissionService.getSysPermissionPage(pageIndex, pageNumber, name, remark, status);
    }


    @ApiOperation(value = "获取启用的系统权限", notes = "", produces = "application/json")
    @ApiImplicitParams({
    })
    @GetMapping("/all/enabled")
    public List<SysPermission> getEnabledSysPermissionList() {
        return sysPermissionService.getEnabledSysPermissionList();
    }

    @ApiOperation(value = "获取系统权限", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysPermissionId", value = "系统权限id", required = true, dataTypeClass = String.class, paramType = "path")
    })
    @GetMapping("/{sysPermissionId}")
    public SysPermission getSysPermission(@PathVariable("sysPermissionId") String sysPermissionId) {
        return sysPermissionService.getSysPermission(sysPermissionId);
    }


    @ApiOperation(value = "创建系统权限", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "权限名", required = true, dataTypeClass = String.class, paramType = "from"),
            @ApiImplicitParam(name = "remark", value = "备注", required = true, dataTypeClass = String.class, paramType = "from"),
            @ApiImplicitParam(name = "status", value = "状态", required = true, dataTypeClass = String.class, paramType = "from"),
    })
    @PostMapping
    public void createSysPermission(@RequestParam("name") String name,
                                    @RequestParam("remark") String remark,
                                    @RequestParam("status") StatusEnum status) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        sysPermissionService.createSysPermission(name, remark, status, operatedSysUserId);
    }

    @ApiOperation(value = "创建系统权限", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "SysPermissionInputDTO", value = "系统权限信息", required = true, dataTypeClass = SysPermissionInputDTO.class, paramType = "body"),
    })
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
    public void createSysPermission(@RequestBody() SysPermissionInputDTO sysPermissionInputDTO) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        sysPermissionService.createSysPermission(sysPermissionInputDTO.getName(), sysPermissionInputDTO.getRemark(), sysPermissionInputDTO.getStatus(), operatedSysUserId);
    }

    @ApiOperation(value = "修改系统权限", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysPermissionId", value = "修改的系统权限id", required = true, dataTypeClass = String.class, paramType = "path"),
            @ApiImplicitParam(name = "name", value = "权限名", required = true, dataTypeClass = String.class, paramType = "from"),
            @ApiImplicitParam(name = "remark", value = "备注", required = true, dataTypeClass = String.class, paramType = "from"),
            @ApiImplicitParam(name = "status", value = "状态", required = true, dataTypeClass = String.class, paramType = "from"),
    })
    @PutMapping("/{sysPermissionId}")
    public void updateSysPermission(@PathVariable("sysPermissionId") String sysPermissionId,
                                    @RequestParam("name") String name,
                                    @RequestParam("remark") String remark,
                                    @RequestParam("status") StatusEnum status) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        sysPermissionService.updateSysPermission(sysPermissionId, name, remark, status, operatedSysUserId);
    }


    @ApiOperation(value = "修改系统权限", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysPermissionId", value = "修改的系统权限id", required = true, dataTypeClass = String.class, paramType = "path"),
            @ApiImplicitParam(name = "SysPermissionInputDTO", value = "系统权限信息", required = true, dataTypeClass = SysPermissionInputDTO.class, paramType = "body"),
    })
    @PutMapping(path = "/{sysPermissionId}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
    public void updateSysPermission(@PathVariable("sysPermissionId") String sysPermissionId,
                                    @RequestBody() SysPermissionInputDTO sysPermissionInputDTO) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        sysPermissionService.updateSysPermission(sysPermissionId, sysPermissionInputDTO.getName(), sysPermissionInputDTO.getRemark(), sysPermissionInputDTO.getStatus(), operatedSysUserId);
    }

    @ApiOperation(value = "设置系统API", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysPermissionId", value = "修改的系统权限id", required = true, dataTypeClass = String.class, paramType = "path"),
            @ApiImplicitParam(name = "SysApisInputDTO", value = "设置的系统API", required = true, dataTypeClass = SysApisInputDTO.class, paramType = "body"),
    })
    @PutMapping(path = "/{sysPermissionId}/setSysApi", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
    public void setSysApiOfSysRole(@PathVariable("sysPermissionId") String sysPermissionId,
                                   @RequestBody() SysApisInputDTO sysApisInputDTO) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        sysPermissionService.setSysApiOfSysPermission(sysPermissionId, sysApisInputDTO.getSysApiList(), operatedSysUserId);
    }

    @ApiOperation(value = "删除系统权限", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysPermissionId", value = "删除的系统权限id", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @DeleteMapping("/{sysPermissionId}")
    public void deleteSysPermission(@PathVariable("sysPermissionId") String sysPermissionId) {
        sysPermissionService.deleteSysPermission(sysPermissionId);
    }

    @ApiOperation(value = "启用系统权限", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysPermissionId", value = "系统权限id", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @PostMapping("/{sysPermissionId}/enable")
    public void enabledSysPermission(@PathVariable("sysPermissionId") String sysPermissionId) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        sysPermissionService.enabledSysPermission(sysPermissionId, operatedSysUserId);
    }

    @ApiOperation(value = "禁用系统权限", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysPermissionId", value = "系统权限id", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @PostMapping("/{sysPermissionId}/disable")
    public void disableSysPermission(@PathVariable("sysPermissionId") String sysPermissionId) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        sysPermissionService.disableSysPermission(sysPermissionId, operatedSysUserId);
    }

}

