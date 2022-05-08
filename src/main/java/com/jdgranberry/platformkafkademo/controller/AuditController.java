package com.jdgranberry.platformkafkademo.controller;

import com.jdgranberry.platformkafkademo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;


@RestController
@RequestMapping("/v1/audit")
public class AuditController {
    UserService userService;

    AuditController(@Autowired UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    ResponseEntity<ArrayList<String>> getUsersByCountry(@RequestParam String country) {
        ArrayList<String> result = userService.getUsersByCountry(country);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
