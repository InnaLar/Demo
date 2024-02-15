package com.example.demo.spring.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class RunnerService {

    private final IPrinterService iPrinterService;

    public RunnerService(@Qualifier("customAnotherPrinterServiceImpl") final IPrinterService iPrinterService) {
        this.iPrinterService = iPrinterService;
    }

    public void run() {
        iPrinterService.print();
    }

}
