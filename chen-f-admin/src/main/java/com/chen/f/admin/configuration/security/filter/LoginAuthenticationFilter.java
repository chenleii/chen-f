package com.chen.f.admin.configuration.security.filter;

import com.chen.f.admin.configuration.security.token.LoginAuthenticationToken;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 处理身份认证表单提交 过滤器
 *
 * @author chen
 * @since 2018/1/15 13:17.
 */
public class LoginAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private boolean postOnly = true;

    public LoginAuthenticationFilter() {
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
        if (MediaType.APPLICATION_JSON_UTF8.isCompatibleWith(MediaType.valueOf(request.getContentType()))) {
            //适配json
            ObjectMapper objectMapper = new ObjectMapper();
            try (ServletInputStream inputStream = request.getInputStream();) {
                Map<String, String> map = objectMapper.readValue(inputStream, new TypeReference<Map<String, String>>() {
                });
                username = map.get(super.getUsernameParameter());
                password = map.get(super.getPasswordParameter());
                map.forEach(request::setAttribute);
            } catch (IOException e) {
                logger.warn("获取登录信息出现异常", e);
                throw new AuthenticationServiceException("获取登录信息出现异常", e);
            }

        } else {
            username = obtainUsername(request);
            password = obtainPassword(request);
            Map<String, String[]> parameterMap = request.getParameterMap();
            parameterMap.forEach(request::setAttribute);
        }


        if (username == null) {
            username = "";
        }

        if (password == null) {
            password = "";
        }

        username = username.trim();

        LoginAuthenticationToken authRequest = new LoginAuthenticationToken(
                username, password);
        super.setDetails(request, authRequest);

        return super.getAuthenticationManager().authenticate(authRequest);
    }


}

