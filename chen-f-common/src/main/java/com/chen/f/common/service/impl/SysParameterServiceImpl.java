package com.chen.f.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.f.common.mapper.SysParameterMapper;
import com.chen.f.common.mapper.SysUserMapper;
import com.chen.f.common.pojo.SysParameter;
import com.chen.f.common.pojo.SysUser;
import com.chen.f.common.pojo.enums.StatusEnum;
import com.chen.f.common.pojo.enums.SysParameterTypeEnum;
import com.chen.f.common.pojo.enums.TypeTypeEnum;
import com.chen.f.common.service.ISysParameterService;
import com.chen.f.common.api.ApiAssert;
import com.chen.f.common.api.response.error.ErrorResponse;
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
 * 系统参数表 服务实现类
 * </p>
 *
 * @author chen
 * @since 2018-12-02
 */
@Service
public class SysParameterServiceImpl extends ServiceImpl<SysParameterMapper, SysParameter> implements ISysParameterService {
    protected static final Logger logger = LoggerFactory.getLogger(SysParameterServiceImpl.class);

    @Autowired
    private SysParameterMapper sysParameterMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public List<SysParameter> getEnabledSysParameterList() {
        return sysParameterMapper.selectList(Wrappers.<SysParameter>lambdaQuery().eq(SysParameter::getStatus, StatusEnum.ENABLED));
    }

    @Override
    public IPage<SysParameter> getSysParameterPage(Long pageIndex, Long pageNumber,
                                                   String code, String name, String value, TypeTypeEnum valueType, SysParameterTypeEnum type, String remark, StatusEnum statusEnum) {
        LambdaQueryWrapper<SysParameter> lambdaQueryWrapper = Wrappers.<SysParameter>lambdaQuery();
        lambdaQueryWrapper.eq(StringUtils.isNotBlank(code), SysParameter::getCode, code);
        lambdaQueryWrapper.eq(StringUtils.isNotBlank(name), SysParameter::getName, name);
        lambdaQueryWrapper.eq(StringUtils.isNotBlank(value), SysParameter::getValue, value);
        lambdaQueryWrapper.eq(Objects.nonNull(valueType), SysParameter::getValueType, valueType);
        lambdaQueryWrapper.eq(Objects.nonNull(type), SysParameter::getType, type);
        lambdaQueryWrapper.like(StringUtils.isNotBlank(remark), SysParameter::getRemark, remark);
        lambdaQueryWrapper.eq(Objects.nonNull(statusEnum), SysParameter::getStatus, statusEnum);
        return sysParameterMapper.selectPage(new Page<>(pageIndex, pageNumber), lambdaQueryWrapper);
    }

    @Override
    public SysParameter getSysParameter(String sysParameterId) {
        ApiAssert.isNotBlank(sysParameterId, ErrorResponse.create("系统参数ID不能为空"));
        return sysParameterMapper.selectById(sysParameterId);
    }

    @Override
    public SysParameter getSysParameterByCode(String code) {
        ApiAssert.isNotBlank(code, ErrorResponse.create("系统参数编码不能为空"));
        return sysParameterMapper.selectOne(Wrappers.<SysParameter>lambdaQuery().eq(SysParameter::getCode, code));
    }

    @Override
    public void createSysParameter(String code, String name, String value,
                                   TypeTypeEnum valueType, SysParameterTypeEnum type, String remark, StatusEnum status, String operatedSysUserId) {
        ApiAssert.isNotBlank(code, ErrorResponse.create("系统参数编码不能为空"));
        ApiAssert.isNotBlank(name, ErrorResponse.create("系统参数名称不能为空"));
        ApiAssert.isNotBlank(value, ErrorResponse.create("系统参数值不能为空"));
        ApiAssert.isNotNull(valueType, ErrorResponse.create("系统参数值类型不能为空"));
        ApiAssert.isNotNull(type, ErrorResponse.create("系统参数类型不能为空"));
        //ApiAssert.isNotBlank(remark, ErrorResponse.create("系统参数备注不能为空"));
        ApiAssert.isNotNull(status, ErrorResponse.create("系统参数状态不能为空"));
        ApiAssert.isNotBlank(operatedSysUserId, ErrorResponse.create("操作的系统用户ID不能为空"));

        logger.debug("检查操作的系统用户");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, ErrorResponse.create("操作的系统用户不存在"));

        logger.debug("插入系统参数");
        SysParameter sysParameter = new SysParameter();
        sysParameter.setId(code);
        sysParameter.setCode(code);
        sysParameter.setName(name);
        sysParameter.setValue(value);
        sysParameter.setValueType(valueType);
        sysParameter.setType(type);
        sysParameter.setRemark(remark);
        sysParameter.setStatus(status);
        sysParameter.setUpdatedSysUserId(operatedSysUserId);
        sysParameter.setCreatedSysUserId(operatedSysUserId);
        sysParameter.setUpdatedDateTime(LocalDateTime.now());
        sysParameter.setCreatedDateTime(LocalDateTime.now());
        int i = sysParameterMapper.insert(sysParameter);
        ApiAssert.isEqualToOne(i, ErrorResponse.create("创建系统参数失败"));
    }

    @Override
    public void updateSysParameter(String sysParameterId, String code, String name,
                                   String value, TypeTypeEnum valueType, SysParameterTypeEnum type, String remark, StatusEnum status, String operatedSysUserId) {
        ApiAssert.isNotBlank(sysParameterId, ErrorResponse.create("系统参数ID不能为空"));
        ApiAssert.isNotBlank(code, ErrorResponse.create("系统参数编码不能为空"));
        ApiAssert.isNotBlank(name, ErrorResponse.create("系统参数名称不能为空"));
        ApiAssert.isNotBlank(value, ErrorResponse.create("系统参数值不能为空"));
        ApiAssert.isNotNull(type, ErrorResponse.create("系统参数类型不能为空"));
        ApiAssert.isNotNull(valueType, ErrorResponse.create("系统参数值类型不能为空"));
        //ApiAssert.isNotBlank(remark, ErrorResponse.create("系统参数备注不能为空"));
        ApiAssert.isNotNull(status, ErrorResponse.create("系统参数状态不能为空"));
        ApiAssert.isNotBlank(operatedSysUserId, ErrorResponse.create("操作的系统用户ID不能为空"));

        logger.debug("检查操作的系统用户");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, ErrorResponse.create("操作的系统用户不存在"));

        logger.debug("检查系统参数是否存在");
        SysParameter sysParameter = sysParameterMapper.selectById(sysParameterId);
        ApiAssert.isNotNull(sysParameter, ErrorResponse.create("系统参数不存在"));

        logger.debug("修改系统参数");
        sysParameter.setId(sysParameterId);
        sysParameter.setCode(code);
        sysParameter.setName(name);
        sysParameter.setValue(value);
        sysParameter.setValueType(valueType);
        sysParameter.setType(type);
        sysParameter.setRemark(remark);
        sysParameter.setStatus(status);
        sysParameter.setUpdatedSysUserId(operatedSysUserId);
        sysParameter.setUpdatedDateTime(LocalDateTime.now());
        int i = sysParameterMapper.updateById(sysParameter);
        ApiAssert.isEqualToOne(i, ErrorResponse.create("修改系统参数失败"));
    }

    @Override
    public void deleteSysParameter(String sysParameterId) {
        ApiAssert.isNotBlank(sysParameterId, ErrorResponse.create("系统参数ID不能为空"));
        int i = sysParameterMapper.deleteById(sysParameterId);
        ApiAssert.isEqualToOne(i, ErrorResponse.create("删除系统参数失败"));
    }

    @Override
    public void deleteSysParameterByCode(String code) {
        ApiAssert.isNotBlank(code, ErrorResponse.create("系统参数编码不能为空"));
        int i = sysParameterMapper.delete(Wrappers.<SysParameter>lambdaQuery().eq(SysParameter::getCode, code));
        ApiAssert.isEqualToOne(i, ErrorResponse.create("删除系统参数失败"));
    }

    @Override
    public void enabledSysParameter(String sysParameterId, String operatedSysUserId) {
        ApiAssert.isNotBlank(sysParameterId, ErrorResponse.create("系统参数ID不能为空"));
        ApiAssert.isNotBlank(operatedSysUserId, ErrorResponse.create("操作的系统用户ID不能为空"));

        logger.debug("检查操作的系统用户");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, ErrorResponse.create("操作的系统用户不存在"));

        logger.debug("检查系统参数是否存在");
        SysParameter sysParameter = sysParameterMapper.selectById(sysParameterId);
        ApiAssert.isNotNull(sysParameter, ErrorResponse.create("系统参数不存在"));

        logger.debug("启用系统参数");
        sysParameter.setStatus(StatusEnum.ENABLED);
        sysParameter.setUpdatedSysUserId(operatedSysUserId);
        sysParameter.setUpdatedDateTime(LocalDateTime.now());
        int i = sysParameterMapper.updateById(sysParameter);
        ApiAssert.isEqualToOne(i, ErrorResponse.create("启用系统参数失败"));
    }

    @Override
    public void enabledSysParameterByCode(String code, String operatedSysUserId) {
        ApiAssert.isNotBlank(code, ErrorResponse.create("系统参数编码不能为空"));
        ApiAssert.isNotBlank(operatedSysUserId, ErrorResponse.create("操作的系统用户ID不能为空"));

        logger.debug("检查操作的系统用户");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, ErrorResponse.create("操作的系统用户不存在"));

        logger.debug("检查系统参数是否存在");
        SysParameter sysParameter = sysParameterMapper.selectOne(Wrappers.<SysParameter>lambdaQuery().eq(SysParameter::getCode, code));
        ApiAssert.isNotNull(sysParameter, ErrorResponse.create("系统参数不存在"));

        logger.debug("启用系统参数");
        sysParameter.setStatus(StatusEnum.ENABLED);
        sysParameter.setUpdatedSysUserId(operatedSysUserId);
        sysParameter.setUpdatedDateTime(LocalDateTime.now());
        int i = sysParameterMapper.updateById(sysParameter);
        ApiAssert.isEqualToOne(i, ErrorResponse.create("启用系统参数失败"));
    }

    @Override
    public void disableSysParameter(String sysParameterId, String operatedSysUserId) {
        ApiAssert.isNotBlank(sysParameterId, ErrorResponse.create("系统参数ID不能为空"));
        ApiAssert.isNotBlank(operatedSysUserId, ErrorResponse.create("操作的系统用户ID不能为空"));

        logger.debug("检查操作的系统用户");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, ErrorResponse.create("操作的系统用户不存在"));

        logger.debug("检查系统参数是否存在");
        SysParameter sysParameter = sysParameterMapper.selectById(sysParameterId);
        ApiAssert.isNotNull(sysParameter, ErrorResponse.create("系统参数不存在"));

        logger.debug("禁用系统参数");
        sysParameter.setStatus(StatusEnum.DISABLE);
        sysParameter.setUpdatedSysUserId(operatedSysUserId);
        sysParameter.setUpdatedDateTime(LocalDateTime.now());
        int i = sysParameterMapper.updateById(sysParameter);
        ApiAssert.isEqualToOne(i, ErrorResponse.create("禁用系统参数失败"));
    }

    @Override
    public void disableSysParameterByCode(String code, String operatedSysUserId) {
        ApiAssert.isNotBlank(code, ErrorResponse.create("系统参数编码不能为空"));
        ApiAssert.isNotBlank(operatedSysUserId, ErrorResponse.create("操作的系统用户ID不能为空"));

        logger.debug("检查操作的系统用户");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, ErrorResponse.create("操作的系统用户不存在"));

        logger.debug("检查系统参数是否存在");
        SysParameter sysParameter = sysParameterMapper.selectOne(Wrappers.<SysParameter>lambdaQuery().eq(SysParameter::getCode, code));
        ApiAssert.isNotNull(sysParameter, ErrorResponse.create("系统参数不存在"));

        logger.debug("禁用系统参数");
        sysParameter.setStatus(StatusEnum.DISABLE);
        sysParameter.setUpdatedSysUserId(operatedSysUserId);
        sysParameter.setUpdatedDateTime(LocalDateTime.now());
        int i = sysParameterMapper.updateById(sysParameter);
        ApiAssert.isEqualToOne(i, ErrorResponse.create("禁用系统参数失败"));
    }

}
