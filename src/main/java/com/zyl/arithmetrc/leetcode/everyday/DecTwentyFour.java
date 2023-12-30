package com.zyl.arithmetrc.leetcode.everyday;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DecTwentyFour {

    public static List<Integer> combinationSum(int[] nums, int target) {
        Arrays.sort(nums);
        int right = nums.length - 1;
        List<Integer> result = new ArrayList<>();
        generator(new ArrayList<>(), target, Integer.MAX_VALUE, result, nums, right);
        System.out.println(result);
        return result;
    }

    private static void generator(List<Integer> current, int target, int min,
                           List<Integer> best, int[] nums, int start){
        if(start < 0){
            return;
        }
        current.add(nums[start]);
        int sum = current.stream().mapToInt(Integer::intValue).sum();
        if(sum == target){
            if(current.size() < min){
                best.clear();
                best.addAll(current);
            }
        }

         else if(sum - target < 0){
            generator(current, target, min, best, nums, start);
        }
        else if(sum - target > 0 && start - 1 >= 0){
            current.remove(current.size() - 1);
            generator(current, target, min, best, nums, start - 1);
        }
    }

    public static List<Integer> combinationSum1(int[] nums, int target) {
        int[] dp = new int[target + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;

        for (int num : nums) {
            for (int i = num; i <= target; i++) {
                if (dp[i - num] != Integer.MAX_VALUE) {
                    dp[i] = Math.min(dp[i], dp[i - num] + 1);
                }
            }
        }

        if (dp[target] == Integer.MAX_VALUE) {
            return null;
        } else {
            List<Integer> result = new ArrayList<>();
            int currentSum = target;
            while (currentSum > 0) {
                for (int num : nums) {
                    if (currentSum - num >= 0 && dp[currentSum] == dp[currentSum - num] + 1) {
                        result.add(num);
                        currentSum -= num;
                        break;
                    }
                }
            }
            return result;
        }
    }
    public static void main(String[] args) {
        int[] nums = {2, 3, 5, 7};
        int target = 14;
        List<Integer> result = combinationSum(nums, target);

    }

}
