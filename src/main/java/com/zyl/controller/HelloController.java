package com.zyl.controller;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.google.common.collect.Lists;
import com.zyl.common.Resp;
import com.zyl.common.constants.RedisConstants;
import com.zyl.common.enums.CacheEnum;
import com.zyl.pojo.AlignInfoVo;
import com.zyl.pojo.SendEmailDto;
import com.zyl.service.EasyExcelService;
import com.zyl.service.SendEmailService;
import com.zyl.utils.RedisUtils;
import com.zyl.utils.validator.ValidateField;
import com.zyl.utils.validator.ValidateGroup;
import feign.HeaderMap;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

/**
 * @ClassName：Hello
 * @Description：
 * @Author：zhangyl31@ziroom.com
 * @Data：2023/3/9 15:09
 **/
@RequestMapping("/hello")
@RestController
public class HelloController {

    @Resource
    private EasyExcelService easyExcelService;

    @Resource
    RedisUtils redisUtils;
    @Resource
    SendEmailService sendEmailService;
    @Resource
    Environment environment;

    @ValidateGroup(fields = {
            @ValidateField(index = 0, filedName = "id", notNull = true, maxVal = 5),
            @ValidateField(index = 0, filedName = "createName", notNull = false, maxLen = 5)
    })
    @PostMapping("/test/valid")
    public void testValid(@RequestBody AlignInfoVo alignInfoVo){
        System.out.println(alignInfoVo);
    }

    @GetMapping("/test/resources")
    public void testResources(@RequestParam("name") String name)throws Exception{
        System.out.println(environment.containsProperty("server.port"));
        String[] activeProfiles = environment.getActiveProfiles();
        for (String activeProfile : activeProfiles) {
            System.out.println(activeProfile);
        }
    }

    @GetMapping("/test/email")
    public void testEamil()throws Exception{
        sendEmailService.sendEmail(SendEmailDto.of().setTos(Lists.newArrayList("zhangyl31@ziroom.com")).setTitle("ghh"));
    }

    @GetMapping("/redis/set")
    public Resp redisSet(){
        redisUtils.set(RedisConstants.REDIS_TEST, 100);
        return  Resp.success();
    }

    @GetMapping("/redis/get")
    public Resp redisGet(){
        return  Resp.success(redisUtils.get(RedisConstants.REDIS_TEST));
    }

    @GetMapping("/redis/set/cache")
    public Resp redisSetFromCache(){
        Boolean aBoolean = redisUtils.setFromCacheManager(CacheEnum.REDIS_TEST, RedisConstants.REDIS_TEST_V, "hahhha");
        return  Resp.success(aBoolean);
    }

    @GetMapping("/redis/get/cache")
    public Resp redisGetFromCache(){
        Object redisCacheManager = redisUtils.getFromCacheManager(CacheEnum.REDIS_TEST, RedisConstants.REDIS_TEST_V);
        return  Resp.success(redisCacheManager);
    }
    @GetMapping("/redis/get/cache/class")
    public Resp redisGetFromCacheByClass(){
        String redisCacheManager = redisUtils.getFromCacheManager(CacheEnum.REDIS_TEST, RedisConstants.REDIS_TEST_V, String.class);
        return  Resp.success(redisCacheManager);
    }

//    @GetMapping("/world")
//    @MethodLog
    @RequestMapping(value = "world", method = RequestMethod.GET)
//    @Limiter(time = 10, count = 2)
    public Resp<String> hello1(HttpServletRequest request){
        return Resp.success("hello");
    }

    @GetMapping("/export")
//    @MethodLog
    public void hello1(HttpServletResponse response){
        easyExcelService.xssDown(response);
    }

    @GetMapping("/down")
//    @MethodLog
    public void down(HttpServletResponse response){
        try {
            easyExcelService.writeExcel(response, "test", 0, initData());
        }catch (IOException e) {
            System.out.println("错误");
        }
    }


    private List<Student> initData() {
        ArrayList<Student> students = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Student data = new Student();
            data.setName("学号" + i);
            data.setBirthday(new Date());
            data.setGender("男");
            students.add(data);
        }
        return students;
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class Student {

        @ExcelIgnore
        private String id;

        @ExcelProperty("学生姓名")
        private String name;

        @ExcelProperty("学生性别")
        private String gender;

        @ExcelProperty("学生出生日期")
        private Date birthday;
    }

}

