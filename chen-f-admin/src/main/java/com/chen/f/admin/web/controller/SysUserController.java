package com.chen.f.admin.web.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.chen.f.admin.configuration.security.SecurityHelper;
import com.chen.f.common.service.ISysRoleService;
import com.chen.f.common.service.ISysUserService;
import com.chen.f.admin.web.dto.SysRolesInputDTO;
import com.chen.f.common.pojo.SysRole;
import com.chen.f.common.pojo.SysUser;
import com.chen.f.common.pojo.enums.SysUserStatusEnum;
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
    private ISysRoleService sysRoleService;

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
            @ApiImplicitParam(name = "pageIndex", value = "页数", required = true, dataTypeClass = Long.class, paramType = "query", defaultValue = "1"),
            @ApiImplicitParam(name = "pageNumber", value = "页大小", required = true, dataTypeClass = Long.class, paramType = "query", defaultValue = "10"),
            @ApiImplicitParam(name = "username", value = "系统用户名称", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "remark", value = "系统用户描述", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "status", value = "系统用户状态", required = false, dataTypeClass = SysUserStatusEnum.class, paramType = "query"),
            @ApiImplicitParam(name = "level", value = "系统用户级别", required = false, dataTypeClass = Integer.class, paramType = "query"),
    })
    @GetMapping
    public IPage<SysUser> getSysUserPage(@RequestParam(name = "pageIndex", defaultValue = "1") Long pageIndex,
                                         @RequestParam(name = "pageNumber", defaultValue = "10") Long pageNumber,
                                         @RequestParam(name = "username", required = false) String username,
                                         @RequestParam(name = "remark", required = false) String remark,
                                         @RequestParam(name = "status", required = false) SysUserStatusEnum sysUserStatusEnum,
                                         @RequestParam(name = "level", required = false) Integer level
    ) {
        return sysUserService.getSysUserPage(pageIndex, pageNumber, username, level, remark, sysUserStatusEnum);
    }

    /**
     * @param sysUserId 系统用户id
     * @return 系统用户
     */
    @ApiOperation(value = "获取系统用户", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysUserId", value = "系统用户id", required = true, dataTypeClass = String.class, paramType = "path")
    })
    @GetMapping("/{sysUserId}")
    public SysUser getSysUser(@PathVariable("sysUserId") String sysUserId) {
        return sysUserService.getSysUser(sysUserId);
    }

    /**
     * @param sysUserId 系统用户id
     * @return 系统用户角色集合
     */
    @ApiOperation(value = "获取系统用户的角色", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysUserId", value = "系统用户id", required = true, dataTypeClass = String.class, paramType = "path")
    })
    @GetMapping("/{sysUserId}/sysRole")
    public List<SysRole> getRoleOfSysUser(@PathVariable("sysUserId") String sysUserId) {
        return sysUserService.getSysRoleOfSysUser(sysUserId);
    }


    @ApiOperation(value = "创建系统用户", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "系统用户名称", required = true, dataTypeClass = String.class, paramType = "from"),
            @ApiImplicitParam(name = "password", value = "系统用户密码", required = true, dataTypeClass = String.class, paramType = "from"),
            @ApiImplicitParam(name = "remark", value = "系统用户备注", required = true, dataTypeClass = String.class, paramType = "from"),
            @ApiImplicitParam(name = "status", value = "系统用户状态", required = true, dataTypeClass = String.class, paramType = "from"),
            @ApiImplicitParam(name = "level", value = "系统用户等级", required = true, dataTypeClass = String.class, paramType = "from"),
    })
    @PostMapping
    public void createSysUser(@RequestParam("username") String username,
                              @RequestParam("password") String password,
                              @RequestParam("remark") String remark,
                              @RequestParam("status") SysUserStatusEnum status,
                              @RequestParam("level") Integer level) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        sysUserService.createSysUser(username, password, level, remark, status, operatedSysUserId);
    }

    @ApiOperation(value = "创建系统用户", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "SysUser", value = "系统用户", required = true, dataTypeClass = SysUser.class, paramType = "body"),
    })
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void createSysUser(@RequestBody() SysUser sysUser) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        sysUserService.createSysUser(sysUser.getUsername(), sysUser.getPassword(), sysUser.getLevel(), sysUser.getRemark(),
                sysUser.getStatus(), operatedSysUserId);
    }

    @ApiOperation(value = "修改系统用户", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysUserId", value = "修改的系统用户ID", required = true, dataTypeClass = String.class, paramType = "path"),
            @ApiImplicitParam(name = "username", value = "系统用户用户名称", required = true, dataTypeClass = String.class, paramType = "from"),
            @ApiImplicitParam(name = "password", value = "系统用户密码", required = true, dataTypeClass = String.class, paramType = "from"),
            @ApiImplicitParam(name = "level", value = "系统用户等级", required = true, dataTypeClass = String.class, paramType = "from"),
            @ApiImplicitParam(name = "remark", value = "系统用户备注", required = true, dataTypeClass = String.class, paramType = "from"),
            @ApiImplicitParam(name = "status", value = "系统用户状态", required = true, dataTypeClass = String.class, paramType = "from"),
    })
    @PutMapping("/{sysUserId}")
    public void updateSysUser(@PathVariable("sysUserId") String sysUserId,
                              @RequestParam("username") String username,
                              @RequestParam("password") String password,
                              @RequestParam("level") Integer level,
                              @RequestParam("remark") String remark,
                              @RequestParam("status") SysUserStatusEnum status) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        sysUserService.updateSysUser(sysUserId, username, password, level, remark, status, operatedSysUserId);
    }

    @ApiOperation(value = "修改系统用户", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysUserId", value = "修改的系统用户id", required = true, dataTypeClass = String.class, paramType = "path"),
            @ApiImplicitParam(name = "SysUser", value = "系统用户", required = true, dataTypeClass = SysUser.class, paramType = "body"),
    })
    @PutMapping(path = "/{sysUserId}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void updateSysUser(@PathVariable("sysUserId") String sysUserId,
                              @RequestBody() SysUser sysUserInputDTO) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        sysUserService.updateSysUser(sysUserId, sysUserInputDTO.getUsername(), sysUserInputDTO.getPassword(), sysUserInputDTO.getLevel(), sysUserInputDTO.getRemark(),
                sysUserInputDTO.getStatus(), operatedSysUserId);
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

