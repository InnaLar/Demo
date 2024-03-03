package com.example.demo.spring.controller;

import com.example.demo.spring.model.dto.DocRegistrationRq;
import com.example.demo.spring.model.dto.DocRs;
import com.example.demo.spring.model.entity.Doc;
import com.example.demo.spring.model.entity.User;
import com.example.demo.spring.support.IntegrationTestBase;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DocControllerTest extends IntegrationTestBase {

    @Test
    void getDocListShouldWork() {
        //GIVEN
        final User user = User.builder()
            .email("ColobocLSR@mail.ru")
            .password("222")
            .build()
            .withDoc(Doc.builder()
                .title("document1")
                .build());
        userRepository.save(user);

        webTestClient.get()
            .uri("/api/v1/docs")
            .exchange()
            .expectStatus().isOk()
            .expectBodyList(DocRs.class)
            .isEqualTo(List.of(DocRs.builder()
                .id(1L)
                .title("document1")
                .userId(user.getId())
                .build()
            ));
    }

    @Test
    void postDocShouldSuccess() {
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
        webTestClient.post()
            .uri("/api/v1/docs")
            .bodyValue(docRegistrationRq)
            .exchange()
            .expectStatus().isOk()
            .expectBody(DocRs.class)
            .isEqualTo(DocRs.builder()
                .id(1L)
                .title("document2")
                .userId(user.getId())
                .build());

        transactionTemplate.execute(ts -> assertThat(docRepository.findById(1L))
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
        final User user = User.builder()
            .email("ColobocLSR@mail.ru")
            .password("222")
            .build()
            .withDoc(Doc.builder()
                .title("document1")
                .build());
        userRepository.save(user);

        webTestClient.get()
            .uri(uriBuilder -> uriBuilder
                .path("/api/v1/docs/{id}")
                .build(1L))
            .exchange()
            .expectStatus().isOk()
            .expectBody(DocRs.class)
            .isEqualTo(DocRs.builder()
                .id(1L)
                .title("document1")
                .userId(user.getId())
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

        final DocRs docRs = DocRs.builder()
            .id(1L)
            .title("doc1")
            .build();

        //WHEN
        webTestClient.put()
            .uri("/api/v1/docs")
            .bodyValue(docRs)
            .exchange()
            .expectStatus().isOk()
            .expectBody(DocRs.class)
            .isEqualTo(DocRs.builder()
                .id(1L)
                .title("doc1")
                .userId(user.getId())
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
        webTestClient.delete()
            .uri(uriBuilder -> uriBuilder
                .path("/api/v1/docs/{id}")
                .build(1L))
            .exchange()
            .expectStatus().isOk();

        assertThat(docRepository.findAll())
            .isEmpty();
    }

    @Test
    void shouldThrowExceptionDeleteByWrongId() {
        webTestClient.delete()
            .uri(uriBuilder -> uriBuilder
                .pathSegment("api", "v1", "docs", "5")
                .build())
            .exchange()
            .expectStatus()
            .isEqualTo(404);
    }
}
