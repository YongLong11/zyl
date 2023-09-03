//package com.ziroom.zyl.listener;
//
//import com.ziroom.zyl.common.events.UserEvent;
//import org.springframework.context.ApplicationListener;
//import org.springframework.context.event.EventListener;
//import org.springframework.stereotype.Component;
//import com.ziroom.zyl.mybatisPlus.entity.User;
//
//@Component
//public class TestEventListener implements ApplicationListener<UserEvent> {
//
//    public void onApplicationEvent(UserEvent event){
//        User data = event.getData();
//        System.out.println(data.getAge());
//        System.out.println(data.getUserName());
//    }
//
//}
