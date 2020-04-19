package com.chen.f.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.f.core.api.ApiAssert;
import com.chen.f.core.api.response.error.ErrorResponse;
import com.chen.f.common.mapper.SysDictionaryItemMapper;
import com.chen.f.common.mapper.SysUserMapper;
import com.chen.f.common.pojo.SysDictionaryItem;
import com.chen.f.common.pojo.SysUser;
import com.chen.f.common.pojo.enums.StatusEnum;
import com.chen.f.common.pojo.enums.TypeTypeEnum;
import com.chen.f.common.service.ISysDictionaryItemService;
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
    public IPage<SysDictionaryItem> getSysDictionaryItemPage(Long pageIndex, Long pageNumber, String sysDictionaryId, String code, String name, String key, String value, TypeTypeEnum valueTypeEnum, String color, String remark, StatusEnum statusEnum) {
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
        return sysDictionaryItemMapper.selectPage(new Page<>(pageIndex, pageNumber), wrapper);
    }

    @Override
    public List<SysDictionaryItem> getEnabledSysDictionaryItemListByCode() {
        return sysDictionaryItemMapper.selectList(Wrappers.<SysDictionaryItem>lambdaQuery().eq(SysDictionaryItem::getStatus, StatusEnum.ENABLED));
    }

    @Override
    public SysDictionaryItem getSysDictionaryItem(String sysDictionaryItemId) {
        ApiAssert.isNotBlank(sysDictionaryItemId, ErrorResponse.create("系统字典项目ID不能为空"));
        return sysDictionaryItemMapper.selectById(sysDictionaryItemId);
    }

    @Override
    public List<SysDictionaryItem> getSysDictionaryItemListBySysDictionaryId(String sysDictionaryId) {
        return sysDictionaryItemMapper.selectList(Wrappers.<SysDictionaryItem>lambdaQuery().eq(SysDictionaryItem::getSysDictionaryId, sysDictionaryId));
    }

    @Override
    public List<SysDictionaryItem> getSysDictionaryItemListByCode(String code) {
        ApiAssert.isNotBlank(code, ErrorResponse.create("系统字典项目编码不能为空"));
        return sysDictionaryItemMapper.selectList(Wrappers.<SysDictionaryItem>lambdaQuery().eq(SysDictionaryItem::getCode, code));
    }

    @Override
    public List<SysDictionaryItem> getEnabledSysDictionaryItemListByCode(String code) {
        ApiAssert.isNotBlank(code, ErrorResponse.create("系统字典项目编码不能为空"));
        return sysDictionaryItemMapper.selectList(Wrappers.<SysDictionaryItem>lambdaQuery().eq(SysDictionaryItem::getCode, code).eq(SysDictionaryItem::getStatus, StatusEnum.ENABLED));
    }

    @Override
    public SysDictionaryItem getSysDictionaryItemByCodeAndKey(String code, String key) {
        ApiAssert.isNotBlank(code, ErrorResponse.create("系统字典项目编码不能为空"));
        ApiAssert.isNotBlank(key, ErrorResponse.create("系统字典项目KEY不能为空"));
        return sysDictionaryItemMapper.selectOne(Wrappers.<SysDictionaryItem>lambdaQuery().eq(SysDictionaryItem::getCode, code).eq(SysDictionaryItem::getKey, key));
    }

    @Override
    public void createSysDictionaryItem(String sysDictionaryId, String code, String name, String key, String value, String valueI18n, TypeTypeEnum keyType, TypeTypeEnum valueType, String color, Integer order, String remark,
                                        StatusEnum status, String operatedSysUserId) {
        ApiAssert.isNotBlank(sysDictionaryId, ErrorResponse.create("系统字典ID不能为空"));
        ApiAssert.isNotBlank(code, ErrorResponse.create("系统字典项目编码不能为空"));
        ApiAssert.isNotBlank(key, ErrorResponse.create("系统字典项目KEY不能为空"));
        ApiAssert.isNotBlank(name, ErrorResponse.create("系统字典项目名称不能为空"));
        ApiAssert.isNotBlank(value, ErrorResponse.create("系统字典项目值不能为空"));
        //ApiAssert.isNotBlank(valueI18n, ErrorResponse.create("系统字典项目值的国际化不能为空"));
        ApiAssert.isNotNull(keyType, ErrorResponse.create("系统字典项目KEY类型不能为空"));
        ApiAssert.isNotNull(valueType, ErrorResponse.create("系统字典项目值类型不能为空"));
        //ApiAssert.isNotBlank(color, ErrorResponse.create("系统字典项目颜色不能为空"));
        //ApiAssert.isNotBlank(remark, ErrorResponse.create("系统字典项目备注不能为空"));
        ApiAssert.isNotNull(status, ErrorResponse.create("系统字典项目状态不能为空"));
        ApiAssert.isNotBlank(operatedSysUserId, ErrorResponse.create("操作的系统用户ID不能为空"));

        logger.debug("检查操作的系统用户");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, ErrorResponse.create("操作的系统用户不存在"));

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
        ApiAssert.isEqualToOne(i, ErrorResponse.create("创建系统字典项目失败"));

    }

    @Override
    public void updateSysDictionaryItem(String sysDictionaryItemId, String sysDictionaryId, String code, String name, String key, String value, String valueI18n, TypeTypeEnum keyType, TypeTypeEnum valueType, String color, Integer order, String remark,
                                        StatusEnum status, String operatedSysUserId) {
        ApiAssert.isNotBlank(sysDictionaryItemId, ErrorResponse.create("系统字典项目ID不能为空"));
        ApiAssert.isNotBlank(sysDictionaryId, ErrorResponse.create("系统字典ID不能为空"));
        ApiAssert.isNotBlank(code, ErrorResponse.create("系统字典项目编码不能为空"));
        ApiAssert.isNotBlank(key, ErrorResponse.create("系统字典项目KEY不能为空"));
        ApiAssert.isNotBlank(name, ErrorResponse.create("系统字典项目名称不能为空"));
        ApiAssert.isNotBlank(value, ErrorResponse.create("系统字典项目值不能为空"));
        //ApiAssert.isNotBlank(valueI18n, ErrorResponse.create("系统字典项目值的国际化不能为空"));
        ApiAssert.isNotNull(keyType, ErrorResponse.create("系统字典项目KEY类型不能为空"));
        ApiAssert.isNotNull(valueType, ErrorResponse.create("系统字典项目值类型不能为空"));
        //ApiAssert.isNotBlank(color, ErrorResponse.create("系统字典项目颜色不能为空"));
        //ApiAssert.isNotBlank(remark, ErrorResponse.create("系统字典项目备注不能为空"));
        ApiAssert.isNotNull(status, ErrorResponse.create("系统字典项目状态不能为空"));
        ApiAssert.isNotBlank(operatedSysUserId, ErrorResponse.create("操作的系统用户ID不能为空"));

        logger.debug("检查操作的系统用户");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, ErrorResponse.create("操作的系统用户不存在"));

        logger.debug("检查系统字典项目是否存在");
        SysDictionaryItem sysDictionaryItem = sysDictionaryItemMapper.selectById(sysDictionaryItemId);
        ApiAssert.isNotNull(sysDictionaryItem, ErrorResponse.create("系统字典项目不存在"));

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
        ApiAssert.isEqualToOne(i, ErrorResponse.create("修改系统字典项目失败"));
    }

    @Override
    public void deleteSysDictionaryItem(String sysDictionaryItemId) {
        ApiAssert.isNotBlank(sysDictionaryItemId, ErrorResponse.create("系统字典项目ID不能为空"));
        int i = sysDictionaryItemMapper.deleteById(sysDictionaryItemId);
        ApiAssert.isEqualToOne(i, ErrorResponse.create("删除系统字典项目失败"));
    }

    @Override
    public void deleteSysDictionaryItemByCode(String code) {
        ApiAssert.isNotBlank(code, ErrorResponse.create("系统字典项目编码不能为空"));
        int i = sysDictionaryItemMapper.delete(Wrappers.<SysDictionaryItem>lambdaQuery().eq(SysDictionaryItem::getCode, code));
        ApiAssert.isGreaterThatZero(i, ErrorResponse.create("删除系统字典项目失败"));
    }

    @Override
    public void deleteSysDictionaryItemByCodeAndKey(String code, String key) {
        ApiAssert.isNotBlank(code, ErrorResponse.create("系统字典项目编码不能为空"));
        ApiAssert.isNotBlank(key, ErrorResponse.create("系统字典项目KEY不能为空"));
        int i = sysDictionaryItemMapper.delete(Wrappers.<SysDictionaryItem>lambdaQuery().eq(SysDictionaryItem::getCode, code).eq(SysDictionaryItem::getKey, key));
        ApiAssert.isEqualToOne(i, ErrorResponse.create("删除系统字典项目失败"));
    }

    @Override
    public void enabledSysDictionaryItem(String sysDictionaryItemId, String operatedSysUserId) {
        ApiAssert.isNotBlank(sysDictionaryItemId, ErrorResponse.create("系统字典项目ID不能为空"));
        ApiAssert.isNotBlank(operatedSysUserId, ErrorResponse.create("操作的系统用户ID不能为空"));

        logger.debug("检查操作的系统用户");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, ErrorResponse.create("操作的系统用户不存在"));

        logger.debug("检查系统字典项目是否存在");
        SysDictionaryItem sysDictionaryItem = sysDictionaryItemMapper.selectById(sysDictionaryItemId);
        ApiAssert.isNotNull(sysDictionaryItem, ErrorResponse.create("系统字典项目不存在"));

        logger.debug("启用系统字典项目");
        sysDictionaryItem.setStatus(StatusEnum.ENABLED);
        sysDictionaryItem.setUpdatedSysUserId(operatedSysUserId);
        sysDictionaryItem.setUpdatedDateTime(LocalDateTime.now());
        int i = sysDictionaryItemMapper.updateById(sysDictionaryItem);
        ApiAssert.isEqualToOne(i, ErrorResponse.create("启用系统字典项目失败"));
    }

    @Override
    public void enabledSysDictionaryItemByCodeAndKey(String code, String key, String operatedSysUserId) {
        ApiAssert.isNotBlank(code, ErrorResponse.create("系统字典项目编码不能为空"));
        ApiAssert.isNotBlank(key, ErrorResponse.create("系统字典项目KEY不能为空"));
        ApiAssert.isNotBlank(operatedSysUserId, ErrorResponse.create("操作的系统用户ID不能为空"));

        logger.debug("检查操作的系统用户");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, ErrorResponse.create("操作的系统用户不存在"));

        logger.debug("检查系统字典项目是否存在");
        SysDictionaryItem sysDictionaryItem = sysDictionaryItemMapper.selectOne((Wrappers.<SysDictionaryItem>lambdaQuery()
                .eq(SysDictionaryItem::getCode, code).eq(SysDictionaryItem::getKey, key)));
        ApiAssert.isNotNull(sysDictionaryItem, ErrorResponse.create("系统字典项目不存在"));

        logger.debug("启用系统字典项目");
        sysDictionaryItem.setCode(code);
        sysDictionaryItem.setKey(key);
        sysDictionaryItem.setStatus(StatusEnum.ENABLED);
        sysDictionaryItem.setUpdatedSysUserId(operatedSysUserId);
        sysDictionaryItem.setUpdatedDateTime(LocalDateTime.now());
        int i = sysDictionaryItemMapper.update(sysDictionaryItem, Wrappers.<SysDictionaryItem>lambdaQuery().eq(SysDictionaryItem::getCode, code).eq(SysDictionaryItem::getKey, key));
        ApiAssert.isEqualToOne(i, ErrorResponse.create("启用系统字典项目失败"));
    }

    @Override
    public void disableSysDictionaryItem(String sysDictionaryItemId, String operatedSysUserId) {
        ApiAssert.isNotBlank(sysDictionaryItemId, ErrorResponse.create("系统字典项目ID不能为空"));
        ApiAssert.isNotBlank(operatedSysUserId, ErrorResponse.create("操作的系统用户ID不能为空"));

        logger.debug("检查操作的系统用户");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, ErrorResponse.create("操作的系统用户不存在"));
        logger.debug("检查系统字典项目是否存在");
        SysDictionaryItem sysDictionaryItem = sysDictionaryItemMapper.selectById(sysDictionaryItemId);
        ApiAssert.isNotNull(sysDictionaryItem, ErrorResponse.create("系统字典项目不存在"));

        logger.debug("禁用系统字典项目");
        sysDictionaryItem.setStatus(StatusEnum.DISABLED);
        sysDictionaryItem.setUpdatedSysUserId(operatedSysUserId);
        sysDictionaryItem.setUpdatedDateTime(LocalDateTime.now());
        int i = sysDictionaryItemMapper.updateById(sysDictionaryItem);
        ApiAssert.isEqualToOne(i, ErrorResponse.create("禁用系统字典项目失败"));
    }

    @Override
    public void disableSysDictionaryItemByCodeAndKey(String code, String key, String operatedSysUserId) {
        ApiAssert.isNotBlank(code, ErrorResponse.create("系统字典项目编码不能为空"));
        ApiAssert.isNotBlank(key, ErrorResponse.create("系统字典项目KEY不能为空"));
        ApiAssert.isNotBlank(operatedSysUserId, ErrorResponse.create("操作的系统用户ID不能为空"));

        logger.debug("检查操作的系统用户");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, ErrorResponse.create("操作的系统用户不存在"));
        logger.debug("检查系统字典项目是否存在");
        SysDictionaryItem sysDictionaryItem = sysDictionaryItemMapper.selectOne((Wrappers.<SysDictionaryItem>lambdaQuery().eq(SysDictionaryItem::getCode, code)));
        ApiAssert.isNotNull(sysDictionaryItem, ErrorResponse.create("系统字典项目不存在"));

        logger.debug("禁用系统字典项目");
        sysDictionaryItem.setCode(code);
        sysDictionaryItem.setKey(key);
        sysDictionaryItem.setStatus(StatusEnum.DISABLED);
        sysDictionaryItem.setUpdatedSysUserId(operatedSysUserId);
        sysDictionaryItem.setUpdatedDateTime(LocalDateTime.now());
        int i = sysDictionaryItemMapper.update(sysDictionaryItem, Wrappers.<SysDictionaryItem>lambdaQuery().eq(SysDictionaryItem::getCode, code).eq(SysDictionaryItem::getKey, key));
        ApiAssert.isEqualToOne(i, ErrorResponse.create("禁用系统字典项目失败"));
    }
}
