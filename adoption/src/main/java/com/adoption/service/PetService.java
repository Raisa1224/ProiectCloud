package com.adoption.service;

import com.adoption.entity.Pet;
import com.adoption.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PetService {
    @Autowired
    PetRepository petRepository;

    public Pet save(Pet pet){
        Optional<Pet> old = getById(pet.getPetId());
        if(old.isPresent()){
            return pet;
        }
        petRepository.save(pet);
        return pet;
    }

    public Optional<Pet> getById(Integer id){
        return petRepository.findById(id);
    }
}
