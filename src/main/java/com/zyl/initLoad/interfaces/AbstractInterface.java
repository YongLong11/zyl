package com.zyl.initLoad.interfaces;

public abstract class AbstractInterface implements BaseInterfaces{

//    @PostConstruct
    public void register(){
        InterfaceHelper.register(this);
    }
}
