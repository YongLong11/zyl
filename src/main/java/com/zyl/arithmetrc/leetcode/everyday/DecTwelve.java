package com.zyl.arithmetrc.leetcode.everyday;

import java.util.*;

public class DecTwelve {

    // 给你一个只包含 '(' 和 ')' 的字符串，找出最长有效（格式正确且连续）括号子串的长度。
    // https://leetcode.cn/problems/longest-valid-parentheses/description/
    public static int longestValidParentheses(String s) {
        Stack<String> stack = new Stack<>();
        int capa = 0;
        for (String string : s.split("")) {
            if(string.equals("(")){
                stack.push(string);
            }
            if(!stack.empty()){
                if(string.equals(")")){
                    if(stack.pop().equals("(")){
                        capa += 2;
                    }else {
                        stack.push("(");
                    }
                }
            }
        }
        return capa;
    }
    // https://leetcode.cn/problems/find-first-and-last-position-of-element-in-sorted-array/description/
    //给你一个按照非递减顺序排列的整数数组 nums，和一个目标值 target。请你找出给定目标值在数组中的开始位置和结束位置。
    //如果数组中不存在目标值 target，返回 [-1, -1]。
    //你必须设计并实现时间复杂度为 O(log n) 的算法解决此问题。
    public static int[] searchRange(int[] nums, int target) {
        int length = nums.length;
        int[] arr = new int[]{-1, -1};
        if(length == 0){
            return arr;
        }
        if(length == 1 && nums[0] == target){
            arr[0] = 0;
            arr[1] = 0;
            return arr;
        }
        int left = 0 , right = length - 1;
        boolean isStopRight = false;
        boolean isStopLeft = false;

        while (left <= right){
            if(nums[left] == target){
                arr[0] = left;
                isStopLeft = true;
            }
            if(nums[right] == target){
                arr[1] = right;
                isStopRight = true;
            }
            if(!isStopLeft){
                left++;
            }
            if(!isStopRight){
                right--;
            }
            if(isStopRight && isStopLeft){
                break;
            }
        }
        return arr;
    }

    public static List<List<Integer>> combinationSum(int[] candidates, int target) {
        int len = candidates.length;
        List<List<Integer>> res = new ArrayList<>();
        if (len == 0) {
            return res;
        }

        // 必须有序，才能处理
        Arrays.sort(candidates);
        Deque<Integer> path = new ArrayDeque<>();
        dfs(candidates, 0, len, target, path, res);
        return res;
    }

    private static void dfs(int[] candidates, int begin, int len, int target, Deque<Integer> path, List<List<Integer>> res) {
        // 由于进入更深层的时候，小于 0 的部分被剪枝，因此递归终止条件值只判断等于 0 的情况
        if (target == 0) {
            res.add(new ArrayList<>(path));
            return;
        }

        for (int i = begin; i < len; i++) {
            // 重点理解这里剪枝，前提是候选数组已经有序，
            if (target - candidates[i] < 0) {
                break;
            }

            path.addLast(candidates[i]);
            dfs(candidates, i, len, target - candidates[i], path, res);
            path.removeLast();
        }
    }


    public static void main(String[] args) {
        System.out.println(combinationSum(new int[]{2, 3, 6, 7}, 7));
        System.out.println();
    }
}
