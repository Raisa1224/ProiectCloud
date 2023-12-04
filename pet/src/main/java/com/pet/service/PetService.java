package com.pet.service;

import com.pet.entity.Pet;
import com.pet.exceptions.BreedNotFoundException;
import com.pet.exceptions.OwnerNotFoundException;
import com.pet.exceptions.PetNotFoundException;
import com.pet.exceptions.SpeciesNotFloundException;
import com.pet.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;

    public List<Pet> getAllPets(){
        List<Pet> pets = petRepository.findAll();
        if(pets == null){
            pets = new ArrayList<>();
        }
        return pets;
    }

    public List<Pet> getPetsByName(String petName){
        List<Pet> pets = petRepository.findByPetName(petName);
        if(pets == null){
            pets = new ArrayList<>();
        }
        return pets;
    }

    public List<Pet> getPetsByOwner(Integer ownerId){
        List<Pet> pets = petRepository.findByOwnerId(ownerId);
        if(pets == null){
            pets = new ArrayList<>();
        }
        return pets;
    }

    public List<Pet> getPetsBySpecies(Integer speciesId){
        List<Pet> pets = petRepository.findBySpeciesId(speciesId);
        if(pets == null){
            pets = new ArrayList<>();
        }
        return pets;
    }

    public List<Pet> getPetsByBreed(Integer breedId){
        List<Pet> pets = petRepository.findByBreedId(breedId);
        if(pets == null){
            pets = new ArrayList<>();
        }
        return pets;
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

}
