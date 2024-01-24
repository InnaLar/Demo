package com.example.demo.student;

import com.example.demo.student.model.Student;
import com.example.demo.student.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class StudentConfig {
    @Bean
    CommandLineRunner commandLineRunner(
            StudentRepository repository) {
        return args -> {
            Student mariam = Student.builder()
                    .name("Mariam")
                    .email("mariam.jamal@gmail.com")
                    .dob(LocalDate.of(2000, Month.JANUARY, 5))
                    .build();
            Student alex = Student.builder()
                    .name("Alex")
                    .email("alex@gmail.com")
                    .dob(LocalDate.of(2004, Month.JANUARY, 5))
                    .build();
            repository.saveAll(List.of(mariam, alex));
        };
    }
}
