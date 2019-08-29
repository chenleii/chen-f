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
import com.chen.f.common.service.ISysParameterService;
import com.chen.f.core.api.ApiAssert;
import com.chen.f.core.api.response.error.ErrorResponse;
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
    public IPage<SysParameter> getSysParameterPage(long pageIndex, long pageNumber,
                                                   String code, String name, String value, SysParameterTypeEnum type, String remark, StatusEnum statusEnum) {
        LambdaQueryWrapper<SysParameter> lambdaQueryWrapper = Wrappers.<SysParameter>lambdaQuery();
        if (StringUtils.isNotBlank(code)) {
            lambdaQueryWrapper.eq(SysParameter::getCode, code);
        }
        if (StringUtils.isNotBlank(name)) {
            lambdaQueryWrapper.eq(SysParameter::getName, name);
        }
        if (StringUtils.isNotBlank(value)) {
            lambdaQueryWrapper.eq(SysParameter::getValue, value);
        }
        if (Objects.nonNull(type)) {
            lambdaQueryWrapper.eq(SysParameter::getType, type);
        }
        if (StringUtils.isNotBlank(remark)) {
            lambdaQueryWrapper.like(SysParameter::getRemark, remark);
        }
        if (Objects.nonNull(statusEnum)) {
            lambdaQueryWrapper.eq(SysParameter::getStatus, statusEnum);
        }
        return sysParameterMapper.selectPage(new Page<>(pageIndex, pageNumber), lambdaQueryWrapper);
    }

    @Override
    public SysParameter getSysParameter(String sysParameterId) {
        ApiAssert.isNotBlank(sysParameterId, ErrorResponse.create("系统参数ID不能为空"));
        return sysParameterMapper.selectById(sysParameterId);
    }

    @Override
    public SysParameter getSysParameterByCode(String code) {
        ApiAssert.isNotBlank(code, ErrorResponse.create("系统参数标识不能为空"));
        return sysParameterMapper.selectOne(Wrappers.<SysParameter>lambdaQuery().eq(SysParameter::getCode, code));
    }

    @Override
    public void createSysParameter(String code, String name, String value,
                                   SysParameterTypeEnum type, String remark, StatusEnum status, String operatedSysUserId) {
        ApiAssert.isNotBlank(code, ErrorResponse.create("系统参数标识不能为空"));
        ApiAssert.isNotBlank(name, ErrorResponse.create("系统参数名称不能为空"));
        ApiAssert.isNotBlank(value, ErrorResponse.create("系统参数值不能为空"));
        ApiAssert.isNotNull(type, ErrorResponse.create("系统参数类型不能为空"));
        //ApiAssert.isNotBlank(remark, ErrorResponse.create("系统参数备注不能为空"));
        ApiAssert.isNotNull(status, ErrorResponse.create("系统参数状态不能为空"));
        ApiAssert.isNotBlank(operatedSysUserId, ErrorResponse.create("操作的系统用户id不能为空"));

        logger.debug("检查操作的系统用户");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, ErrorResponse.create("操作的系统用户不存在"));

        logger.debug("插入系统参数");
        SysParameter sysParameter = new SysParameter();
        sysParameter.setCode(code);
        sysParameter.setName(name);
        sysParameter.setValue(value);
        sysParameter.setType(type);
        sysParameter.setRemark(remark);
        sysParameter.setStatus(status);
        sysParameter.setCreateSysUserId(operatedSysUserId);
        sysParameter.setCreateDateTime(LocalDateTime.now());
        int i = sysParameterMapper.insert(sysParameter);
        ApiAssert.isEqualToOne(i, ErrorResponse.create("创建系统参数失败"));
    }

    @Override
    public void updateSysParameter(String code, String name, String value, SysParameterTypeEnum type, String remark, StatusEnum status, String operatedSysUserId) {
        ApiAssert.isNotBlank(code, ErrorResponse.create("系统参数标识不能为空"));
        ApiAssert.isNotBlank(name, ErrorResponse.create("系统参数名称不能为空"));
        ApiAssert.isNotBlank(value, ErrorResponse.create("系统参数值不能为空"));
        ApiAssert.isNotNull(type, ErrorResponse.create("系统参数类型不能为空"));
        //ApiAssert.isNotBlank(remark, ErrorResponse.create("系统参数备注不能为空"));
        ApiAssert.isNotNull(status, ErrorResponse.create("系统参数状态不能为空"));
        ApiAssert.isNotBlank(operatedSysUserId, ErrorResponse.create("操作的系统用户id不能为空"));

        logger.debug("检查操作的系统用户");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, ErrorResponse.create("操作的系统用户不存在"));

        logger.debug("检查系统参数是否存在");
        SysParameter sysParameter = sysParameterMapper.selectById(code);
        ApiAssert.isNotNull(sysParameter, ErrorResponse.create("系统参数不存在"));

        logger.debug("修改系统参数");
        sysParameter.setCode(code);
        sysParameter.setName(name);
        sysParameter.setValue(value);
        sysParameter.setType(type);
        sysParameter.setRemark(remark);
        sysParameter.setStatus(status);
        sysParameter.setUpdateSysUserId(operatedSysUserId);
        sysParameter.setUpdateDateTime(LocalDateTime.now());
        int i = sysParameterMapper.updateById(sysParameter);
        ApiAssert.isEqualToOne(i, ErrorResponse.create("修改系统参数失败"));
    }

    @Override
    public void deleteSysParameter(String code) {
        ApiAssert.isNotBlank(code, ErrorResponse.create("系统参数标识不能为空"));
        int i = sysParameterMapper.deleteById(code);
        ApiAssert.isEqualToOne(i, ErrorResponse.create("删除系统参数失败"));
    }

    @Override
    public void enabledSysParameter(String code, String operatedSysUserId) {
        ApiAssert.isNotBlank(code, ErrorResponse.create("系统参数标识不能为空"));
        ApiAssert.isNotBlank(operatedSysUserId, ErrorResponse.create("操作的系统用户id不能为空"));

        logger.debug("检查操作的系统用户");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, ErrorResponse.create("操作的系统用户不存在"));

        logger.debug("检查系统参数是否存在");
        SysParameter sysParameter = sysParameterMapper.selectById(code);
        ApiAssert.isNotNull(sysParameter, ErrorResponse.create("系统参数不存在"));

        logger.debug("启用系统参数");
        sysParameter.setCode(code);
        sysParameter.setStatus(StatusEnum.ENABLED);
        sysParameter.setUpdateSysUserId(operatedSysUserId);
        sysParameter.setUpdateDateTime(LocalDateTime.now());
        int i = sysParameterMapper.updateById(sysParameter);
        ApiAssert.isEqualToOne(i, ErrorResponse.create("启用系统参数失败"));
    }

    @Override
    public void disableSysParameter(String code, String operatedSysUserId) {
        ApiAssert.isNotBlank(code, ErrorResponse.create("系统参数标识不能为空"));
        ApiAssert.isNotBlank(operatedSysUserId, ErrorResponse.create("操作的系统用户id不能为空"));

        logger.debug("检查操作的系统用户");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, ErrorResponse.create("操作的系统用户不存在"));

        logger.debug("检查系统参数是否存在");
        SysParameter sysParameter = sysParameterMapper.selectById(code);
        ApiAssert.isNotNull(sysParameter, ErrorResponse.create("系统参数不存在"));

        logger.debug("禁用系统参数");
        sysParameter.setCode(code);
        sysParameter.setStatus(StatusEnum.DISABLE);
        sysParameter.setUpdateSysUserId(operatedSysUserId);
        sysParameter.setUpdateDateTime(LocalDateTime.now());
        int i = sysParameterMapper.updateById(sysParameter);
        ApiAssert.isEqualToOne(i, ErrorResponse.create("禁用系统参数失败"));
    }

}
