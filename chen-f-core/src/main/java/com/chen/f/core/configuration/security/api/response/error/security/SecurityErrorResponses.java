package com.chen.f.core.configuration.security.api.response.error.security;

import com.chen.f.core.api.response.error.ErrorResponse;
import com.chen.f.core.configuration.i18n.I18nHelper;

import java.util.Collection;

/**
 * spring-security的错误响应
 * <p>
 * 继承{@link com.chen.f.core.api.response.error.ErrorResponse}方便查找
 *
 * @author chen
 * @since 2018/11/11 17:19.
 */
public interface SecurityErrorResponses extends ErrorResponse {

    /**
     * 没有登录
     */
    static ErrorResponse notLogin() {
        String message = I18nHelper.getMessage(SecurityErrorResponses.class.getName() + ".notLogin", "没有登录");
        return ErrorResponse.create(message);
    }

    /**
     * 拒绝访问
     */
    static ErrorResponse accessDenied() {
        String message = I18nHelper.getMessage(SecurityErrorResponses.class.getName() + ".accessDenied", "拒绝访问");
        return ErrorResponse.create(message);
    }

    /**
     * 登录失败
     */
    static ErrorResponse loginFail() {
        String message = I18nHelper.getMessage(SecurityErrorResponses.class.getName() + ".loginFail", "登录失败");
        return ErrorResponse.create(message);
    }

    /**
     * 验证码错误
     */
    static ErrorResponse captchaError() {
        String message = I18nHelper.getMessage(SecurityErrorResponses.class.getName() + ".captchaError", "验证码错误或已过期");
        return ErrorResponse.create(message);
    }

    /**
     * 用户名或密码错误
     */
    static ErrorResponse usernameOrPasswordError() {
        String message = I18nHelper.getMessage(SecurityErrorResponses.class.getName() + ".usernameOrPasswordError",
                "用户名或密码错误");
        return ErrorResponse.create(message);
    }

    /**
     * 账户已锁定
     */
    static ErrorResponse accountLocked() {
        String message = I18nHelper.getMessage(SecurityErrorResponses.class.getName() + ".accountLocked", "账户已锁定");
        return ErrorResponse.create(message);
    }

    /**
     * 账户已过期
     */
    static ErrorResponse accountExpired() {
        String message = I18nHelper.getMessage(SecurityErrorResponses.class.getName() + ".accountExpired", "账户已过期");
        return ErrorResponse.create(message);
    }

    /**
     * 账户已禁用
     */
    static ErrorResponse accountDisabled() {
        String message = I18nHelper.getMessage(SecurityErrorResponses.class.getName() + ".accountDisabled", "账户已禁用");
        return ErrorResponse.create(message);
    }

    /**
     * 账户不存在
     */
    static ErrorResponse accountNotFount() {
        String message = I18nHelper.getMessage(SecurityErrorResponses.class.getName() + ".accountNotFount", "账户不存在");
        return ErrorResponse.create(message);
    }

    /**
     * 账户没有完全认证
     */
    static ErrorResponse accountNotFullyAuthenticated() {
        String message = I18nHelper.getMessage(SecurityErrorResponses.class.getName() + ".accountNotFullyAuthenticated", "账户没有完全认证");
        return ErrorResponse.create(message);
    }

    /**
     * principal错误(使用了其他UserDetailsService获取用户对象)
     */
    static ErrorResponse principalError() {
        String message = I18nHelper.getMessage(SecurityErrorResponses.class.getName() + ".principalError", "principal错误");
        return ErrorResponse.create(message);
    }

    /**
     * details错误(使用了其他WebAuthenticationDetails包装请求)
     */
    static ErrorResponse detailsError() {
        String message = I18nHelper.getMessage(SecurityErrorResponses.class.getName() + ".detailsError", "details错误");
        return ErrorResponse.create(message);
    }

    /**
     * 没有权限
     */
    static ErrorResponse notPermission(String permission) {
        String message = I18nHelper.getMessage(SecurityErrorResponses.class.getName() + ".notPermission", new Object[]{permission}, "没有权限[{0}]");
        return ErrorResponse.create(message);
    }

    /**
     * 没有权限
     */
    static ErrorResponse notPermission(String... permissions) {
        String message = I18nHelper.getMessage(SecurityErrorResponses.class.getName() + ".notPermission", new Object[]{String.join(",", permissions)}, "没有权限[{0}]");
        return ErrorResponse.create(message);
    }

    /**
     * 没有权限
     */
    static ErrorResponse notPermission(Collection<String> permissionCollection) {
        String message = I18nHelper.getMessage(SecurityErrorResponses.class.getName() + ".notPermission", new Object[]{String.join(",", permissionCollection)}, "没有权限[{0}]");
        return ErrorResponse.create(message);
    }

    /**
     * 没有角色
     */
    static ErrorResponse notRole(String role) {
        String message = I18nHelper.getMessage(SecurityErrorResponses.class.getName() + ".notRole", new Object[]{role}, "没有角色[{0}]");
        return ErrorResponse.create(message);
    }

    /**
     * 没有角色
     */
    static ErrorResponse notRole(String... roles) {
        String message = I18nHelper.getMessage(SecurityErrorResponses.class.getName() + ".notRole", new Object[]{String.join(",", roles)}, "没有角色[{0}]");
        return ErrorResponse.create(message);
    }

    /**
     * 没有角色
     */
    static ErrorResponse notRole(Collection<String> roleCollection) {
        String message = I18nHelper.getMessage(SecurityErrorResponses.class.getName() + ".notRole", new Object[]{String.join(",", roleCollection)}, "没有角色[{0}]");
        return ErrorResponse.create(message);
    }

    /**
     * 不是超级管理员
     */
    static ErrorResponse notSuperAdministrator() {
        String message = I18nHelper.getMessage(SecurityErrorResponses.class.getName() + ".notSuperAdministrator", "不是超级管理员");
        return ErrorResponse.create(message);
    }
    

}
