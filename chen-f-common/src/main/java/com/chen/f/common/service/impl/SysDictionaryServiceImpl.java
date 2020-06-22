package com.chen.f.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.f.common.api.response.error.SysDictionaryErrorResponses;
import com.chen.f.common.api.response.error.SysUserErrorResponses;
import com.chen.f.common.mapper.SysDictionaryItemMapper;
import com.chen.f.common.mapper.SysDictionaryMapper;
import com.chen.f.common.mapper.SysUserMapper;
import com.chen.f.common.pojo.SysDictionary;
import com.chen.f.common.pojo.SysDictionaryItem;
import com.chen.f.common.pojo.SysUser;
import com.chen.f.common.pojo.enums.StatusEnum;
import com.chen.f.common.pojo.enums.SysDictionaryTypeEnum;
import com.chen.f.common.service.ISysDictionaryService;
import com.chen.f.core.api.ApiAssert;
import com.chen.f.core.page.Page;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 系统字典表 服务实现类
 * </p>
 *
 * @author chen
 * @since 2020-04-06
 */
@Service
public class SysDictionaryServiceImpl extends ServiceImpl<SysDictionaryMapper, SysDictionary> implements ISysDictionaryService {
    protected static final Logger logger = LoggerFactory.getLogger(SysDictionaryServiceImpl.class);

    @Autowired
    private SysDictionaryMapper sysDictionaryMapper;
    @Autowired
    private SysDictionaryItemMapper sysDictionaryItemMapper;

    @Autowired
    private SysUserMapper sysUserMapper;


    @Override
    public Page<SysDictionary> getSysDictionaryPage(Page<SysDictionary> page,
                                                    String code, String name, SysDictionaryTypeEnum sysDictionaryTypeEnum, String remark, StatusEnum statusEnum) {
        LambdaQueryWrapper<SysDictionary> wrapper = Wrappers.lambdaQuery();
        wrapper.like(StringUtils.isNotBlank(code), SysDictionary::getCode, code);
        wrapper.like(StringUtils.isNotBlank(name), SysDictionary::getName, name);
        wrapper.eq(Objects.nonNull(sysDictionaryTypeEnum), SysDictionary::getType, sysDictionaryTypeEnum);
        wrapper.like(StringUtils.isNotBlank(remark), SysDictionary::getRemark, remark);
        wrapper.eq(Objects.nonNull(statusEnum), SysDictionary::getStatus, statusEnum);
        return sysDictionaryMapper.selectPage(page, wrapper);
    }

    @Override
    public List<SysDictionary> getEnabledSysDictionaryList() {
        return sysDictionaryMapper.selectList(Wrappers.<SysDictionary>lambdaQuery().eq(SysDictionary::getStatus, StatusEnum.ENABLED));
    }

    @Override
    public SysDictionary getSysDictionary(String sysDictionaryId) {
        ApiAssert.isNotBlank(sysDictionaryId, SysDictionaryErrorResponses.sysDictionaryIdCanNotNull());
        return sysDictionaryMapper.selectById(sysDictionaryId);
    }

    @Override
    public SysDictionary getSysDictionaryByCode(String code) {
        ApiAssert.isNotBlank(code, SysDictionaryErrorResponses.sysDictionaryCodeCanNotBlank());
        return sysDictionaryMapper.selectOne(Wrappers.<SysDictionary>lambdaQuery().eq(SysDictionary::getCode, code));
    }


    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void createSysDictionary(String code, String name, SysDictionaryTypeEnum type, String remark,
                                    StatusEnum status, String operatedSysUserId) {
        ApiAssert.isNotBlank(code, SysDictionaryErrorResponses.sysDictionaryCodeCanNotBlank());
        ApiAssert.isNotBlank(name, SysDictionaryErrorResponses.sysDictionaryNameCanNotBlank());
        ApiAssert.isNotNull(type, SysDictionaryErrorResponses.sysDictionaryTypeCanNotNull());
        ApiAssert.isNotNull(status, SysDictionaryErrorResponses.sysDictionaryStatusCanNotNull());
        ApiAssert.isNotBlank(operatedSysUserId, SysUserErrorResponses.operatedSysUserIdCanNotBlank());

        logger.debug("检查操作的系统用户");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, SysUserErrorResponses.operatedSysUserNotExist());
        
        remark = ObjectUtils.defaultIfNull(remark, "");

        logger.debug("插入系统字典");
        SysDictionary sysDictionary = new SysDictionary();
        sysDictionary.setCode(code);
        sysDictionary.setName(name);
        sysDictionary.setType(type);
        sysDictionary.setRemark(remark);
        sysDictionary.setStatus(status);
        sysDictionary.setUpdatedSysUserId(operatedSysUserId);
        sysDictionary.setCreatedSysUserId(operatedSysUserId);
        sysDictionary.setUpdatedDateTime(LocalDateTime.now());
        sysDictionary.setCreatedDateTime(LocalDateTime.now());
        int i = sysDictionaryMapper.insert(sysDictionary);
        ApiAssert.isEqualToOne(i, SysDictionaryErrorResponses.createSysDictionaryFailure());

    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void updateSysDictionary(String sysDictionaryId, String code, String name, SysDictionaryTypeEnum type, String remark,
                                    StatusEnum status, String operatedSysUserId) {
        ApiAssert.isNotBlank(sysDictionaryId, SysDictionaryErrorResponses.sysDictionaryIdCanNotNull());
        ApiAssert.isNotBlank(code, SysDictionaryErrorResponses.sysDictionaryCodeCanNotBlank());
        ApiAssert.isNotBlank(name, SysDictionaryErrorResponses.sysDictionaryNameCanNotBlank());
        ApiAssert.isNotNull(type, SysDictionaryErrorResponses.sysDictionaryTypeCanNotNull());
        ApiAssert.isNotNull(status, SysDictionaryErrorResponses.sysDictionaryStatusCanNotNull());
        ApiAssert.isNotBlank(operatedSysUserId, SysUserErrorResponses.operatedSysUserIdCanNotBlank());

        logger.debug("检查操作的系统用户");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, SysUserErrorResponses.operatedSysUserNotExist());

        logger.debug("检查系统字典是否存在");
        SysDictionary sysDictionary = sysDictionaryMapper.selectOne((Wrappers.<SysDictionary>lambdaQuery().eq(SysDictionary::getCode, code)));
        ApiAssert.isNotNull(sysDictionary, SysDictionaryErrorResponses.sysDictionaryNotExist());

        remark = ObjectUtils.defaultIfNull(remark, "");

        logger.debug("修改系统字典");
        sysDictionary.setId(sysDictionaryId);
        sysDictionary.setCode(code);
        sysDictionary.setName(name);
        sysDictionary.setType(type);
        sysDictionary.setRemark(remark);
        sysDictionary.setStatus(status);
        sysDictionary.setUpdatedSysUserId(operatedSysUserId);
        sysDictionary.setUpdatedDateTime(LocalDateTime.now());
        int i = sysDictionaryMapper.updateById(sysDictionary);
        ApiAssert.isEqualToOne(i, SysDictionaryErrorResponses.updateSysDictionaryFailure());
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void deleteSysDictionary(String sysDictionaryId) {
        ApiAssert.isNotBlank(sysDictionaryId,SysDictionaryErrorResponses.sysDictionaryIdCanNotNull());
        
        int i = sysDictionaryMapper.deleteById(sysDictionaryId);
        ApiAssert.isEqualToOne(i, SysDictionaryErrorResponses.deleteSysDictionaryFailure());

        //删除系统字典项目
        sysDictionaryItemMapper.delete(Wrappers.<SysDictionaryItem>lambdaQuery().eq(SysDictionaryItem::getSysDictionaryId, sysDictionaryId));
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void deleteSysDictionaryByCode(String code) {
        ApiAssert.isNotBlank(code, SysDictionaryErrorResponses.sysDictionaryCodeCanNotBlank());
        
        int i = sysDictionaryMapper.delete(Wrappers.<SysDictionary>lambdaQuery().eq(SysDictionary::getCode, code));
        ApiAssert.isGreaterThatZero(i, SysDictionaryErrorResponses.deleteSysDictionaryFailure());

        //删除系统字典项目
        sysDictionaryItemMapper.delete(Wrappers.<SysDictionaryItem>lambdaQuery().eq(SysDictionaryItem::getCode, code));
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void enabledSysDictionary(String sysDictionaryId, String operatedSysUserId) {
        ApiAssert.isNotBlank(sysDictionaryId,SysDictionaryErrorResponses.sysDictionaryIdCanNotNull());
        ApiAssert.isNotBlank(operatedSysUserId, SysUserErrorResponses.operatedSysUserIdCanNotBlank());

        logger.debug("检查操作的系统用户");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, SysUserErrorResponses.operatedSysUserNotExist());

        logger.debug("检查系统字典是否存在");
        SysDictionary sysDictionary = sysDictionaryMapper.selectById(sysDictionaryId);
        ApiAssert.isNotNull(sysDictionary, SysDictionaryErrorResponses.sysDictionaryNotExist());

        logger.debug("启用系统字典");
        sysDictionary.setStatus(StatusEnum.ENABLED);
        sysDictionary.setUpdatedSysUserId(operatedSysUserId);
        sysDictionary.setUpdatedDateTime(LocalDateTime.now());
        int i = sysDictionaryMapper.updateById(sysDictionary);
        ApiAssert.isEqualToOne(i, SysDictionaryErrorResponses.updateSysDictionaryFailure());

        //更新系统字典项目
        SysDictionaryItem sysDictionaryItem = new SysDictionaryItem();
        sysDictionaryItem.setStatus(StatusEnum.ENABLED);
        sysDictionaryItemMapper.update(sysDictionaryItem,
                Wrappers.<SysDictionaryItem>lambdaQuery().eq(SysDictionaryItem::getSysDictionaryId, sysDictionaryId));
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void disableSysDictionary(String sysDictionaryId, String operatedSysUserId) {
        ApiAssert.isNotBlank(sysDictionaryId, SysDictionaryErrorResponses.sysDictionaryIdCanNotNull());
        ApiAssert.isNotBlank(operatedSysUserId, SysUserErrorResponses.operatedSysUserIdCanNotBlank());

        logger.debug("检查操作的系统用户");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, SysUserErrorResponses.operatedSysUserNotExist());
        
        logger.debug("检查系统字典是否存在");
        SysDictionary sysDictionary = sysDictionaryMapper.selectById(sysDictionaryId);
        ApiAssert.isNotNull(sysDictionary, SysDictionaryErrorResponses.sysDictionaryNotExist());

        logger.debug("禁用系统字典");
        sysDictionary.setStatus(StatusEnum.DISABLED);
        sysDictionary.setUpdatedSysUserId(operatedSysUserId);
        sysDictionary.setUpdatedDateTime(LocalDateTime.now());
        int i = sysDictionaryMapper.updateById(sysDictionary);
        ApiAssert.isEqualToOne(i, SysDictionaryErrorResponses.updateSysDictionaryFailure());

        //更新系统字典项目
        SysDictionaryItem sysDictionaryItem = new SysDictionaryItem();
        sysDictionaryItem.setStatus(StatusEnum.DISABLED);
        sysDictionaryItemMapper.update(sysDictionaryItem,
                Wrappers.<SysDictionaryItem>lambdaQuery().eq(SysDictionaryItem::getSysDictionaryId, sysDictionaryId));
    }
}
