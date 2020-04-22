package com.chen.f.admin.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.chen.f.admin.configuration.security.SecurityHelper;
import com.chen.f.admin.web.dto.SysApisInputDTO;
import com.chen.f.admin.web.dto.SysMenusInputDTO;
import com.chen.f.common.pojo.SysApi;
import com.chen.f.common.pojo.SysMenu;
import com.chen.f.common.pojo.SysPermission;
import com.chen.f.common.pojo.enums.StatusEnum;
import com.chen.f.common.pojo.enums.SysPermissionTypeEnum;
import com.chen.f.common.service.ISysPermissionService;
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
 * 系统权限表 前端控制器
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
            @ApiImplicitParam(name = "code", value = "系统权限编码", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "name", value = "系统权限名称", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "type", value = "系统权限类型", required = false, dataTypeClass = SysPermissionTypeEnum.class, paramType = "query"),
            @ApiImplicitParam(name = "remark", value = "系统权限备注", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "status", value = "系统权限状态", required = false, dataTypeClass = StatusEnum.class, paramType = "query"),
    })
    @GetMapping
    public IPage<SysPermission> getSysPermissionPage(@RequestParam(name = "pageIndex", defaultValue = "1") Long pageIndex,
                                                     @RequestParam(name = "pageNumber", defaultValue = "10") Long pageNumber,
                                                     @RequestParam(name = "code", required = false) String code,
                                                     @RequestParam(name = "name", required = false) String name,
                                                     @RequestParam(name = "type", required = false) SysPermissionTypeEnum type,
                                                     @RequestParam(name = "remark", required = false) String remark,
                                                     @RequestParam(name = "status", required = false) StatusEnum status) {
        return sysPermissionService.getSysPermissionPage(pageIndex, pageNumber, code, name, type, remark, status);
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
            @ApiImplicitParam(name = "sysPermissionId", value = "系统权限ID", required = true, dataTypeClass = String.class, paramType = "path")
    })
    @GetMapping("/{sysPermissionId}")
    public SysPermission getSysPermission(@PathVariable("sysPermissionId") String sysPermissionId) {
        return sysPermissionService.getSysPermission(sysPermissionId);
    }
    
    @ApiOperation(value = "获取系统权限的系统菜单列表", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysPermissionId", value = "系统权限ID", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @GetMapping("/menu/{sysPermissionId}")
    public List<SysMenu> getEnabledSysMenuListBySysPermissionIdList(@PathVariable("sysPermissionId") String sysPermissionId) {
        return sysPermissionService.getSysMenuOfSysPermission(sysPermissionId);
    }
    
    @ApiOperation(value = "获取系统权限的系统接口列表", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysPermissionId", value = "系统权限ID", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @GetMapping("/api/{sysPermissionId}")
    public List<SysApi> getSysApiOfSysPermission(@PathVariable("sysPermissionId") String sysPermissionId) {
        return sysPermissionService.getSysApiOfSysPermission(sysPermissionId);
    }

    @ApiOperation(value = "创建系统权限", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "系统权限编码", required = false, dataTypeClass = String.class, paramType = "from"),
            @ApiImplicitParam(name = "name", value = "系统权限名称", required = false, dataTypeClass = String.class, paramType = "from"),
            @ApiImplicitParam(name = "type", value = "系统权限类型", required = false, dataTypeClass = SysPermissionTypeEnum.class, paramType = "from"),
            @ApiImplicitParam(name = "remark", value = "系统权限备注", required = false, dataTypeClass = String.class, paramType = "from"),
            @ApiImplicitParam(name = "status", value = "系统权限状态", required = false, dataTypeClass = StatusEnum.class, paramType = "from"),
    })
    @PostMapping
    public void createSysPermission(@RequestParam(name = "code") String code,
                                    @RequestParam(name = "name") String name,
                                    @RequestParam(name = "type") SysPermissionTypeEnum type,
                                    @RequestParam(name = "remark", required = false) String remark,
                                    @RequestParam(name = "status") StatusEnum status) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        sysPermissionService.createSysPermission(code, name, type, remark, status, operatedSysUserId);
    }

    @ApiOperation(value = "创建系统权限", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "SysPermission", value = "系统权限", required = true, dataTypeClass = SysPermission.class, paramType = "body"),
    })
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void createSysPermission(@RequestBody() SysPermission sysPermission) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        sysPermissionService.createSysPermission(sysPermission.getCode(), sysPermission.getName(), sysPermission.getType(), sysPermission.getRemark(), sysPermission.getStatus(), operatedSysUserId);
    }

    @ApiOperation(value = "修改系统权限", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysPermissionId", value = "修改的系统权限ID", required = true, dataTypeClass = String.class, paramType = "path"),
            @ApiImplicitParam(name = "code", value = "系统权限编码", required = false, dataTypeClass = String.class, paramType = "from"),
            @ApiImplicitParam(name = "name", value = "系统权限名称", required = false, dataTypeClass = String.class, paramType = "from"),
            @ApiImplicitParam(name = "type", value = "系统权限类型", required = false, dataTypeClass = SysPermissionTypeEnum.class, paramType = "from"),
            @ApiImplicitParam(name = "remark", value = "系统权限备注", required = false, dataTypeClass = String.class, paramType = "from"),
            @ApiImplicitParam(name = "status", value = "系统权限状态", required = false, dataTypeClass = StatusEnum.class, paramType = "from"),
    })
    @PutMapping("/{sysPermissionId}")
    public void updateSysPermission(@PathVariable("sysPermissionId") String sysPermissionId,
                                    @RequestParam(name = "code") String code,
                                    @RequestParam(name = "name") String name,
                                    @RequestParam(name = "type") SysPermissionTypeEnum type,
                                    @RequestParam(name = "remark", required = false) String remark,
                                    @RequestParam(name = "status") StatusEnum status) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        sysPermissionService.updateSysPermission(sysPermissionId,code, name, type, remark, status, operatedSysUserId);
    }


    @ApiOperation(value = "修改系统权限", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysPermissionId", value = "修改的系统权限ID", required = true, dataTypeClass = String.class, paramType = "path"),
            @ApiImplicitParam(name = "SysPermission", value = "系统权限", required = true, dataTypeClass = SysPermission.class, paramType = "body"),
    })
    @PutMapping(path = "/{sysPermissionId}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void updateSysPermission(@PathVariable("sysPermissionId") String sysPermissionId,
                                    @RequestBody() SysPermission sysPermission) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        sysPermissionService.updateSysPermission(sysPermissionId, sysPermission.getCode(), sysPermission.getName(), sysPermission.getType(), sysPermission.getRemark(), sysPermission.getStatus(), operatedSysUserId);
    }

    @ApiOperation(value = "设置系统接口", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysPermissionId", value = "修改的系统权限ID", required = true, dataTypeClass = String.class, paramType = "path"),
            @ApiImplicitParam(name = "SysApisInputDTO", value = "设置的系统接口", required = true, dataTypeClass = SysApisInputDTO.class, paramType = "body"),
    })
    @PutMapping(path = "/{sysPermissionId}/setSysApi", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void setSysApiOfSysRole(@PathVariable("sysPermissionId") String sysPermissionId,
                                   @RequestBody() SysApisInputDTO sysApisInputDTO) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        sysPermissionService.setSysApiOfSysPermission(sysPermissionId, sysApisInputDTO.getSysApiIdList(), operatedSysUserId);
    }

    @ApiOperation(value = "设置系统菜单", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysPermissionId", value = "修改的系统权限ID", required = true, dataTypeClass = String.class, paramType = "path"),
            @ApiImplicitParam(name = "SysMenusInputDTO", value = "设置的系统菜单", required = true, dataTypeClass = SysApisInputDTO.class, paramType = "body"),
    })
    @PutMapping(path = "/{sysPermissionId}/setSysMenu", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void setSysApiOfSysRole(@PathVariable("sysPermissionId") String sysPermissionId,
                                   @RequestBody() SysMenusInputDTO sysMenusInputDTO) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        sysPermissionService.setSysMenuOfSysPermission(sysPermissionId, sysMenusInputDTO.getSysMenuIdList(), operatedSysUserId);
    }

    @ApiOperation(value = "删除系统权限", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysPermissionId", value = "删除的系统权限ID", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @DeleteMapping("/{sysPermissionId}")
    public void deleteSysPermission(@PathVariable("sysPermissionId") String sysPermissionId) {
        sysPermissionService.deleteSysPermission(sysPermissionId);
    }

    @ApiOperation(value = "启用系统权限", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysPermissionId", value = "系统权限ID", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @PostMapping("/{sysPermissionId}/enable")
    public void enabledSysPermission(@PathVariable("sysPermissionId") String sysPermissionId) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        sysPermissionService.enabledSysPermission(sysPermissionId, operatedSysUserId);
    }

    @ApiOperation(value = "禁用系统权限", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysPermissionId", value = "系统权限ID", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @PostMapping("/{sysPermissionId}/disable")
    public void disableSysPermission(@PathVariable("sysPermissionId") String sysPermissionId) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        sysPermissionService.disableSysPermission(sysPermissionId, operatedSysUserId);
    }

}

