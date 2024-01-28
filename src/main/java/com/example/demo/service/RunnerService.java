package com.example.demo.service;

import com.example.demo.service.IPrinterService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class RunnerService {

    private final IPrinterService iPrinterService;

    public RunnerService(@Qualifier("printerServiceImpl") final IPrinterService iPrinterService) {
        this.iPrinterService = iPrinterService;
    }

    public void run() {
        iPrinterService.print();
    }

}
