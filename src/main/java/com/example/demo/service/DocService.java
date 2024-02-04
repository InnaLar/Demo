package com.example.demo.service;

import com.example.demo.mapper.UserMapper;
import com.example.demo.model.dto.DocRegistrationRs;
import com.example.demo.model.dto.DocRs;
import com.example.demo.model.entity.Doc;
import com.example.demo.model.entity.User;
import com.example.demo.repository.DocRepository;
import com.example.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DocService {
    private final DocRepository docRepository;
    private final UserRepository userRepository;

    public List<DocRs> findAll() {
        return
            docRepository.findAll()
                .stream()
                .map(UserMapper::toDocRs)
                .toList();
    }

    public DocRs findDocById(Long id) {
        return
            docRepository.findById(id)
                .map(UserMapper::toDocRs)
                .orElseThrow(() -> new IllegalStateException("can't find docs by id: " + id));
    }

    public DocRs postDoc(final DocRegistrationRs reguest) {
        User user = userRepository.findById(reguest.getUserId())
            .orElseThrow(() -> new IllegalStateException("can't find docs by id: " + reguest.getUserId()));
        Doc doc = Doc.builder()
            .user(user)
            .title(reguest.getTitle())
            .build();
        user.withDoc(doc);
        docRepository.save(doc);
        return UserMapper.toDocRs(doc);
    }

    public void deleteDoc(final Long id) {
        docRepository.deleteById(id);
    }

    @Transactional
    public DocRs putDoc(@RequestBody DocRs request) {
        Doc doc = docRepository.findById(request.getId())
            .orElseThrow(() -> new IllegalStateException("Can't find doc by id: " + request.getId()));
        doc.setTitle(request.getTitle());
        return UserMapper.toDocRs(doc);
    }
}
