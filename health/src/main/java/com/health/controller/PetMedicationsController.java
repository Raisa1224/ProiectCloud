package com.health.controller;

import com.health.constants.Constants;
import com.health.entity.Pet;
import com.health.entity.PetMedications;
import com.health.service.PetMedicationsService;
import com.health.service.RedisService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
@RequestMapping("/medications")
public class PetMedicationsController {
    @Autowired
    PetMedicationsService petMedicationsService;

    @Autowired
    RedisService redisService;

    @GetMapping
    public ResponseEntity<List<PetMedications>> getAllMedications(){
        return ResponseEntity.ok(petMedicationsService.getAll());
    }

    @GetMapping("/{petId}")
    public String getMedicationsForPet(Model model, @PathVariable Integer petId){
        List<PetMedications> petMedications = petMedicationsService.getAllMedicationsForPet(petId);

        String idString = redisService.getData("userId");
        Integer loggedInUser = Integer.valueOf(idString);

        RestTemplate restTemplate = new RestTemplate();
        String ownerIdURL = Constants.PETS_BASE_URL + Constants.GET_OWNER_FOR_PET_URL + petId;
        Integer ownerId = restTemplate.getForObject(ownerIdURL, Integer.class);

        System.out.println("IDS" + loggedInUser + " " + ownerId);
        Boolean logged = true;
        if(ownerId!=loggedInUser){
            logged = false;
        }

        model.addAttribute("medications", petMedications);
        model.addAttribute("HEALTH", Constants.HEALTH_BASE_URL);
        model.addAttribute("loggedInUser", logged);

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
    public String addPetMedication(@ModelAttribute("medication") @Valid PetMedications petMedication,
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
        return "redirect:" + Constants.PETS_BASE_URL + Constants.GET_PET_BY_ID_URL + petMedication.getPet().getPetId() ;
    }

    @PatchMapping("/editMedicationBE/{medicationId}")
    public ResponseEntity<PetMedications> editMedicationBE(@PathVariable Integer medicationId, @RequestBody PetMedications petMedications){
        return ResponseEntity.ok(petMedicationsService.editMedication(medicationId, petMedications.getDosage(), petMedications.getFrequencyDays(), petMedications.getName(), petMedications.getReason(), petMedications.getObservations()));
    }

    @RequestMapping("/edit/{medicationId}")
    public String medicationEditForm(Model model, @PathVariable Integer medicationId) {

        PetMedications petMedications = petMedicationsService.getById(medicationId);

        model.addAttribute("medication", petMedications);

        return "/editPetMedication";
    }


    @PostMapping("/editMedication/{medicationId}")
    public String editMedication(@PathVariable Integer medicationId ,
                                 @ModelAttribute("medication") @Valid PetMedications petMedication,
                                 BindingResult bindingResult){

        PetMedications old = petMedicationsService.getById(medicationId);
        petMedication.setMedicationId(old.getMedicationId());
        petMedication.setPet(old.getPet());

        if (bindingResult.hasErrors()) {
            return "/editPetMedication";
        }
        try{
            petMedicationsService.editMedication(medicationId, petMedication.getDosage(), petMedication.getFrequencyDays(), petMedication.getName(), petMedication.getReason(), petMedication.getObservations());
        }catch (Exception exception){
            bindingResult.reject("globalError", exception.getMessage());
            return "/editPetMedication";
        }
        return "redirect:" + Constants.PETS_BASE_URL + Constants.GET_PET_BY_ID_URL + petMedication.getPet().getPetId() ;

    }


    @RequestMapping("/delete/{medicationId}")
    public String deleteMedication(@PathVariable Integer medicationId){
        Integer petId = petMedicationsService.deleteMedication(medicationId);
        return "redirect:" + Constants.PETS_BASE_URL + Constants.GET_PET_BY_ID_URL + petId ;
    }
}
