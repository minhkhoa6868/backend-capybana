package com.dto;

import lombok.Data;

// use for get value from clients
@Data
public class LoginDto {
    private String usernameOrEmail;
    private String password;
}
