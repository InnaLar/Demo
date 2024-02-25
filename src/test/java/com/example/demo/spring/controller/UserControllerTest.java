package com.example.demo.spring.controller;

import com.example.demo.initializer.PostgreSqlInitializer;
import com.example.demo.spring.exception.ErrorCode;
import com.example.demo.spring.exception.ServiceException;
import com.example.demo.spring.model.dto.DocRegistrationRq;
import com.example.demo.spring.model.dto.DocRs;
import com.example.demo.spring.model.dto.UserDocRegistrationRq;
import com.example.demo.spring.model.dto.UserRegistrationRq;
import com.example.demo.spring.model.dto.UserRs;
import com.example.demo.spring.model.entity.Doc;
import com.example.demo.spring.model.entity.User;
import com.example.demo.spring.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = {
    PostgreSqlInitializer.class
})
class UserControllerTest {

    @Autowired
    private UserController userController;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TransactionTemplate transactionTemplate;

    @Test
    void getUserList() {
        //GIVEN
        int allUsers = 2;

        //THEN
        Assertions
            .assertThat(userController.getUserList())
            .hasSize(allUsers);
    }

    @Test
    void getUserById() {
        //GIVEN
        long existingUserId = 1L;
        //THEN
        Assertions
            .assertThat(userController.getUserById(existingUserId))
            .usingRecursiveComparison()
            .ignoringFields("id", "docList.createDate", "docList.updateDate")
            .isEqualTo(UserRs.builder()
                .email("example@mail.ru")
                .docRs(List.of())
                .build());
    }

    @Test
    void deleteUserById() {
        //GIVEN
        long shouldBeDeleted = 1L;
        //WHEN
        userController.deleteUserById(shouldBeDeleted);
        //THEN
        Assertions
            .assertThat(userRepository.findAll())
            .hasSize(1);
    }

    @Test
    void shouldThrowExceptionDeleteByWrongId() {
        //GIVEN

        //WHEN

        //THEN
        Assertions.assertThatThrownBy(() -> userController.deleteUserById(5L))
            .isInstanceOf(ServiceException.class)
            .hasMessage("User with id %s not found", 5L);
    }

    @Test
    void postUser() {
        //GIVEN
        UserRegistrationRq request = UserRegistrationRq.builder()
            .email("example@mail.ru")
            .password("321")
            .build();

        //WHEN
        UserRs userRs = userController.postUser(request);

        //THEN
        Assertions.assertThat(userRs)
            .usingRecursiveComparison()
            .ignoringFields("id")
            .isEqualTo(UserRs.builder()
                .email("example@mail.ru")
                .docRs(List.of())
                .build());

        transactionTemplate.execute((ts) ->
            Assertions.assertThat(userRepository.findAll())
                .hasSize(1)
                .first()
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
        UserDocRegistrationRq request = UserDocRegistrationRq.builder()
            .email("InnaLar@mail.ru")
            .password("343")
            .docList(List.of(
                DocRegistrationRq.builder()
                    .userId(1L)
                    .title("document1")
                    .build()
            )).build();

        //WHEN
        UserRs userRs = userController.postUserListDoc(request);

        //THEN
        Assertions.assertThat(userRs)
            .usingRecursiveComparison()
            .ignoringFields("id")
            .isEqualTo(UserRs.builder()
                .email("InnaLar@mail.ru")
                .docRs(List.of(DocRs.builder()
                    .id(1L)
                    .title("document1")
                    .userId(2L)
                    .build()))
                .build());

        Assertions.assertThat(userRepository.findAll())
            .hasSize(2)
            .filteredOn("id", 2L)
            .first()
            .usingRecursiveComparison()
            .ignoringFields("id", "docList.updateDate", "docList.createDate")
            .isEqualTo(User.builder()
                .email("InnaLar@mail.ru")
                .password("343")
                .docList(List.of(Doc.builder()
                    .id(1L)
                    .title("document1")
                    .user(userRepository.findById(2L).orElseThrow(() -> new ServiceException(ErrorCode.ERR_CODE_001, 2L)))
                    .build()))
                .build());
    }

    /*@Test
    void putUser() {


        //GIVEN
        UserRegistrationRq request = UserRegistrationRq.builder()
            .email("example@mail.ru")
            .password("321")
            .build();

        List<UserRs> userRsList = userController.getUserList();
        UserRs userRs = userController.postUser(request);

        UserShortRq userRsToPut = UserShortRq.builder()
            .email("colobocLSR@mail.ru")
            .id(1L)
            .build();

        //WHEN
        userRs = userController.putUser(userRsToPut);

        //THEN
        Assertions.assertThat(userRs)
            .isEqualTo(UserRs.builder()
                .docRs(List.of())
                .id(1L)
                .email("colobocLSR@mail.ru")
                .build());
        userController.deleteUserById(1L);
    }*/

}
