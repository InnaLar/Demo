package com.example.demo.spring.controller;

import com.example.demo.initializer.PostgreSqlInitializer;
import com.example.demo.spring.exception.ServiceException;
import com.example.demo.spring.model.dto.DocRegistrationRq;
import com.example.demo.spring.model.dto.DocRs;
import com.example.demo.spring.model.dto.UserDocRegistrationRq;
import com.example.demo.spring.model.dto.UserRegistrationRq;
import com.example.demo.spring.model.dto.UserRs;
import com.example.demo.spring.model.entity.Doc;
import com.example.demo.spring.repository.DocRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = {PostgreSqlInitializer.class})
class DocControllerTest {

    @Autowired
    private DocController docController;

    @Autowired
    private UserController userController;

    @Autowired
    private DocRepository docRepository;

    @Test
    void getDocList() {
        List<DocRs> docList = docController.getDocList();
        Assertions.assertThat(docList).isEmpty();
    }

    @Test
    void postDoc() {
        //GIVEN
        UserRegistrationRq request = UserRegistrationRq.builder().email("example@mail.ru").password("321").build();

        UserRs userRs = userController.postUser(request);

        int usersCnt = userController.getUserList().size();
        long userId = (long) (1L + Math.random() * (usersCnt));

        DocRegistrationRq docRegistrationRq = DocRegistrationRq.builder().title("document2").userId(userId).build();

        //WHEN
        DocRs docRs = docController.postDoc(docRegistrationRq);

        //THEN
        Assertions.assertThat(docRs).usingRecursiveComparison().ignoringFields("id").isEqualTo(DocRs.builder().userId(userId).title("document2").build());

        Assertions.assertThat(docRepository.findAll())
            //.hasSize(1)
            .filteredOn("title", "document2").first().usingRecursiveComparison().ignoringFields("createDate", "updateDate", "user", "id").isEqualTo(Doc.builder().id(1L).title("document2").build());

    }

    @Test
    void GetDocById() {
        //GIVEN
        int docsCnt = docController.getDocList().size();
        long docId = (long) (1L + Math.random() * (docsCnt));

        //WHEN
        DocRs docRs = docController.getDocById(docId);

        //THEN
        Assertions.assertThat(docRs).usingRecursiveComparison().ignoringFields("userId").isEqualTo(DocRs.builder().id(docId).title(docRs.getTitle()).build());
    }

    @Test
    void putDoc() {
        //GIVEN
        int docsCnt = docController.getDocList().size();
        UserDocRegistrationRq userDocRegistrationRq = UserDocRegistrationRq.builder().email("ColobocLSR@mail.ru").password("222").docList(List.of(DocRegistrationRq.builder().title("document1").build())).build();
        UserRs userRs = userController.postUserListDoc(userDocRegistrationRq);
        DocRs docRs = DocRs.builder().id(1L).title("doc1").build();

        //WHEN
        DocRs request = docController.putDoc(docRs);

        //THEN
        Assertions.assertThat(request).usingRecursiveComparison().ignoringFields("userId").isEqualTo(DocRs.builder().id(1L).title("doc1").build());
    }

    @Test
    void deleteDocById() {
        //GIVEN
        int docsCnt = docController.getDocList().size();
        long docId = (long) (1L + Math.random() * (docsCnt));

        //WHEN
        docController.deleteDocById(docId);

        //THEN
        Assertions.assertThat(docRepository.findAll()).hasSize(docsCnt - 1);
    }

    @Test
    void shouldThrowExceptionDeleteByWrongId() {
        //GIVEN

        //WHEN

        //THEN
        Assertions.assertThatThrownBy(() -> docController.deleteDocById(5L))
            .isInstanceOf(ServiceException.class)
            .hasMessage("Doc with id %s not found", 5L);
    }
}
