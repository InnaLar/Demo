package com.example.demo.spring.service;

import com.example.demo.spring.exception.ErrorCode;
import com.example.demo.spring.exception.ServiceException;
import com.example.demo.spring.mapper.UserMapper;
import com.example.demo.spring.model.dto.DocRegistrationRq;
import com.example.demo.spring.model.dto.UserDocRegistrationRq;
import com.example.demo.spring.model.dto.UserRegistrationRq;
import com.example.demo.spring.model.dto.UserRs;
import com.example.demo.spring.model.dto.UserShortRq;
import com.example.demo.spring.model.entity.Doc;
import com.example.demo.spring.model.entity.User;
import com.example.demo.spring.repository.DocRepository;
import com.example.demo.spring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final DocRepository docRepository;
    private final UserMapper userMapper;
    private final UserUpdateService userUpdateService;

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

    public UserRs postUserListDoc(final UserDocRegistrationRq userDocRegistrationRq) {
        User user = User.builder()
            .email(userDocRegistrationRq.getEmail())
            .password(userDocRegistrationRq.getPassword())
            .build();
        userRepository.save(user);
        for (DocRegistrationRq docRegistrationRq : userDocRegistrationRq.getDocList()) {
            Doc doc = Doc.builder()
                .title(docRegistrationRq.getTitle())
                .user(userRepository.findById(user.getId()).orElseThrow(() -> new ServiceException(ErrorCode.ERR_CODE_001, docRegistrationRq.getUserId())))
                .build();
            docRepository.save(doc);
        }
        return userMapper.toUserRs(user);
    }

    public void deleteUser(final Long id) {
        userRepository.deleteById(id);
    }

    public UserRs putUser(final UserShortRq request) {
        return userUpdateService.putUser(request);
    }

}
