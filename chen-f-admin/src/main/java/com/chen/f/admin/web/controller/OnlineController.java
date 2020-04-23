package com.chen.f.admin.web.controller;

import com.chen.f.admin.configuration.security.SecurityHelper;
import com.chen.f.admin.configuration.security.service.LoginUser;
import com.chen.f.common.pojo.SysApi;
import com.chen.f.common.pojo.SysMenu;
import com.chen.f.common.pojo.SysPermission;
import com.chen.f.common.pojo.SysRole;
import com.chen.f.common.pojo.SysUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 在线接口
 * <p>
 * 获取系统用户登录后的信息
 *
 * @author chen
 * @since 2019/3/20 15:13.
 */
@Api(tags = "在线接口")
@RestController
@RequestMapping("/chen/admin/online")
public class OnlineController {
    protected static final Logger logger = LoggerFactory.getLogger(OnlineController.class);


    @ApiOperation(value = "获取在线登录用户", notes = "", produces = "application/json", response = LoginUser.class)
    @ApiImplicitParams({})
    @GetMapping("/loginUser")
    public LoginUser getLoginUser() {
        SecurityHelper.checkFullyAuthenticated();
        return SecurityHelper.getAuthenticationSecurityUser();
    }

    @ApiOperation(value = "获取在线登录用户的系统用户", notes = "", produces = "application/json", response = SysUser.class)
    @ApiImplicitParams({})
    @GetMapping("/loginUser/sysUser")
    public SysUser getOnlineSysUser() {
        SecurityHelper.checkFullyAuthenticated();

        final LoginUser loginUser = SecurityHelper.getAuthenticationSecurityUser();
        return loginUser.getSysUser();
    }

    @ApiOperation(value = "获取在线登录用户的系统用户的角色列表", notes = "", produces = "application/json", response = List.class)
    @ApiImplicitParams({})
    @GetMapping("/loginUser/sysRoleList")
    public List<SysRole> getOnlineSysUserRoleList() {
        SecurityHelper.checkFullyAuthenticated();

        final LoginUser loginUser = SecurityHelper.getAuthenticationSecurityUser();
        return loginUser.getSysUserRoleList();
    }

    @ApiOperation(value = "获取在线登录用户的系统用户的权限列表", notes = "", produces = "application/json", response = List.class)
    @ApiImplicitParams({})
    @GetMapping("/loginUser/sysPermissionList")
    public List<SysPermission> getOnlineSysUserPermissionList() {
        SecurityHelper.checkFullyAuthenticated();

        final LoginUser loginUser = SecurityHelper.getAuthenticationSecurityUser();
        return loginUser.getSysUserPermissionList();
    }


    @ApiOperation(value = "获取在线登录用户的系统用户的菜单列表", notes = "", produces = "application/json", response = List.class)
    @ApiImplicitParams({})
    @GetMapping("/loginUser/sysMenuList")
    public List<SysMenu> getOnlineSysUserMenuList() {
        SecurityHelper.checkFullyAuthenticated();

        final LoginUser loginUser = SecurityHelper.getAuthenticationSecurityUser();
        return loginUser.getSysUserMenuList();
    }

    @ApiOperation(value = "获取在线登录用户的系统用户的接口列表", notes = "", produces = "application/json", response = List.class)
    @ApiImplicitParams({})
    @GetMapping("/loginUser/sysApiList")
    public List<SysApi> getOnlineSysUserApiList() {
        SecurityHelper.checkFullyAuthenticated();

        final LoginUser loginUser = SecurityHelper.getAuthenticationSecurityUser();
        return loginUser.getSysUserApiList();
    }
}
