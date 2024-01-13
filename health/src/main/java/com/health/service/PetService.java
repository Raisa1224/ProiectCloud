package com.health.service;

import com.health.entity.Pet;
import com.health.entity.PetMedications;
import com.health.exceptions.EntityAlreadyExistsException;
import com.health.exceptions.NoEntityFoundException;
import com.health.repository.PetMedicationsRepository;
import com.health.repository.PetRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
