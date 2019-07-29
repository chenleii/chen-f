package com.chen.f.core.mapper;

import com.chen.f.core.pojo.SysApiRolePermission;

import java.util.List;

/**
 * @author chen
 * @since 2019/7/25 17:18.
 */
public interface SysApiRolePermissionMapper {
    /**
     * 根据用户名查找用户角色权限信息
     *
     * @return 用户角色权限信息
     */
    List<SysApiRolePermission> selectSysApiRolePermissionList();


}
