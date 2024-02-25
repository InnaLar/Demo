package com.example.demo.spring.service;

import com.example.demo.spring.exception.ErrorCode;
import com.example.demo.spring.exception.ServiceException;
import com.example.demo.spring.mapper.UserMapper;
import com.example.demo.spring.model.dto.DocRegistrationRq;
import com.example.demo.spring.model.dto.DocRs;
import com.example.demo.spring.model.entity.Doc;
import com.example.demo.spring.model.entity.User;
import com.example.demo.spring.repository.DocRepository;
import com.example.demo.spring.repository.UserRepository;
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
        final User user = userRepository.findById(reguest.getUserId())
            .orElseThrow(() -> new ServiceException(ErrorCode.ERR_CODE_001, reguest.getUserId()));
        final Doc doc = Doc.builder()
            .user(user)
            .title(reguest.getTitle())
            .build();

        docRepository.save(doc);

        return userMapper.toDocRs(doc);
    }

    public void deleteDoc(final Long id) {
        if (docRepository.findById(id).isEmpty()) {
            throw new ServiceException(ErrorCode.ERR_CODE_003, id);
        }
        docRepository.deleteById(id);
    }

    public DocRs putDoc(@RequestBody DocRs request) {
        //validate request
        return docUpdateService.update(request);
    }

}
