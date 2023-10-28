//package com.zyl.service.mybatisGenerate;
//
//import com.alibaba.fastjson.JSONObject;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import javax.annotation.Resource;
//import java.util.List;
//
//@SpringBootTest
//public class UserSertcieTest {
//
//    @Resource()
//    MybatisGenerateUserService mybatisGenerateUserService;
//
//    @Test
//    void testUser(){
//        List<User> users = mybatisGenerateUserService.getUsers(1);
//        System.out.println(JSONObject.toJSONString(users));
//        User one = mybatisGenerateUserService.getOne(1);
//        System.out.println(JSONObject.toJSONString(one));
//    }
//
//}
