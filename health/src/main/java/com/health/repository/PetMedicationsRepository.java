package com.health.repository;

import com.health.entity.PetMedications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetMedicationsRepository extends JpaRepository<PetMedications, Integer> {

    List<PetMedications> findAll();

    Optional<PetMedications> findById(Integer id);

    List<PetMedications> findByPetId(Integer petId);
}
