package com.chen.f.common.service.impl;
import com.baomidou.mybatisplus.test.autoconfigure.AutoConfigureMybatisPlus;
import com.chen.f.common.configuration.EnableChenFCommonConfiguration;
import com.chen.f.common.mapper.SysTimedTaskLogMapper;
import com.chen.f.common.pojo.SysTimedTaskLog;
import com.chen.f.common.pojo.enums.SysTimedTaskExecutionStatusEnum;
import com.chen.f.common.pojo.enums.SysTimedTaskTypeEnum;
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

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author chen
 * @since 2020/9/8 21:44.
 */
@SpringBootTest(classes = {SysTimedTaskLogServiceImpl.class,})
@Transactional
@AutoConfigureMybatisPlus
@AutoConfigureTestDatabase
@ImportAutoConfiguration({
        EnableChenFCommonConfiguration.class,
        EnableChenFMybatisPlusConfiguration.class,
})
//@Sql("classpath:db/schema-h2.sql")
class SysTimedTaskLogServiceImplTest {

    @Autowired
    private SysTimedTaskLogServiceImpl sysTimedTaskLogService;

    @Autowired
    private SysTimedTaskLogMapper sysTimedTaskLogMapper;


    @BeforeEach
    void setUp() {
    }

    @Test
    void getSysTimedTaskLogPage() {
        final SysTimedTaskLog sysTimedTaskLog1 = new SysTimedTaskLog();
        sysTimedTaskLog1.setId("1");
        sysTimedTaskLog1.setSysTimedTaskId("1");
        sysTimedTaskLog1.setCode("1");
        sysTimedTaskLog1.setName("1");
        sysTimedTaskLog1.setClassName("");
        sysTimedTaskLog1.setCronExpression("");
        sysTimedTaskLog1.setData("");
        sysTimedTaskLog1.setType(SysTimedTaskTypeEnum.SYSTEM);
        sysTimedTaskLog1.setExecutionStatus(SysTimedTaskExecutionStatusEnum.SUCCESS);
        sysTimedTaskLog1.setExceptionMessage("");
        sysTimedTaskLog1.setExecutionStartDateTime(LocalDateTime.now());
        sysTimedTaskLog1.setExecutionEndDateTime(LocalDateTime.now());
        sysTimedTaskLog1.setExecutionTime(0L);
        sysTimedTaskLog1.setRemark("");
        sysTimedTaskLog1.setUpdatedDateTime(LocalDateTime.now());
        sysTimedTaskLog1.setCreatedDateTime(LocalDateTime.now());
        sysTimedTaskLogMapper.insert(sysTimedTaskLog1);

        final Page<SysTimedTaskLog> sysTimedTaskLogPage = sysTimedTaskLogService.getSysTimedTaskLogPage(new Page<>(), null, null, null, null, null);

        assertThat(sysTimedTaskLogPage).isNotNull();
        assertThat(sysTimedTaskLogPage.getTotal()).isEqualTo(1);
        assertThat(sysTimedTaskLogPage.getList())
                .isNotNull()
                .hasSize(1)
                .usingElementComparator(Comparator.comparing(SysTimedTaskLog::getId))
                .contains(sysTimedTaskLog1);
    }

    @Test
    void getSysTimedTaskLog() {

        final SysTimedTaskLog sysTimedTaskLog1 = new SysTimedTaskLog();
        sysTimedTaskLog1.setId("1");
        sysTimedTaskLog1.setSysTimedTaskId("1");
        sysTimedTaskLog1.setCode("1");
        sysTimedTaskLog1.setName("1");
        sysTimedTaskLog1.setClassName("");
        sysTimedTaskLog1.setCronExpression("");
        sysTimedTaskLog1.setData("");
        sysTimedTaskLog1.setType(SysTimedTaskTypeEnum.SYSTEM);
        sysTimedTaskLog1.setExecutionStatus(SysTimedTaskExecutionStatusEnum.SUCCESS);
        sysTimedTaskLog1.setExceptionMessage("");
        sysTimedTaskLog1.setExecutionStartDateTime(LocalDateTime.now());
        sysTimedTaskLog1.setExecutionEndDateTime(LocalDateTime.now());
        sysTimedTaskLog1.setExecutionTime(0L);
        sysTimedTaskLog1.setRemark("");
        sysTimedTaskLog1.setUpdatedDateTime(LocalDateTime.now());
        sysTimedTaskLog1.setCreatedDateTime(LocalDateTime.now());
        sysTimedTaskLogMapper.insert(sysTimedTaskLog1);

        final SysTimedTaskLog sysTimedTaskLog = sysTimedTaskLogService.getSysTimedTaskLog("1");

        assertThat(sysTimedTaskLog)
                .isNotNull()
                .usingComparator(Comparator.comparing(SysTimedTaskLog::getId))
                .isEqualTo(sysTimedTaskLog1);
    }

    @Test
    void deleteSysTimedTaskLog() {

        final SysTimedTaskLog sysTimedTaskLog1 = new SysTimedTaskLog();
        sysTimedTaskLog1.setId("1");
        sysTimedTaskLog1.setSysTimedTaskId("1");
        sysTimedTaskLog1.setCode("1");
        sysTimedTaskLog1.setName("1");
        sysTimedTaskLog1.setClassName("");
        sysTimedTaskLog1.setCronExpression("");
        sysTimedTaskLog1.setData("");
        sysTimedTaskLog1.setType(SysTimedTaskTypeEnum.SYSTEM);
        sysTimedTaskLog1.setExecutionStatus(SysTimedTaskExecutionStatusEnum.SUCCESS);
        sysTimedTaskLog1.setExceptionMessage("");
        sysTimedTaskLog1.setExecutionStartDateTime(LocalDateTime.now());
        sysTimedTaskLog1.setExecutionEndDateTime(LocalDateTime.now());
        sysTimedTaskLog1.setExecutionTime(0L);
        sysTimedTaskLog1.setRemark("");
        sysTimedTaskLog1.setUpdatedDateTime(LocalDateTime.now());
        sysTimedTaskLog1.setCreatedDateTime(LocalDateTime.now());
        sysTimedTaskLogMapper.insert(sysTimedTaskLog1);

        sysTimedTaskLogService.deleteSysTimedTaskLog("1");

        final SysTimedTaskLog sysTimedTaskLog = sysTimedTaskLogMapper.selectById("1");

        assertThat(sysTimedTaskLog).isNull();
    }
}