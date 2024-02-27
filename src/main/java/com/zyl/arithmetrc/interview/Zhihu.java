package com.zyl.arithmetrc.interview;

import java.util.Arrays;

public class Zhihu {



    // 数组，复写0，数组长度不变，超出部分丢弃
    public static void repeatZero(int[] arr){
        if(arr == null || arr.length == 0){
            return;
        }
        int length = arr.length;
        for (int i = 0; i < length - 1; ) {
            if(arr[i] != 0){
                i++;
                continue;
            }else {
                i++;
                move(arr, i);
                arr[i] = 0;
                i++;
            }
        }
        System.out.println(Arrays.toString(arr));
    }
    private static void move(int[] arr, int startIndex){
        int length = arr.length;
        for (int i = length - 2; i >= startIndex; i--) {
            arr[i + 1] = arr[i];
        }
    }

    public static void main(String[] args) {
        int[] arr = new int[]{1, 0, 2, 3, 0 ,4 ,5, 0};
//        {0, 1, 0};
//        {1,1, 0, ,0, 0,9}
//        ;
//        {0, 0, 1, 0, 0, 1, 0 , 0 , 0 }
//        {};
//        null;
//        {1};
//        {0};
        repeatZero(arr);
    }
}
