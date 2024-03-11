package com.example.demo.spring.configuration;

import com.example.demo.spring.service.printer.AnotherPrinterServiceImpl;
import com.example.demo.spring.service.printer.IprinterService;
import com.example.demo.spring.service.printer.PrinterServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class AppConfiguration {

    @Bean(name = "printerServiceImpl")
    public IprinterService iprinterService() {
        System.out.println("PrinterServiceImpl created");
        return new PrinterServiceImpl();
    }

    @Bean
    @ConditionalOnProperty(name = "printer.service", havingValue = "true")
    public IprinterService anotherPrinterServiceImpl() {
        System.out.println("AnotherPrinterServiceImpl created");
        return new AnotherPrinterServiceImpl();
    }
}
