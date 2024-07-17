package com.example.depenses.web.dto;

import com.example.depenses.dao.enums.Role;
import lombok.Data;

@Data
public class UserResponseDto {
        
        private Integer id;
        private String firstname;
        private String lastname;
        private String email;
        private String password;
        private Role role;
    }