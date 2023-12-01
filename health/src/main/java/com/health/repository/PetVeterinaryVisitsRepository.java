package com.health.repository;

import com.health.entity.PetMedications;
import com.health.entity.PetVeterinaryVisits;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetVeterinaryVisitsRepository extends JpaRepository<PetVeterinaryVisits, Integer> {
    List<PetVeterinaryVisits> findAll();

    Optional<PetVeterinaryVisits> findById(Integer id);

    List<PetVeterinaryVisits> findByPetId(Integer petId);
}
