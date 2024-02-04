package com.example.demo.mapper;

import com.example.demo.model.dto.DocRs;
import com.example.demo.model.dto.UserRs;
import com.example.demo.model.entity.Doc;
import com.example.demo.model.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {
    public UserRs toUserRs(final User user) {
        return UserRs.builder()
            .id(user.getId())
            .email(user.getEmail())
            .docRs(toDocRsList(user.getDocList()))
            .build();
    }

    public DocRs toDocRs(final Doc doc) {
        return DocRs.builder()
            .id(doc.getId())
            .title(doc.getTitle())
            .userId(doc.getUser().getId())
            .build();
    }

    public List<DocRs> toDocRsList(final List<Doc> docList) {
        return docList.stream()
            .map(this::toDocRs)
            .toList();
    }
}
