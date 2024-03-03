package com.example.demo.spring.service;

import com.example.demo.spring.mapper.UserMapper;
import com.example.demo.spring.model.dto.DocRs;
import com.example.demo.spring.model.entity.Doc;
import com.example.demo.spring.repository.DocRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DocUpdateService {
    private final DocRepository docRepository;
    private final UserMapper userMapper;

    @Transactional
    public DocRs update(final DocRs request) {
        final Doc doc = docRepository.findById(request.getId())
            .orElseThrow(() -> new IllegalStateException("Can't find doc by id: " + request.getId()));
        doc.setTitle(request.getTitle());
        return userMapper.toDocRs(doc);
    }
}
