package com.ziroom.zyl.initLoad.interfaces;

import javax.annotation.PostConstruct;

public abstract class AbstractInterface implements BaseInterfaces{

    @PostConstruct
    public void register(){
        InterfaceHelper.register(this);
    }
}