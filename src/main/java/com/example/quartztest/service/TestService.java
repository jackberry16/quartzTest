package com.example.quartztest.service;

import com.example.quartztest.entity.QuartzJobEntity;

public interface TestService {
    //增加
    void addJob(QuartzJobEntity entity);

    //暂停
    void pauseJob(String jobName, String jobGroup);

    //重启
    void resumeJob(String jobName, String jobGroup);

    //删除
    void delJob(String jobName, String jobGroup);

    //修改
    void updateJob(QuartzJobEntity entity);

}
