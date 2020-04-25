package com.chen.f.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
import com.chen.f.core.api.response.error.ErrorResponse;
import com.chen.f.core.page.Page;
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
        ApiAssert.isNotBlank(sysDictionaryId, ErrorResponse.create("系统字典ID不能为空"));
        return sysDictionaryMapper.selectById(sysDictionaryId);
    }

    @Override
    public SysDictionary getSysDictionaryByCode(String code) {
        ApiAssert.isNotBlank(code, ErrorResponse.create("系统字典标识不能为空"));
        return sysDictionaryMapper.selectOne(Wrappers.<SysDictionary>lambdaQuery().eq(SysDictionary::getCode, code));
    }


    @Override
    public void createSysDictionary(String code, String name, SysDictionaryTypeEnum type, String remark,
                                    StatusEnum status, String operatedSysUserId) {
        ApiAssert.isNotBlank(code, ErrorResponse.create("系统字典标识不能为空"));
        ApiAssert.isNotBlank(name, ErrorResponse.create("系统字典名称不能为空"));
        ApiAssert.isNotNull(type, ErrorResponse.create("系统字典类型不能为空"));
        //ApiAssert.isNotBlank(remark, ErrorResponse.create("字典备注不能为空"));
        ApiAssert.isNotNull(status, ErrorResponse.create("系统字典状态不能为空"));
        ApiAssert.isNotBlank(operatedSysUserId, ErrorResponse.create("操作的系统用户ID不能为空"));

        logger.debug("检查操作的系统用户");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, ErrorResponse.create("操作的系统用户不存在"));

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
        ApiAssert.isEqualToOne(i, ErrorResponse.create("创建系统字典失败"));

    }

    @Override
    public void updateSysDictionary(String sysDictionaryId, String code, String name, SysDictionaryTypeEnum type, String remark,
                                    StatusEnum status, String operatedSysUserId) {
        ApiAssert.isNotBlank(sysDictionaryId, ErrorResponse.create("系统字典ID不能为空"));
        ApiAssert.isNotBlank(code, ErrorResponse.create("系统字典标识不能为空"));
        ApiAssert.isNotBlank(name, ErrorResponse.create("系统字典名称不能为空"));
        ApiAssert.isNotNull(type, ErrorResponse.create("系统字典类型不能为空"));
        //ApiAssert.isNotBlank(remark, ErrorResponse.create("字典备注不能为空"));
        ApiAssert.isNotNull(status, ErrorResponse.create("系统字典状态不能为空"));
        ApiAssert.isNotBlank(operatedSysUserId, ErrorResponse.create("操作的系统用户ID不能为空"));

        logger.debug("检查操作的系统用户");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, ErrorResponse.create("操作的系统用户不存在"));

        logger.debug("检查系统字典是否存在");
        SysDictionary sysDictionary = sysDictionaryMapper.selectOne((Wrappers.<SysDictionary>lambdaQuery().eq(SysDictionary::getCode, code)));
        ApiAssert.isNotNull(sysDictionary, ErrorResponse.create("系统字典不存在"));

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
        ApiAssert.isEqualToOne(i, ErrorResponse.create("修改系统字典失败"));
    }

    @Override
    public void deleteSysDictionary(String sysDictionaryId) {
        ApiAssert.isNotBlank(sysDictionaryId, ErrorResponse.create("系统字典ID不能为空"));
        int i = sysDictionaryMapper.deleteById(sysDictionaryId);
        ApiAssert.isEqualToOne(i, ErrorResponse.create("删除系统字典失败"));

        //删除系统字典项目
        sysDictionaryItemMapper.delete(Wrappers.<SysDictionaryItem>lambdaQuery().eq(SysDictionaryItem::getSysDictionaryId, sysDictionaryId));
    }

    @Override
    public void deleteSysDictionaryByCode(String code) {
        ApiAssert.isNotBlank(code, ErrorResponse.create("系统字典标识不能为空"));
        int i = sysDictionaryMapper.delete(Wrappers.<SysDictionary>lambdaQuery().eq(SysDictionary::getCode, code));
        ApiAssert.isGreaterThatZero(i, ErrorResponse.create("删除系统字典失败"));

        //删除系统字典项目
        sysDictionaryItemMapper.delete(Wrappers.<SysDictionaryItem>lambdaQuery().eq(SysDictionaryItem::getCode, code));
    }

    @Override
    public void enabledSysDictionary(String sysDictionaryId, String operatedSysUserId) {
        ApiAssert.isNotBlank(sysDictionaryId, ErrorResponse.create("系统字典ID不能为空"));
        ApiAssert.isNotBlank(operatedSysUserId, ErrorResponse.create("操作的系统用户ID不能为空"));

        logger.debug("检查操作的系统用户");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, ErrorResponse.create("操作的系统用户不存在"));

        logger.debug("检查系统字典是否存在");
        SysDictionary sysDictionary = sysDictionaryMapper.selectById(sysDictionaryId);
        ApiAssert.isNotNull(sysDictionary, ErrorResponse.create("系统字典不存在"));

        logger.debug("启用系统字典");
        sysDictionary.setStatus(StatusEnum.ENABLED);
        sysDictionary.setUpdatedSysUserId(operatedSysUserId);
        sysDictionary.setUpdatedDateTime(LocalDateTime.now());
        int i = sysDictionaryMapper.updateById(sysDictionary);
        ApiAssert.isEqualToOne(i, ErrorResponse.create("启用系统字典失败"));

        //更新系统字典项目
        SysDictionaryItem sysDictionaryItem = new SysDictionaryItem();
        sysDictionaryItem.setStatus(StatusEnum.ENABLED);
        sysDictionaryItemMapper.update(sysDictionaryItem,
                Wrappers.<SysDictionaryItem>lambdaQuery().eq(SysDictionaryItem::getSysDictionaryId, sysDictionaryId));
    }

    @Override
    public void disableSysDictionary(String sysDictionaryId, String operatedSysUserId) {
        ApiAssert.isNotBlank(sysDictionaryId, ErrorResponse.create("系统字典ID不能为空"));
        ApiAssert.isNotBlank(operatedSysUserId, ErrorResponse.create("操作的系统用户ID不能为空"));

        logger.debug("检查操作的系统用户");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, ErrorResponse.create("操作的系统用户不存在"));
        logger.debug("检查系统字典是否存在");
        SysDictionary sysDictionary = sysDictionaryMapper.selectById(sysDictionaryId);
        ApiAssert.isNotNull(sysDictionary, ErrorResponse.create("系统字典不存在"));

        logger.debug("禁用系统字典");
        sysDictionary.setStatus(StatusEnum.DISABLED);
        sysDictionary.setUpdatedSysUserId(operatedSysUserId);
        sysDictionary.setUpdatedDateTime(LocalDateTime.now());
        int i = sysDictionaryMapper.updateById(sysDictionary);
        ApiAssert.isEqualToOne(i, ErrorResponse.create("禁用系统字典失败"));

        //更新系统字典项目
        SysDictionaryItem sysDictionaryItem = new SysDictionaryItem();
        sysDictionaryItem.setStatus(StatusEnum.DISABLED);
        sysDictionaryItemMapper.update(sysDictionaryItem,
                Wrappers.<SysDictionaryItem>lambdaQuery().eq(SysDictionaryItem::getSysDictionaryId, sysDictionaryId));
    }
}
