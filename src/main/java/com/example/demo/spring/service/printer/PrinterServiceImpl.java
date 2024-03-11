package com.example.demo.spring.service.printer;

public class PrinterServiceImpl implements IprinterService {

    @Override
    public void print() {
        System.out.println("Hello im printer!");
    }
}
