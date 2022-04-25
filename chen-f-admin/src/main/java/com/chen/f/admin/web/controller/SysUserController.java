package com.chen.f.admin.web.controller;


import com.chen.f.admin.configuration.security.SecurityHelper;
import com.chen.f.admin.web.dto.SysOrganizationsInputDTO;
import com.chen.f.admin.web.dto.SysRolesInputDTO;
import com.chen.f.common.pojo.SysOrganization;
import com.chen.f.common.pojo.SysRole;
import com.chen.f.common.pojo.SysUser;
import com.chen.f.common.pojo.enums.SysUserStatusEnum;
import com.chen.f.common.service.ISysUserService;
import com.chen.f.core.page.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
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
 * 系统用户表 前端控制器
 * </p>
 *
 * @author chen
 * @since 2018-12-02
 */
@Api(tags = "系统用户接口")
@RestController
@RequestMapping("/chen/admin/sys/user")
public class SysUserController {
    protected static final Logger logger = LoggerFactory.getLogger(SysUserController.class);

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @ApiOperation(value = "获取所有的系统用户", notes = "", produces = "application/json")
    @ApiImplicitParams({})
    @GetMapping("/all")
    public List<SysUser> getAllSysUserList() {
        return sysUserService.getAllSysUserList();
    }

    @ApiOperation(value = "获取启用的系统用户", notes = "", produces = "application/json")
    @ApiImplicitParams({})
    @GetMapping("/all/enabled")
    public List<SysUser> getEnabledSysOrganizationList() {
        return sysUserService.getEnabledSysUserList();
    }

    @ApiOperation(value = "获取分页的系统用户", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageIndex", value = "当前页数", required = true, dataTypeClass = Long.class, paramType = "query", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", required = true, dataTypeClass = Long.class, paramType = "query", defaultValue = "10"),
            @ApiImplicitParam(name = "sort", value = "排序信息(eg:name1.ascend-name2.descend)", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "username", value = "系统用户名称", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "remark", value = "系统用户描述", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "status", value = "系统用户状态", required = false, dataTypeClass = SysUserStatusEnum.class, paramType = "query"),
            @ApiImplicitParam(name = "level", value = "系统用户级别", required = false, dataTypeClass = Integer.class, paramType = "query"),
    })
    @ApiOperationSupport(ignoreParameters = {"list", "total", "orderList", "optimizeCountSql", "isSearchCount","searchCount", "hitCount",})
    @GetMapping
    public Page<SysUser> getSysUserPage(
            Page<SysUser> page,
            @RequestParam(name = "username", required = false) String username,
            @RequestParam(name = "remark", required = false) String remark,
            @RequestParam(name = "status", required = false) SysUserStatusEnum sysUserStatusEnum,
            @RequestParam(name = "level", required = false) Integer level
    ) {
        return sysUserService.getSysUserPage(page, username, level, remark, sysUserStatusEnum);
    }
    
    @ApiOperation(value = "获取系统用户", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysUserId", value = "系统用户id", required = true, dataTypeClass = String.class, paramType = "path")
    })
    @GetMapping("/{sysUserId}")
    public SysUser getSysUser(@PathVariable("sysUserId") String sysUserId) {
        return sysUserService.getSysUser(sysUserId);
    }
    
    @ApiOperation(value = "获取系统用户的组织列表", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysUserId", value = "系统用户id", required = true, dataTypeClass = String.class, paramType = "path")
    })
    @GetMapping("/{sysUserId}/sysOrganization")
    public List<SysOrganization> getSysOrganizationOfSysUser(@PathVariable("sysUserId") String sysUserId) {
        return sysUserService.getSysOrganizationOfSysUser(sysUserId);
    }
    
    @ApiOperation(value = "获取系统用户的角色列表", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysUserId", value = "系统用户id", required = true, dataTypeClass = String.class, paramType = "path")
    })
    @GetMapping("/{sysUserId}/sysRole")
    public List<SysRole> getRoleOfSysUser(@PathVariable("sysUserId") String sysUserId) {
        return sysUserService.getSysRoleOfSysUser(sysUserId);
    }


    @ApiOperation(value = "创建系统用户", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "系统用户名称", required = true, dataTypeClass = String.class, paramType = "form"),
            @ApiImplicitParam(name = "password", value = "系统用户密码", required = true, dataTypeClass = String.class, paramType = "form"),
            @ApiImplicitParam(name = "remark", value = "系统用户备注", required = true, dataTypeClass = String.class, paramType = "form"),
            @ApiImplicitParam(name = "status", value = "系统用户状态", required = true, dataTypeClass = String.class, paramType = "form"),
            @ApiImplicitParam(name = "level", value = "系统用户等级", required = true, dataTypeClass = String.class, paramType = "form"),
    })
    @PostMapping
    public void createSysUser(@RequestParam("username") String username,
                              @RequestParam("password") String password,
                              @RequestParam("remark") String remark,
                              @RequestParam("status") SysUserStatusEnum status,
                              @RequestParam("level") Integer level) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        if (StringUtils.isNotBlank(password)) {
            password = passwordEncoder.encode(password);
        }
        sysUserService.createSysUser(username, password, level, remark, status, operatedSysUserId);
    }

    @ApiOperation(value = "创建系统用户", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "SysUser", value = "系统用户", required = true, dataTypeClass = SysUser.class, paramType = "body"),
    })
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void createSysUser(@RequestBody() SysUser sysUser) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        if (StringUtils.isNotBlank(sysUser.getPassword())) {
            sysUser.setPassword(passwordEncoder.encode(sysUser.getPassword()));
        }
        sysUserService.createSysUser(sysUser.getUsername(), sysUser.getPassword(), sysUser.getLevel(), sysUser.getRemark(),
                sysUser.getStatus(), operatedSysUserId);
    }

    @ApiOperation(value = "修改系统用户", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysUserId", value = "修改的系统用户ID", required = true, dataTypeClass = String.class, paramType = "path"),
            @ApiImplicitParam(name = "username", value = "系统用户用户名称", required = true, dataTypeClass = String.class, paramType = "form"),
            @ApiImplicitParam(name = "password", value = "系统用户密码", required = true, dataTypeClass = String.class, paramType = "form"),
            @ApiImplicitParam(name = "level", value = "系统用户等级", required = true, dataTypeClass = String.class, paramType = "form"),
            @ApiImplicitParam(name = "remark", value = "系统用户备注", required = true, dataTypeClass = String.class, paramType = "form"),
            @ApiImplicitParam(name = "status", value = "系统用户状态", required = true, dataTypeClass = String.class, paramType = "form"),
    })
    @PutMapping("/{sysUserId}")
    public void updateSysUser(@PathVariable("sysUserId") String sysUserId,
                              @RequestParam("username") String username,
                              @RequestParam("password") String password,
                              @RequestParam("level") Integer level,
                              @RequestParam("remark") String remark,
                              @RequestParam("status") SysUserStatusEnum status) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        if (StringUtils.isNotBlank(password)) {
            password = passwordEncoder.encode(password);
        }
        sysUserService.updateSysUser(sysUserId, username, password, level, remark, status, operatedSysUserId);
    }

    @ApiOperation(value = "修改系统用户", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysUserId", value = "修改的系统用户id", required = true, dataTypeClass = String.class, paramType = "path"),
            @ApiImplicitParam(name = "SysUser", value = "系统用户", required = true, dataTypeClass = SysUser.class, paramType = "body"),
    })
    @PutMapping(path = "/{sysUserId}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void updateSysUser(@PathVariable("sysUserId") String sysUserId,
                              @RequestBody() SysUser sysUser) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        if (StringUtils.isNotBlank(sysUser.getPassword())) {
            sysUser.setPassword(passwordEncoder.encode(sysUser.getPassword()));
        }
        sysUserService.updateSysUser(sysUserId, sysUser.getUsername(), sysUser.getPassword(), sysUser.getLevel(), sysUser.getRemark(),
                sysUser.getStatus(), operatedSysUserId);
    }

    @ApiOperation(value = "设置系统组织", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysUserId", value = "修改的系统用户id", required = true, dataTypeClass = String.class, paramType = "path"),
            @ApiImplicitParam(name = "SysRolesInputDTO", value = "设置的系统组织", required = true, dataTypeClass = SysOrganizationsInputDTO.class, paramType = "body"),
    })
    @PutMapping(path = "/{sysUserId}/setSysOrganization", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void setSysOrganizationOfSysUser(@PathVariable("sysUserId") String sysUserId,
                                    @RequestBody() SysOrganizationsInputDTO sysOrganizationsInputDTO) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        sysUserService.setSysOrganizationOfSysUser(sysUserId, sysOrganizationsInputDTO.getSysOrganizationIdList(), operatedSysUserId);
    }

    @ApiOperation(value = "设置系统角色", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysUserId", value = "修改的系统用户id", required = true, dataTypeClass = String.class, paramType = "path"),
            @ApiImplicitParam(name = "SysRolesInputDTO", value = "设置的系统角色", required = true, dataTypeClass = SysRolesInputDTO.class, paramType = "body"),
    })
    @PutMapping(path = "/{sysUserId}/setSysRole", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void setSysRoleOfSysUser(@PathVariable("sysUserId") String sysUserId,
                                    @RequestBody() SysRolesInputDTO sysRolesInputDTO) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        sysUserService.setSysRoleOfSysUser(sysUserId, sysRolesInputDTO.getSysRoleIdList(), operatedSysUserId);
    }


    @ApiOperation(value = "删除系统用户", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysUserId", value = "删除的系统用户id", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @DeleteMapping("/{sysUserId}")
    public void deleteSysUser(@PathVariable("sysUserId") String sysUserId) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        sysUserService.deleteSysUser(sysUserId, operatedSysUserId);
    }

    @ApiOperation(value = "启用系统用户", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysUserId", value = "系统用户id", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @PostMapping("/{sysUserId}/enable")
    public void enabledSysUser(@PathVariable("sysUserId") String sysUserId) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        sysUserService.enabledSysUser(sysUserId, operatedSysUserId);
    }

    @ApiOperation(value = "禁用系统用户", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysUserId", value = "系统用户id", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @PostMapping("/{sysUserId}/disable")
    public void disableSysUser(@PathVariable("sysUserId") String sysUserId) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        sysUserService.disableSysUser(sysUserId, operatedSysUserId);
    }

    @ApiOperation(value = "锁定系统用户", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysUserId", value = "系统用户id", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @PostMapping("/{sysUserId}/lock")
    public void lockSysUser(@PathVariable("sysUserId") String sysUserId) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        sysUserService.lockSysUser(sysUserId, operatedSysUserId);
    }

    @ApiOperation(value = "过期系统用户", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysUserId", value = "系统用户id", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @PostMapping("/{sysUserId}/expire")
    public void expireSysUser(@PathVariable("sysUserId") String sysUserId) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        sysUserService.expireSysUser(sysUserId, operatedSysUserId);
    }

}

