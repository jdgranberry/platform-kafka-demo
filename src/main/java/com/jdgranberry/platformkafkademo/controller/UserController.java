package com.jdgranberry.platformkafkademo.controller;

import com.jdgranberry.platformkafkademo.model.CreateUserRecord;
import com.jdgranberry.platformkafkademo.model.CreateUserRequest;
import com.jdgranberry.platformkafkademo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/user")
public class UserController {
    UserService userService;

    UserController(@Autowired UserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    ResponseEntity<CreateUserRecord> createUser(@RequestBody CreateUserRequest createUserRequest) {
        CreateUserRecord result = userService.createUser(createUserRequest);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
}
