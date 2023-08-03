package com.ziroom.zyl.controller;


import com.ziroom.zyl.common.Resp;
import com.ziroom.zyl.common.events.BaseEvent;
import com.ziroom.zyl.common.events.StudentsEvent;
import com.ziroom.zyl.common.events.UserEvent;
import com.ziroom.zyl.entity.Students;
import com.ziroom.zyl.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 事件发布，通过注解监听  @EventListener
 * 通过 监听者的入参判断控制进入哪个方法，所以 事件发布时要注意不能丢失参数类型
 *  @EventListener 注解有参数控制是否执行
 */
@RequestMapping("/event")
@RestController
@Slf4j
public class EventController {

    @Resource
    private ApplicationEventPublisher eventPublisher;


    @GetMapping("/test")
    public Resp eventTest(){
        User user = new User();
        user.setUserName("zhangyonglong");
        user.setAge("18");
        BaseEvent<User> produceEvent = new UserEvent(user);
        eventPublisher.publishEvent(produceEvent);
        return Resp.success();
    }
    @GetMapping("/student")
    public Resp eventTestStudent(){
        Students students = new Students();
        students.setName("hahh");
        StudentsEvent produceEvent = new StudentsEvent(students);
        eventPublisher.publishEvent(produceEvent);
        return Resp.success();
    }
}
