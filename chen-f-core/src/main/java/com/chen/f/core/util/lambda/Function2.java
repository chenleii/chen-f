package com.chen.f.core.util.lambda;

/**
 * @author chen
 * @since 2020/4/24 14:52.
 */
@FunctionalInterface
public interface Function2<R, T1, T2> extends Function {
   R apply(T1 t1, T2 t2);
}
