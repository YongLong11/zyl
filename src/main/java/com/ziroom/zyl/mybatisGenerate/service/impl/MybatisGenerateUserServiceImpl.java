package com.ziroom.zyl.mybatisGenerate.service.impl;

import com.ziroom.zyl.mybatisGenerate.dao.entity.User;
import com.ziroom.zyl.mybatisGenerate.dao.entity.UserCriteria;
import com.ziroom.zyl.mybatisGenerate.mapper.UserMapper;
import com.ziroom.zyl.mybatisGenerate.service.MybatisGenerateUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("mybatsi-gengeate-user-service")
public class MybatisGenerateUserServiceImpl implements MybatisGenerateUserService {

    @Resource
    UserMapper zylUserMapper;

    public User getOne(Integer id){
        return zylUserMapper.selectByPrimaryKey(id);
    }
    public List<User> getUsers(Integer id){
        UserCriteria criteria = new UserCriteria();
        criteria.createCriteria().andIdEqualTo(id);
        return zylUserMapper.selectByFilter(criteria);
    }

}
