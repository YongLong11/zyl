package com.ziroom.zyl.listener;

import com.ziroom.zyl.common.events.UserEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import com.ziroom.zyl.mybatisPlus.entity.User;

@Component
public class TestEventListener {

    @EventListener
    public void run(UserEvent event){
        User user = (User) event.getSource();
        System.out.println(user.getAge());
        System.out.println(user.getUserName());
    }

}
