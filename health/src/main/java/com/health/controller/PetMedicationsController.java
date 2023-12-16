package com.health.controller;

import com.health.constants.Constants;
import com.health.entity.Pet;
import com.health.entity.PetMedications;
import com.health.service.PetMedicationsService;
import org.apache.tomcat.util.bcel.Const;
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
    public String getMedicationsForPet(Model model, @PathVariable Integer petId){
        List<PetMedications> petMedications = petMedicationsService.getAllMedicationsForPet(petId);

        model.addAttribute("medications", petMedications);
        model.addAttribute("HEALTH", Constants.HEALTH_BASE_URL);

        return "/getAllPetMedicationsForPet";
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
        return "redirect:" + Constants.GET_ALL_PETS_URL ; //metoda din controller nu din html
    }

    @PatchMapping("/editMedicationBE/{medicationId}")
    public ResponseEntity<PetMedications> editMedicationBE(@PathVariable Integer medicationId, @RequestBody PetMedications petMedications){
        return ResponseEntity.ok(petMedicationsService.editMedication(medicationId, petMedications.getDosage(), petMedications.getFrequencyDays(), petMedications.getName(), petMedications.getReason(), petMedications.getObservations()));
    }

    @RequestMapping("/edit/{medicationId}")
    public String medicationEditForm(Model model, @PathVariable Integer medicationId) {

        PetMedications petMedications = petMedicationsService.getById(medicationId);

        System.out.println("IN FIRST EDIT METHOD:"+ petMedications);

        model.addAttribute("medication", petMedications);

        return "/editPetMedication";
    }


    @PostMapping("/editMedication/{medicationId}")
    public String editMedication(@PathVariable Integer medicationId ,
                                 @ModelAttribute("medication") PetMedications petMedication,
                                 BindingResult bindingResult){

        PetMedications old = petMedicationsService.getById(medicationId);
        petMedication.setMedicationId(old.getMedicationId());
        petMedication.setPet(old.getPet());

        System.out.println("IN FIRST EDIT METHOD:"+ petMedication);
        if (bindingResult.hasErrors()) {
            return "/editPetMedication";
        }
        try{
            petMedicationsService.editMedication(medicationId, petMedication.getDosage(), petMedication.getFrequencyDays(), petMedication.getName(), petMedication.getReason(), petMedication.getObservations());
        }catch (Exception exception){
            bindingResult.reject("globalError", exception.getMessage());
            return "/editPetMedication";
        }
        return "redirect:" + Constants.GET_ALL_PETS_URL; //metoda din controller nu din html

    }


    @RequestMapping("/delete/{medicationId}")
    public String deleteMedication(@PathVariable Integer medicationId){
        Integer petId = petMedicationsService.deleteMedication(medicationId);
        System.out.println(petId);
        //get pet by id
        return "redirect:" + Constants.GET_PET_BY_ID_URL + petId;
    }
}
