package com.example.depenses.services.revenu;

import java.util.List;

import com.example.depenses.dao.entities.User;
import com.example.depenses.web.dto.RevenuDto;
import com.example.depenses.web.dto.RevenuResponseDto;

public interface RevenuService {
    RevenuResponseDto postRevenu(RevenuDto revenuDto, User user);

    List<RevenuResponseDto> getAllRevenusByUser(User user);

    RevenuResponseDto getRevenuByIdAndUser(Long id, User user);

    RevenuResponseDto updateRevenu(Long id, RevenuDto revenuDto,User user);
    
    void deleteRevenu(Long id, User user);
}
