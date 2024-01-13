package com.pet.service;

import com.pet.entity.Pet;
import com.pet.exceptions.BreedNotFoundException;
import com.pet.exceptions.OwnerNotFoundException;
import com.pet.exceptions.PetNotFoundException;
import com.pet.exceptions.SpeciesNotFloundException;
import com.pet.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PetService implements PetServiceImpl{

    @Autowired
    private PetRepository petRepository;

    public List<Pet> getAllPets(){
        return petRepository.findAll();
    }

    public Pet getPetById(Integer petId){
        Optional<Pet> petOptional= petRepository.findById(petId);
        if (petOptional.isEmpty()) {
            throw new PetNotFoundException(String.format("Not found pet with id %s", petId));
        }
        return petOptional.get();
    }

    public Pet addPet(Pet pet){
        if (pet.getSpecies() == null) {
            throw new SpeciesNotFloundException("Species must be present");
        }
        if (pet.getBreed() == null) {
            throw new BreedNotFoundException("Breed must be present");
        }
        if (pet.getOwner() == null) {
            throw new OwnerNotFoundException("Owner must be present");
        }
        return petRepository.save(pet);
    }

    public Pet deletePet(Integer petId){
        Optional<Pet> petOptional= petRepository.findById(petId);
        if (petOptional.isEmpty()) {
            throw new PetNotFoundException(String.format("Not found pet with id %s", petId));
        }
        petRepository.deleteById(petId);
        return petOptional.get();
    }

    public Pet editPet(Integer petId, Pet newPet){
        Optional<Pet> petOptional= petRepository.findById(petId);
        if (petOptional.isEmpty()) {
            throw new PetNotFoundException(String.format("Not found pet with id %s", petId));
        }
        newPet.setPetId(petOptional.get().getPetId());
        return petRepository.save(newPet);
    }
    @Transactional
    public Pet editPetFE(Integer petId, String petName, Integer yearOfBirth, String gender, String color, Double price, Boolean available){
        Optional<Pet> petOld= petRepository.findById(petId);
        if (petOld.isEmpty()) {
            throw new PetNotFoundException(String.format("Not found pet with id %s", petId));
        }
        else{
            petRepository.editPetFEPAg(petId, petName, yearOfBirth, gender, color, price, available);
        }
        return petOld.get();
    }


    @Override
    public Page<Pet> findPaginated(Pageable pageable) {
        return petRepository.findAll(pageable);
    }

}
