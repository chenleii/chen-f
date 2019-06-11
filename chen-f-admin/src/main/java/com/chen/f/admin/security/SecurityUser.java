package com.chen.f.admin.security;

import com.chen.f.core.pojo.SysUserRolePermission;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * @author chen
 * @since 2018/12/24 19:12.
 */
public class SecurityUser extends User {

    private SysUserRolePermission original;

    public SysUserRolePermission getOriginal() {
        return original;
    }

    public void setOriginal(SysUserRolePermission original) {
        this.original = original;
    }

    public SecurityUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public SecurityUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }
}
