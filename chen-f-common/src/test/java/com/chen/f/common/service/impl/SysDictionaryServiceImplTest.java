package com.chen.f.common.service.impl;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.chen.f.common.pojo.SysUser;
import com.chen.f.common.pojo.enums.SysDictionaryTypeEnum;

import java.sql.Wrapper;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import com.chen.f.common.pojo.enums.StatusEnum;

import com.baomidou.mybatisplus.test.autoconfigure.AutoConfigureMybatisPlus;
import com.chen.f.common.configuration.EnableChenFCommonConfiguration;
import com.chen.f.common.mapper.SysApiMapper;
import com.chen.f.common.mapper.SysDictionaryMapper;
import com.chen.f.common.mapper.SysUserMapper;
import com.chen.f.common.pojo.SysDictionary;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


/**
 * @author chen
 * @since 2020/9/8 10:33
 */
@SpringBootTest(classes = {SysDictionaryServiceImpl.class,})
@Transactional
@AutoConfigureMybatisPlus
@AutoConfigureTestDatabase
@ImportAutoConfiguration({
        EnableChenFCommonConfiguration.class,
        EnableChenFMybatisPlusConfiguration.class,
})
//@Sql("classpath:db/schema-h2.sql")
class SysDictionaryServiceImplTest {

    @Autowired
    private SysDictionaryServiceImpl sysDictionaryService;

    @Autowired
    private SysDictionaryMapper sysDictionaryMapper;

    @Autowired
    private SysUserMapper sysUserMapper;


    @BeforeEach
    void setUp() {
    }

    @Test
    void getSysDictionaryPage() {
        SysDictionary sysDictionary1 = new SysDictionary();
        sysDictionary1.setId("1");
        sysDictionary1.setCode("1");
        sysDictionary1.setName("");
        sysDictionary1.setType(SysDictionaryTypeEnum.DB_TABLE_COLUMN);
        sysDictionary1.setRemark("");
        sysDictionary1.setStatus(StatusEnum.ENABLED);
        sysDictionary1.setUpdatedSysUserId("");
        sysDictionary1.setCreatedSysUserId("");
        sysDictionary1.setUpdatedDateTime(LocalDateTime.now());
        sysDictionary1.setCreatedDateTime(LocalDateTime.now());
        sysDictionaryMapper.insert(sysDictionary1);

        Page<SysDictionary> sysDictionaryPage = sysDictionaryService.getSysDictionaryPage(new Page<>(), null, null, null, null, null);

        assertThat(sysDictionaryPage).isNotNull();
        assertThat(sysDictionaryPage.getTotal()).isEqualTo(1);
        assertThat(sysDictionaryPage.getList())
                .isNotNull()
                .hasSize(1)
                .usingElementComparator(Comparator.comparing(SysDictionary::getId))
                .contains(sysDictionary1);

    }

    @Test
    void getEnabledSysDictionaryList() {
        SysDictionary sysDictionary1 = new SysDictionary();
        sysDictionary1.setId("1");
        sysDictionary1.setCode("1");
        sysDictionary1.setName("");
        sysDictionary1.setType(SysDictionaryTypeEnum.DB_TABLE_COLUMN);
        sysDictionary1.setRemark("");
        sysDictionary1.setStatus(StatusEnum.ENABLED);
        sysDictionary1.setUpdatedSysUserId("");
        sysDictionary1.setCreatedSysUserId("");
        sysDictionary1.setUpdatedDateTime(LocalDateTime.now());
        sysDictionary1.setCreatedDateTime(LocalDateTime.now());
        sysDictionaryMapper.insert(sysDictionary1);

        List<SysDictionary> enabledSysDictionaryList = sysDictionaryService.getEnabledSysDictionaryList();

        assertThat(enabledSysDictionaryList)
                .isNotNull()
                .hasSize(1)
                .usingElementComparator(Comparator.comparing(SysDictionary::getId))
                .contains(sysDictionary1);

    }

    @Test
    void getSysDictionary() {
        SysDictionary sysDictionary1 = new SysDictionary();
        sysDictionary1.setId("1");
        sysDictionary1.setCode("1");
        sysDictionary1.setName("");
        sysDictionary1.setType(SysDictionaryTypeEnum.DB_TABLE_COLUMN);
        sysDictionary1.setRemark("");
        sysDictionary1.setStatus(StatusEnum.ENABLED);
        sysDictionary1.setUpdatedSysUserId("");
        sysDictionary1.setCreatedSysUserId("");
        sysDictionary1.setUpdatedDateTime(LocalDateTime.now());
        sysDictionary1.setCreatedDateTime(LocalDateTime.now());
        sysDictionaryMapper.insert(sysDictionary1);

        SysDictionary sysDictionary = sysDictionaryService.getSysDictionary("1");

        assertThat(sysDictionary)
                .isNotNull()
                .usingComparator(Comparator.comparing(SysDictionary::getId))
                .isEqualTo(sysDictionary1);

    }

    @Test
    void getSysDictionaryByCode() {
        SysDictionary sysDictionary1 = new SysDictionary();
        sysDictionary1.setId("1");
        sysDictionary1.setCode("1");
        sysDictionary1.setName("");
        sysDictionary1.setType(SysDictionaryTypeEnum.DB_TABLE_COLUMN);
        sysDictionary1.setRemark("");
        sysDictionary1.setStatus(StatusEnum.ENABLED);
        sysDictionary1.setUpdatedSysUserId("");
        sysDictionary1.setCreatedSysUserId("");
        sysDictionary1.setUpdatedDateTime(LocalDateTime.now());
        sysDictionary1.setCreatedDateTime(LocalDateTime.now());
        sysDictionaryMapper.insert(sysDictionary1);

        SysDictionary sysDictionary = sysDictionaryService.getSysDictionaryByCode("1");

        assertThat(sysDictionary)
                .isNotNull()
                .usingComparator(Comparator.comparing(SysDictionary::getCode))
                .isEqualTo(sysDictionary1);
    }

    @Test
    void createSysDictionary() {
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

        sysDictionaryService.createSysDictionary("1", "1", SysDictionaryTypeEnum.DB_TABLE_COLUMN, null, StatusEnum.ENABLED, "1");

        List<SysDictionary> sysDictionaryList = sysDictionaryMapper.selectList(Wrappers.<SysDictionary>lambdaQuery()
                .eq(SysDictionary::getCode, "1"));

        assertThat(sysDictionaryList)
                .isNotNull()
                .hasSize(1);
    }

    @Test
    void updateSysDictionary() {
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

        SysDictionary sysDictionary1 = new SysDictionary();
        sysDictionary1.setId("1");
        sysDictionary1.setCode("1");
        sysDictionary1.setName("");
        sysDictionary1.setType(SysDictionaryTypeEnum.DB_TABLE_COLUMN);
        sysDictionary1.setRemark("");
        sysDictionary1.setStatus(StatusEnum.ENABLED);
        sysDictionary1.setUpdatedSysUserId("");
        sysDictionary1.setCreatedSysUserId("");
        sysDictionary1.setUpdatedDateTime(LocalDateTime.now());
        sysDictionary1.setCreatedDateTime(LocalDateTime.now());
        sysDictionaryMapper.insert(sysDictionary1);

        sysDictionaryService.updateSysDictionary("1","2", "1", SysDictionaryTypeEnum.DB_TABLE_COLUMN, null, StatusEnum.ENABLED, "1");

        List<SysDictionary> sysDictionaryList = sysDictionaryMapper.selectList(Wrappers.<SysDictionary>lambdaQuery()
                .eq(SysDictionary::getCode, "2"));

        assertThat(sysDictionaryList)
                .isNotNull()
                .hasSize(1);
    }

    @Test
    void deleteSysDictionary() {
        SysDictionary sysDictionary1 = new SysDictionary();
        sysDictionary1.setId("1");
        sysDictionary1.setCode("1");
        sysDictionary1.setName("");
        sysDictionary1.setType(SysDictionaryTypeEnum.DB_TABLE_COLUMN);
        sysDictionary1.setRemark("");
        sysDictionary1.setStatus(StatusEnum.ENABLED);
        sysDictionary1.setUpdatedSysUserId("");
        sysDictionary1.setCreatedSysUserId("");
        sysDictionary1.setUpdatedDateTime(LocalDateTime.now());
        sysDictionary1.setCreatedDateTime(LocalDateTime.now());
        sysDictionaryMapper.insert(sysDictionary1);

        sysDictionaryService.deleteSysDictionary("1");

        SysDictionary sysDictionary = sysDictionaryMapper.selectById("1");

        assertThat(sysDictionary).isNull();
    }

    @Test
    void deleteSysDictionaryByCode() {
        SysDictionary sysDictionary1 = new SysDictionary();
        sysDictionary1.setId("1");
        sysDictionary1.setCode("1");
        sysDictionary1.setName("");
        sysDictionary1.setType(SysDictionaryTypeEnum.DB_TABLE_COLUMN);
        sysDictionary1.setRemark("");
        sysDictionary1.setStatus(StatusEnum.ENABLED);
        sysDictionary1.setUpdatedSysUserId("");
        sysDictionary1.setCreatedSysUserId("");
        sysDictionary1.setUpdatedDateTime(LocalDateTime.now());
        sysDictionary1.setCreatedDateTime(LocalDateTime.now());
        sysDictionaryMapper.insert(sysDictionary1);

        sysDictionaryService.deleteSysDictionaryByCode("1");

        SysDictionary sysDictionary = sysDictionaryMapper.selectOne(Wrappers.<SysDictionary>lambdaQuery()
                .eq(SysDictionary::getCode, "1"));

        assertThat(sysDictionary).isNull();
    }

    @Test
    void enabledSysDictionary() {
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

        SysDictionary sysDictionary1 = new SysDictionary();
        sysDictionary1.setId("1");
        sysDictionary1.setCode("1");
        sysDictionary1.setName("");
        sysDictionary1.setType(SysDictionaryTypeEnum.DB_TABLE_COLUMN);
        sysDictionary1.setRemark("");
        sysDictionary1.setStatus(StatusEnum.DISABLED);
        sysDictionary1.setUpdatedSysUserId("");
        sysDictionary1.setCreatedSysUserId("");
        sysDictionary1.setUpdatedDateTime(LocalDateTime.now());
        sysDictionary1.setCreatedDateTime(LocalDateTime.now());
        sysDictionaryMapper.insert(sysDictionary1);

        sysDictionaryService.enabledSysDictionary("1", "1");

        SysDictionary sysDictionary = sysDictionaryMapper.selectById("1");

        assertThat(sysDictionary).isNotNull();
        assertThat(sysDictionary.getStatus()).isEqualByComparingTo(StatusEnum.ENABLED);
    }

    @Test
    void disableSysDictionary() {
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

        SysDictionary sysDictionary1 = new SysDictionary();
        sysDictionary1.setId("1");
        sysDictionary1.setCode("1");
        sysDictionary1.setName("");
        sysDictionary1.setType(SysDictionaryTypeEnum.DB_TABLE_COLUMN);
        sysDictionary1.setRemark("");
        sysDictionary1.setStatus(StatusEnum.ENABLED);
        sysDictionary1.setUpdatedSysUserId("");
        sysDictionary1.setCreatedSysUserId("");
        sysDictionary1.setUpdatedDateTime(LocalDateTime.now());
        sysDictionary1.setCreatedDateTime(LocalDateTime.now());
        sysDictionaryMapper.insert(sysDictionary1);

        sysDictionaryService.disableSysDictionary("1","1");

        SysDictionary sysDictionary = sysDictionaryMapper.selectOne(Wrappers.<SysDictionary>lambdaQuery()
                .eq(SysDictionary::getCode, "1"));

        assertThat(sysDictionary).isNotNull();
        assertThat(sysDictionary.getStatus()).isEqualByComparingTo(StatusEnum.DISABLED);
    }
}
