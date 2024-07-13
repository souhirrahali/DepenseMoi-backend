package com.example.depenses.web.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class DepenseResponseDto {
    private Long id;
    private String titre;
    private String description;
    private String categorie;
    private LocalDate date;
    private Double montant;
    
}