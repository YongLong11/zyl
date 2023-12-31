package com.zyl.controller;

import com.zyl.common.Resp;
import com.zyl.orm.jpa.repository.ScheduledAnalyzeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/datasource")
@Slf4j
public class MultiDataSourceController {

//    @Resource
//    CycleService cycleService;

    @Resource
    ScheduledAnalyzeRepository repository;

//    @GetMapping("/okr")
//    public Resp okr(){
//        return Resp.success(cycleService.getAll());
//    }

    @GetMapping("/zyl")
    public Resp zyl(){
        return Resp.success(repository.findAll());
    }
}
