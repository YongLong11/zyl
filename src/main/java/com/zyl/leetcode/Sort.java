package com.zyl.leetcode;

public class Sort {

    public static void bubbleSort(int[] arr){
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
            if(isSwap){
                break;
            }
        }
    }
}
