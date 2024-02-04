package com.example.demo.controller;

import com.example.demo.model.dto.DocRegistrationRq;
import com.example.demo.model.dto.DocRs;
import com.example.demo.service.DocService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/docs")
@RequiredArgsConstructor
public class DocController {
    private final DocService docService;

    @GetMapping
    public List<DocRs> getDocList() {
        return docService.findAll();
    }

    @GetMapping("{id}")
    public DocRs getDocById(@PathVariable final Long id) {
        return docService.findDocById(id);
    }

    @PostMapping
    public DocRs postDoc(@RequestBody final DocRegistrationRq docRegistrationRq) {
        return docService.postDoc(docRegistrationRq);
    }

    @DeleteMapping("{id}")
    public void deleteDocById(@PathVariable final Long id) {
        docService.deleteDoc(id);
    }

    @PutMapping
    public DocRs putDoc(@RequestBody final DocRs request) {
        return docService.putDoc(request);
    }
}
