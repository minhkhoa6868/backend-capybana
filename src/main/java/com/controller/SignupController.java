package com.controller;

import org.springframework.web.bind.annotation.RestController;

import com.model.UserInfo;
import com.repository.UserRepository;
// import com.service.UserService;

// import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/signup")
public class SignupController {

    // @Autowired
    // private UserService userService;

    @Autowired
    private UserRepository userRepository ;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // singup and save user information to database
    @PostMapping(consumes = "application/json")
    public UserInfo createUser(@RequestBody UserInfo user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
    
    // @GetMapping
    // public ResponseEntity<List<UserInfo>> getAllUser() {
    //     return ResponseEntity.status(HttpStatus.OK).body(this.userService.handleGetAllUser());
    // }
}
