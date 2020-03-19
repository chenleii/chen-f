package com.chen.f.common.api.response.error.basic;

import com.chen.f.common.api.response.error.ErrorResponse;
import com.chen.f.common.configuration.helper.I18nHelper;
import org.springframework.validation.FieldError;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Set;

/**
 * 基本的错误响应
 * <p>
 * 继承{@link com.chen.f.common.api.response.error.ErrorResponse}方便查找
 *
 * @author chen
 * @since 2018/11/2 19:47.
 */
public interface BasicErrorResponses extends ErrorResponse {

    /**
     * 服务器异常
     */
    static ErrorResponse serverException() {
        String message = I18nHelper.getMessage(BasicErrorResponses.class.getName() + ".serverException", "服务器异常");
        return ErrorResponse.create(message);
    }

    /**
     * 参数错误
     */
    static ErrorResponse parameterError() {
        String message = I18nHelper.getMessage(BasicErrorResponses.class.getName() + ".parameterError", "参数错误");
        return ErrorResponse.create(message);
    }

    /**
     * 参数格式错误
     */
    static ErrorResponse parameterFormatError() {
        String message = I18nHelper.getMessage(BasicErrorResponses.class.getName() + ".parameterFormatError", "参数格式错误");
        return ErrorResponse.create(message);
    }

    /**
     * 参数缺失错误
     */
    static ErrorResponse parameterMissingError(String parameterName) {
        String message = I18nHelper.getMessage(BasicErrorResponses.class.getName() + ".parameterMissingError", new Object[]{parameterName}, "参数缺失{0}");
        return ErrorResponse.create(message);
    }

    /**
     * 参数验证错误
     */
    static ErrorResponse parameterVerificationError(List<FieldError> error) {
        String message = I18nHelper.getMessage(BasicErrorResponses.class.getName() + ".parameterVerificationError", "参数验证错误");
        return ErrorResponse.create(message, error);
    }

    /**
     * 参数验证错误
     */
    static ErrorResponse parameterVerificationError(Set<ConstraintViolation<?>> constraintViolationSet) {
        String message = I18nHelper.getMessage(BasicErrorResponses.class.getName() + ".parameterVerificationError", "参数验证错误");
        return ErrorResponse.create(message, constraintViolationSet);
    }

    /**
     * 没有找到api
     */
    static ErrorResponse notFoundApi(String api) {
        String message = I18nHelper.getMessage(BasicErrorResponses.class.getName() + ".notFoundApi", new Object[]{api}, "没有找到api{0}");
        return ErrorResponse.create(message);
    }
}
