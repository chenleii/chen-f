package com.chen.f.common.api.response.error.i18n;

import com.chen.f.common.api.response.error.ErrorResponse;
import com.chen.f.common.configuration.helper.I18nHelper;

/**
 * 国际化的错误响应
 * <p>
 * 继承{@link com.chen.f.common.api.response.error.ErrorResponse}方便查找
 *
 * @author chen
 * @since 2019/11/29 16:04.
 */
public interface I18nErrorResponses extends ErrorResponse {


    /**
     * 创建国际化的错误响应
     *
     * @param i18nMessageKey 国际化消息KEY
     * @return 错误响应
     */
    static ErrorResponse createI18nErrorResponse(String i18nMessageKey) {
        String message = I18nHelper.getMessage(i18nMessageKey);
        return ErrorResponse.create(message);
    }

    /**
     * 创建国际化的错误响应
     *
     * @param errorCode      错误码
     * @param i18nMessageKey 国际化消息KEY
     * @return 错误响应
     */
    static ErrorResponse createI18nErrorResponse0(String errorCode, String i18nMessageKey) {
        String message = I18nHelper.getMessage(i18nMessageKey);
        return ErrorResponse.create(errorCode, message);
    }

    /**
     * 创建国际化的错误响应
     *
     * @param i18nMessageKey 国际化消息KEY
     * @param error          错误信息
     * @return 错误响应
     */
    static ErrorResponse createI18nErrorResponse(String i18nMessageKey, Object error) {
        String message = I18nHelper.getMessage(i18nMessageKey);
        return ErrorResponse.create(message, error);
    }

    /**
     * 创建国际化的错误响应
     *
     * @param i18nMessageKey     国际化消息KEY
     * @param i18nDefaultMessage 国际化默认消息
     * @param error              错误信息
     * @return 错误响应
     */
    static ErrorResponse createI18nErrorResponse0(String i18nMessageKey, String i18nDefaultMessage, Object error) {
        String message = I18nHelper.getMessage(i18nMessageKey, i18nDefaultMessage);
        return ErrorResponse.create(message, error);
    }

    /**
     * 创建国际化的错误响应
     *
     * @param i18nMessageKey     国际化消息KEY
     * @param objects            国际化消息参数
     * @param i18nDefaultMessage 国际化默认消息
     * @param error              错误信息
     * @return 错误响应
     */
    static ErrorResponse createI18nErrorResponse(String i18nMessageKey, Object[] objects, String i18nDefaultMessage, Object error) {
        String message = I18nHelper.getMessage(i18nMessageKey, objects, i18nDefaultMessage);
        return ErrorResponse.create(message, error);
    }

    /**
     * 创建国际化的错误响应
     *
     * @param errorCode          错误码
     * @param i18nMessageKey     国际化消息KEY
     * @param i18nDefaultMessage 国际化默认消息
     * @param error              错误信息
     * @return 错误响应
     */
    static ErrorResponse createI18nErrorResponse(String errorCode, String i18nMessageKey, String i18nDefaultMessage, Object error) {
        String message = I18nHelper.getMessage(i18nMessageKey, i18nDefaultMessage);
        return ErrorResponse.create(errorCode, message, error);
    }

    /**
     * 创建国际化的错误响应
     *
     * @param errorCode          错误码
     * @param i18nMessageKey     国际化消息KEY
     * @param objects            国际化消息参数
     * @param i18nDefaultMessage 国际化默认消息
     * @param error              错误信息
     * @return 错误响应
     */
    static ErrorResponse createI18nErrorResponse(String errorCode, String i18nMessageKey, Object[] objects, String i18nDefaultMessage, Object error) {
        String message = I18nHelper.getMessage(i18nMessageKey, objects, i18nDefaultMessage);
        return ErrorResponse.create(errorCode, message, error);
    }

}
