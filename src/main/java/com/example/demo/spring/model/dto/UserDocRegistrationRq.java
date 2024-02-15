package com.example.demo.spring.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDocRegistrationRq {
    private String email;
    private String password;
    private List<DocRegistrationRq> docList;
}
