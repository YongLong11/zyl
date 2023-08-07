package com.ziroom.zyl.initLoad.beanNameGenerator;

import org.springframework.stereotype.Service;

@Service
@BeanName(name = "AAA")
public class BInterface {

    public void send(){
        System.out.println("BBBBBBBB");
    }

    public String registerSelf(){
        return "B";
    }
}
