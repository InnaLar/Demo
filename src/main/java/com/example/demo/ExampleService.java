package com.example.demo;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExampleService {

    private final PrinterService printerService;
    private List<String> stringList;

    @PostConstruct
    void setStringList() {
        this.stringList = List.of("1", "2", "3");
    }

}
