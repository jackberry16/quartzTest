package com.example.quartztest.service.impl;

import com.example.quartztest.Dao.JobDao;
import com.example.quartztest.service.JobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class JobServiceImpl implements JobService {

    @Autowired
    JobDao jobDao;

    @Override
    public String getApi(Long id) {
        return jobDao.getApi(id);
    }
}
