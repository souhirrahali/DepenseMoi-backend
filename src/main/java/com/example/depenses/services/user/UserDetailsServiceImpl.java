package com.example.depenses.services.user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.depenses.dao.entities.User;
import com.example.depenses.dao.repositories.UserRepository;
import com.example.depenses.web.dto.UserDto;
import com.example.depenses.web.dto.UserResponseDto;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

public UserResponseDto convertToUserDto(User user) {
        UserResponseDto  dto = new UserResponseDto ();
        dto.setId(user.getUserId());
        dto.setEmail(user.getEmail());
        dto.setFirstname(user.getFirstname());
        dto.setLastname(user.getLastname());
        dto.setPassword(user.getPassword());
        dto.setRole(user.getRole());
       
        return dto;
    }
}
