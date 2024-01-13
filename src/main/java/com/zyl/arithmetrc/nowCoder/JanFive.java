package com.zyl.arithmetrc.nowCoder;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class JanFive {


    public static String solve(String s, String t) {
        // write code here
        if (s.isEmpty() || t.isEmpty()) {
            return s.isEmpty() ? t : s;
        }
        String longStr = s;
        String shortStr = t;
        if (s.length() < t.length()) {
            longStr = t;
            shortStr = s;
        }
        int longLen = longStr.length();
        int shortLen = shortStr.length();
        StringBuilder stringBuilder = new StringBuilder();
        int longIndex = longLen - 1;
        int shortIndex = shortLen - 1;
        int temp = 0;
        while (longIndex >= 0 || shortIndex >= 0 || temp > 0) {
            int currentNum = temp;
            if (longIndex >= 0) {
                currentNum += longStr.charAt(longIndex) - '0';
            }
            if (shortIndex >= 0) {
                currentNum += shortStr.charAt(shortIndex) - '0';
            }
            stringBuilder.append(currentNum % 10);
            temp = currentNum / 10;
            longIndex--;
            shortIndex--;
        }
        return stringBuilder.reverse().toString();
    }

    public static void merge(int[] arr1, int m, int[] arr2, int n) {

        int index = arr1.length - 1;
        n--;
        m--;
        while (n >= 0) {
            if (m >= 0 && arr1[m] > arr2[n]) {
                arr1[index] = arr1[m];
                index--;
                m--;
            } else {
                arr1[index] = arr2[n];
                index--;
                n--;
            }

        }
        for (int i : arr1) {
            System.out.println(i);
        }
    }

    @Data
    public static class Interval {
        int start;
        int end;

        public  Interval(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    public static ArrayList<Interval> merge(ArrayList<Interval> intervals) {
        // write code here
        if (intervals.isEmpty()){
            return intervals;
        }
        Collections.sort(intervals, Comparator.comparing(val -> val.start));
        ArrayList<Interval> result = new ArrayList<>();
        result.add(intervals.get(0));
        for (int i = 1; i < intervals.size(); i++) {
            Interval currentInterval = intervals.get(i);
            Interval preInterval = result.get(result.size() - 1);
            if(preInterval.end >= currentInterval.start){
                int maxEnd = Math.max(currentInterval.end, preInterval.end);
                preInterval.setEnd(maxEnd);
            }else {
                result.add(currentInterval);
            }
        }
        return result;
    }

    public static String minWindow (String S, String T) {
        // write code here
        if(S == null || T == null || S.isEmpty() || T.isEmpty() || S.length() < T.length()){
            return "";
        }
        for (int step = T.length(); step <= S.length() ; step++) {
            int startIndex = 0 ;
            int endIndex = startIndex + step;

            while (endIndex <=S.length()){
                boolean contain = contain(S.substring(startIndex, endIndex), T);
                if(contain){
                    return S.substring(startIndex, endIndex);
                }else {
                    startIndex++;
                    endIndex = startIndex + step;
                }
            }
        }
        return "";
    }
    private static boolean contain(String source, String target){
        Map<String, Long> targetMap = Arrays.stream(target.split("")).collect(Collectors.groupingBy(i -> i, Collectors.counting()));
        Map<String, Long> sourceMap = Arrays.stream(source.split("")).collect(Collectors.groupingBy(i -> i, Collectors.counting()));
        return targetMap.entrySet().stream().allMatch(entry -> {
            Long sourceValue = sourceMap.get(entry.getKey());
            if(Objects.isNull(sourceValue)){
                return false;
            }else {
               return entry.getValue() <= sourceValue;
            }
        });
    }


    public static void main(String[] args) {
        Interval interval1 = new Interval(10, 30);
        Interval interval2 = new Interval(20, 60);
        Interval interval3 = new Interval(80, 100);
        Interval interval4 = new Interval(150, 180);
        System.out.println(contain("blrprcxvta", "baxtr"));
    }
}
