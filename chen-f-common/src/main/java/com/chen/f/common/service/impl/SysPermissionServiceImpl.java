package com.chen.f.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.f.common.api.response.error.SysPermissionErrorResponses;
import com.chen.f.common.api.response.error.SysUserErrorResponses;
import com.chen.f.common.mapper.SysApiMapper;
import com.chen.f.common.mapper.SysMenuMapper;
import com.chen.f.common.mapper.SysPermissionApiMapper;
import com.chen.f.common.mapper.SysPermissionMapper;
import com.chen.f.common.mapper.SysPermissionMenuMapper;
import com.chen.f.common.mapper.SysRolePermissionMapper;
import com.chen.f.common.mapper.SysUserMapper;
import com.chen.f.common.pojo.SysApi;
import com.chen.f.common.pojo.SysMenu;
import com.chen.f.common.pojo.SysPermission;
import com.chen.f.common.pojo.SysPermissionApi;
import com.chen.f.common.pojo.SysPermissionMenu;
import com.chen.f.common.pojo.SysRolePermission;
import com.chen.f.common.pojo.SysUser;
import com.chen.f.common.pojo.enums.StatusEnum;
import com.chen.f.common.pojo.enums.SysPermissionTypeEnum;
import com.chen.f.common.service.ISysPermissionService;
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
 * 系统权限表 服务实现类
 * </p>
 *
 * @author chen
 * @since 2018-12-02
 */
@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements ISysPermissionService {
    protected static final Logger logger = LoggerFactory.getLogger(SysPermissionServiceImpl.class);
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    @Autowired
    private SysRolePermissionMapper sysRolePermissionMapper;
    @Autowired
    private SysPermissionApiMapper sysPermissionApiMapper;
    @Autowired
    private SysPermissionMenuMapper sysPermissionMenuMapper;
    @Autowired
    private SysApiMapper sysApiMapper;
    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    public List<SysPermission> getEnabledSysPermissionList() {
        return sysPermissionMapper.selectList(Wrappers.<SysPermission>lambdaQuery().eq(SysPermission::getStatus, StatusEnum.ENABLED));
    }

    @Override
    public Page<SysPermission> getSysPermissionPage(Page<SysPermission> page,
                                                    String code, String name, SysPermissionTypeEnum type, String remark, StatusEnum status) {
        LambdaQueryWrapper<SysPermission> queryWrapper = Wrappers.<SysPermission>lambdaQuery();
        queryWrapper.eq(StringUtils.isNotBlank(code), SysPermission::getCode, code);
        queryWrapper.eq(StringUtils.isNotBlank(name), SysPermission::getName, name);
        queryWrapper.eq(Objects.nonNull(type), SysPermission::getType, type);
        queryWrapper.like(StringUtils.isNotBlank(remark), SysPermission::getRemark, remark);
        queryWrapper.eq(Objects.nonNull(status), SysPermission::getStatus, status);
        return sysPermissionMapper.selectPage(page, queryWrapper);
    }

    @Override
    public SysPermission getSysPermission(String sysPermissionId) {
        ApiAssert.isNotBlank(sysPermissionId, SysPermissionErrorResponses.sysPermissionIdCanNotNull());
        return sysPermissionMapper.selectById(sysPermissionId);
    }

    @Override
    public List<SysMenu> getSysMenuOfSysPermission(String sysPermissionId) {
        ApiAssert.isNotBlank(sysPermissionId, SysPermissionErrorResponses.sysPermissionIdCanNotNull());

        logger.debug("获取系统权限");
        final SysPermission sysPermission = sysPermissionMapper.selectById(sysPermissionId);
        ApiAssert.isNotNull(sysPermission, ErrorResponse.create("系统权限不存在"));

        return getSysMenuOfSysPermission(Arrays.asList(sysPermissionId));
    }

    @Override
    public List<SysMenu> getSysMenuOfSysPermission(List<String> sysPermissionIdList) {
        if (CollectionUtils.isEmpty(sysPermissionIdList)) {
            //系统权限ID列表为空
            return Collections.emptyList();
        }

        final List<SysPermissionMenu> sysPermissionMenuList = sysPermissionMenuMapper.selectList(Wrappers.<SysPermissionMenu>lambdaQuery().in(SysPermissionMenu::getSysPermissionId, sysPermissionIdList));
        if (CollectionUtils.isEmpty(sysPermissionMenuList)) {
            return Collections.emptyList();
        }
        final List<String> sysMenuIdList = sysPermissionMenuList.stream()
                .map(SysPermissionMenu::getSysMenuId)
                .collect(Collectors.toList());
        return sysMenuMapper.selectList(Wrappers.<SysMenu>lambdaQuery().in(SysMenu::getId, sysMenuIdList).orderByAsc(SysMenu::getOrder));

    }

    @Override
    public List<SysApi> getSysApiOfSysPermission(String sysPermissionId) {
        ApiAssert.isNotBlank(sysPermissionId, SysPermissionErrorResponses.sysPermissionIdCanNotNull());

        logger.debug("获取系统权限");
        final SysPermission sysPermission = sysPermissionMapper.selectById(sysPermissionId);
        ApiAssert.isNotNull(sysPermission, ErrorResponse.create("系统权限不存在"));

        return getSysApiOfSysPermission(Arrays.asList(sysPermissionId));
    }

    @Override
    public List<SysApi> getSysApiOfSysPermission(List<String> sysPermissionIdList) {
        if (CollectionUtils.isEmpty(sysPermissionIdList)) {
            return Collections.emptyList();
        }

        List<SysPermissionApi> sysPermissionApiList = sysPermissionApiMapper.selectList(Wrappers.<SysPermissionApi>lambdaQuery().in(SysPermissionApi::getSysPermissionId, sysPermissionIdList));
        if (CollectionUtils.isEmpty(sysPermissionApiList)) {
            return Collections.emptyList();
        }

        List<String> sysApiIdList = sysPermissionApiList.stream()
                .map(SysPermissionApi::getSysApiId)
                .collect(Collectors.toList());
        return sysApiMapper.selectList(Wrappers.<SysApi>lambdaQuery().in(SysApi::getId, sysApiIdList));
    }


    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void createSysPermission(String code, String name, SysPermissionTypeEnum type, String remark, StatusEnum status, String operatedSysUserId) {
        ApiAssert.isNotBlank(code, SysPermissionErrorResponses.sysPermissionCodeCanNotBlank());
        ApiAssert.isNotBlank(name, SysPermissionErrorResponses.sysPermissionNameCanNotBlank());
        ApiAssert.isNotNull(type, SysPermissionErrorResponses.sysPermissionTypeCanNotNull());
        ApiAssert.isNotNull(status, SysPermissionErrorResponses.sysPermissionStatusCanNotNull());
        ApiAssert.isNotBlank(operatedSysUserId, SysUserErrorResponses.operatedSysUserIdCanNotBlank());

        logger.debug("检查操作的系统用户是否存在");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, SysUserErrorResponses.operatedSysUserNotExist());

        remark = ObjectUtils.defaultIfNull(remark, "");

        logger.debug("插入系统权限");
        SysPermission sysPermission = new SysPermission();
        sysPermission.setCode(code);
        sysPermission.setName(name);
        sysPermission.setRemark(remark);
        sysPermission.setType(type);
        sysPermission.setStatus(status);
        sysPermission.setUpdatedSysUserId(operatedSysUserId);
        sysPermission.setCreatedSysUserId(operatedSysUserId);
        sysPermission.setUpdatedDateTime(LocalDateTime.now());
        sysPermission.setCreatedDateTime(LocalDateTime.now());
        int i = sysPermissionMapper.insert(sysPermission);
        ApiAssert.isEqualToOne(i, SysPermissionErrorResponses.createSysPermissionFailure());
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void updateSysPermission(String sysPermissionId, String code, String name, SysPermissionTypeEnum type, String remark, StatusEnum status, String operatedSysUserId) {
        ApiAssert.isNotBlank(sysPermissionId, SysPermissionErrorResponses.sysPermissionIdCanNotNull());
        ApiAssert.isNotBlank(code, SysPermissionErrorResponses.sysPermissionCodeCanNotBlank());
        ApiAssert.isNotBlank(name, SysPermissionErrorResponses.sysPermissionNameCanNotBlank());
        ApiAssert.isNotNull(type, SysPermissionErrorResponses.sysPermissionTypeCanNotNull());
        ApiAssert.isNotNull(status, SysPermissionErrorResponses.sysPermissionStatusCanNotNull());
        ApiAssert.isNotBlank(operatedSysUserId, SysUserErrorResponses.operatedSysUserIdCanNotBlank());

        logger.debug("检查操作的系统用户是否存在");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, SysUserErrorResponses.operatedSysUserNotExist());

        logger.debug("检查系统权限是否存在");
        SysPermission sysPermission = sysPermissionMapper.selectById(sysPermissionId);
        ApiAssert.isNotNull(sysPermission, SysPermissionErrorResponses.sysPermissionNotExist());

        remark = ObjectUtils.defaultIfNull(remark, "");

        logger.debug("修改系统权限");
        sysPermission.setCode(code);
        sysPermission.setName(name);
        sysPermission.setRemark(remark);
        sysPermission.setType(type);
        sysPermission.setStatus(status);
        sysPermission.setUpdatedSysUserId(operatedSysUserId);
        sysPermission.setUpdatedDateTime(LocalDateTime.now());
        int i = sysPermissionMapper.updateById(sysPermission);
        ApiAssert.isEqualToOne(i, SysPermissionErrorResponses.updateSysPermissionFailure());
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void setSysApiOfSysPermission(String sysPermissionId, List<String> sysApiIdList, String operatedSysUserId) {
        ApiAssert.isNotBlank(sysPermissionId, SysPermissionErrorResponses.sysPermissionIdCanNotNull());
        ApiAssert.isNotBlank(operatedSysUserId, SysUserErrorResponses.operatedSysUserIdCanNotBlank());

        SysPermission sysPermission = sysPermissionMapper.selectById(sysPermissionId);
        ApiAssert.isNotNull(sysPermission, SysPermissionErrorResponses.sysPermissionNotExist());

        logger.debug("检查操作的系统用户是否存在");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, SysUserErrorResponses.operatedSysUserNotExist());

        List<SysPermissionApi> sysPermissionApiList = Collections.emptyList();
        if (CollectionUtils.isNotEmpty(sysApiIdList)) {
            //转换
            sysPermissionApiList = sysApiIdList.stream()
                    .map((sysApiId) -> {
                        SysPermissionApi sysPermissionApi = new SysPermissionApi();
                        sysPermissionApi.setSysPermissionId(sysPermissionId);
                        sysPermissionApi.setSysApiId(sysApiId);
                        sysPermissionApi.setCreatedSysUserId(operatedSysUserId);
                        sysPermissionApi.setCreatedDateTime(LocalDateTime.now());
                        return sysPermissionApi;
                    }).collect(Collectors.toList());
        }
        logger.debug("删除系统权限[{}]所有系统接口关联", sysPermissionId);
        sysPermissionApiMapper.delete(Wrappers.<SysPermissionApi>lambdaQuery().eq(SysPermissionApi::getSysPermissionId, sysPermissionId));
        logger.debug("插入系统权限接口关联");
        sysPermissionApiMapper.insertBatch(sysPermissionApiList);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void setSysMenuOfSysPermission(String sysPermissionId, List<String> sysMenuIdList, String operatedSysUserId) {
        ApiAssert.isNotBlank(sysPermissionId, SysPermissionErrorResponses.sysPermissionIdCanNotNull());
        ApiAssert.isNotBlank(operatedSysUserId, SysUserErrorResponses.operatedSysUserNotExist());

        logger.debug("检查操作的系统用户是否存在");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, SysUserErrorResponses.operatedSysUserNotExist());

        logger.debug("检查系统权限是否存在");
        SysPermission sysPermission = sysPermissionMapper.selectById(sysPermissionId);
        ApiAssert.isNotNull(sysPermission, SysPermissionErrorResponses.sysPermissionNotExist());

        List<SysPermissionMenu> sysPermissionMenuList = Collections.emptyList();
        if (CollectionUtils.isNotEmpty(sysMenuIdList)) {
            //转换
            sysPermissionMenuList = sysMenuIdList.stream()
                    .map((sysMenuId) -> {
                        SysPermissionMenu sysPermissionMenu = new SysPermissionMenu();
                        sysPermissionMenu.setSysPermissionId(sysPermissionId);
                        sysPermissionMenu.setSysMenuId(sysMenuId);
                        sysPermissionMenu.setCreatedSysUserId(operatedSysUserId);
                        sysPermissionMenu.setCreatedDateTime(LocalDateTime.now());
                        return sysPermissionMenu;
                    }).collect(Collectors.toList());
        }
        logger.debug("删除系统权限[{}]所有系统菜单关联", sysPermissionId);
        sysPermissionMenuMapper.delete(Wrappers.<SysPermissionMenu>lambdaQuery().eq(SysPermissionMenu::getSysPermissionId, sysPermissionId));
        logger.debug("插入系统权限接口关联");
        sysPermissionMenuMapper.insertBatch(sysPermissionMenuList);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void deleteSysPermission(String sysPermissionId) {
        ApiAssert.isNotBlank(sysPermissionId, SysPermissionErrorResponses.sysPermissionIdCanNotNull());

        logger.debug("检查系统权限是否存在");
        SysPermission sysPermission = sysPermissionMapper.selectById(sysPermissionId);
        ApiAssert.isNotNull(sysPermission, SysPermissionErrorResponses.sysPermissionNotExist());

        logger.debug("删除系统权限");
        int i = sysPermissionMapper.deleteById(sysPermissionId);
        ApiAssert.isEqualToOne(i, SysPermissionErrorResponses.deleteSysPermissionFailure());

        //删除系统角色权限
        sysRolePermissionMapper.delete(Wrappers.<SysRolePermission>lambdaQuery().eq(SysRolePermission::getSysPermissionId, sysPermissionId));

        //删除系统权限菜单
        sysPermissionMenuMapper.delete(Wrappers.<SysPermissionMenu>lambdaQuery().eq(SysPermissionMenu::getSysPermissionId, sysPermissionId));

        //删除系统权限接口
        sysPermissionApiMapper.delete(Wrappers.<SysPermissionApi>lambdaQuery().eq(SysPermissionApi::getSysPermissionId, sysPermissionId));
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void enabledSysPermission(String sysPermissionId, String operatedSysUserId) {
        ApiAssert.isNotBlank(sysPermissionId, SysPermissionErrorResponses.sysPermissionIdCanNotNull());
        ApiAssert.isNotBlank(operatedSysUserId, SysUserErrorResponses.operatedSysUserIdCanNotBlank());

        logger.debug("检查操作的系统用户是否存在");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, SysUserErrorResponses.operatedSysUserNotExist());

        logger.debug("检查系统权限是否存在");
        SysPermission sysPermission = sysPermissionMapper.selectById(sysPermissionId);
        ApiAssert.isNotNull(sysPermission, SysPermissionErrorResponses.sysPermissionNotExist());

        logger.debug("启用系统权限");
        sysPermission.setStatus(StatusEnum.ENABLED);
        sysPermission.setUpdatedSysUserId(operatedSysUserId);
        sysPermission.setUpdatedDateTime(LocalDateTime.now());
        int i = sysPermissionMapper.updateById(sysPermission);
        ApiAssert.isEqualToOne(i, SysPermissionErrorResponses.updateSysPermissionFailure());
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void disableSysPermission(String sysPermissionId, String operatedSysUserId) {
        ApiAssert.isNotBlank(sysPermissionId, SysPermissionErrorResponses.sysPermissionIdCanNotNull());
        ApiAssert.isNotBlank(operatedSysUserId, SysUserErrorResponses.operatedSysUserIdCanNotBlank());

        logger.debug("检查操作的系统用户是否存在");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, SysUserErrorResponses.operatedSysUserNotExist());

        logger.debug("检查系统权限是否存在");
        SysPermission sysPermission = sysPermissionMapper.selectById(sysPermissionId);
        ApiAssert.isNotNull(sysPermission, SysPermissionErrorResponses.sysPermissionNotExist());

        logger.debug("禁用系统权限");
        sysPermission.setStatus(StatusEnum.DISABLED);
        sysPermission.setUpdatedSysUserId(operatedSysUserId);
        sysPermission.setUpdatedDateTime(LocalDateTime.now());
        int i = sysPermissionMapper.updateById(sysPermission);
        ApiAssert.isEqualToOne(i, SysPermissionErrorResponses.updateSysPermissionFailure());
    }
}
