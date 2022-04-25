package com.chen.f.admin.web.controller;


import com.chen.f.admin.configuration.security.SecurityHelper;
import com.chen.f.common.pojo.SysDictionaryItem;
import com.chen.f.common.pojo.enums.StatusEnum;
import com.chen.f.common.pojo.enums.TypeTypeEnum;
import com.chen.f.common.service.ISysDictionaryItemService;
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
 * 系统字典项目表 前端控制器
 * </p>
 *
 * @author chen
 * @since 2020-04-06
 */
@Api(tags = "系统字典项目接口")
@RestController
@RequestMapping("/chen/admin/sys/dictionary/item")
public class SysDictionaryItemController {
    protected static final Logger logger = LoggerFactory.getLogger(SysDictionaryItemController.class);

    @Autowired
    private ISysDictionaryItemService sysDictionaryItemService;

    @ApiOperation(value = "获取分页的系统字典项目", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageIndex", value = "当前页数", required = true, dataTypeClass = Long.class, paramType = "query", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", required = true, dataTypeClass = Long.class, paramType = "query", defaultValue = "10"),
            @ApiImplicitParam(name = "sort", value = "排序信息(eg:name1.ascend-name2.descend)", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "sysDictionaryId", value = "系统字典ID", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "code", value = "系统字典项目标识", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "name", value = "系统字典项目名称", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "key", value = "系统字典项目KEY", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "value", value = "系统字典项目值", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "remark", value = "系统字典项目描述", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "color", value = "系统字典项目颜色", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "type", value = "系统字典项目类型", required = false, dataTypeClass = TypeTypeEnum.class, paramType = "query"),
            @ApiImplicitParam(name = "status", value = "系统字典项目状态", required = false, dataTypeClass = StatusEnum.class, paramType = "query"),
    })
    @ApiOperationSupport(ignoreParameters = {"list", "total", "orderList", "optimizeCountSql", "isSearchCount","searchCount", "hitCount",})
    @GetMapping
    public Page<SysDictionaryItem> getSysDictionaryItemPage(
            Page<SysDictionaryItem> page,
            @RequestParam(name = "sysDictionaryId", required = false) String sysDictionaryId,
            @RequestParam(name = "code", required = false) String code,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "key", required = false) String key,
            @RequestParam(name = "value", required = false) String value,
            @RequestParam(name = "remark", required = false) String remark,
            @RequestParam(name = "color", required = false) String color,
            @RequestParam(name = "type", required = false) TypeTypeEnum valueTypeEnum,
            @RequestParam(name = "status", required = false) StatusEnum statusEnum
    ) {
        return sysDictionaryItemService.getSysDictionaryItemPage(page, sysDictionaryId, code, name, key, value, valueTypeEnum, color, remark, statusEnum);
    }

    @ApiOperation(value = "获取启用的系统字典项目", notes = "", produces = "application/json")
    @ApiImplicitParams({
    })
    @GetMapping("/enabled")
    public List<SysDictionaryItem> getEnabledSysDictionaryItemList() {
        return sysDictionaryItemService.getEnabledSysDictionaryItemList();
    }


    @ApiOperation(value = "获取系统字典项目", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysDictionaryItemId", value = "系统字典项目ID", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @GetMapping(path = "/{sysDictionaryItemId}")
    public SysDictionaryItem getSysDictionaryItem(
            @PathVariable(name = "sysDictionaryItemId") String sysDictionaryItemId) {
        return sysDictionaryItemService.getSysDictionaryItem(sysDictionaryItemId);
    }

    @ApiOperation(value = "获取系统字典项目", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysDictionaryItemId", value = "系统字典项目ID", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @GetMapping(path = "/sysDictionaryId/{sysDictionaryId}")
    public List<SysDictionaryItem> getSysDictionaryItemListBySysDictionaryId(
            @PathVariable(name = "sysDictionaryId") String sysDictionaryId) {
        return sysDictionaryItemService.getSysDictionaryItemListBySysDictionaryId(sysDictionaryId);
    }

    @ApiOperation(value = "获取系统字典项目列表", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "系统字典项目标识", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @GetMapping(path = "/code/{code:.*}")
    public List<SysDictionaryItem> getSysDictionaryItemList(@PathVariable(name = "code") String code) {
        return sysDictionaryItemService.getSysDictionaryItemListByCode(code);
    }

    @ApiOperation(value = "获取系统字典项目", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "系统字典项目标识", required = true, dataTypeClass = String.class, paramType = "path"),
            @ApiImplicitParam(name = "key", value = "系统字典项目KEY", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @GetMapping(path = "/code/key/{code:.*}/{key}")
    public com.chen.f.common.pojo.SysDictionaryItem getSysDictionaryItem(
            @PathVariable(name = "code") String code,
            @PathVariable(name = "key") String key) {
        return sysDictionaryItemService.getSysDictionaryItemByCodeAndKey(code, key);
    }

    @ApiOperation(value = "创建系统字典项目", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysDictionaryId", value = "系统字典ID", required = true, dataTypeClass = String.class, paramType = "form"),
            @ApiImplicitParam(name = "code", value = "系统字典项目标识", required = true, dataTypeClass = String.class, paramType = "form"),
            @ApiImplicitParam(name = "name", value = "系统字典项目名称", required = true, dataTypeClass = String.class, paramType = "form"),
            @ApiImplicitParam(name = "key", value = "系统字典项目KEY", required = true, dataTypeClass = String.class, paramType = "form"),
            @ApiImplicitParam(name = "value", value = "系统字典项目值", required = true, dataTypeClass = String.class, paramType = "form"),
            @ApiImplicitParam(name = "valueI18n", value = "系统字典项目值的国际化", required = false, dataTypeClass = String.class, paramType = "form"),
            @ApiImplicitParam(name = "keyType", value = "系统字典项目KEY类型", required = true, dataTypeClass = TypeTypeEnum.class, paramType = "form"),
            @ApiImplicitParam(name = "valueType", value = "系统字典项目值类型", required = true, dataTypeClass = TypeTypeEnum.class, paramType = "form"),
            @ApiImplicitParam(name = "color", value = "系统字典项目颜色", required = false, dataTypeClass = String.class, paramType = "form"),
            @ApiImplicitParam(name = "order", value = "系统字典项目顺序", required = false, dataTypeClass = String.class, paramType = "form"),
            @ApiImplicitParam(name = "remark", value = "系统字典项目备注", required = false, dataTypeClass = String.class, paramType = "form"),
            @ApiImplicitParam(name = "status", value = "系统字典项目状态", required = true, dataTypeClass = StatusEnum.class, paramType = "form"),
    })
    @PostMapping
    public void createSysDictionaryItem(
            @RequestParam(name = "sysDictionaryId") String sysDictionaryId,
            @RequestParam(name = "code") String code,
            @RequestParam(name = "name") String name,
            @RequestParam(name = "key") String key,
            @RequestParam(name = "value") String value,
            @RequestParam(name = "valueI18n", required = false) String valueI18n,
            @RequestParam(name = "keyType") TypeTypeEnum keyType,
            @RequestParam(name = "valueType") TypeTypeEnum valueType,
            @RequestParam(name = "color", required = false) String color,
            @RequestParam(name = "order", required = false) Integer order,
            @RequestParam(name = "remark", required = false) String remark,
            @RequestParam(name = "status") StatusEnum status) {
        String operatedSysRoleId = SecurityHelper.getSysUserId();
        sysDictionaryItemService.createSysDictionaryItem(sysDictionaryId, code, name, key, value, valueI18n, keyType, valueType, color, order, remark, status, operatedSysRoleId);
    }

    @ApiOperation(value = "创建系统字典项目", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "SysDictionaryItem", value = "系统字典项目", required = true, dataTypeClass = SysDictionaryItem.class, paramType = "body"),
    })
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void createSysDictionaryItem(@RequestBody() SysDictionaryItem sysDictionaryItem) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        sysDictionaryItemService.createSysDictionaryItem(sysDictionaryItem.getSysDictionaryId(), sysDictionaryItem.getCode(), sysDictionaryItem.getName(), sysDictionaryItem.getKey(), sysDictionaryItem.getValue(),
                sysDictionaryItem.getValueI18n(), sysDictionaryItem.getKeyType(), sysDictionaryItem.getValueType(), sysDictionaryItem.getColor(), sysDictionaryItem.getOrder(), sysDictionaryItem.getRemark(), sysDictionaryItem.getStatus(), operatedSysUserId);
    }

    @ApiOperation(value = "修改系统字典项目", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysDictionaryItemId", value = "系统字典项目ID", required = true, dataTypeClass = String.class, paramType = "path"),
            @ApiImplicitParam(name = "sysDictionaryId", value = "系统字典ID", required = true, dataTypeClass = String.class, paramType = "form"),
            @ApiImplicitParam(name = "code", value = "系统字典项目标识", required = true, dataTypeClass = String.class, paramType = "form"),
            @ApiImplicitParam(name = "name", value = "系统字典项目名称", required = true, dataTypeClass = String.class, paramType = "form"),
            @ApiImplicitParam(name = "key", value = "系统字典项目KEY", required = true, dataTypeClass = String.class, paramType = "form"),
            @ApiImplicitParam(name = "value", value = "系统字典项目值", required = true, dataTypeClass = String.class, paramType = "form"),
            @ApiImplicitParam(name = "valueI18n", value = "系统字典项目值的国际化", required = false, dataTypeClass = String.class, paramType = "form"),
            @ApiImplicitParam(name = "keyType", value = "系统字典项目KEY类型", required = true, dataTypeClass = TypeTypeEnum.class, paramType = "form"),
            @ApiImplicitParam(name = "valueType", value = "系统字典项目值类型", required = true, dataTypeClass = TypeTypeEnum.class, paramType = "form"),
            @ApiImplicitParam(name = "color", value = "系统字典项目颜色", required = false, dataTypeClass = String.class, paramType = "form"),
            @ApiImplicitParam(name = "order", value = "系统字典项目顺序", required = false, dataTypeClass = String.class, paramType = "form"),
            @ApiImplicitParam(name = "remark", value = "系统字典项目备注", required = false, dataTypeClass = String.class, paramType = "form"),
            @ApiImplicitParam(name = "status", value = "系统字典项目状态", required = true, dataTypeClass = StatusEnum.class, paramType = "form"),
    })
    @PutMapping("/{sysDictionaryItemId}")
    public void updateSysDictionaryItem(
            @PathVariable(name = "sysDictionaryItemId") String sysDictionaryItemId,
            @RequestParam(name = "sysDictionaryId") String sysDictionaryId,
            @RequestParam(name = "code") String code,
            @RequestParam(name = "name") String name,
            @RequestParam(name = "key") String key,
            @RequestParam(name = "value") String value,
            @RequestParam(name = "valueI18n", required = false) String valueI18n,
            @RequestParam(name = "keyType") TypeTypeEnum keyType,
            @RequestParam(name = "valueType") TypeTypeEnum valueType,
            @RequestParam(name = "color", required = false) String color,
            @RequestParam(name = "order", required = false) Integer order,
            @RequestParam(name = "remark", required = false) String remark,
            @RequestParam(name = "status") StatusEnum status) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        sysDictionaryItemService.updateSysDictionaryItem(sysDictionaryItemId, sysDictionaryId, code, name, key,
                value, valueI18n, keyType, valueType, color, order, remark, status, operatedSysUserId);
    }

    @ApiOperation(value = "修改系统字典项目", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysDictionaryItemId", value = "系统字典项目ID", required = true, dataTypeClass = String.class, paramType = "path"),
            @ApiImplicitParam(name = "SysDictionaryItem", value = "系统字典项目", required = true, dataTypeClass = SysDictionaryItem.class, paramType = "body"),
    })
    @PutMapping(path = "/{sysDictionaryItemId}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void updateSysDictionaryItem(
            @PathVariable(name = "sysDictionaryItemId") String sysDictionaryItemId,
            @RequestBody() SysDictionaryItem sysDictionaryItem) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        sysDictionaryItemService.updateSysDictionaryItem(sysDictionaryItemId, sysDictionaryItem.getSysDictionaryId(), sysDictionaryItem.getCode(), sysDictionaryItem.getName(), sysDictionaryItem.getKey(), sysDictionaryItem.getValue(),
                sysDictionaryItem.getValueI18n(), sysDictionaryItem.getKeyType(), sysDictionaryItem.getValueType(), sysDictionaryItem.getColor(), sysDictionaryItem.getOrder(), sysDictionaryItem.getRemark(),
                sysDictionaryItem.getStatus(), operatedSysUserId);
    }

    @ApiOperation(value = "删除系统字典项目", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysDictionaryItemId", value = "系统字典项目ID", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @DeleteMapping("/{sysDictionaryItemId}")
    public void deleteSysDictionaryItem(
            @PathVariable(name = "sysDictionaryItemId") String sysDictionaryItemId) {
        sysDictionaryItemService.deleteSysDictionaryItem(sysDictionaryItemId);
    }

    @ApiOperation(value = "删除系统字典项目", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "系统字典项目标识", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @DeleteMapping("/code/{code:.*}")
    public void deleteSysDictionaryItemByCode(
            @PathVariable(name = "code") String code) {
        sysDictionaryItemService.deleteSysDictionaryItemByCode(code);
    }

    @ApiOperation(value = "删除系统字典项目", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "系统字典项目标识", required = true, dataTypeClass = String.class, paramType = "path"),
            @ApiImplicitParam(name = "key", value = "系统字典项目KEY", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @DeleteMapping("/code/key/{code:.*}/{key}")
    public void deleteSysDictionaryItem(
            @PathVariable(name = "code") String code,
            @PathVariable(name = "key") String key) {
        sysDictionaryItemService.deleteSysDictionaryItemByCodeAndKey(code, key);
    }

    @ApiOperation(value = "启用系统字典项目", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "系统字典项目标识", required = true, dataTypeClass = String.class, paramType = "path"),
            @ApiImplicitParam(name = "key", value = "系统字典项目KEY", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @PostMapping("/code/key/{code:.*}/{key}/enable")
    public void enabledSysDictionaryItem(
            @PathVariable(name = "code") String code,
            @PathVariable(name = "key") String key) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        sysDictionaryItemService.enabledSysDictionaryItemByCodeAndKey(code, key, operatedSysUserId);
    }

    @ApiOperation(value = "启用系统字典项目", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysDictionaryItemId", value = "系统字典项目ID", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @PostMapping("/{sysDictionaryItemId}/enable")
    public void enabledSysDictionaryItem(
            @PathVariable(name = "sysDictionaryItemId") String sysDictionaryItemId) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        sysDictionaryItemService.enabledSysDictionaryItem(sysDictionaryItemId, operatedSysUserId);
    }

    @ApiOperation(value = "禁用系统字典项目", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysDictionaryItemId", value = "系统字典项目ID", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @PostMapping("/{sysDictionaryItemId}/disable")
    public void disableSysDictionaryItem(
            @PathVariable(name = "sysDictionaryItemId") String sysDictionaryItemId) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        sysDictionaryItemService.disableSysDictionaryItem(sysDictionaryItemId, operatedSysUserId);
    }

    @ApiOperation(value = "禁用系统字典项目", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "系统字典项目标识", required = true, dataTypeClass = String.class, paramType = "path"),
            @ApiImplicitParam(name = "key", value = "系统字典项目KEY", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @PostMapping("/code/key/{code:.*}/{key}/disable")
    public void disableSysDictionaryItem(
            @PathVariable(name = "code") String code,
            @PathVariable(name = "key") String key) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        sysDictionaryItemService.disableSysDictionaryItemByCodeAndKey(code, key, operatedSysUserId);
    }
}

