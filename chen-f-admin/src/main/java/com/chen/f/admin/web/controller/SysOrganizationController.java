package com.chen.f.admin.web.controller;


import com.chen.f.admin.configuration.security.SecurityHelper;
import com.chen.f.admin.service.ISysOrganizationService;
import com.chen.f.admin.web.dto.SysRolesInputDTO;
import com.chen.f.admin.web.dto.SysUsersInputDTO;
import com.chen.f.common.pojo.SysOrganization;
import com.chen.f.common.pojo.SysRole;
import com.chen.f.common.pojo.SysUser;
import com.chen.f.common.pojo.enums.StatusEnum;
import com.chen.f.common.pojo.enums.SysOrganizationTypeEnum;
import io.swagger.annotations.Api;
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
@Api(tags = "系统组织接口")
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


    @ApiOperation(value = "获取系统菜单列表", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "parentId", value = "父级系统组织ID", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "name", value = "系统组织名称", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "fullName", value = "系统组织全称", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "type", value = "系统组织类型", required = false, dataTypeClass = SysOrganizationTypeEnum.class, paramType = "query"),
            @ApiImplicitParam(name = "remark", value = "系统组织备注", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "status", value = "系统组织状态", required = false, dataTypeClass = StatusEnum.class, paramType = "query"),
    })
    @GetMapping("/list")
    public List<SysOrganization> getSysOrganizationList(
            @RequestParam(name = "parentId", required = false) String parentId,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "fullName", required = false) String fullName,
            @RequestParam(name = "type", required = false) SysOrganizationTypeEnum type,
            @RequestParam(name = "remark", required = false) String remark,
            @RequestParam(name = "status", required = false) StatusEnum status) {
        return sysOrganizationService.getSysOrganizationList(parentId, name, fullName, type, remark, status);
    }

    @ApiOperation(value = "获取系统组织", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "SysOrganizationId", value = "系统组织ID", required = true, dataTypeClass = String.class, paramType = "path")
    })
    @GetMapping("/{sysOrganizationId}")
    public SysOrganization getSysOrganization(@PathVariable("sysOrganizationId") String sysOrganizationId) {
        return sysOrganizationService.getSysOrganization(sysOrganizationId);
    }

    @ApiOperation(value = "获取系统组织的系统角色", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysOrganizationId", value = "系统组织ID", required = true, dataTypeClass = String.class, paramType = "path")
    })
    @GetMapping("/{sysOrganizationId}/sysRole")
    public List<SysRole> getSysRoleOfSysOrganization(@PathVariable("sysOrganizationId") String sysOrganizationId) {
        return sysOrganizationService.getSysRoleOfSysOrganization(sysOrganizationId);
    }

    @ApiOperation(value = "获取系统组织的系统用户", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysOrganizationId", value = "系统组织ID", required = true, dataTypeClass = String.class, paramType = "path")
    })
    @GetMapping("/{sysOrganizationId}/sysUser")
    public List<SysUser> getSysUserOfSysOrganization(@PathVariable("sysOrganizationId") String sysOrganizationId) {
        return sysOrganizationService.getSysUserOfSysOrganization(sysOrganizationId);
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


    @ApiOperation(value = "设置系统用户", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysOrganizationId", value = "修改的系统组织ID", required = true, dataTypeClass = String.class, paramType = "path"),
            @ApiImplicitParam(name = "SysUsersInputDTO", value = "设置的系统用户", required = true, dataTypeClass = SysRolesInputDTO.class, paramType = "body"),
    })
    @PutMapping(path = "/{sysOrganizationId}/setSysUser", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void setSysUserOfSysOrganization(@PathVariable("sysOrganizationId") String sysOrganizationId,
                                            @RequestBody() SysUsersInputDTO sysUsersInputDTO) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        sysOrganizationService.setSysUserOfSysOrganization(sysOrganizationId, sysUsersInputDTO.getSysUserIdList(), operatedSysUserId);
    }


    @ApiOperation(value = "设置系统角色", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysOrganizationId", value = "修改的系统组织ID", required = true, dataTypeClass = String.class, paramType = "path"),
            @ApiImplicitParam(name = "SysRolesInputDTO", value = "设置的系统角色", required = true, dataTypeClass = SysRolesInputDTO.class, paramType = "body"),
    })
    @PutMapping(path = "/{sysOrganizationId}/setSysRole", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void setSysRoleOfSysOrganization(@PathVariable("sysOrganizationId") String sysOrganizationId,
                                            @RequestBody() SysRolesInputDTO sysRolesInputDTO) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        sysOrganizationService.setSysRoleOfSysOrganization(sysOrganizationId, sysRolesInputDTO.getSysRoleIdList(), operatedSysUserId);
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
            @ApiImplicitParam(name = "sysOrganizationId", value = "删除的系统组织ID", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @DeleteMapping("/{sysOrganizationId}")
    public void deleteSysOrganization(@PathVariable("sysOrganizationId") String sysOrganizationId) {
        sysOrganizationService.deleteSysOrganization(sysOrganizationId);
    }

    @ApiOperation(value = "启用系统组织", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "SysOrganizationId", value = "系统组织ID", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @PostMapping("/{sysOrganizationId}/enable")
    public void enabledSysOrganization(@PathVariable("sysOrganizationId") String sysOrganizationId) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        sysOrganizationService.enabledSysOrganization(sysOrganizationId, operatedSysUserId);
    }

    @ApiOperation(value = "禁用系统组织", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "SysOrganizationId", value = "系统组织ID", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @PostMapping("/{sysOrganizationId}/disable")
    public void disableSysOrganization(@PathVariable("sysOrganizationId") String sysOrganizationId) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        sysOrganizationService.disableSysOrganization(sysOrganizationId, operatedSysUserId);
    }
}

