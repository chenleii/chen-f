package com.chen.f.admin.configuration.security.service;

import com.chen.f.common.pojo.SysApi;
import com.chen.f.common.pojo.SysMenu;
import com.chen.f.common.pojo.SysOrganization;
import com.chen.f.common.pojo.SysPermission;
import com.chen.f.common.pojo.SysRole;
import com.chen.f.common.pojo.SysUser;
import com.chen.f.common.pojo.enums.SysUserStatusEnum;
import com.chen.f.common.service.ISysApiService;
import com.chen.f.common.service.ISysMenuService;
import com.chen.f.common.service.ISysOrganizationService;
import com.chen.f.common.service.ISysPermissionService;
import com.chen.f.common.service.ISysRoleService;
import com.chen.f.common.service.ISysUserService;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * @author chen
 * @since 2018/1/15 13:17.
 */
public class DefaultUserDetailsService implements UserDetailsService, ReactiveUserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(DefaultUserDetailsService.class);

    private final String rolePrefix;

    private final ISysOrganizationService sysOrganizationService;
    private final ISysUserService sysUserService;
    private final ISysRoleService sysRoleService;
    private final ISysPermissionService sysPermissionService;

    private final ISysMenuService sysMenuService;
    private final ISysApiService sysApiService;

    public DefaultUserDetailsService(String rolePrefix,
                                     ISysOrganizationService sysOrganizationService,
                                     ISysUserService sysUserService,
                                     ISysRoleService sysRoleService,
                                     ISysPermissionService sysPermissionService,

                                     ISysMenuService sysMenuService,
                                     ISysApiService sysApiService) {
        this.rolePrefix = rolePrefix;

        this.sysOrganizationService = sysOrganizationService;
        this.sysUserService = sysUserService;
        this.sysRoleService = sysRoleService;
        this.sysPermissionService = sysPermissionService;

        this.sysMenuService = sysMenuService;
        this.sysApiService = sysApiService;
    }


    /**
     * 根据用户名定位用户。在实际的实现中，搜索可能是大小写敏感的
     *
     * @param username 用户名编码需要数据的用户
     * @return 一个完全填充的用户记录（永远不会是null
     * @throws UsernameNotFoundException 如果用户不能被找到或者用户没有GrantedAuthority
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final SysUser sysUser = sysUserService.getSysUserByUsername(username);
        if (sysUser == null) {
            logger.debug("没有找到该用户:[{}]", username);
            throw new UsernameNotFoundException("没有找到该用户[ " + username + " ]");
        }

        //系统用户ID
        final String sysUserId = sysUser.getId();

        //更新系统用户最后登录时间
        sysUserService.updateSysUserLastLoginDateTime(sysUserId, LocalDateTime.now());

        //系统用户的组织列表
        final List<SysOrganization> sysUserOrganizationList = new ArrayList<>();
        //系统用户的角色列表
        final List<SysRole> sysUserRoleList = new ArrayList<>();
        //系统用户的权限列表
        final List<SysPermission> sysUserPermissionList = new ArrayList<>();
        //系统用户的菜单列表
        final List<SysMenu> sysUserMenuList = new ArrayList<>();
        //系统用户的接口列表
        final List<SysApi> sysUserApiList = new ArrayList<>();

        {
            //查询系统用户的组织
            final List<SysOrganization> sysOrganizationList = sysUserService.getSysOrganizationOfSysUser(sysUserId);
            if (CollectionUtils.isNotEmpty(sysOrganizationList)) {
                //添加系统用户的组织列表
                sysUserOrganizationList.addAll(sysOrganizationList);
            }
        }

        if (sysUser.getLevel().equals(0)) {
            logger.debug("[{}]是超级用户,设置所有角色和权限", sysUser.getUsername());

            List<SysRole> sysRoleList = sysRoleService.getEnabledSysRoleList();
            if (CollectionUtils.isNotEmpty(sysRoleList)) {
                sysUserRoleList.addAll(sysRoleList);
            }

            List<SysPermission> sysPermissionList = sysPermissionService.getEnabledSysPermissionList();
            if (CollectionUtils.isNotEmpty(sysPermissionList)) {
                sysUserPermissionList.addAll(sysPermissionList);
            }

            final List<SysMenu> allSysMenuList = sysMenuService.getAllSysMenuList();
            if (CollectionUtils.isNotEmpty(allSysMenuList)) {
                sysUserMenuList.addAll(
                        allSysMenuList.stream()
                                .sorted(Comparator.comparing(SysMenu::getOrder))
                                .collect(Collectors.toList())
                );
            }

            final List<SysApi> allSysApiList = sysApiService.getAllSysApiList();
            if (CollectionUtils.isNotEmpty(allSysApiList)) {
                sysUserApiList.addAll(allSysApiList);
            }

        } else {
            {
                final Set<SysRole> sysUserRoleDistinctSet = new TreeSet<>(Comparator.comparing(SysRole::getId));
                //查询系统组织的角色
                if (CollectionUtils.isNotEmpty(sysUserOrganizationList)) {
                    final List<SysRole> sysRoleOfSysOrganization = sysOrganizationService.getSysRoleOfSysOrganization(
                            sysUserOrganizationList.stream().map(SysOrganization::getId).collect(Collectors.toList()));
                    if (CollectionUtils.isNotEmpty(sysRoleOfSysOrganization)) {
                        //添加系统用户的权限列表
                        sysUserRoleDistinctSet.addAll(sysRoleOfSysOrganization);
                    }
                }
                //查询系统用户的角色
                final List<SysRole> sysRoleOfSysUser = sysUserService.getSysRoleOfSysUser(sysUserId);
                if (CollectionUtils.isNotEmpty(sysRoleOfSysUser)) {
                    //添加系统用户的角色列表
                    sysUserRoleDistinctSet.addAll(sysRoleOfSysUser);
                }
                if (CollectionUtils.isNotEmpty(sysUserRoleDistinctSet)) {
                    sysUserRoleList.addAll(sysUserRoleDistinctSet);
                }
            }

            {
                final Set<SysPermission> sysUserPermissionDistinctSet = new TreeSet<>(Comparator.comparing(SysPermission::getId));
                //查询系统角色的权限
                if (CollectionUtils.isNotEmpty(sysUserRoleList)) {
                    final List<SysPermission> sysPermissionOfSysRole = sysRoleService.getSysPermissionOfSysRole(
                            sysUserRoleList.stream().map(SysRole::getId).collect(Collectors.toList()));
                    if (CollectionUtils.isNotEmpty(sysPermissionOfSysRole)) {
                        //添加系统用户的权限列表
                        sysUserPermissionDistinctSet.addAll(sysPermissionOfSysRole);
                    }
                }
                if (CollectionUtils.isNotEmpty(sysUserPermissionDistinctSet)) {
                    sysUserPermissionList.addAll(sysUserPermissionDistinctSet);
                }
            }

            {
                final Set<SysMenu> sysUserMenuDistinctSet = new TreeSet<>(Comparator.comparing(SysMenu::getId));
                //设置系统用户的菜单
                if (CollectionUtils.isNotEmpty(sysUserRoleList)) {
                    final List<SysMenu> sysMenuOfSysRole = sysRoleService.getSysMenuOfSysRole(sysUserRoleList.stream().map(SysRole::getId).collect(Collectors.toList()));
                    if (CollectionUtils.isNotEmpty(sysMenuOfSysRole)) {
                        //添加系统用户的菜单列表
                        sysUserMenuDistinctSet.addAll(sysMenuOfSysRole);
                    }
                }
                if (CollectionUtils.isNotEmpty(sysUserPermissionList)) {
                    final List<SysMenu> sysMenuOfSysPermission = sysPermissionService.getSysMenuOfSysPermission(sysUserPermissionList.stream().map(SysPermission::getId).collect(Collectors.toList()));
                    if (CollectionUtils.isNotEmpty(sysMenuOfSysPermission)) {
                        //添加系统用户的菜单列表
                        sysUserMenuDistinctSet.addAll(sysMenuOfSysPermission);
                    }
                }
                if (CollectionUtils.isNotEmpty(sysUserMenuDistinctSet)) {
                    sysUserMenuList.addAll(
                            sysUserMenuDistinctSet.stream()
                                    .sorted(Comparator.comparing(SysMenu::getOrder))
                                    .collect(Collectors.toList())
                    );
                }
            }


            {
                final Set<SysApi> sysUserApiDistinctSet = new TreeSet<>(Comparator.comparing(SysApi::getId));
                //设置系统用户的接口
                if (CollectionUtils.isNotEmpty(sysUserRoleList)) {
                    final List<SysApi> sysApiOfSysRole = sysRoleService.getSysApiOfSysRole(sysUserRoleList.stream().map(SysRole::getId).collect(Collectors.toList()));
                    if (CollectionUtils.isNotEmpty(sysApiOfSysRole)) {
                        //添加系统用户的接口列表
                        sysUserApiDistinctSet.addAll(sysApiOfSysRole);
                    }
                }
                if (CollectionUtils.isNotEmpty(sysUserPermissionList)) {
                    final List<SysApi> sysApiOfSysPermission = sysPermissionService.getSysApiOfSysPermission(sysUserPermissionList.stream().map(SysPermission::getId).collect(Collectors.toList()));
                    if (CollectionUtils.isNotEmpty(sysApiOfSysPermission)) {
                        //添加系统用户的接口列表
                        sysUserApiDistinctSet.addAll(sysApiOfSysPermission);
                    }
                }
                if (CollectionUtils.isNotEmpty(sysUserApiDistinctSet)) {
                    sysUserApiList.addAll(sysUserApiDistinctSet);
                }
            }
        }


        //spring-security的权限列表
        List<SimpleGrantedAuthority> simpleGrantedAuthorityList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(sysUserRoleList)) {
            //转换角色（加前缀）
            List<SimpleGrantedAuthority> sysRoleList = sysUserRoleList.stream()
                    .filter(Objects::nonNull)
                    .map((sysRolePermission) -> new SimpleGrantedAuthority(rolePrefix + sysRolePermission.getCode()))
                    .collect(Collectors.toList());
            simpleGrantedAuthorityList.addAll(sysRoleList);
        }
        if (CollectionUtils.isNotEmpty(sysUserPermissionList)) {
            //转换权限
            List<SimpleGrantedAuthority> sysPermissionList = sysUserPermissionList.stream()
                    .filter(Objects::nonNull)
                    .map((sysPermission) -> new SimpleGrantedAuthority(sysPermission.getCode()))
                    .collect(Collectors.toList());
            simpleGrantedAuthorityList.addAll(sysPermissionList);
        }

        //创建用户
        LoginUser loginUser = new LoginUser(sysUser.getUsername(), sysUser.getPassword(),
                sysUser.getStatus() == SysUserStatusEnum.ENABLED,
                sysUser.getStatus() != SysUserStatusEnum.EXPIRED,
                true, sysUser.getStatus() != SysUserStatusEnum.LOCKED,
                simpleGrantedAuthorityList);
        loginUser.setSysUser(sysUser);
        loginUser.setSysUserOrganizationList(sysUserOrganizationList);
        loginUser.setSysUserRoleList(sysUserRoleList);
        loginUser.setSysUserPermissionList(sysUserPermissionList);

        loginUser.setSysUserMenuList(sysUserMenuList);
        loginUser.setSysUserApiList(sysUserApiList);
        return loginUser;
    }


    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return Mono.just(loadUserByUsername(username));
    }

}
