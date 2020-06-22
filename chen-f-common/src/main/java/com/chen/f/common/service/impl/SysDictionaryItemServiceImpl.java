package com.chen.f.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.f.common.api.response.error.SysDictionaryErrorResponses;
import com.chen.f.common.api.response.error.SysDictionaryItemErrorResponses;
import com.chen.f.common.api.response.error.SysUserErrorResponses;
import com.chen.f.common.mapper.SysDictionaryItemMapper;
import com.chen.f.common.mapper.SysUserMapper;
import com.chen.f.common.pojo.SysDictionaryItem;
import com.chen.f.common.pojo.SysUser;
import com.chen.f.common.pojo.enums.StatusEnum;
import com.chen.f.common.pojo.enums.TypeTypeEnum;
import com.chen.f.common.service.ISysDictionaryItemService;
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
 * 系统字典项目表 服务实现类
 * </p>
 *
 * @author chen
 * @since 2020-04-06
 */
@Service
public class SysDictionaryItemServiceImpl extends ServiceImpl<SysDictionaryItemMapper, SysDictionaryItem> implements ISysDictionaryItemService {
    protected static final Logger logger = LoggerFactory.getLogger(SysDictionaryItemServiceImpl.class);

    @Autowired
    private SysDictionaryItemMapper sysDictionaryItemMapper;

    @Autowired
    private SysUserMapper sysUserMapper;


    @Override
    public Page<SysDictionaryItem> getSysDictionaryItemPage(Page<SysDictionaryItem> page, String sysDictionaryId, String code, String name, String key, String value, TypeTypeEnum valueTypeEnum, String color, String remark, StatusEnum statusEnum) {
        LambdaQueryWrapper<SysDictionaryItem> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(StringUtils.isNotBlank(sysDictionaryId), SysDictionaryItem::getSysDictionaryId, sysDictionaryId);
        wrapper.like(StringUtils.isNotBlank(code), SysDictionaryItem::getCode, code);
        wrapper.like(StringUtils.isNotBlank(key), SysDictionaryItem::getKey, key);
        wrapper.like(StringUtils.isNotBlank(name), SysDictionaryItem::getName, name);
        wrapper.like(StringUtils.isNotBlank(value), SysDictionaryItem::getValue, value);
        wrapper.eq(Objects.nonNull(valueTypeEnum), SysDictionaryItem::getValueType, valueTypeEnum);
        wrapper.like(StringUtils.isNotBlank(color), SysDictionaryItem::getColor, color);
        wrapper.like(StringUtils.isNotBlank(remark), SysDictionaryItem::getRemark, remark);
        wrapper.eq(Objects.nonNull(statusEnum), SysDictionaryItem::getStatus, statusEnum);
        return sysDictionaryItemMapper.selectPage(page, wrapper);
    }

    @Override
    public List<SysDictionaryItem> getEnabledSysDictionaryItemList() {
        return sysDictionaryItemMapper.selectList(Wrappers.<SysDictionaryItem>lambdaQuery().eq(SysDictionaryItem::getStatus, StatusEnum.ENABLED));
    }

    @Override
    public SysDictionaryItem getSysDictionaryItem(String sysDictionaryItemId) {
        ApiAssert.isNotBlank(sysDictionaryItemId, SysDictionaryItemErrorResponses.sysDictionaryItemIdCanNotNull());
        return sysDictionaryItemMapper.selectById(sysDictionaryItemId);
    }

    @Override
    public List<SysDictionaryItem> getSysDictionaryItemListBySysDictionaryId(String sysDictionaryId) {
        return sysDictionaryItemMapper.selectList(Wrappers.<SysDictionaryItem>lambdaQuery().eq(SysDictionaryItem::getSysDictionaryId, sysDictionaryId));
    }

    @Override
    public List<SysDictionaryItem> getSysDictionaryItemListByCode(String code) {
        ApiAssert.isNotBlank(code, SysDictionaryItemErrorResponses.sysDictionaryItemCodeCanNotBlank());
        return sysDictionaryItemMapper.selectList(Wrappers.<SysDictionaryItem>lambdaQuery().eq(SysDictionaryItem::getCode, code));
    }

    @Override
    public List<SysDictionaryItem> getEnabledSysDictionaryItemListByCode(String code) {
        ApiAssert.isNotBlank(code, SysDictionaryItemErrorResponses.sysDictionaryItemCodeCanNotBlank());
        return sysDictionaryItemMapper.selectList(Wrappers.<SysDictionaryItem>lambdaQuery().eq(SysDictionaryItem::getCode, code).eq(SysDictionaryItem::getStatus, StatusEnum.ENABLED));
    }

    @Override
    public SysDictionaryItem getSysDictionaryItemByCodeAndKey(String code, String key) {
        ApiAssert.isNotBlank(code, SysDictionaryItemErrorResponses.sysDictionaryItemCodeCanNotBlank());
        ApiAssert.isNotBlank(key, SysDictionaryItemErrorResponses.sysDictionaryItemKeyCanNotBlank());
        return sysDictionaryItemMapper.selectOne(Wrappers.<SysDictionaryItem>lambdaQuery().eq(SysDictionaryItem::getCode, code).eq(SysDictionaryItem::getKey, key));
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void createSysDictionaryItem(String sysDictionaryId, String code, String name, String key, String value, String valueI18n, TypeTypeEnum keyType, TypeTypeEnum valueType, String color, Integer order, String remark,
                                        StatusEnum status, String operatedSysUserId) {
        ApiAssert.isNotBlank(sysDictionaryId, SysDictionaryErrorResponses.sysDictionaryIdCanNotNull());
        ApiAssert.isNotBlank(code, SysDictionaryItemErrorResponses.sysDictionaryItemCodeCanNotBlank());
        ApiAssert.isNotBlank(name, SysDictionaryItemErrorResponses.sysDictionaryItemNameCanNotBlank());
        ApiAssert.isNotBlank(key, SysDictionaryItemErrorResponses.sysDictionaryItemKeyCanNotBlank());
        ApiAssert.isNotBlank(value, SysDictionaryItemErrorResponses.sysDictionaryItemValueCanNotBlank());
        ApiAssert.isNotNull(keyType, SysDictionaryItemErrorResponses.sysDictionaryItemKeyTypeCanNotNull());
        ApiAssert.isNotNull(valueType, SysDictionaryItemErrorResponses.sysDictionaryItemValueTypeCanNotNull());
        ApiAssert.isNotNull(status, SysDictionaryItemErrorResponses.sysDictionaryItemStatusCanNotNull());
        ApiAssert.isNotBlank(operatedSysUserId, SysUserErrorResponses.operatedSysUserIdCanNotBlank());

        logger.debug("检查操作的系统用户");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, SysUserErrorResponses.operatedSysUserNotExist());

        color = ObjectUtils.defaultIfNull(color, "");
        remark = ObjectUtils.defaultIfNull(remark, "");
        valueI18n = ObjectUtils.defaultIfNull(valueI18n, "");

        logger.debug("检查系统字典显示顺序");
        if (Objects.isNull(order)) {
            SysDictionaryItem maxOrderSysDictionaryItem = sysDictionaryItemMapper.selectFirstOne(Wrappers.<SysDictionaryItem>lambdaQuery()
                    .eq(SysDictionaryItem::getSysDictionaryId, sysDictionaryId).orderByDesc(SysDictionaryItem::getOrder));

            if (Objects.nonNull(maxOrderSysDictionaryItem) && Objects.nonNull(maxOrderSysDictionaryItem.getOrder())) {
                order = maxOrderSysDictionaryItem.getOrder() + 1;
            } else {
                //默认顺序号1
                order = 1;
            }
        }

        logger.debug("插入系统字典项目");
        SysDictionaryItem sysDictionaryItem = new SysDictionaryItem();
        sysDictionaryItem.setSysDictionaryId(sysDictionaryId);
        sysDictionaryItem.setCode(code);
        sysDictionaryItem.setName(name);
        sysDictionaryItem.setKey(key);
        sysDictionaryItem.setValue(value);
        sysDictionaryItem.setValueI18n(valueI18n);
        sysDictionaryItem.setKeyType(keyType);
        sysDictionaryItem.setValueType(valueType);
        sysDictionaryItem.setColor(color);
        sysDictionaryItem.setOrder(order);
        sysDictionaryItem.setRemark(remark);
        sysDictionaryItem.setStatus(status);
        sysDictionaryItem.setUpdatedSysUserId(operatedSysUserId);
        sysDictionaryItem.setCreatedSysUserId(operatedSysUserId);
        sysDictionaryItem.setUpdatedDateTime(LocalDateTime.now());
        sysDictionaryItem.setCreatedDateTime(LocalDateTime.now());
        int i = sysDictionaryItemMapper.insert(sysDictionaryItem);
        ApiAssert.isEqualToOne(i, SysDictionaryItemErrorResponses.createSysDictionaryItemFailure());

    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void updateSysDictionaryItem(String sysDictionaryItemId, String sysDictionaryId, String code, String name, String key, String value, String valueI18n, TypeTypeEnum keyType, TypeTypeEnum valueType, String color, Integer order, String remark,
                                        StatusEnum status, String operatedSysUserId) {
        ApiAssert.isNotBlank(sysDictionaryItemId, SysDictionaryItemErrorResponses.sysDictionaryItemIdCanNotNull());
        ApiAssert.isNotBlank(sysDictionaryId, SysDictionaryErrorResponses.sysDictionaryIdCanNotNull());
        ApiAssert.isNotBlank(code, SysDictionaryItemErrorResponses.sysDictionaryItemCodeCanNotBlank());
        ApiAssert.isNotBlank(name, SysDictionaryItemErrorResponses.sysDictionaryItemNameCanNotBlank());
        ApiAssert.isNotBlank(key, SysDictionaryItemErrorResponses.sysDictionaryItemKeyCanNotBlank());
        ApiAssert.isNotBlank(value, SysDictionaryItemErrorResponses.sysDictionaryItemValueCanNotBlank());
        ApiAssert.isNotNull(keyType, SysDictionaryItemErrorResponses.sysDictionaryItemKeyTypeCanNotNull());
        ApiAssert.isNotNull(valueType, SysDictionaryItemErrorResponses.sysDictionaryItemValueTypeCanNotNull());
        ApiAssert.isNotNull(status, SysDictionaryItemErrorResponses.sysDictionaryItemStatusCanNotNull());
        ApiAssert.isNotBlank(operatedSysUserId, SysUserErrorResponses.operatedSysUserIdCanNotBlank());

        logger.debug("检查操作的系统用户");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, SysUserErrorResponses.operatedSysUserNotExist());

        logger.debug("检查系统字典项目是否存在");
        SysDictionaryItem sysDictionaryItem = sysDictionaryItemMapper.selectById(sysDictionaryItemId);
        ApiAssert.isNotNull(sysDictionaryItem, SysDictionaryItemErrorResponses.sysDictionaryItemNotExist());

        color = ObjectUtils.defaultIfNull(color, "");
        remark = ObjectUtils.defaultIfNull(remark, "");
        valueI18n = ObjectUtils.defaultIfNull(valueI18n, "");

        logger.debug("检查系统字典显示顺序");
        if (Objects.isNull(order)) {
            SysDictionaryItem maxOrderSysDictionaryItem = sysDictionaryItemMapper.selectFirstOne(Wrappers.<SysDictionaryItem>lambdaQuery()
                    .eq(SysDictionaryItem::getSysDictionaryId, sysDictionaryId).orderByDesc(SysDictionaryItem::getOrder));

            if (Objects.nonNull(maxOrderSysDictionaryItem) && Objects.nonNull(maxOrderSysDictionaryItem.getOrder())) {
                order = maxOrderSysDictionaryItem.getOrder() + 1;
            } else {
                //默认顺序号1
                order = 1;
            }
        }
        logger.debug("修改系统字典项目");
        sysDictionaryItem.setId(sysDictionaryItemId);
        sysDictionaryItem.setSysDictionaryId(sysDictionaryId);
        sysDictionaryItem.setCode(code);
        sysDictionaryItem.setKey(key);
        sysDictionaryItem.setName(name);
        sysDictionaryItem.setValue(value);
        sysDictionaryItem.setValueI18n(valueI18n);
        sysDictionaryItem.setKeyType(keyType);
        sysDictionaryItem.setValueType(valueType);
        sysDictionaryItem.setColor(color);
        sysDictionaryItem.setOrder(order);
        sysDictionaryItem.setRemark(remark);
        sysDictionaryItem.setStatus(status);
        sysDictionaryItem.setUpdatedSysUserId(operatedSysUserId);
        sysDictionaryItem.setUpdatedDateTime(LocalDateTime.now());
        int i = sysDictionaryItemMapper.updateById(sysDictionaryItem);
        ApiAssert.isEqualToOne(i, SysDictionaryItemErrorResponses.updateSysDictionaryItemFailure());
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void deleteSysDictionaryItem(String sysDictionaryItemId) {
        ApiAssert.isNotBlank(sysDictionaryItemId, SysDictionaryItemErrorResponses.sysDictionaryItemIdCanNotNull());

        int i = sysDictionaryItemMapper.deleteById(sysDictionaryItemId);
        ApiAssert.isEqualToOne(i, SysDictionaryItemErrorResponses.deleteSysDictionaryItemFailure());
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void deleteSysDictionaryItemByCode(String code) {
        ApiAssert.isNotBlank(code, SysDictionaryItemErrorResponses.sysDictionaryItemCodeCanNotBlank());

        int i = sysDictionaryItemMapper.delete(Wrappers.<SysDictionaryItem>lambdaQuery().eq(SysDictionaryItem::getCode, code));
        ApiAssert.isGreaterThatZero(i, SysDictionaryItemErrorResponses.deleteSysDictionaryItemFailure());
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void deleteSysDictionaryItemByCodeAndKey(String code, String key) {
        ApiAssert.isNotBlank(code, SysDictionaryItemErrorResponses.sysDictionaryItemCodeCanNotBlank());
        ApiAssert.isNotBlank(key, SysDictionaryItemErrorResponses.sysDictionaryItemKeyCanNotBlank());

        int i = sysDictionaryItemMapper.delete(Wrappers.<SysDictionaryItem>lambdaQuery().eq(SysDictionaryItem::getCode, code).eq(SysDictionaryItem::getKey, key));
        ApiAssert.isEqualToOne(i, SysDictionaryItemErrorResponses.deleteSysDictionaryItemFailure());
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void enabledSysDictionaryItem(String sysDictionaryItemId, String operatedSysUserId) {
        ApiAssert.isNotBlank(sysDictionaryItemId, SysDictionaryItemErrorResponses.sysDictionaryItemIdCanNotNull());
        ApiAssert.isNotBlank(operatedSysUserId, SysUserErrorResponses.operatedSysUserIdCanNotBlank());

        logger.debug("检查操作的系统用户");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, SysUserErrorResponses.operatedSysUserNotExist());

        logger.debug("检查系统字典项目是否存在");
        SysDictionaryItem sysDictionaryItem = sysDictionaryItemMapper.selectById(sysDictionaryItemId);
        ApiAssert.isNotNull(sysDictionaryItem, SysDictionaryItemErrorResponses.sysDictionaryItemNotExist());

        logger.debug("启用系统字典项目");
        sysDictionaryItem.setStatus(StatusEnum.ENABLED);
        sysDictionaryItem.setUpdatedSysUserId(operatedSysUserId);
        sysDictionaryItem.setUpdatedDateTime(LocalDateTime.now());
        int i = sysDictionaryItemMapper.updateById(sysDictionaryItem);
        ApiAssert.isEqualToOne(i, SysDictionaryItemErrorResponses.updateSysDictionaryItemFailure());
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void enabledSysDictionaryItemByCodeAndKey(String code, String key, String operatedSysUserId) {
        ApiAssert.isNotBlank(code, SysDictionaryItemErrorResponses.sysDictionaryItemCodeCanNotBlank());
        ApiAssert.isNotBlank(key, SysDictionaryItemErrorResponses.sysDictionaryItemKeyCanNotBlank());
        ApiAssert.isNotBlank(operatedSysUserId, SysUserErrorResponses.operatedSysUserIdCanNotBlank());

        logger.debug("检查操作的系统用户");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, SysUserErrorResponses.operatedSysUserNotExist());

        logger.debug("检查系统字典项目是否存在");
        SysDictionaryItem sysDictionaryItem = sysDictionaryItemMapper.selectOne((Wrappers.<SysDictionaryItem>lambdaQuery()
                .eq(SysDictionaryItem::getCode, code).eq(SysDictionaryItem::getKey, key)));
        ApiAssert.isNotNull(sysDictionaryItem, SysDictionaryItemErrorResponses.sysDictionaryItemNotExist());

        logger.debug("启用系统字典项目");
        sysDictionaryItem.setCode(code);
        sysDictionaryItem.setKey(key);
        sysDictionaryItem.setStatus(StatusEnum.ENABLED);
        sysDictionaryItem.setUpdatedSysUserId(operatedSysUserId);
        sysDictionaryItem.setUpdatedDateTime(LocalDateTime.now());
        int i = sysDictionaryItemMapper.updateById(sysDictionaryItem);
        ApiAssert.isEqualToOne(i, SysDictionaryItemErrorResponses.updateSysDictionaryItemFailure());
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void disableSysDictionaryItem(String sysDictionaryItemId, String operatedSysUserId) {
        ApiAssert.isNotBlank(sysDictionaryItemId, SysDictionaryItemErrorResponses.sysDictionaryItemIdCanNotNull());
        ApiAssert.isNotBlank(operatedSysUserId, SysUserErrorResponses.operatedSysUserIdCanNotBlank());

        logger.debug("检查操作的系统用户");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, SysUserErrorResponses.operatedSysUserNotExist());

        logger.debug("检查系统字典项目是否存在");
        SysDictionaryItem sysDictionaryItem = sysDictionaryItemMapper.selectById(sysDictionaryItemId);
        ApiAssert.isNotNull(sysDictionaryItem, SysDictionaryItemErrorResponses.sysDictionaryItemNotExist());

        logger.debug("禁用系统字典项目");
        sysDictionaryItem.setStatus(StatusEnum.DISABLED);
        sysDictionaryItem.setUpdatedSysUserId(operatedSysUserId);
        sysDictionaryItem.setUpdatedDateTime(LocalDateTime.now());
        int i = sysDictionaryItemMapper.updateById(sysDictionaryItem);
        ApiAssert.isEqualToOne(i, SysDictionaryItemErrorResponses.updateSysDictionaryItemFailure());
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void disableSysDictionaryItemByCodeAndKey(String code, String key, String operatedSysUserId) {
        ApiAssert.isNotBlank(code, SysDictionaryItemErrorResponses.sysDictionaryItemCodeCanNotBlank());
        ApiAssert.isNotBlank(key, SysDictionaryItemErrorResponses.sysDictionaryItemKeyCanNotBlank());
        ApiAssert.isNotBlank(operatedSysUserId, SysUserErrorResponses.operatedSysUserIdCanNotBlank());

        logger.debug("检查操作的系统用户");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, SysUserErrorResponses.operatedSysUserNotExist());

        logger.debug("检查系统字典项目是否存在");
        SysDictionaryItem sysDictionaryItem = sysDictionaryItemMapper.selectOne((Wrappers.<SysDictionaryItem>lambdaQuery().eq(SysDictionaryItem::getCode, code)));
        ApiAssert.isNotNull(sysDictionaryItem, SysDictionaryItemErrorResponses.sysDictionaryItemNotExist());

        logger.debug("禁用系统字典项目");
        sysDictionaryItem.setCode(code);
        sysDictionaryItem.setKey(key);
        sysDictionaryItem.setStatus(StatusEnum.DISABLED);
        sysDictionaryItem.setUpdatedSysUserId(operatedSysUserId);
        sysDictionaryItem.setUpdatedDateTime(LocalDateTime.now());
        int i = sysDictionaryItemMapper.updateById(sysDictionaryItem);
        ApiAssert.isEqualToOne(i, SysDictionaryItemErrorResponses.updateSysDictionaryItemFailure());
    }
}
