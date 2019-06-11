package com.chen.f.core.api.response.error.security;

import com.chen.f.core.api.response.error.AbstractErrorResponse;

import java.util.Collection;

/**
 * spring-security错误响应
 *
 * @author chen
 * @since 2018/11/11 17:19.
 */
public class SecurityErrorResponse extends AbstractErrorResponse {

    public SecurityErrorResponse(String errorCode, String errorMsg) {
        super(errorCode, errorMsg);
    }

    public SecurityErrorResponse(String errorCode, String errorMsg, Object error) {
        super(errorCode, errorMsg, error);
    }

    /**
     * 没有登录
     */
    public static SecurityErrorResponse notLogin() {
        return new SecurityErrorResponse("not_login", "没有登录");
    }

    /**
     * 拒绝访问
     */
    public static SecurityErrorResponse accessDenied() {
        return new SecurityErrorResponse("access_denied", "拒绝访问");
    }

    /**
     * 登录失败
     */
    public static SecurityErrorResponse loginFail() {
        return new SecurityErrorResponse("login_fail", "登录失败");
    }

    /**
     * 验证码错误
     */
    public static SecurityErrorResponse captchaError(String errorMsg) {
        return new SecurityErrorResponse("captcha_error", errorMsg);
    }

    /**
     * 用户名或密码错误
     */
    public static SecurityErrorResponse usernameOrPasswordError() {
        return new SecurityErrorResponse("username_or_password_error", "用户名或密码错误");
    }

    /**
     * 账户已锁定
     */
    public static SecurityErrorResponse accountLocked() {
        return new SecurityErrorResponse("account_locked", "账户已锁定");
    }

    /**
     * 账户已过期
     */
    public static SecurityErrorResponse accountExpired() {
        return new SecurityErrorResponse("account_expired", "账户已过期");
    }

    /**
     * 账户已禁用
     */
    public static SecurityErrorResponse accountDisable() {
        return new SecurityErrorResponse("account_disable", "账户已禁用");
    }

    /**
     * 账户不存在
     */
    public static SecurityErrorResponse accountNotFount() {
        return new SecurityErrorResponse("account_not_fount", "账户不存在");
    }

    /**
     * 账户没有完全认证
     */
    public static SecurityErrorResponse accountNotFullyAuthenticated() {
        return new SecurityErrorResponse("account_not_fully_authenticated", "账户没有完全认证");
    }

    /**
     * principal错误(使用了其他UserDetailsService获取用户对象)
     */
    public static SecurityErrorResponse principalError() {
        return new SecurityErrorResponse("principal_error", "principal错误");
    }

    /**
     * details错误(使用了其他WebAuthenticationDetails包装请求)
     */
    public static SecurityErrorResponse detailsError() {
        return new SecurityErrorResponse("details_error", "details错误");
    }

    /**
     * 没有权限
     */
    public static SecurityErrorResponse notPermission(String permission) {
        return new SecurityErrorResponse("not_permission", String.format("没有权限[%s]", permission));
    }

    /**
     * 没有权限
     */
    public static SecurityErrorResponse notPermission(String... permissions) {
        return new SecurityErrorResponse("not_permission", String.format("没有权限[%s]", (Object[]) permissions));
    }

    /**
     * 没有权限
     */
    public static SecurityErrorResponse notPermission(Collection<String> permissionCollection) {
        return new SecurityErrorResponse("not_permission", String.format("没有权限[%s]", permissionCollection));
    }

    /**
     * 没有角色
     */
    public static SecurityErrorResponse notRole(String role) {
        return new SecurityErrorResponse("not_role", String.format("没有角色[%s]", role));
    }

    /**
     * 没有角色
     */
    public static SecurityErrorResponse notRole(String... roles) {
        return new SecurityErrorResponse("not_role", String.format("没有角色[%s]", (Object[]) roles));
    }

    /**
     * 没有角色
     */
    public static SecurityErrorResponse notRole(Collection<String> roleCollection) {
        return new SecurityErrorResponse("not_role", String.format("没有角色[%s]", roleCollection));
    }

    /**
     * 不是超级管理员
     */
    public static SecurityErrorResponse notSuperAdministrator() {
        return new SecurityErrorResponse("not_super_administrator", "不是超级管理员");
    }

}
