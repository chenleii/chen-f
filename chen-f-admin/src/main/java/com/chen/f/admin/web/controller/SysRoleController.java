package com.chen.f.admin.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.chen.f.admin.configuration.security.SecurityHelper;
import com.chen.f.admin.web.dto.SysApisInputDTO;
import com.chen.f.admin.web.dto.SysMenusInputDTO;
import com.chen.f.admin.web.dto.SysPermissionsInputDTO;
import com.chen.f.common.pojo.SysApi;
import com.chen.f.common.pojo.SysMenu;
import com.chen.f.common.pojo.SysPermission;
import com.chen.f.common.pojo.SysRole;
import com.chen.f.common.pojo.enums.StatusEnum;
import com.chen.f.common.service.ISysRoleService;
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
 * 系统角色表 前端控制器
 * </p>
 *
 * @author chen
 * @since 2018-12-02
 */
@Api(tags = "系统角色接口")
@RestController
@RequestMapping("/chen/admin/sys/role")
public class SysRoleController {
    protected static final Logger logger = LoggerFactory.getLogger(SysRoleController.class);

    @Autowired
    private ISysRoleService sysRoleService;

    @ApiOperation(value = "获取分页的系统角色", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageIndex", value = "页数", required = true, dataTypeClass = Long.class, paramType = "query", defaultValue = "1"),
            @ApiImplicitParam(name = "pageNumber", value = "页大小", required = true, dataTypeClass = Long.class, paramType = "query", defaultValue = "10"),
            @ApiImplicitParam(name = "code", value = "系统角色编码", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "name", value = "系统角色名称", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "remark", value = "系统角色备注", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "status", value = "系统角色状态", required = false, dataTypeClass = StatusEnum.class, paramType = "query"),
    })
    @GetMapping
    public IPage<SysRole> getSysRolePage(@RequestParam(name = "pageIndex", defaultValue = "1") Long pageIndex,
                                         @RequestParam(name = "pageNumber", defaultValue = "10") Long pageNumber,
                                         @RequestParam(name = "code", required = false) String code,
                                         @RequestParam(name = "name", required = false) String name,
                                         @RequestParam(name = "remark", required = false) String remark,
                                         @RequestParam(name = "status", required = false) StatusEnum status) {
        return sysRoleService.getSysRolePage(pageIndex, pageNumber, code, name, remark, status);
    }


    @ApiOperation(value = "获取启用的系统角色", notes = "", produces = "application/json")
    @ApiImplicitParams({
    })
    @GetMapping("/all/enabled")
    public List<SysRole> getEnabledSysRoleList() {
        return sysRoleService.getEnabledSysRoleList();
    }

    @ApiOperation(value = "获取系统角色", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysRoleId", value = "系统角色ID", required = true, dataTypeClass = String.class, paramType = "path")
    })
    @GetMapping("/{sysRoleId}")
    public SysRole getSysRole(@PathVariable("sysRoleId") String sysRoleId) {
        return sysRoleService.getSysRole(sysRoleId);
    }

    @ApiOperation(value = "获取系统角色的系统权限", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysRoleId", value = "系统角色ID", required = true, dataTypeClass = String.class, paramType = "path")
    })
    @GetMapping("/permission/{sysRoleId}")
    public List<SysPermission> getSysPermissionOfSysRole(@PathVariable("sysRoleId") String sysRoleId) {
        return sysRoleService.getSysPermissionOfSysRole(sysRoleId);
    }

    @ApiOperation(value = "获取系统角色的系统菜单列表", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysRoleId", value = "系统角色ID", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @GetMapping("/menu/{sysRoleId}")
    public List<SysMenu> getSysMenuOfSysRole(@PathVariable("sysRoleId") String sysRoleId) {
        return sysRoleService.getSysMenuOfSysRole(sysRoleId);
    }
    
    @ApiOperation(value = "获取系统角色的系统接口列表", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysRoleId", value = "系统角色ID", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @GetMapping("/api/{sysRoleId}")
    public List<SysApi> getSysApiOfSysRole(@PathVariable("sysRoleId") String sysRoleId) {
        return sysRoleService.getSysApiOfSysRole(sysRoleId);
    }

    @ApiOperation(value = "创建系统角色", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "系统角色编码", required = true, dataTypeClass = String.class, paramType = "from"),
            @ApiImplicitParam(name = "name", value = "系统角色名称", required = true, dataTypeClass = String.class, paramType = "from"),
            @ApiImplicitParam(name = "remark", value = "系统角色备注", required = true, dataTypeClass = String.class, paramType = "from"),
            @ApiImplicitParam(name = "status", value = "系统角色状态", required = true, dataTypeClass = StatusEnum.class, paramType = "from"),
    })
    @PostMapping
    public void createSysRole(@RequestParam("code") String code,
                              @RequestParam("name") String name,
                              @RequestParam("remark") String remark,
                              @RequestParam("status") StatusEnum status) {
        String operatedSysRoleId = SecurityHelper.getSysUserId();
        sysRoleService.createSysRole(code, name, remark, status, operatedSysRoleId);
    }

    @ApiOperation(value = "创建系统角色", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "SysRoleInputDTO", value = "系统角色信息", required = true, dataTypeClass = SysRole.class, paramType = "body"),
    })
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void createSysRole(@RequestBody() SysRole sysRole) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        sysRoleService.createSysRole(sysRole.getCode(), sysRole.getName(), sysRole.getRemark(), sysRole.getStatus(), operatedSysUserId);
    }

    @ApiOperation(value = "修改系统角色", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysRoleId", value = "修改的系统角色ID", required = true, dataTypeClass = String.class, paramType = "path"),
            @ApiImplicitParam(name = "code", value = "系统角色编码", required = true, dataTypeClass = String.class, paramType = "from"),
            @ApiImplicitParam(name = "name", value = "系统角色名称", required = true, dataTypeClass = String.class, paramType = "from"),
            @ApiImplicitParam(name = "remark", value = "系统角色备注", required = true, dataTypeClass = String.class, paramType = "from"),
            @ApiImplicitParam(name = "status", value = "系统角色状态", required = true, dataTypeClass = StatusEnum.class, paramType = "from"),
    })
    @PutMapping("/{sysRoleId}")
    public void updateSysRole(@PathVariable("sysRoleId") String sysRoleId,
                              @RequestParam("code") String code,
                              @RequestParam("name") String name,
                              @RequestParam("remark") String remark,
                              @RequestParam("status") StatusEnum status) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        sysRoleService.updateSysRole(sysRoleId, code, name, remark, status, operatedSysUserId);
    }

    @ApiOperation(value = "修改系统角色", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysRoleId", value = "修改的系统角色ID", required = true, dataTypeClass = String.class, paramType = "path"),
            @ApiImplicitParam(name = "SysRoleInputDTO", value = "系统角色信息", required = true, dataTypeClass = SysRole.class, paramType = "body"),
    })
    @PutMapping(path = "/{sysRoleId}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void updateSysRole(@PathVariable("sysRoleId") String sysRoleId,
                              @RequestBody() SysRole sysRole) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        sysRoleService.updateSysRole(sysRoleId, sysRole.getCode(), sysRole.getName(), sysRole.getRemark(), sysRole.getStatus(), operatedSysUserId);
    }

    @ApiOperation(value = "设置系统权限", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysRoleId", value = "修改的系统角色ID", required = true, dataTypeClass = String.class, paramType = "path"),
            @ApiImplicitParam(name = "SysPermissionsInputDTO", value = "设置的系统角色", required = true, dataTypeClass = SysPermissionsInputDTO.class, paramType = "body"),
    })
    @PutMapping(path = "/{sysRoleId}/setSysPermission", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void setSysPermissionOfSysRole(@PathVariable("sysRoleId") String sysRoleId,
                                          @RequestBody() SysPermissionsInputDTO sysPermissionsInputDTO) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        sysRoleService.setSysPermissionOfSysRole(sysRoleId, sysPermissionsInputDTO.getSysPermissionIdList(), operatedSysUserId);
    }

    @ApiOperation(value = "设置系统菜单", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysRoleId", value = "修改的系统角色ID", required = true, dataTypeClass = String.class, paramType = "path"),
            @ApiImplicitParam(name = "SysMenusInputDTO", value = "设置的系统菜单", required = true, dataTypeClass = SysMenusInputDTO.class, paramType = "body"),
    })
    @PutMapping(path = "/{sysRoleId}/setSysMenu", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void setSysMenuOfSysRole(@PathVariable("sysRoleId") String sysRoleId,
                                    @RequestBody() SysMenusInputDTO sysMenusInputDTO) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        sysRoleService.setSysMenuOfSysRole(sysRoleId, sysMenusInputDTO.getSysMenuIdList(), operatedSysUserId);
    }

    @ApiOperation(value = "设置系统接口", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysRoleId", value = "修改的系统角色ID", required = true, dataTypeClass = String.class, paramType = "path"),
            @ApiImplicitParam(name = "SysApisInputDTO", value = "设置的系统接口", required = true, dataTypeClass = SysApisInputDTO.class, paramType = "body"),
    })
    @PutMapping(path = "/{sysRoleId}/setSysApi", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void setSysApiOfSysRole(@PathVariable("sysRoleId") String sysRoleId,
                                   @RequestBody() SysApisInputDTO sysApisInputDTO) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        sysRoleService.setSysApiOfSysRole(sysRoleId, sysApisInputDTO.getSysApiIdList(), operatedSysUserId);
    }

    @ApiOperation(value = "删除系统角色", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysRoleId", value = "删除的系统角色ID", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @DeleteMapping("/{sysRoleId}")
    public void deleteSysRole(@PathVariable("sysRoleId") String sysRoleId) {
        sysRoleService.deleteSysRole(sysRoleId);
    }

    @ApiOperation(value = "启用系统角色", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysRoleId", value = "系统角色ID", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @PostMapping("/{sysRoleId}/enable")
    public void enabledSysRole(@PathVariable("sysRoleId") String sysRoleId) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        sysRoleService.enabledSysRole(sysRoleId, operatedSysUserId);
    }

    @ApiOperation(value = "禁用系统角色", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysRoleId", value = "系统角色ID", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @PostMapping("/{sysRoleId}/disable")
    public void disableSysRole(@PathVariable("sysRoleId") String sysRoleId) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        sysRoleService.disableSysRole(sysRoleId, operatedSysUserId);
    }


}

