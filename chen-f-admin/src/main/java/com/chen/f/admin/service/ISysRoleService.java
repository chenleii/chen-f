package com.chen.f.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chen.f.core.pojo.SysPermission;
import com.chen.f.core.pojo.SysRole;
import com.chen.f.core.pojo.enums.StatusEnum;

import java.util.List;

/**
 * <p>
 * 系统角色表 服务类
 * </p>
 *
 * @author chen
 * @since 2018-12-02
 */
public interface ISysRoleService extends IService<SysRole> {

    /**
     * 获取分页的系统角色集合
     *
     * @param pageIndex  页数
     * @param pageNumber 页大小
     * @param name       系统角色名称
     * @param remark     系统角色描述
     * @param status     系统角色状态
     * @return 分页的系统角色集合
     */
    IPage<SysRole> getSysRolePage(long pageIndex, long pageNumber, String name, String remark, StatusEnum status);

    /**
     * 获取启用的系统角色列表
     *
     * @return 启用的系统角色列表
     */
    List<SysRole> getEnabledSysRoleList();

    /**
     * 获取系统角色
     *
     * @param sysRoleId 系统角色id
     * @return 系统角色
     */
    SysRole getSysRole(String sysRoleId);

    /**
     * 获取系统角色的系统权限集合
     *
     * @param sysRoleId 系统用户id
     * @return 系统权限集合
     */
    List<SysPermission> getSysPermissionOfSysRole(String sysRoleId);

    /**
     * 创建系统角色
     *
     * @param name              角色名
     * @param remark            备注
     * @param status            状态
     * @param operatedSysUserId 操作的系统用户id
     */
    void createSysRole(String name, String remark, StatusEnum status, String operatedSysUserId);

    /**
     * 设置系统角色的系统权限
     *
     * @param sysRoleId           设置的系统角色id
     * @param sysPermissionIdList 系统权限id集合
     * @param operatedSysUserId   操作的系统用户id
     */
    void setSysPermissionOfSysRole(String sysRoleId, List<String> sysPermissionIdList, String operatedSysUserId);

    /**
     * 设置系统角色的系统菜单
     *
     * @param sysRoleId         设置的系统角色id
     * @param sysMenuIdList     系统菜单id集合
     * @param operatedSysUserId 操作的系统用户id
     */
    void setSysMenuOfSysRole(String sysRoleId, List<String> sysMenuIdList, String operatedSysUserId);

    /**
     * 设置系统角色的系统API
     *
     * @param sysRoleId         设置的系统角色id
     * @param sysApiIdList      系统Api id集合
     * @param operatedSysUserId 操作的系统用户id
     */
    void setSysApiOfSysRole(String sysRoleId, List<String> sysApiIdList, String operatedSysUserId);

    /**
     * 修改系统角色
     *
     * @param sysRoleId         系统角色id
     * @param name              角色名
     * @param remark            备注
     * @param status            状态
     * @param operatedSysUserId 操作的系统用户id
     */
    void updateSysRole(String sysRoleId, String name, String remark, StatusEnum status, String operatedSysUserId);

    /**
     * 删除系统角色
     *
     * @param sysRoleId 系统角色id
     */
    void deleteSysRole(String sysRoleId);

    /**
     * 启用系统角色
     *
     * @param sysRoleId         系统角色id
     * @param operatedSysUserId 操作的系统用户id
     */
    void enabledSysRole(String sysRoleId, String operatedSysUserId);

    /**
     * 禁用系统角色
     *
     * @param sysRoleId         系统角色id
     * @param operatedSysUserId 操作的系统用户id
     */
    void disableSysRole(String sysRoleId, String operatedSysUserId);

}
