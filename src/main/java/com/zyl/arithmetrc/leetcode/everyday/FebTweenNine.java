package com.zyl.arithmetrc.leetcode.everyday;

import java.util.*;
import java.util.stream.Collectors;

public class FebTweenNine {

    public static void main(String[] args) {
//        Random random = new Random();
//        System.out.println(random.nextInt(10));
//        System.out.println(random.nextDouble());
        int[] arr = new int[]{1,1,1,2,2,3};
        System.out.println(Arrays.toString(topKFrequent(arr, 3)));
    }

    public int maxSubArray(int[] nums) {
        if(nums == null || nums.length == 0){
            return 0;
        }
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        int max = dp[0];
        for (int i = 1; i < nums.length; i++) {
            if(dp[i - 1 ] > 0 ){
                dp[i] = dp[i - 1] + nums[i];
                max = Math.max(max, dp[i]);
            }else {
                dp[i] = nums[i];
                max = Math.max(max, dp[i]);
            }
        }
        return max;
    }
    public static Integer[] topKFrequent(int[] nums, int k) {
        return Arrays.stream(nums).boxed().sorted((a, b) -> b - a)
                .limit(k)
                .toArray(Integer[]::new);
    }

}
