package com.ziroom.zyl.leetcode;

import com.google.common.collect.Lists;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Arithmetic {
    public static void main(String[] args) {
        String str = "civilwartestingwhetherthatnaptionoranynartionsoconceivedandsodedicatedcanlongendureWeareqmetonagreatbattlefiemldoftzhatwarWehavecometodedicpateaportionofthatfieldasafinalrestingplaceforthosewhoheregavetheirlivesthatthatnationmightliveItisaltogetherfangandproperthatweshoulddothisButinalargersensewecannotdedicatewecannotconsecratewecannothallowthisgroundThebravelmenlivinganddeadwhostruggledherehaveconsecrateditfaraboveourpoorponwertoaddordetractTgheworldadswfilllittlenotlenorlongrememberwhatwesayherebutitcanneverforgetwhattheydidhereItisforusthelivingrathertobededicatedheretotheulnfinishedworkwhichtheywhofoughtherehavethusfarsonoblyadvancedItisratherforustobeherededicatedtothegreattdafskremainingbeforeusthatfromthesehonoreddeadwetakeincreaseddevotiontothatcauseforwhichtheygavethelastpfullmeasureofdevotionthatweherehighlyresolvethatthesedeadshallnothavediedinvainthatthisnationunsderGodshallhaveanewbirthoffreedomandthatgovernmentofthepeoplebythepeopleforthepeopleshallnotperishfromtheearth";
        System.out.println(longestPalindrome(str));
    }

    //给你一个整数数组 nums ，请你找出一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
    //子数组 是数组中的一个连续部分。
    //https://leetcode.cn/problems/maximum-subarray/
    public int maxSubArray(int[] nums) {
        int len = nums.length;
        int sum = 0;
        for (int i = 0; i < len; i++)
            for (int q = i + 1; q <= len; q++) {

            }
        return -1;
    }


    //给你一个字符串 s，找到 s 中最长的回文子串。
    //如果字符串的反序与原始字符串相同，则该字符串称为回文字符串。
    // https://leetcode.cn/problems/longest-palindromic-substring/
    public static String longestPalindrome(String s) {
        String ans = "";
        int max = 0;
        int len = s.length();
        for (int i = 0; i < len; i++)
            for (int q = i + 1; q <= len; q++) {
                String test = s.substring(i, q);
                if (isPalindrome(test) && test.length() > max) {
                    ans = s.substring(i, q);
                    max = Math.max(max, ans.length());
                }
            }
        return ans;

    }
    private static boolean isPalindrome(String s){
        if(s.length() == 0){
            return true;
        }
        String[] split = s.split("");
        int start = 0, end = split.length - 1;
        for (int i = 0; start <= end ; i++) {
            if(!split[start].equalsIgnoreCase(split[end])){
                return false;
            }
            start++;
            end--;
        }
        return true;
    }



    //给定一个整数数组 nums，将数组中的元素向右轮转 k 个位置，其中 k 是非负数。
    public static Integer[] rotate(int[] nums, int k) {
        int length = nums.length;
        LinkedList<Integer> ret = new LinkedList<Integer>();

        for (int i = 0; i < length; i++) {
            ret.add(1);
        }
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < length; i++) {
            // 右移有两种情况，1、未超过数组长度，2、超过数组长度
            if( (i + k) < length){
                ret.set(i + k, nums[i]);
//                map.put(i + k, nums[i]);
            }else {
                ret.set(i + k - length, nums[i]);
//                map.put(i + k - length, nums[i]);
            }
        }
//        for (int i = 0; i < length; i++) {
//            ret.add(map.get(i));
//        }
        return ret.toArray(new Integer[0]);
    }

    //给定一个长度为 n 的整数数组 height 。有 n 条垂线，第 i 条线的两个端点是 (i, 0) 和 (i, height[i]) 。
    //找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。
    //返回容器可以储存的最大水量。
    //https://leetcode.cn/problems/container-with-most-water/
    public static int maxArea(int[] height) {
        List<Integer> ret = new ArrayList<>();
        List<Integer> collect = Arrays.stream(height).boxed().collect(Collectors.toList());
        int start = 0, end = height.length - 1;
        for (int i = 0; start < end; i++) {
            List<Integer> area1 = getArea(start, end, collect);
            ret.addAll(area1);
            start = start + 1;
            List<Integer> area2 = getArea(start, end, collect);
            ret.addAll(area2);
            end = end - 1;
        }
        if(ret.isEmpty()){
            return 0;
        }
        return ret.stream().max(Integer::compareTo).get();
    }
    private static List<Integer> getArea(int start , int end, List<Integer> collect){
        int left = collect.get(start);
        int right = collect.get(end);
        int min = Math.min(left, right);
        int area = (BigDecimal.valueOf(start).subtract(BigDecimal.valueOf(end)))
                .multiply(BigDecimal.valueOf(min)).abs().intValue();
        return Lists.newArrayList(area);
    }

    //给定一个字符串 s ，请你找出其中不含有重复字符的 最长子串 的长度。
    public static int lengthOfLongestSubstring(String s) {
        String[] split = s.split("");
        if(s.equals("")){
            return 0;
        }
        if(split.length == 1){
            return 1;
        }
        List<Integer> resultLength = new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            Set<String> strings = new HashSet<>();
            for (int q = i; q < split.length; q++){
                int size = strings.size();
                strings.add(split[q]);
                if (size == strings.size() || strings.size() == split.length){
                    resultLength.add(strings.size());
                    break;
                }
            }
        }
        if(resultLength.isEmpty()){
            return -1;
        }
        resultLength.sort(Comparator.reverseOrder());
        return resultLength.get(0);
    }


    // 思路：将下标和对应的值存进map， 目标值 - 当前值，判断map是否存在，存在则返回对应的下标，不存在存进map
    //给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 target  的那 两个 整数，并返回它们的数组下标。
    //你可以假设每种输入只会对应一个答案。但是，数组中同一个元素在答案里不能重复出现
    public static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>(nums.length);
        for (int i = 0; i < nums.length; i++) {
            // 当前值
            int num = nums[i];
            int other = target - num;
            if(map.containsKey(other)){
                return new int[]{map.get(other), i};
            }else {
                map.put(num, i);
            }
        }
        return null;
    }


    //给定一个大小为 n 的数组 nums ，返回其中的多数元素。多数元素是指在数组中出现次数 大于 ⌊ n/2 ⌋ 的元素。
    //你可以假设数组是非空的，并且给定的数组总是存在多数元素。
    public Integer majorityElement(int[] nums) {
        int length = nums.length;
        long i = BigDecimal.valueOf(length).divide(BigDecimal.valueOf(2), RoundingMode.HALF_UP).longValue();
        Map<Integer, Long> collect = Arrays.stream(nums).boxed().collect(Collectors.groupingBy(a -> a, Collectors.counting()));
        for (Map.Entry<Integer, Long> entry : collect.entrySet()) {
            if(entry.getValue() > i){
                return entry.getKey();
            }
        }
        return null;
    }

    //给你一个 升序排列 的数组 nums ，请你 原地 删除重复出现的元素，使每个元素 只出现一次 ，
    // 返回删除后数组的新长度。元素的 相对顺序 应该保持 一致 。然后返回 nums 中唯一元素的个数。
    //考虑 nums 的唯一元素的数量为 k ，你需要做以下事情确保你的题解可以被通过：
    //更改数组 nums ，使 nums 的前 k 个元素包含唯一元素，并按照它们最初在 nums 中出现的顺序排列。nums 的其余元素与 nums 的大小不重要。
    //返回 k 。
    public int removeDuplicates(int[] nums) {
        return (int) Arrays.stream(nums).boxed().distinct().count();
    }


    //给你一个数组 nums 和一个值 val，你需要 原地 移除所有数值等于 val 的元素，并返回移除后数组的新长度。
    //不要使用额外的数组空间，你必须仅使用 O(1) 额外空间并 原地 修改输入数组。
    public int removeElement(int[] nums, int val) {
        int j = nums.length - 1;
        for (int i = 0; i <= j; i++) {
            if (nums[i] == val) {
                swap(nums, i--, j--);
            }
        }
        return j + 1;
    }
    void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }


    // 合并两个有序数据
    public void merge(int[] nums1, int m, int[] nums2, int n) {
         nums1 = Stream.concat(Arrays.stream(nums1).boxed()
                         , Arrays.stream(nums2).boxed())
                .sorted(Comparator.reverseOrder())
                .filter(num -> Objects.equals(0, num))
                 .mapToInt(a -> a)
                 .toArray();
    }
}
