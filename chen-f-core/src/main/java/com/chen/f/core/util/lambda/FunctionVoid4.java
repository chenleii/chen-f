package com.chen.f.core.util.lambda;

/**
 * @author chen
 * @since 2020/4/24 14:52.
 */
@FunctionalInterface
public interface FunctionVoid4<T1, T2, T3, T4> extends Function {
    void apply(T1 t1, T2 t2, T3 t3, T4 t4);
}
