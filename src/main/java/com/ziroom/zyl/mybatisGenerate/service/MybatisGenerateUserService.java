package com.ziroom.zyl.mybatisGenerate.service;

import com.ziroom.zyl.mybatisGenerate.dao.entity.ZylUser;

import java.util.List;

public interface MybatisGenerateUserService {

    ZylUser getOne(Integer id);
    List<ZylUser> getUsers(Integer id);
}
