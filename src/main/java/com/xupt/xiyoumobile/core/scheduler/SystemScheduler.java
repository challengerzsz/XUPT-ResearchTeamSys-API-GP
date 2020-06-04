package com.xupt.xiyoumobile.core.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author : zengshuaizhi
 * @date : 2020-06-04 14:19
 */
@Slf4j
@Component
@EnableScheduling
public class SystemScheduler {

    private SimpMessagingTemplate simpMessagingTemplate;

    @Value("${im-push.template.weeklyReport}")
    private String REMIND_WEEKLY_REPORT;

    @Autowired
    public SystemScheduler(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }


    /**
     * 每周一早上8:00
     * SpringScheduler 自动借助IM-PUSH需要完成周报
     */
    @Scheduled(cron = "0 0 8 ? * MON")
//    @Scheduled(cron = "*/5 * * * * ?")
    public void remindToCompleteWeeklyReport() {
        log.info("remindToCompleteWeeklyReport scheduler is running!");
        simpMessagingTemplate.convertAndSend("/topic/all", REMIND_WEEKLY_REPORT);
    }
}
