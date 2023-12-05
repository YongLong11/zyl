package com.zyl.leetcode;

import java.util.Arrays;

public class Sort {

    // 冒泡排序
    public static void bubbleSort(int[] arr) {
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

    public static int findMax(int[] nums) {
        int low = 0;
        int high = nums.length - 1;

        while (low < high) {
            int mid = low + (high - low) / 2;

            if (nums[mid] < nums[mid + 1]) {
                // 最大值在 mid 右侧，舍弃左半部分
                low = mid + 1;
            } else {
                // 最大值在 mid 左侧或者就是 mid，舍弃右半部分
                high = mid;
            }
        }

        // 当 low 和 high 相等时，即找到最大值
        return nums[low];
    }

    // 快速排序
    public static void quickSort(int[] arr, int start, int end) {

        if(start < end) {
            // 把数组中的首位数字作为基准数
            int stard = arr[start];
            // 记录需要排序的下标
            int low = start;
            int high = end;
            // 循环找到比基准数大的数和比基准数小的数
            while(low < high) {
                // 右边的数字比基准数大
                while(low < high && arr[high] >= stard) {
                    high--;
                }
                // 使用右边的数替换左边的数
                arr[low] = arr[high];
                // 左边的数字比基准数小
                while(low < high && arr[low] <= stard) {
                    low++;
                }
                // 使用左边的数替换右边的数
                arr[high] = arr[low];
            }
            // 把标准值赋给下标重合的位置
            arr[low] = stard;
            // 处理所有小的数字
            quickSort(arr, start, low);
            // 处理所有大的数字
            quickSort(arr, low + 1, end);
        }
    }

    // 插入排序
    public static void insertSort(int[] arr) {
        // 遍历所有数字
        for(int i = 1; i < arr.length - 1; i++) {
            // 当前数字比前一个数字小
            if(arr[i] < arr[i - 1]) {
                int j;
                // 把当前遍历的数字保存起来
                int temp = arr[i];
                for(j = i - 1; j >= 0 && arr[j] > temp; j--) {
                    // 前一个数字赋给后一个数字
                    arr[j + 1] = arr[j];
                }
                // 把临时变量赋给不满足条件的后一个元素
                arr[j + 1] = temp;
            }
        }
    }

    // 希尔排序
    public static void shellSort(int[] arr) {
        // gap 为步长，每次减为原来的一半。
        for (int gap = arr.length / 2; gap > 0; gap /= 2) {
            // 对每一组都执行直接插入排序
            for (int i = 0 ;i < gap; i++) {
                // 对本组数据执行直接插入排序
                for (int p = i + gap; p < arr.length; p += gap) {
                    // 如果 a[j] < a[j-gap]，则寻找 a[j] 位置，并将后面数据的位置都后移
                    if (arr[p] < arr[p - gap]) {
                        int k;
                        int temp = arr[p];
                        for (k = p - gap; k >= 0 && arr[k] > temp; k -= gap) {
                            arr[k + gap] = arr[k];
                        }
                        arr[k + gap] = temp;
                    }
                }
            }
        }
    }


    public static void main(String[] args) {
        // 示例用法
        int[] nums = {1, 3, 4, 7, 5, 6, 9, 2};
        insertSort(nums);
        Arrays.stream(nums).boxed().forEach(System.out::println);
    }


}
