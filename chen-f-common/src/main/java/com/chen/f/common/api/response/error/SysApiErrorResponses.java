package com.chen.f.common.api.response.error;

import com.chen.f.core.api.response.error.ErrorResponse;
import com.chen.f.core.configuration.i18n.I18nHelper;

/**
 * 系统接口的错误响应
 * <p>
 * 继承{@link com.chen.f.core.api.response.error.ErrorResponse}方便查找
 *
 * @author chen
 * @since 2020/6/19 15:52.
 */
public interface SysApiErrorResponses extends ErrorResponse {


    static ErrorResponse sysApiNotExist() {
        String message = I18nHelper.getMessage(SysUserErrorResponses.class.getSimpleName() + ".sysApiNotExist", "系统接口不存在");
        return ErrorResponse.create(message);
    }

    static ErrorResponse createSysApiFailure() {
        String message = I18nHelper.getMessage(SysApiErrorResponses.class.getSimpleName() + ".createSysApiFailure", "创建系统接口失败");
        return ErrorResponse.create(message);
    }
    
    static ErrorResponse updateSysApiFailure() {
        String message = I18nHelper.getMessage(SysApiErrorResponses.class.getSimpleName() + ".updateSysApiFailure", "修改系统接口失败");
        return ErrorResponse.create(message);
    }

    static ErrorResponse deleteSysApiFailure() {
        String message = I18nHelper.getMessage(SysApiErrorResponses.class.getSimpleName() + ".deleteSysApiFailure", "删除系统接口失败");
        return ErrorResponse.create(message);
    }

    static ErrorResponse sysApiIdCanNotNull() {
        String message = I18nHelper.getMessage(SysApiErrorResponses.class.getSimpleName() + ".sysApiIdCanNotNull", "系统接口ID不能为空");
        return ErrorResponse.create(message);
    }
    
    static ErrorResponse sysApiNameCanNotBlank() {
        String message = I18nHelper.getMessage(SysApiErrorResponses.class.getSimpleName() + ".sysApiNameCanNotBlank", "系统接口名称不能为空白字符串");
        return ErrorResponse.create(message);
    }

    static ErrorResponse sysApiUrlCanNotBlank() {
        String message = I18nHelper.getMessage(SysApiErrorResponses.class.getSimpleName() + ".sysApiUrlCanNotBlank", "系统接口URL不能为空白字符串");
        return ErrorResponse.create(message);
    }

    static ErrorResponse sysApiHttpMethodCanNotNull() {
        String message = I18nHelper.getMessage(SysApiErrorResponses.class.getSimpleName() + ".sysApiHttpMethodCanNotNull", "系统接口HTTP方法不能为空");
        return ErrorResponse.create(message);
    }
    
    static ErrorResponse sysApiTypeCanNotNull() {
        String message = I18nHelper.getMessage(SysApiErrorResponses.class.getSimpleName() + ".sysApiTypeCanNotNull", "系统接口类型不能为空");
        return ErrorResponse.create(message);
    }

    static ErrorResponse sysApiStatusCanNotNull() {
        String message = I18nHelper.getMessage(SysApiErrorResponses.class.getSimpleName() + ".sysApiStatusCanNotNull", "系统接口状态不能为空");
        return ErrorResponse.create(message);
    }
}
