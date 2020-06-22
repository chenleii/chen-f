package com.chen.f.common.api.response.error;

import com.chen.f.core.api.response.error.ErrorResponse;
import com.chen.f.core.configuration.i18n.I18nHelper;

/**
 * 系统字典的错误响应
 * <p>
 * 继承{@link com.chen.f.core.api.response.error.ErrorResponse}方便查找
 *
 * @author chen
 * @since 2020/6/19 15:52.
 */
public interface SysDictionaryErrorResponses extends ErrorResponse {


    static ErrorResponse sysDictionaryNotExist() {
        String message = I18nHelper.getMessage(SysUserErrorResponses.class.getSimpleName() + ".sysDictionaryNotExist", "系统字典不存在");
        return ErrorResponse.create(message);
    }

    static ErrorResponse createSysDictionaryFailure() {
        String message = I18nHelper.getMessage(SysDictionaryErrorResponses.class.getSimpleName() + ".createSysDictionaryFailure", "创建系统字典失败");
        return ErrorResponse.create(message);
    }
    
    static ErrorResponse updateSysDictionaryFailure() {
        String message = I18nHelper.getMessage(SysDictionaryErrorResponses.class.getSimpleName() + ".updateSysDictionaryFailure", "修改系统字典失败");
        return ErrorResponse.create(message);
    }

    static ErrorResponse deleteSysDictionaryFailure() {
        String message = I18nHelper.getMessage(SysDictionaryErrorResponses.class.getSimpleName() + ".deleteSysDictionaryFailure", "删除系统字典失败");
        return ErrorResponse.create(message);
    }

    static ErrorResponse sysDictionaryIdCanNotNull() {
        String message = I18nHelper.getMessage(SysDictionaryErrorResponses.class.getSimpleName() + ".sysDictionaryIdCanNotNull", "系统字典ID不能为空");
        return ErrorResponse.create(message);
    }


    static ErrorResponse sysDictionaryCodeCanNotBlank() {
        String message = I18nHelper.getMessage(SysDictionaryErrorResponses.class.getSimpleName() + ".sysDictionaryCodeCanNotBlank", "系统字典的编码不能为空白字符串");
        return ErrorResponse.create(message);
    }
    
    static ErrorResponse sysDictionaryNameCanNotBlank() {
        String message = I18nHelper.getMessage(SysDictionaryErrorResponses.class.getSimpleName() + ".sysDictionaryNameCanNotBlank", "系统字典名称不能为空白字符串");
        return ErrorResponse.create(message);
    }
    
    static ErrorResponse sysDictionaryTypeCanNotNull() {
        String message = I18nHelper.getMessage(SysDictionaryErrorResponses.class.getSimpleName() + ".sysDictionaryTypeCanNotNull", "系统字典类型不能为空");
        return ErrorResponse.create(message);
    }

    static ErrorResponse sysDictionaryStatusCanNotNull() {
        String message = I18nHelper.getMessage(SysDictionaryErrorResponses.class.getSimpleName() + ".sysDictionaryStatusCanNotNull", "系统字典状态不能为空");
        return ErrorResponse.create(message);
    }

}
