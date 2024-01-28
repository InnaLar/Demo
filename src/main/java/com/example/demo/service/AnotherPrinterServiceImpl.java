package com.example.demo.service;

import org.springframework.stereotype.Service;

@Service("customAnotherPrinterServiceImpl")
public class AnotherPrinterServiceImpl implements IPrinterService {
    @Override
    public void print() {
        System.out.println("AnotherPrinterServiceImpl Hello im AnotherPrinterServiceImpl!");
    }
}
