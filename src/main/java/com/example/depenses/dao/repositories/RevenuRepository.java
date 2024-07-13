package com.example.depenses.dao.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.depenses.dao.entities.Revenu;
import com.example.depenses.dao.entities.User;

@Repository
public interface RevenuRepository  extends JpaRepository<Revenu,Long>{
    
    List<Revenu> findByDateBetween(LocalDate debut, LocalDate fin);

    List<Revenu> findByUser(User user);
    
    Optional<Revenu> findFirstByUserOrderByDateDesc(User user);
    
    
    @Query("SELECT SUM(r.montant) FROM Revenu r WHERE r.user = :user")
    Double sommeRevenusByUser(@Param("user") User user);
    

    Optional<Revenu> findByIdAndUser(Long id, User user);

    List<Revenu> findByUserAndDateBetween(User user, LocalDate debut, LocalDate fin);

    
}
