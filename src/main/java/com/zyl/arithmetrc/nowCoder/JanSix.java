package com.zyl.arithmetrc.nowCoder;

import com.sun.scenario.effect.LinearConvolveCoreEffect;
import javafx.util.Pair;

import javax.swing.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;

public class JanSix {


    // 最长无重复子数组
    public static int maxLength (int[] arr) {
        // write code here
        if(arr == null || arr.length == 0 ){
            return 0;
        }
        int len = arr.length;
        for (int i = len; i >= 0; i--) {
            int start = 0;
            int end = start + i - 1;
            while (end < len){
                boolean repeat = repeat(arr, start, end);
                if(repeat){
                    return i;
                }else {
                    start++;
                    end++;
                }
            }
        }
        return 0;
    }

    private static boolean repeat(int[] arr, int start, int end){
        if(arr == null || start > end || start < 0 || end >= arr.length){
            return false;
        }
        return Arrays.stream(arr).skip(start).limit(end - start + 1).distinct().count() == end - start + 1;
    }

    public static int maxArea(int[] height) {
        if(height == null || height.length == 0 || height.length == 1){
            return 0;
        }
        int left = 0;
        int right = height.length - 1;
        int maxArea = 0;
        while (left < right){
            if(height[left] < height[right]){
                maxArea = Math.max(maxArea, (right - left) *  height[left++]);
            }else {
                maxArea = Math.max(maxArea,  (right - left) * height[right--] );
                right--;
            }
        }
        return maxArea;
    }


    public static void swap(int[] arr){
        int left = 0 ;
        int right = arr.length - 1;

        while (left < right){
            while (arr[left] % 2 != 0){
                left++;
            }
            while (arr[right] % 2 == 0){
                right--;
            }

            int temp = arr[left];
            arr[left] = arr[right];
            arr[right] = temp;
            left++;
            right--;
        }

        System.out.println(Arrays.toString(arr));

    }

    public static void main(String[] args) {
        int[] arr = new int[]{1,8,6,2,5,4,8,3,7};
//        System.out.println(maxLength(arr));
       swap(arr);
    }
}
