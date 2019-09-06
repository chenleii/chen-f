package com.chen.f.spring.boot.configuration.springsecurity.webhandle;

import com.chen.f.admin.security.LoginWebAuthenticationDetails;
import com.chen.f.core.api.response.error.security.SecurityErrorResponse;
import com.chen.f.core.api.response.success.R;
import com.chen.f.core.util.ServletUtils;
import com.chen.f.spring.boot.configuration.springsecurity.exception.CaptchaException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.session.SessionInformationExpiredEvent;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * spring-security接口返回处理
 *
 * @author chen
 * @since 2018/11/4 21:29.
 */
public class SpringSecurityHandle {

    /**
     * 拒绝访问处理
     *
     * @param request               request
     * @param response              response
     * @param accessDeniedException accessDeniedException
     * @throws IOException      IOException
     * @throws ServletException ServletException
     */
    public static void accessDeniedHandle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        ServletUtils.responseJson(response, SecurityErrorResponse.accessDenied());
    }

    /**
     * 认证入口点处理(未认证处理\未登录处理)
     *
     * @param request                 request
     * @param response                response
     * @param authenticationException authenticationException
     * @throws IOException      IOException
     * @throws ServletException ServletException
     */
    public static void authenticationEntryPointHandle(HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        ServletUtils.responseJson(response, SecurityErrorResponse.notLogin());
    }

    /**
     * 认证失败处理(登陆失败处理)
     *
     * @param request                 request
     * @param response                response
     * @param authenticationException authenticationException
     * @throws IOException      IOException
     * @throws ServletException ServletException
     */
    public static void authenticationFailureHandle(HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        if (authenticationException instanceof CaptchaException) {
            ServletUtils.responseJson(response, SecurityErrorResponse.captchaError(authenticationException.getMessage()));
        } else if (authenticationException instanceof BadCredentialsException) {
            ServletUtils.responseJson(response, SecurityErrorResponse.usernameOrPasswordError());
        } else if (authenticationException instanceof UsernameNotFoundException) {
            ServletUtils.responseJson(response, SecurityErrorResponse.usernameOrPasswordError());
        } else if (authenticationException instanceof DisabledException) {
            ServletUtils.responseJson(response, SecurityErrorResponse.accountDisable());
        } else if (authenticationException instanceof LockedException) {
            ServletUtils.responseJson(response, SecurityErrorResponse.accountLocked());
        } else if (authenticationException instanceof AccountExpiredException) {
            ServletUtils.responseJson(response, SecurityErrorResponse.accountExpired());
        } else {
            ServletUtils.responseJson(response, SecurityErrorResponse.loginFail());
        }

    }

    /**
     * 认证成功处理(登录成功处理)
     *
     * @param request        request
     * @param response       response
     * @param authentication authentication
     * @throws IOException      IOException
     * @throws ServletException ServletException
     */
    public static void authenticationSuccessHandle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        ServletUtils.responseJson(response, R.msg("登录成功"));
    }

    /**
     * 注销成功处理
     *
     * @param request        request
     * @param response       response
     * @param authentication authentication
     * @throws IOException      IOException
     * @throws ServletException ServletException
     */
    public static void logoutSuccessHandle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_OK);
        ServletUtils.responseJson(response, R.msg("退出成功"));
    }


    /**
     * 构建用户token细节处理
     *
     * @param request request
     * @return 用户token细节
     */
    public static LoginWebAuthenticationDetails buildDetailsHandle(HttpServletRequest request) {
        return new LoginWebAuthenticationDetails(request);
    }


    /**
     * 无效的session处理
     *
     * @param request request
     * @param response response
     * @throws IOException IOException
     * @throws ServletException ServletException
     */
    public static void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        authenticationEntryPointHandle(request, response, null);
    }


    /**
     * 过期的session处理
     *
     * @param event event
     * @throws IOException IOException
     * @throws ServletException ServletException
     */
    public static void onExpiredSessionDetected(SessionInformationExpiredEvent event)
            throws IOException, ServletException{
        authenticationEntryPointHandle(event.getRequest(), event.getResponse(), null);
    }
}
