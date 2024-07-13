package com.example.depenses.web.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.depenses.dao.entities.User;
import com.example.depenses.services.stats.StatsService;
import com.example.depenses.services.user.UserService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/stats")
@RequiredArgsConstructor
@CrossOrigin("*")
public class StatsController {

    private final StatsService statsService;
    private final UserService userService;

    @GetMapping("/chart/{email}")
    public ResponseEntity<?> getChartDetails(@PathVariable String email) {
        try {
            User user = userService.findUserByEmail(email).
                                orElseThrow(() -> new EntityNotFoundException("User not found for email: " + email));

            return ResponseEntity.ok(statsService.getChartData(user));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong: " + e.getMessage());
        }
    }
    @GetMapping("/{email}")
    public ResponseEntity<?> getStats(@PathVariable String email) {
        try {
            User user = userService.findUserByEmail(email).
            orElseThrow(() -> new EntityNotFoundException("User not found for email: " + email));
            return ResponseEntity.ok(statsService.getStats(user));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong: " + e.getMessage());
        }
    }
}
