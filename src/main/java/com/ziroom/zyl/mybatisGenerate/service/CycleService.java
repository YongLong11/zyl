package com.ziroom.zyl.mybatisGenerate.service;

import com.ziroom.zyl.mybatisGenerate.dao.entity.Cycle;

import java.util.List;

public interface CycleService {

    List<Cycle> getAll();
    int insertBatch(List<Cycle> cycles);

}
