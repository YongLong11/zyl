package com.ziroom.zyl.listener;

import com.ziroom.zyl.common.events.StudentsEvent;
import com.ziroom.zyl.common.events.UserEvent;
import com.ziroom.zyl.entity.Students;
import com.ziroom.zyl.entity.User;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class TestEventListener {

    @EventListener
    public void run(UserEvent event){
        User user = (User) event.getSource();
        System.out.println(user.getAge());
        System.out.println(user.getUserName());
    }

    @EventListener
    public void run(StudentsEvent event){
        Students user = (Students)(event.getSource());
        System.out.println(user.getName());
    }

}
