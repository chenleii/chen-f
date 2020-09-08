package com.chen.f.common.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.test.autoconfigure.AutoConfigureMybatisPlus;
import com.chen.f.common.configuration.EnableChenFCommonConfiguration;
import com.chen.f.common.mapper.SysDictionaryItemMapper;
import com.chen.f.common.mapper.SysUserMapper;
import com.chen.f.common.pojo.SysDictionaryItem;
import com.chen.f.common.pojo.SysUser;
import com.chen.f.common.pojo.enums.StatusEnum;
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
 * @since 2020/4/25 22:50.
 */
@SpringBootTest(classes = {SysDictionaryItemServiceImpl.class,})
@Transactional
@AutoConfigureMybatisPlus
@AutoConfigureTestDatabase
@ImportAutoConfiguration({
        EnableChenFCommonConfiguration.class,
        EnableChenFMybatisPlusConfiguration.class,
})
//@Sql("classpath:db/schema-h2.sql")
class SysDictionaryItemServiceImplTest {

    @Autowired
    private SysDictionaryItemServiceImpl sysDictionaryItemService;

    @Autowired
    private SysDictionaryItemMapper sysDictionaryItemMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @BeforeEach
    void setUp() {

    }
    

    @Test
    void getSysDictionaryItemPage() {
        final SysDictionaryItem sysDictionaryItem1 = new SysDictionaryItem();
        sysDictionaryItem1.setId("1");
        sysDictionaryItem1.setSysDictionaryId("1");
        sysDictionaryItem1.setCode("1");
        sysDictionaryItem1.setName("");
        sysDictionaryItem1.setKey("1");
        sysDictionaryItem1.setValue("");
        sysDictionaryItem1.setValueI18n("");
        sysDictionaryItem1.setKeyType(TypeTypeEnum.STRING);
        sysDictionaryItem1.setValueType(TypeTypeEnum.STRING);
        sysDictionaryItem1.setColor("");
        sysDictionaryItem1.setOrder(0);
        sysDictionaryItem1.setRemark("");
        sysDictionaryItem1.setStatus(StatusEnum.ENABLED);
        sysDictionaryItem1.setUpdatedSysUserId("");
        sysDictionaryItem1.setCreatedSysUserId("");
        sysDictionaryItem1.setUpdatedDateTime(LocalDateTime.now());
        sysDictionaryItem1.setCreatedDateTime(LocalDateTime.now());
        sysDictionaryItemMapper.insert(sysDictionaryItem1);

        final Page<SysDictionaryItem> sysDictionaryItemPage = sysDictionaryItemService.getSysDictionaryItemPage(new Page<>(), 
                null, null, null, null, null, null, null, null, null);

        assertThat(sysDictionaryItemPage).isNotNull();
        
        assertThat(sysDictionaryItemPage.getTotal()).isEqualTo(1);
        assertThat(sysDictionaryItemPage.getList())
                .isNotNull()
                .hasSize(1)
                .usingElementComparator(Comparator.comparing(SysDictionaryItem::getId))
                .contains(sysDictionaryItem1);
    }

    @Test
    void getEnabledSysDictionaryItemList() {
        final SysDictionaryItem sysDictionaryItem1 = new SysDictionaryItem();
        sysDictionaryItem1.setId("1");
        sysDictionaryItem1.setSysDictionaryId("1");
        sysDictionaryItem1.setCode("1");
        sysDictionaryItem1.setName("");
        sysDictionaryItem1.setKey("1");
        sysDictionaryItem1.setValue("");
        sysDictionaryItem1.setValueI18n("");
        sysDictionaryItem1.setKeyType(TypeTypeEnum.STRING);
        sysDictionaryItem1.setValueType(TypeTypeEnum.STRING);
        sysDictionaryItem1.setColor("");
        sysDictionaryItem1.setOrder(0);
        sysDictionaryItem1.setRemark("");
        sysDictionaryItem1.setStatus(StatusEnum.ENABLED);
        sysDictionaryItem1.setUpdatedSysUserId("");
        sysDictionaryItem1.setCreatedSysUserId("");
        sysDictionaryItem1.setUpdatedDateTime(LocalDateTime.now());
        sysDictionaryItem1.setCreatedDateTime(LocalDateTime.now());
        sysDictionaryItemMapper.insert(sysDictionaryItem1);

        final List<SysDictionaryItem> enabledSysDictionaryItemList = sysDictionaryItemService.getEnabledSysDictionaryItemList();
        
        assertThat(enabledSysDictionaryItemList)
                .isNotNull()
                .hasSize(1)
                .usingElementComparator(Comparator.comparing(SysDictionaryItem::getId))
                .contains(sysDictionaryItem1);
    }

    @Test
    void getSysDictionaryItem() {
        final SysDictionaryItem sysDictionaryItem1 = new SysDictionaryItem();
        sysDictionaryItem1.setId("1");
        sysDictionaryItem1.setSysDictionaryId("1");
        sysDictionaryItem1.setCode("1");
        sysDictionaryItem1.setName("");
        sysDictionaryItem1.setKey("1");
        sysDictionaryItem1.setValue("");
        sysDictionaryItem1.setValueI18n("");
        sysDictionaryItem1.setKeyType(TypeTypeEnum.STRING);
        sysDictionaryItem1.setValueType(TypeTypeEnum.STRING);
        sysDictionaryItem1.setColor("");
        sysDictionaryItem1.setOrder(0);
        sysDictionaryItem1.setRemark("");
        sysDictionaryItem1.setStatus(StatusEnum.ENABLED);
        sysDictionaryItem1.setUpdatedSysUserId("");
        sysDictionaryItem1.setCreatedSysUserId("");
        sysDictionaryItem1.setUpdatedDateTime(LocalDateTime.now());
        sysDictionaryItem1.setCreatedDateTime(LocalDateTime.now());
        sysDictionaryItemMapper.insert(sysDictionaryItem1);

        final SysDictionaryItem sysDictionaryItem = sysDictionaryItemService.getSysDictionaryItem("1");

        assertThat(sysDictionaryItem)
                .isNotNull()   
                .usingComparator(Comparator.comparing(SysDictionaryItem::getId))
                .isEqualTo(sysDictionaryItem1);
    
    }

    @Test
    void getSysDictionaryItemListBySysDictionaryId() {
        final SysDictionaryItem sysDictionaryItem1 = new SysDictionaryItem();
        sysDictionaryItem1.setId("1");
        sysDictionaryItem1.setSysDictionaryId("1");
        sysDictionaryItem1.setCode("1");
        sysDictionaryItem1.setName("");
        sysDictionaryItem1.setKey("1");
        sysDictionaryItem1.setValue("");
        sysDictionaryItem1.setValueI18n("");
        sysDictionaryItem1.setKeyType(TypeTypeEnum.STRING);
        sysDictionaryItem1.setValueType(TypeTypeEnum.STRING);
        sysDictionaryItem1.setColor("");
        sysDictionaryItem1.setOrder(0);
        sysDictionaryItem1.setRemark("");
        sysDictionaryItem1.setStatus(StatusEnum.ENABLED);
        sysDictionaryItem1.setUpdatedSysUserId("");
        sysDictionaryItem1.setCreatedSysUserId("");
        sysDictionaryItem1.setUpdatedDateTime(LocalDateTime.now());
        sysDictionaryItem1.setCreatedDateTime(LocalDateTime.now());
        sysDictionaryItemMapper.insert(sysDictionaryItem1);

        final List<SysDictionaryItem> sysDictionaryItemList = sysDictionaryItemService.getSysDictionaryItemListBySysDictionaryId("1");
        
        assertThat(sysDictionaryItemList)
                .isNotNull()
                .hasSize(1)
                .usingElementComparator(Comparator.comparing(SysDictionaryItem::getId))
                .contains(sysDictionaryItem1);
    }

    @Test
    void getSysDictionaryItemListByCode() {
        final SysDictionaryItem sysDictionaryItem1 = new SysDictionaryItem();
        sysDictionaryItem1.setId("1");
        sysDictionaryItem1.setSysDictionaryId("1");
        sysDictionaryItem1.setCode("1");
        sysDictionaryItem1.setName("");
        sysDictionaryItem1.setKey("1");
        sysDictionaryItem1.setValue("");
        sysDictionaryItem1.setValueI18n("");
        sysDictionaryItem1.setKeyType(TypeTypeEnum.STRING);
        sysDictionaryItem1.setValueType(TypeTypeEnum.STRING);
        sysDictionaryItem1.setColor("");
        sysDictionaryItem1.setOrder(0);
        sysDictionaryItem1.setRemark("");
        sysDictionaryItem1.setStatus(StatusEnum.ENABLED);
        sysDictionaryItem1.setUpdatedSysUserId("");
        sysDictionaryItem1.setCreatedSysUserId("");
        sysDictionaryItem1.setUpdatedDateTime(LocalDateTime.now());
        sysDictionaryItem1.setCreatedDateTime(LocalDateTime.now());
        sysDictionaryItemMapper.insert(sysDictionaryItem1);

        final List<SysDictionaryItem> sysDictionaryItemList = sysDictionaryItemService.getSysDictionaryItemListByCode("1");

        assertThat(sysDictionaryItemList)
                .isNotNull()
                .hasSize(1)
                .usingElementComparator(Comparator.comparing(SysDictionaryItem::getId))
                .contains(sysDictionaryItem1);
    }

    @Test
    void getEnabledSysDictionaryItemListByCode() {
        final SysDictionaryItem sysDictionaryItem1 = new SysDictionaryItem();
        sysDictionaryItem1.setId("1");
        sysDictionaryItem1.setSysDictionaryId("1");
        sysDictionaryItem1.setCode("1");
        sysDictionaryItem1.setName("");
        sysDictionaryItem1.setKey("1");
        sysDictionaryItem1.setValue("");
        sysDictionaryItem1.setValueI18n("");
        sysDictionaryItem1.setKeyType(TypeTypeEnum.STRING);
        sysDictionaryItem1.setValueType(TypeTypeEnum.STRING);
        sysDictionaryItem1.setColor("");
        sysDictionaryItem1.setOrder(0);
        sysDictionaryItem1.setRemark("");
        sysDictionaryItem1.setStatus(StatusEnum.ENABLED);
        sysDictionaryItem1.setUpdatedSysUserId("");
        sysDictionaryItem1.setCreatedSysUserId("");
        sysDictionaryItem1.setUpdatedDateTime(LocalDateTime.now());
        sysDictionaryItem1.setCreatedDateTime(LocalDateTime.now());
        sysDictionaryItemMapper.insert(sysDictionaryItem1);

        final List<SysDictionaryItem> sysDictionaryItemList = sysDictionaryItemService.getEnabledSysDictionaryItemListByCode("1");

        assertThat(sysDictionaryItemList)
                .isNotNull()
                .hasSize(1)
                .usingElementComparator(Comparator.comparing(SysDictionaryItem::getId))
                .contains(sysDictionaryItem1);
    }

    @Test
    void getSysDictionaryItemByCodeAndKey() {    
        final SysDictionaryItem sysDictionaryItem1 = new SysDictionaryItem();
        sysDictionaryItem1.setId("1");
        sysDictionaryItem1.setSysDictionaryId("1");
        sysDictionaryItem1.setCode("1");
        sysDictionaryItem1.setName("");
        sysDictionaryItem1.setKey("1");
        sysDictionaryItem1.setValue("");
        sysDictionaryItem1.setValueI18n("");
        sysDictionaryItem1.setKeyType(TypeTypeEnum.STRING);
        sysDictionaryItem1.setValueType(TypeTypeEnum.STRING);
        sysDictionaryItem1.setColor("");
        sysDictionaryItem1.setOrder(0);
        sysDictionaryItem1.setRemark("");
        sysDictionaryItem1.setStatus(StatusEnum.ENABLED);
        sysDictionaryItem1.setUpdatedSysUserId("");
        sysDictionaryItem1.setCreatedSysUserId("");
        sysDictionaryItem1.setUpdatedDateTime(LocalDateTime.now());
        sysDictionaryItem1.setCreatedDateTime(LocalDateTime.now());
        sysDictionaryItemMapper.insert(sysDictionaryItem1);

        final SysDictionaryItem sysDictionaryItem = sysDictionaryItemService.getSysDictionaryItemByCodeAndKey("1","1");

        assertThat(sysDictionaryItem)
                .isNotNull()
                .usingComparator(Comparator.comparing(SysDictionaryItem::getId))
                .isEqualTo(sysDictionaryItem1);
    }

    @Test
    void createSysDictionaryItem() {
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

        sysDictionaryItemService.createSysDictionaryItem("1",
                "1", "1", "1", "1", "", TypeTypeEnum.STRING, TypeTypeEnum.STRING, null, null, null, StatusEnum.ENABLED, "1");

        final List<SysDictionaryItem> sysDictionaryItemList = sysDictionaryItemMapper.selectList(Wrappers.<SysDictionaryItem>lambdaQuery().eq(SysDictionaryItem::getSysDictionaryId, "1"));

        assertThat(sysDictionaryItemList)
                .isNotNull()
                .hasSize(1);
    }

    @Test
    void updateSysDictionaryItem() {
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

        final SysDictionaryItem sysDictionaryItem1 = new SysDictionaryItem();
        sysDictionaryItem1.setId("1");
        sysDictionaryItem1.setSysDictionaryId("1");
        sysDictionaryItem1.setCode("1");
        sysDictionaryItem1.setName("");
        sysDictionaryItem1.setKey("1");
        sysDictionaryItem1.setValue("");
        sysDictionaryItem1.setValueI18n("");
        sysDictionaryItem1.setKeyType(TypeTypeEnum.STRING);
        sysDictionaryItem1.setValueType(TypeTypeEnum.STRING);
        sysDictionaryItem1.setColor("");
        sysDictionaryItem1.setOrder(0);
        sysDictionaryItem1.setRemark("");
        sysDictionaryItem1.setStatus(StatusEnum.ENABLED);
        sysDictionaryItem1.setUpdatedSysUserId("");
        sysDictionaryItem1.setCreatedSysUserId("");
        sysDictionaryItem1.setUpdatedDateTime(LocalDateTime.now());
        sysDictionaryItem1.setCreatedDateTime(LocalDateTime.now());
        sysDictionaryItemMapper.insert(sysDictionaryItem1);
        
        sysDictionaryItemService.updateSysDictionaryItem("1","2",
                "1", "1", "1", "1", "", TypeTypeEnum.STRING, TypeTypeEnum.STRING, null, null, null, StatusEnum.ENABLED, "1");

        final List<SysDictionaryItem> sysDictionaryItemList = sysDictionaryItemMapper.selectList(Wrappers.<SysDictionaryItem>lambdaQuery()
                .eq(SysDictionaryItem::getSysDictionaryId, "2"));

        assertThat(sysDictionaryItemList)
                .isNotNull()
                .hasSize(1);
    }

    @Test
    void deleteSysDictionaryItem() {
        final SysDictionaryItem sysDictionaryItem1 = new SysDictionaryItem();
        sysDictionaryItem1.setId("1");
        sysDictionaryItem1.setSysDictionaryId("1");
        sysDictionaryItem1.setCode("1");
        sysDictionaryItem1.setName("");
        sysDictionaryItem1.setKey("1");
        sysDictionaryItem1.setValue("");
        sysDictionaryItem1.setValueI18n("");
        sysDictionaryItem1.setKeyType(TypeTypeEnum.STRING);
        sysDictionaryItem1.setValueType(TypeTypeEnum.STRING);
        sysDictionaryItem1.setColor("");
        sysDictionaryItem1.setOrder(0);
        sysDictionaryItem1.setRemark("");
        sysDictionaryItem1.setStatus(StatusEnum.ENABLED);
        sysDictionaryItem1.setUpdatedSysUserId("");
        sysDictionaryItem1.setCreatedSysUserId("");
        sysDictionaryItem1.setUpdatedDateTime(LocalDateTime.now());
        sysDictionaryItem1.setCreatedDateTime(LocalDateTime.now());
        sysDictionaryItemMapper.insert(sysDictionaryItem1);

        sysDictionaryItemService.deleteSysDictionaryItem("1");

        final SysDictionaryItem sysDictionaryItem = sysDictionaryItemMapper.selectById("1");

        assertThat(sysDictionaryItem).isNull();
    }

    @Test
    void deleteSysDictionaryItemByCode() {
        final SysDictionaryItem sysDictionaryItem1 = new SysDictionaryItem();
        sysDictionaryItem1.setId("1");
        sysDictionaryItem1.setSysDictionaryId("1");
        sysDictionaryItem1.setCode("1");
        sysDictionaryItem1.setName("");
        sysDictionaryItem1.setKey("1");
        sysDictionaryItem1.setValue("");
        sysDictionaryItem1.setValueI18n("");
        sysDictionaryItem1.setKeyType(TypeTypeEnum.STRING);
        sysDictionaryItem1.setValueType(TypeTypeEnum.STRING);
        sysDictionaryItem1.setColor("");
        sysDictionaryItem1.setOrder(0);
        sysDictionaryItem1.setRemark("");
        sysDictionaryItem1.setStatus(StatusEnum.ENABLED);
        sysDictionaryItem1.setUpdatedSysUserId("");
        sysDictionaryItem1.setCreatedSysUserId("");
        sysDictionaryItem1.setUpdatedDateTime(LocalDateTime.now());
        sysDictionaryItem1.setCreatedDateTime(LocalDateTime.now());
        sysDictionaryItemMapper.insert(sysDictionaryItem1);

        sysDictionaryItemService.deleteSysDictionaryItemByCode("1");

        final SysDictionaryItem sysDictionaryItem = sysDictionaryItemMapper.selectOne(Wrappers.<SysDictionaryItem>lambdaQuery()
                .eq(SysDictionaryItem::getCode, "1"));

        assertThat(sysDictionaryItem).isNull();
    }

    @Test
    void deleteSysDictionaryItemByCodeAndKey() {
        final SysDictionaryItem sysDictionaryItem1 = new SysDictionaryItem();
        sysDictionaryItem1.setId("1");
        sysDictionaryItem1.setSysDictionaryId("1");
        sysDictionaryItem1.setCode("1");
        sysDictionaryItem1.setName("");
        sysDictionaryItem1.setKey("1");
        sysDictionaryItem1.setValue("");
        sysDictionaryItem1.setValueI18n("");
        sysDictionaryItem1.setKeyType(TypeTypeEnum.STRING);
        sysDictionaryItem1.setValueType(TypeTypeEnum.STRING);
        sysDictionaryItem1.setColor("");
        sysDictionaryItem1.setOrder(0);
        sysDictionaryItem1.setRemark("");
        sysDictionaryItem1.setStatus(StatusEnum.ENABLED);
        sysDictionaryItem1.setUpdatedSysUserId("");
        sysDictionaryItem1.setCreatedSysUserId("");
        sysDictionaryItem1.setUpdatedDateTime(LocalDateTime.now());
        sysDictionaryItem1.setCreatedDateTime(LocalDateTime.now());
        sysDictionaryItemMapper.insert(sysDictionaryItem1);

        sysDictionaryItemService.deleteSysDictionaryItemByCodeAndKey("1","1");

        final SysDictionaryItem sysDictionaryItem = sysDictionaryItemMapper.selectOne(Wrappers.<SysDictionaryItem>lambdaQuery()
                .eq(SysDictionaryItem::getCode, "1")
                .eq(SysDictionaryItem::getKey, "1"));

        assertThat(sysDictionaryItem).isNull();
    }

    @Test
    void enabledSysDictionaryItem() {
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
        
        final SysDictionaryItem sysDictionaryItem1 = new SysDictionaryItem();
        sysDictionaryItem1.setId("1");
        sysDictionaryItem1.setSysDictionaryId("1");
        sysDictionaryItem1.setCode("1");
        sysDictionaryItem1.setName("");
        sysDictionaryItem1.setKey("1");
        sysDictionaryItem1.setValue("");
        sysDictionaryItem1.setValueI18n("");
        sysDictionaryItem1.setKeyType(TypeTypeEnum.STRING);
        sysDictionaryItem1.setValueType(TypeTypeEnum.STRING);
        sysDictionaryItem1.setColor("");
        sysDictionaryItem1.setOrder(0);
        sysDictionaryItem1.setRemark("");
        sysDictionaryItem1.setStatus(StatusEnum.DISABLED);
        sysDictionaryItem1.setUpdatedSysUserId("");
        sysDictionaryItem1.setCreatedSysUserId("");
        sysDictionaryItem1.setUpdatedDateTime(LocalDateTime.now());
        sysDictionaryItem1.setCreatedDateTime(LocalDateTime.now());
        sysDictionaryItemMapper.insert(sysDictionaryItem1);

        sysDictionaryItemService.enabledSysDictionaryItem("1","1");

        final SysDictionaryItem sysDictionaryItem = sysDictionaryItemMapper.selectById("1");
        assertThat(sysDictionaryItem).isNotNull();
        assertThat(sysDictionaryItem.getStatus()).isEqualByComparingTo(StatusEnum.ENABLED);
    }

    @Test
    void enabledSysDictionaryItemByCodeAndKey() {
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
        
        final SysDictionaryItem sysDictionaryItem1 = new SysDictionaryItem();
        sysDictionaryItem1.setId("1");
        sysDictionaryItem1.setSysDictionaryId("1");
        sysDictionaryItem1.setCode("1");
        sysDictionaryItem1.setName("");
        sysDictionaryItem1.setKey("1");
        sysDictionaryItem1.setValue("");
        sysDictionaryItem1.setValueI18n("");
        sysDictionaryItem1.setKeyType(TypeTypeEnum.STRING);
        sysDictionaryItem1.setValueType(TypeTypeEnum.STRING);
        sysDictionaryItem1.setColor("");
        sysDictionaryItem1.setOrder(0);
        sysDictionaryItem1.setRemark("");
        sysDictionaryItem1.setStatus(StatusEnum.DISABLED);
        sysDictionaryItem1.setUpdatedSysUserId("");
        sysDictionaryItem1.setCreatedSysUserId("");
        sysDictionaryItem1.setUpdatedDateTime(LocalDateTime.now());
        sysDictionaryItem1.setCreatedDateTime(LocalDateTime.now());
        sysDictionaryItemMapper.insert(sysDictionaryItem1);

        sysDictionaryItemService.enabledSysDictionaryItemByCodeAndKey("1","1","1");

        final SysDictionaryItem sysDictionaryItem =  sysDictionaryItemMapper.selectOne(Wrappers.<SysDictionaryItem>lambdaQuery()
                .eq(SysDictionaryItem::getCode, "1")
                .eq(SysDictionaryItem::getKey, "1"));
        assertThat(sysDictionaryItem).isNotNull();
        assertThat(sysDictionaryItem.getStatus()).isEqualByComparingTo(StatusEnum.ENABLED);
    }

    @Test
    void disableSysDictionaryItem() {
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
        
        final SysDictionaryItem sysDictionaryItem1 = new SysDictionaryItem();
        sysDictionaryItem1.setId("1");
        sysDictionaryItem1.setSysDictionaryId("1");
        sysDictionaryItem1.setCode("1");
        sysDictionaryItem1.setName("");
        sysDictionaryItem1.setKey("1");
        sysDictionaryItem1.setValue("");
        sysDictionaryItem1.setValueI18n("");
        sysDictionaryItem1.setKeyType(TypeTypeEnum.STRING);
        sysDictionaryItem1.setValueType(TypeTypeEnum.STRING);
        sysDictionaryItem1.setColor("");
        sysDictionaryItem1.setOrder(0);
        sysDictionaryItem1.setRemark("");
        sysDictionaryItem1.setStatus(StatusEnum.ENABLED);
        sysDictionaryItem1.setUpdatedSysUserId("");
        sysDictionaryItem1.setCreatedSysUserId("");
        sysDictionaryItem1.setUpdatedDateTime(LocalDateTime.now());
        sysDictionaryItem1.setCreatedDateTime(LocalDateTime.now());
        sysDictionaryItemMapper.insert(sysDictionaryItem1);

        sysDictionaryItemService.disableSysDictionaryItem("1","1");

        final SysDictionaryItem sysDictionaryItem = sysDictionaryItemMapper.selectById("1");
        assertThat(sysDictionaryItem).isNotNull();
        assertThat(sysDictionaryItem.getStatus()).isEqualByComparingTo(StatusEnum.DISABLED);
    }

    @Test
    void disableSysDictionaryItemByCodeAndKey() {
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
        
        final SysDictionaryItem sysDictionaryItem1 = new SysDictionaryItem();
        sysDictionaryItem1.setId("1");
        sysDictionaryItem1.setSysDictionaryId("1");
        sysDictionaryItem1.setCode("1");
        sysDictionaryItem1.setName("");
        sysDictionaryItem1.setKey("1");
        sysDictionaryItem1.setValue("");
        sysDictionaryItem1.setValueI18n("");
        sysDictionaryItem1.setKeyType(TypeTypeEnum.STRING);
        sysDictionaryItem1.setValueType(TypeTypeEnum.STRING);
        sysDictionaryItem1.setColor("");
        sysDictionaryItem1.setOrder(0);
        sysDictionaryItem1.setRemark("");
        sysDictionaryItem1.setStatus(StatusEnum.ENABLED);
        sysDictionaryItem1.setUpdatedSysUserId("");
        sysDictionaryItem1.setCreatedSysUserId("");
        sysDictionaryItem1.setUpdatedDateTime(LocalDateTime.now());
        sysDictionaryItem1.setCreatedDateTime(LocalDateTime.now());
        sysDictionaryItemMapper.insert(sysDictionaryItem1);

        sysDictionaryItemService.disableSysDictionaryItemByCodeAndKey("1","1","1");

        final SysDictionaryItem sysDictionaryItem =  sysDictionaryItemMapper.selectOne(Wrappers.<SysDictionaryItem>lambdaQuery()
                .eq(SysDictionaryItem::getCode, "1")
                .eq(SysDictionaryItem::getKey, "1"));
        assertThat(sysDictionaryItem).isNotNull();
        assertThat(sysDictionaryItem.getStatus()).isEqualByComparingTo(StatusEnum.DISABLED);
    }
}
