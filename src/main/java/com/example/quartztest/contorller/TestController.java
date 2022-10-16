package com.example.quartztest.contorller;

import com.example.quartztest.entity.QuartzJobEntity;
import com.example.quartztest.job.Test1Job;
import com.example.quartztest.job.TestJob;
import com.example.quartztest.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private TestService testService;

    @GetMapping("job")
    public String addJob() {
        QuartzJobEntity entity = new QuartzJobEntity();
        entity.setJobName("12");
        entity.setJobGroup("13");
        entity.setJobClass(TestJob.class);//业务实现类
        entity.setCron("*/5 * * * * ?");
        testService.addJob(entity);
        return "成功";
    }

    @GetMapping("jo1")
    public String addJob1() {
        QuartzJobEntity entity = new QuartzJobEntity();
        entity.setJobName("16");
        entity.setJobGroup("17");
        entity.setJobClass(Test1Job.class);//业务实现类
        entity.setCron("*/10 * * * * ?");
        testService.addJob(entity);
        return "成功";
    }

    @GetMapping("delJob")
    public String DelJob() {
        testService.delJob("12", "13");
        return "成功";
    }

    @GetMapping("delJob1")
    public String DelJob1() {
        testService.delJob("16", "17");
        return "成功";
    }
}
