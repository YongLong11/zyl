package com.zyl.initLoad.beanNameGenerator;

import org.springframework.stereotype.Service;

@Service
@BeanName(name = "AAA")
public class AInterface{

    public void send(){
        System.out.println("AAAAA");
    }

    public String registerSelf(){
        return "A";
    }
}
