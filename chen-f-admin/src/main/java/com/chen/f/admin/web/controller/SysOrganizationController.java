package com.chen.f.admin.web.controller;


import com.chen.f.admin.configuration.helper.SecurityHelper;
import com.chen.f.admin.service.ISysOrganizationService;
import com.chen.f.common.pojo.SysOrganization;
import com.chen.f.common.pojo.enums.StatusEnum;
import com.chen.f.common.pojo.enums.SysOrganizationTypeEnum;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 系统组织表 前端控制器
 * </p>
 *
 * @author chen
 * @since 2020-03-25
 */
@RestController
@RequestMapping("/chen/admin/sys/organization")
public class SysOrganizationController {
    protected static final Logger logger = LoggerFactory.getLogger(SysOrganizationController.class);

    @Autowired
    private ISysOrganizationService sysOrganizationService;

    @ApiOperation(value = "获取所有系统组织", notes = "", produces = "application/json")
    @ApiImplicitParams({})
    @GetMapping("/all")
    public List<SysOrganization> getAllSysOrganizationList() {
        return sysOrganizationService.getAllSysOrganizationList();
    }

    @ApiOperation(value = "获取启用的系统组织", notes = "", produces = "application/json")
    @ApiImplicitParams({
    })
    @GetMapping("/all/enabled")
    public List<SysOrganization> getEnabledSysOrganizationList() {
        return sysOrganizationService.getEnabledSysOrganizationList();
    }


    @ApiOperation(value = "获取系统组织", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "SysOrganizationId", value = "系统组织ID", required = true, dataTypeClass = String.class, paramType = "path")
    })
    @GetMapping("/{SysOrganizationId}")
    public SysOrganization getSysOrganization(@PathVariable("sysRoleId") String SysOrganizationId) {
        return sysOrganizationService.getSysOrganization(SysOrganizationId);
    }

    @ApiOperation(value = "创建系统组织", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "parentId", value = "父级系统组织ID", required = false, dataTypeClass = String.class, paramType = "from"),
            @ApiImplicitParam(name = "name", value = "系统组织名称", required = true, dataTypeClass = String.class, paramType = "from"),
            @ApiImplicitParam(name = "fullName", value = "系统组织全称", required = true, dataTypeClass = String.class, paramType = "from"),
            @ApiImplicitParam(name = "type", value = "系统组织类型", required = true, dataTypeClass = SysOrganizationTypeEnum.class, paramType = "from"),
            @ApiImplicitParam(name = "remark", value = "系统组织备注", required = false, dataTypeClass = String.class, paramType = "from"),
            @ApiImplicitParam(name = "status", value = "系统组织状态", required = true, dataTypeClass = StatusEnum.class, paramType = "from"),
    })
    @PostMapping
    public void createSysOrganization(
            @RequestParam(name = "parentId", required = false) String parentId,
            @RequestParam(name = "name") String name,
            @RequestParam(name = "fullName") String fullName,
            @RequestParam(name = "type") SysOrganizationTypeEnum type,
            @RequestParam(name = "remark", required = false) String remark,
            @RequestParam(name = "status") StatusEnum status) {

        String operatedSysUserId = SecurityHelper.getSysUserId();
        sysOrganizationService.createSysOrganization(parentId, name, fullName, type, remark, status, operatedSysUserId);
    }

    @ApiOperation(value = "创建系统组织", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "SysOrganization", value = "系统组织", required = true, dataTypeClass = SysOrganization.class, paramType = "body"),
    })
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void createSysOrganization(@RequestBody() SysOrganization sysOrganization) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        sysOrganizationService.createSysOrganization(sysOrganization.getParentId(), sysOrganization.getName(), sysOrganization.getFullName(),
                sysOrganization.getType(), sysOrganization.getRemark(), sysOrganization.getStatus(), operatedSysUserId);
    }

    @ApiOperation(value = "修改系统组织", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysOrganizationId", value = "修改的系统组织ID", required = true, dataTypeClass = String.class, paramType = "path"),
            @ApiImplicitParam(name = "parentId", value = "父级系统组织ID", required = false, dataTypeClass = String.class, paramType = "from"),
            @ApiImplicitParam(name = "name", value = "系统组织名称", required = true, dataTypeClass = String.class, paramType = "from"),
            @ApiImplicitParam(name = "fullName", value = "系统组织全称", required = true, dataTypeClass = String.class, paramType = "from"),
            @ApiImplicitParam(name = "type", value = "系统组织类型", required = true, dataTypeClass = SysOrganizationTypeEnum.class, paramType = "from"),
            @ApiImplicitParam(name = "remark", value = "系统组织备注", required = false, dataTypeClass = String.class, paramType = "from"),
            @ApiImplicitParam(name = "status", value = "系统组织状态", required = true, dataTypeClass = StatusEnum.class, paramType = "from"),
    })
    @PutMapping("/{sysOrganizationId}")
    public void updateSysOrganization(@PathVariable("sysOrganizationId") String sysOrganizationId,
                                      @RequestParam(name = "parentId", required = false) String parentId,
                                      @RequestParam(name = "name") String name,
                                      @RequestParam(name = "fullName") String fullName,
                                      @RequestParam(name = "type") SysOrganizationTypeEnum type,
                                      @RequestParam(name = "remark", required = false) String remark,
                                      @RequestParam(name = "status") StatusEnum status) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        sysOrganizationService.updateSysOrganization(sysOrganizationId, parentId, name, fullName, type, remark, status, operatedSysUserId);
    }

    @ApiOperation(value = "修改系统组织", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysOrganizationId", value = "修改的系统组织ID", required = true, dataTypeClass = String.class, paramType = "path"),
            @ApiImplicitParam(name = "SysOrganization", value = "系统组织", required = true, dataTypeClass = SysOrganization.class, paramType = "body"),
    })
    @PutMapping(path = "/{sysOrganizationId}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void updateSysOrganization(@PathVariable("sysOrganizationId") String sysOrganizationId,
                                      @RequestBody() SysOrganization sysOrganization) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        sysOrganizationService.updateSysOrganization(sysOrganizationId, sysOrganization.getParentId(), sysOrganization.getName(), sysOrganization.getFullName(),
                sysOrganization.getType(), sysOrganization.getRemark(), sysOrganization.getStatus(), operatedSysUserId);
    }

    @ApiOperation(value = "删除系统组织", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "SysOrganizationId", value = "删除的系统组织ID", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @DeleteMapping("/{SysOrganizationId}")
    public void deleteSysOrganization(@PathVariable("SysOrganizationId") String SysOrganizationId) {
        sysOrganizationService.deleteSysOrganization(SysOrganizationId);
    }

    @ApiOperation(value = "启用系统组织", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "SysOrganizationId", value = "系统组织ID", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @PostMapping("/{SysOrganizationId}/enable")
    public void enabledSysOrganization(@PathVariable("SysOrganizationId") String SysOrganizationId) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        sysOrganizationService.enabledSysOrganization(SysOrganizationId, operatedSysUserId);
    }

    @ApiOperation(value = "禁用系统组织", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "SysOrganizationId", value = "系统组织ID", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @PostMapping("/{SysOrganizationId}/disable")
    public void disableSysOrganization(@PathVariable("SysOrganizationId") String SysOrganizationId) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        sysOrganizationService.disableSysOrganization(SysOrganizationId, operatedSysUserId);
    }
}

