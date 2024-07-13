package com.example.depenses.web.dto;

import java.util.List;
import com.example.depenses.dao.entities.Depense;
import com.example.depenses.dao.entities.Revenu;

import lombok.Data;

@Data
public class GraphDto {
    

    private List<DepenseDto> listeDepenses;

    private List<RevenuDto> listeRevenus;

}