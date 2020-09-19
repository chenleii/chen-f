package com.chen.f.admin.test;

import com.chen.f.admin.configuration.security.service.LoginUser;
import com.chen.f.common.mapper.SysUserMapper;
import com.chen.f.common.pojo.SysUser;
import com.chen.f.common.pojo.enums.SysUserStatusEnum;
import com.chen.f.core.configuration.security.token.LoginAuthenticationToken;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author chen
 * @since 2020/9/19 20:34.
 */
public class ChenFWithMockUserSecurityContextFactory implements
        WithSecurityContextFactory<ChenFWithMockUser> {
    
    private BeanFactory beans;

    @Autowired
    ChenFWithMockUserSecurityContextFactory(BeanFactory beans) {
        this.beans = beans;
    }
    
    
    public SecurityContext createSecurityContext(ChenFWithMockUser withUser) {
        String username = StringUtils.hasLength(withUser.username()) ? withUser
                .username() : withUser.value();
        if (username == null) {
            throw new IllegalArgumentException(withUser
                    + " cannot have null username on both username and value properties");
        }
        

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (String authority : withUser.authorities()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(authority));
        }

        if (grantedAuthorities.isEmpty()) {
            for (String role : withUser.roles()) {
                if (role.startsWith("ROLE_")) {
                    throw new IllegalArgumentException("roles cannot start with ROLE_ Got "
                            + role);
                }
                grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role));
            }
        } else if (!(withUser.roles().length == 1 && "USER".equals(withUser.roles()[0]))) {
            throw new IllegalStateException("You cannot define roles attribute "+ Arrays.asList(withUser.roles())+" with authorities attribute "+ Arrays.asList(withUser.authorities()));
        }

        final LoginUser loginUser = new LoginUser(
                username, withUser.password(), grantedAuthorities);
        final SysUser sysUser1 = new SysUser();
        sysUser1.setId(null);
        sysUser1.setUsername(username);
        sysUser1.setPassword(withUser.password());
        sysUser1.setLevel(0);
        sysUser1.setLastLoginDateTime(LocalDateTime.now());
        sysUser1.setRemark("test");
        sysUser1.setStatus(SysUserStatusEnum.ENABLED);
        sysUser1.setUpdatedSysUserId("");
        sysUser1.setCreatedSysUserId("");
        sysUser1.setUpdatedDateTime(LocalDateTime.now());
        sysUser1.setCreatedDateTime(LocalDateTime.now());
        loginUser.setSysUser(sysUser1);
        beans.getBean(SysUserMapper.class).insert(sysUser1);

        Authentication authentication = new LoginAuthenticationToken(
                loginUser, loginUser.getPassword(), loginUser.getAuthorities());
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        return context;
    }
}
