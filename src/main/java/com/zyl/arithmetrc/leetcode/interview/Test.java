package com.zyl.arithmetrc.leetcode.interview;


import java.util.*;
import java.util.stream.Collectors;

public class Test {

    // 获取所有随机顺序的组合
    public static List<List<Integer>> generatePermutations(int[] arr, int k) {
        Set<List<Integer>> result = new HashSet<>();
        List<Integer> currentPermutation = new ArrayList<>();

        generatePermutationsHelper(arr, k, 0, currentPermutation, result);

        return new ArrayList<>(result);
    }

    private static void generatePermutationsHelper(int[] arr, int k, int start,
                                                   List<Integer> currentPermutation,
                                                   Set<List<Integer>> result) {
        if (currentPermutation.size() == k) {
            // 当当前排列长度达到 k，加入结果列表
            result.add(new ArrayList<>(currentPermutation));
            return;
        }

        for (int i = start; i < arr.length; i++) {
            // 交换元素，固定当前位置元素，递归生成后面的排列
            swap(arr, start, i);
            currentPermutation.add(arr[start]);

            generatePermutationsHelper(arr, k, start + 1, currentPermutation, result);

            // 恢复交换，以便进行下一次排列生成
            currentPermutation.remove(currentPermutation.size() - 1);
            swap(arr, start, i);
        }
    }

    private static void swap(int[] arr, int i, int j) {
        if (i == j) {
            return;
        }
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }


    // 指定长度的随机组合
    public static List<List<Integer>> generateCombinations(List<Integer> sourceList, int length) {
        List<List<Integer>> result = new ArrayList<>();

        int n = sourceList.size();

        // 长度小于等于0或大于源列表长度时返回空列表
        if (length <= 0 || length > n) {
            return result;
        }

        // 初始化索引数组，表示当前组合的索引
        int[] indices = new int[length];
        for (int i = 0; i < length; i++) {
            indices[i] = i;
        }

        // 开始迭代生成组合
        while (indices[0] <= n - length) {
            // 生成当前组合
            List<Integer> currentCombination = new ArrayList<>();
            for (int index : indices) {
                currentCombination.add(sourceList.get(index));
            }
            result.add(new ArrayList<>(currentCombination));

            // 寻找下一个组合的索引
            int i = length - 1;
            while (i >= 0 && indices[i] == n - length + i) {
                i--;
            }

            // 如果没有找到下一个组合，结束迭代
            if (i < 0) {
                break;
            }

            // 递增索引数组
            indices[i]++;
            for (int j = i + 1; j < length; j++) {
                indices[j] = indices[j - 1] + 1;
            }
        }

        return result;
    }

    public static  List<List<Integer>> generatorRadonLists(int[] arr, int k) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> current = new ArrayList<>();
        generatorRadonLists(result, current, k, 0 , arr);
        return result;
    }

    public static void generatorRadonLists(List<List<Integer>> result, List<Integer> current,
                                                   int k, int start, int[] arr) {
        if(current.size() == k){
            result.add(new ArrayList<>(current));
            return;
        }
        for (int i = start ; i < arr.length; i++) {
            int currentNum = arr[i];
            current.add(currentNum);
            generatorRadonLists(result, current, k, i + 1, arr);
            current.remove(current.size() - 1);
        }

    }

    public static void main(String[] args) {
        int[] arr = new int[3];
        for (int i = 0; i < 3; i++) {
            arr[i] = i;
        }

        List<String> strings = new ArrayList<>();
        int combinationLength = 3; // 指定长度
        List<List<Integer>> lists1 = generatorRadonLists(arr, 2);
        List<List<Integer>> lists = generatePermutations(arr, 2);


        System.out.println("组合集合：" + lists);
        System.out.println("组合集合：" + lists1);
    }


}
