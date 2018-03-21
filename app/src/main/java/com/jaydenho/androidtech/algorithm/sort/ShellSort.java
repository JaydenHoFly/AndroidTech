package com.jaydenho.androidtech.algorithm.sort;

/**
 * Created by Administrator on 2018/3/21.
 */

public class ShellSort {
    public static <AnyType extends Comparable<? super AnyType>>
    void shellSort(AnyType[] a) {
        int j;
        for (int gap = a.length / 2; gap >= 1; gap /= 2) {
            for (int i = gap; i < a.length; i++) {
                AnyType temp = a[i];
                for (j = i; j >= gap && temp.compareTo(a[j - gap]) < 0; j -= gap) {
                    a[j] = a[j - gap];
                }
                a[j] = temp;
            }
        }
    }
}
