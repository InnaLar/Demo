package com.example.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ScheduleUserService {
    @Scheduled(fixedDelay = 3000)
    public void scheduleFixedDelayTask() {
        log.info("Fixed delay task - " + System.currentTimeMillis() / 1000);
    }
}
