package com.zyl.arithmetrc.nowCoder;

import java.util.*;
import java.util.function.Function;

public class JanFour {

    // 给出一个长度为 n 的，仅包含字符 '(' 和 ')' 的字符串，计算最长的格式正确的括号子串的长度。
    // https://www.nowcoder.com/practice/45fd68024a4c4e97a8d6c45fc61dc6ad?tpId=295&tqId=715&ru=%2Fexam%2Foj&qru=%2Fta%2Fformat-top101%2Fquestion-ranking&sourceUrl=%2Fexam%2Foj&dayCountBigMember=%E8%BF%9E%E7%BB%AD%E5%8C%85%E6%9C%88
    public int longestValidParentheses1 (String s) {
        int res = 0;
        //记录上一次连续括号结束的位置
        int start = -1;
        Stack<Integer> st = new Stack<Integer>();
        for(int i = 0; i < s.length(); i++){
            //左括号入栈
            if(s.charAt(i) == '(')
                st.push(i);
                //右括号
            else{
                //如果右括号时栈为空，不合法，设置为结束位置
                if(st.isEmpty())
                    start = i;
                else{
                    //弹出左括号
                    st.pop();
                    //栈中还有左括号，说明右括号不够，减去栈顶位置就是长度
                    if(!st.empty())
                        res = Math.max(res, i - st.peek());
                        //栈中没有括号，说明左右括号行号，减去上一次结束的位置就是长度
                    else
                        res = Math.max(res, i - start);
                }
            }
        }
        return res;
    }
    public static int longestValidParentheses (String s) {
        // write code here
        int returnLen = (s.length() % 2) == 0 ?  s.length() : s.length() - 1;
        Stack<String> stack = new Stack<>();
        while (returnLen > 0){
            boolean validParentheses = isValidParentheses(s, returnLen, stack);
            if(validParentheses){
                return returnLen;
            }
            returnLen = returnLen - 2;
        }
        return 0;
    }

    private static boolean isValidParentheses(String s, int stepLen, Stack<String> stack){
        int start = 0;
        int end = start + stepLen;
        while (end <= s.length()){
            boolean valid = isValid(s.substring(start, end), stack);
            if(valid){
                return true;
            }
            start++;
            end = start + stepLen;
        }
        return false;
    }

    public static boolean isValid(String s , Stack<String> leftStack) {
        leftStack.clear();
        if ((s.split("").length % 2) != 0) {
            return false;
        }
        try {
            for (String string : s.split("")) {
                if (Objects.equals(string, "(")) {
                    leftStack.push(string);
                } else {
                    String peek = leftStack.peek();
                    if (Objects.equals(peek, "(")) {
                        leftStack.pop();
                    } else  {
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


    public static void main(String[] args) {
        System.out.println(longestValidParentheses("(()"));
    }
}
