package com.chen.f.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chen.f.core.pojo.SysMenu;
import com.chen.f.core.pojo.enums.StatusEnum;
import com.chen.f.core.pojo.enums.SysMenuTypeEnum;

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
     * 获取系统菜单
     *
     * @return 所有系统菜单
     */
    List<SysMenu> getSysMenuList();

    /**
     * 获取启用的系统菜单列表
     *
     * @return 启用的系统菜单列表
     */
    List<SysMenu> getEnabledSysMenuList();

    /**
     * 获取系统菜单
     *
     * @param sysMenuId 系统菜单id
     * @return 系统菜单
     */
    SysMenu getSysMenu(String sysMenuId);

    /**
     * 创建系统菜单
     *
     * @param parentId          系统菜单父级id
     * @param name              菜单名称
     * @param url               菜单url
     * @param icon              菜单图表
     * @param type              菜单类型
     * @param remark            菜单描述
     * @param order             菜单顺序
     * @param status            菜单状态
     * @param operatedSysUserId 操作用户id
     */
    void createSysMenu(String parentId, String name, String url, String icon, SysMenuTypeEnum type, String remark, Integer order,
                       StatusEnum status, String operatedSysUserId);

    /**
     * 创建系统菜单
     *
     * @param sysMenuId         系统菜单id
     * @param parentId          父级系统菜单id
     * @param name              菜单名称
     * @param url               菜单url
     * @param icon              菜单图表
     * @param type              菜单类型
     * @param remark            菜单描述
     * @param order             菜单顺序
     * @param status            菜单状态
     * @param operatedSysUserId 操作用户id
     */
    void updateSysMenu(String sysMenuId, String parentId, String name, String url, String icon, SysMenuTypeEnum type, String remark, Integer order,
                       StatusEnum status, String operatedSysUserId);

    /**
     * 删除系统菜单
     *
     * @param sysMenuId 系统菜单
     */
    void deleteSysMenu(String sysMenuId);


    /**
     * 启用系统菜单
     *
     * @param sysMenuId         系统菜单id
     * @param operatedSysUserId 操作的用户id
     */
    void enabledSysMenu(String sysMenuId, String operatedSysUserId);

    /**
     * 禁用系统菜单
     *
     * @param sysMenuId         系统菜单id
     * @param operatedSysUserId 操作的用户id
     */
    void disableSysMenu(String sysMenuId, String operatedSysUserId);
}
