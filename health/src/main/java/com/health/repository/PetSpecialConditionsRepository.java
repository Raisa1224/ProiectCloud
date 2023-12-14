package com.health.repository;

import com.health.entity.PetSpecialConditions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetSpecialConditionsRepository extends JpaRepository<PetSpecialConditions, Integer> {

    Optional<PetSpecialConditions> findById(Integer id);

    @Query("SELECT p FROM PetSpecialConditions p WHERE p.pet.petId = :petId")
    List<PetSpecialConditions> findByPetId(Integer petId);

    @Modifying
    @Query("Update PetSpecialConditions u set u.name = :name, u.description = :description, u.observations = :observations where u.conditionId = :id")
    void editSpecialCondition(Integer id, String name, String description, String observations);
}
