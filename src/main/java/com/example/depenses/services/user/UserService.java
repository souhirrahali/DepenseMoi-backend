package com.example.depenses.services.user;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.depenses.dao.entities.User;
import com.example.depenses.dao.repositories.UserRepository;

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

}