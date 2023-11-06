package com.zyl.controller;

import com.zyl.common.Resp;
import com.zyl.jpa.entity.ScheduledAnalyze;
import com.zyl.jpa.repository.ScheduledAnalyzeRepository;
import com.zyl.something.quartz.JobStatus;
import com.zyl.something.quartz.QuartzManager;
import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RequestMapping("/scheduled")
@RestController
@Slf4j
public class ScheduledController {

    @Resource
    ScheduledAnalyzeRepository repository;

    @Resource
    QuartzManager quartzManager;

    @GetMapping("/run/all")
    public Resp runScheduled(){
        List<ScheduledAnalyze> all = repository.findAll();
        all.stream().filter(job -> job.getJobStatus().equals(JobStatus.NORMAL.getStatus()))
                .forEach(task -> {
            try {
                quartzManager.addJob(task);
                log.info("添加任务成功 - taskId:【{}】, taskName:【{}】, taskCron:【{}】", task.getId(), task.getJobName(), task.getCron());
            } catch (SchedulerException e) {
                log.error("添加任务失败 - taskId:【{}】, taskName:【{}】, taskCron:【{}】", task.getId(), task.getJobName(), task.getCron());
                e.printStackTrace();
            }
        });
        return Resp.success();
    }
}
