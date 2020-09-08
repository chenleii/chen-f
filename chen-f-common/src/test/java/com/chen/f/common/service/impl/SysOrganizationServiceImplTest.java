package com.chen.f.common.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.test.autoconfigure.AutoConfigureMybatisPlus;
import com.chen.f.common.configuration.EnableChenFCommonConfiguration;
import com.chen.f.common.mapper.*;
import com.chen.f.common.pojo.*;
import com.chen.f.common.pojo.enums.*;
import com.chen.f.core.configuration.mybatisplus.EnableChenFMybatisPlusConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author chen
 * @since 2020/9/8 11:16
 */
@SpringBootTest(classes = {SysOrganizationServiceImpl.class,})
@Transactional
@AutoConfigureMybatisPlus
@AutoConfigureTestDatabase
@ImportAutoConfiguration({
        EnableChenFCommonConfiguration.class,
        EnableChenFMybatisPlusConfiguration.class,
})
//@Sql("classpath:db/schema-h2.sql")
class SysOrganizationServiceImplTest {

    @Autowired
    private SysOrganizationServiceImpl sysOrganizationService;

    @Autowired
    private SysOrganizationMapper sysOrganizationMapper;

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysOrganizationUserMapper sysOrganizationUserMapper;
    @Autowired
    private SysOrganizationRoleMapper sysOrganizationRoleMapper;
    
    @BeforeEach
    void setUp() {
    }

    @Test
    void getAllSysOrganizationList() {
        SysOrganization sysOrganization1 = new SysOrganization();
        sysOrganization1.setId("1");
        sysOrganization1.setParentId("");
        sysOrganization1.setName("1");
        sysOrganization1.setFullName("1");
        sysOrganization1.setType(SysOrganizationTypeEnum.COMPANY);
        sysOrganization1.setRemark("");
        sysOrganization1.setStatus(StatusEnum.ENABLED);
        sysOrganization1.setUpdatedSysUserId("");
        sysOrganization1.setCreatedSysUserId("");
        sysOrganization1.setUpdatedDateTime(LocalDateTime.now());
        sysOrganization1.setCreatedDateTime(LocalDateTime.now());
        sysOrganizationMapper.insert(sysOrganization1);

        List<SysOrganization> sysOrganizationList = sysOrganizationService.getAllSysOrganizationList();

        assertThat(sysOrganizationList)
                .isNotNull()
                .hasSize(1)
                .usingElementComparator(Comparator.comparing(SysOrganization::getId))
                .contains(sysOrganization1);

    }

    @Test
    void getEnabledSysOrganizationList() {
        SysOrganization sysOrganization1 = new SysOrganization();
        sysOrganization1.setId("1");
        sysOrganization1.setParentId("");
        sysOrganization1.setName("1");
        sysOrganization1.setFullName("1");
        sysOrganization1.setType(SysOrganizationTypeEnum.COMPANY);
        sysOrganization1.setRemark("");
        sysOrganization1.setStatus(StatusEnum.ENABLED);
        sysOrganization1.setUpdatedSysUserId("");
        sysOrganization1.setCreatedSysUserId("");
        sysOrganization1.setUpdatedDateTime(LocalDateTime.now());
        sysOrganization1.setCreatedDateTime(LocalDateTime.now());
        sysOrganizationMapper.insert(sysOrganization1);

        List<SysOrganization> sysOrganizationList = sysOrganizationService.getAllSysOrganizationList();

        assertThat(sysOrganizationList)
                .isNotNull()
                .hasSize(1)
                .usingElementComparator(Comparator.comparing(SysOrganization::getId))
                .contains(sysOrganization1);
    }

    @Test
    void getSysOrganizationList() {
        SysOrganization sysOrganization1 = new SysOrganization();
        sysOrganization1.setId("1");
        sysOrganization1.setParentId("");
        sysOrganization1.setName("1");
        sysOrganization1.setFullName("1");
        sysOrganization1.setType(SysOrganizationTypeEnum.COMPANY);
        sysOrganization1.setRemark("");
        sysOrganization1.setStatus(StatusEnum.ENABLED);
        sysOrganization1.setUpdatedSysUserId("");
        sysOrganization1.setCreatedSysUserId("");
        sysOrganization1.setUpdatedDateTime(LocalDateTime.now());
        sysOrganization1.setCreatedDateTime(LocalDateTime.now());
        sysOrganizationMapper.insert(sysOrganization1);

        List<SysOrganization> sysOrganizationList = sysOrganizationService.getSysOrganizationList(null, null, null, null, null, null);

        assertThat(sysOrganizationList)
                .isNotNull()
                .hasSize(1)
                .usingElementComparator(Comparator.comparing(SysOrganization::getId))
                .contains(sysOrganization1);
    }

    @Test
    void getSysOrganization() {
        SysOrganization sysOrganization1 = new SysOrganization();
        sysOrganization1.setId("1");
        sysOrganization1.setParentId("");
        sysOrganization1.setName("1");
        sysOrganization1.setFullName("1");
        sysOrganization1.setType(SysOrganizationTypeEnum.COMPANY);
        sysOrganization1.setRemark("");
        sysOrganization1.setStatus(StatusEnum.ENABLED);
        sysOrganization1.setUpdatedSysUserId("");
        sysOrganization1.setCreatedSysUserId("");
        sysOrganization1.setUpdatedDateTime(LocalDateTime.now());
        sysOrganization1.setCreatedDateTime(LocalDateTime.now());
        sysOrganizationMapper.insert(sysOrganization1);

        SysOrganization sysOrganization = sysOrganizationService.getSysOrganization("1");

        assertThat(sysOrganization)
                .isNotNull()
                .usingComparator(Comparator.comparing(SysOrganization::getId))
                .isEqualTo(sysOrganization1);
    }

    @Test
    void getSysUserOfSysOrganization() {
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

        SysOrganization sysOrganization1 = new SysOrganization();
        sysOrganization1.setId("1");
        sysOrganization1.setParentId("");
        sysOrganization1.setName("1");
        sysOrganization1.setFullName("1");
        sysOrganization1.setType(SysOrganizationTypeEnum.COMPANY);
        sysOrganization1.setRemark("");
        sysOrganization1.setStatus(StatusEnum.ENABLED);
        sysOrganization1.setUpdatedSysUserId("");
        sysOrganization1.setCreatedSysUserId("");
        sysOrganization1.setUpdatedDateTime(LocalDateTime.now());
        sysOrganization1.setCreatedDateTime(LocalDateTime.now());
        sysOrganizationMapper.insert(sysOrganization1);

        SysOrganizationUser sysOrganizationUser1 = new SysOrganizationUser();
        sysOrganizationUser1.setId("1");
        sysOrganizationUser1.setSysOrganizationId(sysOrganization1.getId());
        sysOrganizationUser1.setSysUserId(sysUser1.getId());
        sysOrganizationUser1.setCreatedSysUserId("1");
        sysOrganizationUser1.setCreatedDateTime(LocalDateTime.now());
        sysOrganizationUserMapper.insert(sysOrganizationUser1);

        List<SysUser> sysUserOfSysOrganization = sysOrganizationService.getSysUserOfSysOrganization("1");

        assertThat(sysUserOfSysOrganization)
                .isNotNull()
                .usingElementComparator(Comparator.comparing(SysUser::getId))
                .contains(sysUser1);
    }

    @Test
    void testGetSysUserOfSysOrganization() {
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

        SysOrganization sysOrganization1 = new SysOrganization();
        sysOrganization1.setId("1");
        sysOrganization1.setParentId("");
        sysOrganization1.setName("1");
        sysOrganization1.setFullName("1");
        sysOrganization1.setType(SysOrganizationTypeEnum.COMPANY);
        sysOrganization1.setRemark("");
        sysOrganization1.setStatus(StatusEnum.ENABLED);
        sysOrganization1.setUpdatedSysUserId("");
        sysOrganization1.setCreatedSysUserId("");
        sysOrganization1.setUpdatedDateTime(LocalDateTime.now());
        sysOrganization1.setCreatedDateTime(LocalDateTime.now());
        sysOrganizationMapper.insert(sysOrganization1);

        SysOrganizationUser sysOrganizationUser1 = new SysOrganizationUser();
        sysOrganizationUser1.setId("1");
        sysOrganizationUser1.setSysOrganizationId(sysOrganization1.getId());
        sysOrganizationUser1.setSysUserId(sysUser1.getId());
        sysOrganizationUser1.setCreatedSysUserId("1");
        sysOrganizationUser1.setCreatedDateTime(LocalDateTime.now());
        sysOrganizationUserMapper.insert(sysOrganizationUser1);

        List<SysUser> sysUserOfSysOrganization = sysOrganizationService.getSysUserOfSysOrganization(Arrays.asList("1"));

        assertThat(sysUserOfSysOrganization)
                .isNotNull()
                .usingElementComparator(Comparator.comparing(SysUser::getId))
                .contains(sysUser1);
    }

    @Test
    void getSysRoleOfSysOrganization() {
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

        SysOrganization sysOrganization1 = new SysOrganization();
        sysOrganization1.setId("1");
        sysOrganization1.setParentId("");
        sysOrganization1.setName("1");
        sysOrganization1.setFullName("1");
        sysOrganization1.setType(SysOrganizationTypeEnum.COMPANY);
        sysOrganization1.setRemark("");
        sysOrganization1.setStatus(StatusEnum.ENABLED);
        sysOrganization1.setUpdatedSysUserId("");
        sysOrganization1.setCreatedSysUserId("");
        sysOrganization1.setUpdatedDateTime(LocalDateTime.now());
        sysOrganization1.setCreatedDateTime(LocalDateTime.now());
        sysOrganizationMapper.insert(sysOrganization1);

        SysOrganizationRole sysOrganizationRole1 = new SysOrganizationRole();
        sysOrganizationRole1.setId("1");
        sysOrganizationRole1.setSysOrganizationId(sysOrganization1.getId());
        sysOrganizationRole1.setSysRoleId(sysRole1.getId());
        sysOrganizationRole1.setCreatedSysUserId("1");
        sysOrganizationRole1.setCreatedDateTime(LocalDateTime.now());
        sysOrganizationRoleMapper.insert(sysOrganizationRole1);

        List<SysRole> sysRoleOfSysOrganization = sysOrganizationService.getSysRoleOfSysOrganization("1");

        assertThat(sysRoleOfSysOrganization)
                .isNotNull()
                .usingElementComparator(Comparator.comparing(SysRole::getId))
                .contains(sysRole1);
    }

    @Test
    void testGetSysRoleOfSysOrganization() {
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

        SysOrganization sysOrganization1 = new SysOrganization();
        sysOrganization1.setId("1");
        sysOrganization1.setParentId("");
        sysOrganization1.setName("1");
        sysOrganization1.setFullName("1");
        sysOrganization1.setType(SysOrganizationTypeEnum.COMPANY);
        sysOrganization1.setRemark("");
        sysOrganization1.setStatus(StatusEnum.ENABLED);
        sysOrganization1.setUpdatedSysUserId("");
        sysOrganization1.setCreatedSysUserId("");
        sysOrganization1.setUpdatedDateTime(LocalDateTime.now());
        sysOrganization1.setCreatedDateTime(LocalDateTime.now());
        sysOrganizationMapper.insert(sysOrganization1);

        SysOrganizationRole sysOrganizationRole1 = new SysOrganizationRole();
        sysOrganizationRole1.setId("1");
        sysOrganizationRole1.setSysOrganizationId(sysOrganization1.getId());
        sysOrganizationRole1.setSysRoleId(sysRole1.getId());
        sysOrganizationRole1.setCreatedSysUserId("1");
        sysOrganizationRole1.setCreatedDateTime(LocalDateTime.now());
        sysOrganizationRoleMapper.insert(sysOrganizationRole1);

        List<SysRole> sysRoleOfSysOrganization = sysOrganizationService.getSysRoleOfSysOrganization(Arrays.asList("1"));

        assertThat(sysRoleOfSysOrganization)
                .isNotNull()
                .usingElementComparator(Comparator.comparing(SysRole::getId))
                .contains(sysRole1);
    }

    @Test
    void createSysOrganization() {
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

        sysOrganizationService.createSysOrganization("1", "1", "11", SysOrganizationTypeEnum.COMPANY, null, StatusEnum.ENABLED, "1");

        List<SysOrganization> sysOrganizationList = sysOrganizationMapper.selectList(Wrappers.<SysOrganization>lambdaQuery().eq(SysOrganization::getName, "1"));

        assertThat(sysOrganizationList)
                .isNotNull()
                .hasSize(1);
    }

    @Test
    void updateSysOrganization() {
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

        SysOrganization sysOrganization1 = new SysOrganization();
        sysOrganization1.setId("1");
        sysOrganization1.setParentId("");
        sysOrganization1.setName("1");
        sysOrganization1.setFullName("1");
        sysOrganization1.setType(SysOrganizationTypeEnum.COMPANY);
        sysOrganization1.setRemark("");
        sysOrganization1.setStatus(StatusEnum.ENABLED);
        sysOrganization1.setUpdatedSysUserId("");
        sysOrganization1.setCreatedSysUserId("");
        sysOrganization1.setUpdatedDateTime(LocalDateTime.now());
        sysOrganization1.setCreatedDateTime(LocalDateTime.now());
        sysOrganizationMapper.insert(sysOrganization1);

        sysOrganizationService.updateSysOrganization("1","1", "2", "11", SysOrganizationTypeEnum.COMPANY, null, StatusEnum.ENABLED, "1");

        List<SysOrganization> sysOrganizationList = sysOrganizationMapper.selectList(Wrappers.<SysOrganization>lambdaQuery().eq(SysOrganization::getName, "2"));

        assertThat(sysOrganizationList)
                .isNotNull()
                .hasSize(1);
    }

    @Test
    void setSysUserOfSysOrganization() {
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

        SysOrganization sysOrganization1 = new SysOrganization();
        sysOrganization1.setId("1");
        sysOrganization1.setParentId("");
        sysOrganization1.setName("1");
        sysOrganization1.setFullName("1");
        sysOrganization1.setType(SysOrganizationTypeEnum.COMPANY);
        sysOrganization1.setRemark("");
        sysOrganization1.setStatus(StatusEnum.ENABLED);
        sysOrganization1.setUpdatedSysUserId("");
        sysOrganization1.setCreatedSysUserId("");
        sysOrganization1.setUpdatedDateTime(LocalDateTime.now());
        sysOrganization1.setCreatedDateTime(LocalDateTime.now());
        sysOrganizationMapper.insert(sysOrganization1);

        sysOrganizationService.setSysUserOfSysOrganization("1",Arrays.asList("1"), "1");

        List<SysUser> sysUserOfSysOrganization = sysOrganizationService.getSysUserOfSysOrganization("1");

        assertThat(sysUserOfSysOrganization)
                .isNotNull()
                .hasSize(1)
                .usingElementComparator(Comparator.comparing(SysUser::getId))
                .contains(sysUser1);
    }

    @Test
    void setSysRoleOfSysOrganization() {
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

        SysOrganization sysOrganization1 = new SysOrganization();
        sysOrganization1.setId("1");
        sysOrganization1.setParentId("");
        sysOrganization1.setName("1");
        sysOrganization1.setFullName("1");
        sysOrganization1.setType(SysOrganizationTypeEnum.COMPANY);
        sysOrganization1.setRemark("");
        sysOrganization1.setStatus(StatusEnum.ENABLED);
        sysOrganization1.setUpdatedSysUserId("");
        sysOrganization1.setCreatedSysUserId("");
        sysOrganization1.setUpdatedDateTime(LocalDateTime.now());
        sysOrganization1.setCreatedDateTime(LocalDateTime.now());
        sysOrganizationMapper.insert(sysOrganization1);

        sysOrganizationService.setSysRoleOfSysOrganization("1",Arrays.asList("1"), "1");

        List<SysRole> sysRoleOfSysOrganization = sysOrganizationService.getSysRoleOfSysOrganization("1");

        assertThat(sysRoleOfSysOrganization)
                .isNotNull()
                .hasSize(1)
                .usingElementComparator(Comparator.comparing(SysRole::getId))
                .contains(sysRole1);
    }

    @Test
    void deleteSysOrganization() {

        SysOrganization sysOrganization1 = new SysOrganization();
        sysOrganization1.setId("1");
        sysOrganization1.setParentId("");
        sysOrganization1.setName("1");
        sysOrganization1.setFullName("1");
        sysOrganization1.setType(SysOrganizationTypeEnum.COMPANY);
        sysOrganization1.setRemark("");
        sysOrganization1.setStatus(StatusEnum.ENABLED);
        sysOrganization1.setUpdatedSysUserId("");
        sysOrganization1.setCreatedSysUserId("");
        sysOrganization1.setUpdatedDateTime(LocalDateTime.now());
        sysOrganization1.setCreatedDateTime(LocalDateTime.now());
        sysOrganizationMapper.insert(sysOrganization1);

        sysOrganizationService.deleteSysOrganization("1");

        SysOrganization sysOrganization = sysOrganizationMapper.selectById("1");

        assertThat(sysOrganization).isNull();

    }

    @Test
    void enabledSysOrganization() {
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

        SysOrganization sysOrganization1 = new SysOrganization();
        sysOrganization1.setId("1");
        sysOrganization1.setParentId("");
        sysOrganization1.setName("1");
        sysOrganization1.setFullName("1");
        sysOrganization1.setType(SysOrganizationTypeEnum.COMPANY);
        sysOrganization1.setRemark("");
        sysOrganization1.setStatus(StatusEnum.DISABLED);
        sysOrganization1.setUpdatedSysUserId("");
        sysOrganization1.setCreatedSysUserId("");
        sysOrganization1.setUpdatedDateTime(LocalDateTime.now());
        sysOrganization1.setCreatedDateTime(LocalDateTime.now());
        sysOrganizationMapper.insert(sysOrganization1);

        sysOrganizationService.enabledSysOrganization("1","1");

        SysOrganization sysOrganization = sysOrganizationMapper.selectById("1");

        assertThat(sysOrganization).isNotNull();
        assertThat(sysOrganization.getStatus()).isEqualByComparingTo(StatusEnum.ENABLED);
    }

    @Test
    void disableSysOrganization() {
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

        SysOrganization sysOrganization1 = new SysOrganization();
        sysOrganization1.setId("1");
        sysOrganization1.setParentId("");
        sysOrganization1.setName("1");
        sysOrganization1.setFullName("1");
        sysOrganization1.setType(SysOrganizationTypeEnum.COMPANY);
        sysOrganization1.setRemark("");
        sysOrganization1.setStatus(StatusEnum.ENABLED);
        sysOrganization1.setUpdatedSysUserId("");
        sysOrganization1.setCreatedSysUserId("");
        sysOrganization1.setUpdatedDateTime(LocalDateTime.now());
        sysOrganization1.setCreatedDateTime(LocalDateTime.now());
        sysOrganizationMapper.insert(sysOrganization1);

        sysOrganizationService.disableSysOrganization("1","1");

        SysOrganization sysOrganization = sysOrganizationMapper.selectById("1");

        assertThat(sysOrganization).isNotNull();
        assertThat(sysOrganization.getStatus()).isEqualByComparingTo(StatusEnum.DISABLED);
    }
}
