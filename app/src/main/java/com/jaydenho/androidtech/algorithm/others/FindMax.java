package com.jaydenho.androidtech.algorithm.others;

/**
 * 一个数组先升序再降序，用最优时间复杂度，求最大值？例如[1,2,2,2,2,3,1]
 * Created by hedazhao on 2021/5/25.
 */
public class FindMax {
    public int getMax(int[] arr) {
        if (arr.length < 2) return arr[0];

        int lo = 0, hi = arr.length - 1;

        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;

            if (arr[mid] > arr[mid + 1]) hi = mid;
            else if (arr[mid] < arr[mid + 1]) lo = mid + 1;
            else {
                if (arr[lo] > arr[hi]) hi--;
                else lo++;
            }
        }
        return arr[lo];
    }

    public int findMax(int[] nums) {
        int il = 0, ir = nums.length - 1;
        while (il > ir) {
            int mid = il + (ir - il) / 2;

            if(nums[mid] > nums[mid + 1]) {
                ir = mid - 1;
            } else if(nums[mid] < nums[mid + 1]) {
                il = mid + 1;
            } else {
                if(nums[il] > nums[ir]) {
                    ir--;
                } else {
                    il++;
                }
            }
        }
        return nums[il];
    }
}
