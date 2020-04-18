package com.chen.f.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chen.f.common.pojo.SysOrganization;
import com.chen.f.common.pojo.SysRole;
import com.chen.f.common.pojo.SysUser;
import com.chen.f.common.pojo.enums.StatusEnum;
import com.chen.f.common.pojo.enums.SysOrganizationTypeEnum;

import java.util.List;

/**
 * <p>
 * 系统组织表 服务类
 * </p>
 *
 * @author chen
 * @since 2020-03-25
 */
public interface ISysOrganizationService extends IService<SysOrganization> {

    /**
     * 获取所有系统组织
     *
     * @return 所有系统组织
     */
    List<SysOrganization> getAllSysOrganizationList();

    /**
     * 获取启用的系统组织列表
     *
     * @return 启用的系统组织列表
     */
    List<SysOrganization> getEnabledSysOrganizationList();

    /**
     * 获取系统组织列表
     *
     * @param parentId 父级ID
     * @param name     系统组织名称
     * @param fullName 系统组织全称
     * @param type     系统组织类型
     * @param remark   系统组织备注
     * @param status   系统组织状态
     * @return 系统组织列表
     */
    List<SysOrganization> getSysOrganizationList(String parentId, String name, String fullName,
                                                 SysOrganizationTypeEnum type, String remark, StatusEnum status);

    /**
     * 获取系统组织
     *
     * @param sysOrganizationId 系统组织ID
     * @return 系统组织
     */
    SysOrganization getSysOrganization(String sysOrganizationId);

    /**
     * 获取系统组织的系统用户
     *
     * @param sysOrganizationId 系统组织ID
     * @return 系统用户列表
     */
    List<SysUser> getSysUserOfSysOrganization(String sysOrganizationId);

    /**
     * 获取系统组织的系统角色
     *
     * @param sysOrganizationId 系统组织ID
     * @return 系统角色列表
     */
    List<SysRole> getSysRoleOfSysOrganization(String sysOrganizationId);

    /**
     * 创建系统组织
     *
     * @param parentId          系统组织父级ID
     * @param name              系统组织名称
     * @param fullName          系统组织URL
     * @param type              系统组织类型
     * @param remark            系统组织描述
     * @param status            系统组织状态
     * @param operatedSysUserId 操作用户ID
     */
    void createSysOrganization(String parentId, String name, String fullName, SysOrganizationTypeEnum type,
                               String remark, StatusEnum status, String operatedSysUserId);

    /**
     * 修改系统组织
     *
     * @param sysOrganizationId 系统组织ID
     * @param parentId          系统组织父级ID
     * @param name              系统组织名称
     * @param fullName          系统组织URL
     * @param type              系统组织类型
     * @param remark            系统组织描述
     * @param status            系统组织状态
     * @param operatedSysUserId 操作用户ID
     */
    void updateSysOrganization(String sysOrganizationId, String parentId, String name, String fullName, SysOrganizationTypeEnum type,
                               String remark, StatusEnum status, String operatedSysUserId);

    /**
     * 设置系统组织的系统用户
     *
     * @param sysOrganizationId 设置的系统组织ID
     * @param sysUserIdList     系统用户ID集合
     * @param operatedSysUserId 操作的系统用户
     */
    void setSysUserOfSysOrganization(String sysOrganizationId, List<String> sysUserIdList, String operatedSysUserId);

    /**
     * 设置系统组织的系统角色
     *
     * @param sysOrganizationId 设置的系统组织ID
     * @param sysRoleIdList     系统角色ID集合
     * @param operatedSysUserId 操作的系统用户
     */
    void setSysRoleOfSysOrganization(String sysOrganizationId, List<String> sysRoleIdList, String operatedSysUserId);

    /**
     * 删除系统组织
     *
     * @param sysOrganizationId 系统组织ID
     */
    void deleteSysOrganization(String sysOrganizationId);


    /**
     * 启用系统组织
     *
     * @param sysOrganizatioId  系统组织ID
     * @param operatedSysUserId 操作的系统用户ID
     */
    void enabledSysOrganization(String sysOrganizatioId, String operatedSysUserId);

    /**
     * 禁用系统组织
     *
     * @param sysOrganizatioId  系统组织ID
     * @param operatedSysUserId 操作的系统用户ID
     */
    void disableSysOrganization(String sysOrganizatioId, String operatedSysUserId);

    /**
     * 根据系统用户ID获取启用的系统组织列表
     *
     * @param sysUserId 系统用户ID
     * @return 系统组织列表
     */
    List<SysOrganization> getEnabledSysOrganizationListBySysUserId(String sysUserId);
}
