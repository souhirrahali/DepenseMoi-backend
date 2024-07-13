package com.example.depenses.web.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.depenses.dao.entities.User;
import com.example.depenses.services.revenu.RevenuService;
import com.example.depenses.services.user.UserService;
import com.example.depenses.web.dto.RevenuDto;
import com.example.depenses.web.dto.RevenuResponseDto;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/revenu")
@RequiredArgsConstructor
@CrossOrigin("*")
public class RevenuController {

    private final RevenuService revenuService;
    private final UserService userService;



    @PostMapping("add/{email}")
    public ResponseEntity<?> postRevenu(@PathVariable String email, @Valid @RequestBody RevenuDto revenuDto) {
        try {
            User user = userService.findUserByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found for email: " + email));
            RevenuResponseDto createdRevenu = revenuService.postRevenu(revenuDto, user);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdRevenu);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong: " + e.getMessage());
        }
    }


    @GetMapping("/{id}/{email}")
    public ResponseEntity<?> getRevenuById(@PathVariable Long id, @PathVariable String email) {
        try {
            User user = userService.findUserByEmail(email).orElseThrow();
            RevenuResponseDto revenu = revenuService.getRevenuByIdAndUser(id, user);
            return ResponseEntity.ok(revenu);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong: " + e.getMessage());
        }
    }



    @PutMapping("/{id}/{email}")
    public ResponseEntity<?> updateRevenu(@PathVariable Long id, @RequestBody RevenuDto revenuDto,
                                           @PathVariable String email) {
        try {
            User user = userService.findUserByEmail(email).orElseThrow();
            RevenuResponseDto updatedRevenu = revenuService.updateRevenu(id, revenuDto, user);
            return ResponseEntity.ok(updatedRevenu);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
        }
    }


    @DeleteMapping("/{id}/{email}")
    public ResponseEntity<?> deleteRevenu(@PathVariable Long id, @PathVariable String email) {
        try {
            User user = userService.findUserByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found for email: " + email));
            revenuService.deleteRevenu(id, user);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong: " + e.getMessage());
        }
    }
    @GetMapping("/all/{email}")
    public ResponseEntity<?> getRevenusByUser(@PathVariable String email) {
        try {
            User user = userService.findUserByEmail(email).orElseThrow(() -> new EntityNotFoundException("User not found"));
            List<RevenuResponseDto> revenus = revenuService.getAllRevenusByUser(user);
            return ResponseEntity.ok(revenus);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong: " + e.getMessage());
        }
    }
}
