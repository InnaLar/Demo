package com.example.demo.mapper;

import com.example.demo.model.dto.DocRs;
import com.example.demo.model.dto.DocShortRs;
import com.example.demo.model.dto.UserRs;
import com.example.demo.model.entity.Doc;
import com.example.demo.model.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public static UserRs toUserRs(final User user) {
        UserRs userRs = UserRs.builder()
            .id(user.getId())
            .email(user.getEmail())
            .build();
        for (Doc doc : user.getDocList()) {
            userRs.getListDoc().add(DocShortRs.builder()
                .title(doc.getTitle())
                .build());
        }
        return userRs;
    }

    public static DocRs toDocRs(final Doc doc) {
        return DocRs.builder()
            .id(doc.getId())
            .title(doc.getTitle())
            .userId(doc.getUser().getId())
            .build();
    }
}
