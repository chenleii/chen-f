package com.chen.f.common.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.test.autoconfigure.AutoConfigureMybatisPlus;
import com.chen.f.common.configuration.EnableChenFCommonConfiguration;
import com.chen.f.common.mapper.SysApiMapper;
import com.chen.f.common.mapper.SysPermissionApiMapper;
import com.chen.f.common.mapper.SysRoleApiMapper;
import com.chen.f.common.mapper.SysUserMapper;
import com.chen.f.common.pojo.SysApi;
import com.chen.f.common.pojo.SysUser;
import com.chen.f.common.pojo.enums.StatusEnum;
import com.chen.f.common.pojo.enums.SysApiHttpMethodEnum;
import com.chen.f.common.pojo.enums.SysApiTypeEnum;
import com.chen.f.common.pojo.enums.SysUserStatusEnum;
import com.chen.f.core.configuration.mybatisplus.EnableChenFMybatisPlusConfiguration;
import com.chen.f.core.page.Page;
import org.apache.commons.lang3.StringUtils;
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
 * @since 2020/4/22 22:51.
 */
@SpringBootTest(classes = {SysApiServiceImpl.class,})
@Transactional
@AutoConfigureMybatisPlus
@AutoConfigureTestDatabase
@ImportAutoConfiguration({
        EnableChenFCommonConfiguration.class,
        EnableChenFMybatisPlusConfiguration.class,
})
//@Sql("classpath:db/schema-h2.sql")
class SysApiServiceImplTest {

    @Autowired
    private SysApiServiceImpl sysApiService;

    @Autowired
    private SysApiMapper sysApiMapper;

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysRoleApiMapper sysRoleApiMapper;
    @Autowired
    private SysPermissionApiMapper sysPermissionApiMapper;

    @BeforeEach
    void setUp() {

    }
    
    @Test
    void getAllSysApiList() {
        final SysApi sysApi1 = new SysApi();
        sysApi1.setId("1");
        sysApi1.setName("登陆");
        sysApi1.setUrl("/login");
        sysApi1.setHttpMethod(SysApiHttpMethodEnum.POST);
        sysApi1.setType(SysApiTypeEnum.LOGIN);
        sysApi1.setRemark("登录接口哈");
        sysApi1.setStatus(StatusEnum.ENABLED);
        sysApi1.setUpdatedSysUserId("1");
        sysApi1.setCreatedSysUserId("1");
        sysApi1.setUpdatedDateTime(LocalDateTime.now());
        sysApi1.setCreatedDateTime(LocalDateTime.now());
        sysApiMapper.insert(sysApi1);

        final List<SysApi> sysApiList = sysApiService.getAllSysApiList();

        assertThat(sysApiList)
                .isNotNull()
                .hasSize(1)
                .usingElementComparator(Comparator.comparing(SysApi::getId))
                .contains(sysApi1);
    }
    
    @Test
    void getEnabledSysApiList() {

        final SysApi sysApi1 = new SysApi();
        sysApi1.setId("1");
        sysApi1.setName("登陆");
        sysApi1.setUrl("/login");
        sysApi1.setHttpMethod(SysApiHttpMethodEnum.POST);
        sysApi1.setType(SysApiTypeEnum.LOGIN);
        sysApi1.setRemark("登录接口哈");
        sysApi1.setStatus(StatusEnum.ENABLED);
        sysApi1.setUpdatedSysUserId("1");
        sysApi1.setCreatedSysUserId("1");
        sysApi1.setUpdatedDateTime(LocalDateTime.now());
        sysApi1.setCreatedDateTime(LocalDateTime.now());
        final SysApi sysApi2 = new SysApi();
        sysApi2.setId("2");
        sysApi2.setName("登出");
        sysApi2.setUrl("/logout");
        sysApi2.setHttpMethod(SysApiHttpMethodEnum.POST);
        sysApi2.setType(SysApiTypeEnum.LOGOUT);
        sysApi2.setRemark("登出接口哈");
        sysApi2.setStatus(StatusEnum.ENABLED);
        sysApi2.setUpdatedSysUserId("1");
        sysApi2.setCreatedSysUserId("1");
        sysApi2.setUpdatedDateTime(LocalDateTime.now());
        sysApi2.setCreatedDateTime(LocalDateTime.now());
        sysApiMapper.insertBatch(Arrays.asList(sysApi1, sysApi2));

        final List<SysApi> enabledSysApiList = sysApiService.getEnabledSysApiList();

        assertThat(enabledSysApiList)
                .isNotNull()
                .hasSize(2)
                .usingElementComparator(Comparator.comparing(SysApi::getId))
                .contains(sysApi1, sysApi2);
    }

    @Test
    void getSysApiPage() {
        final SysApi sysApi1 = new SysApi();
        sysApi1.setId("1");
        sysApi1.setName("登陆");
        sysApi1.setUrl("/login");
        sysApi1.setHttpMethod(SysApiHttpMethodEnum.POST);
        sysApi1.setType(SysApiTypeEnum.LOGIN);
        sysApi1.setRemark("登录接口哈");
        sysApi1.setStatus(StatusEnum.ENABLED);
        sysApi1.setUpdatedSysUserId("1");
        sysApi1.setCreatedSysUserId("1");
        sysApi1.setUpdatedDateTime(LocalDateTime.now());
        sysApi1.setCreatedDateTime(LocalDateTime.now());
        sysApiMapper.insert(sysApi1);

        final Page<SysApi> sysApiPage = sysApiService.getSysApiPage(new Page<>(),
                sysApi1.getName(), sysApi1.getUrl() + "*", sysApi1.getHttpMethod(), sysApi1.getType(),
                StringUtils.substring(sysApi1.getRemark(), 1, sysApi1.getRemark().length()), sysApi1.getStatus());

        assertThat(sysApiPage).isNotNull();
        assertThat(sysApiPage.getTotal()).isEqualTo(1);
        assertThat(sysApiPage.getList())
                .isNotNull()
                .hasSize(1)
                .usingElementComparator(Comparator.comparing(SysApi::getId))
                .contains(sysApi1);

    }

   

    @Test
    void getSysApi() {      
        final SysApi sysApi1 = new SysApi();
        sysApi1.setId("1");
        sysApi1.setName("登陆");
        sysApi1.setUrl("/login");
        sysApi1.setHttpMethod(SysApiHttpMethodEnum.POST);
        sysApi1.setType(SysApiTypeEnum.LOGIN);
        sysApi1.setRemark("登录接口哈");
        sysApi1.setStatus(StatusEnum.ENABLED);
        sysApi1.setUpdatedSysUserId("1");
        sysApi1.setCreatedSysUserId("1");
        sysApi1.setUpdatedDateTime(LocalDateTime.now());
        sysApi1.setCreatedDateTime(LocalDateTime.now());
        sysApiMapper.insert(sysApi1);
        
        final SysApi sysApi = sysApiService.getSysApi("1");

        assertThat(sysApi)
                .isNotNull()
                .usingComparator(Comparator.comparing(SysApi::getId))
                .isEqualTo(sysApi1);
    }

    @Test
    void createSysApi() {
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

        sysApiService.createSysApi("1", "/data", SysApiHttpMethodEnum.GET, SysApiTypeEnum.SYSTEM, "test", StatusEnum.ENABLED, "1");
        sysApiService.createSysApi("1", "/data", SysApiHttpMethodEnum.GET, SysApiTypeEnum.SYSTEM, null, StatusEnum.ENABLED, "1");

        final List<SysApi> sysApiList = sysApiMapper.selectList(Wrappers.<SysApi>lambdaQuery().eq(SysApi::getName, "1"));

        assertThat(sysApiList)
                .isNotNull()
                .hasSize(2);

    }

    @Test
    void updateSysApi() {
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
        
        final SysApi sysApi1 = new SysApi();
        sysApi1.setId("1");
        sysApi1.setName("登陆");
        sysApi1.setUrl("/login");
        sysApi1.setHttpMethod(SysApiHttpMethodEnum.POST);
        sysApi1.setType(SysApiTypeEnum.LOGIN);
        sysApi1.setRemark("登录接口哈");
        sysApi1.setStatus(StatusEnum.ENABLED);
        sysApi1.setUpdatedSysUserId("1");
        sysApi1.setCreatedSysUserId("1");
        sysApi1.setUpdatedDateTime(LocalDateTime.now());
        sysApi1.setCreatedDateTime(LocalDateTime.now());
        sysApiMapper.insert(sysApi1);

        sysApiService.updateSysApi("1","1", "/data", SysApiHttpMethodEnum.GET, SysApiTypeEnum.SYSTEM, "test", StatusEnum.ENABLED, "1");
        sysApiService.updateSysApi("1", "1", "/data", SysApiHttpMethodEnum.GET, SysApiTypeEnum.SYSTEM, null, StatusEnum.ENABLED, "1");

        final List<SysApi> sysApiList = sysApiMapper.selectList(Wrappers.<SysApi>lambdaQuery()
                .eq(SysApi::getName, "1")
                .eq(SysApi::getUrl, "/data"));

        assertThat(sysApiList)
                .isNotNull()
                .hasSize(1);
    }

    @Test
    void deleteSysApi() {
        final SysApi sysApi1 = new SysApi();
        sysApi1.setId("1");
        sysApi1.setName("登陆");
        sysApi1.setUrl("/login");
        sysApi1.setHttpMethod(SysApiHttpMethodEnum.POST);
        sysApi1.setType(SysApiTypeEnum.LOGIN);
        sysApi1.setRemark("登录接口哈");
        sysApi1.setStatus(StatusEnum.ENABLED);
        sysApi1.setUpdatedSysUserId("1");
        sysApi1.setCreatedSysUserId("1");
        sysApi1.setUpdatedDateTime(LocalDateTime.now());
        sysApi1.setCreatedDateTime(LocalDateTime.now());
        sysApiMapper.insert(sysApi1);
        
        sysApiService.deleteSysApi("1");

        final SysApi sysApi = sysApiMapper.selectById("1");

        assertThat(sysApi).isNull();
    }

    @Test
    void enabledSysApi() {
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
        
        final SysApi sysApi1 = new SysApi();
        sysApi1.setId("1");
        sysApi1.setName("登陆");
        sysApi1.setUrl("/login");
        sysApi1.setHttpMethod(SysApiHttpMethodEnum.POST);
        sysApi1.setType(SysApiTypeEnum.LOGIN);
        sysApi1.setRemark("登录接口哈");
        sysApi1.setStatus(StatusEnum.DISABLED);
        sysApi1.setUpdatedSysUserId("1");
        sysApi1.setCreatedSysUserId("1");
        sysApi1.setUpdatedDateTime(LocalDateTime.now());
        sysApi1.setCreatedDateTime(LocalDateTime.now());
        sysApiMapper.insert(sysApi1);
        
        sysApiService.enabledSysApi("1", "1");

        final SysApi sysApi = sysApiMapper.selectById("1");

        assertThat(sysApi).isNotNull();
        assertThat(sysApi.getStatus()).isEqualByComparingTo(StatusEnum.ENABLED);
    }

    @Test
    void disableSysApi() {

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

        final SysApi sysApi1 = new SysApi();
        sysApi1.setId("1");
        sysApi1.setName("登陆");
        sysApi1.setUrl("/login");
        sysApi1.setHttpMethod(SysApiHttpMethodEnum.POST);
        sysApi1.setType(SysApiTypeEnum.LOGIN);
        sysApi1.setRemark("登录接口哈");
        sysApi1.setStatus(StatusEnum.ENABLED);
        sysApi1.setUpdatedSysUserId("1");
        sysApi1.setCreatedSysUserId("1");
        sysApi1.setUpdatedDateTime(LocalDateTime.now());
        sysApi1.setCreatedDateTime(LocalDateTime.now());
        sysApiMapper.insert(sysApi1);
        
        sysApiService.disableSysApi("1", "1");


        final SysApi sysApi = sysApiMapper.selectById("1");

        assertThat(sysApi).isNotNull();
        assertThat(sysApi.getStatus()).isEqualByComparingTo(StatusEnum.DISABLED);
    }
}
