package com.example.demo.controller;

import com.example.demo.exception.ErrorResponse;
import com.example.demo.exception.ServiceException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ServiceControllerAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(final Exception e) {
        return ResponseEntity
            .status(500)
            .body(ErrorResponse.builder()
                .code("ERR.CODE.9999")
                .message("Something went wrong: " + e.getMessage())
                .build());
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ErrorResponse> handleServiceException(final ServiceException e) {
        return ResponseEntity
            .status(e.getHttpCode())
            .body(ErrorResponse.builder()
                .code(e.getCode())
                .message(e.getMessage())
                .build());
    }

}
