package com.pet.repository;

import com.pet.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetRepository extends JpaRepository<Pet, Integer> {

    Optional<Pet> findById(Integer id);

    List<Pet> findByPetName(String name);

    @Query("SELECT p FROM Pet p WHERE p.owner.userId = :ownerId")
    List<Pet> findByOwnerId(Integer ownerId);

    @Query("SELECT p FROM Pet p WHERE p.species.species_id = :speciesId")
    List<Pet> findBySpeciesId(Integer speciesId);

    @Query("SELECT p FROM Pet p WHERE p.breed.breed_id = :breedId")
    List<Pet> findByBreedId(Integer breedId);

    @Modifying
    @Query("UPDATE Pet p SET p.petName = :pet_name, p.yearOfBirth = :year_of_birth, p.gender = :gender, p.color = :color, p.price = :price, p.available = :available WHERE p.petId = :pet_id")
    void editPetFEPAg(Integer pet_id, String pet_name, Integer year_of_birth, String gender, String color, Double price, Boolean available);
}
