package com.ziroom.zyl.common.events;

import org.springframework.context.ApplicationEvent;

import java.io.Serializable;

public abstract class BaseEvent<T> extends ApplicationEvent implements Serializable {

    private static final long serialVersionUID = 2389458315090491097L;

    private T data;
//    public BaseEvent(Object source, T data){
//        super(source);
//        this.data = data;
//    }

    public BaseEvent(T data){
        super(data);
        this.data = data;
    };

    void setData(T data){
        this.data = data;
    }

    T getData(){
        return this.data;
    }

}
