package com.chen.f.common.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chen.f.common.pojo.SysApi;
import com.chen.f.common.pojo.SysMenu;
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
     * 获取启用的系统权限列表
     *
     * @return 启用的系统权限列表
     */
    List<SysPermission> getEnabledSysPermissionList();
    
    /**
     * 获取分页的系统权限列表
     *
     * @param pageIndex  页数
     * @param pageNumber 页大小
     * @param code       系统权限编码
     * @param name       系统权限名称
     * @param type       系统权限类型
     * @param remark     系统权限描述
     * @param status     系统权限状态
     * @return 分页的系统权限列表
     */
    IPage<SysPermission> getSysPermissionPage(Long pageIndex, Long pageNumber, String code, String name, SysPermissionTypeEnum type, String remark, StatusEnum status);

    /**
     * 获取系统权限
     *
     * @param sysPermissionId 系统权限ID
     * @return 系统权限
     */
    SysPermission getSysPermission(String sysPermissionId);
    
    /**
     * 获取系统权限的系统菜单列表
     *
     * @param sysPermissionId 系统权限ID
     * @return 系统菜单列表
     */
    List<SysMenu> getSysMenuOfSysPermission(String sysPermissionId);
    
    /**
     * 获取系统权限的系统菜单列表
     *
     * @param sysPermissionIdList 系统权限ID列表
     * @return 系统菜单列表
     */
    List<SysMenu> getSysMenuOfSysPermission(List<String> sysPermissionIdList);
    
    /**
     * 获取系统权限的系统接口列表
     *
     * @param sysPermissionId 系统权限ID
     * @return 系统接口列表
     */
    List<SysApi> getSysApiOfSysPermission(String sysPermissionId);

    /**
     * 获取系统权限的系统接口列表
     *
     * @param sysPermissionIdList 系统权限ID列表
     * @return 系统接口列表
     */
    List<SysApi> getSysApiOfSysPermission(List<String> sysPermissionIdList);

    /**
     * 创建系统权限
     *
     * @param code              系统权限编码
     * @param name              系统权限名称
     * @param type              系统权限类型
     * @param remark            系统权限备注
     * @param status            系统权限状态
     * @param operatedSysUserId 操作的系统用户ID
     */
    void createSysPermission(String code, String name, SysPermissionTypeEnum type, String remark, StatusEnum status, String operatedSysUserId);

    /**
     * 修改系统权限
     *
     * @param sysPermissionId   系统权限ID
     * @param code              系统权限编码
     * @param name              系统权限名称
     * @param type              系统权限类型
     * @param remark            系统权限备注
     * @param status            系统权限状态
     * @param operatedSysUserId 操作的系统用户ID
     */
    void updateSysPermission(String sysPermissionId, String code, String name, SysPermissionTypeEnum type, String remark, StatusEnum status, String operatedSysUserId);


    /**
     * 设置系统权限的系统接口
     *
     * @param sysPermissionId   系统权限ID
     * @param sysApiIdList      系统接口ID列表
     * @param operatedSysUserId 操作的系统用户ID
     */
    void setSysApiOfSysPermission(String sysPermissionId, List<String> sysApiIdList, String operatedSysUserId);

    /**
     * 设置系统权限的系统菜单
     *
     * @param sysPermissionId   系统权限ID
     * @param sysMenuIdList     系统菜单ID列表
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
