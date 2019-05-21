package com.jaydenho.androidtech.algorithm.common.dynamicprogramming;

import java.util.HashMap;
import java.util.Map;

/**
 * 台阶问题。
 * <p>
 * 有一座高度是10级台阶的楼梯，从下往上走，每跨一步只能向上1级或者2级台阶。要求用程序来求出一共有多少种走法。
 * 比如，每次走1级台阶，一共走10步，这是其中一种走法。我们可以简写成 1,1,1,1,1,1,1,1,1,1。
 * 再比如，每次走2级台阶，一共走5步，这是另一种走法。我们可以简写成 2,2,2,2,2。
 * <p>
 * https://mp.weixin.qq.com/s?__biz=MzIxMjE5MTE1Nw==&mid=2653190796&idx=1&sn=2bf42e5783f3efd03bfb0ecd3cbbc380&chksm=8c990856bbee8140055c3429f59c8f46dc05be20b859f00fe8168efe1e6a954fdc5cfc7246b0&scene=21#wechat_redirect
 * <p>
 * Created by hedazhao on 2019/5/21.
 */
public class Step {
    public static void main(String[] args) {
        System.out.println("sum(10)=" + sum(10));
        System.out.println("sum2(10)=" + sum2(10, null));
        System.out.println("sum3(10)=" + sum3(10));
    }

    /**
     * 最普通的方法，时间复杂度O(2^n)。
     */
    public static int sum(int n) {
        if (n == 2) {
            return 2;
        }
        if (n == 1) {
            return 1;
        }
        return sum(n - 1) + sum(n - 2);
    }

    /**
     * 备忘录法，时间复杂度O(n)，空间复杂度O(n)。
     */
    public static int sum2(int n, Map<Integer, Integer> records) {
        if (n == 2) {
            return 2;
        }
        if (n == 1) {
            return 1;
        }
        if (records == null) {
            records = new HashMap<>();
        }
        if (records.containsKey(n)) {
            return records.get(n);
        } else {
            int result = sum2(n - 1, records) + sum2(n - 2, records);
            records.put(n, result);
            return result;
        }
    }

    /**
     * 动态规划，自底向上，时间复杂度O(n)，空间复杂度O(1)。
     */
    public static int sum3(int n) {
        if (n == 2) {
            return 2;
        }
        if (n == 1) {
            return 1;
        }
        int t1 = 1, t2 = 2, result = 0;
        for (int i = 3; i <= n; i++) {
            result = t1+t2;
            t1 = t2;
            t2 = result;
        }
        return result;
    }

}
