package com.health.repository;

import com.health.entity.PetMedications;
import com.health.entity.PetVaccinations;
import com.health.entity.PetVeterinaryVisits;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetVaccinationsRepository extends JpaRepository<PetVaccinations, Integer> {
    List<PetVaccinations> findAll();

    Optional<PetVaccinations> findById(Integer id);

    List<PetVaccinations> findByPetId(Integer petId);
}
