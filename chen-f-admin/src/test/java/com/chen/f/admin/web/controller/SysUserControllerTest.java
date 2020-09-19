package com.chen.f.admin.web.controller;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.chen.f.admin.test.ChenFWithMockUser;
import com.chen.f.admin.web.dto.SysOrganizationsInputDTO;
import com.chen.f.admin.web.dto.SysRolesInputDTO;
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
import com.chen.f.core.configuration.redis.AutoConfigureTestRedis;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author chen
 * @since 2020/9/19 19:23.
 */
@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@AutoConfigureTestRedis
@AutoConfigureTestDatabase
class SysUserControllerTest {
    @Autowired
    private MockMvc mvc;

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
    
    
    @Autowired
    private ObjectMapper objectMapper;


    @BeforeEach
    void setUp() {
    }

    @Test
    @WithMockUser(value = "chen")
    void getAllSysUserList() throws Exception {
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

        mvc.perform(
                get("/chen/admin/sys/user/all")
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf())
        )
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(hasSize(1)))
                .andExpect(jsonPath("$[*].id").value(anything("1")));

    }

    @Test
    @WithMockUser(value = "chen")
    void getEnabledSysOrganizationList() throws Exception {
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

        mvc.perform(
                get("/chen/admin/sys/user/all/enabled")
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf())
        )
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(hasSize(1)))
                .andExpect(jsonPath("$[*].id").value(anything("1")));

    }

    @Test
    @WithMockUser(value = "chen")
    void getSysUserPage() throws Exception {
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

        mvc.perform(
                get("/chen/admin/sys/user")
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf())
        )
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(notNullValue()))
                .andExpect(jsonPath("$.total").value(is(1)))
                .andExpect(jsonPath("$.list").value(hasSize(1)))
                .andExpect(jsonPath("$.list[*].id").value(anything("1")));


    }

    @Test
    @WithMockUser(value = "chen")
    void getSysUser() throws Exception {

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

        mvc.perform(
                get("/chen/admin/sys/user/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf())
        )
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(notNullValue()))
                .andExpect(jsonPath("$.id").value(is("1")));

    }

    @Test
    @WithMockUser(value = "chen")
    void getSysOrganizationOfSysUser() throws Exception {

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

        mvc.perform(
                get("/chen/admin/sys/user/1/sysOrganization")
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf())
        )
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(notNullValue()))
                .andExpect(jsonPath("$").value(hasSize(1)))
                .andExpect(jsonPath("$[*].id").value(anything("1")));

    }


    @Test
    @WithMockUser(value = "chen")
    void getRoleOfSysUser() throws Exception {
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

        mvc.perform(
                get("/chen/admin/sys/user/1/sysRole")
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf())
        )
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(notNullValue()))
                .andExpect(jsonPath("$").value(hasSize(1)))
                .andExpect(jsonPath("$[*].id").value(anything("1")));
    }

    @Test
    @ChenFWithMockUser(value = "chen")
    void createSysUser() throws Exception {
        mvc.perform(
                post("/chen/admin/sys/user")
                        .param("username", "1")
                        .param("password", "1")
                        .param("remark", "1")
                        .param("status", "ENABLED")
                        .param("level", "1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf())
        )
                .andExpect(status().isOk());
        
        final List<SysUser> sysUserList = sysUserMapper.selectList(Wrappers.<SysUser>lambdaQuery()
                .eq(SysUser::getUsername, "1"));

        assertThat(sysUserList)
                .isNotNull()
                .hasSize(1);
    }

    @Test
    @ChenFWithMockUser(value = "chen")
    void testCreateSysUser() throws Exception {

        final SysUser sysUser1 = new SysUser();
        sysUser1.setId("1");
        sysUser1.setUsername("1");
        sysUser1.setPassword("1");
        sysUser1.setLevel(0);
        sysUser1.setLastLoginDateTime(LocalDateTime.now());
        sysUser1.setRemark("1");
        sysUser1.setStatus(SysUserStatusEnum.ENABLED);
        sysUser1.setUpdatedSysUserId("");
        sysUser1.setCreatedSysUserId("");
        sysUser1.setUpdatedDateTime(LocalDateTime.now());
        sysUser1.setCreatedDateTime(LocalDateTime.now());
        
        mvc.perform(
                post("/chen/admin/sys/user")
                        .content(objectMapper.writeValueAsString(sysUser1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf())
        )
                .andExpect(status().isOk());

        final List<SysUser> sysUserList = sysUserMapper.selectList(Wrappers.<SysUser>lambdaQuery()
                .eq(SysUser::getUsername, "1"));

        assertThat(sysUserList)
                .isNotNull()
                .hasSize(1);
    }

    @Test
    @ChenFWithMockUser(value = "chen")
    void updateSysUser() throws Exception {
        final SysUser sysUser1 = new SysUser();
        sysUser1.setId("1");
        sysUser1.setUsername("1");
        sysUser1.setPassword("1");
        sysUser1.setLevel(1);
        sysUser1.setLastLoginDateTime(LocalDateTime.now());
        sysUser1.setRemark("test");
        sysUser1.setStatus(SysUserStatusEnum.ENABLED);
        sysUser1.setUpdatedSysUserId("");
        sysUser1.setCreatedSysUserId("");
        sysUser1.setUpdatedDateTime(LocalDateTime.now());
        sysUser1.setCreatedDateTime(LocalDateTime.now());
        sysUserMapper.insert(sysUser1);
        
        mvc.perform(
            put("/chen/admin/sys/user/1")
                    .param("id", "1")
                    .param("username", "2")
                    .param("password", "2")
                    .param("remark", "1")
                    .param("status", "ENABLED")
                    .param("level", "1")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .accept(MediaType.APPLICATION_JSON)
                    .with(csrf())
    )
            .andExpect(status().isOk()); 
        
        final List<SysUser> sysUserList = sysUserMapper.selectList(Wrappers.<SysUser>lambdaQuery()
                .eq(SysUser::getUsername, "2"));

        assertThat(sysUserList)
                .isNotNull()
                .hasSize(1);
    }

    @Test
    @ChenFWithMockUser(value = "chen")
    void testUpdateSysUser() throws Exception {

        final SysUser sysUser1 = new SysUser();
        sysUser1.setId("1");
        sysUser1.setUsername("1");
        sysUser1.setPassword("1");
        sysUser1.setLevel(1);
        sysUser1.setLastLoginDateTime(LocalDateTime.now());
        sysUser1.setRemark("1");
        sysUser1.setStatus(SysUserStatusEnum.ENABLED);
        sysUser1.setUpdatedSysUserId("");
        sysUser1.setCreatedSysUserId("");
        sysUser1.setUpdatedDateTime(LocalDateTime.now());
        sysUser1.setCreatedDateTime(LocalDateTime.now());
        sysUserMapper.insert(sysUser1);

        sysUser1.setUsername("2");

        mvc.perform(
                put("/chen/admin/sys/user/1")
                        .content(objectMapper.writeValueAsString(sysUser1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf())
        )
                .andExpect(status().isOk());
        
        final List<SysUser> sysUserList = sysUserMapper.selectList(Wrappers.<SysUser>lambdaQuery()
                .eq(SysUser::getUsername, "2"));

        assertThat(sysUserList)
                .isNotNull()
                .hasSize(1);
    }

    @Test
    @ChenFWithMockUser(value = "chen")
    void setSysOrganizationOfSysUser() throws Exception {
        final SysUser sysUser1 = new SysUser();
        sysUser1.setId("1");
        sysUser1.setUsername("1");
        sysUser1.setPassword("1");
        sysUser1.setLevel(1);
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

        final SysOrganizationsInputDTO sysOrganizationsInputDTO = new SysOrganizationsInputDTO();
        sysOrganizationsInputDTO.setSysOrganizationIdList(Arrays.asList("1"));
        mvc.perform(
                put("/chen/admin/sys/user/1/setSysOrganization")
                        .content(objectMapper.writeValueAsString(sysOrganizationsInputDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf())
        )
                .andExpect(status().isOk());


        mvc.perform(
                get("/chen/admin/sys/user/1/sysOrganization")
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf())
        )
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(notNullValue()))
                .andExpect(jsonPath("$").value(hasSize(1)))
                .andExpect(jsonPath("$[*].id").value(anything("1")));

    }

    @Test
    @ChenFWithMockUser(value = "chen")
    void setSysRoleOfSysUser() throws Exception {
        final SysUser sysUser1 = new SysUser();
        sysUser1.setId("1");
        sysUser1.setUsername("1");
        sysUser1.setPassword("1");
        sysUser1.setLevel(1);
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

        final SysRolesInputDTO sysRolesInputDTO = new SysRolesInputDTO();
        sysRolesInputDTO.setSysRoleIdList(Arrays.asList("1"));
        mvc.perform(
                put("/chen/admin/sys/user/1/setSysRole")
                        .content(objectMapper.writeValueAsString(sysRolesInputDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf())
        )
                .andExpect(status().isOk());

        mvc.perform(
                get("/chen/admin/sys/user/1/sysRole")
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf())
        )
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(notNullValue()))
                .andExpect(jsonPath("$").value(hasSize(1)))
                .andExpect(jsonPath("$[*].id").value(anything("1")));
    }

    @Test
    @ChenFWithMockUser(value = "chen")
    void deleteSysUser() throws Exception {
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

        mvc.perform(
                delete("/chen/admin/sys/user/2")
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf())
        )
                .andExpect(status().isOk());

        final SysUser sysUser = sysUserMapper.selectById("2");

        assertThat(sysUser).isNull();
    }

    @Test
    @ChenFWithMockUser(value = "chen")
    void enabledSysUser() throws Exception {
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

        mvc.perform(
                post("/chen/admin/sys/user/2/enable")
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf())
        )
                .andExpect(status().isOk());
        
        final SysUser sysUser = sysUserMapper.selectById("2");

        assertThat(sysUser).isNotNull();
        assertThat(sysUser.getStatus()).isEqualByComparingTo(SysUserStatusEnum.ENABLED);
    }

    @Test
    @ChenFWithMockUser(value = "chen")
    void disableSysUser() throws Exception {

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
        
        mvc.perform(
                post("/chen/admin/sys/user/2/disable")
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf())
        )
                .andExpect(status().isOk());
        
        final SysUser sysUser = sysUserMapper.selectById("2");

        assertThat(sysUser).isNotNull();
        assertThat(sysUser.getStatus()).isEqualByComparingTo(SysUserStatusEnum.DISABLED);
    }

    @Test
    @ChenFWithMockUser(value = "chen")
    void lockSysUser() throws Exception {
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

        mvc.perform(
                post("/chen/admin/sys/user/2/lock")
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf())
        )
                .andExpect(status().isOk());

        final SysUser sysUser = sysUserMapper.selectById("2");

        assertThat(sysUser).isNotNull();
        assertThat(sysUser.getStatus()).isEqualByComparingTo(SysUserStatusEnum.LOCKED);
    }

    @Test
    @ChenFWithMockUser(value = "chen")
    void expireSysUser() throws Exception {

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
        
        mvc.perform(
                post("/chen/admin/sys/user/2/expire")
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf())
        )
                .andExpect(status().isOk());

        final SysUser sysUser = sysUserMapper.selectById("2");

        assertThat(sysUser).isNotNull();
        assertThat(sysUser.getStatus()).isEqualByComparingTo(SysUserStatusEnum.EXPIRED);
        
    }
}

