package com.example.depenses.services.revenu;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.depenses.dao.entities.Revenu;
import com.example.depenses.dao.entities.User;
import com.example.depenses.dao.repositories.RevenuRepository;
import com.example.depenses.web.dto.RevenuDto;
import com.example.depenses.web.dto.RevenuResponseDto;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RevenuServiceImpl implements RevenuService {

    @Autowired
    private final RevenuRepository revenuRepository;

    @Override
    public RevenuResponseDto postRevenu(RevenuDto revenuDto, User user) {
        Revenu newRevenu = new Revenu();
        updateRevenuFromDto(newRevenu, revenuDto);
        newRevenu.setUser(user);
        Revenu savedRevenu = revenuRepository.save(newRevenu);
        return convertToDto(savedRevenu);
    }
    @Override
    public List<RevenuResponseDto> getAllRevenusByUser(User user) {
        return revenuRepository.findByUser(user)
                                .stream()
                                .map(this::convertToDto)
                                .sorted(Comparator.comparing(RevenuResponseDto::getDate).reversed())
                                .collect(Collectors.toList());
    }

    private RevenuResponseDto convertToDto(Revenu revenu) {
        RevenuResponseDto dto = new RevenuResponseDto();
        dto.setId(revenu.getId());
        dto.setTitre(revenu.getTitre());
        dto.setDescription(revenu.getDescription());
        dto.setCategorie(revenu.getCategorie());
        dto.setDate(revenu.getDate());
        dto.setMontant(revenu.getMontant());
        return dto;
    }
    @Override
    public RevenuResponseDto getRevenuByIdAndUser(Long id, User user) {
        Optional<Revenu> optionalRevenu = revenuRepository.findByIdAndUser(id, user);
        if (optionalRevenu.isPresent()) {
            return convertToDto(optionalRevenu.get());
        } else {
            throw new EntityNotFoundException("Revenu avec l'id " + id + " n'existe pas");
        }
    }

    @Override
    public RevenuResponseDto updateRevenu(Long id, RevenuDto revenuDto, User user) {
        Revenu revenu = revenuRepository.findByIdAndUser(id, user)
            .orElseThrow(() -> new EntityNotFoundException("Revenu avec l'id " + id + " n'existe pas"));
        updateRevenuFromDto(revenu, revenuDto);
        Revenu updatedRevenu = revenuRepository.save(revenu);
        return convertToDto(updatedRevenu);
    }
    
    private void updateRevenuFromDto(Revenu revenu, RevenuDto revenuDto) {
        revenu.setTitre(revenuDto.getTitre());
        revenu.setDate(revenuDto.getDate());
        revenu.setMontant(revenuDto.getMontant());
        revenu.setCategorie(revenuDto.getCategorie());
        revenu.setDescription(revenuDto.getDescription());
    }
    @Override
    public void deleteRevenu(Long id, User user) {
        Revenu revenu = revenuRepository.findByIdAndUser(id, user)
            .orElseThrow(() -> new EntityNotFoundException("Revenu avec l'id " + id + " n'existe pas"));
        revenuRepository.delete(revenu);
    }
}