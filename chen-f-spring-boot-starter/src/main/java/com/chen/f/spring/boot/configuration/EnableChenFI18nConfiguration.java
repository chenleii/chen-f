package com.chen.f.spring.boot.configuration;

import com.chen.f.common.helper.I18nHelper;
import com.chen.f.spring.boot.configuration.i18n.I18nFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.Assert;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.Locale;

/**
 * 国际化配置类
 *
 * @author chen
 * @since 2019/11/9 17:45.
 */
@Configuration
public class EnableChenFI18nConfiguration implements ImportAware {
    protected static final Logger logger = LoggerFactory.getLogger(EnableChenFI18nConfiguration.class);

    private String timeZoneHeaderName;
    private Integer i18nFilterOrder;

    @Override
    public void setImportMetadata(AnnotationMetadata importMetadata) {
        AnnotationAttributes annotationAttributes = AnnotationAttributes
                .fromMap(importMetadata.getAnnotationAttributes(EnableChenFI18n.class.getName()));
        Assert.notNull(annotationAttributes, "没有找到 " + EnableChenFI18n.class.getName() + " AnnotationAttributes");

        this.timeZoneHeaderName = annotationAttributes.getString("timeZoneHeaderName");
        this.i18nFilterOrder = annotationAttributes.getNumber("i18nFilterOrder");
    }

    @Bean
    public I18nHelper i18nHelper(MessageSource messageSource) {
        return new I18nHelper(messageSource);
    }
    
    @Bean
    @ConditionalOnMissingBean
    public FilterRegistrationBean<I18nFilter> i18nFilterFilterRegistrationBean(@Autowired(required = false)
                                                                                       LocaleResolver localeResolver) {
        if (localeResolver == null) {
            localeResolver = defaultLocaleResolver();
        }
        I18nFilter i18nFilter = new I18nFilter(localeResolver, timeZoneHeaderName);

        FilterRegistrationBean<I18nFilter> i18nFilterFilterRegistrationBean = new FilterRegistrationBean<>();
        i18nFilterFilterRegistrationBean.setFilter(i18nFilter);
        i18nFilterFilterRegistrationBean.setOrder(i18nFilterOrder);
        i18nFilterFilterRegistrationBean.addUrlPatterns("/*");
        return i18nFilterFilterRegistrationBean;
    }

    private LocaleResolver defaultLocaleResolver() {
        AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
        localeResolver.setDefaultLocale(Locale.CHINA);
        return localeResolver;
    }
}
