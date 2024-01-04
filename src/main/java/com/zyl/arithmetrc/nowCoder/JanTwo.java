package com.zyl.arithmetrc.nowCoder;

import com.google.common.collect.Lists;
import com.zyl.arithmetrc.leetcode.pojo.Node;
import com.zyl.arithmetrc.leetcode.pojo.TreeNode;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class JanTwo {

    //https://www.nowcoder.com/practice/2b317e02f14247a49ffdbdba315459e7?tpId=295&tqId=1024572&ru=/exam/oj&qru=/ta/format-top101/question-ranking&sourceUrl=%2Fexam%2Foj
    public static int compare (String version1, String version2) {
        // write code here
        int index = 0;
        String[] split1 = version1.split("\\.");
        String[] split2 = version2.split("\\.");
        for (int i = 0; i < Math.max(split1.length, split2.length); i++) {

            if(i >= split1.length){
                Integer num2 = Integer.valueOf(split2[i]);
                if(num2 == 0){
                    continue;
                }
                return -1;
            }else if(i >= split2.length){
                Integer num1 = Integer.valueOf(split1[i]);

                if(num1 == 0){
                    continue;
                }
                return 1;
            }else if( i < split1.length && i < split2.length){
                Integer num1 = Integer.valueOf(split1[i]);
                Integer num2 = Integer.valueOf(split2[i]);
                if(num1.equals(num2)){
                    continue;
                }else {
                    return num1 > num2 ? 1 : -1;
                }
            }
        }
        return 0;
    }

    private static int[] transform(List<Integer> list){
        int[] arr = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            arr[i] = list.get(i);
        }
        return arr;
    }

    // 二叉树层序遍历
    public static ArrayList<ArrayList<Integer>> levelOrder (TreeNode root) {
        // write code here
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        if(root == null){
            return result;
        }
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);
        while (!queue.isEmpty()){
            int size = queue.size();
            ArrayList<Integer> currentList = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                TreeNode poll = queue.poll();
                Optional.ofNullable(poll.left()).ifPresent(left -> queue.add(left));
                Optional.ofNullable(poll.right()).ifPresent(right -> queue.add(right));
                currentList.add(poll.var());
            }
            result.add(currentList);
        }
        return result;
//        ArrayList<ArrayList<Integer> > res = new ArrayList();
//        if(root == null)
//            //如果是空，则直接返回空数组
//            return res;
//        //队列存储，进行层次遍历
//        Queue<TreeNode> q = new ArrayDeque<TreeNode>();
//        q.add(root);
//        while(!q.isEmpty()){
//            //记录二叉树的某一行
//            ArrayList<Integer> row = new ArrayList();
//            int n = q.size();
//            //因先进入的是根节点，故每层节点多少，队列大小就是多少
//            for(int i = 0; i < n; i++){
//                TreeNode cur = q.poll();
//                row.add(cur.var());
//                //若是左右孩子存在，则存入左右孩子作为下一个层次
//                if(cur.left() != null)
//                    q.add(cur.left());
//                if(cur.right() != null)
//                    q.add(cur.right());
//            }
//            //每一层加入输出
//            res.add(row);
//        }
//        return res;
    }

    public static ArrayList<ArrayList<Integer>> Print (TreeNode pRoot) {
        // write code here
        ArrayList<ArrayList<Integer>> levelOrder = levelOrder(pRoot);
        boolean left = true;
        ArrayList<ArrayList<Integer>> reslt = new ArrayList<>(levelOrder.size());
        for (ArrayList<Integer> level : levelOrder) {
            if(left){
                reslt.add(level);
                left = false;
            }else {
                left = true;
                Collections.reverse(level);
                reslt.add(level);
            }
        }
        return reslt;
    }

    private static Integer height(Node node){
        if(node == null){
            return 0;
        }
        int height = 0;
        List<Node> children = node.getChildren();

        if(children!= null && !children.isEmpty()){
            for (Node child : children) {
                height = Math.max(height, height(child));
            }
        }
        return height + 1;
    }

    private static Integer height(TreeNode node){
        if(node == null){
            return 0;
        }
        int height = 0;
        TreeNode left = node.left();
        TreeNode right = node.right();
        height = Math.max(height, height(left));
        height = Math.max(height, height(right));
        return height + 1;
    }


    // https://www.nowcoder.com/practice/ff05d44dfdb04e1d83bdbdab320efbcb?tpId=295&tags=&title=&difficulty=0&judgeStatus=0&rp=0&sourceUrl=%2Fexam%2Foj
    public static boolean isSymmetrical (TreeNode pRoot) {
        // write code here
        return recursion(pRoot, pRoot);
    }

    static boolean recursion(TreeNode root1, TreeNode root2){
        //可以两个都为空
        if(root1 == null && root2 == null)
            return true;
        //只有一个为空或者节点值不同，必定不对称
        if(root1 == null || root2 == null || root1.var() != root2.var())
            return false;
        //每层对应的节点进入递归比较
        return recursion(root1.left(), root2.right()) && recursion(root1.right(), root2.left());
    }



    public static ArrayList<Integer> maxInWindows (int[] num, int size) {
        // write code here
        int skip = 0;
        int limit = size;
        int end = skip + limit - 1;
        if(num == null || size > num.length || size <= 0 ){
            return null;
        }
        ArrayList<Integer> result = new ArrayList<>();
        while (end < num.length){
            int asInt = Arrays.stream(num).skip((long) skip).limit(limit).sorted().skip(limit - 1).findAny().getAsInt();
            result.add(asInt);
            skip++;
            end = skip + limit - 1;
        }
        return result;
    }


    public static void main(String[] args) {
//        System.out.println(compare("1.1.011.1", "1.1.11"));
//        Node root = new Node(1);
//        Node node1 = new Node(2);
//        Node node2 = new Node(22);
//        Node node3 = new Node(333);
//        Node node4 = new Node(333);
//        Node node5 = new Node(333);
//        node1.setChildren(Lists.newArrayList(node3, node4));
//        node2.setChildren(Lists.newArrayList(node5));
//        root.setChildren(Lists.newArrayList(node1, node2));
        TreeNode root = new TreeNode(0);
        TreeNode node1 = new TreeNode(1);
        TreeNode node2 = new TreeNode(1);
        TreeNode node3 = new TreeNode(2);
        TreeNode node4 = new TreeNode(2);
        node1.left(node3);
        node1.right(node4);
        root.left(node1);
        root.right(node2);
        int[] arr = new int[]{2,3,4,2,6,2,5,1};
        System.out.println(maxInWindows(arr, 8));

    }
}
