package com.zyl.arithmetrc.leetcode;


import java.util.HashMap;
import java.util.Map;

// 最不经常使用（LFU）缓存算法
public class LFUCache {

    public static int subarraySum(int[] nums, int k) {
        if (nums == null) {
            return 0;
        }
        int result = 0;
        int winLen = nums.length;
        for (int step = winLen; step > 0; step--) {
            int left = 0;
            int right = left + step;
            while (right < nums.length) {
                if(sum(nums, left, right) == k){
                    result++;
                }
                left++;
                right++;
            }
        }
        return result;
    }

    private static int sum(int[] arr, int startStart, int endIndex) {

        int sum = 0;
        int left = startStart;
        int right = endIndex;
        while (left <= right) {
            if (left == right) {
                sum += arr[left];
            } else {
                sum = sum + arr[left] + arr[right];
            }
            left++;
            right--;
        }
        return sum;
    }

    public static void main(String[] args) {
        LFUCache cache = new LFUCache(2);
        cache.put(1, 1);
        cache.put(2, 2);
        // 返回 1
        System.out.println(cache.get(1));
        cache.put(3, 3);    // 去除 key 2
        // 返回 -1 (未找到key 2)
        System.out.println(cache.get(2));
        // 返回 3
        System.out.println(cache.get(3));
        cache.put(4, 4);    // 去除 key 1
        // 返回 -1 (未找到 key 1)
        System.out.println(cache.get(1));
        // 返回 3
        System.out.println(cache.get(3));
        // 返回 4
        System.out.println(cache.get(4));
    }

    Map<Integer, Node> cache;
    /**
     * 这两个代表的是以 DoubleLinkedList 连接成的双向链表的头尾节点，
     * 且为哨兵节点。每个list中，又包含一个由 node 组成的一个双向链表。
     * 最外层双向链表中，freq 频次较大的 list 在前面，较小的 list 在后面
     */
    DoubleLinkedList firstLinkedList, lastLinkedList;
    int capacity;
    int size;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        cache = new HashMap<>();
        //初始化外层链表的头尾节点，作为哨兵节点
        firstLinkedList = new DoubleLinkedList();
        lastLinkedList = new DoubleLinkedList();
        firstLinkedList.next = lastLinkedList;
        lastLinkedList.pre = firstLinkedList;
    }

    //存储具体键值对信息的node
    private class Node {
        int key;
        int value;
        int freq = 1;
        Node pre;
        Node next;
        DoubleLinkedList doubleLinkedList;

        public Node() {

        }

        public Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    public int get(int key) {
        Node node = cache.get(key);
        if (node == null) return -1;
        freqInc(node);
        return node.value;
    }

    public void put(int key, int value) {
        if (capacity == 0) return;
        Node node = cache.get(key);
        if (node != null) {
            node.value = value;
            freqInc(node);
        } else {
            if (size == capacity) {
                /**
                 * 如果满了，则需要把频次最小的，且最久未访问的节点删除
                 * 由于list组成的链表频次从前往后依次减小，故最小的频次list是 lastLinkedList.pre
                 * list中的双向node链表采用的是头插法，因此最久未访问的元素是 lastLinkedList.pre.tail.pre
                 */
                //最小频次list
                DoubleLinkedList list = lastLinkedList.pre;
                //最久未访问的元素，需要删除
                Node deadNode = list.tail.pre;
                cache.remove(deadNode.key);
                list.removeNode(deadNode);
                size--;
                //如果删除deadNode之后，此list中的双向链表空了，则删除此list
                if (list.isEmpty()) {
                    removeDoubleLinkedList(list);
                }
            }
            //没有满，则新建一个node
            Node newNode = new Node(key, value);
            cache.put(key, newNode);
            //判断频次为1的list是否存在，不存在则新建
            DoubleLinkedList list = lastLinkedList.pre;
            if (list.freq != 1) {
                DoubleLinkedList newList = new DoubleLinkedList(1);
                addDoubleLinkedList(newList, list);
                newList.addNode(newNode);
            } else {
                list.addNode(newNode);
            }
            size++;
        }
    }

    //修改频次
    private void freqInc(Node node) {
        //从当前频次的list中移除当前 node
        DoubleLinkedList list = node.doubleLinkedList;
        if (list != null) {
            list.removeNode(node);
        }
        //如果当前list中的双向node链表空，则删除此list
        if (list.isEmpty()) {
            removeDoubleLinkedList(list);
        }
        //当前node频次加1
        node.freq++;
        //找到当前list前面的list，并把当前node加入进去
        DoubleLinkedList preList = list.pre;
        //如果前面的list不存在，则新建一个，并插入到由list组成的双向链表中
        //前list的频次不等于当前node频次，则说明不存在
        if (preList.freq != node.freq) {
            DoubleLinkedList newList = new DoubleLinkedList(node.freq);
            addDoubleLinkedList(newList, preList);
            newList.addNode(node);
        } else {
            preList.addNode(node);
        }

    }

    //从外层双向链表中删除当前list节点
    public void removeDoubleLinkedList(DoubleLinkedList list) {
        list.pre.next = list.next;
        list.next.pre = list.pre;
    }

    //知道了它的前节点，即可把新的list节点插入到其后面
    public void addDoubleLinkedList(DoubleLinkedList newList, DoubleLinkedList preList) {
        newList.pre = preList;
        newList.next = preList.next;
        preList.next.pre = newList;
        preList.next = newList;
    }

    //维护一个双向DoubleLinkedList链表 + 双向Node链表的结构
    private class DoubleLinkedList {
        //当前list中的双向Node链表所有频次都相同
        int freq;
        //当前list中的双向Node链表的头结点
        Node head;
        //当前list中的双向Node链表的尾结点
        Node tail;
        //当前list的前一个list
        DoubleLinkedList pre;
        //当前list的后一个list
        DoubleLinkedList next;

        public DoubleLinkedList() {
            //初始化内部链表的头尾节点，并作为哨兵节点
            head = new Node();
            tail = new Node();
            head.next = tail;
            tail.pre = head;
        }

        public DoubleLinkedList(int freq) {
            head = new Node();
            tail = new Node();
            head.next = tail;
            tail.pre = head;
            this.freq = freq;
        }

        //删除当前list中的某个node节点
        public void removeNode(Node node) {
            node.pre.next = node.next;
            node.next.pre = node.pre;
        }

        //头插法将新的node插入到当前list，并在新node中记录当前list的引用
        public void addNode(Node node) {
            node.pre = head;
            node.next = head.next;
            head.next.pre = node;
            head.next = node;
            node.doubleLinkedList = this;
        }

        //当前list中的双向node链表是否存在有效节点
        public boolean isEmpty() {
            //只有头尾哨兵节点，则说明为空
            return head.next == tail;
        }
    }


}