package com.chen.f.common.api.response.error;

import com.chen.f.core.api.response.error.ErrorResponse;
import com.chen.f.core.configuration.i18n.I18nHelper;

/**
 * 系统角色的错误响应
 * <p>
 * 继承{@link com.chen.f.core.api.response.error.ErrorResponse}方便查找
 *
 * @author chen
 * @since 2020/6/19 15:52.
 */
public interface SysRoleErrorResponses extends ErrorResponse {


    static ErrorResponse sysRoleNotExist() {
        String message = I18nHelper.getMessage(SysUserErrorResponses.class.getSimpleName() + ".sysRoleNotExist", "系统角色不存在");
        return ErrorResponse.create(message);
    }

    static ErrorResponse createSysRoleFailure() {
        String message = I18nHelper.getMessage(SysRoleErrorResponses.class.getSimpleName() + ".createSysRoleFailure", "创建系统角色失败");
        return ErrorResponse.create(message);
    }
    
    static ErrorResponse updateSysRoleFailure() {
        String message = I18nHelper.getMessage(SysRoleErrorResponses.class.getSimpleName() + ".updateSysRoleFailure", "修改系统角色失败");
        return ErrorResponse.create(message);
    }

    static ErrorResponse deleteSysRoleFailure() {
        String message = I18nHelper.getMessage(SysRoleErrorResponses.class.getSimpleName() + ".deleteSysRoleFailure", "删除系统角色失败");
        return ErrorResponse.create(message);
    }

    static ErrorResponse sysRoleIdCanNotNull() {
        String message = I18nHelper.getMessage(SysRoleErrorResponses.class.getSimpleName() + ".sysRoleIdCanNotNull", "系统角色ID不能为空");
        return ErrorResponse.create(message);
    }


    static ErrorResponse sysRoleCodeCanNotBlank() {
        String message = I18nHelper.getMessage(SysRoleErrorResponses.class.getSimpleName() + ".sysRoleCodeCanNotBlank", "系统角色的编码不能为空白字符串");
        return ErrorResponse.create(message);
    }
    
    static ErrorResponse sysRoleNameCanNotBlank() {
        String message = I18nHelper.getMessage(SysRoleErrorResponses.class.getSimpleName() + ".sysRoleNameCanNotBlank", "系统角色名称不能为空白字符串");
        return ErrorResponse.create(message);
    }

    static ErrorResponse sysRoleStatusCanNotNull() {
        String message = I18nHelper.getMessage(SysRoleErrorResponses.class.getSimpleName() + ".sysRoleStatusCanNotNull", "系统角色状态不能为空");
        return ErrorResponse.create(message);
    }

}
