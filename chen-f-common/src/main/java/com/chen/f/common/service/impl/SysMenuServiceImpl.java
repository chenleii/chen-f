package com.chen.f.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.f.common.service.ISysMenuService;
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
import com.chen.f.core.api.ApiAssert;
import com.chen.f.core.api.response.error.ErrorResponse;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
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
        ApiAssert.isNotBlank(sysMenuId, ErrorResponse.create("系统菜单ID不能为空"));
        return sysMenuMapper.selectById(sysMenuId);
    }

    @Override
    public void createSysMenu(String parentId, String name, String url, String icon, SysMenuTypeEnum type,
                              Integer order, String remark, StatusEnum status, String operatedSysUserId) {
        //ApiAssert.isNotNull(parentId, ErrorResponse.create("系统菜单父级ID不能为空"));
        ApiAssert.isNotBlank(name, ErrorResponse.create("系统菜单名称不能为空"));
        //ApiAssert.isNotBlank(url, ErrorResponse.create("系统菜单URL不能为空"));
        //ApiAssert.isNotBlank(icon, ErrorResponse.create("系统菜单图标不能为空"));
        ApiAssert.isNotNull(type, ErrorResponse.create("系统菜单类型不能为空"));
        //ApiAssert.isNotNull(order, ErrorResponse.create("系统菜单顺序不能为空"));
        //ApiAssert.isNotNull(remark, ErrorResponse.create("系统菜单备注不能为空"));
        ApiAssert.isNotNull(status, ErrorResponse.create("系统菜单状态不能为空"));
        ApiAssert.isNotBlank(operatedSysUserId, ErrorResponse.create("操作的系统用户ID不能为空"));

        logger.debug("检查操作的系统用户");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, ErrorResponse.create("操作的系统用户不存在"));

        //初始化父级菜单默认值
        parentId = ObjectUtils.defaultIfNull(parentId, "");
        
        url = ObjectUtils.defaultIfNull(url, "");
        icon = ObjectUtils.defaultIfNull(icon, "");
        remark = ObjectUtils.defaultIfNull(remark, "");

        logger.debug("检查菜单显示顺序");
        if (Objects.isNull(order)) {
            List<SysMenu> sysMenuList = sysMenuMapper.selectList(Wrappers.<SysMenu>lambdaQuery().eq(SysMenu::getParentId, parentId));

            if (CollectionUtils.isNotEmpty(sysMenuList)) {
                //获取当前菜单下最后一个顺序号
                SysMenu lastSysMenu = sysMenuList.get(sysMenuList.size() - 1);
                order = lastSysMenu.getOrder() + 1;
            } else {
                //默认顺序号1
                order = 1;
            }
        }

        logger.debug("插入系统菜单");
        SysMenu sysMenu = new SysMenu();
        sysMenu.setParentId(parentId);
        sysMenu.setName(name);
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
        ApiAssert.isEqualToOne(i, ErrorResponse.create("系统菜单插入失败"));

    }

    @Override
    public void updateSysMenu(String sysMenuId, String parentId, String name, String url, String icon, SysMenuTypeEnum type,
                              Integer order, String remark, StatusEnum status, String operatedSysUserId) {
        ApiAssert.isNotBlank(sysMenuId, ErrorResponse.create("系统菜单ID不能为空"));
        //ApiAssert.isNotBlank(parentId, ErrorResponse.create("系统菜单父级ID不能为空"));
        ApiAssert.isNotBlank(name, ErrorResponse.create("系统菜单名称不能为空"));
        ApiAssert.isNotBlank(url, ErrorResponse.create("系统菜单URL不能为空"));
        //ApiAssert.isNotBlank(icon, ErrorResponse.create("系统菜单图标不能为空"));
        ApiAssert.isNotNull(type, ErrorResponse.create("系统菜单类型不能为空"));
        //ApiAssert.isNotNull(order, ErrorResponse.create("系统菜单顺序不能为空"));
        //ApiAssert.isNotBlank(remark, ErrorResponse.create("系统菜单备注不能为空"));
        ApiAssert.isNotNull(status, ErrorResponse.create("系统菜单状态不能为空"));
        ApiAssert.isNotBlank(operatedSysUserId, ErrorResponse.create("操作的系统用户ID不能为空"));

        logger.debug("检查操作的系统用户");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, ErrorResponse.create("操作的系统用户不存在"));

        logger.debug("检查系统菜单是否存在");
        SysMenu sysMenu = sysMenuMapper.selectById(sysMenuId);
        ApiAssert.isNotNull(sysMenu, ErrorResponse.create("系统菜单不存在"));

        //初始化父级菜单默认值
        parentId = ObjectUtils.defaultIfNull(parentId, "");

        logger.debug("检查菜单显示顺序");
        if (Objects.isNull(order)) {
            List<SysMenu> sysMenuList = sysMenuMapper.selectList(Wrappers.<SysMenu>lambdaQuery().eq(SysMenu::getParentId, parentId));
            if (CollectionUtils.isNotEmpty(sysMenuList)) {
                //获取当前菜单下最后一个顺序号
                SysMenu lastSysMenu = sysMenuList.get(sysMenuList.size() - 1);
                if (Objects.equals(sysMenu.getId(), lastSysMenu.getId())) {
                    order = sysMenu.getOrder();
                } else {
                    order = lastSysMenu.getOrder() + 1;
                }
            } else {
                //默认顺序号1
                order = 1;
            }
        }

        logger.debug("修改系统菜单");
        sysMenu.setParentId(parentId);
        sysMenu.setName(name);
        sysMenu.setUrl(url);
        sysMenu.setIcon(icon);
        sysMenu.setType(type);
        sysMenu.setOrder(order);
        sysMenu.setRemark(remark);
        sysMenu.setStatus(status);
        sysMenu.setUpdatedSysUserId(operatedSysUserId);
        sysMenu.setUpdatedDateTime(LocalDateTime.now());
        int i = sysMenuMapper.updateById(sysMenu);
        ApiAssert.isEqualToOne(i, ErrorResponse.create("系统菜单修改失败"));
    }

    @Override
    public void deleteSysMenu(String sysMenuId) {
        ApiAssert.isNotBlank(sysMenuId, ErrorResponse.create("系统菜单ID不能为空"));

        SysMenu sysMenu = sysMenuMapper.selectById(sysMenuId);
        ApiAssert.isNotNull(sysMenu, ErrorResponse.create("系统菜单不存在"));

        int i = sysMenuMapper.deleteById(sysMenuId);
        ApiAssert.isEqualToOne(i, ErrorResponse.create("删除系统菜单失败"));

        //删除系统角色菜单
        sysRoleMenuMapper.delete(Wrappers.<SysRoleMenu>lambdaQuery().eq(SysRoleMenu::getSysMenuId, sysMenuId));

        //删除系统权限菜单
        sysPermissionMenuMapper.delete(Wrappers.<SysPermissionMenu>lambdaQuery().eq(SysPermissionMenu::getSysMenuId, sysMenuId));
    }

    @Override
    public void enabledSysMenu(String sysMenuId, String operatedSysUserId) {
        ApiAssert.isNotBlank(sysMenuId, ErrorResponse.create("系统菜单ID不能为空"));
        ApiAssert.isNotBlank(operatedSysUserId, ErrorResponse.create("操作的系统用户ID不能为空"));

        logger.debug("检查操作的系统用户");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, ErrorResponse.create("操作的系统用户不存在"));

        logger.debug("检查系统菜单是否存在");
        SysMenu sysMenu = sysMenuMapper.selectById(sysMenuId);
        ApiAssert.isNotNull(sysMenu, ErrorResponse.create("系统菜单不存在"));

        logger.debug("启用系统菜单");
        sysMenu.setStatus(StatusEnum.ENABLED);
        sysMenu.setUpdatedSysUserId(operatedSysUserId);
        sysMenu.setUpdatedDateTime(LocalDateTime.now());
        int i = sysMenuMapper.updateById(sysMenu);
        ApiAssert.isEqualToOne(i, ErrorResponse.create("系统菜单启用失败"));
    }

    @Override
    public void disableSysMenu(String sysMenuId, String operatedSysUserId) {
        ApiAssert.isNotBlank(sysMenuId, ErrorResponse.create("系统菜单ID不能为空"));
        ApiAssert.isNotBlank(operatedSysUserId, ErrorResponse.create("操作的系统用户ID不能为空"));

        logger.debug("检查操作的系统用户");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, ErrorResponse.create("操作的系统用户不存在"));

        logger.debug("检查系统菜单是否存在");
        SysMenu sysMenu = sysMenuMapper.selectById(sysMenuId);
        ApiAssert.isNotNull(sysMenu, ErrorResponse.create("系统菜单不存在"));

        logger.debug("禁用系统菜单");
        sysMenu.setStatus(StatusEnum.DISABLED);
        sysMenu.setUpdatedSysUserId(operatedSysUserId);
        sysMenu.setUpdatedDateTime(LocalDateTime.now());
        int i = sysMenuMapper.updateById(sysMenu);
        ApiAssert.isEqualToOne(i, ErrorResponse.create("系统菜单禁用失败"));
    }

    @Override
    public List<SysMenu> getEnabledSysMenuListBySysRoleIdList(List<String> sysRoleIdList) {
        if (CollectionUtils.isEmpty(sysRoleIdList)) {
            //系统角色ID列表为空
            return Collections.emptyList();
        }

        final List<SysRoleMenu> sysRoleMenuList = sysRoleMenuMapper.selectList(Wrappers.<SysRoleMenu>lambdaQuery().in(SysRoleMenu::getSysRoleId, sysRoleIdList));
        if (CollectionUtils.isEmpty(sysRoleMenuList)) {
            return Collections.emptyList();
        }
        final List<String> sysMenuIdList = sysRoleMenuList.stream()
                .map(SysRoleMenu::getSysMenuId)
                .collect(Collectors.toList());
        return sysMenuMapper.selectList(Wrappers.<SysMenu>lambdaQuery().in(SysMenu::getId, sysMenuIdList).eq(SysMenu::getStatus, StatusEnum.ENABLED).orderByAsc(SysMenu::getOrder));
    }

    @Override
    public List<SysMenu> getEnabledSysMenuListBySysPermissionIdList(List<String> sysPermissionIdList) {
        if (CollectionUtils.isEmpty(sysPermissionIdList)) {
            //系统权限ID列表为空
            return Collections.emptyList();
        }

        final List<SysPermissionMenu> sysPermissionMenuList = sysPermissionMenuMapper.selectList(Wrappers.<SysPermissionMenu>lambdaQuery().in(SysPermissionMenu::getSysPermissionId, sysPermissionIdList));
        if (CollectionUtils.isEmpty(sysPermissionMenuList)) {
            return Collections.emptyList();
        }
        final List<String> sysMenuIdList = sysPermissionMenuList.stream()
                .map(SysPermissionMenu::getSysMenuId)
                .collect(Collectors.toList());
        return sysMenuMapper.selectList(Wrappers.<SysMenu>lambdaQuery().in(SysMenu::getId, sysMenuIdList).eq(SysMenu::getStatus, StatusEnum.ENABLED).orderByAsc(SysMenu::getOrder));
    }

}
