package com.chen.f.core.util.lambda;

/**
 * @author chen
 * @since 2020/4/24 14:51.
 */
@FunctionalInterface
public interface Function0<R> extends Function { 
    R apply();
}
