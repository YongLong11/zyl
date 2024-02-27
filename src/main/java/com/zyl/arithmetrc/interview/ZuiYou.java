package com.zyl.arithmetrc.interview;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import sun.security.provider.Sun;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class ZuiYou {

    // 数组，无序，有正有负，连续元素和最大，返回和


    int[] arr = new int[]{0, 1, -1, 0 ,0};
    // 滑动窗口，
    public static Integer max(int[] num){
        if(num == null ){
            return null;
        }else if (num.length <= 1 ){
            return num.length == 1 ? num[0] : null;
        }
        int length = num.length;

        int step;

        List<Integer> sumList = new ArrayList<>();

        for (step = 1; step <= length; step++) {
            for (int i = 0; i < length; i++) {
                int startIndex = i;
                int endIndex = i + step;
                int sum = 0;
                if(endIndex < length){
                    while (startIndex <= endIndex){
                        if(startIndex == endIndex){
                            sum += num[startIndex];
                        }else {
                            sum = sum + num[startIndex] + num[endIndex];
                        }
                        startIndex++;
                        endIndex--;
                    }
                }
                sumList.add(sum);
            }
        }
        return sumList.stream().sorted(( n1 , n2) -> n2 - n1).findFirst().get();
    }

    public static void main(String[] args) {

        int[] arr = new int[]{1, 2, 3, 4};

        System.out.println(max(arr));

    }





}
