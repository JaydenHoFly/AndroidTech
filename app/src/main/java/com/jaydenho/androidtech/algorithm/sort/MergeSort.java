package com.jaydenho.androidtech.algorithm.sort;

import java.util.Arrays;

/**
 * 归并排序。
 * https://www.cnblogs.com/chengxiao/p/6194356.html
 * <p>
 * Created by hedazhao on 2019/5/7.
 */
public class MergeSort {
    public static void main(String[] args) {
        int[] array = new int[]{3, 1, 34, 52, 8, 5, 3, 0, 9};
        System.out.println("mergeSort: " + Arrays.toString(sort(array)));
        betterSort(array);
        System.out.println("betterMergeSort: " + Arrays.toString(array));
    }

    /**
     * 这个排序算法是自己写的，没有考虑好内存的问题。
     */
    public static int[] sort(int[] array) {
        if (array.length == 1) {
            return array;
        }
        int middle = array.length / 2;
        int[] array1 = sort(Arrays.copyOfRange(array, 0, middle));
        int[] array2 = sort(Arrays.copyOfRange(array, middle, array.length));
        return merge(array1, array2);
    }

    private static int[] merge(int[] array1, int[] array2) {
        int[] array = new int[array1.length + array2.length];
        int i = 0, j = 0, k = 0;
        while (i < array1.length || j < array2.length) {
            if (j == array2.length || (i < array1.length && array1[i] < array2[j])) {
                array[k++] = array1[i++];
            } else {
                array[k++] = array2[j++];
            }
        }
        return array;
    }

    /**
     * 这个算法是参考https://www.cnblogs.com/chengxiao/p/6194356.html实现的。
     *
     * @param array 要排序的数组。
     */
    public static void betterSort(int[] array) {
        //预先分配一个临时数据，减少内存分配。
        int[] temp = new int[array.length];
        betterSort(array, 0, array.length - 1, temp);
    }

    /**
     * 对数组的[left,right]区间进行排序。
     *
     * @param array 要排序的数组。
     * @param left  要排序部分的左指针，包含。
     * @param right 要排序部分的右指针，包含。
     * @param temp  临时数组。
     */
    private static void betterSort(int[] array, int left, int right, int[] temp) {
        if (left < right) {
            //要排序部分的数量超过1才需要排序。
            int middle = (left + right) / 2;
            //分，将数组分开排序。
            betterSort(array, left, middle, temp);
            betterSort(array, middle + 1, right, temp);
            //合并，此时[left,middle]和[middle+1,right]两部分均已排好序。
            betterMerge(array, left, middle, right, temp);
        }
    }

    /**
     * 将两个排好序的部分[left, middle]和[middle + 1, right]合并。
     *
     * @param array  数组。
     * @param left   左指针，包含。
     * @param middle 中间指针，属于左侧。
     * @param right  右指针，包含。
     * @param temp   临时数组。
     */
    private static void betterMerge(int[] array, int left, int middle, int right, int[] temp) {
        int i = left, j = middle + 1, k = 0;
        while (i <= middle && j <= right) {
            //两段数组都没遍历完。
            if (array[i] < array[j]) {
                //左侧数据更小，k位置上使用左侧数据。
                temp[k++] = array[i++];
            } else {
                temp[k++] = array[j++];
            }
        }
        while (i <= middle) {
            //此时一定有一段已排序数组遍历完毕，将另一段已排序数组顺次添加到临时数组中即可。
            temp[k++] = array[i++];
        }
        while (j <= right) {
            //此时一定有一段已排序数组遍历完毕，将另一段已排序数组顺次添加到临时数组中即可。
            temp[k++] = array[j++];
        }
        k = 0;
        while (left <= right) {
            //将临时数组中的内容替换到原始数组中。
            array[left++] = temp[k++];
        }
    }

}
