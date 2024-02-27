package com.zyl.arithmetrc.leetcode.everyday;

import com.zyl.arithmetrc.leetcode.pojo.ListNode;
import com.zyl.arithmetrc.leetcode.pojo.Node;
import org.w3c.dom.ls.LSException;

import java.awt.geom.RectangularShape;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JanTwentyNine {
    //链表反转
    public ListNode reverser(ListNode head) {
        ListNode pre = null;
        ListNode current = head;
        while (current != null) {
            ListNode next = current.next();
            current.next(pre);
            pre = current;
            current = next;
        }
        return pre;
    }

    public static int longestConsecutive(int[] nums) {
        Set<Integer> num_set = new HashSet<Integer>();
        for (int num : nums) {
            num_set.add(num);
        }

        int longestStreak = 0;

        for (int num : num_set) {
            if (!num_set.contains(num - 1)) {
                int currentNum = num;
                int currentStreak = 1;

                while (num_set.contains(currentNum + 1)) {
                    currentNum += 1;
                    currentStreak += 1;
                }

                longestStreak = Math.max(longestStreak, currentStreak);
            }
        }

        return longestStreak;
    }

    public static List<Integer> findAnagrams(String s, String p) {
        if(s == null || p == null){
            return null;
        }
        List<String> strings = generatePermutations(p);
        Set<Integer> result = new HashSet<>();
        int index ;
        for (String string : strings) {
            int start = 0 ;
            while (true){
                index = s.indexOf(string, start);
                if(index >= 0){
                    start = index + 1;
                    result.add(index);
                }else {
                    break;
                }
            }
        }
        return new ArrayList<>(result);
    }

    private static List<String> generatePermutations(String input) {
        List<String> result = new ArrayList<>();
        permute("", input, result);
        return result;
    }

    private static void permute(String prefix, String remaining, List<String> result) {
        int n = remaining.length();
        if (n == 0) {
            result.add(prefix);
        } else {
            for (int i = 0; i < n; i++) {
                permute(prefix + remaining.charAt(i),
                        remaining.substring(0, i) + remaining.substring(i + 1, n),
                        result);
            }
        }
    }

    public static List<String> generator(String str) {
        List<String>  result = new ArrayList<>();
        generator(result, new StringBuilder(), str.split(""), 0);
        return result;
    }

    private static void generator(List<String> result, StringBuilder current,
                           String[] spilt, int index){
        if (current.length() == spilt.length){
            result.add(new String(current));
            return;
        }
        for (int i = index; i < spilt.length; i++) {
            swap(spilt, index, i);
            current.append(spilt[i]);
            generator(result, current, spilt, index + 1);
            current.deleteCharAt(current.length() - 1);
            swap(spilt, index, i);
        }
    }
    private static void swap(String[] nums, int left, int right){
        if(left == right){
            return;
        }
        String tmp = nums[left];
        nums[left] = nums[right];
        nums[right] = tmp;
    }

    public static int[] maxSlidingWindow(int[] nums, int k) {
        if(nums == null){
            return null;
        }
        int resultIndex = 0;
        int[] result = new int[nums.length - 2];
        int leftIndex = 0;
        int rightIndex = leftIndex + k - 1;
        while (rightIndex  < nums.length){
            int currentMax = getMax(nums, leftIndex, rightIndex);
            result[resultIndex] = currentMax;
            leftIndex++;
            rightIndex = leftIndex + k - 1;
        }
        return result;
    }
    private static Integer getMax(int[] nums, int left, int right){
//        if(left < 0 || right >= nums.length || right < left){
//            return null;
//        }
        int start = left;
        int end = right;
        int sum = 0 ;
        while (start <= right){
            if(start == right){
                sum = sum + nums[start];
            }else {
                sum = sum + nums[start] + nums[end];
            }
            start++;
            end--;
        }
        return sum;
    }

    public static void main(String[] args) {
        int[] arr = new int[]{1, 2, 4, 5, 6, 7, 10, 11, 15, 19};
        System.out.println(findAnagrams("cbaebabacd", "abc"));
    }

}
