package com.jaydenho.androidtech.algorithm.sort;

import android.util.Log;

import java.util.Random;

/**
 * Created by hedazhao on 2021/5/25.
 */
public class QuickPick {

    public static void main(String[] args){
        int[] nums = new int[]{1,4,1,3,5,6,9,0};
        new QuickPick().quickPick(nums, 3);
    }

    public static void run() {
        int[] nums = new int[]{1, 4, 1, 3, 5, 6, 9, 0};
        new QuickPick().quickPick(nums,3);
    }

    public int quickPick(int[] nums, int k){
        int target = nums.length - k;

        int left = 0, right = nums.length - 1;
        while (true) {
            int res = quick(nums, left, right);
            if(res > target) {
                right = res - 1;
            } else if(res < target) {
                left = res + 1;
            } else {
                break;
            }
        }

        return nums[target];
    }

    public int quick(int[] nums, int left, int right) {
        int pivot = nums[left];

        int il = left + 1;
        int ir = right;

        while (true) {
            while (il <= ir && nums[il] < pivot) {
                il++;
            }

            while (il <= ir && nums[ir] > pivot) {
                ir--;
            }

            if(il < ir) {
                swap(nums, il, ir);
                il++;
                ir--;
            } else {
                break;
            }
        }

        swap(nums, left, ir);
        return ir;
    }

    public void swap(int[] nums, int i, int j){
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
