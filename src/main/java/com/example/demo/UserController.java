package com.example.demo;

import jakarta.annotation.PostConstruct;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
public class UserController {

    public static List<UserRs> USER_RS_LIST;

    @GetMapping("api/v1/users")
    public List<UserRs> getUserList() {
        return USER_RS_LIST;
    }

    @GetMapping("api/v1/users/{id}")
    public UserRs getUserById(@PathVariable Long id) {
        return USER_RS_LIST.stream()
            .filter(userRs -> userRs.getId().equals(id))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("Cant find user"));
    }

    @PostMapping("api/v1/users")
    public UserRs postUser(@RequestBody UserRegistrationRq userRegistrationRq) {
        UserRs userRs = UserRs.builder()
            .age(userRegistrationRq.getAge())
            .name(userRegistrationRq.getName())
            .id(new Random().nextLong(10000))
            .build();
        USER_RS_LIST.add(userRs);
        return userRs;
    }

    @DeleteMapping("api/v1/users/{id}")
    public List<UserRs> deleteUser(@PathVariable Long id) {
        UserRs userRsFound = USER_RS_LIST.stream()
            .filter(userRs -> userRs.getId().equals(id))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("Cant find user"));
        USER_RS_LIST.remove(userRsFound);
        return USER_RS_LIST;
    }

    @PutMapping("api/v1/users")
    public UserRs putUser(@RequestBody UserRs userRsToPut) {
        UserRs userRsFound = USER_RS_LIST.stream()
            .filter(userRs -> userRs.getId().equals(userRsToPut.getId()))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("Cant find user"));
        userRsFound.setName(userRsToPut.getName());
        userRsFound.setAge(userRsToPut.getAge());
        return userRsFound;
    }

    @PostConstruct
    void postConstruct() {
        USER_RS_LIST = new ArrayList<>();
        USER_RS_LIST.add(UserRs.builder()
            .id(1L)
            .name("111")
            .age(15)
            .build());
        USER_RS_LIST.add(UserRs.builder()
            .id(2L)
            .name("222")
            .age(30)
            .build());
        USER_RS_LIST.add(UserRs.builder()
            .id(3L)
            .name("333")
            .age(45)
            .build());

    }
}
