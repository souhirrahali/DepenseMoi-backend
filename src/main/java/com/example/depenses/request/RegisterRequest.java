package com.example.depenses.request;



import com.example.depenses.dao.enums.Role;

import lombok.Data;

@Data
public class RegisterRequest {
    private String firstname;
    private String lastname;
    private String userEmail;
    private String password;
    private Role role;
}
