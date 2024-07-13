package com.example.depenses.services.depense;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.depenses.dao.entities.Depense;
import com.example.depenses.dao.entities.User;
import com.example.depenses.dao.repositories.DepenseRepository;
import com.example.depenses.web.dto.DepenseDto;
import com.example.depenses.web.dto.DepenseResponseDto;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class DepenseServiceImpl implements DepenseService {

   
    private final DepenseRepository depenseRepository;

    @Override
    public DepenseResponseDto postDepense(DepenseDto depenseDto, User user) {
        Depense newDepense = new Depense();
        updateDepenseFromDto(newDepense, depenseDto);
        newDepense.setUser(user);
        Depense savedDepense = depenseRepository.save(newDepense);
        return convertToDto(savedDepense);
    }
    @Override
    public List<DepenseResponseDto> getAllDepensesByUser(User user) {
        return depenseRepository.findByUser(user)
                                .stream()
                                .map(this::convertToDto)
                                .sorted(Comparator.comparing(DepenseResponseDto::getDate).reversed())
                                .collect(Collectors.toList());
    }

    private DepenseResponseDto convertToDto(Depense depense) {
        DepenseResponseDto dto = new DepenseResponseDto();
        dto.setId(depense.getId());
        dto.setTitre(depense.getTitre());
        dto.setDescription(depense.getDescription());
        dto.setCategorie(depense.getCategorie());
        dto.setDate(depense.getDate());
        dto.setMontant(depense.getMontant());
        return dto;
    }
    @Override
    public DepenseResponseDto getDepenseByIdAndUser(Long id, User user) {
        Optional<Depense> optionalDepense = depenseRepository.findByIdAndUser(id, user);
        if (optionalDepense.isPresent()) {
            return convertToDto(optionalDepense.get());
        } else {
            throw new EntityNotFoundException("Depense avec l'id " + id + " n'existe pas");
        }
    }

    @Override
    public DepenseResponseDto updateDepense(Long id, DepenseDto depenseDto, User user) {
        Depense depense = depenseRepository.findByIdAndUser(id, user)
            .orElseThrow(() -> new EntityNotFoundException("Depense avec l'id " + id + " n'existe pas"));
        updateDepenseFromDto(depense, depenseDto);
        Depense updatedDepense = depenseRepository.save(depense);
        return convertToDto(updatedDepense);
    }
    
    private void updateDepenseFromDto(Depense depense, DepenseDto depenseDto) {
        depense.setTitre(depenseDto.getTitre());
        depense.setDate(depenseDto.getDate());
        depense.setMontant(depenseDto.getMontant());
        depense.setCategorie(depenseDto.getCategorie());
        depense.setDescription(depenseDto.getDescription());
    }
    @Override
    public void deleteDepense(Long id, User user) {
        Depense depense = depenseRepository.findByIdAndUser(id, user)
            .orElseThrow(() -> new EntityNotFoundException("Depense avec l'id " + id + " n'existe pas"));
        depenseRepository.delete(depense);
    }

    /* @Override
    public List<DepenseResponseDto> findDepensesByUser(User user) {
        return depenseRepository.findByUser(user);
    } */
}
