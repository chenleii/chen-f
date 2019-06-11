package com.chen.f.admin.web.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.chen.f.admin.security.Securitys;
import com.chen.f.admin.web.dto.SysParameterInputDTO;
import com.chen.f.core.pojo.SysParameter;
import com.chen.f.core.pojo.enums.StatusEnum;
import com.chen.f.core.pojo.enums.SysParameterTypeEnum;
import com.chen.f.core.service.ISysParameterService;
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
            @ApiImplicitParam(name = "pageIndex", value = "页数", required = true, dataTypeClass = Long.class, paramType = "query"),
            @ApiImplicitParam(name = "pageNumber", value = "页大小", required = true, dataTypeClass = Long.class, paramType = "query"),
            @ApiImplicitParam(name = "code", value = "标识", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "name", value = "名称", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "value", value = "值", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "type", value = "类型", required = false, dataTypeClass = SysParameterTypeEnum.class, paramType = "query"),
            @ApiImplicitParam(name = "remark", value = "备注", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "status", value = "状态", required = false, dataTypeClass = StatusEnum.class, paramType = "query"),
    })
    @GetMapping
    public IPage<SysParameter> getSysParameterPage(@RequestParam("pageIndex") long pageIndex,
                                                   @RequestParam("pageNumber") long pageNumber,
                                                   @RequestParam(name = "code", required = false) String code,
                                                   @RequestParam(name = "name", required = false) String name,
                                                   @RequestParam(name = "value", required = false) String value,
                                                   @RequestParam(name = "type", required = false) SysParameterTypeEnum type,
                                                   @RequestParam(name = "remark", required = false) String remark,
                                                   @RequestParam(name = "status", required = false) StatusEnum status) {
        return sysParameterService.getSysParameterPage(pageIndex, pageNumber, code, name, value, type, remark, status);
    }


    @ApiOperation(value = "获取所有启用的系统参数", notes = "", produces = "application/json")
    @ApiImplicitParams({
    })
    @GetMapping("/all/enabled")
    public List<SysParameter> getAllEnabledSysParameterList() {
        return sysParameterService.getAllEnabledSysParameterList();
    }

    @ApiOperation(value = "获取系统参数", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "系统参数标识", required = true, dataTypeClass = String.class, paramType = "path")
    })
    @GetMapping("/{code}")
    public SysParameter getSysParameter(@PathVariable("code") String code) {
        return sysParameterService.getSysParameter(code);
    }

    @ApiOperation(value = "创建系统参数", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "标识", required = true, dataTypeClass = String.class, paramType = "from"),
            @ApiImplicitParam(name = "name", value = "名称", required = true, dataTypeClass = String.class, paramType = "from"),
            @ApiImplicitParam(name = "value", value = "值", required = true, dataTypeClass = String.class, paramType = "from"),
            @ApiImplicitParam(name = "type", value = "类型", required = true, dataTypeClass = SysParameterTypeEnum.class, paramType = "from"),
            @ApiImplicitParam(name = "remark", value = "备注", required = false, dataTypeClass = String.class, paramType = "from"),
            @ApiImplicitParam(name = "status", value = "状态", required = true, dataTypeClass = StatusEnum.class, paramType = "from"),
    })
    @PostMapping
    public void createSysParameter(
            @RequestParam(name = "code") String code,
            @RequestParam(name = "name") String name,
            @RequestParam(name = "value") String value,
            @RequestParam(name = "type") SysParameterTypeEnum type,
            @RequestParam(name = "remark", required = false) String remark,
            @RequestParam(name = "status") StatusEnum status) {
        String operatedSysRoleId = Securitys.getSysUserId();
        sysParameterService.createSysParameter(code, name, value, type, remark, status, operatedSysRoleId);
    }

    @ApiOperation(value = "创建系统参数", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "SysParameterInputDTO", value = "系统参数", required = true, dataTypeClass = SysParameterInputDTO.class, paramType = "body"),
    })
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
    public void createSysParameter(@RequestBody() SysParameterInputDTO sysParameterInputDTO) {
        String operatedSysUserId = Securitys.getSysUserId();
        sysParameterService.createSysParameter(sysParameterInputDTO.getCode(), sysParameterInputDTO.getName(), sysParameterInputDTO.getValue(),
                sysParameterInputDTO.getType(), sysParameterInputDTO.getRemark(), sysParameterInputDTO.getStatus(), operatedSysUserId);
    }

    @ApiOperation(value = "修改系统参数", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "标识", required = true, dataTypeClass = String.class, paramType = "path"),
            @ApiImplicitParam(name = "name", value = "名称", required = true, dataTypeClass = String.class, paramType = "from"),
            @ApiImplicitParam(name = "value", value = "值", required = true, dataTypeClass = String.class, paramType = "from"),
            @ApiImplicitParam(name = "type", value = "类型", required = true, dataTypeClass = SysParameterTypeEnum.class, paramType = "from"),
            @ApiImplicitParam(name = "remark", value = "备注", required = false, dataTypeClass = String.class, paramType = "from"),
            @ApiImplicitParam(name = "status", value = "状态", required = true, dataTypeClass = StatusEnum.class, paramType = "from"),
    })
    @PutMapping("/{code}")
    public void updateSysParameter(
            @PathVariable(name = "code") String code,
            @RequestParam(name = "name") String name,
            @RequestParam(name = "value") String value,
            @RequestParam(name = "type") SysParameterTypeEnum type,
            @RequestParam(name = "remark", required = false) String remark,
            @RequestParam(name = "status") StatusEnum status) {
        String operatedSysUserId = Securitys.getSysUserId();
        sysParameterService.updateSysParameter(code, name, value, type, remark, status, operatedSysUserId);
    }

    @ApiOperation(value = "修改系统参数", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "系统参数标识", required = true, dataTypeClass = String.class, paramType = "path"),
            @ApiImplicitParam(name = "SysParameterInputDTO", value = "系统参数DTO", required = true, dataTypeClass = SysParameterInputDTO.class, paramType = "body"),
    })
    @PutMapping(path = "/{code}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
    public void updateSysParameter(@PathVariable("code") String code,
                                   @RequestBody() SysParameterInputDTO sysParameterInputDTO) {
        String operatedSysUserId = Securitys.getSysUserId();
        sysParameterService.updateSysParameter(code, sysParameterInputDTO.getName(), sysParameterInputDTO.getValue(),
                sysParameterInputDTO.getType(), sysParameterInputDTO.getRemark(), sysParameterInputDTO.getStatus(), operatedSysUserId);
    }

    @ApiOperation(value = "删除系统参数", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "系统参数标识", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @DeleteMapping("/{code}")
    public void deleteSysParameter(@PathVariable("code") String code) {
        sysParameterService.deleteSysParameter(code);
    }

    @ApiOperation(value = "启用系统参数", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "系统参数标识", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @PostMapping("/{code}/enable")
    public void enabledSysParameter(@PathVariable("code") String code) {
        String operatedSysUserId = Securitys.getSysUserId();
        sysParameterService.enabledSysParameter(code, operatedSysUserId);
    }

    @ApiOperation(value = "禁用系统参数", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysParameterId", value = "系统参数标识", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @PostMapping("/{code}/disable")
    public void disableSysParameter(@PathVariable("code") String code) {
        String operatedSysUserId = Securitys.getSysUserId();
        sysParameterService.disableSysParameter(code, operatedSysUserId);
    }
}

