package com.chen.f.common.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.test.autoconfigure.AutoConfigureMybatisPlus;
import com.chen.f.common.configuration.EnableChenFCommonConfiguration;
import com.chen.f.common.mapper.SysTimedTaskMapper;
import com.chen.f.common.mapper.SysUserMapper;
import com.chen.f.common.pojo.SysTimedTask;
import com.chen.f.common.pojo.SysUser;
import com.chen.f.common.pojo.enums.StatusEnum;
import com.chen.f.common.pojo.enums.SysTimedTaskTypeEnum;
import com.chen.f.common.pojo.enums.SysUserStatusEnum;
import com.chen.f.core.configuration.mybatisplus.EnableChenFMybatisPlusConfiguration;
import com.chen.f.core.configuration.quartz.EnableChenFQuartz;
import com.chen.f.core.page.Page;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.quartz.QuartzAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author chen
 * @since 2020/9/8 23:01.
 */
@SpringBootTest(classes = {SysTimedTaskServiceImpl.class,})
@Transactional
@AutoConfigureMybatisPlus
@AutoConfigureTestDatabase
@ImportAutoConfiguration({
        EnableChenFCommonConfiguration.class,
        EnableChenFMybatisPlusConfiguration.class,
        QuartzAutoConfiguration.class
})
@EnableChenFQuartz
//@Sql("classpath:db/schema-h2.sql")
class SysTimedTaskServiceImplTest {

    @Autowired
    private SysTimedTaskServiceImpl sysTimedTaskService;

    @Autowired
    private SysTimedTaskMapper sysTimedTaskMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getSysTimedTaskPage() {
        final SysTimedTask sysTimedTask1 = new SysTimedTask();
        sysTimedTask1.setId("1");
        sysTimedTask1.setCode("1");
        sysTimedTask1.setName("1");
        sysTimedTask1.setClassName(TestTask.class.getName());
        sysTimedTask1.setCronExpression("1 * * * * ? ");
        sysTimedTask1.setData("");
        sysTimedTask1.setType(SysTimedTaskTypeEnum.SYSTEM);
        sysTimedTask1.setRemark("");
        sysTimedTask1.setStatus(StatusEnum.ENABLED);
        sysTimedTask1.setUpdatedSysUserId("");
        sysTimedTask1.setCreatedSysUserId("");
        sysTimedTask1.setUpdatedDateTime(LocalDateTime.now());
        sysTimedTask1.setCreatedDateTime(LocalDateTime.now());
        sysTimedTaskMapper.insert(sysTimedTask1);

        final Page<SysTimedTask> sysTimedTaskPage = sysTimedTaskService.getSysTimedTaskPage(new Page<>(), null, null, null, null, null, null);

        assertThat(sysTimedTaskPage).isNotNull();
        assertThat(sysTimedTaskPage.getTotal()).isEqualTo(1);
        assertThat(sysTimedTaskPage.getList())
                .isNotNull()
                .hasSize(1)
                .usingElementComparator(Comparator.comparing(SysTimedTask::getId))
                .contains(sysTimedTask1);

    }

    @Test
    void getEnabledSysTimedTaskList() {
        final SysTimedTask sysTimedTask1 = new SysTimedTask();
        sysTimedTask1.setId("1");
        sysTimedTask1.setCode("1");
        sysTimedTask1.setName("1");
        sysTimedTask1.setClassName(TestTask.class.getName());
        sysTimedTask1.setCronExpression("1 * * * * ? ");
        sysTimedTask1.setData("");
        sysTimedTask1.setType(SysTimedTaskTypeEnum.SYSTEM);
        sysTimedTask1.setRemark("");
        sysTimedTask1.setStatus(StatusEnum.ENABLED);
        sysTimedTask1.setUpdatedSysUserId("");
        sysTimedTask1.setCreatedSysUserId("");
        sysTimedTask1.setUpdatedDateTime(LocalDateTime.now());
        sysTimedTask1.setCreatedDateTime(LocalDateTime.now());
        sysTimedTaskMapper.insert(sysTimedTask1);

        final List<SysTimedTask> sysTimedTaskList = sysTimedTaskService.getEnabledSysTimedTaskList();

        assertThat(sysTimedTaskList)
                .isNotNull()
                .hasSize(1)
                .usingElementComparator(Comparator.comparing(SysTimedTask::getId))
                .contains(sysTimedTask1);
    }

    @Test
    void getSysTimedTask() {
        final SysTimedTask sysTimedTask1 = new SysTimedTask();
        sysTimedTask1.setId("1");
        sysTimedTask1.setCode("1");
        sysTimedTask1.setName("1");
        sysTimedTask1.setClassName(TestTask.class.getName());
        sysTimedTask1.setCronExpression("1 * * * * ? ");
        sysTimedTask1.setData("");
        sysTimedTask1.setType(SysTimedTaskTypeEnum.SYSTEM);
        sysTimedTask1.setRemark("");
        sysTimedTask1.setStatus(StatusEnum.ENABLED);
        sysTimedTask1.setUpdatedSysUserId("");
        sysTimedTask1.setCreatedSysUserId("");
        sysTimedTask1.setUpdatedDateTime(LocalDateTime.now());
        sysTimedTask1.setCreatedDateTime(LocalDateTime.now());
        sysTimedTaskMapper.insert(sysTimedTask1);

        final SysTimedTask sysTimedTask = sysTimedTaskService.getSysTimedTask("1");

        assertThat(sysTimedTask)
                .isNotNull()
                .usingComparator(Comparator.comparing(SysTimedTask::getId))
                .isEqualTo(sysTimedTask1);
    }

    @Test
    void getSysTimedTaskByCode() {
        final SysTimedTask sysTimedTask1 = new SysTimedTask();
        sysTimedTask1.setId("1");
        sysTimedTask1.setCode("1");
        sysTimedTask1.setName("1");
        sysTimedTask1.setClassName(TestTask.class.getName());
        sysTimedTask1.setCronExpression("1 * * * * ? ");
        sysTimedTask1.setData("");
        sysTimedTask1.setType(SysTimedTaskTypeEnum.SYSTEM);
        sysTimedTask1.setRemark("");
        sysTimedTask1.setStatus(StatusEnum.ENABLED);
        sysTimedTask1.setUpdatedSysUserId("");
        sysTimedTask1.setCreatedSysUserId("");
        sysTimedTask1.setUpdatedDateTime(LocalDateTime.now());
        sysTimedTask1.setCreatedDateTime(LocalDateTime.now());
        sysTimedTaskMapper.insert(sysTimedTask1);

        final SysTimedTask sysTimedTask = sysTimedTaskService.getSysTimedTaskByCode("1");

        assertThat(sysTimedTask)
                .isNotNull()
                .usingComparator(Comparator.comparing(SysTimedTask::getCode))
                .isEqualTo(sysTimedTask1);
    }

    
    @Test
    void createSysTimedTask() {

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

        sysTimedTaskService.createSysTimedTask("1", "1", TestTask.class.getName(), "1 * * * * ? ", "", SysTimedTaskTypeEnum.SYSTEM, null, StatusEnum.ENABLED, "1");

        final List<SysTimedTask> sysTimedTaskList = sysTimedTaskMapper.selectList(Wrappers.<SysTimedTask>lambdaQuery()
                .eq(SysTimedTask::getCode, "1"));
        
        assertThat(sysTimedTaskList)
                .isNotNull()
                .hasSize(1);
        
        sysTimedTaskService.deleteSysTimedTask(sysTimedTaskList.get(0).getId());
    }

    @Test
    void updateSysTimedTask() {
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

        final SysTimedTask sysTimedTask1 = new SysTimedTask();
        sysTimedTask1.setId("1");
        sysTimedTask1.setCode("1");
        sysTimedTask1.setName("1");
        sysTimedTask1.setClassName(TestTask.class.getName());
        sysTimedTask1.setCronExpression("1 * * * * ? ");
        sysTimedTask1.setData("");
        sysTimedTask1.setType(SysTimedTaskTypeEnum.SYSTEM);
        sysTimedTask1.setRemark("");
        sysTimedTask1.setStatus(StatusEnum.ENABLED);
        sysTimedTask1.setUpdatedSysUserId("");
        sysTimedTask1.setCreatedSysUserId("");
        sysTimedTask1.setUpdatedDateTime(LocalDateTime.now());
        sysTimedTask1.setCreatedDateTime(LocalDateTime.now());
        sysTimedTaskMapper.insert(sysTimedTask1);
        
        sysTimedTaskService.updateSysTimedTask("1","2", "1", TestTask.class.getName(), "1 * * * * ? ", "", SysTimedTaskTypeEnum.SYSTEM, null, StatusEnum.ENABLED, "1");

        final List<SysTimedTask> sysTimedTaskList = sysTimedTaskMapper.selectList(Wrappers.<SysTimedTask>lambdaQuery()
                .eq(SysTimedTask::getCode, "2"));

        assertThat(sysTimedTaskList)
                .isNotNull()
                .hasSize(1);
        
        sysTimedTaskService.deleteSysTimedTask(sysTimedTaskList.get(0).getId());
    }

    @Test
    void deleteSysTimedTask() {
        final SysTimedTask sysTimedTask1 = new SysTimedTask();
        sysTimedTask1.setId("1");
        sysTimedTask1.setCode("1");
        sysTimedTask1.setName("1");
        sysTimedTask1.setClassName(TestTask.class.getName());
        sysTimedTask1.setCronExpression("1 * * * * ? ");
        sysTimedTask1.setData("");
        sysTimedTask1.setType(SysTimedTaskTypeEnum.SYSTEM);
        sysTimedTask1.setRemark("");
        sysTimedTask1.setStatus(StatusEnum.ENABLED);
        sysTimedTask1.setUpdatedSysUserId("");
        sysTimedTask1.setCreatedSysUserId("");
        sysTimedTask1.setUpdatedDateTime(LocalDateTime.now());
        sysTimedTask1.setCreatedDateTime(LocalDateTime.now());
        sysTimedTaskMapper.insert(sysTimedTask1);

        sysTimedTaskService.deleteSysTimedTask("1");

        final SysTimedTask sysTimedTask = sysTimedTaskMapper.selectById("1");

        assertThat(sysTimedTask).isNull();
    }

    @Test
    void deleteSysTimedTaskByCode() {

        final SysTimedTask sysTimedTask1 = new SysTimedTask();
        sysTimedTask1.setId("1");
        sysTimedTask1.setCode("1");
        sysTimedTask1.setName("1");
        sysTimedTask1.setClassName(TestTask.class.getName());
        sysTimedTask1.setCronExpression("1 * * * * ? ");
        sysTimedTask1.setData("");
        sysTimedTask1.setType(SysTimedTaskTypeEnum.SYSTEM);
        sysTimedTask1.setRemark("");
        sysTimedTask1.setStatus(StatusEnum.ENABLED);
        sysTimedTask1.setUpdatedSysUserId("");
        sysTimedTask1.setCreatedSysUserId("");
        sysTimedTask1.setUpdatedDateTime(LocalDateTime.now());
        sysTimedTask1.setCreatedDateTime(LocalDateTime.now());
        sysTimedTaskMapper.insert(sysTimedTask1);

        sysTimedTaskService.deleteSysTimedTaskByCode("1");

        final SysTimedTask sysTimedTask = sysTimedTaskMapper.selectOne(Wrappers.<SysTimedTask>lambdaQuery().eq(SysTimedTask::getCode, "1"));

        assertThat(sysTimedTask).isNull();
    }

    @Test
    void enabledSysTimedTask() {
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
        
        final SysTimedTask sysTimedTask1 = new SysTimedTask();
        sysTimedTask1.setId("1");
        sysTimedTask1.setCode("1");
        sysTimedTask1.setName("1");
        sysTimedTask1.setClassName(TestTask.class.getName());
        sysTimedTask1.setCronExpression("1 * * * * ? ");
        sysTimedTask1.setData("");
        sysTimedTask1.setType(SysTimedTaskTypeEnum.SYSTEM);
        sysTimedTask1.setRemark("");
        sysTimedTask1.setStatus(StatusEnum.DISABLED);
        sysTimedTask1.setUpdatedSysUserId("");
        sysTimedTask1.setCreatedSysUserId("");
        sysTimedTask1.setUpdatedDateTime(LocalDateTime.now());
        sysTimedTask1.setCreatedDateTime(LocalDateTime.now());
        sysTimedTaskMapper.insert(sysTimedTask1);

        sysTimedTaskService.enabledSysTimedTask("1", "1");

        final SysTimedTask sysTimedTask = sysTimedTaskMapper.selectById("1");

        assertThat(sysTimedTask).isNotNull();
        assertThat(sysTimedTask.getStatus()).isEqualByComparingTo(StatusEnum.ENABLED);
        
        sysTimedTaskService.deleteSysTimedTask(sysTimedTask.getId());
    }

    @Test
    void enabledSysTimedTaskByCode() {
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

        final SysTimedTask sysTimedTask1 = new SysTimedTask();
        sysTimedTask1.setId("1");
        sysTimedTask1.setCode("1");
        sysTimedTask1.setName("1");
        sysTimedTask1.setClassName(TestTask.class.getName());
        sysTimedTask1.setCronExpression("1 * * * * ? ");
        sysTimedTask1.setData("");
        sysTimedTask1.setType(SysTimedTaskTypeEnum.SYSTEM);
        sysTimedTask1.setRemark("");
        sysTimedTask1.setStatus(StatusEnum.DISABLED);
        sysTimedTask1.setUpdatedSysUserId("");
        sysTimedTask1.setCreatedSysUserId("");
        sysTimedTask1.setUpdatedDateTime(LocalDateTime.now());
        sysTimedTask1.setCreatedDateTime(LocalDateTime.now());
        sysTimedTaskMapper.insert(sysTimedTask1);

        sysTimedTaskService.enabledSysTimedTask("1", "1");

        final SysTimedTask sysTimedTask = sysTimedTaskMapper.selectOne(Wrappers.<SysTimedTask>lambdaQuery().eq(SysTimedTask::getCode, "1"));

        assertThat(sysTimedTask).isNotNull();
        assertThat(sysTimedTask.getStatus()).isEqualByComparingTo(StatusEnum.ENABLED);
        
        sysTimedTaskService.deleteSysTimedTask(sysTimedTask.getId());
    }

    @Test
    void disableSysTimedTask() {
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

        final SysTimedTask sysTimedTask1 = new SysTimedTask();
        sysTimedTask1.setId("1");
        sysTimedTask1.setCode("1");
        sysTimedTask1.setName("1");
        sysTimedTask1.setClassName(TestTask.class.getName());
        sysTimedTask1.setCronExpression("1 * * * * ? ");
        sysTimedTask1.setData("");
        sysTimedTask1.setType(SysTimedTaskTypeEnum.SYSTEM);
        sysTimedTask1.setRemark("");
        sysTimedTask1.setStatus(StatusEnum.ENABLED);
        sysTimedTask1.setUpdatedSysUserId("");
        sysTimedTask1.setCreatedSysUserId("");
        sysTimedTask1.setUpdatedDateTime(LocalDateTime.now());
        sysTimedTask1.setCreatedDateTime(LocalDateTime.now());
        sysTimedTaskMapper.insert(sysTimedTask1);

        sysTimedTaskService.disableSysTimedTask("1", "1");

        final SysTimedTask sysTimedTask = sysTimedTaskMapper.selectById("1");

        assertThat(sysTimedTask).isNotNull();
        assertThat(sysTimedTask.getStatus()).isEqualByComparingTo(StatusEnum.DISABLED);
    }

    @Test
    void disableSysTimedTaskByCode() {
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

        final SysTimedTask sysTimedTask1 = new SysTimedTask();
        sysTimedTask1.setId("1");
        sysTimedTask1.setCode("1");
        sysTimedTask1.setName("1");
        sysTimedTask1.setClassName(TestTask.class.getName());
        sysTimedTask1.setCronExpression("1 * * * * ? ");
        sysTimedTask1.setData("");
        sysTimedTask1.setType(SysTimedTaskTypeEnum.SYSTEM);
        sysTimedTask1.setRemark("");
        sysTimedTask1.setStatus(StatusEnum.ENABLED);
        sysTimedTask1.setUpdatedSysUserId("");
        sysTimedTask1.setCreatedSysUserId("");
        sysTimedTask1.setUpdatedDateTime(LocalDateTime.now());
        sysTimedTask1.setCreatedDateTime(LocalDateTime.now());
        sysTimedTaskMapper.insert(sysTimedTask1);

        sysTimedTaskService.disableSysTimedTaskByCode("1", "1");

        final SysTimedTask sysTimedTask = sysTimedTaskMapper.selectOne(Wrappers.<SysTimedTask>lambdaQuery().eq(SysTimedTask::getCode, "1"));

        assertThat(sysTimedTask).isNotNull();
        assertThat(sysTimedTask.getStatus()).isEqualByComparingTo(StatusEnum.DISABLED);
    }

    @Test
    void executionSysTimedTask() {
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

        sysTimedTaskService.createSysTimedTask("1", "1", TestTask.class.getName(), "1 * * * * ? ", "", SysTimedTaskTypeEnum.SYSTEM, null, StatusEnum.ENABLED, "1");

        final SysTimedTask sysTimedTask = sysTimedTaskMapper.selectOne(Wrappers.<SysTimedTask>lambdaQuery().eq(SysTimedTask::getCode, "1"));

        sysTimedTaskService.executionSysTimedTask(sysTimedTask.getId());
        
        
    }

    @Test
    void executionSysTimedTaskByCode() {
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

        sysTimedTaskService.createSysTimedTask("1", "1", TestTask.class.getName(), "1 * * * * ? ", "", SysTimedTaskTypeEnum.SYSTEM, null, StatusEnum.ENABLED, "1");

        sysTimedTaskService.executionSysTimedTaskByCode("1");
    }

    @Test
    void initSysTimedTask() {
        sysTimedTaskService.initSysTimedTask();
    }
    
}

class TestTask implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

    }
}