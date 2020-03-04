package com.chen.f.common.api.response.error;

/**
 * 错误响应
 *
 * @author chen
 * @since 2018/11/2 19:43.
 */
public interface ErrorResponse {

    /**
     * 默认的错误状态码
     */
    String DEFAULT_ERROR_CODE = "error";


    /**
     * 创建错误响应
     *
     * @param errorMsg 错误信息
     * @return 错误响应
     */
    static ErrorResponse create(String errorMsg) {
        return create(DEFAULT_ERROR_CODE, errorMsg, null);
    }

    /**
     * 创建错误响应
     *
     * @param errorCode 错误码
     * @param errorMsg  错误信息
     * @return 错误响应
     */
    static ErrorResponse create(String errorCode, String errorMsg) {
        return create(errorCode, errorMsg, null);
    }

    /**
     * 创建错误响应
     *
     * @param errorMsg 错误信息
     * @param error    错误内容
     * @return 错误响应
     */
    static ErrorResponse create(String errorMsg, Object error) {
        return create(DEFAULT_ERROR_CODE, errorMsg, error);
    }

    /**
     * 创建错误响应
     *
     * @param errorCode 错误码
     * @param errorMsg  错误信息
     * @param error     错误内容
     * @return 错误响应
     */
    static ErrorResponse create(String errorCode, String errorMsg, Object error) {
        return new ErrorResponse() {
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
        };
    }
    
    
    /**
     * 错误状态码
     *
     * @return 错误状态码
     */
    String getErrorCode();

    /**
     * 错误信息
     *
     * @return 错误信息
     */
    String getErrorMsg();

    /**
     * 错误
     * 用于描述更为详细的错误信息
     *
     * @return 错误内容
     */
    Object getError();


}
