package com.ziroom.zyl.mybatisGenerate.dao.mapper;

import com.ziroom.zyl.mybatisGenerate.dao.entity.Cycle;
import com.ziroom.zyl.mybatisGenerate.dao.entity.CycleCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CycleMapper {
    long countByFilter(CycleCriteria filter);

    int deleteByFilter(CycleCriteria filter);

    int deleteByPrimaryKey(Long id);

    int insert(Cycle record);
    int insertBatch(List<Cycle> cycles);

    int insertSelective(Cycle record);

    List<Cycle> selectByFilter(CycleCriteria filter);

    Cycle selectByPrimaryKey(Long id);

    int updateByFilterSelective(@Param("record") Cycle record, @Param("example") CycleCriteria filter);

    int updateByFilter(@Param("record") Cycle record, @Param("example") CycleCriteria filter);

    int updateByPrimaryKeySelective(Cycle record);

    int updateByPrimaryKey(Cycle record);
}