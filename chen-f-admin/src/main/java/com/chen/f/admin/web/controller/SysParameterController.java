package com.chen.f.admin.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.chen.f.admin.configuration.helper.SecurityHelper;
import com.chen.f.admin.web.dto.SysParameterInputDTO;
import com.chen.f.common.pojo.SysParameter;
import com.chen.f.common.pojo.enums.StatusEnum;
import com.chen.f.common.pojo.enums.SysParameterTypeEnum;
import com.chen.f.common.service.ISysParameterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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
            @ApiImplicitParam(name = "pageIndex", value = "页数", required = true, dataTypeClass = Long.class, paramType = "query", defaultValue = "1"),
            @ApiImplicitParam(name = "pageNumber", value = "页大小", required = true, dataTypeClass = Long.class, paramType = "query", defaultValue = "10"),
            @ApiImplicitParam(name = "code", value = "系统参数标识", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "name", value = "系统参数名称", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "value", value = "系统参数值", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "type", value = "系统参数类型", required = false, dataTypeClass = SysParameterTypeEnum.class, paramType = "query"),
            @ApiImplicitParam(name = "remark", value = "系统参数备注", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "status", value = "系统参数状态", required = false, dataTypeClass = StatusEnum.class, paramType = "query"),
    })
    @GetMapping
    public IPage<SysParameter> getSysParameterPage(@RequestParam(name = "pageIndex", defaultValue = "1") Long pageIndex,
                                                   @RequestParam(name = "pageNumber", defaultValue = "10") Long pageNumber,
                                                   @RequestParam(name = "code", required = false) String code,
                                                   @RequestParam(name = "name", required = false) String name,
                                                   @RequestParam(name = "value", required = false) String value,
                                                   @RequestParam(name = "type", required = false) SysParameterTypeEnum type,
                                                   @RequestParam(name = "remark", required = false) String remark,
                                                   @RequestParam(name = "status", required = false) StatusEnum status) {
        return sysParameterService.getSysParameterPage(pageIndex, pageNumber, code, name, value, type, remark, status);
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
            @ApiImplicitParam(name = "code", value = "系统参数标识", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @GetMapping("/{code}/byCode")
    public SysParameter getSysParameterByCode(@PathVariable("code") String code) {
        return sysParameterService.getSysParameterByCode(code);
    }

    @ApiOperation(value = "创建系统参数", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "系统参数标识", required = true, dataTypeClass = String.class, paramType = "from"),
            @ApiImplicitParam(name = "name", value = "系统参数名称", required = true, dataTypeClass = String.class, paramType = "from"),
            @ApiImplicitParam(name = "value", value = "系统参数值", required = true, dataTypeClass = String.class, paramType = "from"),
            @ApiImplicitParam(name = "type", value = "系统参数类型", required = true, dataTypeClass = SysParameterTypeEnum.class, paramType = "from"),
            @ApiImplicitParam(name = "remark", value = "系统参数备注", required = false, dataTypeClass = String.class, paramType = "from"),
            @ApiImplicitParam(name = "status", value = "系统参数状态", required = true, dataTypeClass = StatusEnum.class, paramType = "from"),
    })
    @PostMapping
    public void createSysParameter(
            @RequestParam(name = "code") String code,
            @RequestParam(name = "name") String name,
            @RequestParam(name = "value") String value,
            @RequestParam(name = "type") SysParameterTypeEnum type,
            @RequestParam(name = "remark", required = false) String remark,
            @RequestParam(name = "status") StatusEnum status) {
        String operatedSysRoleId = SecurityHelper.getSysUserId();
        sysParameterService.createSysParameter(code, name, value, type, remark, status, operatedSysRoleId);
    }

    @ApiOperation(value = "创建系统参数", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "SysParameterInputDTO", value = "系统参数", required = true, dataTypeClass = SysParameterInputDTO.class, paramType = "body"),
    })
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
    public void createSysParameter(@RequestBody() SysParameterInputDTO sysParameterInputDTO) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        sysParameterService.createSysParameter(sysParameterInputDTO.getCode(), sysParameterInputDTO.getName(), sysParameterInputDTO.getValue(),
                sysParameterInputDTO.getType(), sysParameterInputDTO.getRemark(), sysParameterInputDTO.getStatus(), operatedSysUserId);
    }

    @ApiOperation(value = "修改系统参数", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysParameterId", value = "系统参数ID", required = true, dataTypeClass = String.class, paramType = "path"),
            @ApiImplicitParam(name = "code", value = "系统参数标识", required = true, dataTypeClass = String.class, paramType = "from"),
            @ApiImplicitParam(name = "name", value = "系统参数名称", required = true, dataTypeClass = String.class, paramType = "from"),
            @ApiImplicitParam(name = "value", value = "系统参数值", required = true, dataTypeClass = String.class, paramType = "from"),
            @ApiImplicitParam(name = "type", value = "系统参数类型", required = true, dataTypeClass = SysParameterTypeEnum.class, paramType = "from"),
            @ApiImplicitParam(name = "remark", value = "系统参数备注", required = false, dataTypeClass = String.class, paramType = "from"),
            @ApiImplicitParam(name = "status", value = "系统参数状态", required = true, dataTypeClass = StatusEnum.class, paramType = "from"),
    })
    @PutMapping("/{sysParameterId}")
    public void updateSysParameter(
            @PathVariable(name = "sysParameterId") String sysParameterId,
            @RequestParam(name = "code") String code,
            @RequestParam(name = "name") String name,
            @RequestParam(name = "value") String value,
            @RequestParam(name = "type") SysParameterTypeEnum type,
            @RequestParam(name = "remark", required = false) String remark,
            @RequestParam(name = "status") StatusEnum status) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        sysParameterService.updateSysParameter(sysParameterId, code, name, value, type, remark, status, operatedSysUserId);
    }

    @ApiOperation(value = "修改系统参数", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysParameterId", value = "系统参数ID", required = true, dataTypeClass = String.class, paramType = "path"),
            @ApiImplicitParam(name = "SysParameterInputDTO", value = "系统参数DTO", required = true, dataTypeClass = SysParameterInputDTO.class, paramType = "body"),
    })
    @PutMapping(path = "/{sysParameterId}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
    public void updateSysParameter(@PathVariable("sysParameterId") String sysParameterId,
                                   @RequestBody() SysParameterInputDTO sysParameterInputDTO) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        sysParameterService.updateSysParameter(sysParameterId, sysParameterInputDTO.getCode(), sysParameterInputDTO.getName(), sysParameterInputDTO.getValue(),
                sysParameterInputDTO.getType(), sysParameterInputDTO.getRemark(), sysParameterInputDTO.getStatus(), operatedSysUserId);
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
            @ApiImplicitParam(name = "code", value = "系统参数标识", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @DeleteMapping("/{code}/byCode")
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
            @ApiImplicitParam(name = "code", value = "系统参数标识", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @PostMapping("/{code}/enable/byCode")
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
            @ApiImplicitParam(name = "code", value = "系统参数标识", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @PostMapping("/{code}/disable/byCode")
    public void disableSysParameterByCode(@PathVariable("code") String code) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        sysParameterService.disableSysParameterByCode(code, operatedSysUserId);
    }
}

