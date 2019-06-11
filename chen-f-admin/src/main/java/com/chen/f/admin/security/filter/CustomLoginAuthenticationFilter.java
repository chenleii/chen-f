package com.chen.f.admin.security.filter;

import com.chen.f.admin.security.token.CustomLoginAuthenticationToken;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 处理身份认证表单提交 过滤器
 *
 * @author chen
 * @since 2018/1/15 13:17.
 */
public class CustomLoginAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    public static final String CHEN_USERNAME_KEY = "username";
    public static final String CHEN_PASSWORD_KEY = "password";

    private String usernameParameter = CHEN_USERNAME_KEY;
    private String passwordParameter = CHEN_PASSWORD_KEY;
    private boolean postOnly = true;

    public CustomLoginAuthenticationFilter() {

    }

    @Override
    public void setUsernameParameter(String usernameParameter) {
        this.usernameParameter = usernameParameter;
    }

    @Override
    public void setPasswordParameter(String passwordParameter) {
        this.passwordParameter = passwordParameter;
    }

    @Override
    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse httpServletResponse)
            throws AuthenticationException {
        if (postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }

        String username;
        String password;
        //if (StringUtils.startsWith(request.getContentType(), MediaType.APPLICATION_JSON_VALUE)) {
        //    //适配json
        //    ObjectMapper objectMapper = new ObjectMapper();
        //    try {
        //        Map<String, String> map = objectMapper.readValue(request.getInputStream(), new TypeReference<Map<String, String>>() {
        //        });
        //        map.forEach(request::setAttribute);
        //        username = map.get(usernameParameter);
        //        password = map.get(passwordParameter);
        //    } catch (IOException e) {
        //        logger.warn("获取登录信息出现异常", e);
        //        throw new AuthenticationServiceException("获取登录信息出现异常", e);
        //    }
        //
        //} else {
            username = request.getParameter(usernameParameter);
            password = request.getParameter(passwordParameter);
            Map<String, String[]> parameterMap = request.getParameterMap();
            parameterMap.forEach(request::setAttribute);
        //}


        if (username == null) {
            username = "";
        }

        if (password == null) {
            password = "";
        }

        username = username.trim();

        CustomLoginAuthenticationToken authRequest = new CustomLoginAuthenticationToken(
                username, password);
        super.setDetails(request, authRequest);

        return super.getAuthenticationManager().authenticate(authRequest);
    }

}

