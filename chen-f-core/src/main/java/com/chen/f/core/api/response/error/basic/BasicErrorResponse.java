package com.chen.f.core.api.response.error.basic;

import com.chen.f.core.api.response.error.AbstractErrorResponse;
import org.springframework.validation.FieldError;

import java.util.List;

/**
 * 基本错误响应
 *
 * @author chen
 * @since 2018/11/2 19:47.
 */
public class BasicErrorResponse extends AbstractErrorResponse {


    public BasicErrorResponse(String errorCode, String errorMsg) {
        super(errorCode, errorMsg);
    }

    public BasicErrorResponse(String errorCode, String errorMsg, Object error) {
        super(errorCode, errorMsg, error);
    }

    /**
     * 服务器异常
     */
    public static BasicErrorResponse serverException() {
        return new BasicErrorResponse("server_exception", "服务器异常");
    }

    /**
     * 参数错误
     */
    public static BasicErrorResponse parameterError() {
        return new BasicErrorResponse("parameter_error", "参数错误");
    }

    /**
     * 参数格式错误
     */
    public static BasicErrorResponse parameterFormatError() {
        return new BasicErrorResponse("parameter_format_error", "参数格式错误");
    }

    /**
     * 参数缺失错误
     */
    public static BasicErrorResponse parameterMissingError(String parameterName) {
        return new BasicErrorResponse("parameter_missing_error", String.format("参数缺失[%s]", parameterName));
    }

    /**
     * 参数验证错误
     */
    public static BasicErrorResponse parameterVerificationError(List<FieldError> error) {
        return new BasicErrorResponse("parameter_verification_error", "参数验证错误", error);
    }

    /**
     * 没有找到api
     */
    public static BasicErrorResponse notFoundApi(String api) {
        return new BasicErrorResponse("not_found_api", String.format("没有找到api[%s]", api));
    }
}
