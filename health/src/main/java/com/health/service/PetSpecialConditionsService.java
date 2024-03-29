package com.health.service;

import com.health.entity.PetMedications;
import com.health.entity.PetSpecialConditions;
import com.health.exceptions.EntityAlreadyExistsException;
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
        Optional<PetSpecialConditions> existing = petSpecialConditionsRepository.findByNameAndDescriptionAndObservations(petSpecialConditions.getName(), petSpecialConditions.getDescription(), petSpecialConditions.getObservations());
        if(existing.isPresent()){
            throw new EntityAlreadyExistsException("Condition was already added");
        }
        return petSpecialConditionsRepository.save(petSpecialConditions);
    }

    @Transactional
    public PetSpecialConditions editCondition(Integer conditionId, String name, String description, String observations){
        Optional<PetSpecialConditions> old = petSpecialConditionsRepository.findById(conditionId);
        if(old.isPresent()){
            Optional<PetSpecialConditions> existing = petSpecialConditionsRepository.findByNameAndDescriptionAndObservations(name, description, observations);
            if(existing.isPresent()){
                throw new EntityAlreadyExistsException("Condition was already added");
            }
            petSpecialConditionsRepository.editSpecialCondition(conditionId, name, description, observations);
        }
        else{
            throw new NoEntityFoundException("NO ENTITY FOUND");
        }
        return old.get();
    }

    @Transactional
    public Integer deleteCondition(Integer conditionId){
        Optional<PetSpecialConditions> oldCondition = petSpecialConditionsRepository.findById(conditionId);
        if(oldCondition.isPresent()){
            petSpecialConditionsRepository.deleteById(conditionId);
        }
        else{
            throw new NoEntityFoundException("NO ENTITY FOUND");
        }
        return oldCondition.get().getPet().getPetId();
    }

}
