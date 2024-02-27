package com.zyl.arithmetrc.interview;

import java.util.Stack;

public class ZhongRuan {
    public int solution(String s_json) {
        // Stack⾥写代码
        if(s_json == null || s_json.isEmpty()){
            return -1;
        }
        Stack<Node> stack = new Stack<>();
        String[] strs = s_json.split("");
        for(int i = 0 ; i < strs.length; i++){
            String str = strs[i];
            if(str.equals("{") || str.equals("[")){

                stack.push(new Node(str, i));
            }
//            if(str.equals("}") || str.equals("]")){
//                Node last = stack.lastElement();
//                if(str.equals("]") && last.getCurrent().equals("{")){
//                    continue;
//                }
//                if(str.equals("]") && last.getCurrent().equals("[")){
//                    continue;
//                }
//                return last.getIndex();
//
//            }
            if(str.equals("}")){
                if(stack.empty()){
                    return 0;
                }
                Node node = stack.lastElement();
                if(node.getCurrent().equals("{")){
                    continue;
                }else {
                    return node.getIndex();
                }
            }
            if(str.equals("]")){
                if(stack.empty()){
                    return 0;
                }
                Node node = stack.lastElement();
                if(node.getCurrent().equals("[")){
                    continue;
                }else {
                    return node.getIndex();
                }
            }
            }
        return -1;
    }

    class Node{
        String current;
        int index;
        public Node(String cur, int index){
            this.current = cur;
            this.index = index;
        }

        public String getCurrent() {
            return current;
        }

        public int getIndex() {
            return index;
        }
    }
}
