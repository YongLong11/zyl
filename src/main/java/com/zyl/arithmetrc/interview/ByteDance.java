package com.zyl.arithmetrc.interview;

import com.zyl.arithmetrc.leetcode.pojo.ListNode;

import java.util.Arrays;

public class ByteDance {

    public static void sort(int[] arr1, int m, int[] arr2, int n) {
        int index = arr1.length - 1;
        int index1 = m - 1;
        int index2 = arr2.length - 1;
        while (index1 >= 0 || index2 >= 0) {
            if (index1 >= 0 && index2 >= 0) {
                if (arr1[index1] > arr2[index2]) {
                    arr1[index] = arr1[index1];
                    index1--;
                } else {
                    arr1[index] = arr2[index2];
                    index2--;
                }
            } else if
            (index1 >= 0 && index2 < 0) {
                arr1[index] = arr1[index1];
                index1--;
            } else if (index1 < 0 && index2 >= 0) {
                arr1[index] = arr2[index2];
                index2--;
            }
            index--;
        }
        System.out.println(Arrays.toString(arr1));
    }

    public static ListNode reverser(ListNode listNode){
        if(listNode == null){
            return null;
        }
        ListNode pre = null;
        ListNode current = listNode;
        while (current != null){
            ListNode next = current.next();
            current.next(pre);
            pre = current;
            current = next;
        }
        return pre;
    }

    public static void main(String[] args) {
        int[] arr1 = new int[]{1, 3, 5, 7, 0 , 0 , 0, 0};
        int[] arr2 = new int[]{2,6, 9, 11};
//        sort(arr1, 4, arr2, 4);
        ListNode listNode1 = new ListNode(1);
        ListNode listNode2 = new ListNode(2);
        ListNode listNode3 = new ListNode(3);
        listNode1.next(listNode2);
        listNode2.next(listNode3);
        System.out.println(reverser(listNode1));
    }
}
