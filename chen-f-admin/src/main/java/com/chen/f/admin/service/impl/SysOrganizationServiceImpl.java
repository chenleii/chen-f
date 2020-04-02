package com.chen.f.admin.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.f.admin.service.ISysOrganizationService;
import com.chen.f.common.api.ApiAssert;
import com.chen.f.common.api.response.error.ErrorResponse;
import com.chen.f.common.mapper.SysOrganizationMapper;
import com.chen.f.common.mapper.SysOrganizationUserMapper;
import com.chen.f.common.mapper.SysUserMapper;
import com.chen.f.common.pojo.SysOrganization;
import com.chen.f.common.pojo.SysOrganizationUser;
import com.chen.f.common.pojo.SysUser;
import com.chen.f.common.pojo.enums.StatusEnum;
import com.chen.f.common.pojo.enums.SysOrganizationTypeEnum;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
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
    private SysUserMapper sysUserMapper;

    @Override
    public List<SysOrganization> getAllSysOrganizationList() {
        return sysOrganizationMapper.selectAll();
    }

    @Override
    public List<SysOrganization> getEnabledSysOrganizationList() {
        return sysOrganizationMapper.selectList(Wrappers.<SysOrganization>lambdaQuery().eq(SysOrganization::getStatus, StatusEnum.ENABLED));
    }

    @Override
    public SysOrganization getSysOrganization(String sysOrganizationId) {
        ApiAssert.isNotBlank(sysOrganizationId, ErrorResponse.create("系统组织ID不能为空"));
        return sysOrganizationMapper.selectById(sysOrganizationId);
    }

    @Override
    public void createSysOrganization(String parentId, String name, String fullName, SysOrganizationTypeEnum type,
                                      String remark, StatusEnum status, String operatedSysUserId) {
        ApiAssert.isNotNull(parentId, ErrorResponse.create("系统组织父级ID不能为空"));
        ApiAssert.isNotBlank(name, ErrorResponse.create("系统组织名称不能为空"));
        ApiAssert.isNotBlank(fullName, ErrorResponse.create("系统组织URL不能为空"));
        ApiAssert.isNotNull(type, ErrorResponse.create("系统组织类型不能为空"));
        ApiAssert.isNotNull(remark, ErrorResponse.create("系统组织备注不能为空"));
        ApiAssert.isNotNull(status, ErrorResponse.create("系统组织状态不能为空"));
        ApiAssert.isNotBlank(operatedSysUserId, ErrorResponse.create("操作的系统用户ID不能为空"));

        logger.debug("检查操作的系统用户");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, ErrorResponse.create("操作的系统用户不存在"));

        //初始化父级组织默认值
        parentId = ObjectUtils.defaultIfNull(parentId, "");

        logger.debug("插入系统组织");
        final SysOrganization sysOrganization = new SysOrganization();
        sysOrganization.setParentId(parentId);
        sysOrganization.setName(name);
        sysOrganization.setFullName(fullName);
        sysOrganization.setType(type);
        sysOrganization.setRemark(remark);
        sysOrganization.setStatus(status);
        sysOrganization.setCreateSysUserId(operatedSysUserId);
        sysOrganization.setCreateDateTime(LocalDateTime.now());
        final int i = sysOrganizationMapper.insert(sysOrganization);

        ApiAssert.isEqualToOne(i, ErrorResponse.create("系统组织插入失败"));

    }

    @Override
    public void updateSysOrganization(String sysOrganizationId, String parentId, String name, String fullName, SysOrganizationTypeEnum type, String remark, StatusEnum status, String operatedSysUserId) {
        ApiAssert.isNotNull(sysOrganizationId, ErrorResponse.create("系统组织ID不能为空"));
        ApiAssert.isNotNull(parentId, ErrorResponse.create("系统组织父级ID不能为空"));
        ApiAssert.isNotBlank(name, ErrorResponse.create("系统组织名称不能为空"));
        ApiAssert.isNotBlank(fullName, ErrorResponse.create("系统组织URL不能为空"));
        ApiAssert.isNotNull(type, ErrorResponse.create("系统组织类型不能为空"));
        ApiAssert.isNotNull(remark, ErrorResponse.create("系统组织备注不能为空"));
        ApiAssert.isNotNull(status, ErrorResponse.create("系统组织状态不能为空"));
        ApiAssert.isNotBlank(operatedSysUserId, ErrorResponse.create("操作的系统用户ID不能为空"));

        logger.debug("检查操作的系统用户");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, ErrorResponse.create("操作的系统用户不存在"));

        logger.debug("检查系统组织是否存在");
        final SysOrganization sysOrganization = sysOrganizationMapper.selectById(sysOrganizationId);
        ApiAssert.isNotNull(sysOrganization, ErrorResponse.create("系统组织不存在"));

        //初始化父级组织默认值
        parentId = ObjectUtils.defaultIfNull(parentId, "");

        logger.debug("插入系统组织");
        sysOrganization.setParentId(parentId);
        sysOrganization.setName(name);
        sysOrganization.setFullName(fullName);
        sysOrganization.setType(type);
        sysOrganization.setRemark(remark);
        sysOrganization.setStatus(status);
        sysOrganization.setCreateSysUserId(operatedSysUserId);
        sysOrganization.setCreateDateTime(LocalDateTime.now());
        final int i = sysOrganizationMapper.updateById(sysOrganization);

        ApiAssert.isEqualToOne(i, ErrorResponse.create("系统组织修改失败"));
    }

    @Override
    public void deleteSysOrganization(String sysOrganizatioId) {
        ApiAssert.isNotBlank(sysOrganizatioId, ErrorResponse.create("系统组织不能为空"));
        int i = sysOrganizationMapper.deleteById(sysOrganizatioId);
        ApiAssert.isEqualToOne(i, ErrorResponse.create("删除系统组织失败"));
    }

    @Override
    public void enabledSysOrganization(String sysOrganizatioId, String operatedSysUserId) {
        ApiAssert.isNotBlank(sysOrganizatioId, ErrorResponse.create("系统组织不能为空"));
        logger.debug("检查操作的系统用户");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, ErrorResponse.create("操作的系统用户不存在"));

        logger.debug("检查系统组织是否存在");
        final SysOrganization sysOrganization = sysOrganizationMapper.selectById(sysOrganizatioId);
        ApiAssert.isNotNull(sysOrganization, ErrorResponse.create("系统组织不存在"));

        logger.debug("启用系统组织");
        sysOrganization.setStatus(StatusEnum.ENABLED);
        sysOrganization.setUpdateSysUserId(operatedSysUserId);
        sysOrganization.setUpdateDateTime(LocalDateTime.now());
        int i = sysOrganizationMapper.updateById(sysOrganization);
        ApiAssert.isEqualToOne(i, ErrorResponse.create("系统组织启用失败"));
    }

    @Override
    public void disableSysOrganization(String sysOrganizatioId, String operatedSysUserId) {
        ApiAssert.isNotBlank(sysOrganizatioId, ErrorResponse.create("系统组织不能为空"));
        logger.debug("检查操作的系统用户");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, ErrorResponse.create("操作的系统用户不存在"));

        logger.debug("检查系统组织是否存在");
        final SysOrganization sysOrganization = sysOrganizationMapper.selectById(sysOrganizatioId);
        ApiAssert.isNotNull(sysOrganization, ErrorResponse.create("系统组织不存在"));

        logger.debug("禁用系统组织");
        sysOrganization.setStatus(StatusEnum.DISABLE);
        sysOrganization.setUpdateSysUserId(operatedSysUserId);
        sysOrganization.setUpdateDateTime(LocalDateTime.now());
        int i = sysOrganizationMapper.updateById(sysOrganization);
        ApiAssert.isEqualToOne(i, ErrorResponse.create("系统组织禁用失败"));
    }

    @Override
    public List<SysOrganization> getEnabledSysOrganizationListBySysUserId(String sysUserId) {
        ApiAssert.isNotBlank(sysUserId, ErrorResponse.create("系统用户ID不能为空"));

        logger.debug("获取系统用户");
        SysUser operatedSysUser = sysUserMapper.selectById(sysUserId);
        ApiAssert.isNotNull(operatedSysUser, ErrorResponse.create("系统用户不存在"));

        final List<SysOrganizationUser> sysOrganizationUserList = sysOrganizationUserMapper.selectList(Wrappers.<SysOrganizationUser>lambdaQuery().eq(SysOrganizationUser::getSysUserId, sysUserId));
        if (CollectionUtils.isNotEmpty(sysOrganizationUserList)) {
            return Collections.emptyList();
        }

        final List<String> sysOrganizationIdList = sysOrganizationUserList.stream()
                .map(SysOrganizationUser::getSysOrganizationId)
                .collect(Collectors.toList());
        return sysOrganizationMapper.selectSuperiorByIdList(sysOrganizationIdList,
                Wrappers.<SysOrganization>lambdaQuery().eq(SysOrganization::getStatus, StatusEnum.ENABLED));
    }
}
