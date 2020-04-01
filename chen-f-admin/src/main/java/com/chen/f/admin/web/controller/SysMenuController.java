package com.chen.f.admin.web.controller;

import com.chen.f.admin.configuration.helper.SecurityHelper;
import com.chen.f.admin.service.ISysMenuService;
import com.chen.f.admin.web.dto.SysMenuInputDTO;
import com.chen.f.common.pojo.SysMenu;
import com.chen.f.common.pojo.enums.StatusEnum;
import com.chen.f.common.pojo.enums.SysMenuTypeEnum;
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


    @ApiOperation(value = "获取系统菜单", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysMenuId", value = "系统菜单id", required = true, dataTypeClass = String.class, paramType = "path")
    })
    @GetMapping("/{sysMenuId}")
    public SysMenu getSysMenu(@PathVariable("sysRoleId") String sysMenuId) {
        return sysMenuService.getSysMenu(sysMenuId);
    }

    @ApiOperation(value = "创建系统菜单", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "parentId", value = "父级菜单ID", required = false, dataTypeClass = String.class, paramType = "from"),
            @ApiImplicitParam(name = "name", value = "名称", required = true, dataTypeClass = String.class, paramType = "from"),
            @ApiImplicitParam(name = "url", value = "URL", required = true, dataTypeClass = String.class, paramType = "from"),
            @ApiImplicitParam(name = "icon", value = "图标", required = false, dataTypeClass = String.class, paramType = "from"),
            @ApiImplicitParam(name = "type", value = "类型", required = true, dataTypeClass = SysMenuTypeEnum.class, paramType = "from"),
            @ApiImplicitParam(name = "remark", value = "备注", required = false, dataTypeClass = String.class, paramType = "from"),
            @ApiImplicitParam(name = "order", value = "顺序", required = false, dataTypeClass = Integer.class, paramType = "from"),
            @ApiImplicitParam(name = "status", value = "状态", required = true, dataTypeClass = StatusEnum.class, paramType = "from"),
    })
    @PostMapping
    public void createSysMenu(
            @RequestParam(name = "parentId", required = false) String parentId,
            @RequestParam(name = "name") String name,
            @RequestParam(name = "url") String url,
            @RequestParam(name = "icon", required = false) String icon,
            @RequestParam(name = "type") SysMenuTypeEnum type,
            @RequestParam(name = "remark", required = false) String remark,
            @RequestParam(name = "order", required = false) Integer order,
            @RequestParam(name = "status") StatusEnum status) {
        String operatedSysRoleId = SecurityHelper.getSysUserId();
        sysMenuService.createSysMenu(parentId, name, url, icon, type, remark, order, status, operatedSysRoleId);
    }

    @ApiOperation(value = "创建系统菜单", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "SysMenuInputDTO", value = "系统菜单", required = true, dataTypeClass = SysMenuInputDTO.class, paramType = "body"),
    })
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
    public void createSysMenu(@RequestBody() SysMenuInputDTO sysMenuInputDTO) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        sysMenuService.createSysMenu(sysMenuInputDTO.getParentId(), sysMenuInputDTO.getName(), sysMenuInputDTO.getUrl(), sysMenuInputDTO.getIcon(), sysMenuInputDTO.getType(),
                sysMenuInputDTO.getRemark(), sysMenuInputDTO.getOrder(), sysMenuInputDTO.getStatus(), operatedSysUserId);
    }

    @ApiOperation(value = "修改系统菜单", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysMenuId", value = "修改的系统菜单ID", required = true, dataTypeClass = String.class, paramType = "path"),
            @ApiImplicitParam(name = "parentId", value = "父级菜单ID", required = false, dataTypeClass = String.class, paramType = "from"),
            @ApiImplicitParam(name = "name", value = "名称", required = true, dataTypeClass = String.class, paramType = "from"),
            @ApiImplicitParam(name = "url", value = "URL", required = true, dataTypeClass = String.class, paramType = "from"),
            @ApiImplicitParam(name = "icon", value = "图标", required = false, dataTypeClass = String.class, paramType = "from"),
            @ApiImplicitParam(name = "type", value = "类型", required = true, dataTypeClass = SysMenuTypeEnum.class, paramType = "from"),
            @ApiImplicitParam(name = "remark", value = "备注", required = false, dataTypeClass = String.class, paramType = "from"),
            @ApiImplicitParam(name = "order", value = "顺序", required = false, dataTypeClass = Integer.class, paramType = "from"),
            @ApiImplicitParam(name = "status", value = "状态", required = true, dataTypeClass = StatusEnum.class, paramType = "from"),
    })
    @PutMapping("/{sysMenuId}")
    public void updateSysMenu(@PathVariable("sysMenuId") String sysMenuId,
                              @RequestParam(name = "parentId", required = false) String parentId,
                              @RequestParam(name = "name") String name,
                              @RequestParam(name = "url") String url,
                              @RequestParam(name = "icon", required = false) String icon,
                              @RequestParam(name = "type") SysMenuTypeEnum type,
                              @RequestParam(name = "remark", required = false) String remark,
                              @RequestParam(name = "order", required = false) Integer order,
                              @RequestParam(name = "status") StatusEnum status) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        sysMenuService.updateSysMenu(sysMenuId, parentId, name, url, icon, type, remark, order, status, operatedSysUserId);
    }

    @ApiOperation(value = "修改系统菜单", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysMenuId", value = "修改的系统菜单ID", required = true, dataTypeClass = String.class, paramType = "path"),
            @ApiImplicitParam(name = "SysMenuInputDTO", value = "系统菜单DTO", required = true, dataTypeClass = SysMenuInputDTO.class, paramType = "body"),
    })
    @PutMapping(path = "/{sysMenuId}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
    public void updateSysMenu(@PathVariable("sysMenuId") String sysMenuId,
                              @RequestBody() SysMenuInputDTO sysMenuInputDTO) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        sysMenuService.updateSysMenu(sysMenuId, sysMenuInputDTO.getParentId(), sysMenuInputDTO.getName(), sysMenuInputDTO.getUrl(), sysMenuInputDTO.getIcon(),
                sysMenuInputDTO.getType(), sysMenuInputDTO.getRemark(), sysMenuInputDTO.getOrder(), sysMenuInputDTO.getStatus(), operatedSysUserId);
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

