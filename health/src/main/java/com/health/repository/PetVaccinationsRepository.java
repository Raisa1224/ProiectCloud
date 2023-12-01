package com.health.repository;

import com.health.entity.PetVaccinations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetVaccinationsRepository extends JpaRepository<PetVaccinations, Integer> {

    Optional<PetVaccinations> findById(Integer id);
    @Query("SELECT p FROM PetVaccinations p WHERE p.pet.petId = :petId")
    List<PetVaccinations> findByPetId(Integer petId);
}
