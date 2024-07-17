package com.example.depenses.web.dto;

import java.util.List;

import lombok.Data;

@Data
public class GraphDto {
    

    private List<DepenseDto> listeDepenses;

    private List<RevenuDto> listeRevenus;

}