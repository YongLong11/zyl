//package com.zyl.listener;
//
//import org.springframework.context.ApplicationEvent;
//import org.springframework.context.ApplicationListener;
//import org.springframework.context.event.ApplicationEventMulticaster;
//import org.springframework.context.event.EventListener;
//import org.springframework.stereotype.Component;
//
//@Component
//public class TestEventListener implements ApplicationListener<UserEvent> {
//
//    ApplicationEventMulticaster applicationEventMulticaster;
//
//    public void onApplicationEvent(UserEvent event){
//        applicationEventMulticaster.multicastEvent();
//        User data = event.getData();
//        System.out.println(data.getAge());
//        System.out.println(data.getUserName());
//    }
//
//}
