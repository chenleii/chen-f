package com.chen.f.common.api.response.error;

import com.chen.f.core.api.response.error.ErrorResponse;
import com.chen.f.core.configuration.i18n.I18nHelper;

/**
 * 系统字典项目的错误响应
 * <p>
 * 继承{@link com.chen.f.core.api.response.error.ErrorResponse}方便查找
 *
 * @author chen
 * @since 2020/6/19 15:52.
 */
public interface SysDictionaryItemErrorResponses extends ErrorResponse {


    static ErrorResponse sysDictionaryItemNotExist() {
        String message = I18nHelper.getMessage(SysUserErrorResponses.class.getSimpleName() + ".sysDictionaryItemNotExist", "系统字典项目不存在");
        return ErrorResponse.create(message);
    }

    static ErrorResponse createSysDictionaryItemFailure() {
        String message = I18nHelper.getMessage(SysDictionaryItemErrorResponses.class.getSimpleName() + ".createSysDictionaryItemFailure", "创建系统字典项目失败");
        return ErrorResponse.create(message);
    }
    
    static ErrorResponse updateSysDictionaryItemFailure() {
        String message = I18nHelper.getMessage(SysDictionaryItemErrorResponses.class.getSimpleName() + ".updateSysDictionaryItemFailure", "修改系统字典项目失败");
        return ErrorResponse.create(message);
    }

    static ErrorResponse deleteSysDictionaryItemFailure() {
        String message = I18nHelper.getMessage(SysDictionaryItemErrorResponses.class.getSimpleName() + ".deleteSysDictionaryItemFailure", "删除系统字典项目失败");
        return ErrorResponse.create(message);
    }

    static ErrorResponse sysDictionaryItemIdCanNotNull() {
        String message = I18nHelper.getMessage(SysDictionaryItemErrorResponses.class.getSimpleName() + ".sysDictionaryItemIdCanNotNull", "系统字典项目ID不能为空");
        return ErrorResponse.create(message);
    }


    static ErrorResponse sysDictionaryItemCodeCanNotBlank() {
        String message = I18nHelper.getMessage(SysDictionaryItemErrorResponses.class.getSimpleName() + ".sysDictionaryItemCodeCanNotBlank", "系统字典项目的编码不能为空白字符串");
        return ErrorResponse.create(message);
    }

    static ErrorResponse sysDictionaryItemKeyCanNotBlank() {
        String message = I18nHelper.getMessage(SysDictionaryItemErrorResponses.class.getSimpleName() + ".sysDictionaryItemKeyCanNotBlank", "系统字典项目KEY不能为空");
        return ErrorResponse.create(message);
    }
    
    static ErrorResponse sysDictionaryItemNameCanNotBlank() {
        String message = I18nHelper.getMessage(SysDictionaryItemErrorResponses.class.getSimpleName() + ".sysDictionaryItemNameCanNotBlank", "系统字典项目名称不能为空白字符串");
        return ErrorResponse.create(message);
    }

    static ErrorResponse sysDictionaryItemValueCanNotBlank() {
        String message = I18nHelper.getMessage(SysDictionaryItemErrorResponses.class.getSimpleName() + ".sysDictionaryItemValueCanNotBlank", "系统字典项目值不能为空");
        return ErrorResponse.create(message);
    }

    static ErrorResponse sysDictionaryItemKeyTypeCanNotNull() {
        String message = I18nHelper.getMessage(SysDictionaryItemErrorResponses.class.getSimpleName() + ".sysDictionaryItemKeyTypeCanNotNull", "系统字典项目KEY类型不能为空");
        return ErrorResponse.create(message);
    }

    static ErrorResponse sysDictionaryItemValueTypeCanNotNull() {
        String message = I18nHelper.getMessage(SysDictionaryItemErrorResponses.class.getSimpleName() + ".sysDictionaryItemValueTypeCanNotNull", "系统字典项目值类型不能为空");
        return ErrorResponse.create(message);
    }

    static ErrorResponse sysDictionaryItemStatusCanNotNull() {
        String message = I18nHelper.getMessage(SysDictionaryItemErrorResponses.class.getSimpleName() + ".sysDictionaryItemStatusCanNotNull", "系统字典项目状态不能为空");
        return ErrorResponse.create(message);
    }
}
