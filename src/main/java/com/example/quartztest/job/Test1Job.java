package com.example.quartztest.job;

import com.example.quartztest.service.JobService;
import com.mchange.lang.LongUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;

public class Test1Job implements Job {

    @Autowired
    JobService jobService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext){
        long id = Long.parseLong((String) jobExecutionContext.getJobDetail().getJobDataMap().get("id"));
        String api = jobService.getApi(id);
        System.out.println(api);
    }
}
