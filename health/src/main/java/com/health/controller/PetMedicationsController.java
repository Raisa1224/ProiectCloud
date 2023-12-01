package com.health.controller;

import com.health.entity.PetMedications;
import com.health.service.PetMedicationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/medications")
public class PetMedicationsController {
    @Autowired
    PetMedicationsService petMedicationsService;

    @GetMapping
    public ResponseEntity<List<PetMedications>> getAllMedications(){
        return ResponseEntity.ok(petMedicationsService.getAll());
    }
    @GetMapping("/{petId}")
    public ResponseEntity<List<PetMedications>> getMedicationsForPet(@PathVariable Integer petId){
        List<PetMedications> petMedications = petMedicationsService.getAllMedicationsForPet(petId);
        return ResponseEntity.ok(petMedications);
    }

    @GetMapping("/medication/{medicationId}")
    public ResponseEntity<PetMedications> getMedication(@PathVariable Integer medicationId){
        return ResponseEntity.ok(petMedicationsService.getById(medicationId));
    }

    @PostMapping("/add")
    public ResponseEntity<PetMedications> addMedication(@RequestBody PetMedications petMedications){
        return ResponseEntity.ok(petMedicationsService.addMedication(petMedications));
    }

    @PatchMapping("/edit/{medicationId}")
    public ResponseEntity<PetMedications> editMedication(@PathVariable Integer medicationId, @RequestBody PetMedications petMedications){
        return ResponseEntity.ok(petMedicationsService.editMedication(medicationId, petMedications));
    }

    @DeleteMapping("/delete/{medicationId}")
    public ResponseEntity<PetMedications> deleteMedication(@PathVariable Integer medicationId){
        return ResponseEntity.ok(petMedicationsService.deleteMedication(medicationId));
    }
}
