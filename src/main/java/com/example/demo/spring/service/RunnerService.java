package com.example.demo.spring.service;

import com.example.demo.spring.service.printer.IprinterService;
import org.springframework.stereotype.Service;

@Service
public class RunnerService {

    private final IprinterService iPrinterService;

    public RunnerService(final IprinterService iPrinterService) {
        this.iPrinterService = iPrinterService;
    }

    public void run() {
        iPrinterService.print();
    }

}
