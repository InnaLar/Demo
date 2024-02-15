package com.example.demo.spring.service;

import com.example.demo.spring.exception.ErrorCode;
import com.example.demo.spring.exception.ServiceException;
import com.example.demo.spring.mapper.UserMapper;
import com.example.demo.spring.model.dto.UserRs;
import com.example.demo.spring.model.dto.UserShortRq;
import com.example.demo.spring.model.entity.User;
import com.example.demo.spring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserUpdateService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    public UserRs putUser(final UserShortRq request) {
        User user = userRepository.findById(request.getId())
            .orElseThrow(() -> new ServiceException(ErrorCode.ERR_CODE_001, request.getId()));

        user.setEmail(request.getEmail());

        return userMapper.toUserRs(user);
    }
}
