package com.example.demo;

import com.example.demo.service.RunnerService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(Application.class, args);

        RunnerService runnerService = applicationContext.getBean("runnerService", RunnerService.class);
        runnerService.run();
    }

}

