package com.example.depenses.web.controllers;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.depenses.dao.entities.BudgetMensuel;
import com.example.depenses.dao.entities.User;
import com.example.depenses.dao.repositories.UserRepository;
import com.example.depenses.services.budgetMensuel.BudgetMensuelService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/budget")
@RequiredArgsConstructor

public class BudgetController {

    @Autowired
    private BudgetMensuelService budgetMensuelService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/mensuel/{email}")
    public ResponseEntity<?> getBudgetMensuel(@PathVariable String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    return new RuntimeException("User not found");
                });

        Integer year = LocalDate.now().getYear();
        Integer month = LocalDate.now().getMonth().getValue();

        try {
            BudgetMensuel budget = budgetMensuelService.calculerBudgetMensuel(user, year, month);
            return ResponseEntity.ok(budget);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error calculating budget: " + e.getMessage());
        }
    }
}