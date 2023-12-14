package com.health.service;

import com.health.entity.PetSpecialConditions;
import com.health.entity.PetVaccinations;
import com.health.exceptions.NoEntityFoundException;
import com.health.repository.PetVaccinationsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PetVaccinationsService {
    @Autowired
    PetVaccinationsRepository petVaccinationsRepository;

    @Transactional
    public List<PetVaccinations> getAll(){
        List<PetVaccinations> medications = petVaccinationsRepository.findAll();
        if(medications == null){
            medications = new ArrayList<>();
        }
        return medications;
    }

    @Transactional
    public List<PetVaccinations> getAllVaccinationsForPet(Integer petId){
        List<PetVaccinations> vaccinations = petVaccinationsRepository.findByPetId(petId);
        if(vaccinations == null){
            vaccinations = new ArrayList<>();
        }
        return vaccinations;
    }

    @Transactional
    public PetVaccinations getById(Integer vaccinationId){
        Optional<PetVaccinations> petVaccinations = petVaccinationsRepository.findById(vaccinationId);
        if(petVaccinations.isPresent()){
            return  petVaccinations.get();
        }
        else throw new NoEntityFoundException("NO ENTITY FOUND");
    }

    @Transactional
    public PetVaccinations addVaccination(PetVaccinations petVaccinations){
        return petVaccinationsRepository.save(petVaccinations);
    }

    @Transactional
    public PetVaccinations editVaccination(Integer vaccinationId, String name, Date date, Integer dose, Integer totalDoses){
        Optional<PetVaccinations> old = petVaccinationsRepository.findById(vaccinationId);
        if(old.isPresent()){
            petVaccinationsRepository.editVaccination(vaccinationId, name, date, dose, totalDoses);
        }
        else{
            throw new NoEntityFoundException("NO ENTITY FOUND");
        }
        return old.get();
    }

    @Transactional
    public PetVaccinations deleteVaccination(Integer vaccinationId){
        Optional<PetVaccinations> oldVaccination = petVaccinationsRepository.findById(vaccinationId);
        if(oldVaccination.isPresent()){
            petVaccinationsRepository.deleteById(vaccinationId);
        }
        else{
            throw new NoEntityFoundException("NO ENTITY FOUND");
        }
        return oldVaccination.get();
    }
}
