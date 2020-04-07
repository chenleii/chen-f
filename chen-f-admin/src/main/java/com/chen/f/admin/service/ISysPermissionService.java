package com.chen.f.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chen.f.common.pojo.SysPermission;
import com.chen.f.common.pojo.enums.StatusEnum;
import com.chen.f.common.pojo.enums.SysPermissionTypeEnum;

import java.util.List;

/**
 * <p>
 * 系统权限表 服务类
 * </p>
 *
 * @author chen
 * @since 2018-12-02
 */
public interface ISysPermissionService extends IService<SysPermission> {
    /**
     * 获取分页的系统权限集合
     *
     * @param pageIndex  页数
     * @param pageNumber 页大小
     * @param name       系统权限名称
     * @param remark     系统权限描述
     * @param status     系统权限状态
     * @return 分页的系统权限集合
     */
    IPage<SysPermission> getSysPermissionPage(Long pageIndex, Long pageNumber, String name, String remark, StatusEnum status);

    /**
     * 获取启用的系统权限列表
     *
     * @return 启用的系统权限列表
     */
    List<SysPermission> getEnabledSysPermissionList();

    /**
     * 获取系统权限
     *
     * @param sysPermissionId 系统权限ID
     * @return 系统权限
     */
    SysPermission getSysPermission(String sysPermissionId);


    /**
     * 创建系统权限
     *
     * @param name              系统权限名称
     * @param remark            系统权限备注
     * @param type              系统权限类型
     * @param status            系统权限状态
     * @param operatedSysUserId 操作的系统用户ID
     */
    void createSysPermission(String name, String remark, SysPermissionTypeEnum type, StatusEnum status, String operatedSysUserId);

    /**
     * 修改系统权限
     *
     * @param sysPermissionId   系统权限ID
     * @param name              系统权限名称
     * @param remark            系统权限备注
     * @param type              系统权限类型
     * @param status            系统权限状态
     * @param operatedSysUserId 操作的系统用户ID
     */
    void updateSysPermission(String sysPermissionId, String name, String remark, SysPermissionTypeEnum type, StatusEnum status, String operatedSysUserId);


    /**
     * 设置系统权限的系统接口
     *
     * @param sysPermissionId   系统权限ID
     * @param sysApiIdList      系统接口ID集合
     * @param operatedSysUserId 操作的系统用户ID
     */
    void setSysApiOfSysPermission(String sysPermissionId, List<String> sysApiIdList, String operatedSysUserId);

    /**
     * 设置系统权限的系统菜单
     *
     * @param sysPermissionId   系统权限ID
     * @param sysMenuIdList     系统菜单ID集合
     * @param operatedSysUserId 操作的系统用户ID
     */
    void setSysMenuOfSysPermission(String sysPermissionId, List<String> sysMenuIdList, String operatedSysUserId);

    /**
     * 删除系统权限
     *
     * @param sysPermissionId 系统权限ID
     */
    void deleteSysPermission(String sysPermissionId);

    /**
     * 启用系统权限
     *
     * @param sysPermissionId   系统权限ID
     * @param operatedSysUserId 操作的系统用户ID
     */
    void enabledSysPermission(String sysPermissionId, String operatedSysUserId);

    /**
     * 禁用系统权限
     *
     * @param sysPermissionId   系统权限ID
     * @param operatedSysUserId 操作的系统用户ID
     */
    void disableSysPermission(String sysPermissionId, String operatedSysUserId);

}
