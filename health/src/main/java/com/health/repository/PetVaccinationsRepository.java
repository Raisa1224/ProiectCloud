package com.health.repository;

import com.health.entity.PetVaccinations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface PetVaccinationsRepository extends JpaRepository<PetVaccinations, Integer> {

    Optional<PetVaccinations> findById(Integer id);
    @Query("SELECT p FROM PetVaccinations p WHERE p.pet.petId = :petId")
    List<PetVaccinations> findByPetId(Integer petId);

    @Modifying
    @Query("Update PetVaccinations u set u.name = :name, u.date = :date, u.dose = :dose, u.totalDoses = :totalDoses where u.vaccinationId = :id")
    void editVaccination(Integer id, String name, Date date, Integer dose, Integer totalDoses);
}
