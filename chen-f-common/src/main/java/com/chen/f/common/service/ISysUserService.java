package com.chen.f.common.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chen.f.common.pojo.SysOrganization;
import com.chen.f.common.pojo.SysRole;
import com.chen.f.common.pojo.SysUser;
import com.chen.f.common.pojo.enums.SysUserStatusEnum;

import java.util.List;

/**
 * <p>
 * 系统用户表 服务类
 * </p>
 *
 * @author chen
 * @since 2018-12-02
 */
public interface ISysUserService extends IService<SysUser> {

    /**
     * 获取所有系统用户
     *
     * @return 所有系统用户
     */
    List<SysUser> getAllSysUserList();

    /**
     * 获取启用的系统用户列表
     *
     * @return 启用的系统用户列表
     */
    List<SysUser> getEnabledSysUserList();
    
    /**
     * 获取分页的系统用户列表
     *
     * @param pageIndex         页数
     * @param pageNumber        页大小
     * @param username          系统用户名称
     * @param level             系统用户级别
     * @param remark            系统用户描述
     * @param sysUserStatusEnum 系统用户状态
     * @return 分页的系统用户列表
     */
    IPage<SysUser> getSysUserPage(Long pageIndex, Long pageNumber, String username, Integer level, String remark, SysUserStatusEnum sysUserStatusEnum);

    /**
     * 获取系统用户
     *
     * @param sysUserId 系统用户ID
     * @return 系统用户
     */
    SysUser getSysUser(String sysUserId);
    
    /**
     * 获取系统用户的系统组织列表
     *
     * @param sysUserId 系统用户ID
     * @return 系统用户的系统组织列表
     */
    List<SysOrganization> getSysOrganizationOfSysUser(String sysUserId);
    
    /**
     * 获取系统用户的系统角色列表
     *
     * @param sysUserId 系统用户ID
     * @return 系统用户的系统角色列表
     */
    List<SysRole> getSysRoleOfSysUser(String sysUserId);

    /**
     * 创建系统用户
     *
     * @param username          系统用户用户名称
     * @param password          系统用户密码
     * @param level             系统用户等级
     * @param remark            系统用户备注
     * @param status            系统用户状态
     * @param operatedSysUserId 操作的系统用户ID
     */
    void createSysUser(String username, String password, Integer level, String remark, SysUserStatusEnum status, String operatedSysUserId);

    /**
     * 修改系统用户
     *
     * @param sysUserId         系统用户ID
     * @param username          系统用户用户名称
     * @param password          系统用户密码
     * @param level             系统用户等级
     * @param remark            系统用户备注
     * @param status            系统用户状态
     * @param operatedSysUserId 操作的系统用户ID
     */
    void updateSysUser(String sysUserId, String username, String password, Integer level, String remark, SysUserStatusEnum status, String operatedSysUserId);


    /**
     * 设置系统用户的系统角色
     *
     * @param sysUserId         设置的系统用户ID
     * @param sysRoleIdList     系统角色ID列表
     * @param operatedSysUserId 操作的系统用户
     */
    void setSysRoleOfSysUser(String sysUserId, List<String> sysRoleIdList, String operatedSysUserId);
    
    /**
     * 删除系统用户
     *
     * @param sysUserId         系统用户ID
     * @param operatedSysUserId 操作的系统用户ID
     */
    void deleteSysUser(String sysUserId, String operatedSysUserId);

    /**
     * 启用系统用户
     *
     * @param sysUserId         系统用户ID
     * @param operatedSysUserId 操作的系统用户ID
     */
    void enabledSysUser(String sysUserId, String operatedSysUserId);

    /**
     * 禁用系统用户
     *
     * @param sysUserId         系统用户ID
     * @param operatedSysUserId 操作的系统用户ID
     */
    void disableSysUser(String sysUserId, String operatedSysUserId);

    /**
     * 锁定系统用户
     *
     * @param sysUserId         系统用户ID
     * @param operatedSysUserId 操作的系统用户ID
     */
    void lockSysUser(String sysUserId, String operatedSysUserId);

    /**
     * 过期系统用户
     *
     * @param sysUserId         系统用户ID
     * @param operatedSysUserId 操作的系统用户ID
     */
    void expireSysUser(String sysUserId, String operatedSysUserId);
    
}
