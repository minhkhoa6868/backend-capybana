package com.controller;

import com.model.User;
import com.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService uService, PasswordEncoder pEncoder) {
        this.userService = uService;
        this.passwordEncoder = pEncoder;
    }

    @PostMapping("/api/users")
    public ResponseEntity<User> postMethodName(@RequestBody User inUser) {
        String hashPass = this.passwordEncoder.encode(inUser.getPassword());
        inUser.setPassword(hashPass);
        User resUser = this.userService.handleCreateUser(inUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(resUser);
    }

}
