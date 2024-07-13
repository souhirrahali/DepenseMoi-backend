package com.example.depenses.request;

import lombok.Data;

@Data
public class LoginRequest {
    String email;
    String password;
}