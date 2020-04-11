package com.chen.f.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.f.common.mapper.SysDictMapper;
import com.chen.f.common.mapper.SysUserMapper;
import com.chen.f.common.pojo.SysDict;
import com.chen.f.common.pojo.SysUser;
import com.chen.f.common.pojo.enums.StatusEnum;
import com.chen.f.common.pojo.enums.TypeTypeEnum;
import com.chen.f.common.service.ISysDictService;
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
 * 系统字典表 服务实现类
 * </p>
 *
 * @author chen
 * @since 2018-11-10
 */
@Service
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements ISysDictService {
    protected static final Logger logger = LoggerFactory.getLogger(SysDictServiceImpl.class);

    @Autowired
    private SysDictMapper sysDictMapper;

    @Autowired
    private SysUserMapper sysUserMapper;


    @Override
    public IPage<SysDict> getSysDictPage(Long pageIndex, Long pageNumber, String code, String key, String name, String value, String remark, String color, TypeTypeEnum valueTypeEnum, StatusEnum statusEnum) {
        LambdaQueryWrapper<SysDict> wrapper = Wrappers.lambdaQuery();
        if (StringUtils.isNotBlank(code)) {
            wrapper.like(SysDict::getCode, code);
        }
        if (StringUtils.isNotBlank(key)) {
            wrapper.like(SysDict::getKey, key);
        }
        if (StringUtils.isNotBlank(name)) {
            wrapper.like(SysDict::getName, name);
        }
        if (StringUtils.isNotBlank(value)) {
            wrapper.like(SysDict::getValue, value);
        }
        if (StringUtils.isNotBlank(remark)) {
            wrapper.like(SysDict::getRemark, remark);
        }
        if (StringUtils.isNotBlank(color)) {
            wrapper.like(SysDict::getColor, color);
        }
        if (Objects.nonNull(valueTypeEnum)) {
            wrapper.eq(SysDict::getType, valueTypeEnum);
        }
        if (Objects.nonNull(statusEnum)) {
            wrapper.eq(SysDict::getStatus, statusEnum);
        }
        wrapper.groupBy(SysDict::getCode);
        return sysDictMapper.selectPage(new Page<>(pageIndex, pageNumber), wrapper);
    }

    @Override
    public List<SysDict> getEnabledSysDictList() {
        return sysDictMapper.selectList(Wrappers.<SysDict>lambdaQuery().eq(SysDict::getStatus, StatusEnum.ENABLED));
    }

    @Override
    public SysDict getSysDict(String sysDictId) {
        ApiAssert.isNotBlank(sysDictId, ErrorResponse.create("系统字典ID不能为空"));
        return sysDictMapper.selectById(sysDictId);
    }

    @Override
    public List<SysDict> getSysDictList(String code) {
        ApiAssert.isNotBlank(code, ErrorResponse.create("系统字典编码不能为空"));
        return sysDictMapper.selectList(Wrappers.<SysDict>lambdaQuery().eq(SysDict::getCode, code));
    }

    @Override
    public SysDict getSysDict(String code, String key) {
        ApiAssert.isNotBlank(code, ErrorResponse.create("系统字典编码不能为空"));
        ApiAssert.isNotBlank(key, ErrorResponse.create("系统字典KEY不能为空"));
        return sysDictMapper.selectOne(Wrappers.<SysDict>lambdaQuery().eq(SysDict::getCode, code).eq(SysDict::getKey, key));
    }

    @Override
    public void createSysDict(String code, String key, String name, String value, String remark,
                              String color, TypeTypeEnum type, Integer order, StatusEnum status, String operatedSysUserId) {
        ApiAssert.isNotBlank(code, ErrorResponse.create("系统字典编码不能为空"));
        ApiAssert.isNotBlank(key, ErrorResponse.create("系统字典KEY不能为空"));
        ApiAssert.isNotBlank(name, ErrorResponse.create("系统字典名称不能为空"));
        ApiAssert.isNotBlank(value, ErrorResponse.create("系统字典值不能为空"));
        //ApiAssert.isNotBlank(remark, ErrorResponse.create("字典备注不能为空"));
        //ApiAssert.isNotBlank(color, ErrorResponse.create("字典颜色不能为空"));
        ApiAssert.isNotNull(type, ErrorResponse.create("系统字典类型不能为空"));
        ApiAssert.isNotNull(status, ErrorResponse.create("系统字典状态不能为空"));
        ApiAssert.isNotBlank(operatedSysUserId, ErrorResponse.create("操作的系统用户ID不能为空"));

        logger.debug("检查操作的系统用户");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, ErrorResponse.create("操作的系统用户不存在"));

        logger.debug("插入系统字典");
        SysDict sysDict = new SysDict();
        sysDict.setCode(code);
        sysDict.setKey(key);
        sysDict.setName(name);
        sysDict.setValue(value);
        sysDict.setRemark(remark);
        sysDict.setColor(color);
        sysDict.setType(type);
        sysDict.setStatus(status);
        sysDict.setCreatedSysUserId(operatedSysUserId);
        sysDict.setCreatedDateTime(LocalDateTime.now());
        int i = sysDictMapper.insert(sysDict);
        ApiAssert.isEqualToOne(i, ErrorResponse.create("创建系统字典失败"));

    }

    @Override
    public void updateSysDict(String sysDictId, String code, String key, String name, String value, String remark,
                              String color, TypeTypeEnum type, Integer order, StatusEnum status, String operatedSysUserId) {
        ApiAssert.isNotBlank(sysDictId, ErrorResponse.create("系统字典ID不能为空"));
        ApiAssert.isNotBlank(code, ErrorResponse.create("系统字典编码不能为空"));
        ApiAssert.isNotBlank(key, ErrorResponse.create("系统字典KEY不能为空"));
        ApiAssert.isNotBlank(name, ErrorResponse.create("系统字典名称不能为空"));
        ApiAssert.isNotBlank(value, ErrorResponse.create("系统字典值不能为空"));
        //ApiAssert.isNotBlank(remark, ErrorResponse.create("字典备注不能为空"));
        //ApiAssert.isNotBlank(color, ErrorResponse.create("字典颜色不能为空"));
        ApiAssert.isNotNull(type, ErrorResponse.create("系统字典类型不能为空"));
        ApiAssert.isNotNull(status, ErrorResponse.create("系统字典状态不能为空"));
        ApiAssert.isNotBlank(operatedSysUserId, ErrorResponse.create("操作的系统用户ID不能为空"));

        logger.debug("检查操作的系统用户");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, ErrorResponse.create("操作的系统用户不存在"));

        logger.debug("检查系统字典是否存在");
        SysDict sysDict = sysDictMapper.selectOne((Wrappers.<SysDict>lambdaQuery().eq(SysDict::getCode, code)));
        ApiAssert.isNotNull(sysDict, ErrorResponse.create("系统字典不存在"));

        logger.debug("修改系统字典");
        sysDict.setId(sysDictId);
        sysDict.setCode(code);
        sysDict.setKey(key);
        sysDict.setName(name);
        sysDict.setValue(value);
        sysDict.setRemark(remark);
        sysDict.setColor(color);
        sysDict.setType(type);
        sysDict.setStatus(status);
        sysDict.setUpdatedSysUserId(operatedSysUserId);
        sysDict.setUpdatedDateTime(LocalDateTime.now());
        int i = sysDictMapper.updateById(sysDict);
        ApiAssert.isEqualToOne(i, ErrorResponse.create("修改系统字典失败"));
    }

    @Override
    public void deleteSysDict(String sysDictId) {
        ApiAssert.isNotBlank(sysDictId, ErrorResponse.create("系统字典ID不能为空"));
        int i = sysDictMapper.deleteById(sysDictId);
        ApiAssert.isEqualToOne(i, ErrorResponse.create("删除系统字典失败"));
    }

    @Override
    public void deleteSysDictByCode(String code) {
        ApiAssert.isNotBlank(code, ErrorResponse.create("系统字典编码不能为空"));
        int i = sysDictMapper.delete(Wrappers.<SysDict>lambdaQuery().eq(SysDict::getCode, code));
        ApiAssert.isGreaterThatZero(i, ErrorResponse.create("删除系统字典失败"));
    }

    @Override
    public void deleteSysDictByCodeAndKey(String code, String key) {
        ApiAssert.isNotBlank(code, ErrorResponse.create("系统字典编码不能为空"));
        ApiAssert.isNotBlank(key, ErrorResponse.create("系统字典KEY不能为空"));
        int i = sysDictMapper.delete(Wrappers.<SysDict>lambdaQuery().eq(SysDict::getCode, code).eq(SysDict::getKey, key));
        ApiAssert.isEqualToOne(i, ErrorResponse.create("删除系统字典失败"));
    }

    @Override
    public void enabledSysDict(String sysDictId, String operatedSysUserId) {
        ApiAssert.isNotBlank(sysDictId, ErrorResponse.create("系统字典ID不能为空"));
        ApiAssert.isNotBlank(operatedSysUserId, ErrorResponse.create("操作的系统用户ID不能为空"));

        logger.debug("检查操作的系统用户");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, ErrorResponse.create("操作的系统用户不存在"));

        logger.debug("检查系统字典是否存在");
        SysDict sysDict = sysDictMapper.selectById(sysDictId);
        ApiAssert.isNotNull(sysDict, ErrorResponse.create("系统字典不存在"));

        logger.debug("启用系统字典");
        sysDict.setStatus(StatusEnum.ENABLED);
        sysDict.setUpdatedSysUserId(operatedSysUserId);
        sysDict.setUpdatedDateTime(LocalDateTime.now());
        int i = sysDictMapper.updateById(sysDict);
        ApiAssert.isEqualToOne(i, ErrorResponse.create("启用系统字典失败"));
    }

    @Override
    public void enabledSysDictByCodeAndKey(String code, String key, String operatedSysUserId) {
        ApiAssert.isNotBlank(code, ErrorResponse.create("系统字典编码不能为空"));
        ApiAssert.isNotBlank(key, ErrorResponse.create("系统字典KEY不能为空"));
        ApiAssert.isNotBlank(operatedSysUserId, ErrorResponse.create("操作的系统用户ID不能为空"));

        logger.debug("检查操作的系统用户");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, ErrorResponse.create("操作的系统用户不存在"));

        logger.debug("检查系统字典是否存在");
        SysDict sysDict = sysDictMapper.selectOne((Wrappers.<SysDict>lambdaQuery().eq(SysDict::getCode, code)));
        ApiAssert.isNotNull(sysDict, ErrorResponse.create("系统字典不存在"));

        logger.debug("启用系统字典");
        sysDict.setCode(code);
        sysDict.setKey(key);
        sysDict.setStatus(StatusEnum.ENABLED);
        sysDict.setUpdatedSysUserId(operatedSysUserId);
        sysDict.setUpdatedDateTime(LocalDateTime.now());
        int i = sysDictMapper.update(sysDict, Wrappers.<SysDict>lambdaQuery().eq(SysDict::getCode, code).eq(SysDict::getKey, key));
        ApiAssert.isEqualToOne(i, ErrorResponse.create("启用系统字典失败"));
    }

    @Override
    public void disableSysDict(String sysDictId, String operatedSysUserId) {
        ApiAssert.isNotBlank(sysDictId, ErrorResponse.create("系统字典ID不能为空"));
        ApiAssert.isNotBlank(operatedSysUserId, ErrorResponse.create("操作的系统用户ID不能为空"));

        logger.debug("检查操作的系统用户");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, ErrorResponse.create("操作的系统用户不存在"));
        logger.debug("检查系统字典是否存在");
        SysDict sysDict = sysDictMapper.selectById(sysDictId);
        ApiAssert.isNotNull(sysDict, ErrorResponse.create("系统字典不存在"));

        logger.debug("禁用系统字典");
        sysDict.setStatus(StatusEnum.DISABLE);
        sysDict.setUpdatedSysUserId(operatedSysUserId);
        sysDict.setUpdatedDateTime(LocalDateTime.now());
        int i = sysDictMapper.updateById(sysDict);
        ApiAssert.isEqualToOne(i, ErrorResponse.create("禁用系统字典失败"));
    }

    @Override
    public void disableSysDictByCodeAndKey(String code, String key, String operatedSysUserId) {
        ApiAssert.isNotBlank(code, ErrorResponse.create("系统字典编码不能为空"));
        ApiAssert.isNotBlank(key, ErrorResponse.create("系统字典KEY不能为空"));
        ApiAssert.isNotBlank(operatedSysUserId, ErrorResponse.create("操作的系统用户ID不能为空"));

        logger.debug("检查操作的系统用户");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, ErrorResponse.create("操作的系统用户不存在"));
        logger.debug("检查系统字典是否存在");
        SysDict sysDict = sysDictMapper.selectOne((Wrappers.<SysDict>lambdaQuery().eq(SysDict::getCode, code)));
        ApiAssert.isNotNull(sysDict, ErrorResponse.create("系统字典不存在"));

        logger.debug("禁用系统字典");
        sysDict.setCode(code);
        sysDict.setKey(key);
        sysDict.setStatus(StatusEnum.DISABLE);
        sysDict.setUpdatedSysUserId(operatedSysUserId);
        sysDict.setUpdatedDateTime(LocalDateTime.now());
        int i = sysDictMapper.update(sysDict, Wrappers.<SysDict>lambdaQuery().eq(SysDict::getCode, code).eq(SysDict::getKey, key));
        ApiAssert.isEqualToOne(i, ErrorResponse.create("禁用系统字典失败"));
    }
}
