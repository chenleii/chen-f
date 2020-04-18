package com.chen.f.admin.web.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.chen.f.admin.configuration.helper.SecurityHelper;
import com.chen.f.common.pojo.SysDictionary;
import com.chen.f.common.pojo.enums.StatusEnum;
import com.chen.f.common.pojo.enums.SysDictionaryTypeEnum;
import com.chen.f.common.service.ISysDictionaryService;
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
 * 系统字典表 前端控制器
 * </p>
 *
 * @author chen
 * @since 2020-04-06
 */
@Api(tags = "系统字典接口")
@RestController
@RequestMapping("/chen/admin/sys/dictionary")
public class SysDictionaryController {
    protected static final Logger logger = LoggerFactory.getLogger(SysDictionaryController.class);

    @Autowired
    private ISysDictionaryService sysDictionaryService;

    @ApiOperation(value = "获取分页的系统字典", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageIndex", value = "页数", required = true, dataTypeClass = Long.class, paramType = "query", defaultValue = "1"),
            @ApiImplicitParam(name = "pageNumber", value = "页大小", required = true, dataTypeClass = Long.class, paramType = "query", defaultValue = "10"),
            @ApiImplicitParam(name = "code", value = "系统字典编码", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "name", value = "系统字典名称", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "type", value = "系统字典类型", required = false, dataTypeClass = SysDictionaryTypeEnum.class, paramType = "query"),
            @ApiImplicitParam(name = "remark", value = "系统字典描述", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "status", value = "系统字典状态", required = false, dataTypeClass = StatusEnum.class, paramType = "query"),
    })
    @GetMapping
    public IPage<SysDictionary> getSysDictionaryPage(@RequestParam(name = "pageIndex", defaultValue = "1") Long pageIndex,
                                                     @RequestParam(name = "pageNumber", defaultValue = "10") Long pageNumber,
                                                     @RequestParam(name = "code", required = false) String code,
                                                     @RequestParam(name = "name", required = false) String name,
                                                     @RequestParam(name = "type", required = false) SysDictionaryTypeEnum valueTypeEnum,
                                                     @RequestParam(name = "remark", required = false) String remark,
                                                     @RequestParam(name = "status", required = false) StatusEnum statusEnum
    ) {
        return sysDictionaryService.getSysDictionaryPage(pageIndex, pageNumber, code, name, valueTypeEnum, remark, statusEnum);
    }

    @ApiOperation(value = "获取启用的系统字典", notes = "", produces = "application/json")
    @ApiImplicitParams({
    })
    @GetMapping("/enabled")
    public List<SysDictionary> getEnabledSysDictionaryList() {
        return sysDictionaryService.getEnabledSysDictionaryList();
    }


    @ApiOperation(value = "获取系统字典", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysDictionaryId", value = "系统字典ID", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @GetMapping(path = "/{sysDictionaryId}")
    public SysDictionary getSysDictionary(
            @PathVariable(name = "sysDictionaryId") String sysDictionaryId) {
        return sysDictionaryService.getSysDictionary(sysDictionaryId);
    }

    @ApiOperation(value = "获取系统字典集合", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "系统字典标识", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @GetMapping(path = "/code/{code:.*}")
    public SysDictionary getSysDictionaryList(@PathVariable(name = "code") String code) {
        return sysDictionaryService.getSysDictionaryByCode(code);
    }


    @ApiOperation(value = "创建系统字典", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "系统字典标识", required = true, dataTypeClass = String.class, paramType = "from"),
            @ApiImplicitParam(name = "name", value = "系统字典名称", required = true, dataTypeClass = String.class, paramType = "from"),
            @ApiImplicitParam(name = "type", value = "系统字典类型", required = true, dataTypeClass = SysDictionaryTypeEnum.class, paramType = "from"),
            @ApiImplicitParam(name = "remark", value = "系统字典备注", required = false, dataTypeClass = String.class, paramType = "from"),
            @ApiImplicitParam(name = "status", value = "系统字典状态", required = true, dataTypeClass = StatusEnum.class, paramType = "from"),
    })
    @PostMapping
    public void createSysDictionary(
            @RequestParam(name = "code") String code,
            @RequestParam(name = "name") String name,
            @RequestParam(name = "type") SysDictionaryTypeEnum type,
            @RequestParam(name = "remark", required = false) String remark,
            @RequestParam(name = "status") StatusEnum status) {
        String operatedSysRoleId = SecurityHelper.getSysUserId();
        sysDictionaryService.createSysDictionary(code, name, type, remark, status, operatedSysRoleId);
    }

    @ApiOperation(value = "创建系统字典", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "SysDictionary", value = "系统字典", required = true, dataTypeClass = SysDictionary.class, paramType = "body"),
    })
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void createSysDictionary(@RequestBody() SysDictionary sysDictionary) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        sysDictionaryService.createSysDictionary(sysDictionary.getCode(), sysDictionary.getName(), sysDictionary.getType(),
                sysDictionary.getRemark(), sysDictionary.getStatus(), operatedSysUserId);
    }

    @ApiOperation(value = "修改系统字典", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysDictionaryId", value = "系统字典ID", required = true, dataTypeClass = String.class, paramType = "path"),
            @ApiImplicitParam(name = "code", value = "系统字典标识", required = true, dataTypeClass = String.class, paramType = "from"),
            @ApiImplicitParam(name = "name", value = "系统字典名称", required = true, dataTypeClass = String.class, paramType = "from"),
            @ApiImplicitParam(name = "type", value = "系统字典类型", required = true, dataTypeClass = SysDictionaryTypeEnum.class, paramType = "from"),
            @ApiImplicitParam(name = "remark", value = "系统字典备注", required = false, dataTypeClass = String.class, paramType = "from"),
            @ApiImplicitParam(name = "status", value = "系统字典状态", required = true, dataTypeClass = StatusEnum.class, paramType = "from"),
    })
    @PutMapping("/{sysDictionaryId}")
    public void updateSysDictionary(
            @PathVariable(name = "sysDictionaryId") String sysDictionaryId,
            @RequestParam(name = "code") String code,
            @RequestParam(name = "name") String name,
            @RequestParam(name = "type") SysDictionaryTypeEnum type,
            @RequestParam(name = "remark", required = false) String remark,
            @RequestParam(name = "status") StatusEnum status) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        sysDictionaryService.updateSysDictionary(sysDictionaryId, code, name, type, remark, status, operatedSysUserId);
    }

    @ApiOperation(value = "修改系统字典", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysDictionaryId", value = "系统字典ID", required = true, dataTypeClass = String.class, paramType = "path"),
            @ApiImplicitParam(name = "SysDictionary", value = "系统字典", required = true, dataTypeClass = SysDictionary.class, paramType = "body"),
    })
    @PutMapping(path = "/{sysDictionaryId}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void updateSysDictionary(
            @PathVariable(name = "sysDictionaryId") String sysDictionaryId,
            @RequestBody() SysDictionary sysDictionary) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        sysDictionaryService.updateSysDictionary(sysDictionaryId, sysDictionary.getCode(), sysDictionary.getName(),
                sysDictionary.getType(), sysDictionary.getRemark(), sysDictionary.getStatus(), operatedSysUserId);
    }

    @ApiOperation(value = "删除系统字典", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysDictionaryId", value = "系统字典ID", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @DeleteMapping("/{sysDictionaryId}")
    public void deleteSysDictionary(
            @PathVariable(name = "sysDictionaryId") String sysDictionaryId) {
        sysDictionaryService.deleteSysDictionary(sysDictionaryId);
    }

    @ApiOperation(value = "删除系统字典", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "系统字典标识", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @DeleteMapping("/code/{code:.*}")
    public void deleteSysDictionaryByCode(
            @PathVariable(name = "code") String code) {
        sysDictionaryService.deleteSysDictionaryByCode(code);
    }


    @ApiOperation(value = "启用系统字典", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysDictionaryId", value = "系统字典ID", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @PostMapping("/{sysDictionaryId}/enable")
    public void enabledSysDictionary(
            @PathVariable(name = "sysDictionaryId") String sysDictionaryId) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        sysDictionaryService.enabledSysDictionary(sysDictionaryId, operatedSysUserId);
    }

    @ApiOperation(value = "禁用系统字典", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysDictionaryId", value = "系统字典ID", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @PostMapping("/{sysDictionaryId}/disable")
    public void disableSysDictionary(
            @PathVariable(name = "sysDictionaryId") String sysDictionaryId) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        sysDictionaryService.disableSysDictionary(sysDictionaryId, operatedSysUserId);
    }

}

