package com.zyl.arithmetrc.leetcode;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.*;


// 最近最少使用
public class LRUCache {
    class DLinkedNode {
        String key;
        int value;
        DLinkedNode pre;
        DLinkedNode post;
    }

//    private Map<String, Long> connections = new LinkedHashMap<String, Long>(100, 0.75f, true) {
//        @Override
//        protected boolean removeEldestEntry(Map.Entry eldest) {
//            // 当连接数超过阈值时，触发移除最久未使用的节点
//            return size() > 100;
//        }
//    };
    private ConcurrentMap<String, DLinkedNode> cache = new ConcurrentHashMap<String, DLinkedNode>();
    private int count;
    private int capacity;
    private DLinkedNode head, tail;

    public LRUCache(int capacity) {
        this.count = 0;
        this.capacity = capacity;

        head = new DLinkedNode();
        head.pre = null;

        tail = new DLinkedNode();
        tail.post = null;

        // 虚拟头节点和虚拟尾节点互连
        head.post = tail;
        tail.pre = head;
    }

    public int get(String key) {
        DLinkedNode node = cache.get(key);
        if(node == null){
            return -1;
        }
        // 如果 key 存在，先通过哈希表定位，再移到头部
        moveToHead(node);
        return node.value;
    }


    public void put(String key, int value) {
        DLinkedNode node = cache.get(key);
        if (node != null) {
            node.value = value;
            moveToHead(node);
            return;
        }
        // 如果 key 不存在，创建一个新的节点
        DLinkedNode newNode = new DLinkedNode();
        newNode.key = key;
        newNode.value = value;

        cache.put(key, newNode);
        addNode(newNode);

        ++count;
        // 如果达到容量限制，链表删除尾部节点，哈希表删除元素
        if(count > capacity){
            // pop the tail
            DLinkedNode tail = popTail();
            cache.remove(tail.key);
            --count;
        }
    }

    private void addNode(DLinkedNode node){
        node.pre = head;
        node.post = head.post;

        head.post.pre = node;
        head.post = node;
    }

    private void removeNode(DLinkedNode node){
        DLinkedNode pre = node.pre;
        DLinkedNode post = node.post;

        pre.post = post;
        post.pre = pre;
    }

    private void moveToHead(DLinkedNode node){
        removeNode(node);
        addNode(node);
    }

    private DLinkedNode popTail(){
        DLinkedNode res = tail.pre;
        removeNode(res);
        return res;
    }
}