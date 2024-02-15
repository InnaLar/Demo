package com.example.demo.service;

import com.example.demo.exception.ErrorCode;
import com.example.demo.exception.ServiceException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.dto.DocRegistrationRq;
import com.example.demo.model.dto.DocRs;
import com.example.demo.model.entity.Doc;
import com.example.demo.model.entity.User;
import com.example.demo.repository.DocRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DocService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final DocRepository docRepository;
    private final DocUpdateService docUpdateService;

    public List<DocRs> findAll() {
        return docRepository.findAll()
            .stream()
            .map(userMapper::toDocRs)
            .toList();
    }

    public DocRs findDocById(Long id) {
        return docRepository.findById(id)
            .map(userMapper::toDocRs)
            .orElseThrow(() -> new ServiceException(ErrorCode.ERR_CODE_003, id));
    }

    public DocRs postDoc(final DocRegistrationRq reguest) {
        User user = userRepository.findById(reguest.getUserId())
            .orElseThrow(() -> new ServiceException(ErrorCode.ERR_CODE_001, reguest.getUserId()));
        Doc doc = Doc.builder()
            .user(user)
            .title(reguest.getTitle())
            .build();
        user.withDoc(doc);
        docRepository.save(doc);
        return userMapper.toDocRs(doc);
    }

    public void deleteDoc(final Long id) {
        docRepository.deleteById(id);
    }

    public DocRs putDoc(@RequestBody DocRs request) {
        //validate request
        return docUpdateService.update(request);
    }

}
