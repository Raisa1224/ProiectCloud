package com.health.repository;

import com.health.entity.PetMedications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetMedicationsRepository extends JpaRepository<PetMedications, Integer> {

    Optional<PetMedications> findById(Integer id);


    @Query("SELECT p FROM PetMedications p WHERE p.pet.petId = :petId")
    List<PetMedications> findByPetId(Integer petId);

    @Modifying
    @Query("Update PetMedications u set u.dosage = :dosage, u.frequencyDays = :frequencyDays, u.name = :name, u.reason = :reason, u.observations = :observations where u.medicationId = :id")
    void editMedication(Integer id, Integer dosage, Integer frequencyDays, String name, String reason, String observations);
}
