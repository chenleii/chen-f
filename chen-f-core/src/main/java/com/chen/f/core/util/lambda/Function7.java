package com.chen.f.core.util.lambda;

/**
 * @author chen
 * @since 2020/4/24 14:52.
 */
@FunctionalInterface
public interface Function7<R, T1, T2, T3, T4, T5, T6, T7> extends Function {
  R apply(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7);
}
