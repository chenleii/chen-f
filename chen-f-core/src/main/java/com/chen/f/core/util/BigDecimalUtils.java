package com.chen.f.core.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * @author chen
 * @since 2018/11/1 15:14.
 */
public class BigDecimalUtils {

    public static BigDecimal create(String value) {
        return new BigDecimal(value);
    }

    public static BigDecimal create(Number value) {
        return new BigDecimal(value.toString());
    }

    public static BigDecimal create(double value) {
        return new BigDecimal(value);
    }

    public static BigDecimal create(float value) {
        return new BigDecimal(value);
    }

    public static BigDecimal create(int value) {
        return new BigDecimal(value);
    }

    public static BigDecimal create(long value) {
        return new BigDecimal(value);
    }

    /**
     * 格式化小数
     *
     * @param value        值
     * @param pattern      格式化模式
     * @param roundingMode 舍入模式
     * @return 格式化后的数值字符串
     */
    public static <T extends Number> String format(T value, String pattern, RoundingMode roundingMode) {
        DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.applyLocalizedPattern(pattern);
        decimalFormat.setRoundingMode(roundingMode);
        return decimalFormat.format(value);
    }

    /**
     * 格式化千分位保留两位小数四舍五入
     *
     * @param value 值
     * @return 格式化后的数值字符串
     */
    public static String formatThousand2ScaleHalfUp(Number value) {
        return format(value, ",###,##0.00", RoundingMode.HALF_UP);
    }

    /**
     * 格式化保留两位小数四舍五入
     *
     * @param value 值
     * @return 格式化后的数值字符串
     */
    public static String format2ScaleHalfUp(Number value) {
        return format(value, "#0.00", RoundingMode.HALF_UP);
    }

    /**
     * 格式化百分位保留两位小数四舍五入
     *
     * @param value 值
     * @return 格式化后的数值字符串
     */
    public static String formatPercentage2ScaleHalfUp(Number value) {
        return format(value, "#0.00%", RoundingMode.HALF_UP);
    }

    /**
     * 加
     * 如果为null制为0
     *
     * @param value1 被加数
     * @param value2 加数
     * @return 算数结果
     */
    public static BigDecimal add(BigDecimal value1, BigDecimal value2) {
        if (value1 == null) {
            value1 = BigDecimal.ZERO;
        }
        if (value2 == null) {
            value2 = BigDecimal.ZERO;
        }
        return value1.add(value2);
    }

    /**
     * 减
     *
     * @param value1 被减数
     * @param value2 减数
     * @return 算数结果
     */
    public static BigDecimal subtract(BigDecimal value1, BigDecimal value2) {
        return value1.subtract(value2);
    }

    /**
     * 乘
     *
     * @param value1 被乘数
     * @param value2 乘数
     * @return 算数结果
     */
    public static BigDecimal multiply(BigDecimal value1, BigDecimal value2) {
        return value1.multiply(value2);
    }

    /**
     * 除
     *
     * @param value1       被除数
     * @param value2       除数
     * @param scale        保留的小数位
     * @param roundingMode 舍入模式
     * @return 算数结果
     */
    public static BigDecimal divide(BigDecimal value1, BigDecimal value2, int scale, RoundingMode roundingMode) {
        return value1.divide(value2, scale, roundingMode);
    }

    /**
     * 除
     * 舍入模式:四舍五入
     *
     * @param value1 被除数
     * @param value2 除数
     * @param scale  保留的小数位
     * @return 算数结果
     */
    public static BigDecimal divideHalfUp(BigDecimal value1, BigDecimal value2, int scale) {
        return value1.divide(value2, scale, RoundingMode.HALF_UP);
    }

    /**
     * 除
     * 保留的小数位:2
     * 舍入模式:四舍五入
     *
     * @param value1 被除数
     * @param value2 除数
     * @return 算数结果
     */
    public static BigDecimal divide2ScaleHalfUp(BigDecimal value1, BigDecimal value2) {
        return value1.divide(value2, 2, RoundingMode.HALF_UP);
    }

    /**
     * 舍入
     *
     * @param value        需要进行舍入数
     * @param scale        保留小数位数
     * @param roundingMode 舍入模式
     * @return 摄入结果
     */
    public static BigDecimal round(BigDecimal value, int scale, RoundingMode roundingMode) {
        return value.setScale(scale, roundingMode);
    }

    /**
     * 舍入
     * 保留小数位数:2
     * 舍入模式:四舍五入
     *
     * @param value 需要进行舍入数
     * @return 摄入结果
     */
    public static BigDecimal round2ScaleHalfUp(BigDecimal value) {
        return value.setScale(2, RoundingMode.HALF_UP);
    }


    /**
     * 小数点左移n位
     *
     * @param value 值
     * @param n     位数
     * @return 计算结果
     */
    public BigDecimal movePointLeft(BigDecimal value, int n) {
        return value.movePointLeft(n);
    }

    /**
     * 小数点右移n位
     *
     * @param value 值
     * @param n     位数
     * @return 计算结果
     */
    public BigDecimal movePointRight(BigDecimal value, int n) {
        return value.movePointRight(n);
    }


    /**
     * 绝对值
     *
     * @param value 值
     * @return 绝对值
     */
    public static BigDecimal abs(BigDecimal value) {
        return value.abs();
    }

    /**
     * 取反数
     *
     * @param value 值
     * @return 反数
     */
    public static BigDecimal negate(BigDecimal value) {
        return value.negate();
    }

    /**
     * 负数
     *
     * @param value 值
     * @return 负数
     */
    public static BigDecimal negative(BigDecimal value) {
        return value.abs().negate();
    }

    /**
     * 是否等于
     *
     * @param value1 左值
     * @param value2 右值
     * @return 是\否
     */
    public static boolean isEqualTo(BigDecimal value1, BigDecimal value2) {
        return value1.compareTo(value2) == 0;
    }

    /**
     * 是否大于
     *
     * @param value1 左值
     * @param value2 右值
     * @return 是\否
     */
    public static boolean isGreaterThan(BigDecimal value1, BigDecimal value2) {
        return value1.compareTo(value2) > 0;
    }

    /**
     * 是否大于等于
     *
     * @param value1 左值
     * @param value2 右值
     * @return 是\否
     */
    public static boolean isGreaterThanOrEqualTo(BigDecimal value1, BigDecimal value2) {
        return value1.compareTo(value2) >= 0;
    }

    /**
     * 是否小于
     *
     * @param value1 左值
     * @param value2 右值
     * @return 是\否
     */
    public static boolean isLessThan(BigDecimal value1, BigDecimal value2) {
        return value1.compareTo(value2) < 0;
    }

    /**
     * 是否小于等于
     *
     * @param value1 左值
     * @param value2 右值
     * @return 是\否
     */
    public static boolean isLessThanOrEqualTo(BigDecimal value1, BigDecimal value2) {
        return value1.compareTo(value2) <= 0;
    }


    /**
     * 是否是负数
     *
     * @param value 值
     * @return 是\否
     */
    public static boolean isNegative(BigDecimal value) {
        return value.signum() == -1;
    }

    /**
     * 是否是零
     *
     * @param value 值
     * @return 是\否
     */
    public static boolean isZero(BigDecimal value) {
        return value.signum() == 0;
    }

    /**
     * 是否是正数
     *
     * @param value 值
     * @return 是\否
     */
    public static boolean isPositive(BigDecimal value) {
        return value.signum() == 1;
    }


}
