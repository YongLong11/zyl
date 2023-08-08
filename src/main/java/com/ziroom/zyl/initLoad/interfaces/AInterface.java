package com.ziroom.zyl.initLoad.interfaces;

import org.springframework.stereotype.Service;

@Service("interface-A")
public class AInterface extends AbstractInterface{

    public void send(){
        System.out.println("AAAAA");
    }

    public String registerSelf(){
        return "A";
    }
}
