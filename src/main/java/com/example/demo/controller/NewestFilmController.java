package com.example.demo.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class NewestFilmController {
    @GetMapping("/user/new")
    public String UserNewestFilmPage() {
        return "This is user newest film page";
    }
    
    @GetMapping("/admin/new")
    public String AdminNewestFilmPage() {
        return "This is admin newest film page";
    }
}
