package com.example.depenses.services.user;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.depenses.dao.entities.User;
import com.example.depenses.dao.repositories.UserRepository;
import com.example.depenses.web.dto.UserDto;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    public User saveUser(User user) {
        return userRepo.save(user);
    }

    public Optional<User> findUserByEmail(String email) {

        return userRepo.findByEmail(email);

    }

    public User findUserByEmailIdAndPassword(String email, String password) {
        return userRepo.findByEmailAndPassword(email, password);
    }

    public User findByUserId(Integer userId) throws Exception {
        try {
            User user = userRepo.findByUserId(userId);
            return user;
        } catch (Exception e) {
            throw new Exception("user not found");
        }
    }

     public UserDto convertToUserDto(User user) {
        UserDto  dto = new UserDto ();
        
        dto.setFirstname(user.getFirstname());
        dto.setLastname(user.getLastname());
       
        return dto;
    }
       public void deleteUser( String email) {
        User user= userRepo.findByEmail(email)
            .orElseThrow(() -> new EntityNotFoundException("User avec l'email" +email + " n'existe pas"));
        userRepo.delete(user);
    }

}