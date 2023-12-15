package com.health.controller;

import com.health.constants.Constants;
import com.health.entity.Pet;
import com.health.entity.PetVaccinations;
import com.health.entity.PetVeterinaryVisits;
import com.health.service.PetMedicationsService;
import com.health.service.PetVeterinaryVisitsService;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    public ResponseEntity<PetVeterinaryVisits> addVeterinaryVisitForPetBE(@RequestBody PetVeterinaryVisits petVeterinaryVisits){
        return ResponseEntity.ok(petVeterinaryVisitsService.addVisit(petVeterinaryVisits));
    }

    @RequestMapping("/add/{petId}")
    public String addVeterinaryVisit(Model model, @PathVariable Integer petId){
        Pet pet = new Pet(petId);
        PetVeterinaryVisits petVeterinaryVisits = new PetVeterinaryVisits();

        petVeterinaryVisits.setPet(pet);
        System.out.println(petVeterinaryVisits);
        model.addAttribute("veterinaryvisit", petVeterinaryVisits);

        return "/addPetVeterinaryVisit";
    }

    @PostMapping("")
    public String addPetVeterinaryVisit(@ModelAttribute("veterinaryvisit") PetVeterinaryVisits petVeterinaryVisits,
                                    BindingResult bindingResult, Model model){
        System.out.println(petVeterinaryVisits);
        if (bindingResult.hasErrors()) {
            return "/addPetVeterinaryVisit";
        }
        try{
            petVeterinaryVisitsService.addVisit(petVeterinaryVisits);
        }catch (Exception exception){
            bindingResult.reject("globalError", exception.getMessage());
            return "/addPetVeterinaryVisit";
        }
        return "redirect:" + Constants.GET_ALL_PETS_URL; //metoda din controller nu din html
    }

    @PatchMapping("/editBE/{visitId}")
    public ResponseEntity<PetVeterinaryVisits> editVeterinaryVisitBE(@PathVariable Integer visitId, @RequestBody PetVeterinaryVisits petVeterinaryVisits){
        return ResponseEntity.ok(petVeterinaryVisitsService.editVisit(visitId, petVeterinaryVisits.getClinic(), petVeterinaryVisits.getDate(), petVeterinaryVisits.getCause(), petVeterinaryVisits.getResult()));
    }

    @RequestMapping("/edit/{visitId}")
    public String veterinaryVisitEditForm(Model model, @PathVariable Integer visitId) {

        PetVeterinaryVisits petVeterinaryVisits = petVeterinaryVisitsService.getById(visitId);

        System.out.println("IN FIRST EDIT METHOD:"+ petVeterinaryVisits);

        model.addAttribute("veterinaryvisit", petVeterinaryVisits);

        return "/editPetVeterinaryVisit";
    }


    @PostMapping("/editVeterinaryVisit/{visitId}")
    public String editVeterinaryVisit(@PathVariable Integer visitId ,
                                  @ModelAttribute("veterinaryvisit") PetVeterinaryVisits petVeterinaryVisits,
                                  BindingResult bindingResult){

        PetVeterinaryVisits old = petVeterinaryVisitsService.getById(visitId);
        petVeterinaryVisits.setVisitId(old.getVisitId());
        petVeterinaryVisits.setPet(old.getPet());

        System.out.println("IN FIRST EDIT METHOD:"+ petVeterinaryVisits);
        if (bindingResult.hasErrors()) {
            return "/editPetVeterinaryVisit";
        }
        try{
            petVeterinaryVisitsService.editVisit(visitId, petVeterinaryVisits.getClinic(), petVeterinaryVisits.getDate(), petVeterinaryVisits.getCause(), petVeterinaryVisits.getResult());
        }catch (Exception exception){
            bindingResult.reject("globalError", exception.getMessage());
            return "/editPetVeterinaryVisit";
        }
        return "redirect:" + Constants.GET_ALL_PETS_URL ; //metoda din controller nu din html

    }

    @RequestMapping("/delete/{visitId}")
    public String deleteVeterinaryVisit(@PathVariable Integer visitId){
        Integer petId = petVeterinaryVisitsService.deleteVisit(visitId);
        System.out.println(petId);
        //get pet by id
        return "redirect:" + Constants.GET_PET_BY_ID_URL + petId;
    }
}
