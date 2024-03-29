package com.chen.f.admin.web.controller;

import com.chen.f.admin.configuration.security.SecurityHelper;
import com.chen.f.common.pojo.SysMenu;
import com.chen.f.common.pojo.enums.StatusEnum;
import com.chen.f.common.pojo.enums.SysMenuTypeEnum;
import com.chen.f.common.service.ISysMenuService;
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
 * 系统菜单表 前端控制器
 * </p>
 *
 * @author chen
 * @since 2018-12-02
 */
@Api(tags = "系统菜单接口")
@RestController
@RequestMapping("/chen/admin/sys/menu")
public class SysMenuController {
    protected static final Logger logger = LoggerFactory.getLogger(SysMenuController.class);

    @Autowired
    private ISysMenuService sysMenuService;

    @ApiOperation(value = "获取所有系统菜单", notes = "", produces = "application/json")
    @ApiImplicitParams({})
    @GetMapping("/all")
    public List<SysMenu> getAllSysMenuList() {
        return sysMenuService.getAllSysMenuList();
    }

    @ApiOperation(value = "获取启用的系统菜单", notes = "", produces = "application/json")
    @ApiImplicitParams({
    })
    @GetMapping("/all/enabled")
    public List<SysMenu> getEnabledSysMenuList() {
        return sysMenuService.getEnabledSysMenuList();
    }

    @ApiOperation(value = "获取系统菜单列表", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "parentId", value = "父级系统菜单ID", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "name", value = "系统菜单名称", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "url", value = "系统菜单URL", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "type", value = "系统菜单类型", required = false, dataTypeClass = SysMenuTypeEnum.class, paramType = "query"),
            @ApiImplicitParam(name = "remark", value = "系统菜单备注", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "status", value = "系统菜单状态", required = false, dataTypeClass = StatusEnum.class, paramType = "query"),
    })
    @GetMapping("/list")
    public List<SysMenu> getEnabledSysMenuList(
            @RequestParam(name = "parentId", required = false) String parentId,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "url", required = false) String url,
            @RequestParam(name = "type", required = false) SysMenuTypeEnum type,
            @RequestParam(name = "remark", required = false) String remark,
            @RequestParam(name = "status", required = false) StatusEnum status) {
        return sysMenuService.getSysMenuList(parentId, name, url, type, remark, status);
    }
    
    @ApiOperation(value = "获取系统菜单", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysMenuId", value = "系统菜单ID", required = true, dataTypeClass = String.class, paramType = "path")
    })
    @GetMapping("/{sysMenuId}")
    public SysMenu getSysMenu(@PathVariable("sysMenuId") String sysMenuId) {
        return sysMenuService.getSysMenu(sysMenuId);
    }

    @ApiOperation(value = "创建系统菜单", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "parentId", value = "父级系统菜单ID", required = false, dataTypeClass = String.class, paramType = "form"),
            @ApiImplicitParam(name = "name", value = "系统菜单名称", required = true, dataTypeClass = String.class, paramType = "form"),
            @ApiImplicitParam(name = "nameI18n", value = "系统菜单名称的国际化", required = false, dataTypeClass = String.class, paramType = "form"),
            @ApiImplicitParam(name = "url", value = "系统菜单URL", required = true, dataTypeClass = String.class, paramType = "form"),
            @ApiImplicitParam(name = "icon", value = "系统菜单图标", required = false, dataTypeClass = String.class, paramType = "form"),
            @ApiImplicitParam(name = "type", value = "系统菜单类型", required = true, dataTypeClass = SysMenuTypeEnum.class, paramType = "form"),
            @ApiImplicitParam(name = "order", value = "系统菜单顺序", required = false, dataTypeClass = Integer.class, paramType = "form"),
            @ApiImplicitParam(name = "remark", value = "系统菜单备注", required = false, dataTypeClass = String.class, paramType = "form"),
            @ApiImplicitParam(name = "status", value = "系统菜单状态", required = true, dataTypeClass = StatusEnum.class, paramType = "form"),
    })
    @PostMapping
    public void createSysMenu(
            @RequestParam(name = "parentId", required = false) String parentId,
            @RequestParam(name = "name") String name,
            @RequestParam(name = "nameI18n", required = false) String nameI18n,
            @RequestParam(name = "url") String url,
            @RequestParam(name = "icon", required = false) String icon,
            @RequestParam(name = "type") SysMenuTypeEnum type,
            @RequestParam(name = "order", required = false) Integer order,
            @RequestParam(name = "remark", required = false) String remark,
            @RequestParam(name = "status") StatusEnum status) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        sysMenuService.createSysMenu(parentId, name, nameI18n, url, icon, type, order, remark, status, operatedSysUserId);
    }

    @ApiOperation(value = "创建系统菜单", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "SysMenu", value = "系统菜单", required = true, dataTypeClass = SysMenu.class, paramType = "body"),
    })
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void createSysMenu(@RequestBody() SysMenu sysMenu) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        sysMenuService.createSysMenu(sysMenu.getParentId(), sysMenu.getName(), sysMenu.getNameI18n(), sysMenu.getUrl(), sysMenu.getIcon(), sysMenu.getType(),
                sysMenu.getOrder(), sysMenu.getRemark(), sysMenu.getStatus(), operatedSysUserId);
    }

    @ApiOperation(value = "修改系统菜单", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysMenuId", value = "修改的系统菜单ID", required = true, dataTypeClass = String.class, paramType = "path"),
            @ApiImplicitParam(name = "parentId", value = "父级系统菜单ID", required = false, dataTypeClass = String.class, paramType = "form"),
            @ApiImplicitParam(name = "name", value = "系统菜单名称", required = true, dataTypeClass = String.class, paramType = "form"),
            @ApiImplicitParam(name = "nameI18n", value = "系统菜单名称的国际化", required = false, dataTypeClass = String.class, paramType = "form"),
            @ApiImplicitParam(name = "url", value = "系统菜单URL", required = true, dataTypeClass = String.class, paramType = "form"),
            @ApiImplicitParam(name = "icon", value = "系统菜单图标", required = false, dataTypeClass = String.class, paramType = "form"),
            @ApiImplicitParam(name = "type", value = "系统菜单类型", required = true, dataTypeClass = SysMenuTypeEnum.class, paramType = "form"),
            @ApiImplicitParam(name = "order", value = "系统菜单顺序", required = false, dataTypeClass = Integer.class, paramType = "form"),
            @ApiImplicitParam(name = "remark", value = "系统菜单备注", required = false, dataTypeClass = String.class, paramType = "form"),
            @ApiImplicitParam(name = "status", value = "系统菜单状态", required = true, dataTypeClass = StatusEnum.class, paramType = "form"),
    })
    @PutMapping("/{sysMenuId}")
    public void updateSysMenu(@PathVariable("sysMenuId") String sysMenuId,
                              @RequestParam(name = "parentId", required = false) String parentId,
                              @RequestParam(name = "name") String name,
                              @RequestParam(name = "nameI18n", required = false) String nameI18n,
                              @RequestParam(name = "url") String url,
                              @RequestParam(name = "icon", required = false) String icon,
                              @RequestParam(name = "type") SysMenuTypeEnum type,
                              @RequestParam(name = "order", required = false) Integer order,
                              @RequestParam(name = "remark", required = false) String remark,
                              @RequestParam(name = "status") StatusEnum status) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        sysMenuService.updateSysMenu(sysMenuId, parentId, name, nameI18n, url, icon, type, order, remark, status, operatedSysUserId);
    }

    @ApiOperation(value = "修改系统菜单", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysMenuId", value = "修改的系统菜单ID", required = true, dataTypeClass = String.class, paramType = "path"),
            @ApiImplicitParam(name = "SysMenu", value = "系统菜单", required = true, dataTypeClass = SysMenu.class, paramType = "body"),
    })
    @PutMapping(path = "/{sysMenuId}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void updateSysMenu(@PathVariable("sysMenuId") String sysMenuId,
                              @RequestBody() SysMenu sysMenu) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        sysMenuService.updateSysMenu(sysMenuId, sysMenu.getParentId(), sysMenu.getName(), sysMenu.getNameI18n(), sysMenu.getUrl(), sysMenu.getIcon(),
                sysMenu.getType(), sysMenu.getOrder(), sysMenu.getRemark(), sysMenu.getStatus(), operatedSysUserId);
    }

    @ApiOperation(value = "删除系统菜单", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysMenuId", value = "删除的系统菜单ID", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @DeleteMapping("/{sysMenuId}")
    public void deleteSysMenu(@PathVariable("sysMenuId") String sysMenuId) {
        sysMenuService.deleteSysMenu(sysMenuId);
    }

    @ApiOperation(value = "启用系统菜单", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysMenuId", value = "系统菜单ID", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @PostMapping("/{sysMenuId}/enable")
    public void enabledSysMenu(@PathVariable("sysMenuId") String sysMenuId) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        sysMenuService.enabledSysMenu(sysMenuId, operatedSysUserId);
    }

    @ApiOperation(value = "禁用系统菜单", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysMenuId", value = "系统菜单ID", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @PostMapping("/{sysMenuId}/disable")
    public void disableSysMenu(@PathVariable("sysMenuId") String sysMenuId) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        sysMenuService.disableSysMenu(sysMenuId, operatedSysUserId);
    }
}

