package com.zyl.leetcode;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.zyl.leetcode.pojo.ListNode;
import com.zyl.leetcode.pojo.TreeNode;
import lombok.experimental.UtilityClass;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@UtilityClass
public class Arithmetic {
    static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10, 0L, TimeUnit.SECONDS, new LinkedBlockingQueue<>());

    //    public static void main(String[] args) throws Throwable {
//        int[] nums = new int[]{2,3,10,5,7,8,9};
//        System.out.println(maxArea(nums));
//        int divide = divide(1, 1);
//
//    }

    // 寻找峰值
    public int findPeakElement (int[] nums) {
        // write code here
        if(nums.length < 3){
            return 0;
        }
        int right = nums.length - 1;
        int left = 0;
        while (left < right){
            int mid = (left + right) / 2;
            if(nums[mid] > nums[mid + 1]){
                right = mid;
            }else {
                left = mid + 1;
            }
        }
        return nums[right];
    }

    // 使用两个 stack 实现队列功能
    static Stack<Integer> stack1 = new Stack<Integer>();
    static Stack<Integer> stack2 = new Stack<Integer>();
    public static void push(int node) {
        stack1.push(node);
    }

    public static int pop() {
        //将第一个栈中内容弹出放入第二个栈中
        while(!stack1.isEmpty())
            stack2.push(stack1.pop());
        //第二个栈栈顶就是最先进来的元素，即队首
        int res = stack2.pop();
        //再将第二个栈的元素放回第一个栈
        while(!stack2.isEmpty())
            stack1.push(stack2.pop());
        return res;
    }


    // 合并两个有序的链表
    public ListNode mergeKLists (ArrayList<ListNode> lists) {
        // write code here
        List<ListNode> all = new ArrayList<>();
        lists.forEach(listNode -> {
            while (listNode != null){
                all.add(listNode);
                listNode = listNode.next();
            }
        });
        all.sort(Comparator.comparing(ListNode::val));
        ListNode ret = new ListNode(-1);
        ListNode result = ret;
        for (int i = 0; i < all.size(); i++) {
            ret.next(all.get(i));
            ret = ret.next();
            // 最后一个node，他的下一个设为 null
            if(i == lists.size() - 1){
                ret.next(null);
            }
        }
        return result.next();
    }


    //链表中的节点每k个一组翻转
    public ListNode reverseKGroup1 (ListNode head, int k) {
        if(k<=0) return head;
        List<Stack<ListNode>> lists = new Stack<>();
        ListNode dump = head;
        while (dump != null){
            int flag = 0;
            Stack<ListNode> stack = new Stack<>();
            for (int i = 0; i < k; i++) {
                flag++;
                stack.push(dump);
                dump = dump.next();
            }
            // flag 大小不等于 K，说明是到最后一个阶段了，反转顺序后放进 stack 保证输出的顺序一致
            if(flag != k ){
                ListNode pop = null;
                while (flag-- > 0){
                    pop = stack.pop();
                }
                ListNode listNode = reverseList(pop);
                while (listNode != null){
                    stack.push(listNode);
                    listNode = listNode.next();
                }
            }else {
                lists.add(stack);
            }
        }
        // 重组为对列，方便生成 listNode
        List<ListNode> queue = new ArrayList<>();
        lists.forEach(stack -> {
            while (!stack.empty()){
                queue.add(stack.pop());
            }
        });

        ListNode ret = new ListNode(-1);
        ListNode result = ret;
        for (int i = 0; i < queue.size(); i++) {
            ret.next(queue.get(i));
            ret = ret.next();
            // 最后一个node，他的下一个设为 null
            if(i == queue.size() - 1){
                ret.next(null);
            }
        }
        return result.next();

    }
    public ListNode reverseKGroup (ListNode head, int k) {
        if(k<=0) return head;
        ListNode dump = head;
        int start = 1;
        do {
            int end = start + k - 1;
            ListNode reversed = reverseBetween(dump, start, end);
            start = end + 1;
            if(reversed != null){
                dump = reversed;
            }else {
                break;
            }
        } while (true);
        return dump;
    }

    // 判断链表是否有环
    public boolean hasCycle(ListNode head) {
        Set<ListNode> set = new HashSet<>();
        if (head == null) {
            return false;
        }
        while (head != null) {
            if (set.contains(head)) {
                return true;
            }
            set.add(head);
            head = head.next();
        }
        return false;
    }

    // 快慢指针，追赶游戏，慢指针追得上快指针。就是有环
    public boolean hasCycle1(ListNode head) {
        ListNode p1 = head;
        ListNode p2 = head;
        int left = 0;
        int right = 0;
        while (p2 != null && p2.next() != null) {
            p1 = p1.next();
            left++;
            p2 = p2.next().next();
            right += 2;
            if (p1 == p2) {
                System.out.println("链表环的长度 -> " + (right - left));
                return true;
            }
        }
        return false;
    }

    // 局部链表反转
    public ListNode reverseBetween(ListNode head, int m, int n) {
        if (head == null || m >= n) {
            return head;
        }

        ListNode dummy = new ListNode(0);
        dummy.next(head);
        ListNode prev = dummy;

        // 移动到反转的起始位置的前一个节点
        for (int i = 1; i < m; i++) {
            if (prev == null) {
                return null; // 无效输入
            }
            prev = prev.next();
        }
        if (prev == null) {
            return null; // 无效输入
        }
        ListNode current = prev.next();
        ListNode next = null;
        ListNode reversedTail = current;

        // 开始局部反转
        for (int i = m; i <= n; i++) {
            if (current == null) {
                return null; // 无效输入
            }
            ListNode temp = current.next();
            current.next(next);
            next = current;
            // 执行最后一次的时候，temp 就是 n + 1 位置的node
            current = temp;
        }

        // 连接反转部分的头尾
        prev.next(next);
        reversedTail.next(current);

        return dummy.next();

    }


    // 链表反转
    // 先设置一个前置的为 null， 然后反转，当前的下一个应该是前面的那个，
    // 对当前的上一个重新赋值为当前的，对当前的重新赋值为当前的下一个，
    public ListNode reverseList(ListNode head){
        ListNode pre = null;
        ListNode current = head;
        while (current != null){
            ListNode next = current.next();
            current.next(pre);
            pre = current;
            current = next;
        }
        return pre;
    }
        public ListNode ReverseList(ListNode head) {
        Stack<ListNode> stack = new Stack<>();
        //把链表节点全部摘掉放到栈中
        while (head != null) {
            stack.push(head);
            head = head.next();
        }
        if (stack.empty()) {
            return null;
        }
        ListNode node = stack.pop();
        ListNode dummy = node;
        //栈中的结点全部出栈，然后重新连成一个新的链表
        while (!stack.isEmpty()) {
            ListNode tempNode = stack.pop();
            node.next(tempNode);
            node = node.next();
        }
        //最后一个结点就是反转前的头结点，一定要让他的next
        //等于空，否则会构成环
        node.next(null);
        return dummy;
    }

    public boolean isSameTree(TreeNode p, TreeNode q) {
        AtomicInteger atomicInteger = new AtomicInteger();
        atomicInteger.addAndGet(1);
        if (p == null && q == null) {
            return true;
        }
        if (p == null || q == null) {
            return false;
        }
        if (p.var() != q.var()) {
            return false;
        } else {
            return isSameTree(p.left(), q.left()) && isSameTree(p.right(), q.right());
        }
    }

    // 中序遍历的顺序是左、根、右
    // 前序遍历的顺序是根、左、右
    // 中序遍历的顺序是左、右、根
    public void preOrder(TreeNode root) {
        if (root == null) {
            return;
        }
        System.out.println(root.var());
        preOrder(root.left());
        preOrder(root.right());
    }

    public void preOrder2(TreeNode root) {
        if (root == null) {
            return;
        }
        Stack<TreeNode> stack = new Stack<>();
        TreeNode cur = root;
        while (!stack.empty() || cur != null) {
            if (cur != null) {
                //打印根节点
                System.out.print(cur.var());
                //根节点入栈
                stack.push(cur);
                //访问左子树
                cur = cur.left();
            } else {
                cur = stack.pop().right();
            }
        }
    }

    public void inorder(TreeNode root) {
        //当前节点为空或是说当前节点都不存在，也就没有必要继续遍历其叶子节点了
        if (root == null) return;
        //遍历左叶子节点
        inorder(root.left());
        //遍历当前节点
        System.out.print(root.var() + "\t");
        //遍历右叶子节点
        inorder(root.right());
    }

    public void inOrder2(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        TreeNode cur = root;
        while (!stack.empty() || cur != null) {
            if (cur != null) {
                stack.push(cur);
                cur = cur.left();
            } else {
                cur = stack.pop();
                System.out.print(cur.var() + " ");
                cur = cur.right();
            }
        }
    }


    // 使用位运算两数相除
    public int divide(int dividend, int divisor) {
        // 处理特殊情况
        if (dividend == Integer.MIN_VALUE && divisor == -1) {
            return Integer.MAX_VALUE; // 防止溢出
        }
        // 确定最终结果的正负号
        int sign = (dividend < 0) ^ (divisor < 0) ? -1 : 1;

        int ldividend = Math.abs(dividend); // 转换为长整数以处理溢出
        int ldivisor = Math.abs(divisor);

        long quotient = 0;

        while (ldividend >= ldivisor) {
            long temp = ldivisor;
            long multiple = 1;
            while (ldividend >= (temp << 1)) {
                temp <<= 1;
                multiple <<= 1;
            }
            ldividend -= temp;
            quotient += multiple;
        }

        return (int) (sign * quotient);
    }


    // 给你一个整数数组 nums ，判断是否存在三元组 [nums[i], nums[j], nums[k]] 满足 i != j、i != k 且 j != k ，同时还满足 nums[i] + nums[j] + nums[k] == 0 。请
    //你返回所有和为 0 且不重复的三元组。
    //注意：答案中不可以包含重复的三元组。
    // https://leetcode.cn/problems/3sum/
    // 道理同foursum，估计是性能不好
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> lists = generateCombinations(Arrays.stream(nums).boxed().collect(Collectors.toList()), 3);
        return lists.stream().filter(list -> {
                    List<Integer> collect = list.stream().distinct().collect(Collectors.toList());
                    return collect.size() >= 3;
                })
                .filter(list -> list.stream().mapToInt(Integer::intValue).sum() == 0)
                .peek(list -> list.sort(Integer::compareTo))
                .distinct()
                .collect(Collectors.toList());
    }


    //请你来实现一个 myAtoi(string s) 函数，使其能将字符串转换成一个 32 位有符号整数（类似 C/C++ 中的 atoi 函数）。
    //函数 myAtoi(string s) 的算法如下：
    //读入字符串并丢弃无用的前导空格
    //检查下一个字符（假设还未到字符末尾）为正还是负号，读取该字符（如果有）。 确定最终结果是负数还是正数。 如果两者都不存在，则假定结果为正。
    //读入下一个字符，直到到达下一个非数字字符或到达输入的结尾。字符串的其余部分将被忽略。
    //将前面步骤读入的这些数字转换为整数（即，"123" -> 123， "0032" -> 32）。如果没有读入数字，则整数为 0 。必要时更改符号（从步骤 2 开始）。
    //如果整数数超过 32 位有符号整数范围 [−231,  231 − 1] ，需要截断这个整数，使其保持在这个范围内。具体来说，小于 −231 的整数应该被固定为 −231 ，大于 231 − 1 的整数应该被固定为 231 − 1 。
    //返回整数作为最终结果。
    public static int myAtoi(String s) {
        String regex = "-?\\d+";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(s);
        while (matcher.find()) {
            String match = matcher.group();
            long number = Long.parseLong(match);
            return number > Integer.MAX_VALUE ? Integer.MAX_VALUE : number < Integer.MIN_VALUE ? Integer.MIN_VALUE : (int) number;
        }
        return 0;
    }


    // 没懂
    // 思路：知道索引和行数之间的关系，即可
    // 将一个给定字符串 s 根据给定的行数 numRows ，以从上往下、从左到右进行 Z 字形排列。
    //比如输入字符串为 "PAYPALISHIRING" 行数为 3 时，排列如下：
    //P   A   H   N
    //A P L S I I G
    //Y   I   R
    //之后，你的输出需要从左往右逐行读取，产生出一个新的字符串，比如："PAHNAPLSIIGYIR"。
    public static String convert(String s, int numRows) {
        if (numRows < 2) return s;
        List<StringBuilder> rows = new ArrayList<StringBuilder>();
        for (int i = 0; i < numRows; i++) rows.add(new StringBuilder());
        int i = 0, flag = -1;
        for (char c : s.toCharArray()) {
            rows.get(i).append(c);
            if (i == 0 || i == numRows - 1) {
                flag = -flag;
            }
            i += flag;
        }
        StringBuilder res = new StringBuilder();
        for (StringBuilder row : rows) res.append(row);
        return res.toString();
    }

    //一只青蛙一次可以跳上1级台阶，也可以跳上2级台阶。求该青蛙跳上一个 n 级的台阶总共有多少种跳法。
    //答案需要取模 1e9+7（1000000007），如计算初始结果为：1000000008，请返回 1。
    // https://leetcode.cn/problems/qing-wa-tiao-tai-jie-wen-ti-lcof/
    public static int numWays(int n) {
        int first = 1;
        int second = 1;
        int third = 0;
        if (n < 2) {
            return 1;
        } else {
            for (int i = 2; i <= n; i++) {
                third = (first + second) % 1000000007;
                first = second;
                second = third;
            }
            return third;
        }

    }

    public List<List<Integer>> yanHuiSanJiao(int numRows) {
        List<List<Integer>> ret = new ArrayList<>();
        for (int i = 0; i < numRows; i++) {
            List<Integer> curRow = new ArrayList<>();
            for (int p = 0; p <= i; p++) {
                if (p == 0 || p == i) {
                    curRow.add(1);
                } else {
                    Integer num = ret.get(i - 1).get(p - 1) + ret.get(i - 1).get(p);
                    curRow.add(num);
                }
            }
            ret.add(curRow);
        }
        return ret;
    }

    // 杨辉三角
    //https://leetcode.cn/problems/pascals-triangle/solutions/510638/yang-hui-san-jiao-by-leetcode-solution-lew9/
    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> ret = new ArrayList<>();
        for (int i = 0; i < numRows; ++i) {
            List<Integer> row = new ArrayList<Integer>();
            for (int j = 0; j <= i; ++j) {
                if (j == 0 || j == i) {
                    row.add(1);
                } else {
                    row.add(ret.get(i - 1).get(j - 1) + ret.get(i - 1).get(j));
                }
            }
            ret.add(row);
        }
        return ret;
    }

    //https://leetcode.cn/problems/climbing-stairs/
    public static int climbStairs(int n) {
        if (n == 1 || n == 2) {
            return n;
        }
        return climbStairs(n - 1) + climbStairs(n - 2);
    }


    // 给你一个由 n 个整数组成的数组 nums ，和一个目标值 target 。
    // 请你找出并返回满足下述全部条件且不重复的四元组 [nums[a], nums[b], nums[c], nums[d]] （若两个四元组元素一一对应，则认为两个四元组重复）
    // https://leetcode.cn/problems/4sum/
    // 试了下，功能应该是没问题的，性能不太好
    public static List<List<Integer>> fourSum(int[] nums, int target) {
        List<Integer> collect = Arrays.stream(nums).boxed().collect(Collectors.toList());
        List<List<Integer>> generatedCombinations = generateCombinations(collect, 4);
        return generatedCombinations.stream()
                .filter(lists -> lists.stream().mapToInt(Integer::intValue).sum() == target)
                .peek(lists -> lists.sort(Integer::compareTo))
                .distinct()
                .collect(Collectors.toList());
    }

    // 获取 list 中指定长度的随机组合
    private static List<List<Integer>> generateCombinations(List<Integer> list, int k) {
        List<List<Integer>> combinations = new ArrayList<>();
        generateCombinations(list, new ArrayList<>(), 0, k, combinations);
        return combinations;
    }

    private static void generateCombinations(List<Integer> list, List<Integer> combination, int index, int k, List<List<Integer>> result) {
        // 终止条件：当前组合的大小等于指定的元素个数
        if (combination.size() == k) {
            result.add(new ArrayList<>(combination));
            return;
        }
        for (int i = index; i < list.size(); i++) {
            int num = list.get(i);
            // 将选择的元素添加到当前组合中
            combination.add(num);
            // 递归调用函数，选择下一个元素
            generateCombinations(list, combination, i + 1, k, result);
            // 将选择的元素从当前组合中移除
            combination.remove(combination.size() - 1);
        }
    }

    //给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串 s ，判断字符串是否有效。
    //
    //有效字符串需满足：
    //
    //左括号必须用相同类型的右括号闭合。
    //左括号必须以正确的顺序闭合。
    //每个右括号都有一个对应的相同类型的左括号。
    // https://leetcode.cn/problems/valid-parentheses/
    public static boolean isValid(String s) {
        List<String> leftList = Arrays.asList("{", "[", "(");
        List<String> rightList = Arrays.asList("}", "]", ")");

        if ((s.split("").length % 2) != 0) {
            return false;
        }
        try {
            Stack<String> leftStack = new Stack<>();
            for (String string : s.split("")) {
                if (leftList.contains(string)) {
                    leftStack.push(string);
                } else {
                    String peek = leftStack.peek();
                    if (Objects.equals(peek, findRight.apply(string))) {
                        leftStack.pop();
                    } else if (rightList.contains(string)) {
                        return false;
                    }
                }
            }
            return leftStack.empty();
        } catch (Exception e) {
            return false;
        }
    }

    private static final Function<String, String> findRight = iterm -> {
        String ret = "";
        switch (iterm) {
            case "}":
                ret = "{";
                break;
            case "]":
                ret = "[";
                break;
            case ")":
                ret = "(";
                break;
        }
        return ret;
    };


    // 数字 n 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且 有效的 括号组合。
    //输入：n = 3
    //输出：["((()))","(()())","(())()","()(())","()()()"]
    //示例 2：
    //输入：n = 1
    //输出：["()"]
    // https://leetcode.cn/problems/generate-parentheses/solutions/192912/gua-hao-sheng-cheng-by-leetcode-solution/
    // 必然的前提：左括号的数量一定大于等于右括号
    public static List<String> generateParenthesis(int n) {
        List<String> ret = new ArrayList<>();

        if (n < 0) {
            return ret;
        }
        generateParenthesis("", n, n, ret);
        return ret;
    }

    private static void generateParenthesis(String str, int left, int right, List<String> ret) {
        if (left == 0 && right == 0) {
            ret.add(str);
            return;
        }
        if (left == right) {
            //剩余左右括号数相等，下一个只能用左括号
            generateParenthesis(str + "(", left - 1, right, ret);
        } else if (left < right) {
            //剩余左括号小于右括号，下一个可以用左括号也可以用右括号
            if (left > 0) {
                generateParenthesis(str + "(", left - 1, right, ret);
            }
            generateParenthesis(str + ")", left, right - 1, ret);
        }
    }


    // 罗马数字包含以下七种字符： I， V， X， L，C，D 和 M。
    //字符          数值
    //I             1
    //V             5
    //X             10
    //L             50
    //C             100
    //D             500
    //M             1000
    //例如， 罗马数字 2 写做 II ，即为两个并列的 1。12 写做 XII ，即为 X + II 。 27 写做  XXVII, 即为 XX + V + II 。
    //通常情况下，罗马数字中小的数字在大的数字的右边。但也存在特例，例如 4 不写做 IIII，而是 IV。数字 1 在数字 5 的左边，所表示的数等于大数 5 减小数 1 得到的数值 4 。同样地，数字 9 表示为 IX。这个特殊的规则只适用于以下六种情况：
    //I 可以放在 V (5) 和 X (10) 的左边，来表示 4 和 9。
    //X 可以放在 L (50) 和 C (100) 的左边，来表示 40 和 90。
    //C 可以放在 D (500) 和 M (1000) 的左边，来表示 400 和 900。
    //给你一个整数，将其转为罗马数字。
    // https://leetcode.cn/problems/integer-to-roman/
    // 下面两种方法都是解决数据转罗马
    private static String intToRoman(int num) {
        if (num <= 0 || num >= Integer.MAX_VALUE) {
            return "";
        }
        Map<String, List<Integer>> levelMap = getMap();
        String result = "";
        while (num > 0) {
            String luoMa = getLuoMa(num);
            result = result + luoMa;
            Integer i = levelMap.get(luoMa).get(0);
            num = num - i;
        }
        return result;
    }

    private static Map<String, List<Integer>> getMap() {
        Map<String, List<Integer>> levelMap = new LinkedHashMap<>();
        levelMap.put("I", Arrays.asList(1, 4));
        levelMap.put("V", Arrays.asList(5, 9));
        levelMap.put("X", Arrays.asList(10, 40));
        levelMap.put("L", Arrays.asList(50, 90));
        levelMap.put("C", Arrays.asList(100, 400));
        levelMap.put("D", Arrays.asList(500, 100));
        levelMap.put("M", Arrays.asList(1000, Integer.MAX_VALUE));
        levelMap.put("IV", Arrays.asList(4, 5));
        levelMap.put("IX", Arrays.asList(9, 10));
        levelMap.put("XL", Arrays.asList(40, 50));
        levelMap.put("XC", Arrays.asList(90, 100));
        levelMap.put("CD", Arrays.asList(400, 500));
        levelMap.put("CM", Arrays.asList(900, 1000));
        return levelMap;
    }

    private static String getLuoMa(Integer num) {
        Map<String, List<Integer>> levelMap = getMap();
        // 实际使用，左闭右开
        return levelMap.entrySet()
                .stream()
                .filter(entry -> {
                    List<Integer> value = entry.getValue();
                    int min = value.get(0);
                    int max = value.get(1);
                    if (num >= min && num < max) {
                        return true;
                    } else {
                        return false;
                    }
                })
                .findFirst()
                .get()
                .getKey();
    }
//    public static String intToRoman(int num) {
//        int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
//        String[] symbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
//
//        StringBuffer roman = new StringBuffer();
//        for (int i = 0; i < values.length; ++i) {
//            int value = values[i];
//            String symbol = symbols[i];
//            while (num >= value) {
//                num -= value;
//                roman.append(symbol);
//            }
//            if (num == 0) {
//                break;
//            }
//        }
//        return roman.toString();
//    }

    //罗马数字包含以下七种字符: I， V， X， L，C，D 和 M。
    //字符          数值
    //I             1
    //V             5
    //X             10
    //L             50
    //C             100
    //D             500
    //M             1000
    //例如， 罗马数字 2 写做 II ，即为两个并列的 1 。12 写做 XII ，即为 X + II 。 27 写做  XXVII, 即为 XX + V + II 。
    //通常情况下，罗马数字中小的数字在大的数字的右边。但也存在特例，
    // 例如 4 不写做 IIII，而是 IV。数字 1 在数字 5 的左边，所表示的数等于大数 5 减小数 1 得到的数值 4 。
    // 同样地，数字 9 表示为 IX。这个特殊的规则只适用于以下六种情况：
    //I 可以放在 V (5) 和 X (10) 的左边，来表示 4 和 9。
    //X 可以放在 L (50) 和 C (100) 的左边，来表示 40 和 90。
    //C 可以放在 D (500) 和 M (1000) 的左边，来表示 400 和 900。
    //给定一个罗马数字，将其转换成整数。
    public static int romanToInt(String s) {
        int sum = 0;
        int preNum = getValue(s.charAt(0));
        for (int i = 1; i < s.length(); i++) {
            int num = getValue(s.charAt(i));
            if (preNum < num) {
                sum -= preNum;
            } else {
                sum += preNum;
            }
            preNum = num;
        }
        sum += preNum;
        return sum;
    }

    private static int getValue(char ch) {
        switch (ch) {
            case 'I':
                return 1;
            case 'V':
                return 5;
            case 'X':
                return 10;
            case 'L':
                return 50;
            case 'C':
                return 100;
            case 'D':
                return 500;
            case 'M':
                return 1000;
            default:
                return 0;
        }
    }


    // 最长公共前缀
    // https://leetcode.cn/problems/longest-common-prefix/
    public String longestCommonPrefix1 (String[] strs) {
       if(strs ==null || strs.length==0){
           return "";
       }
       if(strs.length == 1){
           return strs[0];
       }
        int length = strs[0].length();
        int count = strs.length;
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char c = strs[0].charAt(i);
            stringBuilder.append(c);
            for (int p = 0; p < count; p++) {
                // 如果没有走这个if，就是都匹配上了，全部相等
                if(strs[p].length() == i|| !strs[p].startsWith(stringBuilder.toString())){
                    return stringBuilder.substring(0, i);
                }
            }
        }
       return strs[0];
    }
    public static String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }
        int length = strs[0].length();
        int count = strs.length;
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char c = strs[0].charAt(i);
            stringBuilder.append(c);
            for (int j = 1; j < count; j++) {
                try {
                    if (!strs[j].startsWith(stringBuilder.toString())) {
                        return stringBuilder.substring(0, stringBuilder.length() - 2);
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    return stringBuilder.substring(0, stringBuilder.length() - 2);
                }

            }
        }
        return strs[0];
    }


    // https://leetcode.cn/problems/reverse-integer/solutions/211865/tu-jie-7-zheng-shu-fan-zhuan-by-wang_ni_ma/
    //给你一个 32 位的有符号整数 x ，返回将 x 中的数字部分反转后的结果。
    //如果反转后整数超过 32 位的有符号整数的范围 [−231,  231 − 1] ，就返回 0。
    //假设环境不允许存储 64 位整数（有符号或无符号）。
    public static int reverse(int x) {
        if (x == 0) {
            return 0;
        }
        boolean isUp = true;
        String str = String.valueOf(x);
        if (str.startsWith("-")) {
            isUp = false;
            str = str.substring(1, str.length());
        }
        String[] split1 = str.split("");
        int left = 0, right = split1.length - 1;
        for (int i = 0; left < right; i++) {
            String temp = split1[left];
            split1[left] = split1[right];
            split1[right] = temp;
            left++;
            right--;
        }
        // 先转为 long ，可以消除字符串前面的 0
        String collect = String.join("", split1);
        long p = Long.parseLong(collect);
        collect = p + "";
        if (!isUp) {
            collect = "-" + collect;
        }
        long l = Long.parseLong(collect);
        return (l > Integer.MAX_VALUE || l < Integer.MIN_VALUE) ? 0 : (int) l;

//        if(x == 0){
//            return 0;
//        }
//        String s = String.valueOf(x);
//        StringBuilder stringBuilder = new StringBuilder();
//        if(s.startsWith("-")) {
//            stringBuilder.append("-");
//        }
//        String[] split = s.split("");
//        int left = 0, right = split.length - 1;
//        while (left < right){
//            if(stringBuilder.toString().startsWith("-")){
//                continue;
//            }
//            stringBuilder.insert(0, split[left]);
//            left ++;
//        }
//        int ret = 0;
//        try {
//            ret = Integer.parseInt(stringBuilder.toString());
//        }catch (NumberFormatException e){
//            return 0;
//        }
//        return ret;
    }

    //给你一个整数数组 nums ，请你找出一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
    //子数组 是数组中的一个连续部分。
    //https://leetcode.cn/problems/maximum-subarray/
    public int maxSubArray(int[] nums) {

        int len = nums.length;
        // dp[i] 表示：以 nums[i] 结尾的连续子数组的最大和
        int[] dp = new int[len];
        dp[0] = nums[0];

        for (int i = 1; i < len; i++) {
            if (dp[i - 1] > 0) {
                dp[i] = dp[i - 1] + nums[i];
            } else {
                dp[i] = nums[i];
            }
        }

        // 也可以在上面遍历的同时求出 res 的最大值，这里我们为了语义清晰分开写，大家可以自行选择
        int res = dp[0];
        for (int i = 1; i < len; i++) {
            res = Math.max(res, dp[i]);
        }
        return res;

    }

    //给你一个字符串 s，找到 s 中最长的回文子串。
    //如果字符串的反序与原始字符串相同，则该字符串称为回文字符串。
    // https://leetcode.cn/problems/longest-palindromic-substring/
    public static String longestPalindrome(String s) {
        String ans = "";
        int max = 0;
        int len = s.length();
        for (int i = 0; i < len; i++)
            for (int q = i + 1; q <= len; q++) {
                String test = s.substring(i, q);
                if (isPalindrome(test) && test.length() > max) {
                    ans = s.substring(i, q);
                    max = Math.max(max, ans.length());
                }
            }
        return ans;

    }

    private static boolean isPalindrome(String s) {
        if (s.length() == 0) {
            return true;
        }
        String[] split = s.split("");
        int start = 0, end = split.length - 1;
        for (int i = 0; start <= end; i++) {
            if (!split[start].equalsIgnoreCase(split[end])) {
                return false;
            }
            start++;
            end--;
        }
        return true;
    }


    public static int longestPalindrome1(String s) {
        if (s == null || s.length() < 1) {
            return 0;
        }

        int start = 0;
        int end = 0;
        int max = 0;
        for (int i = 0; i < s.length(); i++) {
            // 以当前字符为中心的奇数长度回文子串
            int len1 = expandAroundCenter(s, i, i);
            // 以当前字符和下一个字符的中间为中心的偶数长度回文子串
            int len2 = expandAroundCenter(s, i, i + 1);

           max = Math.max(len1, len2);
        }

        // 返回最长回文子串
        return max;
    }

    private static int expandAroundCenter(String s, int left, int right) {
        // 从当前中心向两边扩展，检查回文子串长度
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        // 返回回文子串的长度
        return right - left - 1;
    }


    //给定一个整数数组 nums，将数组中的元素向右轮转 k 个位置，其中 k 是非负数。
    public static Integer[] rotate(int[] nums, int k) {
        int length = nums.length;
        LinkedList<Integer> ret = new LinkedList<Integer>();

        for (int i = 0; i < length; i++) {
            ret.add(1);
        }
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < length; i++) {
            // 右移有两种情况，1、未超过数组长度，2、超过数组长度
            if ((i + k) < length) {
                ret.set(i + k, nums[i]);
//                map.put(i + k, nums[i]);
            } else {
                ret.set(i + k - length, nums[i]);
//                map.put(i + k - length, nums[i]);
            }
        }
//        for (int i = 0; i < length; i++) {
//            ret.add(map.get(i));
//        }
        return ret.toArray(new Integer[0]);
    }

    //给定一个长度为 n 的整数数组 height 。有 n 条垂线，第 i 条线的两个端点是 (i, 0) 和 (i, height[i]) 。
    //找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。
    //返回容器可以储存的最大水量。
    //https://leetcode.cn/problems/container-with-most-water/
    public static int maxArea(int[] height) {

        int i = 0, j = height.length - 1, res = 0;
        while (i < j) {
            res = height[i] < height[j] ?
                    Math.max(res, (j - i) * height[i++]) :
                    Math.max(res, (j - i) * height[j--]);
        }
        return res;

        // 性能不好
//        List<Integer> ret = new ArrayList<>();
//        List<Integer> collect = Arrays.stream(height).boxed().collect(Collectors.toList());
//        int start = 0, end = height.length - 1;
//        for (int i = 0; i < collect.size(); i++) {
//            for (int q = 0; q < collect.size(); q++) {
//                List<Integer> area = getArea(i, q, collect);
//                ret.addAll(area);
//            }
//        }
//        if (ret.isEmpty()) {
//            return 0;
//        }
//        return ret.stream().max(Integer::compareTo).get();
    }

    private static List<Integer> getArea(int start, int end, List<Integer> collect) {
        int left = collect.get(start);
        int right = collect.get(end);
        int min = Math.min(left, right);

        int ret = (end - start) * min;
        int area = (BigDecimal.valueOf(start).subtract(BigDecimal.valueOf(end)))
                .multiply(BigDecimal.valueOf(min)).abs().intValue();
        List<Integer> list = new ArrayList<>();
        list.add(ret);
        return list;
    }

    //给定一个字符串 s ，请你找出其中不含有重复字符的 最长子串 的长度。
    public static int lengthOfLongestSubstring(String s) {
        String[] split = s.split("");
        if (s.equals("")) {
            return 0;
        }
        if (split.length == 1) {
            return 1;
        }
        List<Integer> resultLength = new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            Set<String> strings = new HashSet<>();
            for (int q = i; q < split.length; q++) {
                int size = strings.size();
                strings.add(split[q]);
                if (size == strings.size() || strings.size() == split.length) {
                    resultLength.add(strings.size());
                    break;
                }
            }
        }
        if (resultLength.isEmpty()) {
            return -1;
        }
        resultLength.sort(Comparator.reverseOrder());
        return resultLength.get(0);
    }


    // 思路：将下标和对应的值存进map， 目标值 - 当前值，判断map是否存在，存在则返回对应的下标，不存在存进map
    //给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 target  的那 两个 整数，并返回它们的数组下标。
    //你可以假设每种输入只会对应一个答案。但是，数组中同一个元素在答案里不能重复出现
    public static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>(nums.length);
        for (int i = 0; i < nums.length; i++) {
            // 当前值
            int num = nums[i];
            int other = target - num;
            if (map.containsKey(other)) {
                return new int[]{map.get(other), i};
            } else {
                map.put(num, i);
            }
        }
        return null;
    }


    //给定一个大小为 n 的数组 nums ，返回其中的多数元素。多数元素是指在数组中出现次数 大于 ⌊ n/2 ⌋ 的元素。
    //你可以假设数组是非空的，并且给定的数组总是存在多数元素。
    public Integer majorityElement(int[] nums) {
        int length = nums.length;
        long i = BigDecimal.valueOf(length).divide(BigDecimal.valueOf(2), RoundingMode.HALF_UP).longValue();
        Map<Integer, Long> collect = Arrays.stream(nums).boxed().collect(Collectors.groupingBy(a -> a, Collectors.counting()));
        for (Map.Entry<Integer, Long> entry : collect.entrySet()) {
            if (entry.getValue() > i) {
                return entry.getKey();
            }
        }
        return null;
    }

    //给你一个 升序排列 的数组 nums ，请你 原地 删除重复出现的元素，使每个元素 只出现一次 ，
    // 返回删除后数组的新长度。元素的 相对顺序 应该保持 一致 。然后返回 nums 中唯一元素的个数。
    //考虑 nums 的唯一元素的数量为 k ，你需要做以下事情确保你的题解可以被通过：
    //更改数组 nums ，使 nums 的前 k 个元素包含唯一元素，并按照它们最初在 nums 中出现的顺序排列。nums 的其余元素与 nums 的大小不重要。
    //返回 k 。
//    public int removeDuplicates(int[] nums) {
//        return (int) Arrays.stream(nums).boxed().distinct().count();
//    }

    void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }


    // 合并两个有序数据
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        if (m != n) {
            return;
        }
        int[] array = Stream.concat(Arrays.stream(nums1).boxed()
                        , Arrays.stream(nums2).boxed())
                .filter(num -> !Objects.equals(0, num))
                .sorted()
                .mapToInt(a -> a)
                .toArray();

        System.arraycopy(array, 0, nums1, 0, nums1.length);
//        nums1 = array;
//        for (int i : nums1) {
//            System.out.println(i);
//        }
    }

    //给你一个有序数组 nums ，请你 原地 删除重复出现的元素，使得出现次数超过两次的元素只出现两次 ，返回删除后数组的新长度。
    //不要使用额外的数组空间，你必须在 原地 修改输入数组 并在使用 O(1) 额外空间的条件下完成。
    // https://leetcode.cn/problems/remove-duplicates-from-sorted-array-ii/?envType=study-plan-v2&envId=top-interview-150
    public static int removeDuplicates(int[] nums) {
        Map<Integer, List<Integer>> map = Arrays.stream(nums).boxed().collect(Collectors.groupingBy(Integer::intValue));
        int[] array = new int[nums.length];
        int index = 0;
        for (Map.Entry<Integer, List<Integer>> entry : map.entrySet()) {
            if (entry.getValue().size() >= 2) {
                array[index] = entry.getKey();
                index++;
                array[index] = entry.getKey();
                index++;
            } else if (entry.getValue().size() == 1) {
                array[index] = entry.getKey();
                index++;
            }
        }
        System.arraycopy(array, 0, nums, 0, index + 1);
        for (int num : nums) {
            System.out.println(num);
        }
        return index + 1;
    }

    //  跳跃游戏
    //给你一个非负整数数组 nums ，你最初位于数组的 第一个下标 。数组中的每个元素代表你在该位置可以跳跃的最大长度。
    //判断你是否能够到达最后一个下标，如果可以，返回 true ；否则，返回 false 。
    // 找到0 ，如果能跨过去，就是可以跳过去。如果遇见0，往前找，看是否能跨过去
    public boolean canJump(int[] nums) {
        if (nums.length == 1) {
            return true;
        }
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0 && !check(nums, i)) {
                return false;
            }
        }
        return true;
    }

    // 跨过0 或者是 直接到终点
    private boolean check(int[] nums, int end) {
        for (int j = end - 1; j >= 0; j--) {
            if (nums[j] + j > end || (j + nums[j]) >= nums.length - 1) {
                return true;
            }
        }
        return false;
    }

    //https://leetcode.cn/problems/remove-element/?envType=study-plan-v2&envId=top-interview-150
    //给你一个数组 nums 和一个值 val，你需要 原地 移除所有数值等于 val 的元素，并返回移除后数组的新长度。
    //不要使用额外的数组空间，你必须仅使用 O(1) 额外空间并 原地 修改输入数组。
    //元素的顺序可以改变。你不需要考虑数组中超出新长度后面的元素。
    public int removeElement(int[] nums, int val) {
        int len = nums.length - 1;
        for (int i = 0; i <= len; i++) {
            if (nums[i] == val) {
                swap(nums, i--, len--);
            }
        }
        return len + 1;
    }

    // 连续子数组的最大和
    public int FindGreatestSumOfSubArray (int[] array) {
        int max = array[0];
        int sum = 0;
        for(int i=0;i<array.length;i++){
            // 每开启新的循环，需要把sum归零
            sum = 0;
            for(int j=i;j<array.length;j++){
                // 这里是求从i到j的数值和
                sum += array[j];
                // 每次比较，保存出现的最大值
                max = Math.max(max,sum);
            }
        }
        return max;
    }

    // 标号1-n的n个人首尾相接，1到3报数，报到3的退出，求最后一个人的标号
    public static int getLastPersonNumber(int n) {
        if(n < 1){
            return -1;
        }
        int[] arr = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            arr[i] = i;
        }

        int outCount = 0 ;
        int flag = 0;
        int i = 0 ;
        while (outCount != n - 1){
            if (i > n) { // i 既是下标, 也是编号
                i = 1;
            }

            // 没出去的计入计算
            if(arr[i] != 0){
                flag++;
            }
            if(flag == 3 && outCount != n - 1){
                flag = 0;
                outCount++;
                // 设为0 表示已经出去了
                arr[i] = 0;
            }else if(outCount != n - 1){
                return i;
            }
        }
        return  -1;
    }

        public static void main(String[] args) {
//        ListNode node1 = new ListNode(1);
//        ListNode node2 = new ListNode(9);
//        ListNode node3 = new ListNode(8);
//        ListNode node4 = new ListNode(4);
//        ListNode node5 = new ListNode(7);
//        ListNode node6 = new ListNode(6);
//        ListNode node7 = new ListNode(6);
//        node1.next(node2);
//        node2.next(null);
//        node3.next(node4);
//        node4.next(node5);
//        node5.next(node6);
//        node6.next(node4);

        String input = "babad";
        int result = longestPalindrome1(input);
        System.out.println("Longest Palindrome Substring: " + result);
    }
}
