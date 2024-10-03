package com.example.demo.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class AccountController {
    @GetMapping("/user/account")
    public String UserAccountPage() {
        return "This is user account page";
    }

    @GetMapping("/admin/account")
    public String AdminAccountPage() {
        return "This is admin account page";
    }
    
}
