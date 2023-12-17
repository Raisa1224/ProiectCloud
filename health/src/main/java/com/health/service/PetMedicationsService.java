package com.health.service;

import com.health.constants.Constants;
import com.health.entity.Pet;
import com.health.entity.PetMedications;
import com.health.exceptions.NoEntityFoundException;
import com.health.repository.PetMedicationsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PetMedicationsService {
    @Autowired
    PetMedicationsRepository petMedicationsRepository;


    public List<PetMedications> getAll(){
        List<PetMedications> medications = petMedicationsRepository.findAll();
        if(medications == null){
            medications = new ArrayList<>();
        }
        return medications;
    }
    @Transactional
    public List<PetMedications> getAllMedicationsForPet(Integer petId){
        List<PetMedications> medications = petMedicationsRepository.findByPetId(petId);
        if(medications == null){
            medications = new ArrayList<>();
        }
        return medications;
    }

    @Transactional
    public PetMedications getById(Integer medicationId){
        Optional<PetMedications> petMedications = petMedicationsRepository.findById(medicationId);
        if(petMedications.isPresent()){
            return  petMedications.get();
        }
        else throw new NoEntityFoundException("NO ENTITY FOUND");
    }

    @Transactional
    public PetMedications addMedication(PetMedications petMedications){
        return petMedicationsRepository.save(petMedications);
    }

    @Transactional
    public PetMedications editMedication(Integer medication, Integer dosage, Integer frequencyDays, String name, String reason, String observations){
        Optional<PetMedications> oldMedication = petMedicationsRepository.findById(medication);
        if(oldMedication.isPresent()){
            petMedicationsRepository.editMedication(medication, dosage, frequencyDays, name, reason, observations);
        }
        else{
            throw new NoEntityFoundException("NO ENTITY FOUND");
        }
        return oldMedication.get();
    }

    @Transactional
    public Integer deleteMedication(Integer medicationId){
        Optional<PetMedications> oldMedication = petMedicationsRepository.findById(medicationId);
        if(oldMedication.isPresent()){
            petMedicationsRepository.deleteById(medicationId);
        }
        else{
            throw new NoEntityFoundException("NO ENTITY FOUND");
        }
        return oldMedication.get().getPet().getPetId();
    }
}
