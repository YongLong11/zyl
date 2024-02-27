package com.zyl.arithmetrc.interview;

import java.util.*;

public class GaoTuAgain {


    public static List<Integer> getRept(String str){
        if(str == null | str.length() == 0){
            return null;
        }
        String[] strings = str.split("");
        Map<String, Integer> map = new HashMap<>();
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < strings.length; i++) {
            String string = strings[i];
            if(map.containsKey(string)){
                result.add(map.get(string));
                result.add(i);
            }else {
                map.put(string, i);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        String string = "abcab";
        System.out.println(getRept(string).toString());

    }


}
