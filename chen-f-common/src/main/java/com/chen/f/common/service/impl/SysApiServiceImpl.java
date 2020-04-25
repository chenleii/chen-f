package com.chen.f.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
import com.chen.f.common.service.ISysApiService;
import com.chen.f.core.api.ApiAssert;
import com.chen.f.core.api.response.error.ErrorResponse;
import com.chen.f.core.page.Page;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

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
    public List<SysApi> getAllSysApiList() {
        return sysApiMapper.selectAll();
    }
    
    @Override
    public List<SysApi> getEnabledSysApiList() {
        return sysApiMapper.selectList(Wrappers.<SysApi>lambdaQuery().eq(SysApi::getStatus, StatusEnum.ENABLED));
    }

    @Override
    public Page<SysApi> getSysApiPage(Page<SysApi> page, String name, String url, SysApiHttpMethodEnum httpMethod, SysApiTypeEnum type, String remark, StatusEnum status) {
        LambdaQueryWrapper<SysApi> lambdaQueryWrapper = Wrappers.<SysApi>lambdaQuery();
        lambdaQueryWrapper.eq(StringUtils.isNotBlank(name), SysApi::getName, name);
        lambdaQueryWrapper.like(StringUtils.isNotBlank(url), SysApi::getUrl, StringUtils.replace(url, "*", "%"));
        lambdaQueryWrapper.eq(Objects.nonNull(httpMethod), SysApi::getHttpMethod, httpMethod);
        lambdaQueryWrapper.eq(Objects.nonNull(type), SysApi::getType, type);
        lambdaQueryWrapper.like(StringUtils.isNotBlank(remark), SysApi::getRemark, remark);
        lambdaQueryWrapper.eq(Objects.nonNull(status), SysApi::getStatus, status);
        return sysApiMapper.selectPage(page, lambdaQueryWrapper);
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
        //ApiAssert.isNotBlank(url, ErrorResponse.create("系统接口URL不能为空"));
        ApiAssert.isNotNull(httpMethod, ErrorResponse.create("系统接口HTTP请求方法不能为空"));
        ApiAssert.isNotNull(type, ErrorResponse.create("系统接口类型不能为空"));
        //ApiAssert.isNotBlank(remark, ErrorResponse.create("系统接口备注不能为空"));
        ApiAssert.isNotNull(status, ErrorResponse.create("系统接口状态不能为空"));
        ApiAssert.isNotBlank(operatedSysUserId, ErrorResponse.create("操作的系统用户ID不能为空"));

        logger.debug("检查操作的系统用户");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, ErrorResponse.create("操作的系统用户不存在"));

        url = ObjectUtils.defaultIfNull(url, "");
        
        logger.debug("插入系统用户");
        SysApi sysApi = new SysApi();
        sysApi.setName(name);
        sysApi.setUrl(url);
        sysApi.setHttpMethod(httpMethod);
        sysApi.setType(type);
        sysApi.setRemark(remark);
        sysApi.setStatus(status);
        sysApi.setUpdatedDateTime(LocalDateTime.now());
        sysApi.setCreatedDateTime(LocalDateTime.now());
        sysApi.setUpdatedSysUserId(operatedSysUserId);
        sysApi.setCreatedSysUserId(operatedSysUserId);
        int i = sysApiMapper.insert(sysApi);
        ApiAssert.isEqualToOne(i, ErrorResponse.create("插入系统接口失败"));
    }

    @Override
    public void updateSysApi(String sysApiId, String name, String url, SysApiHttpMethodEnum httpMethod, SysApiTypeEnum type, String remark, StatusEnum status, String operatedSysUserId) {
        ApiAssert.isNotBlank(sysApiId, ErrorResponse.create("系统接口ID不能为空"));
        ApiAssert.isNotBlank(name, ErrorResponse.create("系统接口名称不能为空"));
        //ApiAssert.isNotBlank(url, ErrorResponse.create("系统接口URL不能为空"));
        ApiAssert.isNotNull(httpMethod, ErrorResponse.create("系统接口HTTP请求方法不能为空"));
        ApiAssert.isNotNull(type, ErrorResponse.create("系统接口类型不能为空"));
        //ApiAssert.isNotBlank(remark, ErrorResponse.create("系统接口备注不能为空"));
        ApiAssert.isNotNull(status, ErrorResponse.create("系统接口状态不能为空"));
        ApiAssert.isNotBlank(operatedSysUserId, ErrorResponse.create("操作的系统用户ID不能为空"));

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
        sysApi.setCreatedDateTime(LocalDateTime.now());
        sysApi.setCreatedSysUserId(operatedSysUserId);
        int i = sysApiMapper.updateById(sysApi);
        ApiAssert.isEqualToOne(i, ErrorResponse.create("修改系统接口失败"));
    }

    @Override
    public void deleteSysApi(String sysApiId) {
        ApiAssert.isNotBlank(sysApiId, ErrorResponse.create("系统接口ID不能为空"));
        
        SysApi sysApi = sysApiMapper.selectById(sysApiId);
        ApiAssert.isNotNull(sysApi, ErrorResponse.create("系统接口不存在"));

        int i = sysApiMapper.deleteById(sysApiId);
        ApiAssert.isEqualToOne(i, ErrorResponse.create("删除系统接口失败"));

        //删除系统角色接口
        sysRoleApiMapper.delete(Wrappers.<SysRoleApi>lambdaQuery().eq(SysRoleApi::getSysApiId, sysApiId));

        //删除系统权限接口
        sysPermissionApiMapper.delete(Wrappers.<SysPermissionApi>lambdaQuery().eq(SysPermissionApi::getSysApiId, sysApiId));
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
        sysApi.setUpdatedSysUserId(operatedSysUserId);
        sysApi.setUpdatedDateTime(LocalDateTime.now());
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
        sysApi.setStatus(StatusEnum.DISABLED);
        sysApi.setUpdatedSysUserId(operatedSysUserId);
        sysApi.setUpdatedDateTime(LocalDateTime.now());
        int i = sysApiMapper.updateById(sysApi);
        ApiAssert.isEqualToOne(i, ErrorResponse.create("修改系统接口失败"));
    }
}
