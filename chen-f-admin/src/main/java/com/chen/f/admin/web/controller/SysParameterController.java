package com.chen.f.admin.web.controller;

import com.chen.f.admin.configuration.security.SecurityHelper;
import com.chen.f.common.pojo.SysParameter;
import com.chen.f.common.pojo.enums.StatusEnum;
import com.chen.f.common.pojo.enums.SysParameterTypeEnum;
import com.chen.f.common.pojo.enums.TypeTypeEnum;
import com.chen.f.common.service.ISysParameterService;
import com.chen.f.core.page.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 系统参数表 前端控制器
 * </p>
 *
 * @author chen
 * @since 2018-12-02
 */
@Api(tags = "系统参数接口")
@RestController
@RequestMapping("/chen/admin/sys/parameter")
public class SysParameterController {
    protected static final Logger logger = LoggerFactory.getLogger(SysParameterController.class);

    @Autowired
    private ISysParameterService sysParameterService;

    @ApiOperation(value = "获取分页的系统参数", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageIndex", value = "当前页数", required = true, dataTypeClass = Long.class, paramType = "query", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", required = true, dataTypeClass = Long.class, paramType = "query", defaultValue = "10"),
            @ApiImplicitParam(name = "sort", value = "排序信息(eg:name1.ascend-name2.descend)", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "code", value = "系统参数编码", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "name", value = "系统参数名称", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "value", value = "系统参数值", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "valueType", value = "系统参数值类型", required = false, dataTypeClass = TypeTypeEnum.class, paramType = "query"),
            @ApiImplicitParam(name = "type", value = "系统参数类型", required = false, dataTypeClass = SysParameterTypeEnum.class, paramType = "query"),
            @ApiImplicitParam(name = "remark", value = "系统参数备注", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "status", value = "系统参数状态", required = false, dataTypeClass = StatusEnum.class, paramType = "query"),
    })
    @ApiOperationSupport(ignoreParameters = {"list", "total", "orderList", "optimizeCountSql", "isSearchCount","searchCount", "hitCount",})
    @GetMapping
    public Page<SysParameter> getSysParameterPage(
            Page<SysParameter> page,
            @RequestParam(name = "code", required = false) String code,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "value", required = false) String value,
            @RequestParam(name = "valueType", required = false) TypeTypeEnum valueType,
            @RequestParam(name = "type", required = false) SysParameterTypeEnum type,
            @RequestParam(name = "remark", required = false) String remark,
            @RequestParam(name = "status", required = false) StatusEnum status) {
        return sysParameterService.getSysParameterPage(page, code, name, value, valueType, type, remark, status);
    }


    @ApiOperation(value = "获取启用的系统参数", notes = "", produces = "application/json")
    @ApiImplicitParams({
    })
    @GetMapping("/enabled")
    public List<SysParameter> getEnabledSysParameterList() {
        return sysParameterService.getEnabledSysParameterList();
    }

    @ApiOperation(value = "获取系统参数", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysParameterId", value = "系统参数ID", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @GetMapping("/{sysParameterId}")
    public SysParameter getSysParameter(@PathVariable("sysParameterId") String sysParameterId) {
        return sysParameterService.getSysParameter(sysParameterId);
    }

    @ApiOperation(value = "获取系统参数", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "系统参数编码", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @GetMapping("/code/{code}")
    public SysParameter getSysParameterByCode(@PathVariable("code") String code) {
        return sysParameterService.getSysParameterByCode(code);
    }

    @ApiOperation(value = "创建系统参数", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "系统参数编码", required = true, dataTypeClass = String.class, paramType = "form"),
            @ApiImplicitParam(name = "name", value = "系统参数名称", required = true, dataTypeClass = String.class, paramType = "form"),
            @ApiImplicitParam(name = "value", value = "系统参数值", required = true, dataTypeClass = String.class, paramType = "form"),
            @ApiImplicitParam(name = "valueType", value = "系统参数值类型", required = false, dataTypeClass = TypeTypeEnum.class, paramType = "form"),
            @ApiImplicitParam(name = "type", value = "系统参数类型", required = true, dataTypeClass = SysParameterTypeEnum.class, paramType = "form"),
            @ApiImplicitParam(name = "remark", value = "系统参数备注", required = false, dataTypeClass = String.class, paramType = "form"),
            @ApiImplicitParam(name = "status", value = "系统参数状态", required = true, dataTypeClass = StatusEnum.class, paramType = "form"),
    })
    @PostMapping
    public void createSysParameter(
            @RequestParam(name = "code") String code,
            @RequestParam(name = "name") String name,
            @RequestParam(name = "value") String value,
            @RequestParam(name = "valueType") TypeTypeEnum valueType,
            @RequestParam(name = "type") SysParameterTypeEnum type,
            @RequestParam(name = "remark", required = false) String remark,
            @RequestParam(name = "status") StatusEnum status) {
        String operatedSysRoleId = SecurityHelper.getSysUserId();
        sysParameterService.createSysParameter(code, name, value, valueType, type, remark, status, operatedSysRoleId);
    }

    @ApiOperation(value = "创建系统参数", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "SysParameter", value = "系统参数", required = true, dataTypeClass = SysParameter.class, paramType = "body"),
    })
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void createSysParameter(@RequestBody() SysParameter sysParameter) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        sysParameterService.createSysParameter(sysParameter.getCode(), sysParameter.getName(), sysParameter.getValue(),
                sysParameter.getValueType(), sysParameter.getType(), sysParameter.getRemark(), sysParameter.getStatus(), operatedSysUserId);
    }

    @ApiOperation(value = "修改系统参数", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysParameterId", value = "系统参数ID", required = true, dataTypeClass = String.class, paramType = "path"),
            @ApiImplicitParam(name = "code", value = "系统参数编码", required = true, dataTypeClass = String.class, paramType = "form"),
            @ApiImplicitParam(name = "name", value = "系统参数名称", required = true, dataTypeClass = String.class, paramType = "form"),
            @ApiImplicitParam(name = "value", value = "系统参数值", required = true, dataTypeClass = String.class, paramType = "form"),
            @ApiImplicitParam(name = "valueType", value = "系统参数值类型", required = false, dataTypeClass = TypeTypeEnum.class, paramType = "form"),
            @ApiImplicitParam(name = "type", value = "系统参数类型", required = true, dataTypeClass = SysParameterTypeEnum.class, paramType = "form"),
            @ApiImplicitParam(name = "remark", value = "系统参数备注", required = false, dataTypeClass = String.class, paramType = "form"),
            @ApiImplicitParam(name = "status", value = "系统参数状态", required = true, dataTypeClass = StatusEnum.class, paramType = "form"),
    })
    @PutMapping("/{sysParameterId}")
    public void updateSysParameter(
            @PathVariable(name = "sysParameterId") String sysParameterId,
            @RequestParam(name = "code") String code,
            @RequestParam(name = "name") String name,
            @RequestParam(name = "value") String value,
            @RequestParam(name = "valueType") TypeTypeEnum valueType,
            @RequestParam(name = "type") SysParameterTypeEnum type,
            @RequestParam(name = "remark", required = false) String remark,
            @RequestParam(name = "status") StatusEnum status) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        sysParameterService.updateSysParameter(sysParameterId, code, name, value, valueType, type, remark, status, operatedSysUserId);
    }

    @ApiOperation(value = "修改系统参数", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysParameterId", value = "系统参数ID", required = true, dataTypeClass = String.class, paramType = "path"),
            @ApiImplicitParam(name = "SysParameter", value = "系统参数", required = true, dataTypeClass = SysParameter.class, paramType = "body"),
    })
    @PutMapping(path = "/{sysParameterId}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void updateSysParameter(@PathVariable("sysParameterId") String sysParameterId,
                                   @RequestBody() SysParameter sysParameter) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        sysParameterService.updateSysParameter(sysParameterId, sysParameter.getCode(), sysParameter.getName(), sysParameter.getValue(),
                sysParameter.getValueType(), sysParameter.getType(), sysParameter.getRemark(), sysParameter.getStatus(), operatedSysUserId);
    }

    @ApiOperation(value = "删除系统参数", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysParameterId", value = "系统参数ID", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @DeleteMapping("/{sysParameterId}")
    public void deleteSysParameter(@PathVariable("sysParameterId") String sysParameterId) {
        sysParameterService.deleteSysParameter(sysParameterId);
    }

    @ApiOperation(value = "删除系统参数", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "系统参数编码", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @DeleteMapping("/code/{code}")
    public void deleteSysParameterByCode(@PathVariable("code") String code) {
        sysParameterService.deleteSysParameterByCode(code);
    }

    @ApiOperation(value = "启用系统参数", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysParameterId", value = "系统参数ID", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @PostMapping("/{sysParameterId}/enable")
    public void enabledSysParameter(@PathVariable("sysParameterId") String sysParameterId) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        sysParameterService.enabledSysParameter(sysParameterId, operatedSysUserId);
    }

    @ApiOperation(value = "启用系统参数", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "系统参数编码", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @PostMapping("/code/{code}/enable")
    public void enabledSysParameterByCode(@PathVariable("code") String code) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        sysParameterService.enabledSysParameterByCode(code, operatedSysUserId);
    }

    @ApiOperation(value = "禁用系统参数", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysParameterId", value = "系统参数ID", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @PostMapping("/{sysParameterId}/disable")
    public void disableSysParameter(@PathVariable("sysParameterId") String sysParameterId) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        sysParameterService.disableSysParameter(sysParameterId, operatedSysUserId);
    }

    @ApiOperation(value = "禁用系统参数", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "系统参数编码", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @PostMapping("/code/{code}/disable")
    public void disableSysParameterByCode(@PathVariable("code") String code) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        sysParameterService.disableSysParameterByCode(code, operatedSysUserId);
    }
}

