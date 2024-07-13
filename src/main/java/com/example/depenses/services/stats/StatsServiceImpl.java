package com.example.depenses.services.stats;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.depenses.dao.entities.Depense;
import com.example.depenses.dao.entities.Revenu;
import com.example.depenses.dao.entities.User;
import com.example.depenses.dao.repositories.DepenseRepository;
import com.example.depenses.dao.repositories.RevenuRepository;
import com.example.depenses.web.dto.DepenseDto;
import com.example.depenses.web.dto.GraphDto;
import com.example.depenses.web.dto.RevenuDto;
import com.example.depenses.web.dto.StatsDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {

    private final RevenuRepository revenuRepository;
    private final DepenseRepository depenseRepository;

    public GraphDto getChartData(User user) {
        LocalDate fin = LocalDate.now();
        LocalDate debut = fin.minusMonths(1);
        GraphDto graphDto = new GraphDto();
        
        List<DepenseDto> depenses = depenseRepository.findByUserAndDateBetween(user, debut, fin)
            .stream()
            .map(this::convertToDepenseDto)
            .collect(Collectors.toList());
        
        List<RevenuDto> revenus = revenuRepository.findByUserAndDateBetween(user, debut, fin)
            .stream()
            .map(this::convertToRevenuDto)
            .collect(Collectors.toList());
        
        graphDto.setListeDepenses(depenses);
        graphDto.setListeRevenus(revenus);
        return graphDto;
    }

    public StatsDto getStats(User user) {
        Double totalRevenu = revenuRepository.sommeRevenusByUser(user) != null ? 
            revenuRepository.sommeRevenusByUser(user) : 0.0;
        
        Double totalDepense = depenseRepository.sommeDepensesByUser(user) != null ? 
            depenseRepository.sommeDepensesByUser(user) : 0.0;
        
        Optional<Revenu> optionalRevenu = revenuRepository.findFirstByUserOrderByDateDesc(user);
        Optional<Depense> optionalDepense = depenseRepository.findFirstByUserOrderByDateDesc(user);

        StatsDto statsDto = new StatsDto();
        statsDto.setRevenu(totalRevenu);
        statsDto.setDepense(totalDepense);

        optionalDepense.ifPresent(depense -> statsDto.setLatestDepense(convertToDepenseDto(depense)));
        optionalRevenu.ifPresent(revenu -> statsDto.setLatestRevenu(convertToRevenuDto(revenu)));

        statsDto.setBalance(totalRevenu - totalDepense);

        List<Revenu> listeRevenu = revenuRepository.findByUser(user);
        List<Depense> listeDepense = depenseRepository.findByUser(user);

        OptionalDouble minDepense = listeDepense.stream().mapToDouble(Depense::getMontant).min();
        OptionalDouble maxDepense = listeDepense.stream().mapToDouble(Depense::getMontant).max();

        OptionalDouble minRevenu = listeRevenu.stream().mapToDouble(Revenu::getMontant).min();
        OptionalDouble maxRevenu = listeRevenu.stream().mapToDouble(Revenu::getMontant).max();

        statsDto.setMinDepense(minDepense.isPresent() ? minDepense.getAsDouble() : null);
        statsDto.setMaxDepense(maxDepense.isPresent() ? maxDepense.getAsDouble() : null);

        statsDto.setMinRevenu(minRevenu.isPresent() ? minRevenu.getAsDouble() : null);
        statsDto.setMaxRevenu(maxRevenu.isPresent() ? maxRevenu.getAsDouble() : null);

        return statsDto;
    }

    private DepenseDto convertToDepenseDto(Depense depense) {
        DepenseDto dto = new DepenseDto();
        dto.setId(depense.getId());  // Ajout de l'ID
        dto.setTitre(depense.getTitre());
        dto.setDescription(depense.getDescription());
        dto.setCategorie(depense.getCategorie());
        dto.setDate(depense.getDate());
        dto.setMontant(depense.getMontant());
        return dto;
    }

    private RevenuDto convertToRevenuDto(Revenu revenu) {
        RevenuDto dto = new RevenuDto();
        dto.setId(revenu.getId());  // Ajout de l'ID
        dto.setTitre(revenu.getTitre());
        dto.setDescription(revenu.getDescription());
        dto.setCategorie(revenu.getCategorie());
        dto.setDate(revenu.getDate());
        dto.setMontant(revenu.getMontant());
        return dto;
    }
}