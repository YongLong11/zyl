package com.ziroom.zyl.quartz;


import com.ziroom.zyl.jpa.entity.ScheduledAnalyze;
import com.ziroom.zyl.jpa.repository.ScheduledAnalyzeRepository;
import com.ziroom.zyl.quartz.job.QuartzJob;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
@Slf4j
public class QuartzManager {

    @Resource(name = "scheduler")
    private Scheduler sched;

    /**
     * 新建一个job，每个job的名字就是一个分组
     * @param scheduledAnalyze
     * @throws SchedulerException
     */
    public void addJob(ScheduledAnalyze scheduledAnalyze) throws SchedulerException {
        //创建jobDetail实例，绑定Job实现类
        //指明job的名称，所在组的名称，以及绑定job类
        JobDetail jobDetail = JobBuilder.newJob(QuartzJob.class)
                .withIdentity(scheduledAnalyze.getJobName(), scheduledAnalyze.getJobName())//任务名称和组构成任务key
                .build();
        jobDetail.getJobDataMap().put("scheduledAnalyze", scheduledAnalyze);
        log.info("新增定时任务:{}, cron表达式:{}", scheduledAnalyze.getJobName(), scheduledAnalyze.getCron());
        //定义调度触发规则
        //使用cornTrigger规则
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(scheduledAnalyze.getJobName(), scheduledAnalyze.getJobName())//触发器key
                .startAt(DateBuilder.futureDate(1, DateBuilder.IntervalUnit.SECOND))
                .withSchedule(CronScheduleBuilder.cronSchedule(scheduledAnalyze.getCron()))
                .startNow().build();
        //把作业和触发器注册到任务调度中
        sched.scheduleJob(jobDetail, trigger);
        // 启动
        if (!sched.isShutdown()) {
            sched.start();
        }
    }

    /**
     * 修改 job
     * @param
     */
    public void updateJob(String jobName,String jobGroupName,String corn){
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobName);
            CronTrigger trigger = (CronTrigger) sched.getTrigger(triggerKey);
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey)
                    .withSchedule(CronScheduleBuilder.cronSchedule(corn))
                    .build();
            //重启触发器
            sched.rescheduleJob(triggerKey, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public void deleteJob(String jobName,String jobGroupName) throws SchedulerException {
        sched.deleteJob(new JobKey(jobName, jobGroupName));
    }

    /**
     * 暂停
     * @param jobName
     * @param jobGroupName
     * @throws SchedulerException
     */
    public void pauseJob(String jobName,String jobGroupName) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
        sched.pauseJob(jobKey);
    }

    /**
     * 恢复
     * @param jobName
     * @param jobGroupName
     * @throws SchedulerException
     */
    public void resumeJob(String jobName,String jobGroupName) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
        sched.resumeJob(jobKey);
    }

    /**
     * 立即执行一个job
     * @param jobName
     * @param jobGroupName
     */
    public void runAJobNow(String jobName,String jobGroupName){
        try {
            JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
            sched.triggerJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取所有计划中的任务列表
     * @return
     */
    public List<QuartzJobInfo> queryAllJob(){
        List<QuartzJobInfo> jobList=null;
        try {
            GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
            Set<JobKey> jobKeys = sched.getJobKeys(matcher);
            jobList = new ArrayList<>();
            for (JobKey jobKey : jobKeys) {
                List<? extends Trigger> triggers = sched.getTriggersOfJob(jobKey);
                for (Trigger trigger : triggers) {

                    QuartzJobInfo info = new QuartzJobInfo();

                    info.setJobName(jobKey.getName());
                    info.setJobGroupName(jobKey.getGroup());
                    info.setDescription(String.valueOf(trigger.getKey()));

                    Trigger.TriggerState triggerState = sched.getTriggerState(trigger.getKey());
                    info.setJobStatus(triggerState.name());

                    if (trigger instanceof CronTrigger) {
                        CronTrigger cronTrigger = (CronTrigger) trigger;
                        String cronExpression = cronTrigger.getCronExpression();
                        info.setJobTime(cronExpression);
                    }
                    jobList.add(info);
                }
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return jobList;
    }

    /**
     * 获取所有正在运行的job
     * @return
     */
    public List<QuartzJobInfo> queryRunJob(){
        List<QuartzJobInfo> jobList=null;
        try {
            List<JobExecutionContext> executingJobs = sched.getCurrentlyExecutingJobs();
            jobList = new ArrayList<>(executingJobs.size());
            for (JobExecutionContext executingJob : executingJobs) {
                Map<String,Object> map=new HashMap<String, Object>();

                QuartzJobInfo info = new QuartzJobInfo();


                JobDetail jobDetail = executingJob.getJobDetail();
                JobKey jobKey = jobDetail.getKey();
                Trigger trigger = executingJob.getTrigger();


                info.setJobName(jobKey.getName());
                info.setJobGroupName(jobKey.getGroup());
                info.setDescription(String.valueOf(trigger.getKey()));
                Trigger.TriggerState triggerState = sched.getTriggerState(trigger.getKey());
                info.setJobStatus(triggerState.name());
                if (trigger instanceof CronTrigger) {
                    CronTrigger cronTrigger = (CronTrigger) trigger;
                    String cronExpression = cronTrigger.getCronExpression();
                    info.setJobTime(cronExpression);
                }

                jobList.add(info);
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return jobList;
    }

}
