package com.zyl.arithmetrc.leetcode.everyday;

import org.apache.commons.lang3.StringUtils;

import java.util.Locale;
import java.util.Stack;

public class DecTwentySix {

    //返回 单词 顺序颠倒且 单词 之间用单个空格连接的结果字符串。
    //注意：输入字符串 s中可能会存在前导空格、尾随空格或者单词间的多个空格。返回的结果字符串中，单词间应当仅用单个空格分隔，且不包含任何额外的空格。
    public static String reverseWords(String s) {
        String[] split = s.trim().split(" ");
        Stack<String> stack = new Stack<>();
        for (String string : split) {
            if(!string.isEmpty()){
                stack.push(string.trim());
            }
        }
        StringBuilder stringBuilder = new StringBuilder();

        while (!stack.empty()){
        stringBuilder.append(stack.pop()).append(" ");
        }
        return stringBuilder.substring(0, stringBuilder.length() - 1);
    }

    public static int strStr(String haystack, String needle) {
        char[] haystackCharArray = haystack.toCharArray();
        char[] needleCharArray = needle.toCharArray();
        for (int i = 0; i <= haystackCharArray.length - needleCharArray.length; i++) {
            int start = i ;
            int needStart = 0;
            while (needStart < needleCharArray.length && haystackCharArray[start] == needleCharArray[needStart]){
                start++;
                needStart++;
            }
            if(start - i == needleCharArray.length){
                return i;
            }
        }
        return  -1;
    }

//    public boolean isPalindrome(String s) {
//
//    }
    private static int palindromeLength(String s , int left , int right){
        if(left > right || left < 0 || right >= s.length()){
            return 0;
        }
        s = s.toLowerCase(Locale.ROOT);
        int len = 0;
        while (left <= right){
            Character leftChar = getChar(s, left, true);
            Character rightChar = getChar(s, right, false);
            if(leftChar == null || rightChar == null){
                break;
            }
            if(s.charAt(len) == s.charAt(right)){
                len++;
                len++;
                right--;
            }
        }
        return len;
    }
    private static Character getChar(String s, int index, boolean isLeft){
        if(index <= 0 || index >= s.length()){
            return null;
        }
        char c = s.charAt(index);
        if((c>= 'a' && c <= 'z') || (c >= '0' && c <= '9')){
            return c;
        }
        return isLeft ? getChar(s, index--, isLeft) : getChar(s, index++, isLeft);
    }

    public static void main(String[] args) {
        System.out.println(strStr("abcdefg", "cde"));
    }
}
