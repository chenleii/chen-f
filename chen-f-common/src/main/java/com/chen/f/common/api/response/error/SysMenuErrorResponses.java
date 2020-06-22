package com.chen.f.common.api.response.error;

import com.chen.f.core.api.response.error.ErrorResponse;
import com.chen.f.core.configuration.i18n.I18nHelper;

/**
 * 系统菜单的错误响应
 * <p>
 * 继承{@link com.chen.f.core.api.response.error.ErrorResponse}方便查找
 *
 * @author chen
 * @since 2020/6/19 15:52.
 */
public interface SysMenuErrorResponses extends ErrorResponse {


    static ErrorResponse sysMenuNotExist() {
        String message = I18nHelper.getMessage(SysUserErrorResponses.class.getSimpleName() + ".sysMenuNotExist", "系统菜单不存在");
        return ErrorResponse.create(message);
    }

    static ErrorResponse createSysMenuFailure() {
        String message = I18nHelper.getMessage(SysMenuErrorResponses.class.getSimpleName() + ".createSysMenuFailure", "创建系统菜单失败");
        return ErrorResponse.create(message);
    }
    
    static ErrorResponse updateSysMenuFailure() {
        String message = I18nHelper.getMessage(SysMenuErrorResponses.class.getSimpleName() + ".updateSysMenuFailure", "修改系统菜单失败");
        return ErrorResponse.create(message);
    }

    static ErrorResponse deleteSysMenuFailure() {
        String message = I18nHelper.getMessage(SysMenuErrorResponses.class.getSimpleName() + ".deleteSysMenuFailure", "删除系统菜单失败");
        return ErrorResponse.create(message);
    }

    static ErrorResponse sysMenuIdCanNotNull() {
        String message = I18nHelper.getMessage(SysMenuErrorResponses.class.getSimpleName() + ".sysMenuIdCanNotNull", "系统菜单ID不能为空");
        return ErrorResponse.create(message);
    }
    
    static ErrorResponse sysMenuNameCanNotBlank() {
        String message = I18nHelper.getMessage(SysMenuErrorResponses.class.getSimpleName() + ".sysMenuNameCanNotBlank", "系统菜单名称不能为空白字符串");
        return ErrorResponse.create(message);
    }
    
    static ErrorResponse sysMenuTypeCanNotNull() {
        String message = I18nHelper.getMessage(SysMenuErrorResponses.class.getSimpleName() + ".sysMenuTypeCanNotNull", "系统菜单类型不能为空");
        return ErrorResponse.create(message);
    }

    static ErrorResponse sysMenuStatusCanNotNull() {
        String message = I18nHelper.getMessage(SysMenuErrorResponses.class.getSimpleName() + ".sysMenuStatusCanNotNull", "系统菜单状态不能为空");
        return ErrorResponse.create(message);
    }

}
