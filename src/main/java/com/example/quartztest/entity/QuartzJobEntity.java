package com.example.quartztest.entity;

import lombok.Data;
import org.quartz.Job;
import org.quartz.JobDataMap;

@Data
public class QuartzJobEntity {
    //job名称
    private String jobName;

    //job组
    private String jobGroup;

    //cron 表达式
    private String cron;

    //Job 业务类
    private Class<? extends Job> jobClass;

    // job 信息
    private JobDataMap jobDataMap;


}
