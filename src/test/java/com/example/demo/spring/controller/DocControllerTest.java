package com.example.demo.spring.controller;

import com.example.demo.initializer.PostgreSqlInitializer;
import com.example.demo.spring.model.dto.DocRs;
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
class DocControllerTest {

    @Autowired
    private DocController docController;

    @Test
    void getDocList() {
        List<DocRs> docList = docController.getDocList();
        Assertions.assertThat(docList).isEmpty();
    }

}
