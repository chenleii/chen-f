package com.chen.f.common.api.response.error;

import com.chen.f.core.api.response.error.ErrorResponse;
import com.chen.f.core.configuration.i18n.I18nHelper;

/**
 * 系统定时任务的错误响应
 * <p>
 * 继承{@link com.chen.f.core.api.response.error.ErrorResponse}方便查找
 *
 * @author chen
 * @since 2020/6/19 15:52.
 */
public interface SysTimedTaskErrorResponses extends ErrorResponse {


    static ErrorResponse sysTimedTaskNotExist() {
        String message = I18nHelper.getMessage(SysUserErrorResponses.class.getSimpleName() + ".sysTimedTaskNotExist", "系统定时任务不存在");
        return ErrorResponse.create(message);
    }
    
    static ErrorResponse sysTimedTaskJobClassNotExist() {
        String message = I18nHelper.getMessage(SysTimedTaskErrorResponses.class.getSimpleName() + ".sysTimedTaskJobClassNotExist", "系统定时任务类不存在");
        return ErrorResponse.create(message);
    }

    static ErrorResponse sysTimedTaskJobClassNotImplementJob(Class<?> jobClass) {
        String message = I18nHelper.getMessage(SysTimedTaskErrorResponses.class.getSimpleName() + ".sysTimedTaskJobClassNotImplementJob", 
                new Object[]{jobClass.getName()},"系统定时任务[{0}]没有继承Job类");
        return ErrorResponse.create(message);
    }
    
    static ErrorResponse executionSysTimedTaskFailure() {
        String message = I18nHelper.getMessage(SysTimedTaskErrorResponses.class.getSimpleName() + ".executionSysTimedTaskFailure", "执行定时任务失败");
        return ErrorResponse.create(message);
    }
    
    static ErrorResponse createSysTimedTaskFailure() {
        String message = I18nHelper.getMessage(SysTimedTaskErrorResponses.class.getSimpleName() + ".createSysTimedTaskFailure", "创建系统定时任务失败");
        return ErrorResponse.create(message);
    }
    
    static ErrorResponse updateSysTimedTaskFailure() {
        String message = I18nHelper.getMessage(SysTimedTaskErrorResponses.class.getSimpleName() + ".updateSysTimedTaskFailure", "修改系统定时任务失败");
        return ErrorResponse.create(message);
    }

    static ErrorResponse deleteSysTimedTaskFailure() {
        String message = I18nHelper.getMessage(SysTimedTaskErrorResponses.class.getSimpleName() + ".deleteSysTimedTaskFailure", "删除系统定时任务失败");
        return ErrorResponse.create(message);
    }

    static ErrorResponse sysTimedTaskIdCanNotNull() {
        String message = I18nHelper.getMessage(SysTimedTaskErrorResponses.class.getSimpleName() + ".sysTimedTaskIdCanNotNull", "系统定时任务ID不能为空");
        return ErrorResponse.create(message);
    }


    static ErrorResponse sysTimedTaskCodeCanNotBlank() {
        String message = I18nHelper.getMessage(SysTimedTaskErrorResponses.class.getSimpleName() + ".sysTimedTaskCodeCanNotBlank", "系统定时任务的编码不能为空白字符串");
        return ErrorResponse.create(message);
    }
    
    static ErrorResponse sysTimedTaskNameCanNotBlank() {
        String message = I18nHelper.getMessage(SysTimedTaskErrorResponses.class.getSimpleName() + ".sysTimedTaskNameCanNotBlank", "系统定时任务名称不能为空白字符串");
        return ErrorResponse.create(message);
    }

    static ErrorResponse sysTimedTaskJobClassNameCanNotBlank() {
        String message = I18nHelper.getMessage(SysTimedTaskErrorResponses.class.getSimpleName() + ".sysTimedTaskJobClassNameCanNotBlank", "系统定时任务类名不能为空白字符串");
        return ErrorResponse.create(message);
    }

    static ErrorResponse sysTimedTaskCronExpressionCanNotBlank() {
        String message = I18nHelper.getMessage(SysTimedTaskErrorResponses.class.getSimpleName() + ".sysTimedTaskCronExpressionCanNotBlank", "系统定时任务状态不能为空白字符串");
        return ErrorResponse.create(message);
    }
    
    static ErrorResponse sysTimedTaskTypeCanNotNull() {
        String message = I18nHelper.getMessage(SysTimedTaskErrorResponses.class.getSimpleName() + ".sysTimedTaskTypeCanNotNull", "系统定时任务类型不能为空");
        return ErrorResponse.create(message);
    }

    static ErrorResponse sysTimedTaskStatusCanNotNull() {
        String message = I18nHelper.getMessage(SysTimedTaskErrorResponses.class.getSimpleName() + ".sysTimedTaskStatusCanNotNull", "系统定时任务状态不能为空");
        return ErrorResponse.create(message);
    }

}
