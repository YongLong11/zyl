package com.ziroom.zyl.controller;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ziroom.zyl.aop.Retryable;
import com.ziroom.zyl.common.Resp;
import com.ziroom.zyl.common.constants.RedisConstants;
import com.ziroom.zyl.common.exception.BusinessException;
import com.ziroom.zyl.service.EasyExcelService;
import com.ziroom.zyl.utils.RedisUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName：Hello
 * @Description：
 * @Author：zhangyl31@ziroom.com
 * @Data：2023/3/9 15:09
 **/
@RequestMapping("/hello")
@RestController
public class Hello {

    @Resource
    private EasyExcelService easyExcelService;

    @Resource
    RedisUtils redisUtils;

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
        redisUtils.setFromCacheManager("redisCacheManager", RedisConstants.REDIS_TEST_V, "hahhha");
        return  Resp.success();
    }

    @GetMapping("/redis/get/cache")
    public Resp redisGetFromCache(){
        Object redisCacheManager = redisUtils.getFromCacheManager("redisCacheManager", RedisConstants.REDIS_TEST_V);
        return  Resp.success(redisCacheManager);
    }
    @GetMapping("/redis/get/cache/class")
    public Resp redisGetFromCacheByClass(){
        String redisCacheManager = redisUtils.getFromCacheManager("redisCacheManager", RedisConstants.REDIS_TEST_V, String.class);
        return  Resp.success(redisCacheManager);
    }
    @PostMapping("/hello-world")
//    @MethodLog()
//    @Limiter(time = 10, count = 2)
    @Retryable
    public Resp<String> hello(HttpServletRequest request){
        throw new BusinessException("");
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

