package com.chen.f.admin.web.controller;

import com.chen.f.admin.helper.QuartzHelper;
import com.chen.f.common.mapper.SysUserMapper;
import com.chen.f.common.mapper.SysUserRolePermissionMapper;
import com.chen.f.common.pojo.SysUser;
import com.chen.f.common.pojo.SysUserRolePermission;
import com.chen.f.core.api.exception.ApiException;
import com.chen.f.core.api.response.error.ErrorResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chen
 * @date 2018/10/27 12:08.
 */
@Api(tags = "测试接口")
@Controller
@RequestMapping("/test")
public class TestController {

    @Autowired
    private QuartzHelper quartzHelper;
    @Autowired
    private SessionRegistry sessionRegistry;

    @Autowired
    private SysUserRolePermissionMapper sysUserRolePermissionMapper;
    @Autowired
    private SysUserMapper sysUserMapper;

    private Map map = new HashMap<String, String>() {{
        put("dsf", "sdaf");
    }};

    @Data
    static class T {

        @NotBlank(message = "t不能为空")
        private String t;
    }


    @ApiOperation(value = "测试接口1", response = SysUser.class)
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @RequestMapping("/t1")
    @ResponseBody
    public Mono<?> t1(@Valid @RequestBody T t) throws AuthenticationException {
        SysUserRolePermission sysUserRolePermission = sysUserRolePermissionMapper.selectSysUserRolePermissionByUsername(t.getT());
        SysUser sysUser = sysUserMapper.selectById(1);
        return Mono.just(sysUserRolePermission);
    }

    @ApiOperation(value = "测试接口2", response = SysUser.class)
    @PreAuthorize("hasRole('role管理员')")
    @GetMapping("/t2")
    @ResponseBody
    public Mono<?> t2(HttpServletRequest request) throws AuthenticationException {
        throw new ApiException(ErrorResponse.create("dsffff", "ceshicuowu"));
    }

    @ApiOperation(value = "测试接口3", response = SysUser.class)
    @PreAuthorize("hasRole('role管理员') and !T(com.chen.f.admin.security.Securitys).isSuperAdministrator()")
    @GetMapping("/t3")
    @ResponseBody
    public Mono<?> t3(HttpServletRequest request) throws Exception {
        throw new Exception("sss");
    }

}
