package com.chen.f.core.api.response.error;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 堆栈跟踪信息的错误响应
 *
 * @author chen
 * @since 2018/12/29 18:22.
 */
public class StackTraceErrorResponse extends AbstractErrorResponse {

    private String stackTrace;

    public StackTraceErrorResponse(String errorCode, String errorMsg, String stackTrace) {
        super(errorCode, errorMsg);
        this.stackTrace = stackTrace;
    }

    public StackTraceErrorResponse(String errorCode, String errorMsg, Object error, String stackTrace) {
        super(errorCode, errorMsg, error);
        this.stackTrace = stackTrace;
    }

    public StackTraceErrorResponse(ErrorResponse errorResponse, Throwable throwable) {
        super(errorResponse.getErrorCode(), errorResponse.getErrorMsg(), errorResponse.getError());
        try (
                StringWriter stackTrace = new StringWriter();
                PrintWriter printWriter = new PrintWriter(stackTrace);
        ) {
            throwable.printStackTrace(printWriter);
            stackTrace.flush();
            this.stackTrace = stackTrace.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String getStackTrace() {
        return stackTrace;
    }

}
