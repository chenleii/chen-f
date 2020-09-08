package com.chen.f.common.service.impl;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.chen.f.common.pojo.SysApi;
import com.chen.f.common.pojo.SysUser;
import com.chen.f.common.pojo.enums.*;

import com.baomidou.mybatisplus.test.autoconfigure.AutoConfigureMybatisPlus;
import com.chen.f.common.configuration.EnableChenFCommonConfiguration;
import com.chen.f.common.mapper.SysDictionaryMapper;
import com.chen.f.common.mapper.SysMenuMapper;
import com.chen.f.common.mapper.SysUserMapper;
import com.chen.f.common.pojo.SysMenu;
import com.chen.f.core.configuration.mybatisplus.EnableChenFMybatisPlusConfiguration;
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
@SpringBootTest(classes = {SysMenuServiceImpl.class,})
@Transactional
@AutoConfigureMybatisPlus
@AutoConfigureTestDatabase
@ImportAutoConfiguration({
        EnableChenFCommonConfiguration.class,
        EnableChenFMybatisPlusConfiguration.class,
})
//@Sql("classpath:db/schema-h2.sql")
class SysMenuServiceImplTest {

    @Autowired
    private SysMenuServiceImpl sysMenuService;

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getAllSysMenuList() {
        SysMenu sysMenu1 = new SysMenu();
        sysMenu1.setId("1");
        sysMenu1.setParentId("0");
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

        List<SysMenu> sysMenuList = sysMenuService.getAllSysMenuList();

        assertThat(sysMenuList)
                .isNotNull()
                .hasSize(1)
                .usingElementComparator(Comparator.comparing(SysMenu::getId))
                .contains(sysMenu1);

    }

    @Test
    void getEnabledSysMenuList() {
        SysMenu sysMenu1 = new SysMenu();
        sysMenu1.setId("1");
        sysMenu1.setParentId("0");
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

        List<SysMenu> sysMenuList = sysMenuService.getEnabledSysMenuList();

        assertThat(sysMenuList)
                .isNotNull()
                .hasSize(1)
                .usingElementComparator(Comparator.comparing(SysMenu::getId))
                .contains(sysMenu1);
    }

    @Test
    void getSysMenuList() {
        SysMenu sysMenu1 = new SysMenu();
        sysMenu1.setId("1");
        sysMenu1.setParentId("0");
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

        List<SysMenu> sysMenuList = sysMenuService.getSysMenuList(null, null, null, null, null, null);

        assertThat(sysMenuList)
                .isNotNull()
                .hasSize(1)
                .usingElementComparator(Comparator.comparing(SysMenu::getId))
                .contains(sysMenu1);
    }

    @Test
    void getSysMenu() {
        SysMenu sysMenu1 = new SysMenu();
        sysMenu1.setId("1");
        sysMenu1.setParentId("0");
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

        SysMenu sysMenu = sysMenuService.getSysMenu("1");

        assertThat(sysMenu)
                .isNotNull()
                .usingComparator(Comparator.comparing(SysMenu::getId))
                .isEqualTo(sysMenu1);
    }

    @Test
    void createSysMenu() {
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

        sysMenuService.createSysMenu("", "1", "", null, null, SysMenuTypeEnum.EXTERNAL_LINK, null, null, StatusEnum.ENABLED, "1");

        List<SysMenu> sysMenuList = sysMenuMapper.selectList(Wrappers.<SysMenu>lambdaQuery().eq(SysMenu::getName, "1"));

        assertThat(sysMenuList)
                .isNotNull()
                .hasSize(1);
    }

    @Test
    void updateSysMenu() {
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

        SysMenu sysMenu1 = new SysMenu();
        sysMenu1.setId("1");
        sysMenu1.setParentId("0");
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

        sysMenuService.updateSysMenu("1","", "2", "", null, null, SysMenuTypeEnum.EXTERNAL_LINK, null, null, StatusEnum.ENABLED, "1");

        List<SysMenu> sysMenuList = sysMenuMapper.selectList(Wrappers.<SysMenu>lambdaQuery().eq(SysMenu::getName, "2"));

        assertThat(sysMenuList)
                .isNotNull()
                .hasSize(1);
    }

    @Test
    void deleteSysMenu() {

        SysMenu sysMenu1 = new SysMenu();
        sysMenu1.setId("1");
        sysMenu1.setParentId("0");
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

        sysMenuService.deleteSysMenu("1");

        SysMenu sysMenu = sysMenuMapper.selectById("1");

        assertThat(sysMenu).isNull();

    }

    @Test
    void enabledSysMenu() {
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

        SysMenu sysMenu1 = new SysMenu();
        sysMenu1.setId("1");
        sysMenu1.setParentId("0");
        sysMenu1.setName("1");
        sysMenu1.setNameI18n("1");
        sysMenu1.setUrl("");
        sysMenu1.setIcon("");
        sysMenu1.setType(SysMenuTypeEnum.GROUP);
        sysMenu1.setOrder(0);
        sysMenu1.setRemark("");
        sysMenu1.setStatus(StatusEnum.DISABLED);
        sysMenu1.setUpdatedSysUserId("");
        sysMenu1.setCreatedSysUserId("");
        sysMenu1.setUpdatedDateTime(LocalDateTime.now());
        sysMenu1.setCreatedDateTime(LocalDateTime.now());
        sysMenuMapper.insert(sysMenu1);

        sysMenuService.enabledSysMenu("1","1");

        SysMenu sysMenu = sysMenuMapper.selectById("1");

        assertThat(sysMenu).isNotNull();
        assertThat(sysMenu.getStatus()).isEqualByComparingTo(StatusEnum.ENABLED);

    }

    @Test
    void disableSysMenu() {
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

        SysMenu sysMenu1 = new SysMenu();
        sysMenu1.setId("1");
        sysMenu1.setParentId("0");
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

        sysMenuService.disableSysMenu("1","1");

        SysMenu sysMenu = sysMenuMapper.selectById("1");

        assertThat(sysMenu).isNotNull();
        assertThat(sysMenu.getStatus()).isEqualByComparingTo(StatusEnum.DISABLED);
    }
}
