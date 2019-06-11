package com.chen.f.core.api.response.error.basic;

import com.chen.f.core.api.response.error.ErrorResponse;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 基本错误响应
 *
 * @author chen
 * @since 2018/11/2 19:47.
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum BasicErrorResponseEnum implements ErrorResponse {

    //服务器异常
    SERVER_EXCEPTION("server_exception", "服务器异常"),

    ;

    /**
     * 错误状态码
     */
    public final String errorCode;

    /**
     * 错误信息
     */
    public final String errorMsg;

    /**
     * 错误
     */
    public final Object error;

    BasicErrorResponseEnum(String errorMsg) {
        this(ErrorResponse.DEFAULT_ERROR_CODE, errorMsg, null);
    }

    BasicErrorResponseEnum(String errorCode, String errorMsg) {
        this(errorCode, errorMsg, null);
    }

    BasicErrorResponseEnum(String errorCode, String errorMsg, Object error) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.error = error;
    }

    @Override
    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String getErrorMsg() {
        return errorMsg;
    }

    @Override
    public Object getError() {
        return error;
    }
}

