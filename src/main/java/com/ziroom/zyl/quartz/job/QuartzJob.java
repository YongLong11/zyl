package com.ziroom.zyl.quartz.job;

import com.alibaba.fastjson.JSONObject;
import com.ziroom.zyl.jpa.entity.ScheduledAnalyze;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;


@Component
@Slf4j
@DisallowConcurrentExecution
public class QuartzJob implements Job {
    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        ScheduledAnalyze scheduledAnalyze = (ScheduledAnalyze) arg0.getMergedJobDataMap().get("scheduledAnalyze");

        System.out.println(JSONObject.toJSONString(scheduledAnalyze));

    }

}
