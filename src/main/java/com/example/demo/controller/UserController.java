package com.example.demo.controller;

import com.example.demo.model.dto.UserDocRegistrationRq;
import com.example.demo.model.dto.UserRegistrationRq;
import com.example.demo.model.dto.UserRs;
import com.example.demo.model.dto.UserShortRq;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserRs> getUserList() {
        return userService.findAll();
    }

    @GetMapping("{id}")
    public UserRs getUserById(@PathVariable final Long id) {
        return userService.getUserById(id);
    }

    @PostMapping(value = "post1")
    public UserRs postUser(@RequestBody final UserRegistrationRq userRegistrationRq) {
        return userService.postUser(userRegistrationRq);
    }

    @PostMapping(value = "post2")
    public UserRs postUserListDoc(@RequestBody final UserDocRegistrationRq userDocRegistrationRq) {
        return userService.postUserListDoc(userDocRegistrationRq);
    }

    @DeleteMapping("{id}")
    public void deleteUserById(@PathVariable final Long id) {
        userService.deleteUser(id);
    }

    @PutMapping
    public UserRs putUser(@RequestBody final UserShortRq userRsToPut) {
        return userService.putUser(userRsToPut);
    }

}
