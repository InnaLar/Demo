package com.example.demo.spring.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DocRegistrationRq {
    private Long userId;
    private String title;
}
