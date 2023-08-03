package com.ziroom.zyl.common.events;

import com.ziroom.zyl.entity.User;

public class UserEvent extends BaseEvent<User>{

    public UserEvent(User user){
        super(user);
    }

}
