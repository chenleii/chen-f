package com.chen.f.core.configuration.i18n;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.DelegatingMessageSource;
import org.springframework.util.Assert;

import java.time.ZoneId;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 国际化帮助类
 *
 * @author chen
 * @since 2019/11/7 17:18.
 */
public class I18nHelper {
    protected static final Logger logger = LoggerFactory.getLogger(I18nHelper.class);

    private static MessageSource messageSource;

    static {
        I18nHelper.messageSource = new DelegatingMessageSource();
    }

    public I18nHelper(MessageSource messageSource) {
        Assert.notNull(messageSource, "messageSource can not be empty");
        I18nHelper.messageSource = messageSource;
    }

    public static Locale getLocale() {
        return LocaleContextHolder.getLocale();
    }

    public static TimeZone getTimeZone() {
        return LocaleContextHolder.getTimeZone();
    }

    public static ZoneId getZoneId() {
        return LocaleContextHolder.getTimeZone().toZoneId();
    }


    public static String getMessage(String code) {
        return messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
    }

    public static String getMessage(String code, Locale locale) {
        return messageSource.getMessage(code, null, locale);
    }

    public static String getMessage(String code, String defaultMessage) {
        return messageSource.getMessage(code, null, defaultMessage, LocaleContextHolder.getLocale());
    }

    public static String getMessage(String code, String defaultMessage, Locale locale) {
        return messageSource.getMessage(code, null, defaultMessage, locale);
    }

    public static String getMessage(String code, Object[] args) {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }

    public static String getMessage(String code, Object[] args, String defaultMessage) {
        return messageSource.getMessage(code, args, defaultMessage, LocaleContextHolder.getLocale());
    }

    public static String getMessage(String code, Object[] args, Locale locale) {
        return messageSource.getMessage(code, args, locale);
    }

    public static String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
        return messageSource.getMessage(code, args, defaultMessage, locale);
    }


}
