package com.chen.f.common.api.response.error.basic;

import com.chen.f.common.api.response.error.ErrorResponse;
import com.chen.f.common.api.response.error.i18n.I18nErrorResponses;
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
        return I18nErrorResponses.createI18nErrorResponse(BasicErrorResponses.class.getName() + ".serverException",
                "服务器异常");
    }

    /**
     * 参数错误
     */
    static ErrorResponse parameterError() {
        return I18nErrorResponses.createI18nErrorResponse(BasicErrorResponses.class.getName() + ".parameterError",
                "参数错误");
    }

    /**
     * 参数格式错误
     */
    static ErrorResponse parameterFormatError() {
        return I18nErrorResponses.createI18nErrorResponse(BasicErrorResponses.class.getName() + ".parameterFormatError",
                "参数格式错误");
    }

    /**
     * 参数缺失错误
     */
    static ErrorResponse parameterMissingError(String parameterName) {
        return I18nErrorResponses.createI18nErrorResponse(BasicErrorResponses.class.getName() + ".parameterMissingError",
                "参数缺失{0}", new Object[]{parameterName});
    }

    /**
     * 参数验证错误
     */
    static ErrorResponse parameterVerificationError(List<FieldError> error) {
        return I18nErrorResponses.createI18nErrorResponse(BasicErrorResponses.class.getName() + ".parameterVerificationError",
                "参数验证错误", error);
    }

    /**
     * 参数验证错误
     */
    static ErrorResponse parameterVerificationError(Set<ConstraintViolation<?>> constraintViolationSet) {
        return I18nErrorResponses.createI18nErrorResponse(BasicErrorResponses.class.getName() + ".parameterVerificationError",
                "参数验证错误", constraintViolationSet);
    }

    /**
     * 没有找到api
     */
    static ErrorResponse notFoundApi(String api) {
        return I18nErrorResponses.createI18nErrorResponse(BasicErrorResponses.class.getName() + ".notFoundApi",
                "没有找到api{0}", new Object[]{api});
    }
}
