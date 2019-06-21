package com.chen.f.spring.boot.configuration.web;

import com.chen.f.core.api.response.error.basic.BasicErrorResponse;
import com.chen.f.core.api.response.error.ErrorResponse;
import com.chen.f.core.api.response.error.StackTraceErrorResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 覆盖/error接口(保证404异常不会使用spring默认响应)
 *
 * @author chen
 * @since 2019/1/10 14:07.
 */
@Api(tags = "错误接口")
@Controller
@RequestMapping("${server.error.path:${error.path:/error}}")
public class CustomErrorController extends BasicErrorController {
    private ErrorAttributes errorAttributes;

    public CustomErrorController(ErrorAttributes errorAttributes, ErrorProperties errorProperties) {
        this(errorAttributes, errorProperties, Collections.emptyList());
    }

    public CustomErrorController(ErrorAttributes errorAttributes, ErrorProperties errorProperties, List<ErrorViewResolver> errorViewResolvers) {
        super(errorAttributes, errorProperties, errorViewResolvers);
        this.errorAttributes = errorAttributes;
    }

    @ApiOperation(value = "原错误接口(响应页面)", produces = MediaType.TEXT_HTML_VALUE)
    @Override
    @RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
        return super.errorHtml(request, response);
    }

    /**
     * 原错误接口名(解决冲突)
     */
    @ApiOperation(value = "原错误接口(重写接口名,解决冲突)")
    @Override
    @RequestMapping("/original")
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        return super.error(request);
    }

    /**
     * 自定义的错误接口(重写/error)
     */
    @ApiOperation(value = "自定义的错误接口")
    @RequestMapping
    public ResponseEntity<ErrorResponse> error1(HttpServletRequest request) throws Throwable {
        //默认是服务器异常
        ErrorResponse errorResponse = BasicErrorResponse.serverException();
        HttpStatus status = super.getStatus(request);
        WebRequest webRequest = new ServletWebRequest(request);
        //针对404做处理
        //如果使用全局异常处理,只会有404会转发到这里
        if (HttpStatus.NOT_FOUND.equals(status)) {
            Object path = webRequest.getAttribute("javax.servlet.error.request_uri", RequestAttributes.SCOPE_REQUEST);
            errorResponse = BasicErrorResponse.notFoundApi(String.valueOf(path));
        }

        if (super.isIncludeStackTrace(request, MediaType.ALL)) {
            Throwable error = this.errorAttributes.getError(webRequest);
            if (error instanceof Error) {
                //如果是Error直接抛出
                throw error;
            }
            if (error != null) {
                while (error instanceof ServletException && error.getCause() != null) {
                    error = error.getCause();
                }
                errorResponse = new StackTraceErrorResponse(errorResponse, error);
            }
        }

        return ResponseEntity.status(status).body(errorResponse);

    }

}
