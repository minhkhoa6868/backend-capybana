package com.dto;

import lombok.Data;

// use for get value from clients
@Data
public class SignupDto {
    private String username;
    private String email;
    private String password;
}
