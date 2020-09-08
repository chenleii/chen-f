package com.chen.f.common.service.impl;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.chen.f.common.pojo.enums.*;
import com.chen.f.common.mapper.*;
import com.chen.f.common.pojo.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import com.baomidou.mybatisplus.test.autoconfigure.AutoConfigureMybatisPlus;
import com.chen.f.common.configuration.EnableChenFCommonConfiguration;
import com.chen.f.core.configuration.mybatisplus.EnableChenFMybatisPlusConfiguration;
import com.chen.f.core.page.Page;
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
 * @since 2020/9/8 14:37
 */
@SpringBootTest(classes = {SysPermissionServiceImpl.class,})
@Transactional
@AutoConfigureMybatisPlus
@AutoConfigureTestDatabase
@ImportAutoConfiguration({
        EnableChenFCommonConfiguration.class,
        EnableChenFMybatisPlusConfiguration.class,
})
//@Sql("classpath:db/schema-h2.sql")
class SysPermissionServiceImplTest {

    @Autowired
    private SysPermissionServiceImpl sysPermissionService;

    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Autowired
    private SysApiMapper sysApiMapper;

    @Autowired
    private SysPermissionMenuMapper sysPermissionMenuMapper;
    @Autowired
    private SysPermissionApiMapper sysPermissionApiMapper;

    @Test
    void getEnabledSysPermissionList() {
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

        List<SysPermission> sysPermissionList = sysPermissionService.getEnabledSysPermissionList();

        assertThat(sysPermissionList)
                .isNotNull()
                .hasSize(1)
                .usingElementComparator(Comparator.comparing(SysPermission::getId))
                .contains(sysPermission1);


    }

    @Test
    void getSysPermissionPage() {
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

        Page<SysPermission> sysPermissionPage = sysPermissionService.getSysPermissionPage(new Page<>(), null, null, null, null, null);

        assertThat(sysPermissionPage).isNotNull();
        assertThat(sysPermissionPage.getTotal()).isEqualTo(1);
        assertThat(sysPermissionPage.getList())
                .isNotNull()
                .hasSize(1)
                .usingElementComparator(Comparator.comparing(SysPermission::getId))
                .contains(sysPermission1);
    }

    @Test
    void getSysPermission() {
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

        SysPermission sysPermission = sysPermissionService.getSysPermission("1");

        assertThat(sysPermission)
                .isNotNull()
                .usingComparator(Comparator.comparing(SysPermission::getId))
                .isEqualTo(sysPermission1);
    }

    @Test
    void getSysMenuOfSysPermission() {
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

        SysMenu sysMenu1 = new SysMenu();
        sysMenu1.setId("1");
        sysMenu1.setParentId("");
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

        SysPermissionMenu sysPermissionMenu1 = new SysPermissionMenu();
        sysPermissionMenu1.setId("1");
        sysPermissionMenu1.setSysPermissionId(sysPermission1.getId());
        sysPermissionMenu1.setSysMenuId(sysMenu1.getId());
        sysPermissionMenu1.setCreatedSysUserId("1");
        sysPermissionMenu1.setCreatedDateTime(LocalDateTime.now());
        sysPermissionMenuMapper.insert(sysPermissionMenu1);

        List<SysMenu> sysMenuOfSysPermission = sysPermissionService.getSysMenuOfSysPermission("1");

        assertThat(sysMenuOfSysPermission)
                .isNotNull()
                .hasSize(1)
                .usingElementComparator(Comparator.comparing(SysMenu::getId))
                .contains(sysMenu1);
    }

    @Test
    void testGetSysMenuOfSysPermission() {

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

        SysMenu sysMenu1 = new SysMenu();
        sysMenu1.setId("1");
        sysMenu1.setParentId("");
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

        SysPermissionMenu sysPermissionMenu1 = new SysPermissionMenu();
        sysPermissionMenu1.setId("1");
        sysPermissionMenu1.setSysPermissionId(sysPermission1.getId());
        sysPermissionMenu1.setSysMenuId(sysMenu1.getId());
        sysPermissionMenu1.setCreatedSysUserId("1");
        sysPermissionMenu1.setCreatedDateTime(LocalDateTime.now());
        sysPermissionMenuMapper.insert(sysPermissionMenu1);

        List<SysMenu> sysMenuOfSysPermission = sysPermissionService.getSysMenuOfSysPermission(Arrays.asList("1"));

        assertThat(sysMenuOfSysPermission)
                .isNotNull()
                .hasSize(1)
                .usingElementComparator(Comparator.comparing(SysMenu::getId))
                .contains(sysMenu1);
    }

    @Test
    void getSysApiOfSysPermission() {

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

        SysApi sysApi1 = new SysApi();
        sysApi1.setId("1");
        sysApi1.setName("1");
        sysApi1.setUrl("");
        sysApi1.setHttpMethod(SysApiHttpMethodEnum.GET);
        sysApi1.setType(SysApiTypeEnum.LOGIN);
        sysApi1.setRemark("");
        sysApi1.setStatus(StatusEnum.ENABLED);
        sysApi1.setUpdatedSysUserId("");
        sysApi1.setCreatedSysUserId("");
        sysApi1.setUpdatedDateTime(LocalDateTime.now());
        sysApi1.setCreatedDateTime(LocalDateTime.now());
        sysApiMapper.insert(sysApi1);

        SysPermissionApi sysPermissionApi1 = new SysPermissionApi();
        sysPermissionApi1.setId("1");
        sysPermissionApi1.setSysPermissionId(sysPermission1.getId());
        sysPermissionApi1.setSysApiId(sysApi1.getId());
        sysPermissionApi1.setCreatedSysUserId("1");
        sysPermissionApi1.setCreatedDateTime(LocalDateTime.now());
        sysPermissionApiMapper.insert(sysPermissionApi1);

        List<SysApi> sysApiOfSysPermission = sysPermissionService.getSysApiOfSysPermission("1");

        assertThat(sysApiOfSysPermission)
                .isNotNull()
                .hasSize(1)
                .usingElementComparator(Comparator.comparing(SysApi::getId))
                .contains(sysApi1);
    }

    @Test
    void testGetSysApiOfSysPermission() {

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

        SysApi sysApi1 = new SysApi();
        sysApi1.setId("1");
        sysApi1.setName("1");
        sysApi1.setUrl("");
        sysApi1.setHttpMethod(SysApiHttpMethodEnum.GET);
        sysApi1.setType(SysApiTypeEnum.LOGIN);
        sysApi1.setRemark("");
        sysApi1.setStatus(StatusEnum.ENABLED);
        sysApi1.setUpdatedSysUserId("");
        sysApi1.setCreatedSysUserId("");
        sysApi1.setUpdatedDateTime(LocalDateTime.now());
        sysApi1.setCreatedDateTime(LocalDateTime.now());
        sysApiMapper.insert(sysApi1);

        SysPermissionApi sysPermissionApi1 = new SysPermissionApi();
        sysPermissionApi1.setId("1");
        sysPermissionApi1.setSysPermissionId(sysPermission1.getId());
        sysPermissionApi1.setSysApiId(sysApi1.getId());
        sysPermissionApi1.setCreatedSysUserId("1");
        sysPermissionApi1.setCreatedDateTime(LocalDateTime.now());
        sysPermissionApiMapper.insert(sysPermissionApi1);

        List<SysApi> sysApiOfSysPermission = sysPermissionService.getSysApiOfSysPermission(Arrays.asList("1"));

        assertThat(sysApiOfSysPermission)
                .isNotNull()
                .hasSize(1)
                .usingElementComparator(Comparator.comparing(SysApi::getId))
                .contains(sysApi1);
    }

    @Test
    void createSysPermission() {
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

        sysPermissionService.createSysPermission("1", "1", SysPermissionTypeEnum.API, null, StatusEnum.ENABLED, "1");

        List<SysPermission> sysPermissionList = sysPermissionMapper.selectList(Wrappers.<SysPermission>lambdaQuery()
                .eq(SysPermission::getCode, "1"));

        assertThat(sysPermissionList)
                .isNotNull()
                .hasSize(1);
    }

    @Test
    void updateSysPermission() {
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

        sysPermissionService.updateSysPermission("1", "2", "1", SysPermissionTypeEnum.API, null, StatusEnum.ENABLED, "1");

        List<SysPermission> sysPermissionList = sysPermissionMapper.selectList(Wrappers.<SysPermission>lambdaQuery()
                .eq(SysPermission::getCode, "2"));

        assertThat(sysPermissionList)
                .isNotNull()
                .hasSize(1);
    }

    @Test
    void setSysApiOfSysPermission() {
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

        SysApi sysApi1 = new SysApi();
        sysApi1.setId("1");
        sysApi1.setName("1");
        sysApi1.setUrl("");
        sysApi1.setHttpMethod(SysApiHttpMethodEnum.GET);
        sysApi1.setType(SysApiTypeEnum.LOGIN);
        sysApi1.setRemark("");
        sysApi1.setStatus(StatusEnum.ENABLED);
        sysApi1.setUpdatedSysUserId("");
        sysApi1.setCreatedSysUserId("");
        sysApi1.setUpdatedDateTime(LocalDateTime.now());
        sysApi1.setCreatedDateTime(LocalDateTime.now());
        sysApiMapper.insert(sysApi1);

        sysPermissionService.setSysApiOfSysPermission("1", Arrays.asList("1"), "1");

        List<SysApi> sysApiOfSysPermission = sysPermissionService.getSysApiOfSysPermission("1");

        assertThat(sysApiOfSysPermission)
                .isNotNull()
                .hasSize(1)
                .usingElementComparator(Comparator.comparing(SysApi::getId))
                .contains(sysApi1);
    }

    @Test
    void setSysMenuOfSysPermission() {
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

        SysMenu sysMenu1 = new SysMenu();
        sysMenu1.setId("1");
        sysMenu1.setParentId("");
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

        sysPermissionService.setSysMenuOfSysPermission("1", Arrays.asList("1"), "1");

        List<SysMenu> sysMenuOfSysPermission = sysPermissionService.getSysMenuOfSysPermission("1");

        assertThat(sysMenuOfSysPermission)
                .isNotNull()
                .hasSize(1)
                .usingElementComparator(Comparator.comparing(SysMenu::getId))
                .contains(sysMenu1);
    }

    @Test
    void deleteSysPermission() {
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

        sysPermissionService.deleteSysPermission("1");

        SysPermission sysPermission = sysPermissionMapper.selectById("1");

        assertThat(sysPermission).isNull();
    }

    @Test
    void enabledSysPermission() {
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

        SysPermission sysPermission1 = new SysPermission();
        sysPermission1.setId("1");
        sysPermission1.setCode("1");
        sysPermission1.setName("1");
        sysPermission1.setType(SysPermissionTypeEnum.API);
        sysPermission1.setRemark("");
        sysPermission1.setStatus(StatusEnum.DISABLED);
        sysPermission1.setUpdatedSysUserId("");
        sysPermission1.setCreatedSysUserId("");
        sysPermission1.setUpdatedDateTime(LocalDateTime.now());
        sysPermission1.setCreatedDateTime(LocalDateTime.now());
        sysPermissionMapper.insert(sysPermission1);

        sysPermissionService.enabledSysPermission("1", "1");

        SysPermission sysPermission = sysPermissionMapper.selectById("1");

        assertThat(sysPermission).isNotNull();
        assertThat(sysPermission.getStatus()).isEqualByComparingTo(StatusEnum.ENABLED);
    }

    @Test
    void disableSysPermission() {
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

        sysPermissionService.disableSysPermission("1", "1");

        SysPermission sysPermission = sysPermissionMapper.selectById("1");

        assertThat(sysPermission).isNotNull();
        assertThat(sysPermission.getStatus()).isEqualByComparingTo(StatusEnum.DISABLED);
    }
}
