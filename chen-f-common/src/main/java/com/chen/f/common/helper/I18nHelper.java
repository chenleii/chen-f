package com.chen.f.common.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

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

    public I18nHelper(MessageSource messageSource) {
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


    public static String getMessage(String code, Object... objects) {
        return messageSource.getMessage(code, objects, LocaleContextHolder.getLocale());
    }

    public static String getMessage(String code, Locale locale, Object... objects) {
        return messageSource.getMessage(code, objects, locale);
    }

    public static String getMessage(String code, Locale locale, String defaultMessage, Object... objects) {
        return messageSource.getMessage(code, objects, defaultMessage, locale);
    }


}
