package com.zyl.arithmetrc.leetcode.everyday;

import com.zyl.arithmetrc.leetcode.pojo.ListNode;

import java.util.*;

public class FebThree {

    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode first = head;
        for (int i = 0; i < n; i++) {
            if (first == null) {
                return head;
            }
            first = first.next();
        }
        ListNode second = head;
        while (first != null) {
            first = first.next();
            second = second.next();
        }
        second.next(second.next().next());
        return head;
    }

    //        public ListNode swapPairs(ListNode head) {
//        if(head == null || head.next() == null){
//            return head;
//        }
//        ListNode next = head.next();
//        head.next(swapPairs(next.next()));
//        next.next(head);
//    }
    public ListNode swapPairs(ListNode head) {
        if (head == null || head.next() == null) {
            return head;
        }
        ListNode next = head.next();
        head.next(swapPairs(next.next()));
        next.next(head);

        ListNode pre = new ListNode(0);
        pre.next(head);
        ListNode temp = pre;
        while (temp.next() != null && temp.next().next() != null) {
            ListNode start = temp.next();
            ListNode end = temp.next().next();
            temp.next(end);
            start.next(end.next());
            end.next(start);
            temp = start;
        }
        return pre.next();
    }

    public static List<String> letterCombinations(String digits) {
        List<String> combinations = new ArrayList<String>();
        if (digits.length() == 0) {
            return combinations;
        }
        Map<Character, String> phoneMap = new HashMap<Character, String>() {{
            put('2', "abc");
            put('3', "def");
            put('4', "ghi");
            put('5', "jkl");
            put('6', "mno");
            put('7', "pqrs");
            put('8', "tuv");
            put('9', "wxyz");
        }};
        backtrack(combinations, phoneMap, digits, 0, new StringBuffer());
        return combinations;
    }

    public static void backtrack(List<String> combinations, Map<Character, String> phoneMap, String digits, int index, StringBuffer combination) {
        if (index == digits.length()) {
            combinations.add(combination.toString());
        } else {
            char digit = digits.charAt(index);
            String letters = phoneMap.get(digit);
            int lettersCount = letters.length();
            for (int i = 0; i < lettersCount; i++) {
                combination.append(letters.charAt(i));
                backtrack(combinations, phoneMap, digits, index + 1, combination);
                combination.deleteCharAt(index);
            }
        }
    }

    public boolean isValidSudoku(char[][] board) {
        Map<Integer, HashSet<Integer>> rows = new HashMap<>();
        Map<Integer, HashSet<Integer>> cols = new HashMap<>();
        Map<Integer, HashSet<Integer>> areas = new HashMap<>();

        for (int i = 0; i < 9; i++) {
            rows.put(i, new HashSet<>());
            cols.put(i, new HashSet<>());
            areas.put(i, new HashSet<>());
        }
        int rowLength = board.length;
        int colLength = board[0].length;
        for (int i = 0; i < rowLength; i++) {
            for (int p = 0; p < colLength; p++) {
                int current = board[i][p] - '0';
                int areaIndex = (i / 3) * 3 + p / 3;
                if (rows.get(i).contains(current) || cols.get(p).contains(current)
                        || areas.get(areaIndex).contains(current)) {
                    return false;
                } else {
                    rows.get(i).add(current);
                    cols.get(p).add(current);
                    areas.get(areaIndex).add(current);
                }
            }
        }
        return true;
    }

    public String countAndSay(int n) {
        String str = "1";
        for (int i = 2; i <= n; i++) {
            StringBuilder stringBuilder = new StringBuilder();
            int start = 0;
            int end = 0;
            while (end < str.length()) {
                while (end < str.length() && str.charAt(start) == str.charAt(end)) {
                    end++;
                }
                stringBuilder.append(end - start).append(str.charAt(start));
                start = end;
            }
            str = stringBuilder.toString();
        }
        return str;
//
//        String str = "1";
//        for (int i = 2; i <= n; ++i) {
//            StringBuilder sb = new StringBuilder();
//            int start = 0;
//            int pos = 0;
//
//            while (pos < str.length()) {
//                while (pos < str.length() && str.charAt(pos) == str.charAt(start)) {
//                    pos++;
//                }
//                sb.append(Integer.toString(pos - start)).append(str.charAt(start));
//                start = pos;
//            }
//            str = sb.toString();
//        }
//
//        return str;
    }

    // 一个字符串转为另一个的最小次数
    public static int minDistance(String word1, String word2) {
        int n1 = word1.length();
        int n2 = word2.length();
        int[][] dp = new int[n1 + 1][n2 + 1];
        // 第一行
        for (int j = 1; j <= n2; j++) dp[0][j] = dp[0][j - 1] + 1;
        // 第一列
        for (int i = 1; i <= n1; i++) dp[i][0] = dp[i - 1][0] + 1;

        for (int i = 1; i <= n1; i++) {
            for (int j = 1; j <= n2; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) dp[i][j] = dp[i - 1][j - 1];
                else dp[i][j] = Math.min(Math.min(dp[i - 1][j - 1], dp[i][j - 1]), dp[i - 1][j]) + 1;
            }
        }
        return dp[n1][n2];
    }

    // 一次买卖股票
    private static int getMax(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int min = Integer.MAX_VALUE;
        int result = 0;
        for (int i = 0; i < arr.length; i++) {
            min = Math.min(arr[i], min);
            if (arr[i] - min > result) {
                result = arr[i] - min;
            }
        }
        return result;
    }

    //https://leetcode.cn/problems/next-permutation/
    //下一个排列
    //
    public static void nextPermutation(int[] nums) {
        int i = nums.length - 2;
        while (i >= 0 && nums[i] >= nums[i + 1]) {
            i--;
        }
        if (i >= 0) {
            int j = nums.length - 1;
            while (j >= 0 && nums[i] >= nums[j]) {
                j--;
            }
            swap(nums, i, j);
        }
        reverse(nums, i + 1);
    }

    public static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    public static void reverse(int[] nums, int start) {
        int left = start, right = nums.length - 1;
        while (left < right) {
            swap(nums, left, right);
            left++;
            right--;
        }
    }

    public static ListNode sort(ListNode node1, ListNode node2){
        ListNode result = new ListNode(-1);
        ListNode current = result;
        if(node1 == null || node2 == null){
            return node1 == null ? node2 : node1;
        }
        while (node1 != null || node2 != null){
            if(node1 != null && node2 != null){
                if(node1.val() <= node2.val()){
                    current.next(node1);
                    node1 = node1.next();
                }else {
                    current.next(node2);
                    node2 = node2.next();
                }
            }else if (node1 != null){
                current.next(node1);
                node1 = node1.next();
            }else if(node2 != null){
                current.next(node2);
                node2 = node2.next();
            }
            current = current.next();
        }
        return result;

    }

    // 和为K的子数组个数
    public static int subarraySum(int[] nums, int k) {
        List<Integer> list = new ArrayList<>();
        return subarraySum(nums, k, 0, list);
    }

    public static int subarraySum(int[] nums, int k, int index, List<Integer> list) {
        int result = 0;

        int currentSum = 0;
        for (int i = index; i < nums.length; i++) {
            currentSum += nums[i];
            list.add(nums[i]);

            if (currentSum == k) {
                result++;
            }

            result += subarraySum(nums, k, i + 1, list);

            list.remove(list.size() - 1);
        }

        return result;
    }

    public static void main(String[] args) {
        System.out.println(getMax(new int[]{7, 1, 4, 5, 3, 6}));
        int[] arr = new int[]{4, 5, 3, 6, 2 ,1};
        int[] arr1 = new int[]{1,1, 1};
        System.out.println(subarraySum(arr1, 2));
    }

}
