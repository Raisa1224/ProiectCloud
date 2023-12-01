package com.health.repository;

import com.health.entity.PetVeterinaryVisits;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetVeterinaryVisitsRepository extends JpaRepository<PetVeterinaryVisits, Integer> {

    Optional<PetVeterinaryVisits> findById(Integer id);

    @Query("SELECT p FROM PetVeterinaryVisits p WHERE p.pet.petId = :petId")
    List<PetVeterinaryVisits> findByPetId(Integer petId);
}
