package com.chen.f.common.api.response.error;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 带有堆栈跟踪信息的错误响应包装类
 *
 * @author chen
 * @since 2018/12/29 18:22.
 */
public class StackTraceErrorResponseWrapper implements ErrorResponse {

    private ErrorResponse errorResponse;

    private String stackTrace;

    public StackTraceErrorResponseWrapper(ErrorResponse errorResponse, Throwable throwable) {
        this.errorResponse = errorResponse;
        try (
                StringWriter stackTrace = new StringWriter();
                PrintWriter printWriter = new PrintWriter(stackTrace);
        ) {
            throwable.printStackTrace(printWriter);
            stackTrace.flush();
            this.stackTrace = stackTrace.toString();
        } catch (IOException e) {
            //忽略
        }
    }

    public static StackTraceErrorResponseWrapper of(ErrorResponse errorResponse, Throwable throwable) {
        return new StackTraceErrorResponseWrapper(errorResponse, throwable);
    }

    public String getStackTrace() {
        return this.stackTrace;
    }

    @Override
    public String getErrorCode() {
        return this.errorResponse.getErrorCode();
    }

    @Override
    public String getErrorMsg() {
        return this.errorResponse.getErrorMsg();
    }

    @Override
    public Object getError() {
        return this.errorResponse.getError();
    }
}
