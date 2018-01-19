package com.sbm.config;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@EnableScheduling
public class ScheduledTasks {
    private static final org.slf4j.Logger log= LoggerFactory.getLogger(ScheduledTasks.class);

    private  static final SimpleDateFormat format=new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 5000)
    public void CurrentDateTime()
    {
        log.info("The time is now {}",format.format(new Date()));
    }
    //@Scheduled(cron = "")
    public void testTask()
    {

    }

}
