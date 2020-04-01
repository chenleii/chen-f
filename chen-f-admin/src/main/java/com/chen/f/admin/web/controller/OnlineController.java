package com.chen.f.admin.web.controller;

import com.chen.f.admin.configuration.helper.SecurityHelper;
import com.chen.f.admin.configuration.security.service.SecurityUser;
import com.chen.f.admin.service.ISysMenuService;
import com.chen.f.common.pojo.SysMenu;
import com.chen.f.common.pojo.SysPermission;
import com.chen.f.common.pojo.SysRole;
import com.chen.f.common.pojo.SysUserRolePermission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * @author chen
 * @since 2019/3/20 15:13.
 */
@Api(tags = "在线接口")
@RestController
@RequestMapping("/chen/admin/online")
public class OnlineController {
    protected static final Logger logger = LoggerFactory.getLogger(OnlineController.class);

    @Autowired
    private ISysMenuService sysMenuService;

    @ApiOperation(value = "获取在线系统用户", notes = "", produces = "application/json", response = SysUserRolePermission.class)
    @ApiImplicitParams({})
    @GetMapping("/securityUser")
    public SecurityUser getSysUserRolePermission() {
        SecurityHelper.checkFullyAuthenticated();
        return SecurityHelper.getAuthenticationSecurityUser();
    }


    @ApiOperation(value = "获取在线用户的菜单列表", notes = "", produces = "application/json", response = List.class)
    @ApiImplicitParams({})
    @GetMapping("/sysMenuList")
    public List<SysMenu> getOnlineSysUserMenu() {
        SecurityHelper.checkFullyAuthenticated();

        List<SysMenu> sysMenuList = new ArrayList<>();

        final SecurityUser securityUser = SecurityHelper.getAuthenticationSecurityUser();
        final List<SysRole> sysUserRoleList = securityUser.getSysUserRoleList();
        final List<SysMenu> sysRoleMenuList = sysMenuService.getEnabledSysMenuListBySysRoleIdList(sysUserRoleList.stream().map(SysRole::getId).collect(Collectors.toList()));
        sysMenuList.addAll(sysRoleMenuList);

        final List<SysPermission> sysUserPermissionList = securityUser.getSysUserPermissionList();
        final List<SysMenu> sysPermissionMenuList = sysMenuService.getEnabledSysMenuListBySysPermissionIdList(sysUserPermissionList.stream().map(SysPermission::getId).collect(Collectors.toList()));
        sysMenuList.addAll(sysPermissionMenuList);

        //去重
        final Set<SysMenu> distinctSet = new TreeSet<>(Comparator.comparing(SysMenu::getId));
        sysMenuList = sysMenuList.stream()
                .filter(distinctSet::add)
                .collect(Collectors.toList());
        return sysMenuList;

    }
}
