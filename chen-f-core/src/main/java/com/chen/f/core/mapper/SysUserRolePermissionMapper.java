package com.chen.f.core.mapper;

import com.chen.f.core.pojo.SysUserRolePermission;
import org.apache.ibatis.annotations.Param;

/**
 * @author chen
 * @date 2018/10/25 20:40.
 */
public interface SysUserRolePermissionMapper {

    /**
     * 根据用户名查找用户角色权限信息
     *
     * @param username 用户名
     * @return 用户角色权限信息
     */
    SysUserRolePermission selectSysUserRolePermissionByUsername(@Param("username") String username);
}
