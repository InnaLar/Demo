package com.example.demo.spring.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class RunnerService {

    private final IprinterService iPrinterService;

    public RunnerService(@Qualifier("customAnotherPrinterServiceImpl") final IprinterService iPrinterService) {
        this.iPrinterService = iPrinterService;
    }

    public void run() {
        iPrinterService.print();
    }

}
