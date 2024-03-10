package com.example.demo.spring.controller;

import com.example.demo.spring.exception.ErrorCode;
import com.example.demo.spring.exception.ServiceException;
import com.example.demo.spring.model.dto.DocRegistrationRq;
import com.example.demo.spring.model.dto.DocRs;
import com.example.demo.spring.model.dto.UserDocRegistrationRq;
import com.example.demo.spring.model.dto.UserRegistrationRq;
import com.example.demo.spring.model.dto.UserRs;
import com.example.demo.spring.model.dto.UserShortRq;
import com.example.demo.spring.model.entity.Doc;
import com.example.demo.spring.model.entity.User;
import com.example.demo.spring.support.IntegrationTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserControllerTest extends IntegrationTestBase {

    @Test
    void getUserList() {
        //GIVEN
        final User user = User.builder()
            .email("InnaLarina021@gmail.ru")
            .password("123")
            .build()
            .withDoc(Doc.builder()
                .title("schema1")
                .build());
        userRepository.save(user);

        //WHEN
        final WebTestClient.ResponseSpec response = webTestClient.get()
            .uri("/api/v1/users")
            .exchange();

        //THEN
        response
            .expectStatus()
            .isOk()
            .expectBodyList(UserRs.class)
            .isEqualTo(List.of(UserRs.builder()
                .id(1L)
                .email("InnaLarina021@gmail.ru")
                .docRs(List.of(DocRs.builder()
                    .id(1L)
                    .title("schema1")
                    .userId(1L)
                    .build())).build()));
    }

    @Test
    void getUserById() {
        //GIVEN
        final User user = User.builder()
            .email("InnaLarina021@jmail.ru")
            .password("123")
            .build()
            .withDoc(Doc.builder().title("document1").build());
        userRepository.save(user);
        //WHEN
        final WebTestClient.ResponseSpec response = webTestClient.get()
            .uri(uriBuilder -> uriBuilder
                .pathSegment("api", "v1", "users", "1")
                .build())
            .exchange();

        response
            .expectStatus()
            .isOk()
            .expectBody(UserRs.class)
            .isEqualTo(UserRs.builder()
                .id(1L)
                .email("InnaLarina021@jmail.ru")
                .docRs(List.of(DocRs.builder()
                    .id(1L)
                    .title("document1")
                    .userId(1L)
                    .build()))
                .build());
    }

    @Test
    void deleteUserById() {
        //GIVEN
        final User user = User.builder()
            .email("InnaLarina021@jmail.ru")
            .password("123")
            .build()
            .withDoc(Doc.builder().title("document1").build());
        userRepository.save(user);

        //WHEN
        final WebTestClient.ResponseSpec response = webTestClient
            .delete()
            .uri(uriBuilder -> uriBuilder
                .path("api/v1/users/{id}")
                .build(1L))
            .exchange();
        //THEN
        response
            .expectStatus()
            .isOk();

        assertThat(userRepository.findAll())
            .isEmpty();
    }

    @Test
    void shouldThrowExceptionDeleteByWrongId() {
        /*Assertions.assertThatThrownBy(() -> userController.deleteUserById(5L))
            .isInstanceOf(ServiceException.class)
            .hasMessage("User with id %s not found", 5L);*/
        webTestClient
            .delete()
            .uri(uriBuilder -> uriBuilder
                .pathSegment("api", "v1", "users", "5")
                .build())
            .exchange()
            .expectStatus()
            .isEqualTo(404);

    }

    @Test
    void postUser() {
        //GIVEN
        final UserRegistrationRq request = UserRegistrationRq.builder()
            .email("example@mail.ru")
            .password("321")
            .build();

        //WHEN
        final WebTestClient.ResponseSpec response = webTestClient
            .post()
            .uri("api/v1/users/post1")
            .bodyValue(request)
            .exchange();

        //THEN
        response
            .expectStatus()
            .isOk()
            .expectBody(UserRs.class)
            .isEqualTo(UserRs.builder()
                .email("example@mail.ru")
                .id(1L)
                .docRs(List.of())
                .build());

        transactionTemplate.execute(ts ->
            assertThat(userRepository.findById(1L)
                .orElseThrow(() -> new ServiceException(ErrorCode.ERR_CODE_001, 1L)))
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(User.builder()
                    .email("example@mail.ru")
                    .password("321")
                    .build()
                ));
    }

    @Test
    void postUserListDoc() {
        //Given
        final UserDocRegistrationRq request = UserDocRegistrationRq.builder()
            .email("InnaLar@mail.ru")
            .password("343")
            .docList(List.of(
                DocRegistrationRq.builder()
                    .userId(1L)
                    .title("document1")
                    .build()
            )).build();

        //WHEN
        final WebTestClient.ResponseSpec response = webTestClient
            .post()
            .uri("api/v1/users/post2")
            .bodyValue(request)
            .exchange();

        response.expectStatus()
            .isOk()
            .expectBody(UserRs.class)
            .isEqualTo(UserRs.builder()
                .email("InnaLar@mail.ru")
                .id(1L)
                .docRs(List.of(DocRs.builder()
                    .id(1L)
                    .title("document1")
                    .userId(1L)
                    .build()))
                .build());

        transactionTemplate.execute(
            ts -> assertThat(userRepository.findById(1L)
                .orElseThrow(() -> new ServiceException(ErrorCode.ERR_CODE_001, 1L)))
                .usingRecursiveComparison()
                .ignoringFields("id", "docList.updateDate", "docList.createDate")
                .isEqualTo(User.builder()
                    .email("InnaLar@mail.ru")
                    .password("343")
                    .docList(List.of(Doc.builder()
                        .id(1L)
                        .title("document1")
                        .user(userRepository.findById(1L).orElseThrow(() -> new ServiceException(ErrorCode.ERR_CODE_001, 1L)))
                        .build()))
                    .build()));
    }

    @Test
    void putUser() {

        //GIVEN
        final User user = User.builder()
            .email("example@mail.ru")
            .password("321")
            .build();

        userRepository.save(user);

        //WHEN
        final UserShortRq userRsToPut = UserShortRq.builder()
            .email("colobocLSR@mail.ru")
            .id(1L)
            .build();
        //final UserRs userRs = userController.putUser(userRsToPut);
        final WebTestClient.ResponseSpec response = webTestClient
            .put()
            .uri("api/v1/users")
            .bodyValue(userRsToPut)
            .exchange();

        //THEN
        response
            .expectStatus()
            .isOk()
            .expectBody(UserRs.class)
            .isEqualTo(UserRs.builder()
                .id(1L)
                .email("colobocLSR@mail.ru")
                .docRs(List.of())
                .build());

        transactionTemplate.execute(ts -> assertThat(userRepository.findById(1L)
            .orElseThrow(() -> new ServiceException(ErrorCode.ERR_CODE_001, 1L)))
            .usingRecursiveComparison()
            .isEqualTo(User.builder()
                .id(1L)
                .email("colobocLSR@mail.ru")
                .password("321")
                .build()));
    }
}
