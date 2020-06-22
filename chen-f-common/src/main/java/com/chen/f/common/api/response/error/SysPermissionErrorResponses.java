package com.chen.f.common.api.response.error;

import com.chen.f.core.api.response.error.ErrorResponse;
import com.chen.f.core.configuration.i18n.I18nHelper;

/**
 * 系统权限的错误响应
 * <p>
 * 继承{@link com.chen.f.core.api.response.error.ErrorResponse}方便查找
 *
 * @author chen
 * @since 2020/6/19 15:52.
 */
public interface SysPermissionErrorResponses extends ErrorResponse {


    static ErrorResponse sysPermissionNotExist() {
        String message = I18nHelper.getMessage(SysUserErrorResponses.class.getSimpleName() + ".sysPermissionNotExist", "系统权限不存在");
        return ErrorResponse.create(message);
    }

    static ErrorResponse createSysPermissionFailure() {
        String message = I18nHelper.getMessage(SysPermissionErrorResponses.class.getSimpleName() + ".createSysPermissionFailure", "创建系统权限失败");
        return ErrorResponse.create(message);
    }
    
    static ErrorResponse updateSysPermissionFailure() {
        String message = I18nHelper.getMessage(SysPermissionErrorResponses.class.getSimpleName() + ".updateSysPermissionFailure", "修改系统权限失败");
        return ErrorResponse.create(message);
    }

    static ErrorResponse deleteSysPermissionFailure() {
        String message = I18nHelper.getMessage(SysPermissionErrorResponses.class.getSimpleName() + ".deleteSysPermissionFailure", "删除系统权限失败");
        return ErrorResponse.create(message);
    }

    static ErrorResponse sysPermissionIdCanNotNull() {
        String message = I18nHelper.getMessage(SysPermissionErrorResponses.class.getSimpleName() + ".sysPermissionIdCanNotNull", "系统权限ID不能为空");
        return ErrorResponse.create(message);
    }


    static ErrorResponse sysPermissionCodeCanNotBlank() {
        String message = I18nHelper.getMessage(SysPermissionErrorResponses.class.getSimpleName() + ".sysPermissionCodeCanNotBlank", "系统权限的编码不能为空白字符串");
        return ErrorResponse.create(message);
    }
    
    static ErrorResponse sysPermissionNameCanNotBlank() {
        String message = I18nHelper.getMessage(SysPermissionErrorResponses.class.getSimpleName() + ".sysPermissionNameCanNotBlank", "系统权限名称不能为空白字符串");
        return ErrorResponse.create(message);
    }
    
    static ErrorResponse sysPermissionTypeCanNotNull() {
        String message = I18nHelper.getMessage(SysPermissionErrorResponses.class.getSimpleName() + ".sysPermissionTypeCanNotNull", "系统权限类型不能为空");
        return ErrorResponse.create(message);
    }

    static ErrorResponse sysPermissionStatusCanNotNull() {
        String message = I18nHelper.getMessage(SysPermissionErrorResponses.class.getSimpleName() + ".sysPermissionStatusCanNotNull", "系统权限状态不能为空");
        return ErrorResponse.create(message);
    }

}
