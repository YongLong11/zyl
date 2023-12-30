package com.zyl.arithmetrc.leetcode.everyday;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DecFourteen {

    // 教学过程中，教练示范一次，学员跟做三次。
    // 该过程被混乱剪辑后，记录于数组 actions，其中 actions[i] 表示做出该动作的人员编号。请返回教练的编号。
    // https://leetcode.cn/problems/shu-zu-zhong-shu-zi-chu-xian-de-ci-shu-ii-lcof/description/
    public int trainingPlan(int[] actions) {
        Integer key = Arrays.stream(actions).boxed().collect(Collectors.groupingBy(i -> i, Collectors.counting()))
                .entrySet().stream().filter(entry -> entry.getValue() >= 1).findFirst().get().getKey();
        return key;
    }

    // 给你三个整数数组 nums1、nums2 和 nums3 ，请你构造并返回一个 元素各不相同的 数组，且由 至少 在 两个 数组中出现的所有值组成。
    // 数组中的元素可以按 任意 顺序排列。
    // https://leetcode.cn/problems/two-out-of-three/description/
    public List<Integer> twoOutOfThree(int[] nums1, int[] nums2, int[] nums3) {
        Set<Integer> list1 = Arrays.stream(nums1).boxed().collect(Collectors.toSet());
        Set<Integer> list2 = Arrays.stream(nums2).boxed().collect(Collectors.toSet());
        Set<Integer> list3 = Arrays.stream(nums3).boxed().collect(Collectors.toSet());
        List<Integer> collect = list1.stream().filter(num -> list2.contains(num) || list3.contains(num)).distinct().collect(Collectors.toList());
        List<Integer> collect1 = list2.stream().filter(num -> list1.contains(num) || list3.contains(num)).distinct().collect(Collectors.toList());
        HashSet<Integer> set = new HashSet<>(collect1);
        set.addAll(collect);
        return new ArrayList<>(set);
    }

    //给你一个下标从 0 开始的字符串 s ，以及一个下标从 0 开始的整数数组 spaces 。
    //数组 spaces 描述原字符串中需要添加空格的下标。每个空格都应该插入到给定索引处的字符值 之前 。
    //例如，s = "EnjoyYourCoffee" 且 spaces = [5, 9] ，那么我们需要在 'Y' 和 'C' 之前添加空格，这两个字符分别位于下标 5 和下标 9 。因此，最终得到 "Enjoy Your Coffee" 。
    //请你添加空格，并返回修改后的字符串。
    // https://leetcode.cn/problems/adding-spaces-to-a-string/description/
    public String addSpaces(String s, int[] spaces) {
        StringBuilder stringBuilder = new StringBuilder();
        Set<Integer> collect = Arrays.stream(spaces).boxed().collect(Collectors.toSet());
        for (int i = 0; i < s.length(); i++) {
            if (collect.contains(i)) {
                stringBuilder.append(" ");
            }
            stringBuilder.append(s.charAt(i));
        }
        return stringBuilder.toString();
    }

    // 给你一个字符串 title ，它由单个空格连接一个或多个单词组成，每个单词都只包含英文字母。请你按以下规则将每个单词的首字母 大写 ：
    //如果单词的长度为 1 或者 2 ，所有字母变成小写。
    //否则，将单词首字母大写，剩余字母变成小写。
    //请你返回 大写后 的 title 。
    public String capitalizeTitle(String title) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String string : title.split(" ")) {
            if (string.length() <= 2) {
                stringBuilder.append(string.toLowerCase(Locale.ROOT));
            } else {
                char c = string.charAt(0);
                stringBuilder.append(Character.toUpperCase(c));
                stringBuilder.append(string.substring(1).toLowerCase(Locale.ROOT));
            }
            stringBuilder.append(" ");
        }
        return stringBuilder.substring(0, stringBuilder.length() - 1);
    }

    public int minSwaps(int[] nums) {
        int n = nums.length;
        int ans = n, oneCnt = 0;

        for (int num : nums) {
            if (num == 1) {
                oneCnt++;
            }
        }

        // 开一个宽度为 oneCnt 的窗口， 统计窗口里面 0 的数量 zeroCnt
        // 最少的 zeroCnt 即为此题答案
        for (int i = 0, zeroCnt = 0; i < 2 * n; i++) {
            if (i >= oneCnt) {
                ans = Math.min(ans, zeroCnt);

                // 右移窗口时，被移除的数字是 0 则 zeroCnt--
                if (nums[(i - oneCnt) % n] == 0) {
                    zeroCnt--;
                }
            }

            if (nums[i % n] == 0) zeroCnt++;
        }

        return ans;
    }

    public static int minSwaps1(int[] nums) {
        int[] arr = new int[nums.length * 2];
        System.arraycopy(nums, 0, arr, 0, nums.length);
        System.arraycopy(nums, 0, arr, nums.length, nums.length);

        int oneCount = 0;
        for (int num : nums) {
            if (num == 1) {
                oneCount++;
            }
        }
        int left = 0;
        int right = oneCount - 1;
        int minZeroCount = Integer.MAX_VALUE;
        while (right < arr.length) {
            int zeroCount = getZeroCount(arr, left, right);
            minZeroCount = Math.min(zeroCount, minZeroCount);
            left++;
            right++;
        }
        return minZeroCount;
    }

    private static int getZeroCount(int[] arr, int left, int right) {
        int zeroCount = 0;
        int startIndex = left;
        int endIndex = right;
        while (startIndex <= endIndex && startIndex >= 0 && endIndex < arr.length) {
            if (startIndex == endIndex && arr[startIndex] == 0) {
                zeroCount++;
                break;
            }
            if (arr[startIndex] == 0) {
                zeroCount++;
            }
            if (arr[endIndex] == 0) {
                zeroCount++;
            }
            startIndex++;
            endIndex--;
        }
        return zeroCount;
    }

    // 给你一个长度为 n 的字符串 s ，和一个整数 k 。请你找出字符串 s 中 重复 k 次的 最长子序列 。
    //子序列 是由其他字符串删除某些（或不删除）字符派生而来的一个字符串。
    //如果 seq * k 是 s 的一个子序列，其中 seq * k 表示一个由 seq 串联 k 次构造的字符串，那么就称 seq 是字符串 s 中一个 重复 k 次 的子序列。
    //举个例子，"bba" 是字符串 "bababcba" 中的一个重复 2 次的子序列，因为字符串 "bbabba" 是由 "bba" 串联 2 次构造的，而 "bbabba" 是字符串 "bababcba" 的一个子序列。
    //返回字符串 s 中 重复 k 次的最长子序列  。如果存在多个满足的子序列，则返回 字典序最大 的那个。如果不存在这样的子序列，返回一个 空 字符串。
//    public static String longestSubsequenceRepeatedK(String s, int k) {
//        Optional<String> reduce = Arrays.stream(s.split(""))
//                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
//                .entrySet().stream().filter(entry -> entry.getValue() >= (long) k)
//                .map(Map.Entry::getKey)
//                .reduce((s1, s2) -> s2 + s1);
//        return reduce.orElse("");
//
//    }

    // 给你一个大小为 m x n 的二维整数网格 grid 和一个整数 x 。每一次操作，你可以对 grid 中的任一元素 加 x 或 减 x 。
    // 单值网格 是全部元素都相等的网格。
    // 返回使网格化为单值网格所需的 最小 操作数。如果不能，返回 -1 。
    // https://leetcode.cn/problems/minimum-operations-to-make-a-uni-value-grid/description/
    public static int minOperations(int[][] grid, int x) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < grid.length; i++) {
            for (int p = 0; p < grid[0].length; p++) {
                list.add(grid[i][p]);
            }
        }
        List<Integer> collect = list.stream().map(num -> {
            if (Math.abs(num - x) % 2 == 0) {
                return Math.abs(num - x) / 2;
            } else {
                return -1;
            }
        }).collect(Collectors.toList());
        boolean isPass = collect.stream().anyMatch(num -> num == -1);
        if (isPass) {
            return -1;
        }
        return collect.stream().mapToInt(Integer::intValue).sum();

    }

    // 链接：https://www.nowcoder.com/questionTerminal/f9e10e39f68542f5b1674e6d14e0fe09?orderByHotValue=1&mutiTagIds=782&page=1&onlyReference=false
    //输入一个整形数组，数组里有正数也有负数。数组中连续的一个或多个整数组成一个子数组，每个子数组都有一个和。求所有子数组的和的最大值。要求时间复杂度为O(n)。
    //例如输入的数组为1, -2, 3, 10, -4, 7, 2, -5，那么该数组中连续的最大的子数组为3, 10, -4, 7, 2，因此输出为该子数组的和18。
    // 注意题目描述，有正数有负数，如果允许无非负数，那么需要单独考虑。
    //原理：如果前几个数加和小于零，那么前面的数所做的全是无用功所以清零
    public static int getMax(int[] a) {

        int len = a.length;
        int sum = 0;
        int maxSum = 0;
        for (int i = 0; i < len; i++) {
            sum += a[i];
            if (sum > 0) {
                if (sum > maxSum) {
                    maxSum = sum;
                }
            } else {
                sum = 0;
            }
        }
        return maxSum;
    }

    public static void main(String[] args) {
        int[] arr = new int[]{20, -20, 3, 1, -4, -2, 2, -5};
        int[][] num = {{2, 4}, {6, 8}};

        System.out.println(getMax(arr));
    }

}
