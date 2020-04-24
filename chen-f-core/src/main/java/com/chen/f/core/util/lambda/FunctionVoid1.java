package com.chen.f.core.util.lambda;

/**
 * @author chen
 * @since 2020/4/24 14:21.
 */
@FunctionalInterface
public interface FunctionVoid1<T1> extends Function {
    void apply(T1 t1);
}