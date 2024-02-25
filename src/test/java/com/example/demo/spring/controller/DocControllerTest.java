package com.example.demo.spring.controller;

import com.example.demo.spring.exception.ServiceException;
import com.example.demo.spring.model.dto.DocRegistrationRq;
import com.example.demo.spring.model.dto.DocRs;
import com.example.demo.spring.model.entity.Doc;
import com.example.demo.spring.model.entity.User;
import com.example.demo.spring.support.IntegrationTestBase;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DocControllerTest extends IntegrationTestBase {

    @Test
    void getDocList() {
        assertThat(docController.getDocList())
            .isEmpty();
    }

    @Test
    void postDoc() {
        //GIVEN
        final User user = User.builder()
            .password("321")
            .email("example@mail.ru")
            .build();
        userRepository.save(user);

        final DocRegistrationRq docRegistrationRq = DocRegistrationRq.builder()
            .title("document2")
            .userId(user.getId())
            .build();

        //WHEN
        final DocRs docRs = docController.postDoc(docRegistrationRq);

        //THEN
        assertThat(docRs)
            .usingRecursiveComparison()
            .ignoringFields("id")
            .isEqualTo(DocRs.builder()
                .userId(user.getId())
                .title("document2")
                .build());

        transactionTemplate.execute((ts) -> assertThat(docRepository.findById(1L))
            .get()
            .usingRecursiveComparison()
            .ignoringFields("createDate", "updateDate", "user")
            .isEqualTo(Doc.builder()
                .id(1L)
                .title("document2")
                .build())
        );

    }

    @Test
    void getDocById() {
        //GIVEN
        User user = User.builder()
            .email("ColobocLSR@mail.ru")
            .password("222")
            .build()
            .withDoc(Doc.builder()
                .title("document1")
                .build());
        userRepository.save(user);

        //WHEN
        DocRs docRs = docController.getDocById(1L);

        //THEN
        assertThat(docRs)
            .usingRecursiveComparison()
            .ignoringFields("userId")
            .isEqualTo(DocRs.builder()
                .id(1L)
                .title("document1")
                .build());
    }

    @Test
    void putDoc() {
        //GIVEN
        final User user = User.builder()
            .email("ColobocLSR@mail.ru")
            .password("222")
            .build()
            .withDoc(Doc.builder()
                .title("document1")
                .build());

        userRepository.save(user);

        DocRs docRs = DocRs.builder()
            .id(1L)
            .title("doc1")
            .build();

        //WHEN
        final DocRs request = docController.putDoc(docRs);

        //THEN
        assertThat(request)
            .usingRecursiveComparison()
            .ignoringFields("userId")
            .isEqualTo(DocRs.builder()
                .id(1L)
                .title("doc1")
                .build());

        assertThat(docRepository.findById(1L))
            .get()
            .usingRecursiveComparison()
            .ignoringFields("createDate", "updateDate", "user")
            .isEqualTo(Doc.builder()
                .id(1L)
                .title("doc1")
                .build());
    }

    @Test
    void deleteDocById() {
        //GIVEN
        final User user = User.builder()
            .email("ColobocLSR@mail.ru")
            .password("222")
            .build()
            .withDoc(Doc.builder()
                .title("document1")
                .build());

        userRepository.save(user);

        //WHEN
        docController.deleteDocById(1L);

        //THEN
        assertThat(docRepository.findAll())
            .isEmpty();

    }

    @Test
    void shouldThrowExceptionDeleteByWrongId() {
        assertThatThrownBy(() -> docController.deleteDocById(5L))
            .isInstanceOf(ServiceException.class)
            .hasMessage("Doc with id %s not found", 5L);
    }
}
