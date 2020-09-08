package com.chen.f.common.service.impl;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.chen.f.common.pojo.enums.*;
import com.chen.f.common.pojo.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import com.baomidou.mybatisplus.test.autoconfigure.AutoConfigureMybatisPlus;
import com.chen.f.common.configuration.EnableChenFCommonConfiguration;
import com.chen.f.common.mapper.*;
import com.chen.f.common.service.ISysPermissionService;
import com.chen.f.core.configuration.mybatisplus.EnableChenFMybatisPlusConfiguration;
import com.chen.f.core.page.Page;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author chen
 * @since 2020/9/8 16:13
 */
@SpringBootTest(classes = {SysRoleServiceImpl.class,})
@Transactional
@AutoConfigureMybatisPlus
@AutoConfigureTestDatabase
@ImportAutoConfiguration({
        EnableChenFCommonConfiguration.class,
        EnableChenFMybatisPlusConfiguration.class,
})
//@Sql("classpath:db/schema-h2.sql")
class SysRoleServiceImplTest {
    @Autowired
    private SysRoleServiceImpl sysRoleService;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Autowired
    private SysApiMapper sysApiMapper;
    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    @Autowired
    private SysRoleApiMapper sysRoleApiMapper;
    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;
    @Autowired
    private SysRolePermissionMapper sysRolePermissionMapper;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getEnabledSysRoleList() {
        SysRole sysRole1 = new SysRole();
        sysRole1.setId("1");
        sysRole1.setCode("1");
        sysRole1.setName("1");
        sysRole1.setRemark("");
        sysRole1.setStatus(StatusEnum.ENABLED);
        sysRole1.setUpdatedSysUserId("");
        sysRole1.setCreatedSysUserId("");
        sysRole1.setUpdatedDateTime(LocalDateTime.now());
        sysRole1.setCreatedDateTime(LocalDateTime.now());
        sysRoleMapper.insert(sysRole1);

        List<SysRole> sysRoleList = sysRoleService.getEnabledSysRoleList();

        assertThat(sysRoleList)
                .isNotNull()
                .hasSize(1)
                .usingElementComparator(Comparator.comparing(SysRole::getId))
                .contains(sysRole1);

    }

    @Test
    void getSysRolePage() {
        SysRole sysRole1 = new SysRole();
        sysRole1.setId("1");
        sysRole1.setCode("1");
        sysRole1.setName("1");
        sysRole1.setRemark("");
        sysRole1.setStatus(StatusEnum.ENABLED);
        sysRole1.setUpdatedSysUserId("");
        sysRole1.setCreatedSysUserId("");
        sysRole1.setUpdatedDateTime(LocalDateTime.now());
        sysRole1.setCreatedDateTime(LocalDateTime.now());
        sysRoleMapper.insert(sysRole1);

        Page<SysRole> sysRolePage = sysRoleService.getSysRolePage(new Page<>(), null, null, null, null);

        assertThat(sysRolePage).isNotNull();
        assertThat(sysRolePage.getTotal()).isEqualTo(1);
        assertThat(sysRolePage.getList())
                .isNotNull()
                .hasSize(1)
                .usingElementComparator(Comparator.comparing(SysRole::getId))
                .contains(sysRole1);
    }

    @Test
    void getSysRole() {
        SysRole sysRole1 = new SysRole();
        sysRole1.setId("1");
        sysRole1.setCode("1");
        sysRole1.setName("1");
        sysRole1.setRemark("");
        sysRole1.setStatus(StatusEnum.ENABLED);
        sysRole1.setUpdatedSysUserId("");
        sysRole1.setCreatedSysUserId("");
        sysRole1.setUpdatedDateTime(LocalDateTime.now());
        sysRole1.setCreatedDateTime(LocalDateTime.now());
        sysRoleMapper.insert(sysRole1);

        SysRole sysRole = sysRoleService.getSysRole("1");

        assertThat(sysRole)
                .isNotNull()
                .usingComparator(Comparator.comparing(SysRole::getId))
                .isEqualTo(sysRole1);

    }

    @Test
    void getSysPermissionOfSysRole() {
        SysRole sysRole1 = new SysRole();
        sysRole1.setId("1");
        sysRole1.setCode("1");
        sysRole1.setName("1");
        sysRole1.setRemark("");
        sysRole1.setStatus(StatusEnum.ENABLED);
        sysRole1.setUpdatedSysUserId("");
        sysRole1.setCreatedSysUserId("");
        sysRole1.setUpdatedDateTime(LocalDateTime.now());
        sysRole1.setCreatedDateTime(LocalDateTime.now());
        sysRoleMapper.insert(sysRole1);

        SysPermission sysPermission1 = new SysPermission();
        sysPermission1.setId("1");
        sysPermission1.setCode("1");
        sysPermission1.setName("1");
        sysPermission1.setType(SysPermissionTypeEnum.API);
        sysPermission1.setRemark("");
        sysPermission1.setStatus(StatusEnum.ENABLED);
        sysPermission1.setUpdatedSysUserId("");
        sysPermission1.setCreatedSysUserId("");
        sysPermission1.setUpdatedDateTime(LocalDateTime.now());
        sysPermission1.setCreatedDateTime(LocalDateTime.now());
        sysPermissionMapper.insert(sysPermission1);

        SysRolePermission sysRolePermission1 = new SysRolePermission();
        sysRolePermission1.setId("1");
        sysRolePermission1.setSysRoleId(sysRole1.getId());
        sysRolePermission1.setSysPermissionId(sysPermission1.getId());
        sysRolePermission1.setCreatedSysUserId("1");
        sysRolePermission1.setCreatedDateTime(LocalDateTime.now());
        sysRolePermissionMapper.insert(sysRolePermission1);

        List<SysPermission> sysPermissionOfSysRole = sysRoleService.getSysPermissionOfSysRole("1");

        assertThat(sysPermissionOfSysRole)
                .isNotNull()
                .hasSize(1)
                .usingElementComparator(Comparator.comparing(SysPermission::getId))
                .contains(sysPermission1);

    }

    @Test
    void testGetSysPermissionOfSysRole() {
        SysRole sysRole1 = new SysRole();
        sysRole1.setId("1");
        sysRole1.setCode("1");
        sysRole1.setName("1");
        sysRole1.setRemark("");
        sysRole1.setStatus(StatusEnum.ENABLED);
        sysRole1.setUpdatedSysUserId("");
        sysRole1.setCreatedSysUserId("");
        sysRole1.setUpdatedDateTime(LocalDateTime.now());
        sysRole1.setCreatedDateTime(LocalDateTime.now());
        sysRoleMapper.insert(sysRole1);

        SysPermission sysPermission1 = new SysPermission();
        sysPermission1.setId("1");
        sysPermission1.setCode("1");
        sysPermission1.setName("1");
        sysPermission1.setType(SysPermissionTypeEnum.API);
        sysPermission1.setRemark("");
        sysPermission1.setStatus(StatusEnum.ENABLED);
        sysPermission1.setUpdatedSysUserId("");
        sysPermission1.setCreatedSysUserId("");
        sysPermission1.setUpdatedDateTime(LocalDateTime.now());
        sysPermission1.setCreatedDateTime(LocalDateTime.now());
        sysPermissionMapper.insert(sysPermission1);

        SysRolePermission sysRolePermission1 = new SysRolePermission();
        sysRolePermission1.setId("1");
        sysRolePermission1.setSysRoleId(sysRole1.getId());
        sysRolePermission1.setSysPermissionId(sysPermission1.getId());
        sysRolePermission1.setCreatedSysUserId("1");
        sysRolePermission1.setCreatedDateTime(LocalDateTime.now());
        sysRolePermissionMapper.insert(sysRolePermission1);

        List<SysPermission> sysPermissionOfSysRole = sysRoleService.getSysPermissionOfSysRole(Arrays.asList("1"));

        assertThat(sysPermissionOfSysRole)
                .isNotNull()
                .hasSize(1)
                .usingElementComparator(Comparator.comparing(SysPermission::getId))
                .contains(sysPermission1);
    }

    @Test
    void getSysMenuOfSysRole() {
        SysRole sysRole1 = new SysRole();
        sysRole1.setId("1");
        sysRole1.setCode("1");
        sysRole1.setName("1");
        sysRole1.setRemark("");
        sysRole1.setStatus(StatusEnum.ENABLED);
        sysRole1.setUpdatedSysUserId("");
        sysRole1.setCreatedSysUserId("");
        sysRole1.setUpdatedDateTime(LocalDateTime.now());
        sysRole1.setCreatedDateTime(LocalDateTime.now());
        sysRoleMapper.insert(sysRole1);

        SysMenu sysMenu1 = new SysMenu();
        sysMenu1.setId("1");
        sysMenu1.setParentId("1");
        sysMenu1.setName("1");
        sysMenu1.setNameI18n("1");
        sysMenu1.setUrl("");
        sysMenu1.setIcon("");
        sysMenu1.setType(SysMenuTypeEnum.GROUP);
        sysMenu1.setOrder(0);
        sysMenu1.setRemark("");
        sysMenu1.setStatus(StatusEnum.ENABLED);
        sysMenu1.setUpdatedSysUserId("");
        sysMenu1.setCreatedSysUserId("");
        sysMenu1.setUpdatedDateTime(LocalDateTime.now());
        sysMenu1.setCreatedDateTime(LocalDateTime.now());
        sysMenuMapper.insert(sysMenu1);

        SysRoleMenu sysRoleMenu1 = new SysRoleMenu();
        sysRoleMenu1.setId("1");
        sysRoleMenu1.setSysRoleId(sysRole1.getId());
        sysRoleMenu1.setSysMenuId(sysMenu1.getId());
        sysRoleMenu1.setCreatedSysUserId("1");
        sysRoleMenu1.setCreatedDateTime(LocalDateTime.now());
        sysRoleMenuMapper.insert(sysRoleMenu1);

        List<SysMenu> sysMenuOfSysRole = sysRoleService.getSysMenuOfSysRole("1");

        assertThat(sysMenuOfSysRole)
                .isNotNull()
                .hasSize(1)
                .usingElementComparator(Comparator.comparing(SysMenu::getId))
                .contains(sysMenu1);
    }

    @Test
    void testGetSysMenuOfSysRole() {
        SysRole sysRole1 = new SysRole();
        sysRole1.setId("1");
        sysRole1.setCode("1");
        sysRole1.setName("1");
        sysRole1.setRemark("");
        sysRole1.setStatus(StatusEnum.ENABLED);
        sysRole1.setUpdatedSysUserId("");
        sysRole1.setCreatedSysUserId("");
        sysRole1.setUpdatedDateTime(LocalDateTime.now());
        sysRole1.setCreatedDateTime(LocalDateTime.now());
        sysRoleMapper.insert(sysRole1);

        SysMenu sysMenu1 = new SysMenu();
        sysMenu1.setId("1");
        sysMenu1.setParentId("1");
        sysMenu1.setName("1");
        sysMenu1.setNameI18n("1");
        sysMenu1.setUrl("");
        sysMenu1.setIcon("");
        sysMenu1.setType(SysMenuTypeEnum.GROUP);
        sysMenu1.setOrder(0);
        sysMenu1.setRemark("");
        sysMenu1.setStatus(StatusEnum.ENABLED);
        sysMenu1.setUpdatedSysUserId("");
        sysMenu1.setCreatedSysUserId("");
        sysMenu1.setUpdatedDateTime(LocalDateTime.now());
        sysMenu1.setCreatedDateTime(LocalDateTime.now());
        sysMenuMapper.insert(sysMenu1);

        SysRoleMenu sysRoleMenu1 = new SysRoleMenu();
        sysRoleMenu1.setId("1");
        sysRoleMenu1.setSysRoleId(sysRole1.getId());
        sysRoleMenu1.setSysMenuId(sysMenu1.getId());
        sysRoleMenu1.setCreatedSysUserId("1");
        sysRoleMenu1.setCreatedDateTime(LocalDateTime.now());
        sysRoleMenuMapper.insert(sysRoleMenu1);

        List<SysMenu> sysMenuOfSysRole = sysRoleService.getSysMenuOfSysRole(Arrays.asList("1"));

        assertThat(sysMenuOfSysRole)
                .isNotNull()
                .hasSize(1)
                .usingElementComparator(Comparator.comparing(SysMenu::getId))
                .contains(sysMenu1);
    }

    @Test
    void getSysApiOfSysRole() {
        SysRole sysRole1 = new SysRole();
        sysRole1.setId("1");
        sysRole1.setCode("1");
        sysRole1.setName("1");
        sysRole1.setRemark("");
        sysRole1.setStatus(StatusEnum.ENABLED);
        sysRole1.setUpdatedSysUserId("");
        sysRole1.setCreatedSysUserId("");
        sysRole1.setUpdatedDateTime(LocalDateTime.now());
        sysRole1.setCreatedDateTime(LocalDateTime.now());
        sysRoleMapper.insert(sysRole1);

        SysApi sysApi1 = new SysApi();
        sysApi1.setId("1");
        sysApi1.setName("1");
        sysApi1.setUrl("/data");
        sysApi1.setHttpMethod(SysApiHttpMethodEnum.GET);
        sysApi1.setType(SysApiTypeEnum.LOGIN);
        sysApi1.setRemark("");
        sysApi1.setStatus(StatusEnum.ENABLED);
        sysApi1.setUpdatedSysUserId("");
        sysApi1.setCreatedSysUserId("");
        sysApi1.setUpdatedDateTime(LocalDateTime.now());
        sysApi1.setCreatedDateTime(LocalDateTime.now());
        sysApiMapper.insert(sysApi1);

        SysRoleApi sysRoleApi1 = new SysRoleApi();
        sysRoleApi1.setId("1");
        sysRoleApi1.setSysRoleId(sysRole1.getId());
        sysRoleApi1.setSysApiId(sysApi1.getId());
        sysRoleApi1.setCreatedSysUserId("1");
        sysRoleApi1.setCreatedDateTime(LocalDateTime.now());
        sysRoleApiMapper.insert(sysRoleApi1);

        List<SysApi> sysApiOfSysRole = sysRoleService.getSysApiOfSysRole("1");

        assertThat(sysApiOfSysRole)
                .isNotNull()
                .hasSize(1)
                .usingElementComparator(Comparator.comparing(SysApi::getId))
                .contains(sysApi1);
    }

    @Test
    void testGetSysApiOfSysRole() {
        SysRole sysRole1 = new SysRole();
        sysRole1.setId("1");
        sysRole1.setCode("1");
        sysRole1.setName("1");
        sysRole1.setRemark("");
        sysRole1.setStatus(StatusEnum.ENABLED);
        sysRole1.setUpdatedSysUserId("");
        sysRole1.setCreatedSysUserId("");
        sysRole1.setUpdatedDateTime(LocalDateTime.now());
        sysRole1.setCreatedDateTime(LocalDateTime.now());
        sysRoleMapper.insert(sysRole1);

        SysApi sysApi1 = new SysApi();
        sysApi1.setId("1");
        sysApi1.setName("1");
        sysApi1.setUrl("/data");
        sysApi1.setHttpMethod(SysApiHttpMethodEnum.GET);
        sysApi1.setType(SysApiTypeEnum.LOGIN);
        sysApi1.setRemark("");
        sysApi1.setStatus(StatusEnum.ENABLED);
        sysApi1.setUpdatedSysUserId("");
        sysApi1.setCreatedSysUserId("");
        sysApi1.setUpdatedDateTime(LocalDateTime.now());
        sysApi1.setCreatedDateTime(LocalDateTime.now());
        sysApiMapper.insert(sysApi1);

        SysRoleApi sysRoleApi1 = new SysRoleApi();
        sysRoleApi1.setId("1");
        sysRoleApi1.setSysRoleId(sysRole1.getId());
        sysRoleApi1.setSysApiId(sysApi1.getId());
        sysRoleApi1.setCreatedSysUserId("1");
        sysRoleApi1.setCreatedDateTime(LocalDateTime.now());
        sysRoleApiMapper.insert(sysRoleApi1);

        List<SysApi> sysApiOfSysRole = sysRoleService.getSysApiOfSysRole(Arrays.asList("1"));

        assertThat(sysApiOfSysRole)
                .isNotNull()
                .hasSize(1)
                .usingElementComparator(Comparator.comparing(SysApi::getId))
                .contains(sysApi1);
    }

    @Test
    void createSysRole() {
        final SysUser sysUser1 = new SysUser();
        sysUser1.setId("1");
        sysUser1.setUsername("chen");
        sysUser1.setPassword("chen");
        sysUser1.setLevel(0);
        sysUser1.setLastLoginDateTime(LocalDateTime.now());
        sysUser1.setRemark("test");
        sysUser1.setStatus(SysUserStatusEnum.ENABLED);
        sysUser1.setUpdatedSysUserId("");
        sysUser1.setCreatedSysUserId("");
        sysUser1.setUpdatedDateTime(LocalDateTime.now());
        sysUser1.setCreatedDateTime(LocalDateTime.now());
        sysUserMapper.insert(sysUser1);

        sysRoleService.createSysRole("1", "1", null, StatusEnum.ENABLED, "1");

        List<SysRole> sysRoleList = sysRoleMapper.selectList(Wrappers.<SysRole>lambdaQuery().eq(SysRole::getCode, "1"));

        assertThat(sysRoleList)
                .isNotNull()
                .hasSize(1);
    }

    @Test
    void setSysPermissionOfSysRole() {
        final SysUser sysUser1 = new SysUser();
        sysUser1.setId("1");
        sysUser1.setUsername("chen");
        sysUser1.setPassword("chen");
        sysUser1.setLevel(0);
        sysUser1.setLastLoginDateTime(LocalDateTime.now());
        sysUser1.setRemark("test");
        sysUser1.setStatus(SysUserStatusEnum.ENABLED);
        sysUser1.setUpdatedSysUserId("");
        sysUser1.setCreatedSysUserId("");
        sysUser1.setUpdatedDateTime(LocalDateTime.now());
        sysUser1.setCreatedDateTime(LocalDateTime.now());
        sysUserMapper.insert(sysUser1);

        SysRole sysRole1 = new SysRole();
        sysRole1.setId("1");
        sysRole1.setCode("1");
        sysRole1.setName("1");
        sysRole1.setRemark("");
        sysRole1.setStatus(StatusEnum.ENABLED);
        sysRole1.setUpdatedSysUserId("");
        sysRole1.setCreatedSysUserId("");
        sysRole1.setUpdatedDateTime(LocalDateTime.now());
        sysRole1.setCreatedDateTime(LocalDateTime.now());
        sysRoleMapper.insert(sysRole1);

        SysPermission sysPermission1 = new SysPermission();
        sysPermission1.setId("1");
        sysPermission1.setCode("1");
        sysPermission1.setName("1");
        sysPermission1.setType(SysPermissionTypeEnum.API);
        sysPermission1.setRemark("");
        sysPermission1.setStatus(StatusEnum.ENABLED);
        sysPermission1.setUpdatedSysUserId("");
        sysPermission1.setCreatedSysUserId("");
        sysPermission1.setUpdatedDateTime(LocalDateTime.now());
        sysPermission1.setCreatedDateTime(LocalDateTime.now());
        sysPermissionMapper.insert(sysPermission1);

        sysRoleService.setSysPermissionOfSysRole("1", Arrays.asList("1"), "1");

        List<SysPermission> sysPermissionOfSysRole = sysRoleService.getSysPermissionOfSysRole("1");

        assertThat(sysPermissionOfSysRole)
                .isNotNull()
                .hasSize(1)
                .usingElementComparator(Comparator.comparing(SysPermission::getId))
                .contains(sysPermission1);

    }

    @Test
    void setSysMenuOfSysRole() {
        final SysUser sysUser1 = new SysUser();
        sysUser1.setId("1");
        sysUser1.setUsername("chen");
        sysUser1.setPassword("chen");
        sysUser1.setLevel(0);
        sysUser1.setLastLoginDateTime(LocalDateTime.now());
        sysUser1.setRemark("test");
        sysUser1.setStatus(SysUserStatusEnum.ENABLED);
        sysUser1.setUpdatedSysUserId("");
        sysUser1.setCreatedSysUserId("");
        sysUser1.setUpdatedDateTime(LocalDateTime.now());
        sysUser1.setCreatedDateTime(LocalDateTime.now());
        sysUserMapper.insert(sysUser1);

        SysRole sysRole1 = new SysRole();
        sysRole1.setId("1");
        sysRole1.setCode("1");
        sysRole1.setName("1");
        sysRole1.setRemark("");
        sysRole1.setStatus(StatusEnum.ENABLED);
        sysRole1.setUpdatedSysUserId("");
        sysRole1.setCreatedSysUserId("");
        sysRole1.setUpdatedDateTime(LocalDateTime.now());
        sysRole1.setCreatedDateTime(LocalDateTime.now());
        sysRoleMapper.insert(sysRole1);

        SysMenu sysMenu1 = new SysMenu();
        sysMenu1.setId("1");
        sysMenu1.setParentId("1");
        sysMenu1.setName("1");
        sysMenu1.setNameI18n("1");
        sysMenu1.setUrl("");
        sysMenu1.setIcon("");
        sysMenu1.setType(SysMenuTypeEnum.GROUP);
        sysMenu1.setOrder(0);
        sysMenu1.setRemark("");
        sysMenu1.setStatus(StatusEnum.ENABLED);
        sysMenu1.setUpdatedSysUserId("");
        sysMenu1.setCreatedSysUserId("");
        sysMenu1.setUpdatedDateTime(LocalDateTime.now());
        sysMenu1.setCreatedDateTime(LocalDateTime.now());
        sysMenuMapper.insert(sysMenu1);

        sysRoleService.setSysMenuOfSysRole("1", Arrays.asList("1"), "1");

        List<SysMenu> sysMenuOfSysRole = sysRoleService.getSysMenuOfSysRole("1");

        assertThat(sysMenuOfSysRole)
                .isNotNull()
                .hasSize(1)
                .usingElementComparator(Comparator.comparing(SysMenu::getId))
                .contains(sysMenu1);
    }

    @Test
    void setSysApiOfSysRole() {

        final SysUser sysUser1 = new SysUser();
        sysUser1.setId("1");
        sysUser1.setUsername("chen");
        sysUser1.setPassword("chen");
        sysUser1.setLevel(0);
        sysUser1.setLastLoginDateTime(LocalDateTime.now());
        sysUser1.setRemark("test");
        sysUser1.setStatus(SysUserStatusEnum.ENABLED);
        sysUser1.setUpdatedSysUserId("");
        sysUser1.setCreatedSysUserId("");
        sysUser1.setUpdatedDateTime(LocalDateTime.now());
        sysUser1.setCreatedDateTime(LocalDateTime.now());
        sysUserMapper.insert(sysUser1);

        SysRole sysRole1 = new SysRole();
        sysRole1.setId("1");
        sysRole1.setCode("1");
        sysRole1.setName("1");
        sysRole1.setRemark("");
        sysRole1.setStatus(StatusEnum.ENABLED);
        sysRole1.setUpdatedSysUserId("");
        sysRole1.setCreatedSysUserId("");
        sysRole1.setUpdatedDateTime(LocalDateTime.now());
        sysRole1.setCreatedDateTime(LocalDateTime.now());
        sysRoleMapper.insert(sysRole1);

        SysApi sysApi1 = new SysApi();
        sysApi1.setId("1");
        sysApi1.setName("1");
        sysApi1.setUrl("/data");
        sysApi1.setHttpMethod(SysApiHttpMethodEnum.GET);
        sysApi1.setType(SysApiTypeEnum.LOGIN);
        sysApi1.setRemark("");
        sysApi1.setStatus(StatusEnum.ENABLED);
        sysApi1.setUpdatedSysUserId("");
        sysApi1.setCreatedSysUserId("");
        sysApi1.setUpdatedDateTime(LocalDateTime.now());
        sysApi1.setCreatedDateTime(LocalDateTime.now());
        sysApiMapper.insert(sysApi1);

        sysRoleService.setSysApiOfSysRole("1", Arrays.asList("1"), "1");

        List<SysApi> sysApiOfSysRole = sysRoleService.getSysApiOfSysRole("1");

        assertThat(sysApiOfSysRole)
                .isNotNull()
                .hasSize(1)
                .usingElementComparator(Comparator.comparing(SysApi::getId))
                .contains(sysApi1);
    }

    @Test
    void updateSysRole() {
        SysRole sysRole1 = new SysRole();
        sysRole1.setId("1");
        sysRole1.setCode("1");
        sysRole1.setName("1");
        sysRole1.setRemark("");
        sysRole1.setStatus(StatusEnum.ENABLED);
        sysRole1.setUpdatedSysUserId("");
        sysRole1.setCreatedSysUserId("");
        sysRole1.setUpdatedDateTime(LocalDateTime.now());
        sysRole1.setCreatedDateTime(LocalDateTime.now());
        sysRoleMapper.insert(sysRole1);

        final SysUser sysUser1 = new SysUser();
        sysUser1.setId("1");
        sysUser1.setUsername("chen");
        sysUser1.setPassword("chen");
        sysUser1.setLevel(0);
        sysUser1.setLastLoginDateTime(LocalDateTime.now());
        sysUser1.setRemark("test");
        sysUser1.setStatus(SysUserStatusEnum.ENABLED);
        sysUser1.setUpdatedSysUserId("");
        sysUser1.setCreatedSysUserId("");
        sysUser1.setUpdatedDateTime(LocalDateTime.now());
        sysUser1.setCreatedDateTime(LocalDateTime.now());
        sysUserMapper.insert(sysUser1);

        sysRoleService.updateSysRole("1", "2", "1", null, StatusEnum.ENABLED, "1");

        List<SysRole> sysRoleList = sysRoleMapper.selectList(Wrappers.<SysRole>lambdaQuery().eq(SysRole::getCode, "2"));

        assertThat(sysRoleList)
                .isNotNull()
                .hasSize(1);
    }

    @Test
    void deleteSysRole() {
        SysRole sysRole1 = new SysRole();
        sysRole1.setId("1");
        sysRole1.setCode("1");
        sysRole1.setName("1");
        sysRole1.setRemark("");
        sysRole1.setStatus(StatusEnum.ENABLED);
        sysRole1.setUpdatedSysUserId("");
        sysRole1.setCreatedSysUserId("");
        sysRole1.setUpdatedDateTime(LocalDateTime.now());
        sysRole1.setCreatedDateTime(LocalDateTime.now());
        sysRoleMapper.insert(sysRole1);

        sysRoleService.deleteSysRole("1");

        SysRole sysRole = sysRoleMapper.selectById("1");

        assertThat(sysRole).isNull();
    }

    @Test
    void enabledSysRole() {
        final SysUser sysUser1 = new SysUser();
        sysUser1.setId("1");
        sysUser1.setUsername("chen");
        sysUser1.setPassword("chen");
        sysUser1.setLevel(0);
        sysUser1.setLastLoginDateTime(LocalDateTime.now());
        sysUser1.setRemark("test");
        sysUser1.setStatus(SysUserStatusEnum.ENABLED);
        sysUser1.setUpdatedSysUserId("");
        sysUser1.setCreatedSysUserId("");
        sysUser1.setUpdatedDateTime(LocalDateTime.now());
        sysUser1.setCreatedDateTime(LocalDateTime.now());
        sysUserMapper.insert(sysUser1);

        SysRole sysRole1 = new SysRole();
        sysRole1.setId("1");
        sysRole1.setCode("1");
        sysRole1.setName("1");
        sysRole1.setRemark("");
        sysRole1.setStatus(StatusEnum.DISABLED);
        sysRole1.setUpdatedSysUserId("");
        sysRole1.setCreatedSysUserId("");
        sysRole1.setUpdatedDateTime(LocalDateTime.now());
        sysRole1.setCreatedDateTime(LocalDateTime.now());
        sysRoleMapper.insert(sysRole1);

        sysRoleService.enabledSysRole("1", "1");

        SysRole sysRole = sysRoleMapper.selectById("1");

        assertThat(sysRole).isNotNull();
        assertThat(sysRole.getStatus()).isEqualByComparingTo(StatusEnum.ENABLED);
    }

    @Test
    void disableSysRole() {
        final SysUser sysUser1 = new SysUser();
        sysUser1.setId("1");
        sysUser1.setUsername("chen");
        sysUser1.setPassword("chen");
        sysUser1.setLevel(0);
        sysUser1.setLastLoginDateTime(LocalDateTime.now());
        sysUser1.setRemark("test");
        sysUser1.setStatus(SysUserStatusEnum.ENABLED);
        sysUser1.setUpdatedSysUserId("");
        sysUser1.setCreatedSysUserId("");
        sysUser1.setUpdatedDateTime(LocalDateTime.now());
        sysUser1.setCreatedDateTime(LocalDateTime.now());
        sysUserMapper.insert(sysUser1);

        SysRole sysRole1 = new SysRole();
        sysRole1.setId("1");
        sysRole1.setCode("1");
        sysRole1.setName("1");
        sysRole1.setRemark("");
        sysRole1.setStatus(StatusEnum.ENABLED);
        sysRole1.setUpdatedSysUserId("");
        sysRole1.setCreatedSysUserId("");
        sysRole1.setUpdatedDateTime(LocalDateTime.now());
        sysRole1.setCreatedDateTime(LocalDateTime.now());
        sysRoleMapper.insert(sysRole1);

        sysRoleService.disableSysRole("1", "1");

        SysRole sysRole = sysRoleMapper.selectById("1");

        assertThat(sysRole).isNotNull();
        assertThat(sysRole.getStatus()).isEqualByComparingTo(StatusEnum.DISABLED);
    }
}