package com.zyl.initLoad.preDestroy;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 执行顺序
 * @PostContruct注解对应的方法
 * 实现了InitializingBean接口的afterPropertiesSet方法
 * Beaninit-method属性对应的方法
 * @PreDestroy注解对应的方法
 * Beandestroy-method属性对应的方法
 */

@Component
public class PreDestroy {
//    @PostConstruct
    public void postConstruct(){
        System.out.println("PostConstruct");
    }

    public void init() {
        System.out.println("%%%%%%%%%%%%%%%%%%%% PreDestroy 初始化");
    }

    public void preDestroyBean() {
        System.out.println("%%%%%%%%%%%%%%%%%%%% PreDestroy 注销");
    }

    @javax.annotation.PreDestroy
    public void preDestroy(){
        System.out.println("preDestroy");
    }

    @Bean(initMethod = "init", destroyMethod = "preDestroyBean")
    public PreDestroy get(){
        return new PreDestroy();
    }
}
