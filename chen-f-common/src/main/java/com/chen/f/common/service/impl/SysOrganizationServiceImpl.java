package com.chen.f.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.f.common.api.response.error.SysOrganizationErrorResponses;
import com.chen.f.common.api.response.error.SysUserErrorResponses;
import com.chen.f.common.mapper.SysOrganizationMapper;
import com.chen.f.common.mapper.SysOrganizationRoleMapper;
import com.chen.f.common.mapper.SysOrganizationUserMapper;
import com.chen.f.common.mapper.SysRoleMapper;
import com.chen.f.common.mapper.SysUserMapper;
import com.chen.f.common.pojo.SysOrganization;
import com.chen.f.common.pojo.SysOrganizationRole;
import com.chen.f.common.pojo.SysOrganizationUser;
import com.chen.f.common.pojo.SysRole;
import com.chen.f.common.pojo.SysUser;
import com.chen.f.common.pojo.enums.StatusEnum;
import com.chen.f.common.pojo.enums.SysOrganizationTypeEnum;
import com.chen.f.common.service.ISysOrganizationService;
import com.chen.f.core.api.ApiAssert;
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
 * 系统组织表 服务实现类
 * </p>
 *
 * @author chen
 * @since 2020-03-25
 */
@Service
public class SysOrganizationServiceImpl extends ServiceImpl<SysOrganizationMapper, SysOrganization> implements ISysOrganizationService {
    protected static final Logger logger = LoggerFactory.getLogger(SysOrganizationServiceImpl.class);

    @Autowired
    private SysOrganizationMapper sysOrganizationMapper;

    @Autowired
    private SysOrganizationUserMapper sysOrganizationUserMapper;

    @Autowired
    private SysOrganizationRoleMapper sysOrganizationRoleMapper;

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    public List<SysOrganization> getAllSysOrganizationList() {
        return sysOrganizationMapper.selectAll();
    }

    @Override
    public List<SysOrganization> getEnabledSysOrganizationList() {
        return sysOrganizationMapper.selectList(Wrappers.<SysOrganization>lambdaQuery().eq(SysOrganization::getStatus, StatusEnum.ENABLED));
    }

    @Override
    public List<SysOrganization> getSysOrganizationList(String parentId, String name, String fullName, SysOrganizationTypeEnum type, String remark, StatusEnum status) {
        final LambdaQueryWrapper<SysOrganization> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.eq(StringUtils.isNotBlank(parentId), SysOrganization::getParentId, parentId);
        lambdaQueryWrapper.eq(StringUtils.isNotBlank(name), SysOrganization::getName, name);
        lambdaQueryWrapper.like(StringUtils.isNotBlank(fullName), SysOrganization::getFullName, fullName);
        lambdaQueryWrapper.eq(Objects.nonNull(type), SysOrganization::getType, type);
        lambdaQueryWrapper.like(StringUtils.isNotBlank(remark), SysOrganization::getRemark, remark);
        lambdaQueryWrapper.eq(Objects.nonNull(status), SysOrganization::getStatus, status);
        return sysOrganizationMapper.selectList(lambdaQueryWrapper);
    }

    @Override
    public SysOrganization getSysOrganization(String sysOrganizationId) {
        ApiAssert.isNotNull(sysOrganizationId, SysOrganizationErrorResponses.sysOrganizationIdCanNotNull());

        return sysOrganizationMapper.selectById(sysOrganizationId);
    }

    @Override
    public List<SysUser> getSysUserOfSysOrganization(String sysOrganizationId) {
        ApiAssert.isNotNull(sysOrganizationId, SysOrganizationErrorResponses.sysOrganizationIdCanNotNull());

        logger.debug("获取系统组织");
        final SysOrganization sysOrganization = sysOrganizationMapper.selectById(sysOrganizationId);
        ApiAssert.isNotNull(sysOrganization, SysOrganizationErrorResponses.sysOrganizationNotExist());

        return getSysUserOfSysOrganization(Arrays.asList(sysOrganizationId));
    }

    @Override
    public List<SysUser> getSysUserOfSysOrganization(List<String> sysOrganizationIdList) {
        if (CollectionUtils.isEmpty(sysOrganizationIdList)) {
            //系统组织ID列表为空
            return Collections.emptyList();
        }

        final List<SysOrganizationUser> sysOrganizationUserList = sysOrganizationUserMapper.selectList(Wrappers.<SysOrganizationUser>lambdaQuery().in(SysOrganizationUser::getSysOrganizationId, sysOrganizationIdList));
        if (CollectionUtils.isEmpty(sysOrganizationUserList)) {
            return Collections.emptyList();
        }
        final List<String> sysUserIdList = sysOrganizationUserList.stream()
                .map(SysOrganizationUser::getSysUserId)
                .collect(Collectors.toList());
        return sysUserMapper.selectList(Wrappers.<SysUser>lambdaQuery().in(SysUser::getId, sysUserIdList));
    }

    @Override
    public List<SysRole> getSysRoleOfSysOrganization(String sysOrganizationId) {
        ApiAssert.isNotNull(sysOrganizationId, SysOrganizationErrorResponses.sysOrganizationIdCanNotNull());

        logger.debug("获取系统组织");
        final SysOrganization sysOrganization = sysOrganizationMapper.selectById(sysOrganizationId);
        ApiAssert.isNotNull(sysOrganization, SysOrganizationErrorResponses.sysOrganizationNotExist());

        return getSysRoleOfSysOrganization(Arrays.asList(sysOrganizationId));
    }

    @Override
    public List<SysRole> getSysRoleOfSysOrganization(List<String> sysOrganizationIdList) {
        if (CollectionUtils.isEmpty(sysOrganizationIdList)) {
            //系统组织ID列表为空
            return Collections.emptyList();
        }

        final List<SysOrganizationRole> sysOrganizationRoleList = sysOrganizationRoleMapper.selectList(Wrappers.<SysOrganizationRole>lambdaQuery().in(SysOrganizationRole::getSysOrganizationId, sysOrganizationIdList));
        if (CollectionUtils.isEmpty(sysOrganizationRoleList)) {
            return Collections.emptyList();
        }
        final List<String> sysRoleIdList = sysOrganizationRoleList.stream()
                .map(SysOrganizationRole::getSysRoleId)
                .collect(Collectors.toList());
        return sysRoleMapper.selectList(Wrappers.<SysRole>lambdaQuery().in(SysRole::getId, sysRoleIdList));
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void createSysOrganization(String parentId, String name, String fullName, SysOrganizationTypeEnum type,
                                      String remark, StatusEnum status, String operatedSysUserId) {
        ApiAssert.isNotBlank(name, SysOrganizationErrorResponses.sysOrganizationNameCanNotBlank());
        ApiAssert.isNotBlank(fullName, SysOrganizationErrorResponses.sysOrganizationFullNameCanNotBlank());
        ApiAssert.isNotNull(type, SysOrganizationErrorResponses.sysOrganizationTypeCanNotNull());
        ApiAssert.isNotNull(status, SysOrganizationErrorResponses.sysOrganizationStatusCanNotNull());
        ApiAssert.isNotBlank(operatedSysUserId, SysUserErrorResponses.operatedSysUserIdCanNotBlank());

        logger.debug("检查操作的系统用户");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, SysUserErrorResponses.operatedSysUserNotExist());

        //初始化父级组织默认值
        parentId = ObjectUtils.defaultIfNull(parentId, "");

        remark = ObjectUtils.defaultIfNull(remark, "");

        logger.debug("插入系统组织");
        final SysOrganization sysOrganization = new SysOrganization();
        sysOrganization.setParentId(parentId);
        sysOrganization.setName(name);
        sysOrganization.setFullName(fullName);
        sysOrganization.setType(type);
        sysOrganization.setRemark(remark);
        sysOrganization.setStatus(status);
        sysOrganization.setUpdatedSysUserId(operatedSysUserId);
        sysOrganization.setCreatedSysUserId(operatedSysUserId);
        sysOrganization.setUpdatedDateTime(LocalDateTime.now());
        sysOrganization.setCreatedDateTime(LocalDateTime.now());
        final int i = sysOrganizationMapper.insert(sysOrganization);
        ApiAssert.isEqualToOne(i, SysOrganizationErrorResponses.createSysOrganizationFailure());

    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void updateSysOrganization(String sysOrganizationId, String parentId, String name, String fullName,
                                      SysOrganizationTypeEnum type, String remark, StatusEnum status, String operatedSysUserId) {
        ApiAssert.isNotNull(sysOrganizationId, SysOrganizationErrorResponses.sysOrganizationIdCanNotNull());
        ApiAssert.isNotBlank(name, SysOrganizationErrorResponses.sysOrganizationNameCanNotBlank());
        ApiAssert.isNotBlank(fullName, SysOrganizationErrorResponses.sysOrganizationFullNameCanNotBlank());
        ApiAssert.isNotNull(type, SysOrganizationErrorResponses.sysOrganizationTypeCanNotNull());
        ApiAssert.isNotNull(status, SysOrganizationErrorResponses.sysOrganizationStatusCanNotNull());
        ApiAssert.isNotBlank(operatedSysUserId, SysUserErrorResponses.operatedSysUserIdCanNotBlank());

        logger.debug("检查操作的系统用户");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, SysUserErrorResponses.operatedSysUserNotExist());

        logger.debug("检查系统组织是否存在");
        final SysOrganization sysOrganization = sysOrganizationMapper.selectById(sysOrganizationId);
        ApiAssert.isNotNull(sysOrganization, SysOrganizationErrorResponses.sysOrganizationNotExist());

        //初始化父级组织默认值
        parentId = ObjectUtils.defaultIfNull(parentId, "");

        remark = ObjectUtils.defaultIfNull(remark, "");

        logger.debug("插入系统组织");
        sysOrganization.setParentId(parentId);
        sysOrganization.setName(name);
        sysOrganization.setFullName(fullName);
        sysOrganization.setType(type);
        sysOrganization.setRemark(remark);
        sysOrganization.setStatus(status);
        sysOrganization.setUpdatedSysUserId(operatedSysUserId);
        sysOrganization.setUpdatedDateTime(LocalDateTime.now());
        final int i = sysOrganizationMapper.updateById(sysOrganization);
        ApiAssert.isEqualToOne(i, SysOrganizationErrorResponses.updateSysOrganizationFailure());
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void setSysUserOfSysOrganization(String sysOrganizationId, List<String> sysUserIdList, String operatedSysUserId) {
        ApiAssert.isNotNull(sysOrganizationId, SysOrganizationErrorResponses.sysOrganizationIdCanNotNull());
        ApiAssert.isNotBlank(operatedSysUserId, SysUserErrorResponses.operatedSysUserIdCanNotBlank());

        logger.debug("检查操作的系统用户是否存在");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, SysUserErrorResponses.operatedSysUserNotExist());

        SysOrganization sysOrganization = sysOrganizationMapper.selectById(sysOrganizationId);
        ApiAssert.isNotNull(sysOrganization, SysOrganizationErrorResponses.sysOrganizationNotExist());

        List<SysOrganizationUser> sysOrganizationUserList = Collections.emptyList();

        if (CollectionUtils.isNotEmpty(sysUserIdList)) {
            //转换
            sysOrganizationUserList = sysUserIdList.stream()
                    .map((sysUserId) -> {
                        SysOrganizationUser sysOrganizationUser = new SysOrganizationUser();
                        sysOrganizationUser.setSysOrganizationId(sysOrganizationId);
                        sysOrganizationUser.setSysUserId(sysUserId);
                        sysOrganizationUser.setCreatedSysUserId(operatedSysUserId);
                        sysOrganizationUser.setCreatedDateTime(LocalDateTime.now());
                        return sysOrganizationUser;
                    }).collect(Collectors.toList());
        }
        logger.debug("删除系统组织[{}]所有系统角色关联", sysOrganizationId);
        sysOrganizationUserMapper.delete(Wrappers.<SysOrganizationUser>lambdaQuery().eq(SysOrganizationUser::getSysOrganizationId, sysOrganizationId));
        logger.debug("插入系统组织角色关联");
        sysOrganizationUserMapper.insertBatch(sysOrganizationUserList);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void setSysRoleOfSysOrganization(String sysOrganizationId, List<String> sysRoleIdList, String operatedSysUserId) {
        ApiAssert.isNotNull(sysOrganizationId, SysOrganizationErrorResponses.sysOrganizationIdCanNotNull());
        ApiAssert.isNotBlank(operatedSysUserId, SysUserErrorResponses.operatedSysUserIdCanNotBlank());

        logger.debug("检查操作的系统用户是否存在");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, SysUserErrorResponses.operatedSysUserNotExist());

        SysOrganization sysOrganization = sysOrganizationMapper.selectById(sysOrganizationId);
        ApiAssert.isNotNull(sysOrganization, SysOrganizationErrorResponses.sysOrganizationNotExist());

        List<SysOrganizationRole> sysOrganizationRoleList = Collections.emptyList();

        if (CollectionUtils.isNotEmpty(sysRoleIdList)) {
            //转换
            sysOrganizationRoleList = sysRoleIdList.stream()
                    .map((sysRoleId) -> {
                        SysOrganizationRole sysOrganizationRole = new SysOrganizationRole();
                        sysOrganizationRole.setSysOrganizationId(sysOrganizationId);
                        sysOrganizationRole.setSysRoleId(sysRoleId);
                        sysOrganizationRole.setCreatedSysUserId(operatedSysUserId);
                        sysOrganizationRole.setCreatedDateTime(LocalDateTime.now());
                        return sysOrganizationRole;
                    }).collect(Collectors.toList());
        }
        logger.debug("删除系统组织[{}]所有系统角色关联", sysOrganizationId);
        sysOrganizationRoleMapper.delete(Wrappers.<SysOrganizationRole>lambdaQuery().eq(SysOrganizationRole::getSysOrganizationId, sysOrganizationId));
        logger.debug("插入系统组织角色关联");
        sysOrganizationRoleMapper.insertBatch(sysOrganizationRoleList);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void deleteSysOrganization(String sysOrganizationId) {
        ApiAssert.isNotNull(sysOrganizationId, SysOrganizationErrorResponses.sysOrganizationIdCanNotNull());

        SysOrganization sysOrganization = sysOrganizationMapper.selectById(sysOrganizationId);
        ApiAssert.isNotNull(sysOrganization, SysOrganizationErrorResponses.sysOrganizationNotExist());

        int i = sysOrganizationMapper.deleteById(sysOrganizationId);
        ApiAssert.isEqualToOne(i, SysOrganizationErrorResponses.deleteSysOrganizationFailure());

        //删除系统组织用户
        sysOrganizationUserMapper.delete(Wrappers.<SysOrganizationUser>lambdaQuery().eq(SysOrganizationUser::getSysOrganizationId, sysOrganizationId));

        //删除系统组织角色
        sysOrganizationRoleMapper.delete(Wrappers.<SysOrganizationRole>lambdaQuery().eq(SysOrganizationRole::getSysOrganizationId, sysOrganizationId));
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void enabledSysOrganization(String sysOrganizationId, String operatedSysUserId) {
        ApiAssert.isNotNull(sysOrganizationId, SysOrganizationErrorResponses.sysOrganizationIdCanNotNull());
        ApiAssert.isNotBlank(operatedSysUserId, SysUserErrorResponses.operatedSysUserIdCanNotBlank());
        
        logger.debug("检查操作的系统用户");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, SysUserErrorResponses.operatedSysUserNotExist());

        logger.debug("检查系统组织是否存在");
        final SysOrganization sysOrganization = sysOrganizationMapper.selectById(sysOrganizationId);
        ApiAssert.isNotNull(sysOrganization, SysOrganizationErrorResponses.sysOrganizationNotExist());

        logger.debug("启用系统组织");
        sysOrganization.setStatus(StatusEnum.ENABLED);
        sysOrganization.setUpdatedSysUserId(operatedSysUserId);
        sysOrganization.setUpdatedDateTime(LocalDateTime.now());
        int i = sysOrganizationMapper.updateById(sysOrganization);
        ApiAssert.isEqualToOne(i, SysOrganizationErrorResponses.updateSysOrganizationFailure());
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void disableSysOrganization(String sysOrganizationId, String operatedSysUserId) {
        ApiAssert.isNotNull(sysOrganizationId, SysOrganizationErrorResponses.sysOrganizationIdCanNotNull());
        ApiAssert.isNotBlank(operatedSysUserId, SysUserErrorResponses.operatedSysUserIdCanNotBlank());
        
        logger.debug("检查操作的系统用户");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, SysUserErrorResponses.operatedSysUserNotExist());

        logger.debug("检查系统组织是否存在");
        final SysOrganization sysOrganization = sysOrganizationMapper.selectById(sysOrganizationId);
        ApiAssert.isNotNull(sysOrganization, SysOrganizationErrorResponses.sysOrganizationNotExist());

        logger.debug("禁用系统组织");
        sysOrganization.setStatus(StatusEnum.DISABLED);
        sysOrganization.setUpdatedSysUserId(operatedSysUserId);
        sysOrganization.setUpdatedDateTime(LocalDateTime.now());
        int i = sysOrganizationMapper.updateById(sysOrganization);
        ApiAssert.isEqualToOne(i, SysOrganizationErrorResponses.updateSysOrganizationFailure());
    }

}
