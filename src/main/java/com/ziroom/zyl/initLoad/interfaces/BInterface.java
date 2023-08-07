package com.ziroom.zyl.initLoad.interfaces;

import org.springframework.stereotype.Service;

@Service("interface-B")
public class BInterface extends AbstractInterface{

    public void send(){
        System.out.println("BBBBBBBB");
    }

    public String registerSelf(){
        return "B";
    }
}
