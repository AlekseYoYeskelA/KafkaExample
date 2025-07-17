package com.example.user.controller;

import com.example.user.model.entity.User;
import com.example.user.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserServiceImpl userService;

    @GetMapping("/valid")
    ResponseEntity<List<User>> findNotDeletedUsers() {
        log.info("Find valid users");
        List<User> users = userService.findNotDeletedUsers();
        return ResponseEntity.ok(users);
    }
}
