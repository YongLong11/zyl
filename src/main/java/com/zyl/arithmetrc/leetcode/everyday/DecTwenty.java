package com.zyl.arithmetrc.leetcode.everyday;

import com.zyl.arithmetrc.leetcode.pojo.ListNode;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

public class DecTwenty {
    @Data
    @AllArgsConstructor
    public static class Trile<K, V, P>{
        private K left;
        private V mid;
        private P right;

        public Trile<K, V, P> of(K left, V mid, P right){
            return new Trile<>(left, mid, right);
        }
    }

    // 给定一个由 整数 组成的 非空 数组所表示的非负整数，在该数的基础上加一。
    //最高位数字存放在数组的首位， 数组中每个元素只存储单个数字。
    //你可以假设除了整数 0 之外，这个整数不会以零开头。
    // https://leetcode.cn/problems/plus-one/description/
    public int[] plusOne(int[] digits) {
        for (int i = digits.length - 1; i >= 0; i--) {
            if (digits[i] == 9) {
                digits[i] = 0;
            } else {
                digits[i] += 1;
                return digits;
            }

        }
        digits = new int[digits.length + 1];
        digits[0] = 1;
        return digits;
    }

    // 给你一个正整数 n ，生成一个包含 1 到 n2 所有元素，且元素按顺时针顺序螺旋排列的 n x n 正方形矩阵 matrix 。
    // https://leetcode.cn/problems/spiral-matrix-ii/description/
    public int[][] generateMatrix(int n) {
        int l = 0, r = n - 1, t = 0, b = n - 1;
        int[][] mat = new int[n][n];
        int num = 1, tar = n * n;
        while (num <= tar) {
            for (int i = l; i <= r; i++) mat[t][i] = num++; // left to right.
            t++;
            for (int i = t; i <= b; i++) mat[i][r] = num++; // top to bottom.
            r--;
            for (int i = r; i >= l; i--) mat[b][i] = num++; // right to left.
            b--;
            for (int i = b; i >= t; i--) mat[i][l] = num++; // bottom to top.
            l++;
        }
        return mat;
    }

    // 求算数平方根
    // https://leetcode.cn/problems/sqrtx/
    public int mySqrt(int x) {
        int left = 0, right = x, answer = -1;
        while (left <= right){
            int mid = left + (right - left) / 2;
            if((long) mid * mid <= x){
                answer = mid;
                left = mid + 1;
            }else {
                right = mid - 1;
            }
        }
        return answer;
    }

    // 全排列
    // https://leetcode.cn/problems/permutations-ii/
    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>>  result = new ArrayList<>();
        generator(result, new ArrayList<>(), nums, 0, nums.length);
        return result;
    }

    private void generator(List<List<Integer>> result, List<Integer> current,
                           int[] nums, int index, int length){
        if (current.size() == length){
            result.add(new ArrayList<>(current));
            return;
        }
        for (int i = index; i < nums.length; i++) {
            swap(nums, index, i);
            current.add(nums[index]);
            generator(result, current, nums, index + 1, length);
            current.remove(current.size() - 1);
            swap(nums, index, i);
        }
    }

    private static void swap(int[] nums, int left, int right){
        if(left == right){
            return;
        }
        int tmp = nums[left];
        nums[left] = nums[right];
        nums[right] = tmp;
    }

    // 合并两个有序数组
    // 从后往前排，
    public static void merge(int[] nums1, int m, int[] nums2, int n) {
        int i = nums1.length - 1;
        while (n > 0) {
            if (m > 0 && nums1[m - 1] > nums2[n - 1]) {
                nums1[i--] = nums1[m - 1];
                m--;
            } else {
                nums1[i--] = nums2[n - 1];
                n--;
            }
        }
    }

    // 给你两个单链表的头节点 headA 和 headB ，请你找出并返回两个单链表相交的起始节点。如果两个链表不存在相交节点，返回 null 。
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if(headA == null || headB == null){
            return null;
        }
        ListNode pA = headA, pB = headB;
        while (pA != pB) {
            pA = pA == null ? headB : pA.next();
            pB = pB == null ? headA : pB.next();
        }
        return pA;




    }

    public static void main(String[] args) {
        int[]  ar = new int[]{ 0};
        int[] arr = new int[]{1};
        merge(ar, 0, arr, 1);
        for (int i : ar) {
            System.out.println(i);
        }
    }
}
