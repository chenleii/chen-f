package com.chen.f.common.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.test.autoconfigure.AutoConfigureMybatisPlus;
import com.chen.f.common.configuration.EnableChenFCommonConfiguration;
import com.chen.f.common.mapper.SysParameterMapper;
import com.chen.f.common.mapper.SysUserMapper;
import com.chen.f.common.pojo.SysParameter;
import com.chen.f.common.pojo.SysUser;
import com.chen.f.common.pojo.enums.StatusEnum;
import com.chen.f.common.pojo.enums.SysParameterTypeEnum;
import com.chen.f.common.pojo.enums.SysUserStatusEnum;
import com.chen.f.common.pojo.enums.TypeTypeEnum;
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
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author chen
 * @since 2020/9/8 14:13
 */
@SpringBootTest(classes = {SysParameterServiceImpl.class,})
@Transactional
@AutoConfigureMybatisPlus
@AutoConfigureTestDatabase
@ImportAutoConfiguration({
        EnableChenFCommonConfiguration.class,
        EnableChenFMybatisPlusConfiguration.class,
})
//@Sql("classpath:db/schema-h2.sql")
class SysParameterServiceImplTest {

    @Autowired
    private SysParameterServiceImpl sysParameterService;

    @Autowired
    private SysParameterMapper sysParameterMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getEnabledSysParameterList() {
        SysParameter sysParameter1 = new SysParameter();
        sysParameter1.setId("1");
        sysParameter1.setCode("1");
        sysParameter1.setName("1");
        sysParameter1.setValue("1");
        sysParameter1.setValueType(TypeTypeEnum.STRING);
        sysParameter1.setType(SysParameterTypeEnum.SYSTEM);
        sysParameter1.setRemark("");
        sysParameter1.setStatus(StatusEnum.ENABLED);
        sysParameter1.setUpdatedSysUserId("");
        sysParameter1.setCreatedSysUserId("");
        sysParameter1.setUpdatedDateTime(LocalDateTime.now());
        sysParameter1.setCreatedDateTime(LocalDateTime.now());
        sysParameterMapper.insert(sysParameter1);

        List<SysParameter> sysParameterList = sysParameterService.getEnabledSysParameterList();

        assertThat(sysParameterList)
                .isNotNull()
                .hasSize(1)
                .usingElementComparator(Comparator.comparing(SysParameter::getId))
                .contains(sysParameter1);
    }

    @Test
    void getSysParameterPage() {
        SysParameter sysParameter1 = new SysParameter();
        sysParameter1.setId("1");
        sysParameter1.setCode("1");
        sysParameter1.setName("1");
        sysParameter1.setValue("1");
        sysParameter1.setValueType(TypeTypeEnum.STRING);
        sysParameter1.setType(SysParameterTypeEnum.SYSTEM);
        sysParameter1.setRemark("");
        sysParameter1.setStatus(StatusEnum.ENABLED);
        sysParameter1.setUpdatedSysUserId("");
        sysParameter1.setCreatedSysUserId("");
        sysParameter1.setUpdatedDateTime(LocalDateTime.now());
        sysParameter1.setCreatedDateTime(LocalDateTime.now());
        sysParameterMapper.insert(sysParameter1);

        Page<SysParameter> sysParameterPage = sysParameterService.getSysParameterPage(new Page<>(),
                null, null, null, null, null, null, null);

        assertThat(sysParameterPage).isNotNull();
        assertThat(sysParameterPage.getTotal()).isEqualTo(1);
        assertThat(sysParameterPage.getList())
                .isNotNull()
                .hasSize(1)
                .usingElementComparator(Comparator.comparing(SysParameter::getId))
                .contains(sysParameter1);
    }

    @Test
    void getSysParameter() {
        SysParameter sysParameter1 = new SysParameter();
        sysParameter1.setId("1");
        sysParameter1.setCode("1");
        sysParameter1.setName("1");
        sysParameter1.setValue("1");
        sysParameter1.setValueType(TypeTypeEnum.STRING);
        sysParameter1.setType(SysParameterTypeEnum.SYSTEM);
        sysParameter1.setRemark("");
        sysParameter1.setStatus(StatusEnum.ENABLED);
        sysParameter1.setUpdatedSysUserId("");
        sysParameter1.setCreatedSysUserId("");
        sysParameter1.setUpdatedDateTime(LocalDateTime.now());
        sysParameter1.setCreatedDateTime(LocalDateTime.now());
        sysParameterMapper.insert(sysParameter1);

        SysParameter sysParameter = sysParameterService.getSysParameter("1");

        assertThat(sysParameter)
                .isNotNull()
                .usingComparator(Comparator.comparing(SysParameter::getId))
                .isEqualTo(sysParameter1);
    }

    @Test
    void getSysParameterByCode() {
        SysParameter sysParameter1 = new SysParameter();
        sysParameter1.setId("1");
        sysParameter1.setCode("1");
        sysParameter1.setName("1");
        sysParameter1.setValue("1");
        sysParameter1.setValueType(TypeTypeEnum.STRING);
        sysParameter1.setType(SysParameterTypeEnum.SYSTEM);
        sysParameter1.setRemark("");
        sysParameter1.setStatus(StatusEnum.ENABLED);
        sysParameter1.setUpdatedSysUserId("");
        sysParameter1.setCreatedSysUserId("");
        sysParameter1.setUpdatedDateTime(LocalDateTime.now());
        sysParameter1.setCreatedDateTime(LocalDateTime.now());
        sysParameterMapper.insert(sysParameter1);

        SysParameter sysParameter = sysParameterService.getSysParameterByCode("1");

        assertThat(sysParameter)
                .isNotNull()
                .usingComparator(Comparator.comparing(SysParameter::getCode))
                .isEqualTo(sysParameter1);
    }

    @Test
    void createSysParameter() {
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

        sysParameterService.createSysParameter("1", "1", "1", TypeTypeEnum.STRING, SysParameterTypeEnum.SYSTEM, null, StatusEnum.ENABLED, "1");


        List<SysParameter> sysParameterList = sysParameterMapper.selectList(Wrappers.<SysParameter>lambdaQuery()
                .eq(SysParameter::getCode, "1"));

        assertThat(sysParameterList)
                .isNotNull()
                .hasSize(1);
    }

    @Test
    void updateSysParameter() {
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

        SysParameter sysParameter1 = new SysParameter();
        sysParameter1.setId("1");
        sysParameter1.setCode("1");
        sysParameter1.setName("1");
        sysParameter1.setValue("1");
        sysParameter1.setValueType(TypeTypeEnum.STRING);
        sysParameter1.setType(SysParameterTypeEnum.SYSTEM);
        sysParameter1.setRemark("");
        sysParameter1.setStatus(StatusEnum.ENABLED);
        sysParameter1.setUpdatedSysUserId("");
        sysParameter1.setCreatedSysUserId("");
        sysParameter1.setUpdatedDateTime(LocalDateTime.now());
        sysParameter1.setCreatedDateTime(LocalDateTime.now());
        sysParameterMapper.insert(sysParameter1);

        sysParameterService.updateSysParameter("1", "2", "1", "1", TypeTypeEnum.STRING, SysParameterTypeEnum.SYSTEM, null, StatusEnum.ENABLED, "1");


        List<SysParameter> sysParameterList = sysParameterMapper.selectList(Wrappers.<SysParameter>lambdaQuery()
                .eq(SysParameter::getCode, "2"));

        assertThat(sysParameterList)
                .isNotNull()
                .hasSize(1);
    }

    @Test
    void deleteSysParameter() {
        SysParameter sysParameter1 = new SysParameter();
        sysParameter1.setId("1");
        sysParameter1.setCode("1");
        sysParameter1.setName("1");
        sysParameter1.setValue("1");
        sysParameter1.setValueType(TypeTypeEnum.STRING);
        sysParameter1.setType(SysParameterTypeEnum.SYSTEM);
        sysParameter1.setRemark("");
        sysParameter1.setStatus(StatusEnum.ENABLED);
        sysParameter1.setUpdatedSysUserId("");
        sysParameter1.setCreatedSysUserId("");
        sysParameter1.setUpdatedDateTime(LocalDateTime.now());
        sysParameter1.setCreatedDateTime(LocalDateTime.now());
        sysParameterMapper.insert(sysParameter1);

        sysParameterService.deleteSysParameter("1");

        SysParameter sysParameter = sysParameterMapper.selectById("1");

        assertThat(sysParameter).isNull();
    }

    @Test
    void deleteSysParameterByCode() {
        SysParameter sysParameter1 = new SysParameter();
        sysParameter1.setId("1");
        sysParameter1.setCode("1");
        sysParameter1.setName("1");
        sysParameter1.setValue("1");
        sysParameter1.setValueType(TypeTypeEnum.STRING);
        sysParameter1.setType(SysParameterTypeEnum.SYSTEM);
        sysParameter1.setRemark("");
        sysParameter1.setStatus(StatusEnum.ENABLED);
        sysParameter1.setUpdatedSysUserId("");
        sysParameter1.setCreatedSysUserId("");
        sysParameter1.setUpdatedDateTime(LocalDateTime.now());
        sysParameter1.setCreatedDateTime(LocalDateTime.now());
        sysParameterMapper.insert(sysParameter1);

        sysParameterService.deleteSysParameterByCode("1");

        SysParameter sysParameter = sysParameterMapper.selectOne(Wrappers.<SysParameter>lambdaQuery()
                .eq(SysParameter::getCode, "1"));

        assertThat(sysParameter).isNull();
    }

    @Test
    void enabledSysParameter() {
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

        SysParameter sysParameter1 = new SysParameter();
        sysParameter1.setId("1");
        sysParameter1.setCode("1");
        sysParameter1.setName("1");
        sysParameter1.setValue("1");
        sysParameter1.setValueType(TypeTypeEnum.STRING);
        sysParameter1.setType(SysParameterTypeEnum.SYSTEM);
        sysParameter1.setRemark("");
        sysParameter1.setStatus(StatusEnum.DISABLED);
        sysParameter1.setUpdatedSysUserId("");
        sysParameter1.setCreatedSysUserId("");
        sysParameter1.setUpdatedDateTime(LocalDateTime.now());
        sysParameter1.setCreatedDateTime(LocalDateTime.now());
        sysParameterMapper.insert(sysParameter1);

        sysParameterService.enabledSysParameter("1", "1");

        SysParameter sysParameter = sysParameterMapper.selectById("1");

        assertThat(sysParameter).isNotNull();
        assertThat(sysParameter.getStatus()).isEqualByComparingTo(StatusEnum.ENABLED);
    }

    @Test
    void enabledSysParameterByCode() {
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

        SysParameter sysParameter1 = new SysParameter();
        sysParameter1.setId("1");
        sysParameter1.setCode("1");
        sysParameter1.setName("1");
        sysParameter1.setValue("1");
        sysParameter1.setValueType(TypeTypeEnum.STRING);
        sysParameter1.setType(SysParameterTypeEnum.SYSTEM);
        sysParameter1.setRemark("");
        sysParameter1.setStatus(StatusEnum.DISABLED);
        sysParameter1.setUpdatedSysUserId("");
        sysParameter1.setCreatedSysUserId("");
        sysParameter1.setUpdatedDateTime(LocalDateTime.now());
        sysParameter1.setCreatedDateTime(LocalDateTime.now());
        sysParameterMapper.insert(sysParameter1);

        sysParameterService.enabledSysParameterByCode("1", "1");

        SysParameter sysParameter = sysParameterMapper.selectOne(Wrappers.<SysParameter>lambdaQuery()
                .eq(SysParameter::getCode, "1"));

        assertThat(sysParameter).isNotNull();
        assertThat(sysParameter.getStatus()).isEqualByComparingTo(StatusEnum.ENABLED);
    }

    @Test
    void disableSysParameter() {
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

        SysParameter sysParameter1 = new SysParameter();
        sysParameter1.setId("1");
        sysParameter1.setCode("1");
        sysParameter1.setName("1");
        sysParameter1.setValue("1");
        sysParameter1.setValueType(TypeTypeEnum.STRING);
        sysParameter1.setType(SysParameterTypeEnum.SYSTEM);
        sysParameter1.setRemark("");
        sysParameter1.setStatus(StatusEnum.ENABLED);
        sysParameter1.setUpdatedSysUserId("");
        sysParameter1.setCreatedSysUserId("");
        sysParameter1.setUpdatedDateTime(LocalDateTime.now());
        sysParameter1.setCreatedDateTime(LocalDateTime.now());
        sysParameterMapper.insert(sysParameter1);

        sysParameterService.disableSysParameter("1", "1");

        SysParameter sysParameter = sysParameterMapper.selectById("1");

        assertThat(sysParameter).isNotNull();
        assertThat(sysParameter.getStatus()).isEqualByComparingTo(StatusEnum.DISABLED);
    }

    @Test
    void disableSysParameterByCode() {
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

        SysParameter sysParameter1 = new SysParameter();
        sysParameter1.setId("1");
        sysParameter1.setCode("1");
        sysParameter1.setName("1");
        sysParameter1.setValue("1");
        sysParameter1.setValueType(TypeTypeEnum.STRING);
        sysParameter1.setType(SysParameterTypeEnum.SYSTEM);
        sysParameter1.setRemark("");
        sysParameter1.setStatus(StatusEnum.ENABLED);
        sysParameter1.setUpdatedSysUserId("");
        sysParameter1.setCreatedSysUserId("");
        sysParameter1.setUpdatedDateTime(LocalDateTime.now());
        sysParameter1.setCreatedDateTime(LocalDateTime.now());
        sysParameterMapper.insert(sysParameter1);

        sysParameterService.disableSysParameterByCode("1", "1");

        SysParameter sysParameter = sysParameterMapper.selectOne(Wrappers.<SysParameter>lambdaQuery()
                .eq(SysParameter::getCode, "1"));

        assertThat(sysParameter).isNotNull();
        assertThat(sysParameter.getStatus()).isEqualByComparingTo(StatusEnum.DISABLED);
    }
}
