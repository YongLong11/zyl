package com.zyl.arithmetrc.leetcode;

import com.zyl.utils.ArrayUtil;

import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;

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


    public static void quickSort1(int[] arr, int low, int high) {
        if (arr == null || arr.length <= 0) {
            return;
        }
        if (low >= high) {
            return;
        }

        int left = low;
        int right = high;
        int temp = arr[left]; //挖坑1：保存基准的值

        while (left < right) {
            while (left < right && arr[right] >= temp) {
                right--;
            }
            arr[left] = arr[right]; //坑2：从后向前找到比基准小的元素，插入到基准位置坑1中
            while (left < right && arr[left] <= temp) {
                left ++;
            }
            arr[right] = arr[left]; //坑3：从前往后找到比基准大的元素，放到刚才挖的坑2中
        }
        arr[left] = temp; //基准值填补到坑3中，准备分治递归快排
        System.out.println("Sorting: " + Arrays.toString(arr));
        quickSort1(arr, low, left-1);
        quickSort1(arr, left + 1, high);

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

    public static void insertSort(int[] arr, int len){
        for (int i = 1; i < len; i++) {
            if(arr[i] < arr[i - 1]){

                int temp = arr[i];
                int p;
                for (p = i; p - 1 >= 0 && arr[p - 1] > temp ; p--){
                    arr[p] = arr[p - 1];
                }
                arr[p] = temp;
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


    /**
     * 选择排序
     *
     * 1. 从待排序序列中，找到关键字最小的元素；
     * 2. 如果最小元素不是待排序序列的第一个元素，将其和第一个元素互换；
     * 3. 从余下的 N - 1 个元素中，找出关键字最小的元素，重复①、②步，直到排序结束。
     *    仅增量因子为1 时，整个序列作为一个表来处理，表长度即为整个序列的长度。
     * @param arr  待排序数组
     */
    public static void selectionSort(int[] arr){
        for(int i = 0; i < arr.length-1; i++){
            int min = i;
            for(int j = i+1; j < arr.length; j++){    //选出之后待排序中值最小的位置
                if(arr[j] < arr[min]){
                    min = j;
                }
            }
            if(min != i){
                int temp = arr[min];      //交换操作
                arr[min] = arr[i];
                arr[i] = temp;
                System.out.println("Sorting:  " + Arrays.toString(arr));
            }
        }
    }

    /**
     * 堆排序
     *
     * 1. 先将初始序列K[1..n]建成一个大顶堆, 那么此时第一个元素K1最大, 此堆为初始的无序区.
     * 2. 再将关键字最大的记录K1 (即堆顶, 第一个元素)和无序区的最后一个记录 Kn 交换, 由此得到新的无序区K[1..n−1]和有序区K[n], 且满足K[1..n−1].keys⩽K[n].key
     * 3. 交换K1 和 Kn 后, 堆顶可能违反堆性质, 因此需将K[1..n−1]调整为堆. 然后重复步骤②, 直到无序区只有一个元素时停止.
     * @param arr  待排序数组
     */
    public static void heapSort(int[] arr){
        for(int i = arr.length; i > 0; i--){
            maxHeapify(arr, i);

            int temp = arr[0];      //堆顶元素(第一个元素)与Kn交换
            arr[0] = arr[i-1];
            arr[i-1] = temp;
        }

    }

    private static void maxHeapify(int[] arr, int limit){
        if(arr.length <= 0 || arr.length < limit) return;
        int parentIdx = limit / 2;

        for(; parentIdx >= 0; parentIdx--){
            if(parentIdx * 2 >= limit){
                continue;
            }
            int left = parentIdx * 2;       //左子节点位置
            int right = (left + 1) >= limit ? left : (left + 1);    //右子节点位置，如果没有右节点，默认为左节点位置

            int maxChildId = arr[left] >= arr[right] ? left : right;
            if(arr[maxChildId] > arr[parentIdx]){   //交换父节点与左右子节点中的最大值
                int temp = arr[parentIdx];
                arr[parentIdx] = arr[maxChildId];
                arr[maxChildId] = temp;
            }
        }
        System.out.println("Max_Heapify: " + Arrays.toString(arr));
    }

    public static void main(String[] args) {
        // 示例用法
        int[] nums = { 3,1, 4, 7, 5, 6, 9, 2};
        shellSort(nums);
        Arrays.stream(nums).boxed().forEach(System.out::println);
    }


}
