package com.chen.f.test.sysuser;

import com.chen.f.common.mapper.SysUserMapper;
import com.chen.f.common.pojo.SysUser;
import com.chen.f.common.pojo.enums.SysUserStatusEnum;
import com.chen.f.common.service.ISysUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.DataTableType;
import io.cucumber.java.DefaultDataTableCellTransformer;
import io.cucumber.java.DefaultDataTableEntryTransformer;
import io.cucumber.java.DefaultParameterTransformer;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class SysUserStepDefinitions {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @DefaultParameterTransformer
    @DefaultDataTableEntryTransformer
    @DefaultDataTableCellTransformer
    public Object transformer(Object fromValue, Type toValueType) {
        return objectMapper.convertValue(fromValue, objectMapper.constructType(toValueType));
    }

    private String actualResult;

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private ISysUserService sysUserService;


    // @DataTableType(replaceWithEmptyString = "[blank]")
    // public SysUser defaultSysUser(Map<String, String> map){
    //     return new SysUser();
    // }
    @DataTableType(replaceWithEmptyString = "[blank]")
    public String defaultString(String cell){
        return "default";
    }
    @DataTableType
    public SysUser defineSysUser(Map<String, String> map) {
        final SysUser sysUser = new SysUser();
        sysUser.setId(map.get("id"));
        sysUser.setUsername(map.get("name"));
        sysUser.setPassword("default");
        sysUser.setLevel(Integer.parseInt(map.get("level")));
        sysUser.setLastLoginDateTime(LocalDateTime.now());
        sysUser.setRemark("");
        sysUser.setStatus(SysUserStatusEnum.ENABLED);
        sysUser.setUpdatedSysUserId("1");
        sysUser.setCreatedSysUserId("1");
        sysUser.setUpdatedDateTime(LocalDateTime.now());
        sysUser.setCreatedDateTime(LocalDateTime.now());
        return sysUser;
    }

    @ParameterType("\"(\\w+)\"")
    public String test(String string) {
        return string;
    }


    @Given("^创建测试系统用户:$")
    public void createTestSysUser(List<SysUser> sysUserList) {
        sysUserService.saveBatch(sysUserList);
    }

    @When("系统用户 {string} 修改系统用户 {string} 的等级为 {int}")
    public void updateSysUser(String operatedSysUserId, String updatedId, int updatedLevel) {
        try {
            sysUserService.updateSysUser(updatedId,
                    "default", "default", updatedLevel, "", SysUserStatusEnum.ENABLED,
                    operatedSysUserId);
            actualResult = "successful";
        } catch (Exception e) {
            actualResult = "failure";
        }
    }

    @Then("修改结果是 {string}")
    public void isUpdatedResult(String result) {
        Assertions.assertThat(actualResult)
                .isNotNull()
                .isNotBlank()
                .isEqualTo(result);

    }

}
