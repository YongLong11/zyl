package com.zyl.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ArrayUtil {
    public static <T extends Integer> void swap(T[] nums, int i, int j) {
        if(i == j || nums==null || nums.length == 0 || i >= nums.length || j >= nums.length){
            return;
        }
        T tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

    public static void swap(int[] nums, int i, int j) {
        if(i == j || nums==null || nums.length == 0 || i >= nums.length || j >= nums.length){
            return;
        }
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
}
