package com.example.demo.spring.service;

import org.springframework.stereotype.Service;

@Service("customAnotherPrinterServiceImpl")
public class AnotherPrinterServiceImpl implements IPrinterService {
    @Override
    public void print() {
        System.out.println("AnotherPrinterServiceImpl Hello im AnotherPrinterServiceImpl!");
    }
}
