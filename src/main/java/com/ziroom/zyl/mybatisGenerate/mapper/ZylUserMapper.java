package com.ziroom.zyl.mybatisGenerate.mapper;

import com.ziroom.zyl.mybatisGenerate.dao.entity.ZylUser;
import com.ziroom.zyl.mybatisGenerate.dao.entity.ZylUserCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ZylUserMapper {
    long countByFilter(ZylUserCriteria filter);

    int deleteByFilter(ZylUserCriteria filter);

    int deleteByPrimaryKey(Integer id);

    int insert(ZylUser record);

    int insertSelective(ZylUser record);

    List<ZylUser> selectByFilter(ZylUserCriteria filter);

    ZylUser selectByPrimaryKey(Integer id);

    int updateByFilterSelective(@Param("record") ZylUser record, @Param("example") ZylUserCriteria filter);

    int updateByFilter(@Param("record") ZylUser record, @Param("example") ZylUserCriteria filter);

    int updateByPrimaryKeySelective(ZylUser record);

    int updateByPrimaryKey(ZylUser record);
}