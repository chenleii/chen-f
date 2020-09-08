package com.chen.f.common.api.response.error;

import com.chen.f.core.api.response.error.ErrorResponse;
import com.chen.f.core.configuration.i18n.I18nHelper;

/**
 * 系统用户的错误响应
 * <p>
 * 继承{@link com.chen.f.core.api.response.error.ErrorResponse}方便查找
 *
 * @author chen
 * @since 2020/6/2 17:05.
 */
public interface SysUserErrorResponses extends ErrorResponse {

    static ErrorResponse sysUserNotExist() {
        String message = I18nHelper.getMessage(SysUserErrorResponses.class.getSimpleName() + ".sysUserNotExist", "系统用户不存在");
        return ErrorResponse.create(message);
    }
    
    static ErrorResponse operatedSysUserNotExist() {
        String message = I18nHelper.getMessage(SysUserErrorResponses.class.getSimpleName() + ".operatedSysUserNotExist", "操作的系统用户不存在");
        return ErrorResponse.create(message);
    }
    
    static ErrorResponse createdSysUserLevelCanNotGreaterThanOperatedSysUserLevel() {
        String message = I18nHelper.getMessage(SysUserErrorResponses.class.getSimpleName() + ".createdSysUserLevelCanNotGreaterThanOperatedSysUserLevel", "创建的用户级别不能大于操作的系统用户级别");
        return ErrorResponse.create(message);
    }

 
    static ErrorResponse operatedSysUserLevelMustGreaterThanUpdatedSysUserLevel() {
        String message = I18nHelper.getMessage(SysUserErrorResponses.class.getSimpleName() + ".operatedSysUserLevelMustGreaterThanUpdatedSysUserLevel", "操作的系统用户级别必须大于被修改的系统用户级别");
        return ErrorResponse.create(message);
    }
    
    static ErrorResponse updatedSysUserLevelCanNotGreaterThanOperatedSysUserLevel() {
        String message = I18nHelper.getMessage(SysUserErrorResponses.class.getSimpleName() + ".updatedSysUserLevelCanNotGreaterThanOperatedSysUserLevel", "修改后的系统用户级别不能大于操作的系统用户级别");
        return ErrorResponse.create(message);
    }

    static ErrorResponse createSysUserFailure() {
        String message = I18nHelper.getMessage(SysUserErrorResponses.class.getSimpleName() + ".createSysUserFailure", "创建系统用户失败");
        return ErrorResponse.create(message);
    }
    

    static ErrorResponse updateSysUserFailure() {
        String message = I18nHelper.getMessage(SysUserErrorResponses.class.getSimpleName() + ".updateSysUserFailure", "修改系统用户失败");
        return ErrorResponse.create(message);
    }
    

    static ErrorResponse deleteSysUserFailure() {
        String message = I18nHelper.getMessage(SysUserErrorResponses.class.getSimpleName() + ".deleteSysUserFailure", "删除系统用户失败");
        return ErrorResponse.create(message);
    }

    static ErrorResponse sysUserIdCanNotNull() {
        String message = I18nHelper.getMessage(SysUserErrorResponses.class.getSimpleName() + ".sysUserIdCanNotNull", "系统用户ID不能为空");
        return ErrorResponse.create(message);
    }
    
    static ErrorResponse sysUserNameCanNotBlank() {
        String message = I18nHelper.getMessage(SysUserErrorResponses.class.getSimpleName() + ".sysUserNameCanNotBlank", "系统用户名称不能为空白字符串");
        return ErrorResponse.create(message);
    }
    
    static ErrorResponse sysUserPasswordCanNotBlank() {
        String message = I18nHelper.getMessage(SysUserErrorResponses.class.getSimpleName() + ".sysUserPasswordCanNotBlank", "系统用户密码不能为空白字符串");
        return ErrorResponse.create(message);
    }


    static ErrorResponse sysUserStatusCanNotNull() {
        String message = I18nHelper.getMessage(SysUserErrorResponses.class.getSimpleName() + ".sysUserStatusCanNotNull", "系统用户状态不能为空");
        return ErrorResponse.create(message);
    }

    static ErrorResponse sysUserLevelCanNotNull() {
        String message = I18nHelper.getMessage(SysUserErrorResponses.class.getSimpleName() + ".sysUserLevelCanNotNull", "系统用户等级不能为空");
        return ErrorResponse.create(message);
    }
    
    static ErrorResponse operatedSysUserIdCanNotBlank() {
        String message = I18nHelper.getMessage(SysUserErrorResponses.class.getSimpleName() + ".sysUserOperatedSysUserIdCanNotBlank", "操作的系统用户ID不能为空白字符串");
        return ErrorResponse.create(message);
    }

    static ErrorResponse sysUserLastLoginDateTimeCanNotNull() {
        String message = I18nHelper.getMessage(SysUserErrorResponses.class.getSimpleName() + ".sysUserLastLoginDateTimeCanNotNull", "系统用户的最后登录日期时间不能为空");
        return ErrorResponse.create(message);
    }
}
