package com.chen.f.admin.configuration.security.service;

import com.chen.f.common.pojo.SysOrganization;
import com.chen.f.common.pojo.SysPermission;
import com.chen.f.common.pojo.SysRole;
import com.chen.f.common.pojo.SysUser;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * spring-security对象
 *
 * @author chen
 * @since 2018/12/24 19:12.
 */
@Getter
@Setter
@ToString
public class SecurityUser extends User {

    /**
     * 系统用户
     */
    private SysUser sysUser;

    /**
     * 系统用户组织列表
     */
    private List<SysOrganization> sysUserOrganizationList = Collections.emptyList();

    /**
     * 系统用户角色列表
     */
    private List<SysRole> sysUserRoleList = Collections.emptyList();

    /**
     * 系统用户权限列表
     */
    private List<SysPermission> sysUserPermissionList = Collections.emptyList();


    public SecurityUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public SecurityUser(String username, String password,
                        boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked,
                        Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }
}
