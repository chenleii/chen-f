package com.chen.f.core.util.lambda;

/**
 * @author chen
 * @since 2020/4/24 14:21.
 */
@FunctionalInterface
public interface Function1<R, T1> extends Function {
    R apply(T1 t1);
}