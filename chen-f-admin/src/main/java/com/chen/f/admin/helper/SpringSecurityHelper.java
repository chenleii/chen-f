package com.chen.f.admin.helper;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.session.SessionRepository;

import java.util.List;

/**
 * spring-security帮助类
 * <p>
 * 管理用户过期
 *
 * @author chen
 * @since 2019/9/17 17:14.
 */
public class SpringSecurityHelper {
    protected static final Logger logger = LoggerFactory.getLogger(SpringSecurityHelper.class);

    private static SessionRegistry sessionRegistry;

    private static SecurityContextRepository securityContextRepository;
    private static SessionRepository sessionRepository;


    public SpringSecurityHelper(SessionRegistry sessionRegistry) {
        SpringSecurityHelper.sessionRegistry = sessionRegistry;
    }

    /**
     * 是否是未过期的
     *
     * @param username 用户名称
     * @return 是/否
     */
    public static boolean isNotExpired(String username) {
        List<SessionInformation> sessionInformationList = sessionRegistry.getAllSessions(username, false);
        return CollectionUtils.isNotEmpty(sessionInformationList);
    }

    /**
     * 过期
     *
     * @param username 用户名称
     */
    public static void expire(String username) {
        List<SessionInformation> sessionInformationList = sessionRegistry.getAllSessions(username, false);
        for (SessionInformation sessionInformation : sessionInformationList) {
            sessionInformation.expireNow();
        }
    }
}
