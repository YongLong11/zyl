package com.zyl.service.jpa;

import com.zyl.controller.HelloController;
import com.zyl.orm.jpa.entity.User;
import com.zyl.orm.jpa.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MockMvcBuilderSupport;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.annotation.Resource;

@SpringBootTest
public class UserServiceTest {

    @Resource(name = "jpa-user-service")
    private UserService userService;

    @Test
    void testGetUser(){
        MockMvc build = MockMvcBuilders.standaloneSetup(new HelloController()).build();
//        build.perform()
        User user = new User();
        user.setUserName("张永龙");
        System.out.println(userService.getUserAll(user));
    }
}
