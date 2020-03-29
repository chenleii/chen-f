package com.chen.f.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.f.admin.service.ISysPermissionService;
import com.chen.f.common.mapper.SysPermissionApiMapper;
import com.chen.f.common.mapper.SysPermissionMapper;
import com.chen.f.common.mapper.SysRolePermissionMapper;
import com.chen.f.common.mapper.SysUserMapper;
import com.chen.f.common.pojo.SysPermission;
import com.chen.f.common.pojo.SysPermissionApi;
import com.chen.f.common.pojo.SysUser;
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

    @Override
    public IPage<SysPermission> getSysPermissionPage(Long pageIndex, Long pageNumber, String name, String remark, StatusEnum status) {
        LambdaQueryWrapper<SysPermission> queryWrapper = Wrappers.<SysPermission>lambdaQuery();
        if (StringUtils.isNotBlank(name)) {
            queryWrapper.eq(SysPermission::getName, name);
        }
        if (StringUtils.isNotBlank(remark)) {
            queryWrapper.like(SysPermission::getRemark, remark);
        }
        if (Objects.nonNull(status)) {
            queryWrapper.eq(SysPermission::getStatus, status);
        }
        return sysPermissionMapper.selectPage(new Page<>(pageIndex, pageNumber), queryWrapper);
    }

    @Override
    public List<SysPermission> getEnabledSysPermissionList() {
        return sysPermissionMapper.selectList(Wrappers.<SysPermission>lambdaQuery().eq(SysPermission::getStatus, StatusEnum.ENABLED));
    }

    @Override
    public SysPermission getSysPermission(String sysPermissionId) {
        ApiAssert.isNotBlank(sysPermissionId, ErrorResponse.create("系统权限id不能为空"));
        return sysPermissionMapper.selectById(sysPermissionId);
    }


    @Override
    public void createSysPermission(String name, String remark, StatusEnum status, String operatedSysUserId) {
        ApiAssert.isNotBlank(name, ErrorResponse.create("系统权限名称不能为空"));
        ApiAssert.isNotNull(status, ErrorResponse.create("系统权限状态不能为空"));
        ApiAssert.isNotBlank(operatedSysUserId, ErrorResponse.create("操作的系统用户id不能为空"));
        logger.debug("检查操作的系统用户是否存在");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, ErrorResponse.create("操作的系统用户不存在"));
        logger.debug("插入系统权限");
        SysPermission sysPermission = new SysPermission();
        sysPermission.setName(name);
        sysPermission.setRemark(remark);
        sysPermission.setStatus(status);
        sysPermission.setCreateSysUserId(operatedSysUserId);
        sysPermission.setCreateDateTime(LocalDateTime.now());
        int i = sysPermissionMapper.insert(sysPermission);
        ApiAssert.isEqualToOne(i, ErrorResponse.create("创建系统权限失败"));
    }

    @Override
    public void updateSysPermission(String sysPermissionId, String name, String remark, StatusEnum status, String operatedSysUserId) {
        ApiAssert.isNotBlank(sysPermissionId, ErrorResponse.create("系统权限id不能为空"));
        ApiAssert.isNotBlank(name, ErrorResponse.create("系统权限名称不能为空"));
        ApiAssert.isNotNull(status, ErrorResponse.create("系统权限状态不能为空"));
        ApiAssert.isNotBlank(operatedSysUserId, ErrorResponse.create("操作的系统用户id不能为空"));
        logger.debug("检查操作的系统用户是否存在");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, ErrorResponse.create("操作的系统用户不存在"));
        logger.debug("检查系统权限是否存在");
        SysPermission sysPermission = sysPermissionMapper.selectById(sysPermissionId);
        ApiAssert.isNotNull(sysPermission, ErrorResponse.create("修改系统权限失败"));
        logger.debug("修改系统权限");
        sysPermission.setName(name);
        sysPermission.setRemark(remark);
        sysPermission.setStatus(status);
        sysPermission.setUpdateSysUserId(operatedSysUserId);
        sysPermission.setUpdateDateTime(LocalDateTime.now());
        int i = sysPermissionMapper.updateById(sysPermission);
        ApiAssert.isEqualToOne(i, ErrorResponse.create("修改系统权限失败"));
    }

    @Override
    public void setSysApiOfSysPermission(String sysPermissionId, List<String> sysApiIdList, String operatedSysUserId) {
        ApiAssert.isNotBlank(sysPermissionId, ErrorResponse.create("设置的系统权限id不能为空"));
        //ApiAssert.isNotEmpty(sysMenuIdList, ErrorResponse.create("设置的系统API id不能为空"));
        ApiAssert.isNotBlank(operatedSysUserId, ErrorResponse.create("操作系统用户id不能为空"));
        SysPermission sysPermission = sysPermissionMapper.selectById(sysPermissionId);
        ApiAssert.isNotNull(sysPermission, ErrorResponse.create("系统权限不存在"));
        logger.debug("检查操作的系统用户是否存在");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, ErrorResponse.create("操作的系统用户不存在"));

        List<SysPermissionApi> sysPermissionApiList = Collections.emptyList();
        if (CollectionUtils.isNotEmpty(sysApiIdList)) {
            //转换
            sysPermissionApiList = sysApiIdList.stream()
                    .map((sysApiId) -> {
                        SysPermissionApi sysPermissionApi = new SysPermissionApi();
                        sysPermissionApi.setSysPermissionId(sysPermissionId);
                        sysPermissionApi.setSysApiId(sysApiId);
                        sysPermissionApi.setCreateSysUserId(operatedSysUserId);
                        sysPermissionApi.setCreateDateTime(LocalDateTime.now());
                        return sysPermissionApi;
                    }).collect(Collectors.toList());
        }
        logger.debug("删除系统权限[{}]所有系统API关联", sysPermissionId);
        sysPermissionApiMapper.delete(Wrappers.<SysPermissionApi>lambdaQuery().eq(SysPermissionApi::getSysPermissionId, sysPermissionId));
        logger.debug("插入系统权限API关联");
        sysPermissionApiMapper.insertBatch(sysPermissionApiList);
    }

    @Override
    public void deleteSysPermission(String sysPermissionId) {
        ApiAssert.isNotBlank(sysPermissionId, ErrorResponse.create("系统权限id不能为空"));
        logger.debug("检查系统权限是否存在");
        ApiAssert.isEqualToOne(sysPermissionMapper.selectCount(Wrappers.<SysPermission>lambdaQuery().eq(SysPermission::getId, sysPermissionId)), ErrorResponse.create(String.format("没有找到系统权限[%s]", sysPermissionId)));
        logger.debug("删除系统权限");
        int i = sysPermissionMapper.deleteById(sysPermissionId);
        ApiAssert.isEqualToOne(i, ErrorResponse.create("删除系统权限失败"));
    }

    @Override
    public void enabledSysPermission(String sysPermissionId, String operatedSysUserId) {
        ApiAssert.isNotBlank(sysPermissionId, ErrorResponse.create("系统权限id不能为空"));
        ApiAssert.isNotBlank(operatedSysUserId, ErrorResponse.create("操作的系统用户id不能为空"));
        logger.debug("检查操作的系统用户是否存在");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, ErrorResponse.create("操作的系统用户不存在"));
        logger.debug("检查系统权限是否存在");
        SysPermission sysPermission = sysPermissionMapper.selectById(sysPermissionId);
        ApiAssert.isNotNull(sysPermission, ErrorResponse.create("修改系统权限失败"));
        logger.debug("启用系统权限");
        sysPermission.setStatus(StatusEnum.ENABLED);
        sysPermission.setUpdateSysUserId(operatedSysUserId);
        sysPermission.setUpdateDateTime(LocalDateTime.now());
        int i = sysPermissionMapper.update(sysPermission, Wrappers.<SysPermission>lambdaQuery().eq(SysPermission::getId, sysPermissionId).ne(SysPermission::getStatus, StatusEnum.ENABLED));
        ApiAssert.isEqualToOne(i, ErrorResponse.create("修改系统权限失败"));
    }

    @Override
    public void disableSysPermission(String sysPermissionId, String operatedSysUserId) {
        ApiAssert.isNotBlank(sysPermissionId, ErrorResponse.create("系统权限id不能为空"));
        ApiAssert.isNotBlank(operatedSysUserId, ErrorResponse.create("操作的系统用户id不能为空"));
        logger.debug("检查操作的系统用户是否存在");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, ErrorResponse.create("操作的系统用户不存在"));
        logger.debug("检查系统权限是否存在");
        SysPermission sysPermission = sysPermissionMapper.selectById(sysPermissionId);
        ApiAssert.isNotNull(sysPermission, ErrorResponse.create("修改系统权限失败"));
        logger.debug("禁用系统权限");
        sysPermission.setStatus(StatusEnum.DISABLE);
        sysPermission.setUpdateSysUserId(operatedSysUserId);
        sysPermission.setUpdateDateTime(LocalDateTime.now());
        int i = sysPermissionMapper.update(sysPermission, Wrappers.<SysPermission>lambdaQuery().eq(SysPermission::getId, sysPermissionId).ne(SysPermission::getStatus, StatusEnum.DISABLE));
        ApiAssert.isEqualToOne(i, ErrorResponse.create("修改系统权限失败"));
    }
}
