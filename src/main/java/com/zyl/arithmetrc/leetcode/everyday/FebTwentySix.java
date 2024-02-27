package com.zyl.arithmetrc.leetcode.everyday;

import java.util.*;

public class FebTwentySix {

    public static int countSubstrings(String s) {
        if(s == null ||s.isEmpty()){
            return  0;
        }
        int winLen = 2;
        int count = 0 ;
        for (int start = 0; start < s.length(); start++) {
//            int start = 0;
            for (int p = 1; p <= s.length(); p++) {
                int end = start + p - 1;
                if(end >= s.length()){
                    break;
                }
                if(isPalindrome(s, start, end)){
                    count++;
                }
            }

        }
        return count;
    }
    private static boolean isPalindrome(String str, int start, int end){
        if(str.isEmpty() || start < 0 | end >= str.length() || start > end){
            return false;
        }
        int left = start;
        int right = end;
        while (left <= right){
            if(left == right){
                left++;
                right--;
                continue;
            }
            if(!Objects.equals(str.charAt(left), str.charAt(right))){
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

//    private static int palindromeCount(String string, int index){
//        if(string == null || string.isEmpty() || index <= 0 || index >= string.length() - 1){
//            return  0;
//        }
//        int result = 0 ;
//        int length = string.length();
//        while (true){
//            int left = index - 1;
//            int right
//            if(index - 1 >= 0 && index + 1 <= length - 1){
//                if(Objects.equals(string.charAt()))
//            }
//        }
//    }

    public static int[] dailyTemperatures(int[] temperatures) {
        int[] result = new int[temperatures.length];
        for (int i = 0; i < temperatures.length; i++) {
            int ans = 0;
            for (int p = i + 1; p < temperatures.length; p++) {
                if(temperatures[p] > temperatures[i]){
                    ans = p - i;
                    break;
                }
            }
            result[i] = ans;
        }
        return result;
    }

    public static int findTargetSumWays(int[] nums, int target) {
        goBack(nums, target, 0, 0);
        return count;
    }
    static int count = 0;
    private static void goBack(int[] nums, int target, int index, int sum){
        if(index == nums.length){
            if(sum == target){
                count++;
            }
        }else {
            goBack(nums, target, index + 1, sum + nums[index]);
            goBack(nums, target, index + 1, sum - nums[index]);
        }
//        if (index == nums.length) {
//            if (sum == target) {
//                count++;
//            }
//        } else {
//            goBack(nums, target, index + 1, sum + nums[index]);
//            goBack(nums, target, index + 1, sum - nums[index]);
//        }

    }

    public int[][] reconstructQueue(int[][] people) {
        //按数组第一个元素进行降序，按第二个元素进行升序
        Arrays.sort(people, new Comparator<int[]>() {
            @Override
            public int compare(int[] person1, int[] person2){
                if (person1[0] != person2[0]){
                    //第一个元素不相等时，第一个元素降序
                    return person2[0] - person1[0];
                }else{
                    //第一个元素相等时，第二个元素升序
                    return person1[1] - person2[1];
                }
            }
        });
        //新建一个list,用于保存结果集
        List<int[]> list = new LinkedList<>();
        for (int i = 0; i < people.length; i++) {
            if (list.size() > people[i][1]){
                //结果集中元素个数大于第i个人前面应有的人数时，将第i个人插入到结果集的 people[i][1]位置
                list.add(people[i][1],people[i]);
            }else{
                //结果集中元素个数小于等于第i个人前面应有的人数时，将第i个人追加到结果集的后面
                list.add(list.size(),people[i]);
            }
        }
        //将list转化为数组，然后返回
        return list.toArray(new int[list.size()][]);
    }

    public static void main(String[] args) {
        System.out.println(findTargetSumWays(new int[]{1,1,1,1,1}, 3));
    }
}
