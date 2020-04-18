package com.chen.f.admin.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.f.admin.service.ISysRoleService;
import com.chen.f.common.mapper.*;
import com.chen.f.common.pojo.*;
import com.chen.f.common.pojo.enums.StatusEnum;
import com.chen.f.common.api.ApiAssert;
import com.chen.f.common.api.response.error.ErrorResponse;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统角色表 服务实现类
 * </p>
 *
 * @author chen
 * @since 2018-12-02
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {
    protected static final Logger logger = LoggerFactory.getLogger(SysRoleServiceImpl.class);

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    @Autowired
    private SysOrganizationRoleMapper sysOrganizationRoleMapper;
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    @Autowired
    private SysRolePermissionMapper sysRolePermissionMapper;
    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;
    @Autowired
    private SysRoleApiMapper sysRoleApiMapper;
    
    

    @Override
    public IPage<SysRole> getSysRolePage(Long pageIndex, Long pageNumber, String code, String name, String remark, StatusEnum status) {
        LambdaQueryWrapper<SysRole> queryWrapper = Wrappers.<SysRole>lambdaQuery();
        queryWrapper.eq(StringUtils.isNotBlank(code), SysRole::getCode, code);
        queryWrapper.eq(StringUtils.isNotBlank(name), SysRole::getName, name);
        queryWrapper.like(StringUtils.isNotBlank(remark), SysRole::getRemark, remark);
        queryWrapper.eq(Objects.nonNull(status), SysRole::getStatus, status);
        return sysRoleMapper.selectPage(new Page<>(pageIndex, pageNumber), queryWrapper);
    }

    @Override
    public List<SysRole> getEnabledSysRoleList() {
        return sysRoleMapper.selectList(Wrappers.<SysRole>lambdaQuery().eq(SysRole::getStatus, StatusEnum.ENABLED));
    }

    @Override
    public SysRole getSysRole(String sysRoleId) {
        ApiAssert.isNotBlank(sysRoleId, ErrorResponse.create("系统角色ID不能为空"));
        return sysRoleMapper.selectById(sysRoleId);
    }

    @Override
    public List<SysPermission> getSysPermissionOfSysRole(String sysRoleId) {
        ApiAssert.isNotBlank(sysRoleId, ErrorResponse.create("系统角色ID不能为空"));
        List<SysRolePermission> sysRolePermissionList = sysRolePermissionMapper.selectList(
                Wrappers.<SysRolePermission>lambdaQuery().eq(SysRolePermission::getSysRoleId, sysRoleId));
        if (CollectionUtils.isNotEmpty(sysRolePermissionList)) {
            List<String> sysPermissionIdList = sysRolePermissionList.stream()
                    .map(SysRolePermission::getSysPermissionId)
                    .collect(Collectors.toList());
            return sysPermissionMapper.selectBatchIds(sysPermissionIdList);
        }
        logger.debug("系统角色ID[{}],没有对应的系统权限", sysRoleId);
        return Collections.emptyList();
    }

    @Override
    public void createSysRole(String code, String name, String remark, StatusEnum status, String operatedSysUserId) {
        ApiAssert.isNotBlank(name, ErrorResponse.create("系统角色名称不能为空"));
        ApiAssert.isNotNull(status, ErrorResponse.create("系统角色状态不能为空"));
        ApiAssert.isNotBlank(operatedSysUserId, ErrorResponse.create("操作的系统用户ID不能为空"));
        
        logger.debug("检查操作的系统用户是否存在");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, ErrorResponse.create("操作的系统用户不存在"));

        logger.debug("插入系统角色");
        SysRole sysRole = new SysRole();
        sysRole.setCode(code);
        sysRole.setName(name);
        sysRole.setRemark(remark);
        sysRole.setStatus(status);
        sysRole.setUpdatedSysUserId(operatedSysUserId);
        sysRole.setCreatedSysUserId(operatedSysUserId);
        sysRole.setUpdatedDateTime(LocalDateTime.now());
        sysRole.setCreatedDateTime(LocalDateTime.now());
        int i = sysRoleMapper.insert(sysRole);
        ApiAssert.isEqualToOne(i, ErrorResponse.create("创建系统角色失败"));
    }

    @Override
    public void setSysPermissionOfSysRole(String sysRoleId, List<String> sysPermissionIdList, String operatedSysUserId) {
        ApiAssert.isNotBlank(sysRoleId, ErrorResponse.create("设置的系统角色ID不能为空"));
        //ApiAssert.isNotEmpty(sysPermissionIdList, ErrorResponse.create("设置的系统权限ID不能为空"));
        ApiAssert.isNotBlank(operatedSysUserId, ErrorResponse.create("操作系统用户ID不能为空"));

        logger.debug("检查操作的系统用户是否存在");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, ErrorResponse.create("操作的系统用户不存在"));
        
        SysRole sysRole = sysRoleMapper.selectById(sysRoleId);
        ApiAssert.isNotNull(sysRole, ErrorResponse.create("系统角色不存在"));
        

        List<SysRolePermission> sysRolePermissionList = Collections.emptyList();
        if (CollectionUtils.isNotEmpty(sysPermissionIdList)) {
            //转换
            sysRolePermissionList = sysPermissionIdList.stream()
                    .map((sysPermissionId) -> {
                        SysRolePermission sysRolePermission = new SysRolePermission();
                        sysRolePermission.setSysRoleId(sysRoleId);
                        sysRolePermission.setSysPermissionId(sysPermissionId);
                        sysRolePermission.setCreatedSysUserId(operatedSysUserId);
                        sysRolePermission.setCreatedDateTime(LocalDateTime.now());
                        return sysRolePermission;
                    }).collect(Collectors.toList());
        }
        logger.debug("删除系统角色[{}]所有系统权限关联", sysRoleId);
        sysRolePermissionMapper.delete(Wrappers.<SysRolePermission>lambdaQuery().eq(SysRolePermission::getSysRoleId, sysRoleId));
        logger.debug("插入系统角色权限关联");
        sysRolePermissionMapper.insertBatch(sysRolePermissionList);
    }

    @Override
    public void setSysMenuOfSysRole(String sysRoleId, List<String> sysMenuIdList, String operatedSysUserId) {
        ApiAssert.isNotBlank(sysRoleId, ErrorResponse.create("设置的系统角色ID不能为空"));
        //ApiAssert.isNotEmpty(sysMenuIdList, ErrorResponse.create("设置的系统菜单ID不能为空"));
        ApiAssert.isNotBlank(operatedSysUserId, ErrorResponse.create("操作系统用户ID不能为空"));

        logger.debug("检查操作的系统用户是否存在");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, ErrorResponse.create("操作的系统用户不存在"));
        
        SysRole sysRole = sysRoleMapper.selectById(sysRoleId);
        ApiAssert.isNotNull(sysRole, ErrorResponse.create("系统角色不存在"));

        List<SysRoleMenu> sysRoleMenuList = Collections.emptyList();
        if (CollectionUtils.isNotEmpty(sysMenuIdList)) {
            //转换
            sysRoleMenuList = sysMenuIdList.stream()
                    .map((sysMenuId) -> {
                        SysRoleMenu sysRoleMenu = new SysRoleMenu();
                        sysRoleMenu.setSysRoleId(sysRoleId);
                        sysRoleMenu.setSysMenuId(sysMenuId);
                        sysRoleMenu.setCreatedSysUserId(operatedSysUserId);
                        sysRoleMenu.setCreatedDateTime(LocalDateTime.now());
                        return sysRoleMenu;
                    }).collect(Collectors.toList());
        }
        logger.debug("删除系统角色[{}]所有系统菜单关联", sysRoleId);
        sysRoleMenuMapper.delete(Wrappers.<SysRoleMenu>lambdaQuery().eq(SysRoleMenu::getSysRoleId, sysRoleId));
        logger.debug("插入系统角色菜单关联");
        sysRoleMenuMapper.insertBatch(sysRoleMenuList);
    }

    @Override
    public void setSysApiOfSysRole(String sysRoleId, List<String> sysApiIdList, String operatedSysUserId) {
        ApiAssert.isNotBlank(sysRoleId, ErrorResponse.create("设置的系统角色ID不能为空"));
        //ApiAssert.isNotEmpty(sysMenuIdList, ErrorResponse.create("设置的系统接口ID不能为空"));
        ApiAssert.isNotBlank(operatedSysUserId, ErrorResponse.create("操作系统用户ID不能为空"));
        
        logger.debug("检查操作的系统用户是否存在");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, ErrorResponse.create("操作的系统用户不存在"));
        
        SysRole sysRole = sysRoleMapper.selectById(sysRoleId);
        ApiAssert.isNotNull(sysRole, ErrorResponse.create("系统角色不存在"));

        List<SysRoleApi> sysRoleApiList = Collections.emptyList();
        if (CollectionUtils.isNotEmpty(sysApiIdList)) {
            //转换
            sysRoleApiList = sysApiIdList.stream()
                    .map((sysApiId) -> {
                        SysRoleApi sysRoleApi = new SysRoleApi();
                        sysRoleApi.setSysRoleId(sysRoleId);
                        sysRoleApi.setSysApiId(sysApiId);
                        sysRoleApi.setCreatedSysUserId(operatedSysUserId);
                        sysRoleApi.setCreatedDateTime(LocalDateTime.now());
                        return sysRoleApi;
                    }).collect(Collectors.toList());
        }
        logger.debug("删除系统角色[{}]所有系统接口关联", sysRoleId);
        sysRoleApiMapper.delete(Wrappers.<SysRoleApi>lambdaQuery().eq(SysRoleApi::getSysRoleId, sysRoleId));
        logger.debug("插入系统角色接口关联");
        sysRoleApiMapper.insertBatch(sysRoleApiList);
    }

    @Override
    public void updateSysRole(String sysRoleId, String code, String name, String remark, StatusEnum status, String operatedSysUserId) {
        ApiAssert.isNotBlank(sysRoleId, ErrorResponse.create("系统角色ID不能为空"));
        ApiAssert.isNotBlank(name, ErrorResponse.create("系统角色名称不能为空"));
        ApiAssert.isNotNull(status, ErrorResponse.create("系统角色状态不能为空"));
        ApiAssert.isNotBlank(operatedSysUserId, ErrorResponse.create("操作的系统用户ID不能为空"));

        logger.debug("检查操作的系统用户是否存在");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, ErrorResponse.create("操作的系统用户不存在"));
        
        logger.debug("获取系统角色信息");
        SysRole sysRole = sysRoleMapper.selectById(sysRoleId);
        ApiAssert.isNotNull(sysRole, ErrorResponse.create(String.format("没有找到系统角色[%s]", sysRoleId)));
        
        logger.debug("修改系统角色[{}]", sysRoleId);
        sysRole.setCode(code);
        sysRole.setName(name);
        sysRole.setRemark(remark);
        sysRole.setStatus(status);
        sysRole.setUpdatedSysUserId(operatedSysUserId);
        sysRole.setUpdatedDateTime(LocalDateTime.now());
        int i = sysRoleMapper.updateById(sysRole);
        ApiAssert.isEqualToOne(i, ErrorResponse.create("修改系统角色失败"));
    }

    @Override
    public void deleteSysRole(String sysRoleId) {
        ApiAssert.isNotBlank(sysRoleId, ErrorResponse.create("系统角色ID不能为空"));
        
        logger.debug("检查系统角色是否存在");
        ApiAssert.isEqualToOne(sysRoleMapper.selectCount(Wrappers.<SysRole>lambdaQuery().eq(SysRole::getId, sysRoleId)),
                ErrorResponse.create(String.format("没有找到系统角色[%s]", sysRoleId)));
        
        logger.debug("删除系统角色[{}]", sysRoleId);
        int i = sysRoleMapper.deleteById(sysRoleId);
        ApiAssert.isEqualToOne(i, ErrorResponse.create("删除系统角色失败"));
        
        //删除系统组织角色
        sysOrganizationRoleMapper.delete(Wrappers.<SysOrganizationRole>lambdaQuery().eq(SysOrganizationRole::getSysRoleId, sysRoleId));
        
        //删除系统用户角色
        sysUserRoleMapper.delete(Wrappers.<SysUserRole>lambdaQuery().eq(SysUserRole::getSysRoleId, sysRoleId));
        
        //删除系统角色权限
        sysRolePermissionMapper.delete(Wrappers.<SysRolePermission>lambdaQuery().eq(SysRolePermission::getSysRoleId, sysRoleId));
        
        //删除系统角色菜单
        sysRoleMenuMapper.delete(Wrappers.<SysRoleMenu>lambdaQuery().eq(SysRoleMenu::getSysRoleId, sysRoleId));

        //删除系统角色接口
        sysRoleApiMapper.delete(Wrappers.<SysRoleApi>lambdaQuery().eq(SysRoleApi::getSysRoleId, sysRoleId));
    }

    @Override
    public void enabledSysRole(String sysRoleId, String operatedSysUserId) {
        ApiAssert.isNotBlank(sysRoleId, ErrorResponse.create("系统角色ID不能为空"));
        ApiAssert.isNotBlank(operatedSysUserId, ErrorResponse.create("操作的系统用户ID不能为空"));

        logger.debug("检查操作的系统用户是否存在");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, ErrorResponse.create("操作的系统用户不存在"));
        
        logger.debug("获取系统角色信息");
        SysRole sysRole = sysRoleMapper.selectById(sysRoleId);
        ApiAssert.isNotNull(sysRole, ErrorResponse.create(String.format("没有找到系统角色[%s]", sysRoleId)));
        
        logger.debug("启用系统角色");
        sysRole.setStatus(StatusEnum.ENABLED);
        sysRole.setUpdatedSysUserId(operatedSysUserId);
        sysRole.setUpdatedDateTime(LocalDateTime.now());
        int i = sysRoleMapper.update(sysRole, Wrappers.<SysRole>lambdaQuery().eq(SysRole::getId, sysRoleId).ne(SysRole::getStatus, StatusEnum.ENABLED));
        ApiAssert.isEqualToOne(i, ErrorResponse.create("修改系统角色失败"));
    }

    @Override
    public void disableSysRole(String sysRoleId, String operatedSysUserId) {
        ApiAssert.isNotBlank(sysRoleId, ErrorResponse.create("系统角色ID不能为空"));
        ApiAssert.isNotBlank(operatedSysUserId, ErrorResponse.create("操作的系统用户ID不能为空"));

        logger.debug("检查操作的系统用户是否存在");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, ErrorResponse.create("操作的系统用户不存在"));
        
        logger.debug("获取系统角色信息");
        SysRole sysRole = sysRoleMapper.selectById(sysRoleId);
        ApiAssert.isNotNull(sysRole, ErrorResponse.create(String.format("没有找到系统角色[%s]", sysRoleId)));
        
        logger.debug("禁用系统角色");
        sysRole.setStatus(StatusEnum.DISABLED);
        sysRole.setUpdatedSysUserId(operatedSysUserId);
        sysRole.setUpdatedDateTime(LocalDateTime.now());
        int i = sysRoleMapper.update(sysRole, Wrappers.<SysRole>lambdaQuery().eq(SysRole::getId, sysRoleId).ne(SysRole::getStatus, StatusEnum.DISABLED));
        ApiAssert.isEqualToOne(i, ErrorResponse.create("修改系统角色失败"));
    }
}
