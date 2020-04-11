package com.chen.f.admin.configuration.security.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.chen.f.common.mapper.SysOrganizationMapper;
import com.chen.f.common.mapper.SysOrganizationRoleMapper;
import com.chen.f.common.mapper.SysOrganizationUserMapper;
import com.chen.f.common.mapper.SysPermissionMapper;
import com.chen.f.common.mapper.SysRoleMapper;
import com.chen.f.common.mapper.SysRolePermissionMapper;
import com.chen.f.common.mapper.SysUserMapper;
import com.chen.f.common.mapper.SysUserRoleMapper;
import com.chen.f.common.pojo.SysOrganization;
import com.chen.f.common.pojo.SysOrganizationRole;
import com.chen.f.common.pojo.SysOrganizationUser;
import com.chen.f.common.pojo.SysPermission;
import com.chen.f.common.pojo.SysRole;
import com.chen.f.common.pojo.SysRolePermission;
import com.chen.f.common.pojo.SysUser;
import com.chen.f.common.pojo.SysUserRole;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author chen
 * @since 2018/1/15 13:17.
 */
public class DefaultUserDetailsService implements UserDetailsService, ReactiveUserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(DefaultUserDetailsService.class);

    private final String rolePrefix;

    private final SysOrganizationMapper sysOrganizationMapper;
    private final SysOrganizationUserMapper sysOrganizationUserMapper;
    private final SysOrganizationRoleMapper sysOrganizationRoleMapper;

    private final SysUserMapper sysUserMapper;
    private final SysUserRoleMapper sysUserRoleMapper;
    private final SysRoleMapper sysRoleMapper;
    private final SysRolePermissionMapper sysRolePermissionMapper;
    private final SysPermissionMapper sysPermissionMapper;

    public DefaultUserDetailsService(String rolePrefix,
                                     SysOrganizationMapper sysOrganizationMapper,
                                     SysOrganizationUserMapper sysOrganizationUserMapper,
                                     SysOrganizationRoleMapper sysOrganizationRoleMapper,
                                     SysUserMapper sysUserMapper,
                                     SysUserRoleMapper sysUserRoleMapper,
                                     SysRoleMapper sysRoleMapper,
                                     SysRolePermissionMapper sysRolePermissionMapper,
                                     SysPermissionMapper sysPermissionMapper) {
        this.rolePrefix = rolePrefix;

        this.sysOrganizationMapper = sysOrganizationMapper;
        this.sysOrganizationUserMapper = sysOrganizationUserMapper;
        this.sysOrganizationRoleMapper = sysOrganizationRoleMapper;

        this.sysUserMapper = sysUserMapper;
        this.sysUserRoleMapper = sysUserRoleMapper;
        this.sysRoleMapper = sysRoleMapper;
        this.sysRolePermissionMapper = sysRolePermissionMapper;
        this.sysPermissionMapper = sysPermissionMapper;
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
        final SysUser sysUser = sysUserMapper.selectOne(Wrappers.<SysUser>lambdaQuery()
                .eq(SysUser::getUsername, username).eq(SysUser::getStatus, SysUserStatusEnum.ENABLED));
        if (sysUser == null) {
            logger.debug("没有找到该用户:[{}]", username);
            throw new UsernameNotFoundException("没有找到该用户[ " + username + " ]");
        }
        
        //更新系统用户最后登录日期时间
        sysUser.setLastLoginDateTime(LocalDateTime.now());
        sysUserMapper.updateById(sysUser);


        //系统用户的组织列表
        final List<SysOrganization> sysUserOrganizationList = new ArrayList<>();
        //系统用户的角色列表
        final List<SysRole> sysUserRoleList = new ArrayList<>();
        //系统用户的权限列表
        final List<SysPermission> sysUserPermissionList = new ArrayList<>();

        //系统用户的角色ID列表
        final List<String> sysUserRoleIdList = new ArrayList<>();
        //系统用户的权限ID列表
        final List<String> sysUserPermissionIdList = new ArrayList<>();


        //查询系统用户的组织
        final List<SysOrganizationUser> sysOrganizationUserList = sysOrganizationUserMapper.selectList(Wrappers.<SysOrganizationUser>lambdaQuery()
                .eq(SysOrganizationUser::getSysUserId, sysUser.getId()));
        if (CollectionUtils.isNotEmpty(sysOrganizationUserList)) {
            final List<String> sysOrganizationIdList = sysOrganizationUserList.stream()
                    .map(SysOrganizationUser::getSysOrganizationId)
                    .collect(Collectors.toList());
            final List<SysOrganization> sysOrganizationList = sysOrganizationMapper.selectSuperiorByIdList(sysOrganizationIdList,
                    Wrappers.<SysOrganization>lambdaQuery().eq(SysOrganization::getStatus, StatusEnum.ENABLED));
            if (CollectionUtils.isNotEmpty(sysOrganizationList)) {
                //添加系统用户的组织列表
                sysUserOrganizationList.addAll(sysOrganizationList);
            }
        }


        if (sysUser.getLevel().equals(0)) {
            logger.debug("[{}]是超级用户,设置所有角色和权限", sysUser.getUsername());
            List<SysRole> sysRoleList = sysRoleMapper.selectList(Wrappers.<SysRole>lambdaQuery().eq(SysRole::getStatus, StatusEnum.ENABLED));
            List<SysPermission> sysPermissionList = sysPermissionMapper.selectList(Wrappers.<SysPermission>lambdaQuery().eq(SysPermission::getStatus, StatusEnum.ENABLED));

            if (CollectionUtils.isNotEmpty(sysRoleList)) {
                sysUserRoleList.addAll(sysRoleList);
            }
            if (CollectionUtils.isNotEmpty(sysPermissionList)) {
                sysUserPermissionList.addAll(sysPermissionList);
            }
        } else {
            //查询系统组织的角色
            if (CollectionUtils.isNotEmpty(sysUserOrganizationList)) {
                final List<SysOrganizationRole> sysOrganizationRoleList = sysOrganizationRoleMapper.selectList(Wrappers.<SysOrganizationRole>lambdaQuery()
                        .in(SysOrganizationRole::getSysOrganizationId, sysUserOrganizationList.stream().map(SysOrganization::getId).collect(Collectors.toList())));
                if (CollectionUtils.isNotEmpty(sysOrganizationRoleList)) {
                    //添加系统用户的角色ID列表
                    final List<String> sysOrganizationRoleIdList = sysOrganizationRoleList.stream()
                            .map(SysOrganizationRole::getSysRoleId)
                            .collect(Collectors.toList());
                    sysUserRoleIdList.addAll(sysOrganizationRoleIdList);
                }
            }

            //查询系统用户的角色
            final List<SysUserRole> sysUserRoleList1 = sysUserRoleMapper.selectList(Wrappers.<SysUserRole>lambdaQuery()
                    .eq(SysUserRole::getSysUserId, sysUser.getId()));
            if (CollectionUtils.isNotEmpty(sysUserRoleList1)) {
                final List<SysRole> sysRoleList = sysRoleMapper.selectList(Wrappers.<SysRole>lambdaQuery()
                        .in(SysRole::getId, sysUserRoleList1.stream().map(SysUserRole::getSysRoleId).collect(Collectors.toList())));
                if (CollectionUtils.isNotEmpty(sysRoleList)) {
                    //添加系统用户的角色列表
                    sysUserRoleList.addAll(sysRoleList);
                    //添加系统用户的角色ID列表
                    final List<String> sysRoleIdList = sysRoleList.stream().map(SysRole::getId).collect(Collectors.toList());
                    sysUserRoleIdList.addAll(sysRoleIdList);


                    //查询系统角色的权限
                    final List<SysRolePermission> sysRolePermissionList = sysRolePermissionMapper.selectList(Wrappers.<SysRolePermission>lambdaQuery()
                            .in(SysRolePermission::getSysRoleId, sysUserRoleIdList));
                    if (CollectionUtils.isNotEmpty(sysRolePermissionList)) {
                        //添加系统用户的权限ID列表
                        final List<String> sysRolePermissionIdList = sysRolePermissionList.stream()
                                .map(SysRolePermission::getSysPermissionId)
                                .collect(Collectors.toList());
                        sysUserPermissionIdList.addAll(sysRolePermissionIdList);

                        final List<SysPermission> sysPermissionList = sysPermissionMapper.selectList(Wrappers.<SysPermission>lambdaQuery()
                                .in(SysPermission::getId, sysUserPermissionIdList));
                        if (CollectionUtils.isNotEmpty(sysPermissionList)) {
                            //添加系统用户的权限列表
                            sysUserPermissionList.addAll(sysPermissionList);
                        }
                    }
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
        SecurityUser securityUser = new SecurityUser(sysUser.getUsername(), sysUser.getPassword(),
                sysUser.getStatus() == SysUserStatusEnum.ENABLED,
                sysUser.getStatus() != SysUserStatusEnum.EXPIRED,
                true, sysUser.getStatus() != SysUserStatusEnum.LOCKED,
                simpleGrantedAuthorityList);
        securityUser.setSysUser(sysUser);
        securityUser.setSysUserOrganizationList(sysUserOrganizationList);
        securityUser.setSysUserRoleList(sysUserRoleList);
        securityUser.setSysUserPermissionList(sysUserPermissionList);
        return securityUser;
    }


    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return Mono.just(loadUserByUsername(username));
    }

}
