package com.chen.f.core.configuration.security;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.session.SessionRepository;

import java.util.List;

/**
 * SecuritySession帮助类
 * <p>
 * 管理用户过期
 *
 * @author chen
 * @since 2019/9/17 17:14.
 */
public class SecuritySessionHelper {
    protected static final Logger logger = LoggerFactory.getLogger(SecuritySessionHelper.class);

    private static SessionRegistry sessionRegistry;

    private static SecurityContextRepository securityContextRepository;
    private static SessionRepository sessionRepository;


    public SecuritySessionHelper(SessionRegistry sessionRegistry) {
        SecuritySessionHelper.sessionRegistry = sessionRegistry;
    }

    /**
     * 是否登陆的
     * <p>
     * 过期是未登录的
     *
     * @param username 用户名称
     * @return 是/否
     */
    public static boolean isLogin(String username) {
        List<SessionInformation> sessionInformationList = sessionRegistry.getAllSessions(username, false);
        return CollectionUtils.isNotEmpty(sessionInformationList) && sessionInformationList.stream().noneMatch(SessionInformation::isExpired);
    }

    /**
     * 是否不是登陆的
     *
     * @param username 用户名称
     * @return 是/否
     */
    public static boolean isNotLogin(String username) {
        return !isNotLogin(username);
    }


    /**
     * 是否过期的
     * <p>
     * 未登录是过期
     *
     * @param username 用户名称
     * @return 是/否
     */
    public static boolean isExpired(String username) {
        List<SessionInformation> sessionInformationList = sessionRegistry.getAllSessions(username, false);
        return CollectionUtils.isEmpty(sessionInformationList) || sessionInformationList.stream().allMatch(SessionInformation::isExpired);
    }

    /**
     * 是否不是过期的
     *
     * @param username 用户名称
     * @return 是/否
     */
    public static boolean isNotExpired(String username) {
        return !isExpired(username);
    }

    /**
     * 过期
     *
     * @param username 用户名称
     */
    public static void expire(String username) {
        List<SessionInformation> sessionInformationList = sessionRegistry.getAllSessions(username, false);
        if (CollectionUtils.isNotEmpty(sessionInformationList)) {
            for (SessionInformation sessionInformation : sessionInformationList) {
                sessionInformation.expireNow();
            }
        }
    }
}
