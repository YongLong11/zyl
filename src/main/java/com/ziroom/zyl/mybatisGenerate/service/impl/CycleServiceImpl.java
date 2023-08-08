package com.ziroom.zyl.mybatisGenerate.service.impl;

import com.ziroom.zyl.datasource.DataSourceConstant;
import com.ziroom.zyl.datasource.TargetDataSource;
import com.ziroom.zyl.mybatisGenerate.dao.entity.Cycle;
import com.ziroom.zyl.mybatisGenerate.mapper.CycleMapper;
import com.ziroom.zyl.mybatisGenerate.service.CycleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CycleServiceImpl implements CycleService {

    @Resource
    CycleMapper cycleMapper;

    @TargetDataSource(DataSourceConstant.OKR)
    public List<Cycle> getAll(){
        return cycleMapper.selectByFilter(null);
    }

}
