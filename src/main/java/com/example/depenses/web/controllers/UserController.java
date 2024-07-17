package com.example.depenses.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.example.depenses.dao.entities.User;
import com.example.depenses.request.PasswordChangeRequest;
import com.example.depenses.services.user.UserDetailsServiceImpl;
import com.example.depenses.services.user.UserService;
import com.example.depenses.web.dto.UserDto;
import com.example.depenses.web.dto.UserResponseDto;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private UserDetailsServiceImpl userServiceImpl;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/{email}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable String email)  throws UsernameNotFoundException{
        try {
            User user=  (User) userServiceImpl.loadUserByUsername(email);
                UserResponseDto dto=userServiceImpl.convertToUserDto(user);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{email}/update")
    public ResponseEntity<UserDto> updateUser(@PathVariable String email, @RequestBody User updatedUser) {
        try {
            User existingUser = userService.findUserByEmail(email)
                    .orElseThrow(() -> new Exception("User not found"));

            existingUser.setFirstname(updatedUser.getFirstname());
            existingUser.setLastname(updatedUser.getLastname());

            User savedUser = userService.saveUser(existingUser);
            UserDto userDto = userService.convertToUserDto(savedUser);
            return ResponseEntity.ok(userDto);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{email}/delete")
    public ResponseEntity<String> deleteUser(@PathVariable String email) {
        try {
            userService.deleteUser(email);

            return ResponseEntity.ok("User account deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to delete user account");
        }
    }

    @PutMapping("/{email}/change-password")
    public ResponseEntity<String> changePassword(@PathVariable String email,
            @RequestBody PasswordChangeRequest passwordChangeRequest) {
        try {
            User user = userService.findUserByEmail(email)
                    .orElseThrow(() -> new Exception("User not found"));

            if (!passwordEncoder.matches(passwordChangeRequest.getCurrentPassword(), user.getPassword())) {
                return ResponseEntity.badRequest().body("Current password is incorrect");
            }

            if (passwordChangeRequest.getNewPassword().length() < 6) {
                return ResponseEntity.badRequest().body("New password should be at least 6 characters long");
            }

            user.setPassword(passwordEncoder.encode(passwordChangeRequest.getNewPassword()));
            userService.saveUser(user);

            return ResponseEntity.ok("Password changed successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to change password");
        }
    }
}