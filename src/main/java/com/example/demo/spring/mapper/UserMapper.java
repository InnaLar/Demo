package com.example.demo.spring.mapper;

import com.example.demo.spring.model.dto.DocRegistrationRq;
import com.example.demo.spring.model.dto.DocRs;
import com.example.demo.spring.model.dto.UserDocRegistrationRq;
import com.example.demo.spring.model.dto.UserRs;
import com.example.demo.spring.model.entity.Doc;
import com.example.demo.spring.model.entity.User;
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

    public UserDocRegistrationRq toUserDocRegistrationRs(final User user) {
        return UserDocRegistrationRq.builder()
            .email(user.getEmail())
            .docList(user.getDocList().stream()
                .map(doc -> DocRegistrationRq.builder()
                    .title(doc.getTitle())
                    .userId(doc.getUser().getId())
                    .build()).toList())
            .build();
        /*List<DocRegistrationRq> list = user.getDocList().stream()
            .map(doc -> DocRegistrationRq.builder()
                .title(doc.getTitle())
                .userId(doc.getUser().getId())
                .build()).toList();*/

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
