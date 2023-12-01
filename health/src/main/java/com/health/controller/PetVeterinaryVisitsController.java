package com.health.controller;

import com.health.entity.PetVeterinaryVisits;
import com.health.service.PetMedicationsService;
import com.health.service.PetVeterinaryVisitsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/veterinaryvisits")
public class PetVeterinaryVisitsController {
    @Autowired
    PetVeterinaryVisitsService petVeterinaryVisitsService;

    @GetMapping("/{petId}")
    public ResponseEntity<List<PetVeterinaryVisits>> getAllVisitsForASpecificPet(@PathVariable Integer petId){
        List<PetVeterinaryVisits> petVeterinaryVisitsList = petVeterinaryVisitsService.getAllVisitsForPet(petId);
        return ResponseEntity.ok(petVeterinaryVisitsList);
    }

    @GetMapping("visit/{visitId}")
    public ResponseEntity<PetVeterinaryVisits> getSpecificVisitForASpecificPet(@PathVariable Integer visitId){
        return ResponseEntity.ok(petVeterinaryVisitsService.getById(visitId));
    }

    @PostMapping("/add")
    public ResponseEntity<PetVeterinaryVisits> addVeterinaryVisitForPet(@RequestBody PetVeterinaryVisits petVeterinaryVisits){
        return ResponseEntity.ok(petVeterinaryVisitsService.addVisit(petVeterinaryVisits));
    }

    @PatchMapping("/edit/{visitId}")
    public ResponseEntity<PetVeterinaryVisits> editVeterinaryVisit(@PathVariable Integer visitId, @RequestBody PetVeterinaryVisits petVeterinaryVisits){
        return ResponseEntity.ok(petVeterinaryVisitsService.editVisit(visitId, petVeterinaryVisits));
    }

    @DeleteMapping("/delete/{visitId}")
    public ResponseEntity<PetVeterinaryVisits> deleteVeterinaryVisit(@PathVariable Integer visitId){
        return ResponseEntity.ok(petVeterinaryVisitsService.deleteVisit(visitId));
    }
}
