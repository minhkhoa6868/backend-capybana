package com.controller;

// import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;


@Controller
public class HomeController {
    @GetMapping("/user")
    public String UserHomePage() {
        return "home";
    }

    // @GetMapping("/admin")
    // public String AdminHomePage() {
    //     return "This is admin home page";
    // }
    
}
