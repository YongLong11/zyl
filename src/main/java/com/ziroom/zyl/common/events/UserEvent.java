package com.ziroom.zyl.common.events;


import com.ziroom.zyl.mybatisPlus.entity.User;

public class UserEvent extends BaseEvent<User>{

    private User user;
    public UserEvent(User user){
        super(user);
    }

    public void setData(User user){
        this.user = user;
    }

    public User getData(){
        return this.user;
    }

}
