package com.example.depenses.jwt;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.depenses.dao.entities.User;
import com.example.depenses.dao.repositories.UserRepository;
import com.example.depenses.request.LoginRequest;

@Service
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    
    private final AuthenticationManager authenticationManager;


    
    public AuthenticationService(UserRepository repository,
                                    PasswordEncoder passwordEncoder,
                                    JwtService jwtService,
                                    AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse register(User request) {

        
        if(repository.findByEmail(request.getUsername()).isPresent()) {
            return new AuthenticationResponse("user already exist");
        }

        User user = new User();
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));


        user.setRole(request.getRole());

        user = repository.save(user);

        String token = jwtService.generatToken(user);

        return new AuthenticationResponse(token);

    }

    public AuthenticationResponse authenticate(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = repository.findByEmail(request.getEmail()).orElseThrow();
        String token = jwtService.generatToken(user);
        return new AuthenticationResponse(token);

    }
}