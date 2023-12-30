package com.zyl.arithmetrc.nowCoder;

import com.zyl.arithmetrc.leetcode.pojo.ListNode;
import com.zyl.arithmetrc.leetcode.pojo.TreeNode;
import org.apache.poi.ss.formula.functions.Count;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DecTwentyNine {


    // 删除给出链表中的重复元素（链表中元素从小到大有序），使链表中的所有元素都只出现一次
    public static ListNode deleteDuplicates (ListNode head) {
        // write code here
        ListNode listNode = head;
        while (listNode != null){
            ListNode next = listNode.next();
            if(next != null && listNode.val() == next.val()){
                ListNode nexted = next.next();
                listNode.next(nexted);
                continue;
            }
            listNode = listNode.next();
        }
        return head;
    }

    // 给出一个升序排序的链表，删除链表中的所有重复出现的元素，只保留原链表中只出现一次的元素。
    public static ListNode deleteDuplicatesII (ListNode head) {
        // write code here

        if(head == null){
            return  head;
        }
        List<Integer> nodeList = new ArrayList<>();
        while (head!=null){
            nodeList.add(head.val());
            head = head.next();
        }
        List<Integer> collect = nodeList.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue() == 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        if(collect.isEmpty()){
            return null;
        }
        ListNode result = new ListNode(Integer.MAX_VALUE);
        ListNode current = result;
        for (Integer node : nodeList) {
            if(collect.contains(node)){
                current.next(new ListNode(node));
                current = current.next();
            }
        }
        return result.next();
    }

    public static ListNode deleteDuplicatesII (int[] head) {
        // write code here


        List<Integer> nodeList = Arrays.stream(head).boxed().collect(Collectors.toList());

        List<Integer> collect = nodeList.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue() == 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        if(collect.isEmpty()){
            return null;
        }
        ListNode result = new ListNode(Integer.MAX_VALUE);
        ListNode current = result;
        for (Integer node : nodeList) {
            if(collect.contains(node)){
                current.next(new ListNode(node));
                current = current.next();
            }
        }
        return result.next();
    }

    // 二分查找
    public static int search (int[] nums, int target) {
        // write code here
        int left = 0 ;
        int end = nums.length - 1;
        while (left <= end){
            int mid = (left + end) / 2;
            if(target < nums[mid]){
                end = mid - 1;
            }else if(target > nums[mid]){
                left = mid + 1;
            }
            else if(target == nums[mid]){
                return mid;
            }
        }
        return -1;
    }

    /*
    1   2   3
    4   5   6
    7   8   9
     */
    // 在一个二维数组array中（每个一维数组的长度相同），每一行都按照从左到右递增的顺序排序，每一列都按照从上到下递增的顺序排序。请完成一个函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。
    // https://www.nowcoder.com/practice/abc3fe2ce8e146608e868a70efebf62e?tpId=295&tags=&title=&difficulty=0&judgeStatus=0&rp=0&sourceUrl=%2Fexam%2Foj
    public boolean Find (int target, int[][] array) {
        // write code here
        if(array.length == 0)
            return false;
        int n = array.length;
        if(array[0].length == 0)
            return false;
        int m = array[0].length;
        //从最左下角的元素开始往左或往上
        for(int i = n - 1, j = 0; i >= 0 && j < m; ){
            //元素较大，往上走
            if(array[i][j] > target)
                i--;
                //元素较小，往右走
            else if(array[i][j] < target)
                j++;
            else
                return true;
        }
        return false;

    }


    //
    public static int findPeakElement (int[] nums) {
        // write code here


        if(nums == null ){
            return  -1;
        }

        int length = nums.length;

        long[] arr = new long[length + 1];
        System.arraycopy(nums, 0 , arr, 1, length);


        if(nums.length == 1){
            return 0;
        }
        if(nums.length == 2){
            return nums[0] >= nums[1] ? 0 : 1;
        }
        for (int i = 0; i < length - 1; i++) {
            if(i == 0){
                if(nums[i] > nums[ i + 1 ]){
                    return 0;
                }else {
                    continue;
                }
            }
            if (nums[i - 1] < nums[i] && nums[i] > nums[i + 1]) {
                return i;
            }
            if(i == length - 2 && nums[length - 1] > nums[length - 2]){
                return length - 1;
            }
        }
        return -1;

    }


    public static int InversePairs (int[] nums) {
        // write code here

        if(nums == null || nums.length <= 1){
            return 0;
        }
        int count = 0;
        int length = nums.length;
        for (int i = 0; i < length; i++) {
            for (int p = i + 1; p < length; p++) {
                if(nums[i] > nums[p]){
                    count++;
                }
            }
        }
        return count;

    }

    public static Integer minNumberInRotateArray (int[] nums) {
        // write code here
        if(nums == null || nums.length == 0){
            return null;
        }

        int length = nums.length;
        int left = 0, right = length - 1;
        int min = Integer.MAX_VALUE;
        while (left <= right){
            min = Math.min(nums[left], min);
            min = Math.min(min, nums[right]);
            left++;
            right--;
        }
        return min;
    }


    public Integer[] preorderTraversal (TreeNode root) {
        // write code here
        List<Integer> list = new ArrayList<>();
        dfs(root, list);
        return list.toArray(new Integer[1]);
    }

    private static void dfs(TreeNode node, List<Integer> list){
        if(node == null){
            return;
        }
        list.add(node.var());
        dfs(node.left(), list);
        dfs(node.right(), list);
    }

    public static void main(String[] args) {
        ListNode listNode1 = new ListNode(1);
        ListNode listNode2 = new ListNode(2);
        ListNode listNode3 = new ListNode(1);
        ListNode listNode4 = new ListNode(4);
        ListNode listNode5 = new ListNode(4);
        ListNode listNode6 = new ListNode(5);
        ListNode listNode7 = new ListNode(7);

        listNode1.next(listNode2);
//        listNode2.next(listNode3);
        listNode3.next(listNode4);
        listNode4.next(listNode5);
        listNode5.next(listNode6);
        listNode6.next(listNode7);
         int[] arr = new int[]{1,2,3};
        System.out.println(minNumberInRotateArray(arr));
    }
}
