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
public class UserRs {
    private Long id;
    private String email;
    private List<DocRs> docRs;
}
