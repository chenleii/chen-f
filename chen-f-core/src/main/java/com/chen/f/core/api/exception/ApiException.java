package com.chen.f.core.api.exception;


import com.chen.f.core.api.response.error.ErrorResponse;

/**
 * api异常(编码时的错误都该抛出该异常
 *
 * @author chen
 * @date 2018/10/29 18:50.
 */
public class ApiException extends RuntimeException {

    private final ErrorResponse errorResponse;

    public ApiException(ErrorResponse errorResponse) {
        super(errorResponse.getErrorMsg());
        this.errorResponse = errorResponse;
    }

    public ApiException(ErrorResponse errorResponse, Throwable cause) {
        super(errorResponse.getErrorMsg(), cause);
        this.errorResponse = errorResponse;
    }

    public ApiException(ErrorResponse errorResponse, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(errorResponse.getErrorMsg(), cause, enableSuppression, writableStackTrace);
        this.errorResponse = errorResponse;
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }

    //@Override
    //public Throwable fillInStackTrace() {
    //    return this;
    //}
    //
    //public synchronized Throwable realFillInStackTrace() {
    //    return super.fillInStackTrace();
    //}
}
