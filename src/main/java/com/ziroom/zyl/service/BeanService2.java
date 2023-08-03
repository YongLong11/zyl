package com.ziroom.zyl.service;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class BeanService2 implements InitializingBean {

    public void afterPropertiesSet(){
       init();
    }
    private void init(){
        System.out.println("BeanService2被初始化了");
    }
}
