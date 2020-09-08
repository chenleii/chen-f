package com.chen.f.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.f.common.api.response.error.SysUserErrorResponses;
import com.chen.f.common.mapper.SysOrganizationMapper;
import com.chen.f.common.mapper.SysOrganizationUserMapper;
import com.chen.f.common.mapper.SysRoleMapper;
import com.chen.f.common.mapper.SysUserMapper;
import com.chen.f.common.mapper.SysUserRoleMapper;
import com.chen.f.common.pojo.SysOrganization;
import com.chen.f.common.pojo.SysOrganizationUser;
import com.chen.f.common.pojo.SysRole;
import com.chen.f.common.pojo.SysUser;
import com.chen.f.common.pojo.SysUserRole;
import com.chen.f.common.pojo.enums.SysUserStatusEnum;
import com.chen.f.common.service.ISysUserService;
import com.chen.f.core.api.ApiAssert;
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
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统用户表 服务实现类
 * </p>
 *
 * @author chen
 * @since 2018-12-02
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {
    protected static final Logger logger = LoggerFactory.getLogger(SysUserServiceImpl.class);

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysOrganizationUserMapper sysOrganizationUserMapper;
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    @Autowired
    private SysOrganizationMapper sysOrganizationMapper;


    @Override
    public List<SysUser> getAllSysUserList() {
        return sysUserMapper.selectAll();
    }

    @Override
    public List<SysUser> getEnabledSysUserList() {
        return sysUserMapper.selectList(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getStatus, SysUserStatusEnum.ENABLED));
    }

    @Override
    public Page<SysUser> getSysUserPage(Page<SysUser> page,
                                        String username, Integer level, String remark, SysUserStatusEnum sysUserStatusEnum) {
        LambdaQueryWrapper<SysUser> wrapper = Wrappers.lambdaQuery();
        wrapper.like(StringUtils.isNotBlank(username), SysUser::getUsername, username);
        wrapper.eq(Objects.nonNull(level), SysUser::getLevel, level);
        wrapper.like(StringUtils.isNotBlank(remark), SysUser::getRemark, remark);
        wrapper.eq(Objects.nonNull(sysUserStatusEnum), SysUser::getStatus, sysUserStatusEnum);
        return sysUserMapper.selectPage(page, wrapper);
    }

    @Override
    public SysUser getSysUser(String sysUserId) {
        ApiAssert.isNotBlank(sysUserId, SysUserErrorResponses.sysUserIdCanNotNull());
        return sysUserMapper.selectById(sysUserId);
    }

    @Override
    public SysUser getSysUserByUsername(String username) {
        return sysUserMapper.selectOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUsername, username));
    }

    @Override
    public List<SysOrganization> getSysOrganizationOfSysUser(String sysUserId) {
        ApiAssert.isNotBlank(sysUserId, SysUserErrorResponses.sysUserIdCanNotNull());

        logger.debug("获取系统用户");
        SysUser sysUser = sysUserMapper.selectById(sysUserId);
        ApiAssert.isNotNull(sysUser, SysUserErrorResponses.sysUserNotExist());

        final List<SysOrganizationUser> sysOrganizationUserList = sysOrganizationUserMapper.selectList(Wrappers.<SysOrganizationUser>lambdaQuery().eq(SysOrganizationUser::getSysUserId, sysUserId));
        if (CollectionUtils.isEmpty(sysOrganizationUserList)) {
            return Collections.emptyList();
        }

        final List<String> sysOrganizationIdList = sysOrganizationUserList.stream()
                .map(SysOrganizationUser::getSysOrganizationId)
                .collect(Collectors.toList());
        return sysOrganizationMapper.selectSuperiorByIdList(sysOrganizationIdList);
    }

    @Override
    public List<SysRole> getSysRoleOfSysUser(String sysUserId) {
        ApiAssert.isNotBlank(sysUserId, SysUserErrorResponses.sysUserIdCanNotNull());

        logger.debug("获取系统用户");
        SysUser sysUser = sysUserMapper.selectById(sysUserId);
        ApiAssert.isNotNull(sysUser, SysUserErrorResponses.sysUserNotExist());

        List<SysUserRole> sysUserRoleList = sysUserRoleMapper.selectList(Wrappers.<SysUserRole>lambdaQuery().eq(SysUserRole::getSysUserId, sysUserId));
        if (CollectionUtils.isEmpty(sysUserRoleList)) {
            logger.debug("系统用户ID[{}],没有对应系统角色.", sysUserId);
            return Collections.emptyList();
        }
        List<String> sysRoleIdList = sysUserRoleList.stream()
                .map((SysUserRole::getSysRoleId))
                .collect(Collectors.toList());
        return sysRoleMapper.selectBatchIds(sysRoleIdList);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void createSysUser(String username, String password, Integer level, String remark, SysUserStatusEnum status, String operatedSysUserId) {
        ApiAssert.isNotBlank(username, SysUserErrorResponses.sysUserNameCanNotBlank());
        ApiAssert.isNotBlank(password, SysUserErrorResponses.sysUserPasswordCanNotBlank());
        ApiAssert.isNotNull(level, SysUserErrorResponses.sysUserLevelCanNotNull());
        ApiAssert.isNotNull(status, SysUserErrorResponses.sysUserStatusCanNotNull());
        ApiAssert.isNotBlank(operatedSysUserId, SysUserErrorResponses.operatedSysUserIdCanNotBlank());

        logger.debug("检查创建用户级别是否小于等于操作用户等级");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, SysUserErrorResponses.operatedSysUserNotExist());

        logger.debug("检查创建的系统用户级别是否小于等于操作用户级别");
        ApiAssert.isGreaterThanOrEqualTo(level, operatedSysUser.getLevel(), SysUserErrorResponses.createdSysUserLevelCanNotGreaterThanOperatedSysUserLevel());

        remark = ObjectUtils.defaultIfNull(remark, "");

        logger.debug("插入系统用户信息");
        SysUser sysUser = new SysUser();
        sysUser.setUsername(username);
        sysUser.setPassword(password);
        sysUser.setLevel(level);
        sysUser.setRemark(remark);
        sysUser.setStatus(status);
        sysUser.setUpdatedSysUserId(operatedSysUserId);
        sysUser.setCreatedSysUserId(operatedSysUserId);
        sysUser.setUpdatedDateTime(LocalDateTime.now());
        sysUser.setCreatedDateTime(LocalDateTime.now());
        int i = sysUserMapper.insert(sysUser);
        ApiAssert.isEqualToOne(i, SysUserErrorResponses.createSysUserFailure());
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void updateSysUser(String sysUserId, String username, String password, Integer level, String remark, SysUserStatusEnum status, String operatedSysUserId) {
        ApiAssert.isNotBlank(sysUserId, SysUserErrorResponses.sysUserIdCanNotNull());
        ApiAssert.isNotBlank(username, SysUserErrorResponses.sysUserNameCanNotBlank());
        ApiAssert.isNotBlank(password, SysUserErrorResponses.sysUserPasswordCanNotBlank());
        ApiAssert.isNotNull(level, SysUserErrorResponses.sysUserLevelCanNotNull());
        ApiAssert.isNotNull(status, SysUserErrorResponses.sysUserStatusCanNotNull());
        ApiAssert.isNotBlank(operatedSysUserId, SysUserErrorResponses.operatedSysUserIdCanNotBlank());

        logger.debug("检查操作的系统用户是否存在");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, SysUserErrorResponses.operatedSysUserNotExist());

        SysUser sysUser = sysUserMapper.selectById(sysUserId);
        ApiAssert.isNotNull(sysUser, SysUserErrorResponses.sysUserNotExist());

        logger.debug("检查操作的系统用户级别是否大于被修改的系统用户级别");
        ApiAssert.isLessThan(operatedSysUser.getLevel(),sysUser.getLevel(), SysUserErrorResponses.operatedSysUserLevelMustGreaterThanUpdatedSysUserLevel());

        logger.debug("检查修改后的系统用户级别是否小于等于操作用户级别");
        ApiAssert.isGreaterThanOrEqualTo(level, operatedSysUser.getLevel(), SysUserErrorResponses.updatedSysUserLevelCanNotGreaterThanOperatedSysUserLevel());

        remark = ObjectUtils.defaultIfNull(remark, "");

        logger.debug("修改系统用户信息");
        sysUser.setUsername(username);
        sysUser.setPassword(password);
        sysUser.setRemark(remark);
        sysUser.setStatus(status);
        sysUser.setLevel(level);
        sysUser.setUpdatedSysUserId(operatedSysUserId);
        sysUser.setUpdatedDateTime(LocalDateTime.now());
        int i = sysUserMapper.updateById(sysUser);
        ApiAssert.isEqualToOne(i, SysUserErrorResponses.updateSysUserFailure());
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void updateSysUserLastLoginDateTime(String sysUserId, LocalDateTime lastLoginDateTime) {
        ApiAssert.isNotBlank(sysUserId, SysUserErrorResponses.sysUserIdCanNotNull());
        ApiAssert.isNotNull(lastLoginDateTime, SysUserErrorResponses.sysUserLastLoginDateTimeCanNotNull());

        SysUser sysUser = sysUserMapper.selectById(sysUserId);
        ApiAssert.isNotNull(sysUser, SysUserErrorResponses.sysUserNotExist());

        logger.debug("修改系统用户信息");
        sysUser.setLastLoginDateTime(lastLoginDateTime);
        int i = sysUserMapper.updateById(sysUser);
        ApiAssert.isEqualToOne(i, SysUserErrorResponses.updateSysUserFailure());
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void setSysOrganizationOfSysUser(String sysUserId, List<String> sysOrganizationIdList, String operatedSysUserId) {
        ApiAssert.isNotBlank(sysUserId, SysUserErrorResponses.sysUserIdCanNotNull());
        ApiAssert.isNotBlank(operatedSysUserId, SysUserErrorResponses.operatedSysUserIdCanNotBlank());

        logger.debug("检查操作的系统用户是否存在");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, SysUserErrorResponses.operatedSysUserNotExist());

        SysUser sysUser = sysUserMapper.selectById(sysUserId);
        ApiAssert.isNotNull(sysUser, SysUserErrorResponses.sysUserNotExist());

        logger.debug("检查操作的系统用户级别是否大于被修改的系统用户级别");
        ApiAssert.isLessThan(operatedSysUser.getLevel(),sysUser.getLevel(), SysUserErrorResponses.operatedSysUserLevelMustGreaterThanUpdatedSysUserLevel());

        List<SysOrganizationUser> sysOrganizationUserList = Collections.emptyList();

        if (CollectionUtils.isNotEmpty(sysOrganizationIdList)) {
            //转换
            sysOrganizationUserList = sysOrganizationIdList.stream()
                    .map((sysOrganizationId) -> {
                        SysOrganizationUser sysOrganizationUser = new SysOrganizationUser();
                        sysOrganizationUser.setSysOrganizationId(sysOrganizationId);
                        sysOrganizationUser.setSysUserId(sysUserId);
                        sysOrganizationUser.setCreatedSysUserId(operatedSysUserId);
                        sysOrganizationUser.setCreatedDateTime(LocalDateTime.now());
                        return sysOrganizationUser;
                    }).collect(Collectors.toList());
        }
        logger.debug("删除系统用户[{}]所有系统组织关联", sysUserId);
        sysOrganizationUserMapper.delete(Wrappers.<SysOrganizationUser>lambdaQuery().eq(SysOrganizationUser::getSysUserId, sysUserId));
        logger.debug("插入系统用户组织关联");
        sysOrganizationUserMapper.insertBatch(sysOrganizationUserList);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void setSysRoleOfSysUser(String sysUserId, List<String> sysRoleIdList, String operatedSysUserId) {
        ApiAssert.isNotBlank(sysUserId, SysUserErrorResponses.sysUserIdCanNotNull());
        ApiAssert.isNotBlank(operatedSysUserId, SysUserErrorResponses.operatedSysUserIdCanNotBlank());

        logger.debug("检查操作的系统用户是否存在");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, SysUserErrorResponses.operatedSysUserNotExist());

        SysUser sysUser = sysUserMapper.selectById(sysUserId);
        ApiAssert.isNotNull(sysUser, SysUserErrorResponses.sysUserNotExist());

        logger.debug("检查操作的系统用户级别是否大于被修改的系统用户级别");
        ApiAssert.isLessThan(operatedSysUser.getLevel(),sysUser.getLevel(), SysUserErrorResponses.operatedSysUserLevelMustGreaterThanUpdatedSysUserLevel());

        List<SysUserRole> sysUserRoleList = Collections.emptyList();

        if (CollectionUtils.isNotEmpty(sysRoleIdList)) {
            //转换
            sysUserRoleList = sysRoleIdList.stream()
                    .map((sysRoleId) -> {
                        SysUserRole sysUserRole = new SysUserRole();
                        sysUserRole.setSysUserId(sysUserId);
                        sysUserRole.setSysRoleId(sysRoleId);
                        sysUserRole.setCreatedSysUserId(operatedSysUserId);
                        sysUserRole.setCreatedDateTime(LocalDateTime.now());
                        return sysUserRole;
                    }).collect(Collectors.toList());
        }
        logger.debug("删除系统用户[{}]所有系统角色关联", sysUserId);
        sysUserRoleMapper.delete(Wrappers.<SysUserRole>lambdaQuery().eq(SysUserRole::getSysUserId, sysUserId));
        logger.debug("插入系统用户角色关联");
        sysUserRoleMapper.insertBatch(sysUserRoleList);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void deleteSysUser(String sysUserId, String operatedSysUserId) {
        ApiAssert.isNotBlank(sysUserId, SysUserErrorResponses.sysUserIdCanNotNull());
        ApiAssert.isNotBlank(operatedSysUserId, SysUserErrorResponses.operatedSysUserIdCanNotBlank());

        logger.debug("检查操作的系统用户是否存在");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, SysUserErrorResponses.operatedSysUserNotExist());

        SysUser sysUser = sysUserMapper.selectById(sysUserId);
        ApiAssert.isNotNull(sysUser, SysUserErrorResponses.sysUserNotExist());

        logger.debug("检查操作的系统用户级别是否大于被修改的系统用户级别");
        ApiAssert.isLessThan(operatedSysUser.getLevel(),sysUser.getLevel(), SysUserErrorResponses.operatedSysUserLevelMustGreaterThanUpdatedSysUserLevel());

        logger.debug("删除系统用户");
        int i = sysUserMapper.deleteById(sysUserId);
        ApiAssert.isEqualToOne(i, SysUserErrorResponses.deleteSysUserFailure());

        //删除系统用户组织
        sysOrganizationUserMapper.delete(Wrappers.<SysOrganizationUser>lambdaQuery().eq(SysOrganizationUser::getSysUserId, sysUserId));

        //删除系统用户角色
        sysUserRoleMapper.delete(Wrappers.<SysUserRole>lambdaQuery().eq(SysUserRole::getSysUserId, sysUserId));
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void enabledSysUser(String sysUserId, String operatedSysUserId) {
        ApiAssert.isNotBlank(sysUserId, SysUserErrorResponses.sysUserIdCanNotNull());
        ApiAssert.isNotBlank(operatedSysUserId, SysUserErrorResponses.operatedSysUserIdCanNotBlank());

        logger.debug("检查操作的系统用户是否存在");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, SysUserErrorResponses.operatedSysUserNotExist());

        SysUser sysUser = sysUserMapper.selectById(sysUserId);
        ApiAssert.isNotNull(sysUser, SysUserErrorResponses.sysUserNotExist());

        logger.debug("检查操作的系统用户级别是否大于被修改的系统用户级别");
        ApiAssert.isLessThan(operatedSysUser.getLevel(),sysUser.getLevel(), SysUserErrorResponses.operatedSysUserLevelMustGreaterThanUpdatedSysUserLevel());

        logger.debug("启用系统用户");
        sysUser.setStatus(SysUserStatusEnum.ENABLED);
        int i = sysUserMapper.update(sysUser, Wrappers.<SysUser>lambdaQuery().eq(SysUser::getId, sysUserId).ne(SysUser::getStatus, SysUserStatusEnum.ENABLED));
        ApiAssert.isEqualToOne(i, SysUserErrorResponses.updateSysUserFailure());

    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void disableSysUser(String sysUserId, String operatedSysUserId) {
        ApiAssert.isNotBlank(sysUserId, SysUserErrorResponses.sysUserIdCanNotNull());
        ApiAssert.isNotBlank(operatedSysUserId, SysUserErrorResponses.operatedSysUserIdCanNotBlank());

        logger.debug("检查操作的系统用户是否存在");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, SysUserErrorResponses.operatedSysUserNotExist());

        SysUser sysUser = sysUserMapper.selectById(sysUserId);
        ApiAssert.isNotNull(sysUser, SysUserErrorResponses.sysUserNotExist());

        logger.debug("检查操作的系统用户级别是否大于被修改的系统用户级别");
        ApiAssert.isLessThan(operatedSysUser.getLevel(),sysUser.getLevel(), SysUserErrorResponses.operatedSysUserLevelMustGreaterThanUpdatedSysUserLevel());

        logger.debug("禁用系统用户");
        sysUser.setStatus(SysUserStatusEnum.DISABLED);
        int i = sysUserMapper.update(sysUser, Wrappers.<SysUser>lambdaQuery().eq(SysUser::getId, sysUserId).ne(SysUser::getStatus, SysUserStatusEnum.DISABLED));
        ApiAssert.isEqualToOne(i, SysUserErrorResponses.updateSysUserFailure());

    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void lockSysUser(String sysUserId, String operatedSysUserId) {
        ApiAssert.isNotBlank(sysUserId, SysUserErrorResponses.sysUserIdCanNotNull());
        ApiAssert.isNotBlank(operatedSysUserId, SysUserErrorResponses.operatedSysUserIdCanNotBlank());

        logger.debug("检查操作的系统用户是否存在");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, SysUserErrorResponses.operatedSysUserNotExist());

        SysUser sysUser = sysUserMapper.selectById(sysUserId);
        ApiAssert.isNotNull(sysUser, SysUserErrorResponses.sysUserNotExist());

        logger.debug("检查操作的系统用户级别是否大于被修改的系统用户级别");
        ApiAssert.isLessThan(operatedSysUser.getLevel(),sysUser.getLevel(), SysUserErrorResponses.operatedSysUserLevelMustGreaterThanUpdatedSysUserLevel());

        logger.debug("锁定系统用户");
        sysUser.setStatus(SysUserStatusEnum.LOCKED);
        int i = sysUserMapper.update(sysUser, Wrappers.<SysUser>lambdaQuery().eq(SysUser::getId, sysUserId).ne(SysUser::getStatus, SysUserStatusEnum.LOCKED));
        ApiAssert.isEqualToOne(i, SysUserErrorResponses.updateSysUserFailure());
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void expireSysUser(String sysUserId, String operatedSysUserId) {
        ApiAssert.isNotBlank(sysUserId, SysUserErrorResponses.sysUserIdCanNotNull());
        ApiAssert.isNotBlank(operatedSysUserId, SysUserErrorResponses.operatedSysUserIdCanNotBlank());

        logger.debug("检查操作的系统用户是否存在");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, SysUserErrorResponses.operatedSysUserNotExist());

        SysUser sysUser = sysUserMapper.selectById(sysUserId);
        ApiAssert.isNotNull(sysUser, SysUserErrorResponses.sysUserNotExist());

        logger.debug("检查操作的系统用户级别是否大于被修改的系统用户级别");
        ApiAssert.isLessThan(operatedSysUser.getLevel(),sysUser.getLevel(), SysUserErrorResponses.operatedSysUserLevelMustGreaterThanUpdatedSysUserLevel());

        logger.debug("过期系统用户");
        sysUser.setStatus(SysUserStatusEnum.EXPIRED);
        int i = sysUserMapper.update(sysUser, Wrappers.<SysUser>lambdaQuery().eq(SysUser::getId, sysUserId).ne(SysUser::getStatus, SysUserStatusEnum.EXPIRED));
        ApiAssert.isEqualToOne(i, SysUserErrorResponses.updateSysUserFailure());
    }

}
