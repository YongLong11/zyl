package com.zyl.initLoad.initializingBean;

import org.springframework.beans.factory.InitializingBean;

public class Initiali implements InitializingBean {

    public void afterPropertiesSet() throws Exception{
        System.out.println("Initiali加载了");
    }
}
