package com.example.depenses.services.depense;

import java.util.List;

import com.example.depenses.dao.entities.User;
import com.example.depenses.web.dto.DepenseDto;
import com.example.depenses.web.dto.DepenseResponseDto;

public interface DepenseService {
    DepenseResponseDto postDepense(DepenseDto depenseDto, User user);

    List<DepenseResponseDto> getAllDepensesByUser(User user);

    DepenseResponseDto getDepenseByIdAndUser(Long id, User user);

    DepenseResponseDto updateDepense(Long id, DepenseDto depenseDto,User user);
    
    void deleteDepense(Long id, User user);
    //List<DepenseResponseDto> findDepensesByUser(User user);
}

