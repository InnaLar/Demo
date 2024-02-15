package com.example.demo.service;

import com.example.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleUserUpdateService {
    private final UserRepository userRepository;

    @Transactional
    public void updatePasswords() {
        userRepository.findAll().stream()
            .map(user -> user.setPassword("321"))
            .toList();
    }
}
