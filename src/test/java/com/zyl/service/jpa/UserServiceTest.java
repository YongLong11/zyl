package com.zyl.service.jpa;

import com.zyl.jpa.entity.User;
import com.zyl.jpa.service.UserService;
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
