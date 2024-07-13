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
import com.example.depenses.services.depense.DepenseService;
import com.example.depenses.services.user.UserService;
import com.example.depenses.web.dto.DepenseDto;
import com.example.depenses.web.dto.DepenseResponseDto;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/depense")
@RequiredArgsConstructor
@CrossOrigin("*")
public class DepenseController {

    private final DepenseService depenseService;
    private final UserService userService;



    @PostMapping("add/{email}")
    public ResponseEntity<?> postDepense(@PathVariable String email, @Valid @RequestBody DepenseDto depenseDto) {
        try {
            User user = userService.findUserByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found for email: " + email));
            DepenseResponseDto createdDepense = depenseService.postDepense(depenseDto, user);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdDepense);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong: " + e.getMessage());
        }
    }
  /*   @GetMapping("/all")
    public ResponseEntity<?> getAllDepenses(@RequestParam Integer userId) {
        try {
            User user = userService.findByUserId(userId);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
            return ResponseEntity.ok(depenseService.getAllDepensesByUser(user));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong: " + e.getMessage());
        }
    } */

    @GetMapping("/{id}/{email}")
    public ResponseEntity<?> getDepenseById(@PathVariable Long id, @PathVariable String email) {
        try {
            User user = userService.findUserByEmail(email).orElseThrow();
            DepenseResponseDto depense = depenseService.getDepenseByIdAndUser(id, user);
            return ResponseEntity.ok(depense);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong: " + e.getMessage());
        }
    }



    @PutMapping("/{id}/{email}")
    public ResponseEntity<?> updateDepense(@PathVariable Long id, @RequestBody DepenseDto depenseDto,
                                           @PathVariable String email) {
        try {
            User user = userService.findUserByEmail(email).orElseThrow();
            DepenseResponseDto updatedDepense = depenseService.updateDepense(id, depenseDto, user);
            return ResponseEntity.ok(updatedDepense);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
        }
    }


    @DeleteMapping("/{id}/{email}")
    public ResponseEntity<?> deleteDepense(@PathVariable Long id, @PathVariable String email) {
        try {
            User user = userService.findUserByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found for email: " + email));
            depenseService.deleteDepense(id, user);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong: " + e.getMessage());
        }
    }
    @GetMapping("/all/{email}")
    public ResponseEntity<?> getDepensesByUser(@PathVariable String email) {
        try {
            User user = userService.findUserByEmail(email).orElseThrow(() -> new EntityNotFoundException("User not found"));
            List<DepenseResponseDto> depenses = depenseService.getAllDepensesByUser(user);
            return ResponseEntity.ok(depenses);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong: " + e.getMessage());
        }
    }
}
