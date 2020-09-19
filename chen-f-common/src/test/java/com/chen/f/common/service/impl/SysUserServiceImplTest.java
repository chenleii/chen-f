package com.chen.f.common.service.impl;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.test.autoconfigure.AutoConfigureMybatisPlus;
import com.chen.f.common.configuration.EnableChenFCommonConfiguration;
import com.chen.f.common.mapper.SysOrganizationMapper;
import com.chen.f.common.mapper.SysOrganizationUserMapper;
import com.chen.f.common.mapper.SysRoleMapper;
import com.chen.f.common.mapper.SysUserMapper;
import com.chen.f.common.mapper.SysUserRoleMapper;
import com.chen.f.common.pojo.SysOrganization;
import com.chen.f.common.pojo.SysOrganizationUser;
import com.chen.f.common.pojo.SysRole;
import com.chen.f.common.pojo.SysUser;
import com.chen.f.common.pojo.SysUserRole;
import com.chen.f.common.pojo.enums.StatusEnum;
import com.chen.f.common.pojo.enums.SysOrganizationTypeEnum;
import com.chen.f.common.pojo.enums.SysUserStatusEnum;
import com.chen.f.core.configuration.mybatisplus.EnableChenFMybatisPlusConfiguration;
import com.chen.f.core.page.Page;
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
 * @since 2020/9/8 23:49.
 */
@SpringBootTest(classes = {SysUserServiceImpl.class,})
@Transactional
@AutoConfigureMybatisPlus
@AutoConfigureTestDatabase
@ImportAutoConfiguration({
        EnableChenFCommonConfiguration.class,
        EnableChenFMybatisPlusConfiguration.class,
})
//@Sql("classpath:db/schema-h2.sql")
class SysUserServiceImplTest {
    
    @Autowired
    private SysUserServiceImpl sysUserService;

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysOrganizationMapper sysOrganizationMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysOrganizationUserMapper sysOrganizationUserMapper;
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    
    @BeforeEach
    void setUp() {
    }

    @Test
    void getAllSysUserList() {
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

        final List<SysUser> sysUserList = sysUserService.getAllSysUserList();

        assertThat(sysUserList)
                .isNotNull()
                .hasSize(1)
                .usingElementComparator(Comparator.comparing(SysUser::getId))
                .contains(sysUser1);
    }

    @Test
    void getEnabledSysUserList() {
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

        final List<SysUser> sysUserList = sysUserService.getEnabledSysUserList();

        assertThat(sysUserList)
                .isNotNull()
                .hasSize(1)
                .usingElementComparator(Comparator.comparing(SysUser::getId))
                .contains(sysUser1);
    }

    @Test
    void getSysUserPage() {

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

        final Page<SysUser> sysUserPage = sysUserService.getSysUserPage(new Page<>(), null, null, null, null);

        assertThat(sysUserPage).isNotNull();
        assertThat(sysUserPage.getTotal()).isEqualTo(1);
        assertThat(sysUserPage.getList())
                .isNotNull()
                .hasSize(1)
                .usingElementComparator(Comparator.comparing(SysUser::getId))
                .contains(sysUser1);
    }

    @Test
    void getSysUser() {
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

        final SysUser sysUser = sysUserService.getSysUser("1");

        assertThat(sysUser)
                .isNotNull()
                .usingComparator(Comparator.comparing(SysUser::getId))
                .isEqualTo(sysUser1);
    }

    @Test
    void getSysUserByUsername() {

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

        final SysUser sysUser = sysUserService.getSysUserByUsername("chen");

        assertThat(sysUser)
                .isNotNull()
                .usingComparator(Comparator.comparing(SysUser::getId))
                .isEqualTo(sysUser1);
    }

    @Test
    void getSysOrganizationOfSysUser() {

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

        final SysOrganization sysOrganization1 = new SysOrganization();
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
        
        final SysOrganizationUser sysOrganizationUser1 = new SysOrganizationUser();
        sysOrganizationUser1.setId("1");
        sysOrganizationUser1.setSysOrganizationId(sysOrganization1.getId());
        sysOrganizationUser1.setSysUserId(sysUser1.getId());
        sysOrganizationUser1.setCreatedSysUserId("1");
        sysOrganizationUser1.setCreatedDateTime(LocalDateTime.now());
        sysOrganizationUserMapper.insert(sysOrganizationUser1);

        final List<SysOrganization> sysOrganizationOfSysUser = sysUserService.getSysOrganizationOfSysUser("1");

        assertThat(sysOrganizationOfSysUser)
                .isNotNull()
                .hasSize(1)
                .usingElementComparator(Comparator.comparing(SysOrganization::getId))
                .contains(sysOrganization1);
    }

    @Test
    void getSysRoleOfSysUser() {
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

        final SysRole sysRole1 = new SysRole();
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

        final SysUserRole sysUserRole1 = new SysUserRole();
        sysUserRole1.setId("1");
        sysUserRole1.setSysUserId(sysUser1.getId());
        sysUserRole1.setSysRoleId(sysRole1.getId());
        sysUserRole1.setCreatedSysUserId("1");
        sysUserRole1.setCreatedDateTime(LocalDateTime.now());
        sysUserRoleMapper.insert(sysUserRole1);
        

        final List<SysRole> sysRoleOfSysUser = sysUserService.getSysRoleOfSysUser("1");

        assertThat(sysRoleOfSysUser)
                .isNotNull()
                .hasSize(1)
                .usingElementComparator(Comparator.comparing(SysRole::getId))
                .contains(sysRole1);
    }

    @Test
    void createSysUser() {

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

        sysUserService.createSysUser("1", "1", 0, null, SysUserStatusEnum.ENABLED, "1");

        final List<SysUser> sysUserList = sysUserMapper.selectList(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUsername, "1"));

        assertThat(sysUserList)
                .isNotNull()
                .hasSize(1);
    }

    @Test
    void updateSysUser() {

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
        
        final SysUser sysUser2 = new SysUser();
        sysUser2.setId("2");
        sysUser2.setUsername("2");
        sysUser2.setPassword("2");
        sysUser2.setLevel(1);
        sysUser2.setLastLoginDateTime(LocalDateTime.now());
        sysUser2.setRemark("test");
        sysUser2.setStatus(SysUserStatusEnum.ENABLED);
        sysUser2.setUpdatedSysUserId("");
        sysUser2.setCreatedSysUserId("");
        sysUser2.setUpdatedDateTime(LocalDateTime.now());
        sysUser2.setCreatedDateTime(LocalDateTime.now());
        sysUserMapper.insert(sysUser2);

        sysUserService.updateSysUser("2","3", "1", 1, null, SysUserStatusEnum.ENABLED, "1");

        final List<SysUser> sysUserList = sysUserMapper.selectList(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUsername, "3"));

        assertThat(sysUserList)
                .isNotNull()
                .hasSize(1);
    }

    @Test
    void updateSysUserLastLoginDateTime() {

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

        final LocalDateTime lastLoginDateTime = LocalDateTime.now();
        sysUserService.updateSysUserLastLoginDateTime("1", lastLoginDateTime);

        final SysUser sysUser = sysUserMapper.selectById("1");

        assertThat(sysUser).isNotNull();
        assertThat(sysUser.getLastLoginDateTime()).isEqualTo(lastLoginDateTime);
    }

    @Test
    void setSysOrganizationOfSysUser() {
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
        final SysUser sysUser2 = new SysUser();
        sysUser2.setId("2");
        sysUser2.setUsername("2");
        sysUser2.setPassword("2");
        sysUser2.setLevel(1);
        sysUser2.setLastLoginDateTime(LocalDateTime.now());
        sysUser2.setRemark("test");
        sysUser2.setStatus(SysUserStatusEnum.ENABLED);
        sysUser2.setUpdatedSysUserId("");
        sysUser2.setCreatedSysUserId("");
        sysUser2.setUpdatedDateTime(LocalDateTime.now());
        sysUser2.setCreatedDateTime(LocalDateTime.now());
        sysUserMapper.insert(sysUser2);

        final SysOrganization sysOrganization1 = new SysOrganization();
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

        sysUserService.setSysOrganizationOfSysUser("2", Arrays.asList("1"), "1");
        
        final List<SysOrganization> sysOrganizationOfSysUser = sysUserService.getSysOrganizationOfSysUser("2");

        assertThat(sysOrganizationOfSysUser)
                .isNotNull()
                .hasSize(1)
                .usingElementComparator(Comparator.comparing(SysOrganization::getId))
                .contains(sysOrganization1);
    }

    @Test
    void setSysRoleOfSysUser() {
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
        final SysUser sysUser2 = new SysUser();
        sysUser2.setId("2");
        sysUser2.setUsername("2");
        sysUser2.setPassword("2");
        sysUser2.setLevel(1);
        sysUser2.setLastLoginDateTime(LocalDateTime.now());
        sysUser2.setRemark("test");
        sysUser2.setStatus(SysUserStatusEnum.ENABLED);
        sysUser2.setUpdatedSysUserId("");
        sysUser2.setCreatedSysUserId("");
        sysUser2.setUpdatedDateTime(LocalDateTime.now());
        sysUser2.setCreatedDateTime(LocalDateTime.now());
        sysUserMapper.insert(sysUser2);

        final SysRole sysRole1 = new SysRole();
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

        sysUserService.setSysRoleOfSysUser("2", Arrays.asList("1"), "1");
        
        final List<SysRole> sysRoleOfSysUser = sysUserService.getSysRoleOfSysUser("2");

        assertThat(sysRoleOfSysUser)
                .isNotNull()
                .hasSize(1)
                .usingElementComparator(Comparator.comparing(SysRole::getId))
                .contains(sysRole1);
    }

    @Test
    void deleteSysUser() {
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
        
        final SysUser sysUser2 = new SysUser();
        sysUser2.setId("2");
        sysUser2.setUsername("2");
        sysUser2.setPassword("2");
        sysUser2.setLevel(1);
        sysUser2.setLastLoginDateTime(LocalDateTime.now());
        sysUser2.setRemark("test");
        sysUser2.setStatus(SysUserStatusEnum.ENABLED);
        sysUser2.setUpdatedSysUserId("");
        sysUser2.setCreatedSysUserId("");
        sysUser2.setUpdatedDateTime(LocalDateTime.now());
        sysUser2.setCreatedDateTime(LocalDateTime.now());
        sysUserMapper.insert(sysUser2);

        sysUserService.deleteSysUser("2", "1");

        final SysUser sysUser = sysUserMapper.selectById("2");
        
        assertThat(sysUser).isNull();
    }

    @Test
    void enabledSysUser() {
        final SysUser sysUser1 = new SysUser();
        sysUser1.setId("1");
        sysUser1.setUsername("chen");
        sysUser1.setPassword("chen");
        sysUser1.setLevel(0);
        sysUser1.setLastLoginDateTime(LocalDateTime.now());
        sysUser1.setRemark("test");
        sysUser1.setStatus(SysUserStatusEnum.DISABLED);
        sysUser1.setUpdatedSysUserId("");
        sysUser1.setCreatedSysUserId("");
        sysUser1.setUpdatedDateTime(LocalDateTime.now());
        sysUser1.setCreatedDateTime(LocalDateTime.now());
        sysUserMapper.insert(sysUser1);     
        final SysUser sysUser2 = new SysUser();
        sysUser2.setId("2");
        sysUser2.setUsername("2");
        sysUser2.setPassword("2");
        sysUser2.setLevel(1);
        sysUser2.setLastLoginDateTime(LocalDateTime.now());
        sysUser2.setRemark("test");
        sysUser2.setStatus(SysUserStatusEnum.DISABLED);
        sysUser2.setUpdatedSysUserId("");
        sysUser2.setCreatedSysUserId("");
        sysUser2.setUpdatedDateTime(LocalDateTime.now());
        sysUser2.setCreatedDateTime(LocalDateTime.now());
        sysUserMapper.insert(sysUser2);

        sysUserService.enabledSysUser("2", "1");

        final SysUser sysUser = sysUserMapper.selectById("2");

        assertThat(sysUser).isNotNull();
        assertThat(sysUser.getStatus()).isEqualByComparingTo(SysUserStatusEnum.ENABLED);
    }

    @Test
    void disableSysUser() {
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
        final SysUser sysUser2 = new SysUser();
        sysUser2.setId("2");
        sysUser2.setUsername("2");
        sysUser2.setPassword("2");
        sysUser2.setLevel(1);
        sysUser2.setLastLoginDateTime(LocalDateTime.now());
        sysUser2.setRemark("test");
        sysUser2.setStatus(SysUserStatusEnum.ENABLED);
        sysUser2.setUpdatedSysUserId("");
        sysUser2.setCreatedSysUserId("");
        sysUser2.setUpdatedDateTime(LocalDateTime.now());
        sysUser2.setCreatedDateTime(LocalDateTime.now());
        sysUserMapper.insert(sysUser2);

        sysUserService.disableSysUser("2", "1");

        final SysUser sysUser = sysUserMapper.selectById("2");

        assertThat(sysUser).isNotNull();
        assertThat(sysUser.getStatus()).isEqualByComparingTo(SysUserStatusEnum.DISABLED);
    }

    @Test
    void lockSysUser() {
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
        final SysUser sysUser2 = new SysUser();
        sysUser2.setId("2");
        sysUser2.setUsername("2");
        sysUser2.setPassword("2");
        sysUser2.setLevel(1);
        sysUser2.setLastLoginDateTime(LocalDateTime.now());
        sysUser2.setRemark("test");
        sysUser2.setStatus(SysUserStatusEnum.ENABLED);
        sysUser2.setUpdatedSysUserId("");
        sysUser2.setCreatedSysUserId("");
        sysUser2.setUpdatedDateTime(LocalDateTime.now());
        sysUser2.setCreatedDateTime(LocalDateTime.now());
        sysUserMapper.insert(sysUser2);

        sysUserService.lockSysUser("2", "1");

        final SysUser sysUser = sysUserMapper.selectById("2");

        assertThat(sysUser).isNotNull();
        assertThat(sysUser.getStatus()).isEqualByComparingTo(SysUserStatusEnum.LOCKED);
    }

    @Test
    void expireSysUser() {
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
        final SysUser sysUser2 = new SysUser();
        sysUser2.setId("2");
        sysUser2.setUsername("2");
        sysUser2.setPassword("2");
        sysUser2.setLevel(1);
        sysUser2.setLastLoginDateTime(LocalDateTime.now());
        sysUser2.setRemark("test");
        sysUser2.setStatus(SysUserStatusEnum.ENABLED);
        sysUser2.setUpdatedSysUserId("");
        sysUser2.setCreatedSysUserId("");
        sysUser2.setUpdatedDateTime(LocalDateTime.now());
        sysUser2.setCreatedDateTime(LocalDateTime.now());
        sysUserMapper.insert(sysUser2);

        sysUserService.expireSysUser("2", "1");

        final SysUser sysUser = sysUserMapper.selectById("2");

        assertThat(sysUser).isNotNull();
        assertThat(sysUser.getStatus()).isEqualByComparingTo(SysUserStatusEnum.EXPIRED);
    }
}