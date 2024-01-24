package com.example.demo;

import org.springframework.stereotype.Component;

@Component
public class PrinterService {
    public void print() {
        System.out.println("Hello im printer!");
    }
}
