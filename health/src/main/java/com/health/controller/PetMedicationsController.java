package com.health.controller;

import com.health.entity.Pet;
import com.health.entity.PetMedications;
import com.health.service.PetMedicationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

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

    @RequestMapping("/add/{petId}")
    public String addMedication(Model model, @PathVariable Integer petId){
        Pet pet = new Pet(petId);
        PetMedications petMedications = new PetMedications();

        petMedications.setPet(pet);
        model.addAttribute("medication", petMedications);

        return "/addPetMedication";
    }

    @PostMapping("")
    public String addPetMedication(@ModelAttribute("petMedication") PetMedications petMedication,
                                   BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            return "/addPetMedication";
        }
        try{
            petMedicationsService.addMedication(petMedication);
        }catch (Exception exception){
            bindingResult.reject("globalError", exception.getMessage());
            return "/addPetMedication";
        }
        return "redirect:/pets" ; //metoda din controller nu din html
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
