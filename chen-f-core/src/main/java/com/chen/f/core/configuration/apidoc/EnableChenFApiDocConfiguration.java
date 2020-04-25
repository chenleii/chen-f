package com.chen.f.core.configuration.apidoc;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportAware;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.Assert;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * chen-f 接口文档配置类
 *
 * @author chen
 * @since 2017/12/19 13:05.
 */
@Configuration
@Profile({"local", "beta"})
@ConditionalOnClass({EnableSwagger2.class, EnableKnife4j.class})
public class EnableChenFApiDocConfiguration implements ImportAware {
    protected static final Logger logger = LoggerFactory.getLogger(EnableChenFApiDocConfiguration.class);

    private String title;
    private String description;
    private String version;
    private String licenseUrl;

    private String basePackage;

    private String contactName;
    private String contactUrl;
    private String contactEmail;


    @Override
    public void setImportMetadata(AnnotationMetadata importMetadata) {
        AnnotationAttributes annotationAttributes = AnnotationAttributes
                .fromMap(importMetadata.getAnnotationAttributes(EnableChenFApiDoc.class.getName()));
        Assert.notNull(annotationAttributes,
                "@EnableChenFSwagger is not present on importing class " + importMetadata.getClassName());

        this.title = annotationAttributes.getString("title");
        this.description = annotationAttributes.getString("description");
        this.version = annotationAttributes.getString("version");
        this.licenseUrl = annotationAttributes.getString("licenseUrl");
        this.basePackage = annotationAttributes.getString("basePackage");
        this.contactName = annotationAttributes.getString("contactName");
        this.contactUrl = annotationAttributes.getString("contactUrl");
        this.contactEmail = annotationAttributes.getString("contactEmail");

    }

    @Bean
    @ConditionalOnMissingBean
    public Docket customDocket() {
        Contact contact = new Contact(this.contactName, this.contactUrl, this.contactEmail);

        ApiInfo apiInfo = new ApiInfoBuilder()
                .contact(contact)
                .title(this.title)
                .description(this.description)
                .version(this.version)
                .licenseUrl(this.licenseUrl)
                .build();

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.basePackage(this.basePackage))
                .paths(PathSelectors.any())
                .build();


    }
}
