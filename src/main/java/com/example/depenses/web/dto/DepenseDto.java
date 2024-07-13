package com.example.depenses.web.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class DepenseDto {
    private Long id;

    @NotNull
    private String titre;

    @NotNull
    private String description;

    @NotNull
    private String categorie;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @NotNull
    @Positive
    private Double montant;
}
