package com.ziroom.zyl.interfaces;

import org.springframework.stereotype.Service;

@Service
@BeanName(name = "AAA")
public class BInterface  extends AbstractInterface{

    public void send(){
        System.out.println("BBBBBBBB");
    }

    public String registerSelf(){
        return "B";
    }
}
