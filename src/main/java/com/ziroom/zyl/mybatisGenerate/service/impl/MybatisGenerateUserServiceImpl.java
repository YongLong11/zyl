package com.ziroom.zyl.mybatisGenerate.service.impl;

import com.ziroom.zyl.mybatisGenerate.dao.entity.ZylUser;
import com.ziroom.zyl.mybatisGenerate.dao.entity.ZylUserCriteria;
import com.ziroom.zyl.mybatisGenerate.mapper.ZylUserMapper;
import com.ziroom.zyl.mybatisGenerate.service.MybatisGenerateUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("mybatsi-gengeate-user-service")
public class MybatisGenerateUserServiceImpl implements MybatisGenerateUserService {

    @Resource
    ZylUserMapper zylUserMapper;

    public ZylUser getOne(Integer id){
        return zylUserMapper.selectByPrimaryKey(id);
    }
    public List<ZylUser> getUsers(Integer id){
        ZylUserCriteria criteria = new ZylUserCriteria();
        criteria.createCriteria().andIdEqualTo(id);
        return zylUserMapper.selectByFilter(criteria);
    }

}
