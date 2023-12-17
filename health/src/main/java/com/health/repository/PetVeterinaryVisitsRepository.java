package com.health.repository;

import com.health.entity.PetVeterinaryVisits;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface PetVeterinaryVisitsRepository extends JpaRepository<PetVeterinaryVisits, Integer> {

    Optional<PetVeterinaryVisits> findById(Integer id);

    Optional<PetVeterinaryVisits> findByClinicAndCauseAndResultAndDate(String clinic, String cause, String result, Date date);

    @Query("SELECT p FROM PetVeterinaryVisits p WHERE p.pet.petId = :petId")
    List<PetVeterinaryVisits> findByPetId(Integer petId);

    @Modifying
    @Query("Update PetVeterinaryVisits u set u.clinic = :clinic, u.date = :date, u.cause = :cause, u.result = :result where u.visitId = :id")
    void editVeterinaryVisit(Integer id, String clinic, Date date, String cause, String result);
}
