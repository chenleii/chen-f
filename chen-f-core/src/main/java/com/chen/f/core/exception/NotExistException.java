package com.chen.f.core.exception;

/**
 * 不存在异常
 * <p>
 * 因为不存在导致的异常
 *
 * @author chen
 * @since 2020/6/3 17:24.
 */
public class NotExistException extends RuntimeException {
    public NotExistException() {
    }

    public NotExistException(String message) {
        super(message);
    }

    public NotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotExistException(Throwable cause) {
        super(cause);
    }

    public NotExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
