package com.example.spring_security_tutorial.dto;

import lombok.Data;

@Data
public class SignInRequest {
    private String username;
    private String Password;
}
