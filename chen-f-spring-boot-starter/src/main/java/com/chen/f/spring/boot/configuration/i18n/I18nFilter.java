package com.chen.f.spring.boot.configuration.i18n;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.servlet.LocaleContextResolver;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 国际化过滤器
 * <p>
 * 设置语言和时区
 *
 * @author chen
 * @since 2019/11/8 18:25.
 */
public class I18nFilter extends HttpFilter {
    protected static final Logger logger = LoggerFactory.getLogger(I18nFilter.class);

    private static final String DEFAULT_TIME_ZONE_HEADER_NAME = "time-zone";

    private LocaleResolver localeResolver;

    private String timeZoneHeaderName = DEFAULT_TIME_ZONE_HEADER_NAME;

    public I18nFilter(LocaleResolver localeResolver) {
        this.localeResolver = localeResolver;
    }

    public I18nFilter(LocaleResolver localeResolver, String timeZoneHeaderName) {
        this.localeResolver = localeResolver;
        this.timeZoneHeaderName = timeZoneHeaderName;
    }

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        LocaleResolver localeResolver = this.localeResolver;

        if (localeResolver instanceof LocaleContextResolver) {
            LocaleContext localeContext = ((LocaleContextResolver) localeResolver).resolveLocaleContext(req);
            LocaleContextHolder.setLocaleContext(localeContext);
        } else {
            Locale locale = localeResolver.resolveLocale(req);
            LocaleContextHolder.setLocale(locale);
        }

        String timeZoneHeader = req.getHeader(this.timeZoneHeaderName);
        if (StringUtils.isNotBlank(timeZoneHeader)) {
            TimeZone timeZone = TimeZone.getTimeZone(timeZoneHeader);
            LocaleContextHolder.setTimeZone(timeZone);
        }

        super.doFilter(req, res, chain);
    }
}
