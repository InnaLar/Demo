package com.example.demo.spring.controller;

import com.example.demo.initializer.PostgreSqlInitializer;
import com.example.demo.spring.model.dto.UserRegistrationRq;
import com.example.demo.spring.model.dto.UserRs;
import com.example.demo.spring.model.entity.User;
import com.example.demo.spring.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

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

        //org.hibernate.LazyInitializationException: failed to lazily initialize a collection of role:
        //com.example.demo.spring.model.entity.User.docList: could not initialize proxy - no Session

        //Подумать и погуглить про ошибку, почему она возникает, как от нее избавиться
//        Assertions.assertThat(userRepository.findAll())
//            .hasSize(1)
//            .first()
//            .usingRecursiveComparison()
//            .ignoringFields("id")
//            .isEqualTo(User.builder()
//                .email("example@mail.ru")
//                .password("321")
//                .build()
//            );
    }
}
