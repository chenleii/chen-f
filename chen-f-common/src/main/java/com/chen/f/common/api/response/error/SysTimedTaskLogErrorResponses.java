package com.chen.f.common.api.response.error;

import com.chen.f.core.api.response.error.ErrorResponse;
import com.chen.f.core.configuration.i18n.I18nHelper;

/**
 * 系统定时任务日志的错误响应
 * <p>
 * 继承{@link com.chen.f.core.api.response.error.ErrorResponse}方便查找
 *
 * @author chen
 * @since 2020/6/19 15:52.
 */
public interface SysTimedTaskLogErrorResponses extends ErrorResponse {
    
    static ErrorResponse sysTimedTaskLogIdCanNotNull() {
        String message = I18nHelper.getMessage(SysTimedTaskLogErrorResponses.class.getSimpleName() + ".sysTimedTaskLogIdCanNotNull", "系统定时任务日志ID不能为空");
        return ErrorResponse.create(message);
    }

}
