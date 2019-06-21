package com.chen.f.spring.boot.autoconfigure.web;

import com.chen.f.spring.boot.configuration.web.CustomErrorController;
import com.chen.f.spring.boot.configuration.web.PostExceptionHandle;
import com.chen.f.spring.boot.configuration.web.PreExceptionHandle;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.SearchStrategy;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Servlet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author chen
 * @since 2018/11/3 23:33.
 */
@EnableWebMvc
@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@ConditionalOnClass({Servlet.class, DispatcherServlet.class, WebMvcConfigurer.class})
@ConditionalOnMissingBean(WebMvcConfigurationSupport.class)
@AutoConfigureBefore({ErrorMvcAutoConfiguration.class, org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration.class,
        JacksonAutoConfiguration.class})
@AutoConfigureAfter({DispatcherServletAutoConfiguration.class,
        TaskExecutionAutoConfiguration.class, ValidationAutoConfiguration.class})
public class WebMvcAutoConfiguration implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }


    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder = Jackson2ObjectMapperBuilder.json();
        jackson2ObjectMapperBuilderCustomizer().customize(jackson2ObjectMapperBuilder);
        converters.add(new MappingJackson2HttpMessageConverter(jackson2ObjectMapperBuilder.build()));
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return ((builder) -> {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

            builder.serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(dateTimeFormatter));
            builder.serializerByType(LocalDate.class, new LocalDateTimeSerializer(dateFormatter));
            builder.serializerByType(LocalTime.class, new LocalDateTimeSerializer(timeFormatter));
            builder.deserializerByType(LocalDateTime.class, new LocalTimeDeserializer(dateTimeFormatter));
            builder.deserializerByType(LocalDate.class, new LocalTimeDeserializer(dateFormatter));
            builder.deserializerByType(LocalTime.class, new LocalTimeDeserializer(timeFormatter));
        });
    }


    @Bean
    @ConditionalOnMissingBean(value = ErrorController.class, search = SearchStrategy.CURRENT)
    public CustomErrorController errorController(ErrorAttributes errorAttributes, ServerProperties serverProperties, List<ErrorViewResolver> errorViewResolvers) {
        return new CustomErrorController(errorAttributes, serverProperties.getError(), errorViewResolvers);
    }

    @Bean
    @ConditionalOnMissingBean(value = PreExceptionHandle.class)
    public PreExceptionHandle preExceptionHandle(ServerProperties serverProperties) {
        PreExceptionHandle preExceptionHandle = new PreExceptionHandle();
        preExceptionHandle.setEnableStackTrace(serverProperties.getError().getIncludeStacktrace() == ErrorProperties.IncludeStacktrace.ALWAYS);
        return preExceptionHandle;
    }

    @Bean
    @ConditionalOnMissingBean(value = PostExceptionHandle.class)
    public PostExceptionHandle postExceptionHandle(ServerProperties serverProperties) {
        PostExceptionHandle postExceptionHandle = new PostExceptionHandle();
        postExceptionHandle.setEnableStackTrace(serverProperties.getError().getIncludeStacktrace() == ErrorProperties.IncludeStacktrace.ALWAYS);
        return postExceptionHandle;
    }
}
