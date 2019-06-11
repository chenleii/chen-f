package com.chen.f.spring.boot.autoconfigure.swagger2;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author chen
 * @since 2017/12/19 13:05.
 */
@Configuration
@Profile("beta")
@ConditionalOnClass({EnableSwagger2.class})
@EnableSwagger2
public class Swagger2AutoConfiguration {

    @ConditionalOnMissingBean
    @Bean
    public Docket customDocket() {
        Contact contact = new Contact("chen", "http://chen623.com/", "1@1.com");

        ApiInfo apiInfo = new ApiInfoBuilder()
                .contact(contact)
                .title("chen623 api")
                .description("chen623管理接口")
                .version("1.0.1")
                .build();

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.chen.f"))
                .paths(PathSelectors.any())
                .build();


    }
}
