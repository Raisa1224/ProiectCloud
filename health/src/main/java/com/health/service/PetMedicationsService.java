package com.health.service;

import com.health.entity.Pet;
import com.health.entity.PetMedications;
import com.health.exceptions.NoEntityFoundException;
import com.health.repository.PetMedicationsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        System.out.println(petMedications.get());
        if(petMedications.isPresent()){
            return  petMedications.get();
        }
        else throw new NoEntityFoundException("NO ENTITY FOUND");
    }

    @Transactional
    public PetMedications addMedication(PetMedications petMedications){
        System.out.println(petMedications.getPet());
        //search the pet in the db and return error if it doesn't exist
        return petMedicationsRepository.save(petMedications);
    }

    @Transactional
    public PetMedications editMedication(Integer medication, PetMedications petMedications){
        Optional<PetMedications> oldMedication = petMedicationsRepository.findById(medication);
        if(oldMedication.isPresent()){
            petMedicationsRepository.deleteById(medication);
            petMedicationsRepository.save(petMedications);
        }
        else{
            throw new NoEntityFoundException("NO ENTITY FOUND");
        }
        return petMedications;
    }

    @Transactional
    public PetMedications deleteMedication(Integer medicationId){
        Optional<PetMedications> oldMedication = petMedicationsRepository.findById(medicationId);
        if(oldMedication.isPresent()){
            petMedicationsRepository.deleteById(medicationId);
        }
        else{
            throw new NoEntityFoundException("NO ENTITY FOUND");
        }
        return oldMedication.get();
    }
}
