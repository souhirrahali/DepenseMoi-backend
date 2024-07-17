package com.example.depenses.dao.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BudgetMensuel {
    private double totalRevenus;
    private double totalDepenses;
    private double besoins;
    private double loisirs;
    private double epargne;
    private double montantAInvestir;
    private double totalLoisir;
    private double totalEpargne;
}