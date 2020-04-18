package com.chen.f.common.web.controller;


import com.chen.f.common.pojo.SysDictionaryItem;
import com.chen.f.common.service.ISysDictionaryItemService;
import com.chen.f.common.web.dto.AlainSelectOutputDTO;
import com.chen.f.common.web.dto.AlainTagOutputDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
@RequestMapping("/chen/common/sys/dictionary/item")
public class SysDictionaryItemController {
    protected static final Logger logger = LoggerFactory.getLogger(SysDictionaryItemController.class);

    @Autowired
    private ISysDictionaryItemService sysDictionaryItemService;

    @ApiOperation(value = "获取系统字典集合", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "系统字典编码", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @GetMapping(path = "/code/{code}")
    public List<SysDictionaryItem> getSysDictionaryItemList(@PathVariable(name = "code") String code) {
        return sysDictionaryItemService.getSysDictionaryItemListByCode(code);
    }


    @ApiOperation(value = "获取系统字典", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysDictionaryItemId", value = "系统字典ID", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @GetMapping(path = "/{sysDictionaryItemId}")
    public SysDictionaryItem getSysDictionaryItem(
            @PathVariable(name = "sysDictionaryItemId") String sysDictionaryItemId) {
        return sysDictionaryItemService.getSysDictionaryItem(sysDictionaryItemId);
    }

    @ApiOperation(value = "获取系统字典", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "系统字典编码", required = true, dataTypeClass = String.class, paramType = "path"),
            @ApiImplicitParam(name = "key", value = "系统字典KEY", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @GetMapping(path = "/code/key/{code:.*}/{key}")
    public SysDictionaryItem getSysDictionaryItem(
            @PathVariable(name = "code") String code,
            @PathVariable(name = "key") String key) {
        return sysDictionaryItemService.getSysDictionaryItemByCodeAndKey(code, key);
    }

    @ApiOperation(value = "获取系统字典集合", notes = "仅针对alain使用", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "系统字典编码", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @GetMapping(path = "/alain/tag/{code:.*}")
    public Map<Object, AlainTagOutputDTO> getSysDictionaryItemByAlainTag(@PathVariable(name = "code") String code) {
        List<com.chen.f.common.pojo.SysDictionaryItem> sysDictionaryItemList = sysDictionaryItemService.getSysDictionaryItemListByCode(code);
        if (CollectionUtils.isEmpty(sysDictionaryItemList)) {
            return Collections.emptyMap();
        }
        return sysDictionaryItemList.stream()
                .collect(Collectors.toMap(
                        (sysDictionaryItem) -> sysDictionaryItem.getKeyType().to(sysDictionaryItem.getKey()),
                        (sysDictionaryItem) -> new AlainTagOutputDTO(sysDictionaryItem.getValueType().to(sysDictionaryItem.getValue()), sysDictionaryItem.getColor())));
        
    }


    @ApiOperation(value = "获取系统字典集合", notes = "仅针对alain使用", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "系统字典编码", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @GetMapping(path = "/alain/select/{code:.*}")
    public List<AlainSelectOutputDTO> getSysDictionaryItemByAlainSelect(@PathVariable(name = "code") String code) {
        List<SysDictionaryItem> sysDictionaryItemList = sysDictionaryItemService.getSysDictionaryItemListByCode(code);
        if (CollectionUtils.isEmpty(sysDictionaryItemList)) {
            return Collections.emptyList();
        }
        return sysDictionaryItemList.stream()
                .map((sysDictionaryItem -> 
                        new AlainSelectOutputDTO(sysDictionaryItem.getValueType().to(sysDictionaryItem.getValue()), 
                                sysDictionaryItem.getKeyType().to(sysDictionaryItem.getKey())))
                )
                .collect(Collectors.toList());
    }
}

