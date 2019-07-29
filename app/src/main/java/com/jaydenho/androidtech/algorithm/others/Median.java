package com.jaydenho.androidtech.algorithm.others;

/**
 * 寻找两个升序数组合并后的中位数。
 * https://mp.weixin.qq.com/s?__biz=MzIxMjE5MTE1Nw==&mid=2653199183&idx=1&sn=6982272dec695edacdc9066a9ab0963f&chksm=8c99e995bbee6083354a405715636848cc5760095ed3a3afe916504a9676b3ee1a75d59a85f2&mpshare=1&scene=1&srcid=0729K0hSBpyTUgRNkwgRaHDV&sharer_sharetime=1564367383858&sharer_shareid=a4a2282b938ce47a423d42a25a8f8634&key=f186ad987e7761444ebdedbbe4618a592da7ea75cbbf2bb243aad55aa9e1f2a68bc7807a7854fc3556a17ec452f41c1d3f81b11617a2457a381aebf076299554fd3dc88ffaf81096652a101364ec8c1f&ascene=1&uin=MjI2MjI5MjgyOQ%3D%3D&devicetype=Windows+10&version=62060833&lang=zh_CN&pass_ticket=3CYnyT%2Faeo%2BNeIurvhqMrWG%2Bt2HCs7c9zNDmS0jjaLG%2BwMe0NIOC%2F4fzfjlWjFlm
 * Created by hedazhao on 2019/7/29.
 */
public class Median {

    public static double findMedianSortedArrays(int[] arrayA, int[] arrayB) {
        int m = arrayA.length;
        int n = arrayB.length;    //如果数组A的长度大于等于数组B，则交换数组
        if (m > n) {
            int[] temp = arrayA;
            arrayA = arrayB;
            arrayB = temp;
            int tmp = m;
            m = n;
            n = tmp;
        }
        int start = 0;
        int end = m;
        int mid = (m + n + 1) / 2;
        while (start <= end) {
            int i = (start + end) / 2;
            int j = mid - i;
            if (i < end && arrayB[j - 1] > arrayA[i]) {            //i偏小了，需要右移
                start = i + 1;
            } else if (i > start && arrayA[i - 1] > arrayB[j]) {            //i偏大了，需要左移
                end = i - 1;
            } else {            //i刚好合适
                int maxLeft;
                if (i == 0) {                //数组A的元素都大于数组B的情况
                    maxLeft = arrayB[j - 1];
                } else if (j == 0) {                //数组A的元素都小于数组B的情况
                    maxLeft = arrayA[i - 1];
                } else {
                    maxLeft = Math.max(arrayA[i - 1], arrayB[j - 1]);
                }
                if ((m + n) % 2 == 1) {                //如果大数组的长度是奇数，中位数就是左半部分的最大值
                    return maxLeft;
                }
                int minRight;
                if (i == m) {
                    minRight = arrayB[j];
                } else if (j == n) {
                    minRight = arrayA[i];
                } else {
                    minRight = Math.min(arrayB[j], arrayA[i]);
                }            //如果大数组的长度是偶数，取左侧最大值和右侧最小值的平均
                return (maxLeft + minRight) / 2.0;
            }
        }
        return 0.0;
    }

    public static void main(String[] args) {
        int[] arrayA = new int[]{1, 3, 5, 6};
        int[] arrayB = new int[]{7, 8, 9, 10, 11, 12, 13};
        System.out.println(findMedianSortedArrays(arrayA, arrayB));
    }
}
