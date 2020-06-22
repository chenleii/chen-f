package com.chen.f.common.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.f.common.api.response.error.SysRoleErrorResponses;
import com.chen.f.common.api.response.error.SysUserErrorResponses;
import com.chen.f.common.mapper.SysApiMapper;
import com.chen.f.common.mapper.SysMenuMapper;
import com.chen.f.common.mapper.SysOrganizationRoleMapper;
import com.chen.f.common.mapper.SysPermissionMapper;
import com.chen.f.common.mapper.SysRoleApiMapper;
import com.chen.f.common.mapper.SysRoleMapper;
import com.chen.f.common.mapper.SysRoleMenuMapper;
import com.chen.f.common.mapper.SysRolePermissionMapper;
import com.chen.f.common.mapper.SysUserMapper;
import com.chen.f.common.mapper.SysUserRoleMapper;
import com.chen.f.common.pojo.SysApi;
import com.chen.f.common.pojo.SysMenu;
import com.chen.f.common.pojo.SysOrganizationRole;
import com.chen.f.common.pojo.SysPermission;
import com.chen.f.common.pojo.SysRole;
import com.chen.f.common.pojo.SysRoleApi;
import com.chen.f.common.pojo.SysRoleMenu;
import com.chen.f.common.pojo.SysRolePermission;
import com.chen.f.common.pojo.SysUser;
import com.chen.f.common.pojo.SysUserRole;
import com.chen.f.common.pojo.enums.StatusEnum;
import com.chen.f.common.service.ISysRoleService;
import com.chen.f.core.api.ApiAssert;
import com.chen.f.core.api.response.error.ErrorResponse;
import com.chen.f.core.page.Page;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
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
    @Autowired
    private SysApiMapper sysApiMapper;
    @Autowired
    private SysMenuMapper sysMenuMapper;


    @Override
    public List<SysRole> getEnabledSysRoleList() {
        return sysRoleMapper.selectList(Wrappers.<SysRole>lambdaQuery().eq(SysRole::getStatus, StatusEnum.ENABLED));
    }

    @Override
    public Page<SysRole> getSysRolePage(Page<SysRole> page,
                                        String code, String name, String remark, StatusEnum status) {
        LambdaQueryWrapper<SysRole> queryWrapper = Wrappers.<SysRole>lambdaQuery();
        queryWrapper.eq(StringUtils.isNotBlank(code), SysRole::getCode, code);
        queryWrapper.eq(StringUtils.isNotBlank(name), SysRole::getName, name);
        queryWrapper.like(StringUtils.isNotBlank(remark), SysRole::getRemark, remark);
        queryWrapper.eq(Objects.nonNull(status), SysRole::getStatus, status);
        return sysRoleMapper.selectPage(page, queryWrapper);
    }

    @Override
    public SysRole getSysRole(String sysRoleId) {
        ApiAssert.isNotBlank(sysRoleId, SysRoleErrorResponses.sysRoleIdCanNotNull());
        return sysRoleMapper.selectById(sysRoleId);
    }

    @Override
    public List<SysPermission> getSysPermissionOfSysRole(String sysRoleId) {
        ApiAssert.isNotBlank(sysRoleId, SysRoleErrorResponses.sysRoleIdCanNotNull());

        logger.debug("获取系统角色");
        final SysRole sysRole = sysRoleMapper.selectById(sysRoleId);
        ApiAssert.isNotNull(sysRole, SysRoleErrorResponses.sysRoleNotExist());

        return getSysPermissionOfSysRole(Arrays.asList(sysRoleId));
    }

    @Override
    public List<SysPermission> getSysPermissionOfSysRole(List<String> sysRoleIdList) {
        if (CollectionUtils.isEmpty(sysRoleIdList)) {
            //系统角色ID列表为空
            return Collections.emptyList();
        }

        final List<SysRolePermission> sysRolePermissionList = sysRolePermissionMapper.selectList(Wrappers.<SysRolePermission>lambdaQuery().in(SysRolePermission::getSysRoleId, sysRoleIdList));
        if (CollectionUtils.isEmpty(sysRolePermissionList)) {
            return Collections.emptyList();
        }
        final List<String> sysPermissionIdList = sysRolePermissionList.stream()
                .map(SysRolePermission::getSysPermissionId)
                .collect(Collectors.toList());
        return sysPermissionMapper.selectList(Wrappers.<SysPermission>lambdaQuery().in(SysPermission::getId, sysPermissionIdList));
    }

    @Override
    public List<SysMenu> getSysMenuOfSysRole(String sysRoleId) {
        ApiAssert.isNotBlank(sysRoleId, SysRoleErrorResponses.sysRoleIdCanNotNull());

        logger.debug("获取系统角色");
        final SysRole sysRole = sysRoleMapper.selectById(sysRoleId);
        ApiAssert.isNotNull(sysRole, SysRoleErrorResponses.sysRoleNotExist());

        return getSysMenuOfSysRole(Arrays.asList(sysRoleId));
    }

    @Override
    public List<SysMenu> getSysMenuOfSysRole(List<String> sysRoleIdList) {
        if (CollectionUtils.isEmpty(sysRoleIdList)) {
            //系统角色ID列表为空
            return Collections.emptyList();
        }

        final List<SysRoleMenu> sysRoleMenuList = sysRoleMenuMapper.selectList(Wrappers.<SysRoleMenu>lambdaQuery().in(SysRoleMenu::getSysRoleId, sysRoleIdList));
        if (CollectionUtils.isEmpty(sysRoleMenuList)) {
            return Collections.emptyList();
        }
        final List<String> sysMenuIdList = sysRoleMenuList.stream()
                .map(SysRoleMenu::getSysMenuId)
                .collect(Collectors.toList());
        return sysMenuMapper.selectList(Wrappers.<SysMenu>lambdaQuery().in(SysMenu::getId, sysMenuIdList).orderByAsc(SysMenu::getOrder));
    }

    @Override
    public List<SysApi> getSysApiOfSysRole(String sysRoleId) {
        ApiAssert.isNotBlank(sysRoleId, SysRoleErrorResponses.sysRoleIdCanNotNull());

        logger.debug("获取系统角色");
        final SysRole sysRole = sysRoleMapper.selectById(sysRoleId);
        ApiAssert.isNotNull(sysRole, SysRoleErrorResponses.sysRoleNotExist());

        return getSysApiOfSysRole(Arrays.asList(sysRoleId));
    }

    @Override
    public List<SysApi> getSysApiOfSysRole(List<String> sysRoleIdList) {
        if (CollectionUtils.isEmpty(sysRoleIdList)) {
            return Collections.emptyList();
        }

        List<SysRoleApi> sysRoleApiList = sysRoleApiMapper.selectList(Wrappers.<SysRoleApi>lambdaQuery().in(SysRoleApi::getSysRoleId, sysRoleIdList));
        if (CollectionUtils.isEmpty(sysRoleApiList)) {
            return Collections.emptyList();
        }

        List<String> sysApiIdList = sysRoleApiList.stream()
                .map(SysRoleApi::getSysApiId)
                .collect(Collectors.toList());
        return sysApiMapper.selectList(Wrappers.<SysApi>lambdaQuery().in(SysApi::getId, sysApiIdList));
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void createSysRole(String code, String name, String remark, StatusEnum status, String operatedSysUserId) {
        ApiAssert.isNotBlank(code, SysRoleErrorResponses.sysRoleCodeCanNotBlank());
        ApiAssert.isNotBlank(name, SysRoleErrorResponses.sysRoleNameCanNotBlank());
        ApiAssert.isNotNull(status, SysRoleErrorResponses.sysRoleStatusCanNotNull());
        ApiAssert.isNotBlank(operatedSysUserId, SysUserErrorResponses.operatedSysUserIdCanNotBlank());

        logger.debug("检查操作的系统用户是否存在");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, SysUserErrorResponses.operatedSysUserNotExist());

        remark = ObjectUtils.defaultIfNull(remark, "");

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
        ApiAssert.isEqualToOne(i, SysRoleErrorResponses.createSysRoleFailure());
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void setSysPermissionOfSysRole(String sysRoleId, List<String> sysPermissionIdList, String operatedSysUserId) {
        ApiAssert.isNotBlank(sysRoleId, SysRoleErrorResponses.sysRoleIdCanNotNull());
        ApiAssert.isNotBlank(operatedSysUserId, SysUserErrorResponses.operatedSysUserIdCanNotBlank());

        logger.debug("检查操作的系统用户是否存在");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, SysUserErrorResponses.operatedSysUserNotExist());

        SysRole sysRole = sysRoleMapper.selectById(sysRoleId);
        ApiAssert.isNotNull(sysRole, SysRoleErrorResponses.sysRoleNotExist());

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
    @Transactional(rollbackFor = Throwable.class)
    public void setSysMenuOfSysRole(String sysRoleId, List<String> sysMenuIdList, String operatedSysUserId) {
        ApiAssert.isNotBlank(sysRoleId, SysRoleErrorResponses.sysRoleIdCanNotNull());
        ApiAssert.isNotBlank(operatedSysUserId, SysUserErrorResponses.operatedSysUserIdCanNotBlank());

        logger.debug("检查操作的系统用户是否存在");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, SysUserErrorResponses.operatedSysUserNotExist());

        SysRole sysRole = sysRoleMapper.selectById(sysRoleId);
        ApiAssert.isNotNull(sysRole, SysRoleErrorResponses.sysRoleNotExist());

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
    @Transactional(rollbackFor = Throwable.class)
    public void setSysApiOfSysRole(String sysRoleId, List<String> sysApiIdList, String operatedSysUserId) {
        ApiAssert.isNotBlank(sysRoleId, SysRoleErrorResponses.sysRoleIdCanNotNull());
        ApiAssert.isNotBlank(operatedSysUserId, SysUserErrorResponses.operatedSysUserIdCanNotBlank());

        logger.debug("检查操作的系统用户是否存在");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, SysUserErrorResponses.operatedSysUserNotExist());

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
    @Transactional(rollbackFor = Throwable.class)
    public void updateSysRole(String sysRoleId, String code, String name, String remark, StatusEnum status, String operatedSysUserId) {
        ApiAssert.isNotBlank(sysRoleId, SysRoleErrorResponses.sysRoleIdCanNotNull());
        ApiAssert.isNotBlank(code, SysRoleErrorResponses.sysRoleCodeCanNotBlank());
        ApiAssert.isNotBlank(name, SysRoleErrorResponses.sysRoleNameCanNotBlank());
        ApiAssert.isNotNull(status, SysRoleErrorResponses.sysRoleStatusCanNotNull());
        ApiAssert.isNotBlank(operatedSysUserId, SysUserErrorResponses.operatedSysUserIdCanNotBlank());

        logger.debug("检查操作的系统用户是否存在");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, SysUserErrorResponses.operatedSysUserNotExist());

        logger.debug("获取系统角色信息");
        SysRole sysRole = sysRoleMapper.selectById(sysRoleId);
        ApiAssert.isNotNull(sysRole, SysRoleErrorResponses.sysRoleNotExist());

        remark = ObjectUtils.defaultIfNull(remark, "");

        logger.debug("修改系统角色[{}]", sysRoleId);
        sysRole.setCode(code);
        sysRole.setName(name);
        sysRole.setRemark(remark);
        sysRole.setStatus(status);
        sysRole.setUpdatedSysUserId(operatedSysUserId);
        sysRole.setUpdatedDateTime(LocalDateTime.now());
        int i = sysRoleMapper.updateById(sysRole);
        ApiAssert.isEqualToOne(i, SysRoleErrorResponses.updateSysRoleFailure());
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void deleteSysRole(String sysRoleId) {
        ApiAssert.isNotBlank(sysRoleId, SysRoleErrorResponses.sysRoleIdCanNotNull());

        logger.debug("检查系统角色是否存在");
        ApiAssert.isEqualToOne(sysRoleMapper.selectCount(Wrappers.<SysRole>lambdaQuery().eq(SysRole::getId, sysRoleId)),
                SysRoleErrorResponses.sysRoleNotExist());

        logger.debug("删除系统角色[{}]", sysRoleId);
        int i = sysRoleMapper.deleteById(sysRoleId);
        ApiAssert.isEqualToOne(i, SysRoleErrorResponses.deleteSysRoleFailure());

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
    @Transactional(rollbackFor = Throwable.class)
    public void enabledSysRole(String sysRoleId, String operatedSysUserId) {
        ApiAssert.isNotBlank(sysRoleId, SysRoleErrorResponses.sysRoleIdCanNotNull());
        ApiAssert.isNotBlank(operatedSysUserId, SysUserErrorResponses.operatedSysUserIdCanNotBlank());

        logger.debug("检查操作的系统用户是否存在");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, SysUserErrorResponses.operatedSysUserNotExist());

        logger.debug("获取系统角色信息");
        SysRole sysRole = sysRoleMapper.selectById(sysRoleId);
        ApiAssert.isNotNull(sysRole, SysRoleErrorResponses.sysRoleNotExist());

        logger.debug("启用系统角色");
        sysRole.setStatus(StatusEnum.ENABLED);
        sysRole.setUpdatedSysUserId(operatedSysUserId);
        sysRole.setUpdatedDateTime(LocalDateTime.now());
        int i = sysRoleMapper.update(sysRole, Wrappers.<SysRole>lambdaQuery().eq(SysRole::getId, sysRoleId).ne(SysRole::getStatus, StatusEnum.ENABLED));
        ApiAssert.isEqualToOne(i, SysRoleErrorResponses.updateSysRoleFailure());
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void disableSysRole(String sysRoleId, String operatedSysUserId) {
        ApiAssert.isNotBlank(sysRoleId, SysRoleErrorResponses.sysRoleIdCanNotNull());
        ApiAssert.isNotBlank(operatedSysUserId, SysUserErrorResponses.operatedSysUserIdCanNotBlank());

        logger.debug("检查操作的系统用户是否存在");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, SysUserErrorResponses.operatedSysUserNotExist());

        logger.debug("获取系统角色信息");
        SysRole sysRole = sysRoleMapper.selectById(sysRoleId);
        ApiAssert.isNotNull(sysRole, ErrorResponse.create(String.format("没有找到系统角色[%s]", sysRoleId)));

        logger.debug("禁用系统角色");
        sysRole.setStatus(StatusEnum.DISABLED);
        sysRole.setUpdatedSysUserId(operatedSysUserId);
        sysRole.setUpdatedDateTime(LocalDateTime.now());
        int i = sysRoleMapper.update(sysRole, Wrappers.<SysRole>lambdaQuery().eq(SysRole::getId, sysRoleId).ne(SysRole::getStatus, StatusEnum.DISABLED));
        ApiAssert.isEqualToOne(i, SysRoleErrorResponses.updateSysRoleFailure());
    }
}
