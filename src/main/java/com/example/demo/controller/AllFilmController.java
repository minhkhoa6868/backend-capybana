package com.example.demo.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class AllFilmController {
    @GetMapping("/user/all")
    public String UserAllFilmPage() {
        return "This is user all film page";
    }

    @GetMapping("/admin/all")
    public String AdminAllFilmPage() {
        return "This is amdin all film page";
    }
}
