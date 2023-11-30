package com.zyl.leetcode.pojo;


import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true, fluent = true)
public class TreeNode {
    TreeNode left;
    TreeNode right;
    int var;

    TreeNode() {
    }

    TreeNode(int var) {
        this.var = var;
    }

    TreeNode(int var, TreeNode left, TreeNode right) {
        this.var = var;
        this.left = left;
        this.right = right;
    }
}
