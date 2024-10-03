package com.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class HottestFilmController {
    @GetMapping("/user/hot")
    public String UserHottestFilmPage() {
        return "This is user hottest film page";
    }
    
    @GetMapping("/admin/hot")
    public String AdminHottestFilmPage() {
        return "This is admin hottest film page";
    }
}
