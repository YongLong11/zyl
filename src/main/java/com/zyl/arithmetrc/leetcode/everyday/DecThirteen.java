package com.zyl.arithmetrc.leetcode.everyday;

import javafx.util.Pair;
import net.bytebuddy.build.AccessControllerPlugin;

import java.security.Key;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class DecThirteen {


    // https://leetcode.cn/problems/trapping-rain-water/
    // 接雨水
    public static int trap(int[] height) {
        if (height.length <= 2) {
            return 0;
        }

        // 最大数，就是行数
        int row = 0;
        for (int i : height) {
            row = Math.max(i, row);
        }
        int sum = 0;
        for (int i = 1; i <= row; i++) {
            for (int k = 0; k < height.length; k++) {
                // 只有左右两边有大于当前位置数字d的时候，才能接住雨水
                if (hasLeftRight(height, k, i)) {
                    sum++;
                }
            }
        }
        return sum;

    }

    // 判断指定行数的指定位置，左右两边是否有大于该位置的数，并且该位置在这行是没值的
    private static boolean hasLeftRight(int[] arr, int index, int row) {
        boolean left = false;
        boolean right = false;
        for (int i = 0; i < arr.length; i++) {
            if (i < index && arr[i] - row >= 0 && arr[index] - row < 0) {
                left = true;
            }
            if (i > index && arr[i] - row >= 0 && arr[index] - row < 0) {
                right = true;
            }
            if (left && right) {
                return true;
            }
        }
        return false;
    }

    // 给定一个包含红色、白色和蓝色、共 n 个元素的数组 nums ，原地对它们进行排序，使得相同颜色的元素相邻，并按照红色、白色、蓝色顺序排列。
    //我们使用整数 0、 1 和 2 分别表示红色、白色和蓝色。
    //必须在不使用库内置的 sort 函数的情况下解决这个问题。
    public static void sortColors(int[] arr) {
        boolean isSwap = false;
        for (int i = 0; i < arr.length; i++) {
            for (int p = 0; p < arr.length - 1 - i; p++) {
                if (arr[p] > arr[p + 1]) {
                    int temp = arr[p];
                    arr[p] = arr[p + 1];
                    arr[p + 1] = temp;
                    isSwap = true;
                }
            }
            if (!isSwap) {
                break;
            }
        }

    }

    // 给定一个未排序的整数数组 nums ，找出数字连续的最长序列（不要求序列元素在原数组中连续）的长度。
    // 请你设计并实现时间复杂度为 O(n) 的算法解决此问题。
    // https://leetcode.cn/problems/longest-consecutive-sequence/
    public static int longestConsecutive(int[] nums) {
        if (nums.length < 2) {
            return nums.length;
        }
        // 需要排序和去重
        int[] array = Arrays.stream(nums).sorted().distinct().toArray();
        int max = 0;
        int length = 0;
        for (int i = 1; i < array.length; i++) {
            if (array[i] - array[i - 1] == 1) {
                length++;
            }
            max = Math.max(length, max);
            // 如果不是连续的
            if (array[i] - array[i - 1] != 1) {
                length = 0;
            }

        }
        return max + 1;
    }


    // 给你一个长度为 n 的整数数组 nums 和 一个目标值 target。请你从 nums 中选出三个整数，使它们的和与 target 最接近。
    //返回这三个数的和。
    //假定每组输入只存在恰好一个解。
    public static int threeSumClosest(int[] nums, int target) {
        List<List<Integer>> generator = generator(nums, 3);
        List<Long> collect = generator.stream().map(list -> {
            long count = list.stream().mapToInt(Integer::intValue).sum();
            return count;
        }).collect(Collectors.toList());
        Map<Long, Long> map = collect.stream().collect(Collectors.toMap(a -> Math.abs(a - target), i -> i, (k, p) -> k));
        Long get = map.keySet().stream().sorted().findFirst().get();
        Long num = map.get(get);
        return (int)(num + 0) ;
    }

    // 求指定个数的所有匹配
    private static List<List<Integer>> generator(int[] nums, int k) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> current = new ArrayList<>();
        generator(nums, k, 0, current, result);
        return result;
    }

    private static void generator(int[] nums, int k, int index,
                                  List<Integer> current,
                                  List<List<Integer>> result) {
        if (current.size() == k) {
            result.add(new ArrayList<>(current));
        }
        for (int i = index; i < nums.length; i++) {
            int num = nums[i];
            current.add(num);
            generator(nums, k, i + 1, current, result);
            current.remove(current.size() - 1);
        }
    }


    public class Lru {
        private int capacity;
        private Queue<Integer> order;

        private Map<Integer, Integer> storageMap;
        public Lru(int capacity) {
            // write code here
            this.capacity = capacity;
            this.order = new ArrayDeque<>(capacity);
            this.storageMap = new ConcurrentHashMap<>();
        }

        public int get(int key) {
            // write code here
            Integer result = storageMap.get(key);

            order.removeIf(i -> i.equals(key));
            order.add(key);

            return result == null ? -1 : result;
        }

        public void set(int key, int value) {
            // write code here
            order.removeIf(i -> i.equals(key));
            order.add(key);
            storageMap.put(key, value);
                if (storageMap.size() > this.capacity){
                    Integer poll = order.poll();
                    if(poll != null){
                        storageMap.remove(poll);
                    }

            }


        }
    }

    @FunctionalInterface
    interface Function<T, U, R, P>{

        P apply(T t, U u, R r);
    }


        public int[] LFU (int[][] operators, int k) {
            // write code here
            List<Integer> result = new ArrayList<>();
            Queue<Integer> order = new ArrayDeque<>();
            Map<Integer, Integer> storageMap = new HashMap<>();
            Map<Integer, Function<Map<Integer, Integer>, Integer, Integer, Integer>> handleMap = new HashMap<>();
            handleMap.put(1, (map, key, value) -> {
                order.removeIf(i-> i.equals(key));
                order.add(key);
                if(!map.containsKey(key)){
                    if (map.size() >= k){
                        Integer poll = order.poll();
                        if(poll != null){
                            map.remove(poll);
                        }
                    }
                }
                map.put(key, value);
                return null;
            });
            handleMap.put(2, (map, key, value) -> {
                order.removeIf(i-> i.equals(key));
                order.add(key);
                return map.get(key);
            });
            for (int i = 0; i < operators.length; i++) {
                Function<Map<Integer, Integer>, Integer, Integer, Integer> mapIntegerIntegerIntegerFunction = handleMap.get(get(operators, i, 0));
                if(mapIntegerIntegerIntegerFunction != null){
                    Integer apply = mapIntegerIntegerIntegerFunction.apply(storageMap, get(operators, i, 1), get(operators, i, 2));
                    result.add(apply);
                }
            }
            int[] resultArr = new int[result.size()];
            for (int i = 0; i < result.size(); i++) {
                resultArr[i] = result.get(i);
            }
            return resultArr;
        }
        private Integer get(int[][] operators, int row, int col){
            try {
               return operators[row][col];
            }catch (IndexOutOfBoundsException e){
                return null;
            }
        }



    public static void main(String[] args) {
//        System.out.println(hasLeftRight(new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1}, 2, 1));
        int[] arr = new int[]{4,0,5,-5,3,3,0,-4,-5};
        System.out.println(threeSumClosest(arr, -2));

    }
}
