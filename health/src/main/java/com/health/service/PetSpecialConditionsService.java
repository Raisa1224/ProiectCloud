package com.health.service;

import com.health.entity.PetSpecialConditions;
import com.health.exceptions.NoEntityFoundException;
import com.health.repository.PetSpecialConditionsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PetSpecialConditionsService {
    @Autowired
    PetSpecialConditionsRepository petSpecialConditionsRepository;

    @Transactional
    public List<PetSpecialConditions> getAllSpecialConditionsForPet(Integer petId){
        List<PetSpecialConditions> conditions = petSpecialConditionsRepository.findByPetId(petId);
        if(conditions == null){
            conditions = new ArrayList<>();
        }
        return conditions;
    }

    @Transactional
    public PetSpecialConditions getById(Integer vaccinationId){
        Optional<PetSpecialConditions> petCondition = petSpecialConditionsRepository.findById(vaccinationId);
        if(petCondition.isPresent()){
            return  petCondition.get();
        }
        else throw new NoEntityFoundException("NO ENTITY FOUND");
    }

    @Transactional
    public PetSpecialConditions addCondition(PetSpecialConditions petSpecialConditions){
        System.out.println(petSpecialConditions.getPet());
        System.out.println(petSpecialConditions);
        //search the pet in the db and return error if it doesn't exist
        return petSpecialConditionsRepository.save(petSpecialConditions);
    }

    @Transactional
    public PetSpecialConditions editCondition(Integer conditionId, PetSpecialConditions petSpecialConditions){
        Optional<PetSpecialConditions> oldCondition = petSpecialConditionsRepository.findById(conditionId);
        if(oldCondition.isPresent()){
            petSpecialConditionsRepository.deleteById(conditionId);
            petSpecialConditionsRepository.save(petSpecialConditions);
        }
        else{
            throw new NoEntityFoundException("NO ENTITY FOUND");
        }
        return petSpecialConditions;
    }

    @Transactional
    public PetSpecialConditions deleteCondition(Integer conditionId){
        Optional<PetSpecialConditions> oldCondition = petSpecialConditionsRepository.findById(conditionId);
        if(oldCondition.isPresent()){
            petSpecialConditionsRepository.deleteById(conditionId);
        }
        else{
            throw new NoEntityFoundException("NO ENTITY FOUND");
        }
        return oldCondition.get();
    }

}
