package com.health.repository;

import com.health.entity.PetMedications;
import com.health.entity.PetSpecialConditions;
import com.health.entity.PetVeterinaryVisits;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetSpecialConditionsRepository extends JpaRepository<PetSpecialConditions, Integer> {
    List<PetSpecialConditions> findAll();

    Optional<PetSpecialConditions> findById(Integer id);

    List<PetSpecialConditions> findByPetId(Integer petId);
}
