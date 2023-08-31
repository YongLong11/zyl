package com.ziroom.zyl.controller;


import com.ziroom.zyl.cache.cache.PrefixKeyCache;
import com.ziroom.zyl.common.Resp;
import com.ziroom.zyl.mybatisGenerate.service.MybatisGenerateUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author mybatisPlusAutoGenerate
 * @since 2023-08-04
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    MybatisGenerateUserService mybatisGenerateUserService;
    @Resource
    PrefixKeyCache prefixKeyCache;
    @GetMapping("/test/cache")
    public Resp setCache(){

        return  Resp.success(mybatisGenerateUserService.getUsers(1));
    }

}

