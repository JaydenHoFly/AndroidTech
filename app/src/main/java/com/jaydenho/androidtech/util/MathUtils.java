package com.jaydenho.androidtech.util;

/**
 * Created by hedazhao on 2017/11/17.
 */

public class MathUtils {
    public static int plusOrMinus(boolean isPlus, int i1, int i2) {
        return isPlus ? i1 + i2 : i1 - i2;
    }
}
