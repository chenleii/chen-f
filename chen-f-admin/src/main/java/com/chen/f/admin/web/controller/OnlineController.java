package com.chen.f.admin.web.controller;

import com.chen.f.admin.security.Securitys;
import com.chen.f.admin.service.ISysMenuService;
import com.chen.f.common.pojo.SysMenu;
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

import java.util.List;

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

    @ApiOperation(value = "获取在线用户", notes = "", produces = "application/json", response = SysUserRolePermission.class)
    @ApiImplicitParams({})
    @GetMapping("/sysUserRolePermission")
    public SysUserRolePermission getSysUserRolePermission() {
        Securitys.checkFullyAuthenticated();
        return Securitys.getAuthenticationSecurityUserOriginal();
    }


    @ApiOperation(value = "获取在线用户的菜单列表", notes = "", produces = "application/json", response = List.class)
    @ApiImplicitParams({})
    @GetMapping("/menu")
    public List<SysMenu> getOnlineSysUserMenu() {
        Securitys.checkFullyAuthenticated();
        String sysUserId = Securitys.getSysUserId();
        // TODO: 2019/3/20  
        return null;
    }
}
