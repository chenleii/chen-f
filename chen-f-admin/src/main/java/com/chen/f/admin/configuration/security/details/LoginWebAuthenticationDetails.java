package com.chen.f.admin.configuration.security.details;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author chen
 * @since 2018/4/12 18:31
 */
public class LoginWebAuthenticationDetails extends WebAuthenticationDetails {

    private static final String DEFAULT_CAPTCHA_KEY = "captcha";
    private static final String DEFAULT_ORIGINAL_CAPTCHA_KEY = "originalCaptcha";
    private static final String DEFAULT_ORIGINAL_CAPTCHA_EXPIRE_TIME_KEY = "originalCaptchaExpireTime";


    private String captchaKey = DEFAULT_CAPTCHA_KEY;
    private String originalCaptchaKey = DEFAULT_ORIGINAL_CAPTCHA_KEY;
    private String originalCaptchaExpireTimeKey = DEFAULT_ORIGINAL_CAPTCHA_EXPIRE_TIME_KEY;

    public String getCaptchaKey() {
        return captchaKey;
    }

    public void setCaptchaKey(String captchaKey) {
        this.captchaKey = captchaKey;
    }

    public String getOriginalCaptchaKey() {
        return originalCaptchaKey;
    }

    public void setOriginalCaptchaKey(String originalCaptchaKey) {
        this.originalCaptchaKey = originalCaptchaKey;
    }

    public String getOriginalCaptchaExpireTimeKey() {
        return originalCaptchaExpireTimeKey;
    }

    public void setOriginalCaptchaExpireTimeKey(String originalCaptchaExpireTimeKey) {
        this.originalCaptchaExpireTimeKey = originalCaptchaExpireTimeKey;
    }

    private String captcha;
    private String originalCaptcha;
    private Long originalCaptchaExpireTime;

    public String getCaptcha() {
        return captcha;
    }

    public String getOriginalCaptcha() {
        return originalCaptcha;
    }

    public Long getOriginalCaptchaExpireTime() {
        return originalCaptchaExpireTime;
    }

    /**
     * Records the remote address and will also set the session Id if a session already
     * exists (it won't create one).
     *
     * @param request that the authentication request was received from
     */
    public LoginWebAuthenticationDetails(HttpServletRequest request) {
        super(request);
        HttpSession session = request.getSession(false);
        if (session != null) {
            this.captcha = (String) request.getAttribute(this.captchaKey);
            this.originalCaptcha = (String) session.getAttribute(this.originalCaptchaKey);
            this.originalCaptchaExpireTime = (Long) session.getAttribute(this.originalCaptchaExpireTimeKey);
        }


    }
}
