package com.chen.f.common.api.exception;


import com.chen.f.common.api.response.error.ErrorResponse;

/**
 * API异常
 * <p>
 * 编码时需要返回到前端的错误，可以抛出该异常。
 *
 * @author chen
 * @date 2018/10/29 18:50.
 */
public class ApiException extends RuntimeException {

    /**
     * HTTP状态码
     */
    private int httpStatusCode = 400;

    /**
     * 错误响应
     */
    private final ErrorResponse errorResponse;

    public ApiException(ErrorResponse errorResponse) {
        super(errorResponse.getErrorMsg());
        this.errorResponse = errorResponse;
    }

    public ApiException(int httpStatusCode, ErrorResponse errorResponse) {
        super(errorResponse.getErrorMsg());
        this.httpStatusCode = httpStatusCode;
        this.errorResponse = errorResponse;
    }
    
    public ApiException(ErrorResponse errorResponse, Throwable cause) {
        super(errorResponse.getErrorMsg(), cause);
        this.errorResponse = errorResponse;
    }

    public ApiException(int httpStatusCode,ErrorResponse errorResponse, Throwable cause) {
        super(errorResponse.getErrorMsg(), cause);
        this.httpStatusCode = httpStatusCode;
        this.errorResponse = errorResponse;
    }
    
    public ApiException(ErrorResponse errorResponse, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(errorResponse.getErrorMsg(), cause, enableSuppression, writableStackTrace);
        this.errorResponse = errorResponse;
    }

    public ApiException(int httpStatusCode,ErrorResponse errorResponse, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(errorResponse.getErrorMsg(), cause, enableSuppression, writableStackTrace);
        this.httpStatusCode = httpStatusCode;
        this.errorResponse = errorResponse;
    }


    public int getHttpStatusCode() {
        return httpStatusCode;
    }
    
    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }
    
}
