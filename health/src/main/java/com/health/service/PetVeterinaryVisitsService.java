package com.health.service;

import com.health.entity.PetVeterinaryVisits;
import com.health.exceptions.NoEntityFoundException;
import com.health.repository.PetVeterinaryVisitsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PetVeterinaryVisitsService {
    @Autowired
    PetVeterinaryVisitsRepository petVeterinaryVisitsRepository;

    @Transactional
    public List<PetVeterinaryVisits> getAllVisitsForPet(Integer petId){
        List<PetVeterinaryVisits> visits = petVeterinaryVisitsRepository.findByPetId(petId);
        if(visits == null){
            visits = new ArrayList<>();
        }
        return visits;
    }

    @Transactional
    public PetVeterinaryVisits getById(Integer visitId){
        Optional<PetVeterinaryVisits> petVeterinaryVisit = petVeterinaryVisitsRepository.findById(visitId);
        if(petVeterinaryVisit.isPresent()){
            return  petVeterinaryVisit.get();
        }
        else throw new NoEntityFoundException("NO ENTITY FOUND");
    }

    @Transactional
    public PetVeterinaryVisits addVisit(PetVeterinaryVisits petVeterinaryVisits){
        return petVeterinaryVisitsRepository.save(petVeterinaryVisits);
    }

    @Transactional
    public PetVeterinaryVisits editVisit(Integer visitId, PetVeterinaryVisits petVeterinaryVisits){
        Optional<PetVeterinaryVisits> oldPetVeterinaryVisit = petVeterinaryVisitsRepository.findById(visitId);
        if(oldPetVeterinaryVisit.isPresent()){
            petVeterinaryVisitsRepository.deleteById(visitId);
            petVeterinaryVisitsRepository.save(petVeterinaryVisits);
        }
        else{
            throw new NoEntityFoundException("NO ENTITY FOUND");
        }
        return petVeterinaryVisits;
    }

    @Transactional
    public PetVeterinaryVisits deleteVisit(Integer visitId){
        Optional<PetVeterinaryVisits> oldPetVeterinaryVisit = petVeterinaryVisitsRepository.findById(visitId);
        if(oldPetVeterinaryVisit.isPresent()){
            petVeterinaryVisitsRepository.deleteById(visitId);
        }
        else{
            throw new NoEntityFoundException("NO ENTITY FOUND");
        }
        return oldPetVeterinaryVisit.get();
    }
}
