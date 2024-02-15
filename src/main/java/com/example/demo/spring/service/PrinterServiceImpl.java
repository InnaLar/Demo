package com.example.demo.spring.service;

import org.springframework.stereotype.Service;

@Service
public class PrinterServiceImpl implements IPrinterService {

    @Override
    public void print() {
        System.out.println("Hello im printer!");
    }
}
