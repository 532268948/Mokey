package com.example.lib_common.util;

import java.math.BigDecimal;

/**
 * @author : 叶天华
 * @project: Monkey
 * @date : 2019/2/6
 * @time : 14:11
 * @email : 15869107730@163.com
 * @note :
 */
public class NumberUtil {
    public static double div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
