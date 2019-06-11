package com.chen.f.spring.boot.configuration.web;

import com.chen.f.core.api.response.error.ErrorResponse;
import com.chen.f.core.api.response.error.basic.BasicErrorResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 后置的异常处理
 * (保证所有异常都会被拦截)
 *
 * @author chen
 * @since 2019/1/10 12:03.
 */
@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class PostExceptionHandle extends AbstractExceptionHandle {

    /**
     * 未拦截的异常
     *
     * @param request   request
     * @param response  response
     * @param exception exception
     * @return 错误响应
     */
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ErrorResponse exception(HttpServletRequest request, HttpServletResponse response, Exception exception) {
        logger.error("出现未拦截的异常", exception);
        return wrap(BasicErrorResponse.serverException(), exception);
    }
}
