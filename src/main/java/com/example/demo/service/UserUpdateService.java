package com.example.demo.service;

import com.example.demo.exception.ErrorCode;
import com.example.demo.exception.ServiceException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.dto.UserRs;
import com.example.demo.model.dto.UserShortRq;
import com.example.demo.model.entity.User;
import com.example.demo.repository.UserRepository;
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
