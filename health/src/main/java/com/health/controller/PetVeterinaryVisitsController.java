package com.health.controller;

import com.health.constants.Constants;
import com.health.entity.Pet;
import com.health.entity.PetVeterinaryVisits;
import com.health.service.PetVeterinaryVisitsService;
import com.health.service.RedisService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
@RequestMapping("/veterinaryvisits")
public class PetVeterinaryVisitsController {
    @Autowired
    PetVeterinaryVisitsService petVeterinaryVisitsService;

    @Autowired
    RedisService redisService;
    @Value("${docker.application.ip}")
    public  String ip;
    @GetMapping("BE/{petId}")
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

    @GetMapping("/{petId}")
    public String getVeterinaryVisitsForPet(Model model, @PathVariable Integer petId){
        List<PetVeterinaryVisits> petVeterinaryVisits = petVeterinaryVisitsService.getAllVisitsForPet(petId);

        String idString = redisService.getData("userId");
        Integer loggedInUser = Integer.valueOf(idString);

        RestTemplate restTemplate = new RestTemplate();
        String ownerIdURL = Constants.PETS_BASE_URL_CONTAINER + Constants.GET_OWNER_FOR_PET_URL + petId;
        Integer ownerId = restTemplate.getForObject(ownerIdURL, Integer.class);

        System.out.println("IDS" + loggedInUser + " " + ownerId);
        Boolean logged = true;
        if(ownerId!=loggedInUser){
            logged = false;
        }
        String health_url = Constants.HEALTH_BASE_URL_part_1 + ip + Constants.HEALTH_BASE_URL_PART_2;
        model.addAttribute("visits", petVeterinaryVisits);
        model.addAttribute("HEALTH", health_url);
        model.addAttribute("loggedInUser", logged);

        return "getAllPetVeterinaryVisitsForPet";
    }
    @RequestMapping("/add/{petId}")
    public String addVeterinaryVisit(Model model, @PathVariable Integer petId){
        Pet pet = new Pet(petId);
        PetVeterinaryVisits petVeterinaryVisits = new PetVeterinaryVisits();

        petVeterinaryVisits.setPet(pet);
        model.addAttribute("veterinaryvisit", petVeterinaryVisits);

        return "addPetVeterinaryVisit";
    }

    @PostMapping("")
    public String addPetVeterinaryVisit(@ModelAttribute("veterinaryvisit") @Valid PetVeterinaryVisits petVeterinaryVisits,
                                    BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            return "addPetVeterinaryVisit";
        }
        try{
            petVeterinaryVisitsService.addVisit(petVeterinaryVisits);
        }catch (Exception exception){
            bindingResult.reject("globalError", exception.getMessage());
            return "addPetVeterinaryVisit";
        }
        return "redirect:" + Constants.PETS_BASE_URL_PART_1 + ip + Constants.PETS_BASE_URL_PART_2 + Constants.GET_PET_BY_ID_URL + petVeterinaryVisits.getPet().getPetId() ;
    }

    @PatchMapping("/editBE/{visitId}")
    public ResponseEntity<PetVeterinaryVisits> editVeterinaryVisitBE(@PathVariable Integer visitId, @RequestBody PetVeterinaryVisits petVeterinaryVisits){
        return ResponseEntity.ok(petVeterinaryVisitsService.editVisit(visitId, petVeterinaryVisits.getClinic(), petVeterinaryVisits.getDate(), petVeterinaryVisits.getCause(), petVeterinaryVisits.getResult()));
    }

    @RequestMapping("/edit/{visitId}")
    public String veterinaryVisitEditForm(Model model, @PathVariable Integer visitId) {

        PetVeterinaryVisits petVeterinaryVisits = petVeterinaryVisitsService.getById(visitId);

        model.addAttribute("veterinaryvisit", petVeterinaryVisits);

        return "editPetVeterinaryVisit";
    }


    @PostMapping("/editVeterinaryVisit/{visitId}")
    public String editVeterinaryVisit(@PathVariable Integer visitId ,
                                  @ModelAttribute("veterinaryvisit") @Valid PetVeterinaryVisits petVeterinaryVisits,
                                  BindingResult bindingResult){

        PetVeterinaryVisits old = petVeterinaryVisitsService.getById(visitId);
        petVeterinaryVisits.setVisitId(old.getVisitId());
        petVeterinaryVisits.setPet(old.getPet());

        if (bindingResult.hasErrors()) {
            return "editPetVeterinaryVisit";
        }
        try{
            petVeterinaryVisitsService.editVisit(visitId, petVeterinaryVisits.getClinic(), petVeterinaryVisits.getDate(), petVeterinaryVisits.getCause(), petVeterinaryVisits.getResult());
        }catch (Exception exception){
            bindingResult.reject("globalError", exception.getMessage());
            return "editPetVeterinaryVisit";
        }
        return "redirect:" + Constants.PETS_BASE_URL_PART_1 + ip + Constants.PETS_BASE_URL_PART_2 + Constants.GET_PET_BY_ID_URL + petVeterinaryVisits.getPet().getPetId() ;

    }

    @RequestMapping("/delete/{visitId}")
    public String deleteVeterinaryVisit(@PathVariable Integer visitId){
        Integer petId = petVeterinaryVisitsService.deleteVisit(visitId);
        return "redirect:" + Constants.PETS_BASE_URL_PART_1 + ip + Constants.PETS_BASE_URL_PART_2 + Constants.GET_PET_BY_ID_URL + petId ;
    }
}
