package com.zyl.service.jpa;

import com.zyl.orm.jpa.entity.User;
import com.zyl.orm.jpa.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class UserServiceTest {

    @Resource(name = "jpa-user-service")
    private UserService userService;

    @Test
    void testGetUser(){
        User user = new User();
        user.setUserName("张永龙");
        System.out.println(userService.getUserAll(user));
    }
}
