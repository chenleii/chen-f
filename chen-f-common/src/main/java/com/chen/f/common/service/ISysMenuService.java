package com.chen.f.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chen.f.common.pojo.SysMenu;
import com.chen.f.common.pojo.enums.StatusEnum;
import com.chen.f.common.pojo.enums.SysMenuTypeEnum;

import java.util.List;

/**
 * <p>
 * 系统菜单表 服务类
 * </p>
 *
 * @author chen
 * @since 2019-01-15
 */
public interface ISysMenuService extends IService<SysMenu> {

    /**
     * 获取所有的系统菜单
     *
     * @return 所有的系统菜单
     */
    List<SysMenu> getAllSysMenuList();

    /**
     * 获取启用的系统菜单列表
     *
     * @return 启用的系统菜单列表
     */
    List<SysMenu> getEnabledSysMenuList();

    /**
     * 获取系统菜单列表
     *
     * @param parentId 父级ID
     * @param name     系统菜单名称
     * @param url      系统菜单URL
     * @param type     系统菜单类型
     * @param remark   系统菜单备注
     * @param status   系统菜单状态
     * @return 系统菜单列表
     */
    List<SysMenu> getSysMenuList(String parentId, String name, String url,
                                 SysMenuTypeEnum type, String remark, StatusEnum status);

    /**
     * 获取系统菜单
     *
     * @param sysMenuId 系统菜单ID
     * @return 系统菜单
     */
    SysMenu getSysMenu(String sysMenuId);

    /**
     * 创建系统菜单
     *
     * @param parentId          系统菜单父级ID
     * @param name              系统菜单名称
     * @param url               系统菜单URL
     * @param icon              系统菜单图标
     * @param type              系统菜单类型
     * @param order             系统菜单顺序
     * @param remark            系统菜单描述
     * @param status            系统菜单状态
     * @param operatedSysUserId 操作用户ID
     */
    void createSysMenu(String parentId, String name, String url, String icon, SysMenuTypeEnum type, Integer order, String remark,
                       StatusEnum status, String operatedSysUserId);

    /**
     * 创建系统菜单
     *
     * @param sysMenuId         系统菜单ID
     * @param parentId          父级系统菜单ID
     * @param name              系统菜单名称
     * @param url               系统菜单URL
     * @param icon              系统菜单图标
     * @param type              系统菜单类型
     * @param order             系统菜单顺序
     * @param remark            系统菜单描述
     * @param status            系统菜单状态
     * @param operatedSysUserId 操作用户ID
     */
    void updateSysMenu(String sysMenuId, String parentId, String name, String url, String icon, SysMenuTypeEnum type, Integer order, String remark,
                       StatusEnum status, String operatedSysUserId);

    /**
     * 删除系统菜单
     *
     * @param sysMenuId 系统菜单ID
     */
    void deleteSysMenu(String sysMenuId);


    /**
     * 启用系统菜单
     *
     * @param sysMenuId         系统菜单ID
     * @param operatedSysUserId 操作的系统用户ID
     */
    void enabledSysMenu(String sysMenuId, String operatedSysUserId);

    /**
     * 禁用系统菜单
     *
     * @param sysMenuId         系统菜单ID
     * @param operatedSysUserId 操作的系统用户ID
     */
    void disableSysMenu(String sysMenuId, String operatedSysUserId);


    /**
     * 根据系统角色ID列表获取启用的系统菜单列表
     *
     * @param sysRoleIdList 系统角色ID列表
     * @return 系统菜单列表
     */
    List<SysMenu> getEnabledSysMenuListBySysRoleIdList(List<String> sysRoleIdList);

    /**
     * 根据系统权限ID列表获取启用的系统菜单列表
     *
     * @param sysPermissionIdList 系统权限ID列表
     * @return 系统菜单列表
     */
    List<SysMenu> getEnabledSysMenuListBySysPermissionIdList(List<String> sysPermissionIdList);
}
