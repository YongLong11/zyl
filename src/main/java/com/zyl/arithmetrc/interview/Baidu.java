package com.zyl.arithmetrc.interview;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Baidu {

    // 算法：1 -100，循环报数，数到4，退出，再次从1 开始。问最右剩下的一个原始坐标是多少

    public static int getIndex(int num){
        List<Integer> list = new ArrayList<>(num);
        for (int i = 0; i < num; i++) {
            list.add(i + 1);
        }
        int step = 0;
        while (list.size() > 1 ){
            Iterator<Integer> iterator = list.iterator();
            while (iterator.hasNext()){
                iterator.next();
                step++;
                if(step == 4){
                    step = 0;
                    iterator.remove();
                }
            }
        }
        return list.get(0);
    }

    public static void main(String[] args) {
        System.out.println(getIndex(100));
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 1; i <= 100; i++) {
            list.add(i);
        }

        int num;
        int flag = 0;
        // 最终只剩下1人
        while (list.size() > 1) {
            System.out.println("开始：");
            ListIterator<Integer> it = list.listIterator();
            while (it.hasNext()) {
                num = (int) it.next();
                flag++;
                if (flag == 4) {
                    it.remove();
                    System.out.println("被删除的数字："+num);
                    flag = 0;
                }
            }
        }

        System.out.println("最后剩下的人："+list.get(0));

    }
}
