package com.jaydenho.androidtech.algorithm.sort;

/**
 * Created by Administrator on 2018/3/20.
 */

public class InsertSort {
    public static <AnyType extends Comparable<? super AnyType>>
    void sort(AnyType[] a) {
        int j;
        for (int i = 1; i < a.length; i++) {
            AnyType temp = a[i];
            for (j = i; j > 0 && temp.compareTo(a[j - 1]) < 0; j--) {
                a[j] = a[j - 1];
            }
            a[j] = temp;
        }
    }
}
