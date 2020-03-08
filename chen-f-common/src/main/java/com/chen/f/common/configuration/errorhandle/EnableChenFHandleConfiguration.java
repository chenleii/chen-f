package com.chen.f.common.configuration.errorhandle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.SearchStrategy;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * chen-f错误处理配置类
 *
 * @author chen
 * @since 2019/11/9 17:45.
 */
@Configuration
public class EnableChenFHandleConfiguration {
    protected static final Logger logger = LoggerFactory.getLogger(EnableChenFHandleConfiguration.class);

    @Bean
    @ConditionalOnMissingBean(value = ErrorController.class, search = SearchStrategy.CURRENT)
    public ChenFErrorController errorController(ErrorAttributes errorAttributes,
                                                ServerProperties serverProperties, List<ErrorViewResolver> errorViewResolvers) {
        return new ChenFErrorController(errorAttributes, serverProperties.getError(), errorViewResolvers);
    }

    @Bean
    @ConditionalOnMissingBean(PreExceptionHandle.class)
    public PreExceptionHandle preExceptionHandle(ServerProperties serverProperties) {
        PreExceptionHandle preExceptionHandle = new PreExceptionHandle();
        preExceptionHandle.setEnableStackTrace(serverProperties.getError().getIncludeStacktrace() == ErrorProperties.IncludeStacktrace.ALWAYS);
        return preExceptionHandle;
    }

    @Bean
    @ConditionalOnMissingBean(PostExceptionHandle.class)
    public PostExceptionHandle postExceptionHandle(ServerProperties serverProperties) {
        PostExceptionHandle postExceptionHandle = new PostExceptionHandle();
        postExceptionHandle.setEnableStackTrace(serverProperties.getError().getIncludeStacktrace() == ErrorProperties.IncludeStacktrace.ALWAYS);
        return postExceptionHandle;
    }
    
}
