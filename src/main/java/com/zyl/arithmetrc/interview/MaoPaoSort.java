package com.zyl.arithmetrc.interview;

import java.util.Arrays;

public class MaoPaoSort {


    public static void sort(int[] arr){
        for (int i = 0; i < arr.length; i++) {
            for (int p = i + 1; p < arr.length; p++) {
                if(arr[i] >= arr[p]){
                    swap(arr, i , p);
                }
            }
        }
        System.out.println(Arrays.toString(arr));
    }
    private static void swap(int[] arr, int i , int p){
        if(i == p || arr == null){
            return;
        }
        int temp = arr[i];
        arr[i] = arr[p];
        arr[p] = temp;
    }

    public static void main(String[] args) {
        sort(new int[]{2, 1, 4, 3, 5, 0});
    }
}
