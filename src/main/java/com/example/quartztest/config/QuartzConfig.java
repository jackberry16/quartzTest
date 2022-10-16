package com.example.quartztest.config;

import org.quartz.Scheduler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class QuartzConfig {
    // 配置文件路径
    @Resource
    private DataSource dataSource;

    /**
     *  调度器
     *
     * @return
     */
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setDataSource(dataSource);
        Properties prop = new Properties();
        //设置加载的配置文件
        factory.setConfigLocation(new ClassPathResource("/quartz.properties"));
        factory.setQuartzProperties(prop);
        //延时启动
        factory.setStartupDelay(10);
        factory.setApplicationContextSchedulerContextKey("applicationContext");
        //可选，QuartzScheduler 启动时更新己存在的Job，这样就不用每次修改targetObject后删除qrtz_job_details表对应记录了
        factory.setOverwriteExistingJobs(true);
        //设置自动启动，默认为true
        factory.setAutoStartup(true);
        return factory;
    }

    @Bean
    public Scheduler scheduler(SchedulerFactoryBean schedulerFactoryBean) {
        return schedulerFactoryBean.getScheduler();
    }
}
