package com.chen.f.core.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author chen
 * @since 2018/11/3 19:19.
 */
public class IdUtils {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
    private static final AtomicInteger SEQUENCE = new AtomicInteger(Integer.MAX_VALUE - 100);



    public static String getId() {
        int sequence = SEQUENCE.updateAndGet(i -> i < 0 ? 0 : i + 1);
        String datetimeStr = DATE_TIME_FORMATTER.format(LocalDateTime.now());
        return String.format("%s%011d", datetimeStr, sequence);
    }


    public static String getId(String prefix) {
        return prefix + getId();
    }
    

}
