package com.zyl.arithmetrc.leetcode.pojo;

import lombok.Data;

import java.util.List;

@Data
public class Node {
    int val;
    private List<Node> children;
    public Node(int val){
        this.val = val;
    }
}
