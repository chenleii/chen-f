package com.chen.f.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.f.admin.service.ISysUserService;
import com.chen.f.common.mapper.SysRoleMapper;
import com.chen.f.common.mapper.SysUserMapper;
import com.chen.f.common.mapper.SysUserRoleMapper;
import com.chen.f.common.pojo.SysRole;
import com.chen.f.common.pojo.SysUser;
import com.chen.f.common.pojo.SysUserRole;
import com.chen.f.common.pojo.enums.SysUserStatusEnum;
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
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public IPage<SysUser> getSysUserPage(Long pageIndex, Long pageNumber, String username, String remark, SysUserStatusEnum sysUserStatusEnum, Integer level) {
        LambdaQueryWrapper<SysUser> wrapper = Wrappers.lambdaQuery();
        if (StringUtils.isNotBlank(username)) {
            wrapper.like(SysUser::getUsername, username);
        }
        if (StringUtils.isNotBlank(remark)) {
            wrapper.like(SysUser::getRemark, remark);
        }
        if (Objects.nonNull(sysUserStatusEnum)) {
            wrapper.eq(SysUser::getStatus, sysUserStatusEnum);
        }
        if (Objects.nonNull(level)) {
            wrapper.eq(SysUser::getLevel, level);
        }
        return sysUserMapper.selectPage(new Page<>(pageIndex, pageNumber), wrapper);
    }

    @Override
    public SysUser getSysUser(String sysUserId) {
        ApiAssert.isNotBlank(sysUserId, ErrorResponse.create("系统用户ID不能为空"));
        return sysUserMapper.selectById(sysUserId);
    }

    @Override
    public List<SysRole> getSysRoleOfSysUser(String sysUserId) {
        ApiAssert.isNotBlank(sysUserId, ErrorResponse.create("系统用户ID不能为空"));
        List<SysUserRole> sysUserRoleList = sysUserRoleMapper.selectList(Wrappers.<SysUserRole>lambdaQuery().eq(SysUserRole::getSysUserId, sysUserId));
        if (CollectionUtils.isNotEmpty(sysUserRoleList)) {
            List<String> sysRoleIdList = sysUserRoleList.stream()
                    .map((SysUserRole::getSysRoleId))
                    .collect(Collectors.toList());
            return sysRoleMapper.selectBatchIds(sysRoleIdList);
        }
        logger.debug("系统用户id[{}],没有对应角色.", sysUserId);
        return Collections.emptyList();
    }

    @Override
    public void createSysUser(String username, String password, String remark, SysUserStatusEnum status, Integer level, String operatedSysUserId) {
        ApiAssert.isNotBlank(username, ErrorResponse.create("系统用户名称不能为空"));
        ApiAssert.isNotBlank(password, ErrorResponse.create("系统用户密码不能为空"));
        ApiAssert.isNotNull(status, ErrorResponse.create("系统用户状态不能为空"));
        ApiAssert.isNotNull(level, ErrorResponse.create("系统用户等级不能为空"));
        ApiAssert.isNotBlank(operatedSysUserId, ErrorResponse.create("操作的系统用户ID不能为空"));
        logger.debug("检查创建用户级别是否小于等于操作用户等级");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, ErrorResponse.create("操作的系统用户不存在"));
        ApiAssert.isGreaterThanOrEqualTo(level, operatedSysUser.getLevel(), ErrorResponse.create("创建的用户级别不能大于操作用户级别"));
        logger.debug("插入系统用户信息");
        SysUser sysUser = new SysUser();
        sysUser.setUsername(username);
        sysUser.setPassword(password);
        sysUser.setRemark(remark);
        sysUser.setStatus(status);
        sysUser.setLevel(level);
        sysUser.setCreateSysUserId(operatedSysUserId);
        sysUser.setCreateDateTime(LocalDateTime.now());
        int i = sysUserMapper.insert(sysUser);
        ApiAssert.isEqualToOne(i, ErrorResponse.create("创建系统用户失败"));
    }

    @Override
    public void updateSysUser(String sysUserId, String username, String password, String remark, SysUserStatusEnum status, Integer level, String operatedSysUserId) {
        ApiAssert.isNotBlank(sysUserId, ErrorResponse.create("系统用户ID不能为空"));
        ApiAssert.isNotBlank(username, ErrorResponse.create("系统用户名称不能为空"));
        ApiAssert.isNotBlank(password, ErrorResponse.create("系统用户密码不能为空"));
        ApiAssert.isNotNull(status, ErrorResponse.create("系统用户状态不能为空"));
        ApiAssert.isNotNull(level, ErrorResponse.create("系统用户等级不能为空"));
        ApiAssert.isNotBlank(operatedSysUserId, ErrorResponse.create("操作系统用户ID不能为空"));
        SysUser sysUser = sysUserMapper.selectById(sysUserId);
        ApiAssert.isNotNull(sysUser, ErrorResponse.create("系统用户不存在"));
        logger.debug("检查操作的系统用户是否存在");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, ErrorResponse.create("操作的系统用户不存在"));
        logger.debug("检查修改的系统用户级别是否小于操作用户级别");
        ApiAssert.isGreaterThanOrEqualTo(sysUser.getLevel(), operatedSysUser.getLevel(), ErrorResponse.create("操作的系统用户级别不能小于修改用户级别"));
        logger.debug("检查修改后的系统用户级别是否小于操作用户级别");
        ApiAssert.isGreaterThanOrEqualTo(level, operatedSysUser.getLevel(), ErrorResponse.create("修改的系统用户级别不能大于操作用户级别"));
        logger.debug("修改系统用户信息");
        sysUser.setUsername(username);
        sysUser.setPassword(password);
        sysUser.setRemark(remark);
        sysUser.setStatus(status);
        sysUser.setLevel(level);
        sysUser.setUpdateSysUserId(operatedSysUserId);
        sysUser.setUpdateDateTime(LocalDateTime.now());
        int i = sysUserMapper.updateById(sysUser);
        ApiAssert.isEqualToOne(i, ErrorResponse.create("修改系统用户失败"));
    }

    @Override
    public void setSysRoleOfSysUser(String sysUserId, List<String> sysRoleIdList, String operatedSysUserId) {
        ApiAssert.isNotBlank(sysUserId, ErrorResponse.create("系统用户ID不能为空"));
        //ApiAssert.isNotEmpty(sysRoleIdList, ErrorResponse.create("设置的系统用户角色ID不能为空"));
        ApiAssert.isNotBlank(operatedSysUserId, ErrorResponse.create("操作系统用户ID不能为空"));
        SysUser sysUser = sysUserMapper.selectById(sysUserId);
        ApiAssert.isNotNull(sysUser, ErrorResponse.create("系统用户不存在"));
        logger.debug("检查操作的系统用户是否存在");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, ErrorResponse.create("操作的系统用户不存在"));
        logger.debug("检查修改的系统用户级别是否小于操作的系统用户级别");
        ApiAssert.isGreaterThanOrEqualTo(sysUser.getLevel(), operatedSysUser.getLevel(), ErrorResponse.create("操作的系统用户级别不能小于修改的系统用户级别"));

        List<SysUserRole> sysUserRoleList = Collections.emptyList();

        if (CollectionUtils.isNotEmpty(sysRoleIdList)) {
            //转换
            sysUserRoleList = sysRoleIdList.stream()
                    .map((sysRoleId) -> {
                        SysUserRole sysUserRole = new SysUserRole();
                        sysUserRole.setSysUserId(sysUserId);
                        sysUserRole.setSysRoleId(sysRoleId);
                        sysUserRole.setCreateSysUserId(operatedSysUserId);
                        sysUserRole.setCreateDateTime(LocalDateTime.now());
                        return sysUserRole;
                    }).collect(Collectors.toList());
        }
        logger.debug("删除用户[{}]所有系统角色关联", sysUserId);
        sysUserRoleMapper.delete(Wrappers.<SysUserRole>lambdaQuery().eq(SysUserRole::getSysUserId, sysUserId));
        logger.debug("插入系统用户角色关联");
        sysUserRoleMapper.insertBatch(sysUserRoleList);
    }

    @Override
    public void deleteSysUser(String sysUserId, String operatedSysUserId) {
        ApiAssert.isNotBlank(sysUserId, ErrorResponse.create("系统用户ID不能为空"));
        ApiAssert.isNotBlank(operatedSysUserId, ErrorResponse.create("操作系统用户ID不能为空"));
        SysUser sysUser = sysUserMapper.selectById(sysUserId);
        ApiAssert.isNotNull(sysUser, ErrorResponse.create("系统用户不存在"));
        logger.debug("检查操作的系统用户是否存在");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, ErrorResponse.create("操作的系统用户不存在"));
        logger.debug("检查修改的系统用户级别是否小于操作的系统用户级别");
        ApiAssert.isGreaterThanOrEqualTo(sysUser.getLevel(), operatedSysUser.getLevel(), ErrorResponse.create("操作的系统用户级别不能小于修改的系统用户级别"));
        logger.debug("删除系统用户");
        int i = sysUserMapper.deleteById(sysUserId);
        ApiAssert.isEqualToOne(i, ErrorResponse.create("删除系统用户失败"));
    }

    @Override
    public void enabledSysUser(String sysUserId, String operatedSysUserId) {
        ApiAssert.isNotBlank(sysUserId, ErrorResponse.create("系统用户ID不能为空"));
        ApiAssert.isNotBlank(operatedSysUserId, ErrorResponse.create("操作系统用户ID不能为空"));
        SysUser sysUser = sysUserMapper.selectById(sysUserId);
        ApiAssert.isNotNull(sysUser, ErrorResponse.create("系统用户不存在"));
        logger.debug("检查操作的系统用户是否存在");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, ErrorResponse.create("操作的系统用户不存在"));
        logger.debug("检查修改的系统用户级别是否小于操作的系统用户级别");
        ApiAssert.isGreaterThanOrEqualTo(sysUser.getLevel(), operatedSysUser.getLevel(), ErrorResponse.create("操作的系统用户级别不能小于修改的系统用户级别"));
        logger.debug("启用系统用户");
        sysUser.setStatus(SysUserStatusEnum.ENABLED);
        int i = sysUserMapper.update(sysUser, Wrappers.<SysUser>lambdaQuery().eq(SysUser::getId, sysUserId).ne(SysUser::getStatus, SysUserStatusEnum.ENABLED));
        ApiAssert.isEqualToOne(i, ErrorResponse.create("修改系统用户失败"));

    }

    @Override
    public void disableSysUser(String sysUserId, String operatedSysUserId) {
        ApiAssert.isNotBlank(sysUserId, ErrorResponse.create("系统用户ID不能为空"));
        ApiAssert.isNotBlank(operatedSysUserId, ErrorResponse.create("操作系统用户ID不能为空"));
        SysUser sysUser = sysUserMapper.selectById(sysUserId);
        ApiAssert.isNotNull(sysUser, ErrorResponse.create("系统用户不存在"));
        logger.debug("检查操作的系统用户是否存在");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, ErrorResponse.create("操作的系统用户不存在"));
        logger.debug("检查修改的系统用户级别是否小于操作的系统用户级别");
        ApiAssert.isGreaterThanOrEqualTo(sysUser.getLevel(), operatedSysUser.getLevel(), ErrorResponse.create("操作的系统用户级别不能小于修改的系统用户级别"));
        logger.debug("禁用系统用户");
        sysUser.setStatus(SysUserStatusEnum.DISABLE);
        int i = sysUserMapper.update(sysUser, Wrappers.<SysUser>lambdaQuery().eq(SysUser::getId, sysUserId).ne(SysUser::getStatus, SysUserStatusEnum.DISABLE));
        ApiAssert.isEqualToOne(i, ErrorResponse.create("修改系统用户失败"));

    }

    @Override
    public void lockSysUser(String sysUserId, String operatedSysUserId) {
        ApiAssert.isNotBlank(sysUserId, ErrorResponse.create("系统用户ID不能为空"));
        ApiAssert.isNotBlank(operatedSysUserId, ErrorResponse.create("操作系统用户ID不能为空"));
        SysUser sysUser = sysUserMapper.selectById(sysUserId);
        ApiAssert.isNotNull(sysUser, ErrorResponse.create("系统用户不存在"));
        logger.debug("检查操作的系统用户是否存在");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, ErrorResponse.create("操作的系统用户不存在"));
        logger.debug("检查修改的用户级别是否小于操作用户级别");
        ApiAssert.isGreaterThanOrEqualTo(sysUser.getLevel(), operatedSysUser.getLevel(), ErrorResponse.create("操作的系统用户级别不能小于修改的系统用户级别"));
        logger.debug("锁定系统用户");
        sysUser.setStatus(SysUserStatusEnum.LOCKED);
        int i = sysUserMapper.update(sysUser, Wrappers.<SysUser>lambdaQuery().eq(SysUser::getId, sysUserId).ne(SysUser::getStatus, SysUserStatusEnum.LOCKED));
        ApiAssert.isEqualToOne(i, ErrorResponse.create("修改系统用户失败"));
    }

    @Override
    public void expireSysUser(String sysUserId, String operatedSysUserId) {
        ApiAssert.isNotBlank(sysUserId, ErrorResponse.create("系统用户ID不能为空"));
        ApiAssert.isNotBlank(operatedSysUserId, ErrorResponse.create("操作系统用户ID不能为空"));
        SysUser sysUser = sysUserMapper.selectById(sysUserId);
        ApiAssert.isNotNull(sysUser, ErrorResponse.create("系统用户不存在"));
        logger.debug("检查操作的系统用户是否存在");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, ErrorResponse.create("操作的系统用户不存在"));
        logger.debug("检查修改的系统用户级别是否小于操作的系统用户级别");
        ApiAssert.isGreaterThanOrEqualTo(sysUser.getLevel(), operatedSysUser.getLevel(), ErrorResponse.create("操作的系统用户级别不能小于修改的系统用户级别"));
        logger.debug("过期系统用户");
        sysUser.setStatus(SysUserStatusEnum.EXPIRED);
        int i = sysUserMapper.update(sysUser, Wrappers.<SysUser>lambdaQuery().eq(SysUser::getId, sysUserId).ne(SysUser::getStatus, SysUserStatusEnum.EXPIRED));
        ApiAssert.isEqualToOne(i, ErrorResponse.create("修改系统用户失败"));
    }

}
