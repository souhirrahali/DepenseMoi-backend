package com.example.depenses.services.budgetMensuel;

import com.example.depenses.dao.entities.BudgetMensuel;
import com.example.depenses.dao.entities.Depense;
import com.example.depenses.dao.entities.Revenu;
import com.example.depenses.dao.entities.User;
import com.example.depenses.dao.repositories.DepenseRepository;
import com.example.depenses.dao.repositories.RevenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BudgetMensuelService {

    @Autowired
    private DepenseRepository depenseRepository;

    @Autowired
    private RevenuRepository revenuRepository;

    public BudgetMensuel calculerBudgetMensuel(User user, int year, int month) {
        Double totalRevenu = revenuRepository.sommeRevenusByUser(user) != null ?
                revenuRepository.sommeRevenusByUser(user) : 0.0;

        LocalDate debut = LocalDate.of(year, month, 1);
        LocalDate fin = debut.plusMonths(1).minusDays(1);

        List<Depense> depensesMensuelles = depenseRepository.findByUserAndDateBetween(user, debut, fin);
        List<Revenu> revenusMensuels = revenuRepository.findByUserAndDateBetween(user, debut, fin);

        double totalRevenus = revenusMensuels.stream().mapToDouble(Revenu::getMontant).sum();
        
        // Calcul du total des dépenses en excluant les catégories "loisir" et "epargne"
        double totalDepenses = depensesMensuelles.stream()
                .filter(d -> !d.getCategorie().equalsIgnoreCase("loisir")
                                    && !d.getCategorie().equalsIgnoreCase("epargne"))
                .mapToDouble(Depense::getMontant)
                .sum();

        // Calcul du total des dépenses "loisir"
        double totalLoisir = depensesMensuelles.stream()
                .filter(d -> d.getCategorie().equalsIgnoreCase("loisir"))
                .mapToDouble(Depense::getMontant)
                .sum();

        // Calcul du total des dépenses "epargne"
        double totalEpargne = depensesMensuelles.stream()
                .filter(d -> d.getCategorie().equalsIgnoreCase("epargne"))
                .mapToDouble(Depense::getMontant)
                .sum();

        Double rest = (totalRevenu - totalDepenses) - (totalRevenus - totalDepenses);

        double besoins = totalRevenus * 0.5;
        double loisirs = totalRevenus * 0.3;
        double epargne = totalRevenus * 0.2;

        double montantAInvestir = epargne + rest;

        return new BudgetMensuel(totalRevenus, totalDepenses, besoins, loisirs, epargne, montantAInvestir, totalLoisir, totalEpargne);
    }
}