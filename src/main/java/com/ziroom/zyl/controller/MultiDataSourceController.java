package com.ziroom.zyl.controller;

import com.ziroom.zyl.common.Resp;
import com.ziroom.zyl.jpa.repository.ScheduledAnalyzeRepository;
import com.ziroom.zyl.mybatisGenerate.service.CycleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/datasource")
@Slf4j
public class MultiDataSourceController {

    @Resource
    CycleService cycleService;

    @Resource
    ScheduledAnalyzeRepository repository;

    @GetMapping("/okr")
    public Resp okr(){
        return Resp.success(cycleService.getAll());
    }

    @GetMapping("/zyl")
    public Resp zyl(){
        return Resp.success(repository.findAll());
    }
}
