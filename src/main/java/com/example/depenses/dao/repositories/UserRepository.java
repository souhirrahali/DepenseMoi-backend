package com.example.depenses.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.depenses.dao.entities.User;
import java.util.*;


public interface UserRepository extends JpaRepository<User,Integer>{

    //public User findByUserEmail(String email);
    public User findByEmailAndPassword(String userEmail,String password);
    public User findByUserId(Integer id);
    Optional<User> findByEmail(String email);
}