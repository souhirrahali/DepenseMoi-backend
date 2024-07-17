package com.example.depenses.dao.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.depenses.dao.entities.Depense;
import com.example.depenses.dao.entities.User;

@Repository
public interface DepenseRepository extends JpaRepository<Depense, Long> {

    List<Depense> findByDateBetween(LocalDate debut, LocalDate fin);

    List<Depense> findByUser(User user);
    
    Optional<Depense> findFirstByUserOrderByDateDesc(User user);

    @Query("SELECT SUM(d.montant) FROM Depense d WHERE d.user = :user")
    Double sommeDepensesByUser(@Param("user") User user);


    Optional<Depense> findByIdAndUser(Long id, User user);

    List<Depense> findByUserAndDateBetween(User user, LocalDate debut, LocalDate fin);
    


}
