package com.chen.f.spring.boot.configuration.springsecurity.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.chen.f.admin.security.SecurityUser;
import com.chen.f.common.mapper.SysPermissionMapper;
import com.chen.f.common.mapper.SysRoleMapper;
import com.chen.f.common.mapper.SysUserMapper;
import com.chen.f.common.mapper.SysUserRolePermissionMapper;
import com.chen.f.common.pojo.SysPermission;
import com.chen.f.common.pojo.SysRole;
import com.chen.f.common.pojo.SysUserRolePermission;
import com.chen.f.common.pojo.enums.StatusEnum;
import com.chen.f.common.pojo.enums.SysUserStatusEnum;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author chen
 * @since 2018/1/15 13:17.
 */
public class DefaultUserDetailsService implements UserDetailsService, ReactiveUserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(DefaultUserDetailsService.class);

    private final SysUserRolePermissionMapper sysUserRolePermissionMapper;
    private final SysUserMapper sysUserMapper;
    private final SysRoleMapper sysRoleMapper;
    private final SysPermissionMapper sysPermissionMapper;

    private final String rolePrefix;


    public DefaultUserDetailsService(SysUserRolePermissionMapper sysUserRolePermissionMapper,
                                     SysUserMapper sysUserMapper, SysRoleMapper sysRoleMapper, SysPermissionMapper sysPermissionMapper,
                                     String rolePrefix) {
        this.sysUserRolePermissionMapper = sysUserRolePermissionMapper;
        this.sysUserMapper = sysUserMapper;
        this.sysRoleMapper = sysRoleMapper;
        this.sysPermissionMapper = sysPermissionMapper;
        this.rolePrefix = rolePrefix;
    }

    /**
     * 根据用户名定位用户。在实际的实现中，搜索可能是大小写敏感的
     *
     * @param username 用户名标识需要数据的用户
     * @return 一个完全填充的用户记录（永远不会是null
     * @throws UsernameNotFoundException 如果用户不能被找到或者用户没有GrantedAuthority
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUserRolePermission sysUserRolePermission = sysUserRolePermissionMapper.selectSysUserRolePermissionByUsername(username);

        if (sysUserRolePermission == null) {
            logger.debug("没有找到该用户:[{}]", username);
            throw new UsernameNotFoundException("没有找到该用户");
        }

        //权限集合
        List<SimpleGrantedAuthority> authorityList = Collections.emptyList();
        //获取角色
        List<SysUserRolePermission.SysRolePermission> rolePermissionList = sysUserRolePermission.getSysRolePermissionList();
        if (CollectionUtils.isNotEmpty(rolePermissionList)) {
            //获取角色集合（加前缀
            List<SimpleGrantedAuthority> sysRoleList = rolePermissionList.stream()
                    .filter(Objects::nonNull)
                    .map((sysRolePermission) -> new SimpleGrantedAuthority(rolePrefix + sysRolePermission.getName()))
                    .collect(Collectors.toList());

            //获取权限集合
            List<SimpleGrantedAuthority> sysPerminssionList = rolePermissionList.stream()
                    .filter(Objects::nonNull)
                    .filter((sysRolePermission) -> CollectionUtils.isNotEmpty(sysRolePermission.getSysPermissionList()))
                    .flatMap(sysRolePermission -> sysRolePermission.getSysPermissionList().stream())
                    .map((sysPermission) -> new SimpleGrantedAuthority(sysPermission.getName()))
                    .collect(Collectors.toList());

            authorityList = new ArrayList<>();
            authorityList.addAll(sysRoleList);
            authorityList.addAll(sysPerminssionList);
        }

        if (sysUserRolePermission.getLevel().equals(0)) {
            logger.debug("[{}]是超级用户,为它设置所有角色和权限", sysUserRolePermission.getId());
            List<SysRole> sysRoleList = sysRoleMapper.selectList(Wrappers.<SysRole>lambdaQuery().eq(SysRole::getStatus, StatusEnum.ENABLED));
            List<SysPermission> sysPermissionList = sysPermissionMapper.selectList(Wrappers.<SysPermission>lambdaQuery().eq(SysPermission::getStatus, StatusEnum.ENABLED));
            authorityList = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(sysRoleList)) {
                List<SimpleGrantedAuthority> sysRoleStringList = sysRoleList.stream()
                        .filter(Objects::nonNull)
                        .map(sysRole -> new SimpleGrantedAuthority(rolePrefix + sysRole.getName()))
                        .collect(Collectors.toList());
                authorityList.addAll(sysRoleStringList);
            }
            if (CollectionUtils.isNotEmpty(sysPermissionList)) {
                List<SimpleGrantedAuthority> sysPermissionStringList = sysPermissionList.stream()
                        .filter(Objects::nonNull)
                        .map(sysPermission -> new SimpleGrantedAuthority(sysPermission.getName()))
                        .collect(Collectors.toList());
                authorityList.addAll(sysPermissionStringList);
            }
        }

        SecurityUser securityUser = new SecurityUser(sysUserRolePermission.getUsername(), sysUserRolePermission.getPassword(),
                sysUserRolePermission.getStatus() == SysUserStatusEnum.ENABLED,
                sysUserRolePermission.getStatus() != SysUserStatusEnum.EXPIRED,
                true, sysUserRolePermission.getStatus() != SysUserStatusEnum.LOCKED,
                authorityList);
        securityUser.setOriginal(sysUserRolePermission);
        return securityUser;
    }


    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return Mono.just(loadUserByUsername(username));
    }

}
