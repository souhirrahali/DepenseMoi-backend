
package com.example.depenses.web.dto;

import lombok.Data;


@Data
public class StatsDto {
    
    private Double revenu;

    private Double depense;

    private RevenuDto latestRevenu;

    private DepenseDto latestDepense;

    private Double balance;

    private Double minRevenu;

    private Double maxRevenu;

    private Double minDepense;

    private Double maxDepense;

}