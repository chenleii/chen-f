package com.chen.f.spring.boot.configuration.springsecurity.provider;

import com.chen.f.admin.security.CustomWebAuthenticationDetails;
import com.chen.f.spring.boot.configuration.springsecurity.exception.CaptchaException;
import com.chen.f.spring.boot.configuration.springsecurity.token.CustomLoginAuthenticationToken;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

/**
 * 身份认证提供商（身份认证验证阶段）
 *
 * @author chen
 * @since 2018/1/16 9:31.
 */
public class CustomLoginAuthenticationProvider extends DaoAuthenticationProvider {

    /**
     * 创建完整的身份认证对象
     *
     * @param principal      用户信息
     * @param authentication 认证对象
     * @param user           用户信息
     * @return 完整的用户信息
     */
    @Override
    protected Authentication createSuccessAuthentication(Object principal,
                                                         Authentication authentication, UserDetails user) {

        CustomLoginAuthenticationToken result = new CustomLoginAuthenticationToken(
                principal, authentication.getCredentials(),
                user.getAuthorities());
        result.setDetails(authentication.getDetails());
        return result;
    }

    /**
     * 附加认证
     *
     * @param userDetails    用户信息
     * @param authentication 认证对象
     * @throws AuthenticationException 如果认证失败
     */
    private void additionalAuthenticationChecks(UserDetails userDetails,
                                                CustomLoginAuthenticationToken authentication) throws AuthenticationException {

        // 验证 验证码
        Object details = authentication.getDetails();
        Assert.isInstanceOf(CustomWebAuthenticationDetails.class, details, messages.getMessage(
                "CustomLoginAuthenticationProvider.onlySupports",
                "Only CustomLoginAuthenticationToken.getDetails() == CustomWebAuthenticationDetails is supported"));
        CustomWebAuthenticationDetails chenWebAuthenticationDetails = (CustomWebAuthenticationDetails) details;
        String captcha = chenWebAuthenticationDetails.getCaptcha();
        String originalCaptcha = chenWebAuthenticationDetails.getOriginalCaptcha();
        Long originalCaptchaExpireTime = chenWebAuthenticationDetails.getOriginalCaptchaExpireTime();

        if (StringUtils.isNotBlank(captcha) && StringUtils.isNotBlank(originalCaptcha) && originalCaptchaExpireTime != null) {
            long currentTimeMillis = System.currentTimeMillis();
            if (currentTimeMillis > originalCaptchaExpireTime) {
                logger.debug("验证码已过期");
                throw new CaptchaException("验证码已过期 请刷新重试");
            }
            if (!StringUtils.equalsIgnoreCase(captcha, originalCaptcha)) {
                logger.debug("验证码验证失败");
                throw new CaptchaException("验证码错误");
            }
        }

        super.additionalAuthenticationChecks(userDetails, authentication);
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return (CustomLoginAuthenticationToken.class.isAssignableFrom(authentication));
    }

}
