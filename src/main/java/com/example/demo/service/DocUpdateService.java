package com.example.demo.service;

import com.example.demo.mapper.UserMapper;
import com.example.demo.model.dto.DocRs;
import com.example.demo.model.entity.Doc;
import com.example.demo.repository.DocRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DocUpdateService {
    private final DocRepository docRepository;
    private final UserMapper userMapper;

    @Transactional
    public DocRs update(DocRs request) {
        Doc doc = docRepository.findById(request.getId())
            .orElseThrow(() -> new IllegalStateException("Can't find doc by id: " + request.getId()));
        doc.setTitle(request.getTitle());
        return userMapper.toDocRs(doc);
    }
}
