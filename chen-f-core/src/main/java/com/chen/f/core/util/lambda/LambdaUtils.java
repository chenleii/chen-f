package com.chen.f.core.util.lambda;

import java.lang.invoke.SerializedLambda;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Lambda 解析工具类
 *
 * @author chen
 * @since 2020/4/24 12:04.
 */
public class LambdaUtils {

    /**
     * SerializedLambda 反序列化缓存
     */
    private static final Map<Class<?>, WeakReference<SerializedLambda>> FUNC_CACHE = new ConcurrentHashMap<>();


    /**
     * 转换 lambda 表达式
     * <p>
     * 该方法只能序列化 lambda 表达式，不能序列化接口实现或者正常非 lambda 写法的对象
     *
     * @param lambda lambda对象
     * @return 返回解析后的 SerializedLambda
     */
    private static SerializedLambda resolveSerializedLambda(Object lambda) {
        if (!lambda.getClass().isSynthetic()) {
            throw new IllegalArgumentException("is not lambda class");
        }
        try {
            final Method writeReplace = lambda.getClass().getDeclaredMethod("writeReplace");
            writeReplace.setAccessible(true);
            return (SerializedLambda) writeReplace.invoke(lambda);
        } catch (NoSuchMethodException e) {
            //可能是类加载器问题 例如使用了spring热部署
            throw new RuntimeException("This is impossible to happen", e);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("This is impossible to happen", e);
        }
    }


    /**
     * 解析 lambda 表达式,
     * 该缓存可能会在任意不定的时间被清除
     *
     * @param func 需要解析的 lambda 对象
     * @return 返回解析后的结果
     */
    public static SerializedLambda resolve(Object func) {
        Class<?> clazz = func.getClass();

        return Optional.ofNullable(FUNC_CACHE.get(clazz))
                .map(WeakReference::get)
                .orElseGet(() -> {
                    SerializedLambda lambda = resolveSerializedLambda(func);
                    FUNC_CACHE.put(clazz, new WeakReference<>(lambda));
                    return lambda;
                });
    }

    public static <R> SerializedLambda resolve(Function0<R> func) {
        return resolve((Object) func);
    }

    public static <R, T1> SerializedLambda resolve(Function1<R, T1> func) {
        return resolve((Object) func);
    }

    public static <R, T1, T2> SerializedLambda resolve(Function2<R, T1, T2> func) {
        return resolve((Object) func);
    }

    public static <R, T1, T2, T3> SerializedLambda resolve(Function3<R, T1, T2, T3> func) {
        return resolve((Object) func);
    }

    public static <R, T1, T2, T3, T4> SerializedLambda resolve(Function4<R, T1, T2, T3, T4> func) {
        return resolve((Object) func);
    }

    public static <R, T1, T2, T3, T4, T5> SerializedLambda resolve(Function5<R, T1, T2, T3, T4, T5> func) {
        return resolve((Object) func);
    }

    public static <R, T1, T2, T3, T4, T5, T6> SerializedLambda resolve(Function6<R, T1, T2, T3, T4, T5, T6> func) {
        return resolve((Object) func);
    }

    public static <R, T1, T2, T3, T4, T5, T6, T7> SerializedLambda resolve(Function7<R, T1, T2, T3, T4, T5, T6, T7> func) {
        return resolve((Object) func);
    }

    public static <R, T1, T2, T3, T4, T5, T6, T7, T8> SerializedLambda resolve(Function8<R, T1, T2, T3, T4, T5, T6, T7, T8> func) {
        return resolve((Object) func);
    }

    public static <R, T1, T2, T3, T4, T5, T6, T7, T8, T9> SerializedLambda resolve(Function9<R, T1, T2, T3, T4, T5, T6, T7, T8, T9> func) {
        return resolve((Object) func);
    }

    public static SerializedLambda resolve(FunctionVoid0 func) {
        return resolve((Object) func);
    }

    public static <T1> SerializedLambda resolve(FunctionVoid1<T1> func) {
        return resolve((Object) func);
    }

    public static <T1, T2> SerializedLambda resolve(FunctionVoid2<T1, T2> func) {
        return resolve((Object) func);
    }

    public static <T1, T2, T3> SerializedLambda resolve(FunctionVoid3<T1, T2, T3> func) {
        return resolve((Object) func);
    }

    public static <T1, T2, T3, T4> SerializedLambda resolve(FunctionVoid4<T1, T2, T3, T4> func) {
        return resolve((Object) func);
    }

    public static <T1, T2, T3, T4, T5> SerializedLambda resolve(FunctionVoid5<T1, T2, T3, T4, T5> func) {
        return resolve((Object) func);
    }

    public static <T1, T2, T3, T4, T5, T6> SerializedLambda resolve(FunctionVoid6<T1, T2, T3, T4, T5, T6> func) {
        return resolve((Object) func);
    }

    public static <T1, T2, T3, T4, T5, T6, T7> SerializedLambda resolve(FunctionVoid7<T1, T2, T3, T4, T5, T6, T7> func) {
        return resolve((Object) func);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8> SerializedLambda resolve(FunctionVoid8<T1, T2, T3, T4, T5, T6, T7, T8> func) {
        return resolve((Object) func);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9> SerializedLambda resolve(FunctionVoid9<T1, T2, T3, T4, T5, T6, T7, T8, T9> func) {
        return resolve((Object) func);
    }


}
