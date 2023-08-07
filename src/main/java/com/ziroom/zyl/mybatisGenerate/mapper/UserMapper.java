package com.ziroom.zyl.mybatisGenerate.mapper;

import com.ziroom.zyl.mybatisGenerate.dao.entity.User;
import com.ziroom.zyl.mybatisGenerate.dao.entity.UserCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    long countByFilter(UserCriteria filter);

    int deleteByFilter(UserCriteria filter);

    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByFilter(UserCriteria filter);

    User selectByPrimaryKey(Integer id);

    int updateByFilterSelective(@Param("record") User record, @Param("example") UserCriteria filter);

    int updateByFilter(@Param("record") User record, @Param("example") UserCriteria filter);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}