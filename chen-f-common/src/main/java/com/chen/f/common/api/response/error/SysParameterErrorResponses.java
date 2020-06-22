package com.chen.f.common.api.response.error;

import com.chen.f.core.api.response.error.ErrorResponse;
import com.chen.f.core.configuration.i18n.I18nHelper;

/**
 * 系统参数的错误响应
 * <p>
 * 继承{@link com.chen.f.core.api.response.error.ErrorResponse}方便查找
 *
 * @author chen
 * @since 2020/6/19 15:52.
 */
public interface SysParameterErrorResponses extends ErrorResponse {


    static ErrorResponse sysParameterNotExist() {
        String message = I18nHelper.getMessage(SysUserErrorResponses.class.getSimpleName() + ".sysParameterNotExist", "系统参数不存在");
        return ErrorResponse.create(message);
    }

    static ErrorResponse createSysParameterFailure() {
        String message = I18nHelper.getMessage(SysParameterErrorResponses.class.getSimpleName() + ".createSysParameterFailure", "创建系统参数失败");
        return ErrorResponse.create(message);
    }
    
    static ErrorResponse updateSysParameterFailure() {
        String message = I18nHelper.getMessage(SysParameterErrorResponses.class.getSimpleName() + ".updateSysParameterFailure", "修改系统参数失败");
        return ErrorResponse.create(message);
    }

    static ErrorResponse deleteSysParameterFailure() {
        String message = I18nHelper.getMessage(SysParameterErrorResponses.class.getSimpleName() + ".deleteSysParameterFailure", "删除系统参数失败");
        return ErrorResponse.create(message);
    }

    static ErrorResponse sysParameterIdCanNotNull() {
        String message = I18nHelper.getMessage(SysParameterErrorResponses.class.getSimpleName() + ".sysParameterIdCanNotNull", "系统参数ID不能为空");
        return ErrorResponse.create(message);
    }


    static ErrorResponse sysParameterCodeCanNotBlank() {
        String message = I18nHelper.getMessage(SysParameterErrorResponses.class.getSimpleName() + ".sysParameterCodeCanNotBlank", "系统参数的编码不能为空白字符串");
        return ErrorResponse.create(message);
    }
    
    static ErrorResponse sysParameterNameCanNotBlank() {
        String message = I18nHelper.getMessage(SysParameterErrorResponses.class.getSimpleName() + ".sysParameterNameCanNotBlank", "系统参数名称不能为空白字符串");
        return ErrorResponse.create(message);
    }
    
    static ErrorResponse sysParameterValueCanNotBlank() {
        String message = I18nHelper.getMessage(SysParameterErrorResponses.class.getSimpleName() + ".sysParameterValueCanNotBlank", "系统参数值不能为空白字符串");
        return ErrorResponse.create(message);
    }

    static ErrorResponse sysParameterValueTypeCanNotNull() {
        String message = I18nHelper.getMessage(SysParameterErrorResponses.class.getSimpleName() + ".sysParameterValueTypeCanNotNull", "系统参数值类型不能为空");
        return ErrorResponse.create(message);
    }
    
    static ErrorResponse sysParameterTypeCanNotNull() {
        String message = I18nHelper.getMessage(SysParameterErrorResponses.class.getSimpleName() + ".sysParameterTypeCanNotNull", "系统参数类型不能为空");
        return ErrorResponse.create(message);
    }

    static ErrorResponse sysParameterStatusCanNotNull() {
        String message = I18nHelper.getMessage(SysParameterErrorResponses.class.getSimpleName() + ".sysParameterStatusCanNotNull", "系统参数状态不能为空");
        return ErrorResponse.create(message);
    }
}
