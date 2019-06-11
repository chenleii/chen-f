package com.chen.f.core.api.response.error;

/**
 * @author chen
 * @since 2018/12/29 18:35.
 */
public abstract class AbstractErrorResponse implements ErrorResponse {

    /**
     * 错误状态码
     */
    private final String errorCode;

    /**
     * 错误信息
     */
    private final String errorMsg;

    /**
     * 错误
     * 用于描述更为详细的错误信息
     */
    private final Object error;

    /**
     * @param errorMsg 错误信息
     */
    public AbstractErrorResponse(String errorMsg) {
        this(ErrorResponse.DEFAULT_ERROR_CODE, errorMsg, null);
    }

    /**
     * @param errorCode 错误码
     * @param errorMsg  错误信息
     */
    public AbstractErrorResponse(String errorCode, String errorMsg) {
        this(errorCode, errorMsg, null);
    }

    /**
     * @param errorCode 错误码
     * @param errorMsg  错误信息
     * @param error     错误
     */
    public AbstractErrorResponse(String errorCode, String errorMsg, Object error) {
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
