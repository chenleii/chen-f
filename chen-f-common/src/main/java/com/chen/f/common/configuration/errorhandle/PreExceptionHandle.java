package com.chen.f.common.configuration.errorhandle;

import com.chen.f.common.api.exception.ApiException;
import com.chen.f.common.api.response.error.ErrorResponse;
import com.chen.f.common.api.response.error.basic.BasicErrorResponses;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

/**
 * 前置的异常处理
 *
 * @author chen
 * @since 2018/11/3 0:17.
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class PreExceptionHandle extends AbstractExceptionHandle {


    /**
     * api异常
     *
     * @param request      request
     * @param response     response
     * @param apiException exception
     * @return 错误响应
     */
    //@ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ApiException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> apiException(HttpServletRequest request, HttpServletResponse response,
                                       ApiException apiException) {
        logger.warn("apiException", apiException);
        return ResponseEntity.status(apiException.getHttpStatusCode()).body(wrap(apiException.getErrorResponse(), apiException));
    }

    /**
     * 参数验证失败
     * {@link javax.validation.Valid} {@link org.springframework.validation.annotation.Validated}
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
        logger.warn("方法参数验证错误异常", exception);
        BindingResult bindingResult = exception.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        return wrap(BasicErrorResponses.parameterVerificationError(fieldErrors), exception);
    }

    /**
     * 参数验证失败
     * {@link javax.validation.Valid} {@link org.springframework.validation.annotation.Validated}
     *
     * @param request   request
     * @param response  response
     * @param exception exception
     * @return 错误响应
     */
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public ErrorResponse bindException(HttpServletRequest request, HttpServletResponse response,
                                       BindException exception) {
        logger.warn("方法参数验证错误异常", exception);
        BindingResult bindingResult = exception.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        return wrap(BasicErrorResponses.parameterVerificationError(fieldErrors), exception);
    }

    /**
     * 参数验证失败
     * {@link org.springframework.validation.annotation.Validated}
     *
     * @param request   request
     * @param response  response
     * @param exception exception
     * @return 错误响应
     */
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public ErrorResponse constraintViolationException(HttpServletRequest request, HttpServletResponse response,
                                                      ConstraintViolationException exception) {
        logger.warn("方法参数验证错误异常", exception);
        Set<ConstraintViolation<?>> constraintViolationSet = exception.getConstraintViolations();
        return wrap(BasicErrorResponses.parameterVerificationError(constraintViolationSet), exception);
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
        return wrap(BasicErrorResponses.parameterMissingError(exception.getParameterName()), exception);
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
        return wrap(BasicErrorResponses.parameterFormatError(), exception);
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
        return wrap(BasicErrorResponses.notFoundApi(request.getRequestURI()), exception);
    }


}

