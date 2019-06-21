package com.chen.f.spring.boot.configuration.web;

import com.chen.f.core.api.exception.ApiException;
import com.chen.f.core.api.response.error.ErrorResponse;
import com.chen.f.core.api.response.error.basic.BasicErrorResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 前置的异常处理
 *
 * @author chen
 * @since 2018/11/3 0:17.
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
public class PreExceptionHandle extends AbstractExceptionHandle {


    /**
     * api异常
     *
     * @param request      request
     * @param response     response
     * @param apiException exception
     * @return 错误响应
     */
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ApiException.class)
    @ResponseBody
    public ErrorResponse apiException(HttpServletRequest request, HttpServletResponse response,
                                      ApiException apiException) {
        logger.warn("apiException", apiException);
        return wrap(apiException.getErrorResponse(), apiException);
    }

    /**
     * {@code @Valid} 参数验证失败
     *
     * @param request   request
     * @param response  response
     * @param exception exception
     * @return 错误响应
     */
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ErrorResponse methodArgumentNotValidException(HttpServletRequest request, HttpServletResponse response,
                                                         MethodArgumentNotValidException exception) {
        logger.warn("方法参数无效异常", exception);
        BindingResult bindingResult = exception.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        return wrap(BasicErrorResponse.parameterVerificationError(fieldErrors), exception);
    }

    /**
     * 缺少请求参数
     *
     * @param request   request
     * @param response  response
     * @param exception exception
     * @return 错误响应
     */
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    public ErrorResponse exception(HttpServletRequest request, HttpServletResponse response,
                                   MissingServletRequestParameterException exception) {
        logger.warn("缺少请求参数", exception);
        return wrap(BasicErrorResponse.parameterMissingError(exception.getParameterName()), exception);
    }

    /**
     * 消息转换异常(如:json格式错误)
     *
     * @param request   request
     * @param response  response
     * @param exception exception
     * @return 错误响应
     */
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageConversionException.class)
    @ResponseBody
    public ErrorResponse exception(HttpServletRequest request, HttpServletResponse response,
                                   HttpMessageConversionException exception) {
        logger.warn("消息转换异常", exception);
        return wrap(BasicErrorResponse.parameterFormatError(), exception);
    }

    /**
     * 404错误.
     *
     * @param request   request
     * @param response  response
     * @param exception exception
     * @return 错误响应
     */
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseBody
    public ErrorResponse noHandlerFoundException(HttpServletRequest request, HttpServletResponse response,
                                                 NoHandlerFoundException exception) {
        logger.warn("资源不存在异常", exception);
        return wrap(BasicErrorResponse.notFoundApi(request.getRequestURI()), exception);
    }


}

