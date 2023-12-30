package com.zyl.arithmetrc.leetcode.interview;

import org.apache.poi.ss.formula.functions.Count;

import java.util.*;
import java.util.stream.Collectors;

public class OD {

    public static void main(String[] args) {
       int[] arr = new int[]{1, -1 ,6, 7 ,-17, 7, -1};
        System.out.println(maxSumWithKSteps(arr, 2));
    }

    // 数组根据步长不超过指定数子，到达最后一个坐标，取和的最大值
    public static int maxSumWithKSteps(int[] array, int k) {
        int n = array.length;
        int[] dp = new int[n];  // 用于存储最大和的动态规划数组

        for (int i = 0; i < n; i++) {
            int maxSum = Integer.MIN_VALUE;

            for (int j = 1; j <= k && i - j >= 0; j++) {
                // 从前面的位置向后推移步长，找到最大的和
                maxSum = Math.max(maxSum, dp[i - j]);
            }

            // 更新当前位置的最大和
            dp[i] = array[i] + (maxSum > 0 ? maxSum : 0);
        }

        int result = Integer.MIN_VALUE;
        // 在dp数组中找到最大值作为结果
        for (int i = 0; i < n; i++) {
            result = Math.max(result, dp[i]);
        }

        return result;
    }
    public static void arr(long[] arr, int step){
        int size  = arr.length;
        long count = 0;
        for (int i = 0; i < size; ) {
            int end = i + step;
            if(end >= size){
                long bigZeroIndex = findBigZeroIndex(arr, i, size - 1);
                count += bigZeroIndex;
                break;
            }
            int index = findMaxIndex(arr, i, i + step - 1);
            count = count + arr[index];
            i = index;
        }
        if(arr[size -  1] < 0 ){
            count = count + arr[size - 1];
        }
        System.out.println(count);
    }

    private long get(List<Long> list, int index, int step){
        return list.stream().skip(index).limit(step).sorted(Comparator.reverseOrder()).collect(Collectors.toList()).get(0);
    }

    public static long findBigZeroIndex(long[] array, int startIndex, int endIndex) {
        long res =0;
        for (int i = startIndex; i <= endIndex ; i++) {
            if(array[i] > 0){

                res += array[i];
            }
        }
        return res;
    }


        public static int findMaxIndex(long[] array, int startIndex, int endIndex) {
        if (array == null || startIndex < 0 || endIndex > array.length || startIndex >= endIndex) {
            // 输入参数无效
            return -1;
        }

        int maxIndex = startIndex + 1; // 初始化为起始位置的下一个位置

        for (int i = startIndex + 2; i <= endIndex; i++) {
            if (array[i] > array[maxIndex]) {
                // 如果找到更大的值，更新maxIndex
                maxIndex = i;
            }
        }

        return maxIndex;
    }

    public static void main1(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            int size = scanner.nextInt();
            Long[] arr = new Long[size];
            for (int i = 0; i < size; i++) {
                arr[i] = scanner.nextLong();
            }
            int step = scanner.nextInt();

            long count = 0;
            for (int i = 0; i < size; ) {
                for (int p = i; p < i + step - 1; p++) {
                    long currMax = arr[i];
                    if (arr[p] > currMax) {
                        i = p;
                    }
                }
                count = count + arr[i];

            }

        }
    }

    public static void str(String string) {
        // 负数
        StringBuilder second = new StringBuilder("");
        String[] split = string.split("");
        boolean flag = false;
        int first = 0;
        for (String s : split) {
            try {
                if (s.equals("-")) {
                    flag = true;
                    continue;
                }
                Integer num = Integer.parseInt(s);
                if (flag) {
                    second.append(num);
                    continue;
                }
                first += num;
            } catch (Exception e) {
                flag = false;
            }
        }
        if (!second.toString().equals("")) {
            int behind = Integer.parseInt(second.toString());
            first = first - behind;
        }
        System.out.println(first);
    }

//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//
//        while (scanner.hasNext()) {
//            String string = scanner.next();
//            StringBuilder second = new StringBuilder("");
//            String[] split = string.split("");
//            boolean flag = false;
//            long first = 0;
//            for (String s : split) {
//                try {
//                    if (s.equals("-")) {
//                        flag = true;
//                        continue;
//                    }
//                    Integer num = Integer.parseInt(s);
//                    if (flag) {
//
//                        second.append(s);
//                        continue;
//                    }
//                    first += num;
//                } catch (NumberFormatException e) {
//                    flag = false;
//                }
//            }
//            if(!second.toString().equals("")){
//                long behind  = Long.parseLong(second.toString());
//                first = first - behind;
//            }
//            System.out.println(first);
//        }
//    }
//
//
//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//
//        while (scanner.hasNextInt()){
//            int firstLen = scanner.nextInt();
//            int[] firstArr = new int[firstLen + 1];
//            for (int i = 1; i <= firstLen; i++) {
//                firstArr[i] = scanner.nextInt();
//            }
//            int secondLen = scanner.nextInt();
//            int[] secondArr = new int[secondLen + 1];
//            for (int i = 1; i <= secondLen; i++) {
//                secondArr[i] = scanner.nextInt();
//            }
//
//            // 目标组合数
//            int num = scanner.nextInt();
//            LinkedHashSet<List<Integer>> set = new LinkedHashSet<>();
//            List<Integer> integers = new ArrayList<>();
//
//            // 二维数组赋值，不满足条件的设置为 -1
//            int[][] dp = new int[firstLen + 1][ secondLen + 1];
//            for (int i = 1; i <= firstLen; i++) {
//                for (int p = 1; p <= secondLen; p++) {
//                    // 一组数据
//                    dp[i][p] = firstArr[i] + secondArr[p];
//                    List<Integer> list = new ArrayList<>();
//                        list.add(i);
//                        list.add(p);
//
//                    if(set.contains(list)){
//                        dp[i][p] = -1;
//                        continue;
//                    }else {
//                        integers.add(dp[i][p]);
//                        set.add(list);
//                    }
//                }
//            }
//
//            long count = integers.stream().sorted().limit(num).mapToInt(Integer::intValue).sum();
//            System.out.println(count);
//
//        }
//
//
//    }
}
