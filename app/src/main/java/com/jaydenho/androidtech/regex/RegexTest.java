package com.jaydenho.androidtech.regex;

import java.util.Arrays;

/**
 * Created by hedazhao on 2018/6/23.
 */
public class RegexTest {
    public static void testSplit(String input, String regex, int limit) {
        //如果limit是正数，那么regex应用的次数最多是limit-1. 如果limit是负数，那么尽可能切割。如果limit是0, 那么会尽可能切割，但是会抛弃数组末尾的空元素。
        String[] result = input.split(regex, limit);
        System.out.print(Arrays.toString(result));
    }
}
