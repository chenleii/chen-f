package com.chen.f.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.f.admin.service.ISysUserService;
import com.chen.f.core.api.ApiAssert;
import com.chen.f.core.api.response.error.ErrorResponse;
import com.chen.f.common.mapper.SysOrganizationUserMapper;
import com.chen.f.common.mapper.SysRoleMapper;
import com.chen.f.common.mapper.SysUserMapper;
import com.chen.f.common.mapper.SysUserRoleMapper;
import com.chen.f.common.pojo.SysOrganizationUser;
import com.chen.f.common.pojo.SysRole;
import com.chen.f.common.pojo.SysUser;
import com.chen.f.common.pojo.SysUserRole;
import com.chen.f.common.pojo.enums.SysUserStatusEnum;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private SysOrganizationUserMapper sysOrganizationUserMapper;
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<SysUser> getAllSysUserList() {
        return sysUserMapper.selectAll();
    }

    @Override
    public List<SysUser> getEnabledSysUserList() {
        return sysUserMapper.selectList(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getStatus, SysUserStatusEnum.ENABLED));
    }

    @Override
    public IPage<SysUser> getSysUserPage(Long pageIndex, Long pageNumber, String username, Integer level, String remark, SysUserStatusEnum sysUserStatusEnum) {
        LambdaQueryWrapper<SysUser> wrapper = Wrappers.lambdaQuery();
        wrapper.like(StringUtils.isNotBlank(username), SysUser::getUsername, username);
        wrapper.eq(Objects.nonNull(level), SysUser::getLevel, level);
        wrapper.like(StringUtils.isNotBlank(remark), SysUser::getRemark, remark);
        wrapper.eq(Objects.nonNull(sysUserStatusEnum), SysUser::getStatus, sysUserStatusEnum);
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
    public void createSysUser(String username, String password, Integer level, String remark, SysUserStatusEnum status, String operatedSysUserId) {
        ApiAssert.isNotBlank(username, ErrorResponse.create("系统用户名称不能为空"));
        ApiAssert.isNotBlank(password, ErrorResponse.create("系统用户密码不能为空"));
        ApiAssert.isNotNull(status, ErrorResponse.create("系统用户状态不能为空"));
        ApiAssert.isNotNull(level, ErrorResponse.create("系统用户等级不能为空"));
        ApiAssert.isNotBlank(operatedSysUserId, ErrorResponse.create("操作的系统用户ID不能为空"));

        logger.debug("检查创建用户级别是否小于等于操作用户等级");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, ErrorResponse.create("操作的系统用户不存在"));
        ApiAssert.isGreaterThanOrEqualTo(level, operatedSysUser.getLevel(), ErrorResponse.create("创建的用户级别不能大于操作用户级别"));


        password = passwordEncoder.encode(password);

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
        ApiAssert.isEqualToOne(i, ErrorResponse.create("创建系统用户失败"));
    }

    @Override
    public void updateSysUser(String sysUserId, String username, String password, Integer level, String remark, SysUserStatusEnum status, String operatedSysUserId) {
        ApiAssert.isNotBlank(sysUserId, ErrorResponse.create("系统用户ID不能为空"));
        ApiAssert.isNotBlank(username, ErrorResponse.create("系统用户名称不能为空"));
        ApiAssert.isNotBlank(password, ErrorResponse.create("系统用户密码不能为空"));
        ApiAssert.isNotNull(status, ErrorResponse.create("系统用户状态不能为空"));
        ApiAssert.isNotNull(level, ErrorResponse.create("系统用户等级不能为空"));
        ApiAssert.isNotBlank(operatedSysUserId, ErrorResponse.create("操作系统用户ID不能为空"));

        logger.debug("检查操作的系统用户是否存在");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, ErrorResponse.create("操作的系统用户不存在"));

        SysUser sysUser = sysUserMapper.selectById(sysUserId);
        ApiAssert.isNotNull(sysUser, ErrorResponse.create("系统用户不存在"));

        logger.debug("检查修改的系统用户级别是否小于操作用户级别");
        ApiAssert.isGreaterThanOrEqualTo(sysUser.getLevel(), operatedSysUser.getLevel(), ErrorResponse.create("操作的系统用户级别不能小于修改用户级别"));

        logger.debug("检查修改后的系统用户级别是否小于操作用户级别");
        ApiAssert.isGreaterThanOrEqualTo(level, operatedSysUser.getLevel(), ErrorResponse.create("修改的系统用户级别不能大于操作用户级别"));


        password = passwordEncoder.encode(password);

        logger.debug("修改系统用户信息");
        sysUser.setUsername(username);
        sysUser.setPassword(password);
        sysUser.setRemark(remark);
        sysUser.setStatus(status);
        sysUser.setLevel(level);
        sysUser.setUpdatedSysUserId(operatedSysUserId);
        sysUser.setUpdatedDateTime(LocalDateTime.now());
        int i = sysUserMapper.updateById(sysUser);
        ApiAssert.isEqualToOne(i, ErrorResponse.create("修改系统用户失败"));
    }

    @Override
    public void setSysRoleOfSysUser(String sysUserId, List<String> sysRoleIdList, String operatedSysUserId) {
        ApiAssert.isNotBlank(sysUserId, ErrorResponse.create("系统用户ID不能为空"));
        //ApiAssert.isNotEmpty(sysRoleIdList, ErrorResponse.create("设置的系统用户角色ID不能为空"));
        ApiAssert.isNotBlank(operatedSysUserId, ErrorResponse.create("操作系统用户ID不能为空"));

        logger.debug("检查操作的系统用户是否存在");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, ErrorResponse.create("操作的系统用户不存在"));

        SysUser sysUser = sysUserMapper.selectById(sysUserId);
        ApiAssert.isNotNull(sysUser, ErrorResponse.create("系统用户不存在"));

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
    public void deleteSysUser(String sysUserId, String operatedSysUserId) {
        ApiAssert.isNotBlank(sysUserId, ErrorResponse.create("系统用户ID不能为空"));
        ApiAssert.isNotBlank(operatedSysUserId, ErrorResponse.create("操作系统用户ID不能为空"));

        logger.debug("检查操作的系统用户是否存在");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, ErrorResponse.create("操作的系统用户不存在"));

        SysUser sysUser = sysUserMapper.selectById(sysUserId);
        ApiAssert.isNotNull(sysUser, ErrorResponse.create("系统用户不存在"));

        logger.debug("检查修改的系统用户级别是否小于操作的系统用户级别");
        ApiAssert.isGreaterThanOrEqualTo(sysUser.getLevel(), operatedSysUser.getLevel(), ErrorResponse.create("操作的系统用户级别不能小于修改的系统用户级别"));

        logger.debug("删除系统用户");
        int i = sysUserMapper.deleteById(sysUserId);
        ApiAssert.isEqualToOne(i, ErrorResponse.create("删除系统用户失败"));

        //删除系统用户组织
        sysOrganizationUserMapper.delete(Wrappers.<SysOrganizationUser>lambdaQuery().eq(SysOrganizationUser::getSysUserId, sysUserId));

        //删除系统用户角色
        sysUserRoleMapper.delete(Wrappers.<SysUserRole>lambdaQuery().eq(SysUserRole::getSysUserId, sysUserId));
    }

    @Override
    public void enabledSysUser(String sysUserId, String operatedSysUserId) {
        ApiAssert.isNotBlank(sysUserId, ErrorResponse.create("系统用户ID不能为空"));
        ApiAssert.isNotBlank(operatedSysUserId, ErrorResponse.create("操作系统用户ID不能为空"));

        logger.debug("检查操作的系统用户是否存在");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, ErrorResponse.create("操作的系统用户不存在"));

        SysUser sysUser = sysUserMapper.selectById(sysUserId);
        ApiAssert.isNotNull(sysUser, ErrorResponse.create("系统用户不存在"));

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

        logger.debug("检查操作的系统用户是否存在");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, ErrorResponse.create("操作的系统用户不存在"));

        SysUser sysUser = sysUserMapper.selectById(sysUserId);
        ApiAssert.isNotNull(sysUser, ErrorResponse.create("系统用户不存在"));

        logger.debug("检查修改的系统用户级别是否小于操作的系统用户级别");
        ApiAssert.isGreaterThanOrEqualTo(sysUser.getLevel(), operatedSysUser.getLevel(), ErrorResponse.create("操作的系统用户级别不能小于修改的系统用户级别"));

        logger.debug("禁用系统用户");
        sysUser.setStatus(SysUserStatusEnum.DISABLED);
        int i = sysUserMapper.update(sysUser, Wrappers.<SysUser>lambdaQuery().eq(SysUser::getId, sysUserId).ne(SysUser::getStatus, SysUserStatusEnum.DISABLED));
        ApiAssert.isEqualToOne(i, ErrorResponse.create("修改系统用户失败"));

    }

    @Override
    public void lockSysUser(String sysUserId, String operatedSysUserId) {
        ApiAssert.isNotBlank(sysUserId, ErrorResponse.create("系统用户ID不能为空"));
        ApiAssert.isNotBlank(operatedSysUserId, ErrorResponse.create("操作系统用户ID不能为空"));

        logger.debug("检查操作的系统用户是否存在");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, ErrorResponse.create("操作的系统用户不存在"));

        SysUser sysUser = sysUserMapper.selectById(sysUserId);
        ApiAssert.isNotNull(sysUser, ErrorResponse.create("系统用户不存在"));

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

        logger.debug("检查操作的系统用户是否存在");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, ErrorResponse.create("操作的系统用户不存在"));

        SysUser sysUser = sysUserMapper.selectById(sysUserId);
        ApiAssert.isNotNull(sysUser, ErrorResponse.create("系统用户不存在"));

        logger.debug("检查修改的系统用户级别是否小于操作的系统用户级别");
        ApiAssert.isGreaterThanOrEqualTo(sysUser.getLevel(), operatedSysUser.getLevel(), ErrorResponse.create("操作的系统用户级别不能小于修改的系统用户级别"));

        logger.debug("过期系统用户");
        sysUser.setStatus(SysUserStatusEnum.EXPIRED);
        int i = sysUserMapper.update(sysUser, Wrappers.<SysUser>lambdaQuery().eq(SysUser::getId, sysUserId).ne(SysUser::getStatus, SysUserStatusEnum.EXPIRED));
        ApiAssert.isEqualToOne(i, ErrorResponse.create("修改系统用户失败"));
    }

}
