package com.ziroom.zyl.interfaces;

import org.springframework.stereotype.Service;

@Service
@BeanName(name = "AAA")
public class AInterface extends AbstractInterface{

    public void send(){
        System.out.println("AAAAA");
    }

    public String registerSelf(){
        return "A";
    }
}
