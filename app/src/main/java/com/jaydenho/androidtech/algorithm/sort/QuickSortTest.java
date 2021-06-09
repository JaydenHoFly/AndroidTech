package com.jaydenho.androidtech.algorithm.sort;

import android.util.Log;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by hedazhao on 2021/5/25.
 */
public class QuickSortTest {

    public static void main(String[] args) {
        int[] nums = new int[]{1, 4, 1, 3, 5, 6, 9, 0};
        new QuickSortTest().quickSort(nums);
        run();
    }

    public static void run() {
        int[] nums = new int[]{1, 4, 1, 3, 5, 6, 9, 0};
        new QuickSortTest().quickSort(nums);
        System.out.println("QuickSortTest" + Arrays.toString(nums));
    }

    public void quickSort(int[] nums) {
        quick(nums, 0, nums.length - 1);
    }

    public void quick(int[] nums, int first, int last) {
        if (first >= last) {
            return;
        }

        int of = first, ol = last;

        int target = new Random().nextInt(last - first) + first;
        int pivot = nums[target];
        swap(nums, target, last);

        last--;

        while (true) {
            while (first <= last && nums[first] < pivot) {
                first++;
            }

            while (last >= first && nums[last] > pivot) {
                last--;
            }

            if (first < last) {
                swap(nums, first, last);

                first++;
                last--;

            } else {
                break;
            }
        }


        swap(nums, first, ol);

        quick(nums, of, first - 1);
        quick(nums, first + 1, ol);
    }

    public void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
