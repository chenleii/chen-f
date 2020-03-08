package com.chen.f.admin.api.response.error.security;

import com.chen.f.common.api.response.error.ErrorResponse;
import com.chen.f.common.api.response.error.i18n.I18nErrorResponses;
import com.chen.f.common.configuration.helper.I18nHelper;

import java.util.Collection;

/**
 * spring-security的错误响应
 * <p>
 * 继承{@link com.chen.f.common.api.response.error.ErrorResponse}方便查找
 *
 * @author chen
 * @since 2018/11/11 17:19.
 */
public interface SecurityErrorResponses extends ErrorResponse {

    /**
     * 没有登录
     */
    static ErrorResponse notLogin() {
        return I18nErrorResponses.createI18nErrorResponse("not_login", SecurityErrorResponses.class.getName() + ".notLogin",
                "没有登录");
    }

    /**
     * 拒绝访问
     */
    static ErrorResponse accessDenied() {
        return I18nErrorResponses.createI18nErrorResponse("access_denied", SecurityErrorResponses.class.getName() + ".accessDenied",
                "拒绝访问");
    }

    /**
     * 登录失败
     */
    static ErrorResponse loginFail() {
        return I18nErrorResponses.createI18nErrorResponse("login_fail", SecurityErrorResponses.class.getName() + ".loginFail",
                "登录失败");
    }

    /**
     * 验证码错误
     */
    static ErrorResponse captchaError() {
        return I18nErrorResponses.createI18nErrorResponse("captcha_error", SecurityErrorResponses.class.getName() + ".captchaError",
                "验证码错误或已过期");
    }

    /**
     * 用户名或密码错误
     */
    static ErrorResponse usernameOrPasswordError() {
        String message = I18nHelper.getMessage(SecurityErrorResponses.class.getName() + ".usernameOrPasswordError",
                "用户名或密码错误");
        return ErrorResponse.create("username_or_password_error", message);
    }

    /**
     * 账户已锁定
     */
    static ErrorResponse accountLocked() {
        return I18nErrorResponses.createI18nErrorResponse("account_locked", SecurityErrorResponses.class.getName() + ".accountLocked",
                "账户已锁定");
    }

    /**
     * 账户已过期
     */
    static ErrorResponse accountExpired() {
        return I18nErrorResponses.createI18nErrorResponse("account_expired", SecurityErrorResponses.class.getName() + ".accountExpired",
                "账户已过期");
    }

    /**
     * 账户已禁用
     */
    static ErrorResponse accountDisable() {
        return I18nErrorResponses.createI18nErrorResponse("account_disable", SecurityErrorResponses.class.getName() + ".accountDisable",
                "账户已禁用");
    }

    /**
     * 账户不存在
     */
    static ErrorResponse accountNotFount() {
        return I18nErrorResponses.createI18nErrorResponse("account_not_fount", SecurityErrorResponses.class.getName() + ".accountNotFount",
                "账户不存在");
    }

    /**
     * 账户没有完全认证
     */
    static ErrorResponse accountNotFullyAuthenticated() {
        return I18nErrorResponses.createI18nErrorResponse("account_not_fully_authenticated", SecurityErrorResponses.class.getName() + ".accountNotFullyAuthenticated",
                "账户没有完全认证");
    }

    /**
     * principal错误(使用了其他UserDetailsService获取用户对象)
     */
    static ErrorResponse principalError() {
        return I18nErrorResponses.createI18nErrorResponse("principal_error", SecurityErrorResponses.class.getName() + ".principalError",
                "principal错误");
    }

    /**
     * details错误(使用了其他WebAuthenticationDetails包装请求)
     */
    static ErrorResponse detailsError() {
        return I18nErrorResponses.createI18nErrorResponse("details_error", SecurityErrorResponses.class.getName() + ".detailsError",
                "details错误");
    }

    /**
     * 没有权限
     */
    static ErrorResponse notPermission(String permission) {
        return I18nErrorResponses.createI18nErrorResponse("not_permission", SecurityErrorResponses.class.getName() + ".notPermission",
                "没有权限[{0}]", new Object[]{permission});
    }

    /**
     * 没有权限
     */
    static ErrorResponse notPermission(String... permissions) {
        return I18nErrorResponses.createI18nErrorResponse("not_permission", SecurityErrorResponses.class.getName() + ".notPermission",
                "没有权限[{0}]", new Object[]{String.join(",", permissions)});
    }

    /**
     * 没有权限
     */
    static ErrorResponse notPermission(Collection<String> permissionCollection) {
        return I18nErrorResponses.createI18nErrorResponse("not_permission", SecurityErrorResponses.class.getName() + ".notPermission",
                "没有权限[{0}]", new Object[]{String.join(",", permissionCollection)});
    }

    /**
     * 没有角色
     */
    static ErrorResponse notRole(String role) {
        return I18nErrorResponses.createI18nErrorResponse("not_role", SecurityErrorResponses.class.getName() + ".notRole",
                "没有角色[{0}]", new Object[]{role});
    }

    /**
     * 没有角色
     */
    static ErrorResponse notRole(String... roles) {
        return I18nErrorResponses.createI18nErrorResponse("not_role", SecurityErrorResponses.class.getName() + ".notRole",
                "没有角色[{0}]", new Object[]{String.join(",", roles)});
    }

    /**
     * 没有角色
     */
    static ErrorResponse notRole(Collection<String> roleCollection) {
        return I18nErrorResponses.createI18nErrorResponse("not_role", SecurityErrorResponses.class.getName() + ".notRole",
                "没有角色[{0}]", new Object[]{String.join(",", roleCollection)});
    }

    /**
     * 不是超级管理员
     */
    static ErrorResponse notSuperAdministrator() {
        return I18nErrorResponses.createI18nErrorResponse("not_super_administrator", SecurityErrorResponses.class.getName() + ".notSuperAdministrator",
                "不是超级管理员");
    }

}
