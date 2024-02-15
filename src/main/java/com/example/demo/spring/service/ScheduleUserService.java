package com.example.demo.spring.service;

import com.example.demo.spring.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ScheduleUserService {
    //private final ScheduleUserUpdateService scheduleUserUpdateService;
    private final UserRepository userRepository;
    private final ScheduleUserService self;

    public ScheduleUserService(final UserRepository userRepository,
                               @Lazy final ScheduleUserService self) {
        this.userRepository = userRepository;
        this.self = self;
    }


    @Scheduled(fixedDelay = 3000)
    public void scheduleFixedDelayTask() {
        //scheduleUserUpdateService.updatePasswords();
        self.updatePasswords();
        //log.info("Fixed delay task - " + System.currentTimeMillis() / 1000);
        log.info("method finished succesfully");
    }

    @Transactional
    public void updatePasswords() {
        userRepository.findAll().stream()
            .map(user -> user.setPassword("888"))
            .toList();
    }
}
