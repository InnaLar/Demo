package com.example.demo.service;

import com.example.demo.exception.ErrorCode;
import com.example.demo.exception.ServiceException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.dto.UserRegistrationRq;
import com.example.demo.model.dto.UserRs;
import com.example.demo.model.dto.UserShortRq;
import com.example.demo.model.entity.User;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserRs> findAll() {
        return userRepository.findAll()
            .stream()
            .map(userMapper::toUserRs)
            .toList();
    }

    public UserRs getUserById(final Long id) {
        if (id == 2) {
            //http status 400
            throw new ServiceException(ErrorCode.ERR_CODE_002, id);
        }
        return userRepository.findById(id)
            .map(userMapper::toUserRs)
            //http status 404
            .orElseThrow(() -> new ServiceException(ErrorCode.ERR_CODE_001, id));
    }

    public UserRs postUser(final UserRegistrationRq userRegistrationRq) {
        User user = User.builder()
            .email(userRegistrationRq.getEmail())
            .password(userRegistrationRq.getPassword())
            .build();
        userRepository.save(user);
        return userMapper.toUserRs(user);
    }

    public void deleteUser(final Long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public UserRs putUser(final UserShortRq request) {
        User user = userRepository.findById(request.getId())
            .orElseThrow(() -> new IllegalStateException("Can't find user by id: " + request.getId()));

        user.setEmail(request.getEmail());

        return userMapper.toUserRs(user);
    }

}
