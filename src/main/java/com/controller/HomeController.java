package com.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class HomeController {
    @GetMapping("/user")
    public String UserHomePage() {
        return "This is user home page";
    }

    @GetMapping("/admin")
    public String AdminHomePage() {
        return "This is admin home page";
    }
    
}
