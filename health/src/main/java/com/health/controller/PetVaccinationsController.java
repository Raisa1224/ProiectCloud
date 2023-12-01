package com.health.controller;

import com.health.entity.PetVaccinations;
import com.health.service.PetVaccinationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/vaccinations")
public class PetVaccinationsController {
    @Autowired
    PetVaccinationsService petVaccinationsService;
    @GetMapping
    public ResponseEntity<List<PetVaccinations>> getAllVaccinations(){
        return ResponseEntity.ok(petVaccinationsService.getAll());
    }
    @GetMapping("/{petId}")
    public ResponseEntity<List<PetVaccinations>> getAllVaccinationsForASpecificPet(@PathVariable Integer petId){
        List<PetVaccinations> petVaccinations = petVaccinationsService.getAllVaccinationsForPet(petId);
        return ResponseEntity.ok(petVaccinations);
    }

    @GetMapping("/vaccination/{vaccinationId}")
    public ResponseEntity<PetVaccinations> getVaccination(@PathVariable Integer vaccinationId){
        return ResponseEntity.ok(petVaccinationsService.getById(vaccinationId));
    }

    @PostMapping("/add")
    public ResponseEntity<PetVaccinations> addVaccination(@RequestBody PetVaccinations petVaccinations){
        return ResponseEntity.ok(petVaccinationsService.addVaccination(petVaccinations));
    }

    @PatchMapping("/edit/{vaccinationId}")
    public ResponseEntity<PetVaccinations> editVaccination(@PathVariable Integer vaccinationId, @RequestBody PetVaccinations petVaccinations){
        return ResponseEntity.ok(petVaccinationsService.editVaccination(vaccinationId, petVaccinations));
    }

    @DeleteMapping("/delete/{vaccinationId}")
    public ResponseEntity<PetVaccinations> deleteVaccination(@PathVariable Integer vaccinationId){
        return ResponseEntity.ok(petVaccinationsService.deleteVaccination(vaccinationId));
    }
}
