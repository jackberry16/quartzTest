package com.example.quartztest.service.impl;

import com.example.quartztest.entity.QuartzJobEntity;
import com.example.quartztest.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Slf4j
public class TestServiceImpl implements TestService {

    @Resource
    private Scheduler scheduler;


    /**
     * 新增定时任务
     *
     * @param entity
     */
    public void addJob(QuartzJobEntity entity) {
        try {


            /**
             *  构建JobDetail (表示一个具体的可执行的调度程序，Job是这个可执行调度程序所要执行的内容)
             */
            JobDetail jobDetail = null;
            if (entity.getJobDataMap() == null) {
                jobDetail = JobBuilder.newJob(entity.getJobClass())
                        .withIdentity(entity.getJobName(), entity.getJobGroup())
                        .build();
            } else {
                jobDetail = JobBuilder.newJob(entity.getJobClass())
                        .withIdentity(entity.getJobName(), entity.getJobGroup())
                        .setJobData(entity.getJobDataMap())
                        .build();
            }
            //构建触发器Trigger （调度参数的配置，代表何时出发该任务)
            Long time = System.currentTimeMillis();
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(entity.getCron());
            CronTrigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(entity.getJobName(), entity.getJobGroup())
                    .startAt(new Date(time))
                    .withSchedule(scheduleBuilder)
                    .endAt(new Date(time+60000))
                    .build();

            scheduler.scheduleJob(jobDetail, trigger);
            scheduler.start();

        } catch (Exception e) {
            log.error("新增定时任务发生 异常 ----->msg:{},异常:{}", e.getMessage(), e);
        }

    }


    /**
     * 暂停定时任务
     *
     * @param jobName
     * @param jobGroup
     */
    public void pauseJob(String jobName, String jobGroup) {

        try {
            JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
            // 暂停任务
            scheduler.pauseJob(jobKey);
        } catch (SchedulerException e) {
            log.error("暂停定时任务 异常 ----->msg{}，异常{}", e.getMessage(), e);
        }

    }

    /**
     * 恢复定时任务
     *
     * @param jobName
     * @param jobGroup
     */
    public void resumeJob(String jobName, String jobGroup) {

        try {
            // 通过任务名和任务组名获取jobKey
            JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
            // 继续任务
            scheduler.resumeJob(jobKey);
        } catch (SchedulerException e) {
            log.error("恢复定时任务 异常 ----->msg{}，异常{}", e.getMessage(), e);
        }

    }


    /**
     * 删除定时任务
     */
    public void delJob(String jobName, String jobGroup) {
        try {
            // TriggerKey 定义了trigger的名称和组别 ，通过任务名和任务组名获取TriggerKey
            TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
            // 停止触发器
            scheduler.resumeTrigger(triggerKey);
            // 移除触发器
            scheduler.unscheduleJob(triggerKey);
            // 移除任务
            scheduler.deleteJob(JobKey.jobKey(jobName, jobGroup));
        } catch (SchedulerException e) {
            log.error("删除定时任务 异常 ----->msg{}，异常{}", e.getMessage(), e);
        }
    }

    /**
     * 修改定时任务
     */
    public void updateJob(QuartzJobEntity entity) {

        //先删除
        delJob(entity.getJobName(), entity.getJobGroup());

        //再创建
        addJob(entity);

    }
}
