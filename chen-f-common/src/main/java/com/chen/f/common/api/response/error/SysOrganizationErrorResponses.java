package com.chen.f.common.api.response.error;

import com.chen.f.core.api.response.error.ErrorResponse;
import com.chen.f.core.configuration.i18n.I18nHelper;

/**
 * 系统组织的错误响应
 * <p>
 * 继承{@link com.chen.f.core.api.response.error.ErrorResponse}方便查找
 *
 * @author chen
 * @since 2020/6/19 15:52.
 */
public interface SysOrganizationErrorResponses extends ErrorResponse {


    static ErrorResponse sysOrganizationNotExist() {
        String message = I18nHelper.getMessage(SysUserErrorResponses.class.getSimpleName() + ".sysOrganizationNotExist", "系统组织不存在");
        return ErrorResponse.create(message);
    }

    static ErrorResponse createSysOrganizationFailure() {
        String message = I18nHelper.getMessage(SysOrganizationErrorResponses.class.getSimpleName() + ".createSysOrganizationFailure", "创建系统组织失败");
        return ErrorResponse.create(message);
    }
    
    static ErrorResponse updateSysOrganizationFailure() {
        String message = I18nHelper.getMessage(SysOrganizationErrorResponses.class.getSimpleName() + ".updateSysOrganizationFailure", "修改系统组织失败");
        return ErrorResponse.create(message);
    }

    static ErrorResponse deleteSysOrganizationFailure() {
        String message = I18nHelper.getMessage(SysOrganizationErrorResponses.class.getSimpleName() + ".deleteSysOrganizationFailure", "删除系统组织失败");
        return ErrorResponse.create(message);
    }

    static ErrorResponse sysOrganizationIdCanNotNull() {
        String message = I18nHelper.getMessage(SysOrganizationErrorResponses.class.getSimpleName() + ".sysOrganizationIdCanNotNull", "系统组织ID不能为空");
        return ErrorResponse.create(message);
    }
    
    static ErrorResponse sysOrganizationNameCanNotBlank() {
        String message = I18nHelper.getMessage(SysOrganizationErrorResponses.class.getSimpleName() + ".sysOrganizationNameCanNotBlank", "系统组织名称不能为空白字符串");
        return ErrorResponse.create(message);
    }

    static ErrorResponse sysOrganizationFullNameCanNotBlank() {
        String message = I18nHelper.getMessage(SysOrganizationErrorResponses.class.getSimpleName() + ".sysOrganizationFullNameCanNotBlank", "系统组织全称不能为空");
        return ErrorResponse.create(message);
    }
    
    static ErrorResponse sysOrganizationTypeCanNotNull() {
        String message = I18nHelper.getMessage(SysOrganizationErrorResponses.class.getSimpleName() + ".sysOrganizationTypeCanNotNull", "系统组织类型不能为空");
        return ErrorResponse.create(message);
    }

    static ErrorResponse sysOrganizationStatusCanNotNull() {
        String message = I18nHelper.getMessage(SysOrganizationErrorResponses.class.getSimpleName() + ".sysOrganizationStatusCanNotNull", "系统组织状态不能为空");
        return ErrorResponse.create(message);
    }
}
