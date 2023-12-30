package com.zyl.arithmetrc.nowCoder;

import com.zyl.arithmetrc.leetcode.pojo.ListNode;
import com.zyl.utils.ArrayUtil;

import java.util.*;

public class DecTwentyEight {

    // 将给出的链表中的节点每 k 个一组翻转，返回翻转后的链表
    //如果链表中的节点数不是 k 的倍数，将最后剩下的节点保持原样
    //你不能更改节点中的值，只能更改节点本身。
    public static ListNode reverseKGroup (ListNode head, int k) {
        // write code here

        List<Integer> list = new ArrayList<>();
        while (head != null){
            list.add(head.val());
            head = head.next();
        }
        Integer[] array = list.toArray(new Integer[1]);
        Integer[] integers = reverseKGroup(array, k);
        if(integers.length <= 0 ){
            return null;
        }
        ListNode current = new ListNode(integers[0]);
        ListNode result = current;
        for (int i = 1; i < integers.length; i++) {
            ListNode next = new ListNode(integers[i]);
            current.next(next);
            current = next;
        }
        return result;
    }

    public static Integer[] reverseKGroup (Integer[] arr, int k) {
        // write code here
        int currentIndex = 0;
        while (currentIndex < arr.length){
            if(arr.length - (currentIndex) < k){
                break;
            }
            int startIndex = currentIndex;
            int endIndex = startIndex + k -  1;
            if(endIndex >= arr.length){
                break;
            }
            while (startIndex <= endIndex){
                ArrayUtil.swap(arr, startIndex, endIndex);
                startIndex++;
                endIndex--;
            }
            currentIndex = currentIndex + k;
        }
        return arr;
    }

    public static ListNode EntryNodeOfLoop(ListNode pHead) {
        ListNode temp = pHead;
        while(temp.next()!=null){
            //将每个结点的值乘-1，循环遍历，next指-1或next指null就终止
            if(temp.val() < 0){
                temp.val(temp.val() * -1);
                return temp;
            }
            temp.val(temp.val() * -1);
            temp = temp.next();
        }
        return null;
    }
    // https://www.nowcoder.com/practice/886370fe658f41b498d40fb34ae76ff9?tpId=295&tqId=23449&ru=%2Fexam%2Foj&qru=%2Fta%2Fformat-top101%2Fquestion-ranking&sourceUrl=%2Fexam%2Foj
    // 输入一个长度为 n 的链表，设链表中的元素的值为 ai ，返回该链表中倒数第k个节点。
    //如果该链表长度小于k，请返回一个长度为 0 的链表。
    public ListNode FindKthToTail (ListNode pHead, int k) {
        // write code here
        ListNode current = pHead;
        int dept = 0;
        Stack<ListNode> stack = new Stack<>();
        while (current != null){
            dept++;
            stack.push(current);
            current = current.next();
        }
        if(dept < k){
            return null;
        }
        ListNode result = null;
        for(; k > 0 ; k--){
            result = stack.pop();
        }
        return result;
    }

    // 给定一个链表，删除链表的倒数第 n 个节点并返回链表的头指针
    public static ListNode removeNthFromEnd (ListNode head, int n) {
        // write code here
        ListNode current = head;
        int depth = 0;
        List<ListNode> stack = new ArrayList<>();
        while (current != null){
            depth++;
            stack.add(current);
            current = current.next();
        }
        if(depth < n){
            return head;
        }else if(depth == n){
            return head == null ? null : head.next();
        }
        int removeIndex = depth - n;
        ListNode currentResult = head;
        for (int i = 0; i < stack.size(); i++) {
            if(i == removeIndex){
                continue;
            }else if(i + 1 == removeIndex){
                currentResult.next(currentResult.next().next());
            }else {
                currentResult = currentResult.next();
            }
        }
        return head;
    }

    // 输入两个无环的单向链表，找出它们的第一个公共结点，如果没有公共节点则返回空。
    // https://www.nowcoder.com/practice/6ab1d9a29e88450685099d45c9e31e46?tpId=295&tags=&title=&difficulty=0&judgeStatus=0&rp=0&sourceUrl=%2Fexam%2Foj
    public static ListNode FindFirstCommonNode(ListNode pHead1, ListNode pHead2) {
        if (pHead1 == null || pHead2 == null){
            return null;
        }
        ListNode temp1 = pHead1;
        ListNode temp2 = pHead2;
        while (temp1 != temp2){
            temp1 = temp1 == null ? pHead2 :temp1.next();
            temp2 = temp2 == null ? pHead1 : temp2.next();
        }
        return temp1;
    }

    public static ListNode reverseNode(ListNode listNode){
        if(listNode == null){
            return null;
        }
        ListNode curren = listNode;
        ListNode pre = null;
        while (curren != null){
            ListNode next = curren.next();
            curren.next(pre);
            pre = curren;
            curren = next;
        }
        return pre;
    }

    public static int[] addArray(int[] nums1, int[] nums2){
        int length1 = nums1.length;
        int length2 = nums2.length;
        int maxLen = Math.max(length1, length2);
        int[] result = new int[maxLen + 1];
        int temp = 0;
        while (length1 - 1 >= 0 || length2 - 1 >= 0 ){
            int val = temp;
            if(length1 - 1 >= 0 ){
                val = val + nums1[length1 - 1];
            }
            if(length2 - 1 >= 0){
                val += nums2[length2 - 1];
            }
            result[maxLen] = val % 10;
            temp = val / 10;
            length2--;
            length1--;
            maxLen--;
        }
        if(result[1] == 0){
            result[0] = 1;
            return result;
        }else {
            int[] newResult = new int[result.length - 1];
            System.arraycopy(result, 1, newResult, 0, result.length - 1);
            return newResult;
        }
    }

    // 给定一个单链表，请设定一个函数，将链表的奇数位节点和偶数位节点分别放在一起，重排后输出。
    //注意是节点的编号而非节点的数值。
    public static ListNode oddEvenList (ListNode head) {
        // write code here
        if(head == null || head.next() == null){
            return head;
        }
        ListNode oddNode = head;
        ListNode evenNode = head.next();
        ListNode evenNodeHead = evenNode;
        // 奇数的个数大于等于偶数的个数
        // 以偶数判断。偶数位不为空，偶数位下一个为空证明还有一个奇数位，需要再次执行
        while (evenNode != null && evenNode.next() != null){
            oddNode.next(evenNode.next());
            oddNode = oddNode.next();
            evenNode.next(oddNode.next());
            evenNode = evenNode.next();

        }
        oddNode.next(evenNodeHead);
        return head;
    }

    public static void main(String[] args) {
        ListNode listNode1 = new ListNode(1);
        ListNode listNode2 = new ListNode(2);
        ListNode listNode3 = new ListNode(3);
        ListNode listNode4 = new ListNode(4);
        ListNode listNode5 = new ListNode(5);
        ListNode listNode6 = new ListNode(6);
        ListNode listNode7 = new ListNode(7);
        listNode1.next(listNode2);
        listNode2.next(listNode3);
        listNode3.next(listNode4);
//        listNode6.next(listNode7);
        listNode4.next(listNode5);
        listNode5.next(listNode6);
        listNode6.next(listNode7);

        int[] arr1 = new int[]{1, 2, 3};
        int[] arr2 = new int[]{9, 2, 3};
        System.out.println(oddEvenList(listNode1));
    }
}
