package com.ziroom.zyl.service.mybatisPlus;

import com.ziroom.zyl.mybatisPlus.mapper.UserMapper;
import com.ziroom.zyl.mybatisPlus.service.MybatisPlusUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class UserServiceTest {

    @Resource
    private UserMapper userMapper;

    @Resource
    MybatisPlusUserService mybatisPlusUserService;

    @Test
    void testUser(){
//        System.out.println(userMapper.selectById(1));
        System.out.println(mybatisPlusUserService.list());
    }
}
