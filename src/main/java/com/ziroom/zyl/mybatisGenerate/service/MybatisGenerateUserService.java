package com.ziroom.zyl.mybatisGenerate.service;

import com.ziroom.zyl.mybatisGenerate.dao.entity.User;

import java.util.List;

public interface MybatisGenerateUserService {

    User getOne(Integer id);
    List<User> getUsers(Integer id);
}
