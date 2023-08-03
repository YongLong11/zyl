package com.ziroom.zyl.service;

import org.springframework.stereotype.Component;

@Component
public class BeanService1 {

    public BeanService1(){
        init();
    }

    private void init(){
        System.out.println("beanService1被初始化了");
    }
}
