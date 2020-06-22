package com.chen.f.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.f.common.api.response.error.SysMenuErrorResponses;
import com.chen.f.common.api.response.error.SysUserErrorResponses;
import com.chen.f.common.mapper.SysMenuMapper;
import com.chen.f.common.mapper.SysPermissionMenuMapper;
import com.chen.f.common.mapper.SysRoleMenuMapper;
import com.chen.f.common.mapper.SysUserMapper;
import com.chen.f.common.pojo.SysMenu;
import com.chen.f.common.pojo.SysPermissionMenu;
import com.chen.f.common.pojo.SysRoleMenu;
import com.chen.f.common.pojo.SysUser;
import com.chen.f.common.pojo.enums.StatusEnum;
import com.chen.f.common.pojo.enums.SysMenuTypeEnum;
import com.chen.f.common.service.ISysMenuService;
import com.chen.f.core.api.ApiAssert;
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
 * 系统菜单表 服务实现类
 * </p>
 *
 * @author chen
 * @since 2019-01-15
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {
    protected static final Logger logger = LoggerFactory.getLogger(SysMenuServiceImpl.class);

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;
    @Autowired
    private SysPermissionMenuMapper sysPermissionMenuMapper;

    @Override
    public List<SysMenu> getAllSysMenuList() {
        return sysMenuMapper.selectAll();
    }

    @Override
    public List<SysMenu> getEnabledSysMenuList() {
        return sysMenuMapper.selectList(Wrappers.<SysMenu>lambdaQuery().eq(SysMenu::getStatus, StatusEnum.ENABLED));
    }

    @Override
    public List<SysMenu> getSysMenuList(String parentId, String name, String url, SysMenuTypeEnum type, String remark, StatusEnum status) {
        final LambdaQueryWrapper<SysMenu> lambdaQueryWrapper = Wrappers.<SysMenu>lambdaQuery();
        lambdaQueryWrapper.eq(StringUtils.isNotBlank(parentId), SysMenu::getParentId, parentId);
        lambdaQueryWrapper.eq(StringUtils.isNotBlank(name), SysMenu::getName, name);
        lambdaQueryWrapper.like(StringUtils.isNotBlank(url), SysMenu::getUrl, StringUtils.replace(url, "*", "%"));
        lambdaQueryWrapper.eq(Objects.nonNull(type), SysMenu::getType, type);
        lambdaQueryWrapper.like(StringUtils.isNotBlank(remark), SysMenu::getRemark, remark);
        lambdaQueryWrapper.eq(Objects.nonNull(status), SysMenu::getStatus, status);
        return sysMenuMapper.selectList(lambdaQueryWrapper);
    }

    @Override
    public SysMenu getSysMenu(String sysMenuId) {
        ApiAssert.isNotBlank(sysMenuId, SysMenuErrorResponses.sysMenuIdCanNotNull());
        return sysMenuMapper.selectById(sysMenuId);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void createSysMenu(String parentId, String name, String nameI18n, String url, String icon, SysMenuTypeEnum type,
                              Integer order, String remark, StatusEnum status, String operatedSysUserId) {
        ApiAssert.isNotBlank(name, SysMenuErrorResponses.sysMenuNameCanNotBlank());
        ApiAssert.isNotNull(type, SysMenuErrorResponses.sysMenuTypeCanNotNull());
        ApiAssert.isNotNull(status, SysMenuErrorResponses.sysMenuStatusCanNotNull());
        ApiAssert.isNotBlank(operatedSysUserId, SysUserErrorResponses.operatedSysUserIdCanNotBlank());

        logger.debug("检查操作的系统用户");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, SysMenuErrorResponses.sysMenuNotExist());

        //初始化父级菜单默认值
        parentId = ObjectUtils.defaultIfNull(parentId, "");

        nameI18n = ObjectUtils.defaultIfNull(nameI18n, "");
        url = ObjectUtils.defaultIfNull(url, "");
        icon = ObjectUtils.defaultIfNull(icon, "");
        remark = ObjectUtils.defaultIfNull(remark, "");

        logger.debug("检查系统菜单显示顺序");
        if (Objects.isNull(order)) {
            SysMenu maxOrderSysMenu = sysMenuMapper.selectFirstOne(Wrappers.<SysMenu>lambdaQuery().eq(SysMenu::getParentId, parentId).orderByDesc(SysMenu::getOrder));

            if (Objects.nonNull(maxOrderSysMenu)) {
                order = maxOrderSysMenu.getOrder() + 1;
            } else {
                //默认顺序号1
                order = 1;
            }
        }

        logger.debug("插入系统菜单");
        SysMenu sysMenu = new SysMenu();
        sysMenu.setParentId(parentId);
        sysMenu.setName(name);
        sysMenu.setNameI18n(nameI18n);
        sysMenu.setUrl(url);
        sysMenu.setIcon(icon);
        sysMenu.setType(type);
        sysMenu.setOrder(order);
        sysMenu.setRemark(remark);
        sysMenu.setStatus(status);
        sysMenu.setUpdatedSysUserId(operatedSysUserId);
        sysMenu.setCreatedSysUserId(operatedSysUserId);
        sysMenu.setUpdatedDateTime(LocalDateTime.now());
        sysMenu.setCreatedDateTime(LocalDateTime.now());
        int i = sysMenuMapper.insert(sysMenu);
        ApiAssert.isEqualToOne(i, SysMenuErrorResponses.createSysMenuFailure());

    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void updateSysMenu(String sysMenuId, String parentId, String name, String nameI18n, String url, String icon, SysMenuTypeEnum type,
                              Integer order, String remark, StatusEnum status, String operatedSysUserId) {
        ApiAssert.isNotBlank(sysMenuId, SysMenuErrorResponses.sysMenuIdCanNotNull());
        ApiAssert.isNotBlank(name, SysMenuErrorResponses.sysMenuNameCanNotBlank());
        ApiAssert.isNotNull(type, SysMenuErrorResponses.sysMenuTypeCanNotNull());
        ApiAssert.isNotNull(status, SysMenuErrorResponses.sysMenuStatusCanNotNull());
        ApiAssert.isNotBlank(operatedSysUserId, SysUserErrorResponses.operatedSysUserIdCanNotBlank());

        logger.debug("检查操作的系统用户");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, SysUserErrorResponses.operatedSysUserNotExist());

        logger.debug("检查系统菜单是否存在");
        SysMenu sysMenu = sysMenuMapper.selectById(sysMenuId);
        ApiAssert.isNotNull(sysMenu, SysMenuErrorResponses.sysMenuNotExist());

        //初始化父级菜单默认值
        parentId = ObjectUtils.defaultIfNull(parentId, "");

        nameI18n = ObjectUtils.defaultIfNull(nameI18n, "");
        url = ObjectUtils.defaultIfNull(url, "");
        icon = ObjectUtils.defaultIfNull(icon, "");
        remark = ObjectUtils.defaultIfNull(remark, "");

        logger.debug("检查系统菜单显示顺序");
        if (Objects.isNull(order)) {
            SysMenu maxOrderSysMenu = sysMenuMapper.selectFirstOne(Wrappers.<SysMenu>lambdaQuery().eq(SysMenu::getParentId, parentId).orderByDesc(SysMenu::getOrder));

            if (Objects.nonNull(maxOrderSysMenu)) {
                order = maxOrderSysMenu.getOrder() + 1;
            } else {
                //默认顺序号1
                order = 1;
            }
        }

        logger.debug("修改系统菜单");
        sysMenu.setParentId(parentId);
        sysMenu.setName(name);
        sysMenu.setNameI18n(nameI18n);
        sysMenu.setUrl(url);
        sysMenu.setIcon(icon);
        sysMenu.setType(type);
        sysMenu.setOrder(order);
        sysMenu.setRemark(remark);
        sysMenu.setStatus(status);
        sysMenu.setUpdatedSysUserId(operatedSysUserId);
        sysMenu.setUpdatedDateTime(LocalDateTime.now());
        int i = sysMenuMapper.updateById(sysMenu);
        ApiAssert.isEqualToOne(i, SysMenuErrorResponses.updateSysMenuFailure());
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void deleteSysMenu(String sysMenuId) {
        ApiAssert.isNotBlank(sysMenuId, SysMenuErrorResponses.sysMenuIdCanNotNull());

        SysMenu sysMenu = sysMenuMapper.selectById(sysMenuId);
        ApiAssert.isNotNull(sysMenu, SysMenuErrorResponses.sysMenuNotExist());

        int i = sysMenuMapper.deleteById(sysMenuId);
        ApiAssert.isEqualToOne(i, SysMenuErrorResponses.deleteSysMenuFailure());

        //删除系统角色菜单
        sysRoleMenuMapper.delete(Wrappers.<SysRoleMenu>lambdaQuery().eq(SysRoleMenu::getSysMenuId, sysMenuId));

        //删除系统权限菜单
        sysPermissionMenuMapper.delete(Wrappers.<SysPermissionMenu>lambdaQuery().eq(SysPermissionMenu::getSysMenuId, sysMenuId));
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void enabledSysMenu(String sysMenuId, String operatedSysUserId) {
        ApiAssert.isNotBlank(sysMenuId, SysMenuErrorResponses.sysMenuIdCanNotNull());
        ApiAssert.isNotBlank(operatedSysUserId, SysUserErrorResponses.operatedSysUserIdCanNotBlank());

        logger.debug("检查操作的系统用户");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, SysUserErrorResponses.operatedSysUserNotExist());

        logger.debug("检查系统菜单是否存在");
        SysMenu sysMenu = sysMenuMapper.selectById(sysMenuId);
        ApiAssert.isNotNull(sysMenu, SysMenuErrorResponses.sysMenuNotExist());

        logger.debug("启用系统菜单");
        sysMenu.setStatus(StatusEnum.ENABLED);
        sysMenu.setUpdatedSysUserId(operatedSysUserId);
        sysMenu.setUpdatedDateTime(LocalDateTime.now());
        int i = sysMenuMapper.updateById(sysMenu);
        ApiAssert.isEqualToOne(i, SysMenuErrorResponses.updateSysMenuFailure());
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void disableSysMenu(String sysMenuId, String operatedSysUserId) {
        ApiAssert.isNotBlank(sysMenuId, SysMenuErrorResponses.sysMenuIdCanNotNull());
        ApiAssert.isNotBlank(operatedSysUserId, SysUserErrorResponses.operatedSysUserIdCanNotBlank());

        logger.debug("检查操作的系统用户");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, SysUserErrorResponses.operatedSysUserNotExist());

        logger.debug("检查系统菜单是否存在");
        SysMenu sysMenu = sysMenuMapper.selectById(sysMenuId);
        ApiAssert.isNotNull(sysMenu, SysMenuErrorResponses.sysMenuNotExist());

        logger.debug("禁用系统菜单");
        sysMenu.setStatus(StatusEnum.DISABLED);
        sysMenu.setUpdatedSysUserId(operatedSysUserId);
        sysMenu.setUpdatedDateTime(LocalDateTime.now());
        int i = sysMenuMapper.updateById(sysMenu);
        ApiAssert.isEqualToOne(i, SysMenuErrorResponses.updateSysMenuFailure());
    }


}
