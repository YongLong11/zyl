package com.zyl.arithmetrc.interview;

import java.math.BigInteger;
import java.util.*;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Didi {

    // 统计字符串出现的次数，并根据次数从大到小排序

    public void sort(String[] strings){
        Map<String, Integer> map = new HashMap<>(strings.length)
                ;

        for (String string : strings) {
            if(map.containsKey(string)){
                map.put(String.valueOf(map.get(string)), map.get(string)+1);
            }
            else {
                map.put(string, 0);
            }
        }

        String[] array = map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey).sorted(Comparator.reverseOrder())
                .toArray(String[]::new);

    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while(sc.hasNext()){
            int n = sc.nextInt();
            int[] sMs = new int[n];
            int[] sXs = new int[n];
            HashSet<Integer> set = new HashSet<>();
            //关键点就是set必须手动添加一个元素，从而进入循环，添加元素。
            set.add(0);
            // 砝码种类
            for (int i = 0; i < n; i++) {
                sMs[i] = sc.nextInt();
            }
            // 砝码个数
            for (int i = 0; i < n; i++) {
                sXs[i] = sc.nextInt();
            }
            for (int i = 0; i < n; i++) {
                ArrayList<Integer> list = new ArrayList<>(set);
                //每个砝码的个数，关键步骤，只要有砝码，至少为1个
                for (int j = 1; j <= sXs[i]; j++) {
                    for (int k = 0; k < list.size(); k++) {
                        set.add(list.get(k) + sMs[i] * j);
                    }
                }
            }
            System.out.println(set.size());
        }

    }
    private static List<List<Integer>> generateCombinations(List<Integer> list, int k) {
        List<List<Integer>> combinations = new ArrayList<>();
        generateCombinations(list, new ArrayList<>(), 0, k, combinations);
        return combinations;
    }

    private static void generateCombinations(List<Integer> list, List<Integer> combination, int index, int k, List<List<Integer>> result) {
        // 终止条件：当前组合的大小等于指定的元素个数
        if (combination.size() == k) {
            result.add(new ArrayList<>(combination));
            return;
        }
        for (int i = index; i < list.size(); i++) {
            int num = list.get(i);
            // 将选择的元素添加到当前组合中
            combination.add(num);
            // 递归调用函数，选择下一个元素
            generateCombinations(list, combination, i + 1, k, result);
            // 将选择的元素从当前组合中移除
            combination.remove(combination.size() - 1);
        }
    }


}
