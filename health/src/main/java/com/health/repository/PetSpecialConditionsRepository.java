package com.health.repository;

import com.health.entity.PetSpecialConditions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetSpecialConditionsRepository extends JpaRepository<PetSpecialConditions, Integer> {

    Optional<PetSpecialConditions> findById(Integer id);

    @Query("SELECT p FROM PetSpecialConditions p WHERE p.pet.petId = :petId")
    List<PetSpecialConditions> findByPetId(Integer petId);
}
