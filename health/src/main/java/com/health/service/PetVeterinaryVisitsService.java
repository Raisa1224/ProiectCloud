package com.health.service;

import com.health.entity.PetSpecialConditions;
import com.health.entity.PetVeterinaryVisits;
import com.health.exceptions.EntityAlreadyExistsException;
import com.health.exceptions.NoEntityFoundException;
import com.health.repository.PetVeterinaryVisitsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
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
        Optional<PetVeterinaryVisits> existing = petVeterinaryVisitsRepository.findByClinicAndCauseAndResultAndDate(petVeterinaryVisits.getClinic(), petVeterinaryVisits.getCause(), petVeterinaryVisits.getResult(), petVeterinaryVisits.getDate());
        if(existing.isPresent()){
            throw new EntityAlreadyExistsException("Visit was already added");
        }
        return petVeterinaryVisitsRepository.save(petVeterinaryVisits);
    }

    @Transactional
    public PetVeterinaryVisits editVisit(Integer visitId, String clinic, Date date, String cause, String result){
        Optional<PetVeterinaryVisits> old = petVeterinaryVisitsRepository.findById(visitId);
        if(old.isPresent()){
            Optional<PetVeterinaryVisits> existing = petVeterinaryVisitsRepository.findByClinicAndCauseAndResultAndDate(clinic,cause, result, date);
            if(existing.isPresent()){
                throw new EntityAlreadyExistsException("Visit was already added");
            }
            petVeterinaryVisitsRepository.editVeterinaryVisit(visitId, clinic, date, cause, result);
        }
        else{
            throw new NoEntityFoundException("NO ENTITY FOUND");
        }
        return old.get();
    }

    @Transactional
    public Integer deleteVisit(Integer visitId){
        Optional<PetVeterinaryVisits> oldPetVeterinaryVisit = petVeterinaryVisitsRepository.findById(visitId);
        if(oldPetVeterinaryVisit.isPresent()){
            petVeterinaryVisitsRepository.deleteById(visitId);
        }
        else{
            throw new NoEntityFoundException("NO ENTITY FOUND");
        }
        return oldPetVeterinaryVisit.get().getPet().getPetId();
    }
}
