package com.chen.f.spring.boot.configuration.web;

import com.chen.f.core.api.response.error.ErrorResponse;
import com.chen.f.core.api.response.error.StackTraceErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 抽象异常处理
 * <p>
 * 1.忽略spring-security异常
 * 2.包装方法(是否返回堆栈跟踪)
 *
 * @author chen
 * @since 2019/1/10 12:53.
 */
public abstract class AbstractExceptionHandle {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 是否开启堆栈跟踪
     */
    private boolean enableStackTrace = true;

    public void setEnableStackTrace(boolean enableStackTrace) {
        this.enableStackTrace = enableStackTrace;
    }

    public boolean isEnableStackTrace() {
        return enableStackTrace;
    }


    /**
     * 包装错误响应
     *
     * @param errorResponse 原错误响应
     * @param exception     异常信息
     * @return 包装的错误响应
     */
    protected ErrorResponse wrap(ErrorResponse errorResponse, Exception exception) {
        if (isEnableStackTrace()) {
            return new StackTraceErrorResponse(errorResponse, exception);
        }
        return errorResponse;
    }
}
