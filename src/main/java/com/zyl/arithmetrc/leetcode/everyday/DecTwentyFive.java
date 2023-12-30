package com.zyl.arithmetrc.leetcode.everyday;

import com.zyl.utils.ArrayUtil;

import java.util.*;

public class DecTwentyFive {


    // https://leetcode.cn/problems/remove-duplicates-from-sorted-array-ii/?envType=study-plan-v2&envId=top-interview-150
    // 删除有序数组的重复项
    public static int removeDuplicates(int[] nums) {
        int u = 0;
        int k = 2;
        for (int x : nums) {
            if (u < k || nums[u - k] != x) {
                nums[u++] = x;
            }
        }
        return u;
    }

    int process(int[] nums, int k) {
        int u = 0;
        for (int x : nums) {
            if (u < k || nums[u - k] != x) nums[u++] = x;
        }
        return u;
    }

    // 给定一个大小为 n 的数组 nums ，返回其中的多数元素。多数元素是指在数组中出现次数 大于 ⌊ n/2 ⌋ 的元素。
    //你可以假设数组是非空的，并且给定的数组总是存在多数元素。
    // https://leetcode.cn/problems/majority-element/description/?envType=study-plan-v2&envId=top-interview-150
    public int majorityElement(int[] nums) {
        return Arrays.stream(nums).boxed().sorted().skip(nums.length / 2).findFirst().get();
    }

    //给定一个整数数组 nums，将数组中的元素向右轮转 k 个位置，其中 k 是非负数。
    //输入: nums = [1,2,3,4,5,6,7], k = 3
    //输出: [5,6,7,1,2,3,4]
    // https://leetcode.cn/problems/rotate-array/description/?envType=study-plan-v2&envId=top-interview-150
    public static void rotate(int[] nums, int k) {
        int length = nums.length;
         k = k > length ? k -length : k;
        reverArray(nums, 0, nums.length - 1);
        reverArray(nums, 0, k - 1);
        reverArray(nums, k, nums.length - 1);
        for (int num : nums) {
            System.out.println(num);
        }
    }

    private static void reverArray(int[] nums, int start, int end){
        if(start >= end || nums==null || start >= nums.length || end >= nums.length){
            return;
        }
        do {
            swap(nums, start, end);
            start++;
            end--;
        }while ( start <= end)
        ;

    }
    public static void swap(int[] nums, int i, int j) {
        if(i == j || nums==null || nums.length == 0 || i >= nums.length || j >= nums.length){
            return;
        }
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

    // 给定一个长度为 n 的 0 索引整数数组 nums。初始位置为 nums[0]。
    //每个元素 nums[i] 表示从索引 i 向前跳转的最大长度。换句话说，如果你在 nums[i] 处，你可以跳转到任意 nums[i + j] 处:
    //0 <= j <= nums[i]
    //i + j < n
    //返回到达 nums[n - 1] 的最小跳跃次数。生成的测试用例可以到达 nums[n - 1]。
    // https://leetcode.cn/problems/jump-game-ii/description/?envType=study-plan-v2&envId=top-interview-150
    public static int jump(int[] nums) {
        int length = nums.length;
        int end = 0;
        int maxPosition = 0;
        int steps = 0;
        for (int i = 0; i < length - 1; i++) {
            maxPosition = Math.max(maxPosition, i + nums[i]);
            if (i == end) {
                end = maxPosition;
                steps++;
            }
        }
        return steps;

    }

    // https://leetcode.cn/problems/h-index/?envType=study-plan-v2&envId=top-interview-150
    //给你一个整数数组 citations ，其中 citations[i] 表示研究者的第 i 篇论文被引用的次数。计算并返回该研究者的 h 指数。
    //根据维基百科上 h 指数的定义：h 代表“高引用次数” ，一名科研人员的 h 指数 是指他（她）至少发表了 h 篇论文，并且 至少 有 h 篇论文被引用次数大于等于 h 。如果 h 有多种可能的值，h 指数 是其中最大的那个。
    //输入：citations = [3,0,6,1,5]
    //输出：3
    //解释：给定数组表示研究者总共有 5 篇论文，每篇论文相应的被引用了 3, 0, 6, 1, 5 次。
    //     由于研究者有 3 篇论文每篇 至少 被引用了 3 次，其余两篇论文每篇被引用 不多于 3 次，所以她的 h 指数是 3。
    public static int hIndex(int[] citations) {
        int length = citations.length;
        for (int i = length; i >= 0 ; i--) {
            int times = getTimes(citations, i);
            if(times >= i ){
                return i;
            }
        }
        return 0;
    }
    private static int getTimes(int[] nums, int num){
        int times = 0;
        for (int i : nums) {
            if(i >= num){
                times++;
            }
        }
        return times;
    }

    // https://leetcode.cn/problems/simplify-path/?envType=study-plan-v2&envId=top-interview-150
    public static String simplifyPath(String path) {
        List<String > queue = new LinkedList<>();
        for (String string : path.split("/")) {
            if(string.equals("..")){
                if(!queue.isEmpty()){
                    queue.remove(queue.size() - 1);
                }
            }
            else if(string.equals(".")){
                continue;
            }
            else if(!string.isEmpty()){
                queue.add(string);
            }
        }
        if(queue.isEmpty()){
            return "/";
        }
        String res = queue.stream().map(StringBuilder::new)
                .reduce((s1, s2) -> s1.append("/").append(s2))
                .get().toString();
        return "/" + res;
    }

    static class MinStack {
        Integer min = null;
        Stack<Integer> stack;

        public MinStack() {
            if(stack == null){
                synchronized (MinStack.class){
                    if(stack == null){
                        stack = new Stack<>();
                    }
                }
            }
        }

        public void push(int val) {
            min = min != null ? Math.min(val, min) : val;
            stack.push(val);
        }

        public void pop() {
            if(stack.empty()){
                return;
            }
            Integer pop = stack.pop();
            if(pop.equals(min)){
                setMin();
            }
        }
        private void setMin(){
            if (stack.empty()){
                this.min = null;
                return;
            }
            Stack<Integer> integers = new Stack<>();
            integers.addAll(stack);
            this.min = integers.pop();
            while (!integers.empty()){
                min = Math.min(min, integers.pop());
            }
        }

        public Integer top()  {
            if(stack == null || stack.size() - 1 < 0 ){
                return null;
            }
            return stack.get(stack.size() - 1);
        }

        public Integer getMin() {
            if(stack == null || stack.isEmpty()){
                return null;
            }
            return this.min;
        }
    }



    public static void main(String[] args) {
        MinStack minStack = new MinStack();
        minStack.push(512);
        minStack.push(-1024);
        minStack.push(-1024);
        minStack.push(512);
        minStack.pop();
        System.out.println(minStack.getMin()); minStack.pop();
        System.out.println(minStack.getMin()); minStack.pop();
        System.out.println(minStack.getMin());
    }
}

