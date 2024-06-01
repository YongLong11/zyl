package com.zyl.arithmetrc.leetcode.everyday;

import com.zyl.arithmetrc.leetcode.pojo.TreeNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class MarFour {

    private static void dfs(List<Integer> result , TreeNode node){
        if(node == null){
            return;
        }
        result.add(node.var());
        dfs(result, node.left());
        dfs(result, node.right());
    }

    private static List<List<Integer>> bfs(TreeNode root){
        List<List<Integer>> res = new ArrayList<>();

        Queue<TreeNode> queue = new ArrayDeque<>();

        if (root != null) {
            queue.add(root);
        }
        while (!queue.isEmpty()) {
            int n = queue.size();
            List<Integer> level = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                TreeNode node = queue.poll();
                level.add(node.var());
                if (node.left() != null) {
                    queue.add(node.left());
                }
                if (node.right() != null) {
                    queue.add(node.right());
                }
            }
            res.add(level);
        }

        return res;
    }

    public static int climbStairs(int n) {
        int a = 1, b = 1, sum;
        for(int i = 0; i < n - 1; i++){
            sum = a + b;
            a = b;
            b = sum;
        }
        return b;
    }

    public static int dp(int n){
        if(n == 1 || n == 2){
            return n;
        }
        return dp(n - 1 ) + dp(n - 2);
    }

    public static void main(String[] args) {
        System.out.println(climbStairs(9));
        System.out.println(dp(9));
    }
}
