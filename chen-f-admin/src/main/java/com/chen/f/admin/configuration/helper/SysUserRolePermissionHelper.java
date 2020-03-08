package com.chen.f.admin.configuration.helper;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.chen.f.common.mapper.*;
import com.chen.f.common.pojo.SysUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 用户、角色、权限的所有操作(CRUD、修改密码等）
 *
 * @author chen
 * @date 2018/10/28 0:27.
 */
public class SysUserRolePermissionHelper {
    protected static final Logger logger = LoggerFactory.getLogger(SysUserRolePermissionHelper.class);

    private final PasswordEncoder passwordEncoder;

    private final SysUserMapper sysUserMapper;
    private final SysRoleMapper sysRoleMapper;
    private final SysPermissionMapper sysPermissionMapper;
    private final SysUserRoleMapper sysUserRoleMapper;
    private final SysRolePermissionMapper sysRolePermissionMapper;
    private final SysUserRolePermissionMapper sysUserRolePermissionMapper;


    public SysUserRolePermissionHelper(PasswordEncoder passwordEncoder,
                                       SysUserMapper sysUserMapper, SysRoleMapper sysRoleMapper, SysPermissionMapper sysPermissionMapper,
                                       SysUserRoleMapper sysUserRoleMapper, SysRolePermissionMapper sysRolePermissionMapper, SysUserRolePermissionMapper sysUserRolePermissionMapper) {
        this.passwordEncoder = passwordEncoder;
        this.sysUserMapper = sysUserMapper;
        this.sysRoleMapper = sysRoleMapper;
        this.sysPermissionMapper = sysPermissionMapper;
        this.sysUserRoleMapper = sysUserRoleMapper;
        this.sysRolePermissionMapper = sysRolePermissionMapper;
        this.sysUserRolePermissionMapper = sysUserRolePermissionMapper;
    }

    /**
     * Create a new user with the supplied details.
     */
    void createUser(SysUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        sysUserMapper.insert(user);
    }

    /**
     * Update the specified user.
     */
    void updateUser(SysUser user) {
        sysUserMapper.updateById(user);
    }

    /**
     * Remove the user with the given login name from the system.
     */
    void deleteUser(String username) {
        sysUserMapper.delete(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUsername, username));
    }

    /**
     * Modify the current user's password. This should change the user's password in the
     * persistent user repository (datbase, LDAP etc).
     *
     * @param oldPassword current password (for re-authentication if required)
     * @param newPassword the password to change to
     */
    void changePassword(String oldPassword, String newPassword) {

    }

    /**
     * Check if a user with the supplied login name exists in the system.
     */
    boolean userExists(String username) {
        return sysUserMapper.selectCount(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUsername, username)) == 1;

    }

}
