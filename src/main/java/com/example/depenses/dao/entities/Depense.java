package com.example.depenses.dao.entities;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Depense {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String titre;

    private String description;

    private String categorie;

    private LocalDate date;

    private Double montant;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}