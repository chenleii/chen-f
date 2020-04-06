package com.chen.f.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.f.admin.service.ISysApiService;
import com.chen.f.common.api.ApiAssert;
import com.chen.f.common.api.response.error.ErrorResponse;
import com.chen.f.common.mapper.SysApiMapper;
import com.chen.f.common.mapper.SysPermissionApiMapper;
import com.chen.f.common.mapper.SysRoleApiMapper;
import com.chen.f.common.mapper.SysUserMapper;
import com.chen.f.common.pojo.SysApi;
import com.chen.f.common.pojo.SysPermissionApi;
import com.chen.f.common.pojo.SysRoleApi;
import com.chen.f.common.pojo.SysUser;
import com.chen.f.common.pojo.enums.StatusEnum;
import com.chen.f.common.pojo.enums.SysApiHttpMethodEnum;
import com.chen.f.common.pojo.enums.SysApiTypeEnum;
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
 * 系统接口表 服务实现类
 * </p>
 *
 * @author chen
 * @since 2019-03-05
 */
@Service
public class SysApiServiceImpl extends ServiceImpl<SysApiMapper, SysApi> implements ISysApiService {
    protected static final Logger logger = LoggerFactory.getLogger(SysApiServiceImpl.class);

    @Autowired
    private SysApiMapper sysApiMapper;

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysRoleApiMapper sysRoleApiMapper;
    @Autowired
    private SysPermissionApiMapper sysPermissionApiMapper;


    @Override
    public IPage<SysApi> getSysApiPage(Long pageIndex, Long pageNumber,
                                       String name, String url, SysApiHttpMethodEnum httpMethod, SysApiTypeEnum type, String remark, StatusEnum status) {
        LambdaQueryWrapper<SysApi> lambdaQueryWrapper = Wrappers.<SysApi>lambdaQuery();
        if (StringUtils.isNotBlank(name)) {
            lambdaQueryWrapper.eq(SysApi::getName, name);
        }
        if (StringUtils.isNotBlank(url)) {
            if (StringUtils.contains(url, "*")) {
                url = StringUtils.replace(url, "*", "%");
                lambdaQueryWrapper.eq(SysApi::getUrl, url);
            } else {
                lambdaQueryWrapper.like(SysApi::getUrl, url);
            }
        }
        if (Objects.nonNull(httpMethod)) {
            lambdaQueryWrapper.eq(SysApi::getHttpMethod, httpMethod);
        }
        if (Objects.nonNull(type)) {
            lambdaQueryWrapper.eq(SysApi::getType, type);
        }
        if (StringUtils.isNotBlank(remark)) {
            lambdaQueryWrapper.like(SysApi::getRemark, remark);
        }
        if (Objects.nonNull(status)) {
            lambdaQueryWrapper.eq(SysApi::getStatus, status);
        }
        return sysApiMapper.selectPage(new Page<>(pageIndex, pageNumber), lambdaQueryWrapper);
    }

    @Override
    public List<SysApi> getEnabledSysApiList() {
        return sysApiMapper.selectList(Wrappers.<SysApi>lambdaQuery().eq(SysApi::getStatus, StatusEnum.ENABLED));
    }
    
    @Override
    public List<SysApi> getEnabledSysApiListBySysRoleIdList(List<String> sysRoleIdList) {
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
        return sysApiMapper.selectList(Wrappers.<SysApi>lambdaQuery().in(SysApi::getId, sysApiIdList).eq(SysApi::getStatus, StatusEnum.ENABLED));
    }

    @Override
    public List<SysApi> getEnabledSysApiListBySysPermissionIdList(List<String> sysPermissionIdList) {
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
        return sysApiMapper.selectList(Wrappers.<SysApi>lambdaQuery().in(SysApi::getId, sysApiIdList).eq(SysApi::getStatus, StatusEnum.ENABLED));
    }

    @Override
    public SysApi getSysApi(String sysApiId) {
        ApiAssert.isNotBlank(sysApiId, ErrorResponse.create("系统接口ID不能为空"));
        return sysApiMapper.selectById(sysApiId);
    }

    @Override
    public void createSysApi(String name, String url, SysApiHttpMethodEnum httpMethod, SysApiTypeEnum type, String remark,
                             StatusEnum status, String operatedSysUserId) {
        ApiAssert.isNotBlank(name, ErrorResponse.create("系统接口名称不能为空"));
        ApiAssert.isNotBlank(name, ErrorResponse.create("系统接口URL不能为空"));
        ApiAssert.isNotBlank(name, ErrorResponse.create("系统接口HTTP请求方法不能为空"));
        ApiAssert.isNotBlank(name, ErrorResponse.create("系统接口类型不能为空"));
        //ApiAssert.isNotBlank(name, ErrorResponse.create("系统接口备注不能为空"));
        ApiAssert.isNotBlank(name, ErrorResponse.create("系统接口状态不能为空"));
        ApiAssert.isNotBlank(name, ErrorResponse.create("操作的系统用户ID不能为空"));

        logger.debug("检查操作的系统用户");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, ErrorResponse.create("操作的系统用户不存在"));

        logger.debug("插入系统用户");
        SysApi sysApi = new SysApi();
        sysApi.setName(name);
        sysApi.setUrl(url);
        sysApi.setHttpMethod(httpMethod);
        sysApi.setType(type);
        sysApi.setRemark(remark);
        sysApi.setStatus(status);
        sysApi.setCreateDateTime(LocalDateTime.now());
        sysApi.setCreateSysUserId(operatedSysUserId);
        int i = sysApiMapper.insert(sysApi);
        ApiAssert.isEqualToOne(i, ErrorResponse.create("插入系统接口失败"));
    }

    @Override
    public void updateSysApi(String sysApiId, String name, String url, SysApiHttpMethodEnum httpMethod, SysApiTypeEnum type, String remark, StatusEnum status, String operatedSysUserId) {
        ApiAssert.isNotBlank(sysApiId, ErrorResponse.create("系统接口ID不能为空"));
        ApiAssert.isNotBlank(name, ErrorResponse.create("系统接口名称不能为空"));
        ApiAssert.isNotBlank(name, ErrorResponse.create("系统接口URL不能为空"));
        ApiAssert.isNotBlank(name, ErrorResponse.create("系统接口HTTP请求方法不能为空"));
        ApiAssert.isNotBlank(name, ErrorResponse.create("系统接口类型不能为空"));
        //ApiAssert.isNotBlank(name, ErrorResponse.create("系统接口备注不能为空"));
        ApiAssert.isNotBlank(name, ErrorResponse.create("系统接口状态不能为空"));
        ApiAssert.isNotBlank(name, ErrorResponse.create("操作的系统用户ID不能为空"));

        logger.debug("检查操作的系统用户");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, ErrorResponse.create("操作的系统用户不存在"));

        logger.debug("检查系统接口是否存在");
        SysApi sysApi = sysApiMapper.selectById(sysApiId);
        ApiAssert.isNotNull(sysApi, ErrorResponse.create("系统接口不存在"));
        logger.debug("修改系统用户");
        sysApi.setName(name);
        sysApi.setUrl(url);
        sysApi.setHttpMethod(httpMethod);
        sysApi.setType(type);
        sysApi.setRemark(remark);
        sysApi.setStatus(status);
        sysApi.setCreateDateTime(LocalDateTime.now());
        sysApi.setCreateSysUserId(operatedSysUserId);
        int i = sysApiMapper.updateById(sysApi);
        ApiAssert.isEqualToOne(i, ErrorResponse.create("修改系统接口失败"));
    }

    @Override
    public void deleteSysApi(String sysApiId) {
        ApiAssert.isNotBlank(sysApiId, ErrorResponse.create("系统接口ID不能为空"));
        int i = sysApiMapper.deleteById(sysApiId);
        ApiAssert.isEqualToOne(i, ErrorResponse.create("删除系统接口失败"));
    }

    @Override
    public void enabledSysApi(String sysApiId, String operatedSysUserId) {
        ApiAssert.isNotBlank(sysApiId, ErrorResponse.create("系统接口ID不能为空"));
        ApiAssert.isNotBlank(operatedSysUserId, ErrorResponse.create("操作的系统用户ID不能为空"));
        logger.debug("检查系统接口是否存在");
        SysApi sysApi = sysApiMapper.selectById(sysApiId);
        ApiAssert.isNotNull(sysApi, ErrorResponse.create("系统接口不存在"));
        logger.debug("启用系统接口");
        sysApi.setStatus(StatusEnum.ENABLED);
        sysApi.setUpdateSysUserId(operatedSysUserId);
        sysApi.setUpdateDateTime(LocalDateTime.now());
        int i = sysApiMapper.updateById(sysApi);
        ApiAssert.isEqualToOne(i, ErrorResponse.create("修改系统接口失败"));
    }

    @Override
    public void disableSysApi(String sysApiId, String operatedSysUserId) {
        ApiAssert.isNotBlank(sysApiId, ErrorResponse.create("系统接口ID不能为空"));
        ApiAssert.isNotBlank(operatedSysUserId, ErrorResponse.create("操作的系统用户ID不能为空"));
        logger.debug("检查系统接口是否存在");
        SysApi sysApi = sysApiMapper.selectById(sysApiId);
        ApiAssert.isNotNull(sysApi, ErrorResponse.create("系统接口不存在"));
        logger.debug("禁用系统接口");
        sysApi.setStatus(StatusEnum.DISABLE);
        sysApi.setUpdateSysUserId(operatedSysUserId);
        sysApi.setUpdateDateTime(LocalDateTime.now());
        int i = sysApiMapper.updateById(sysApi);
        ApiAssert.isEqualToOne(i, ErrorResponse.create("修改系统接口失败"));
    }
}
