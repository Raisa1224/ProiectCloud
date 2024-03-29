package com.health.controller;

import com.health.constants.Constants;
import com.health.entity.Pet;
import com.health.entity.PetSpecialConditions;
import com.health.service.PetSpecialConditionsService;
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
@RequestMapping("/conditions")
public class PetSpecialConditionsController {
    @Autowired
    PetSpecialConditionsService petSpecialConditionsService;

    @Autowired
    RedisService redisService;
    @Value("${docker.application.ip}")
    public  String ip;
    @GetMapping("BE/{petId}")
    public ResponseEntity<List<PetSpecialConditions>> getAllSpecialConditionsForPet(@PathVariable Integer petId){
        List<PetSpecialConditions> petSpecialConditions = petSpecialConditionsService.getAllSpecialConditionsForPet(petId);
        return ResponseEntity.ok(petSpecialConditions);
    }

    @GetMapping("/condition/{conditionId}")
    public ResponseEntity<PetSpecialConditions> getCondition(@PathVariable Integer conditionId){
        return ResponseEntity.ok(petSpecialConditionsService.getById(conditionId));
    }

    @PostMapping("condition/add")
    public ResponseEntity<PetSpecialConditions> addConditionBE(@RequestBody PetSpecialConditions petSpecialConditions){
        return ResponseEntity.ok(petSpecialConditionsService.addCondition(petSpecialConditions));
    }

    @GetMapping("/{petId}")
    public String getSpecialConditionsForPet(Model model, @PathVariable Integer petId){
        List<PetSpecialConditions> petSpecialConditions = petSpecialConditionsService.getAllSpecialConditionsForPet(petId);

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
        model.addAttribute("conditions", petSpecialConditions);
        model.addAttribute("HEALTH", health_url);
        model.addAttribute("loggedInUser", logged);

        return "getAllPetSpecialConditionsForPet";
    }

    @RequestMapping("/add/{petId}")
    public String addCondition(Model model, @PathVariable Integer petId){
        Pet pet = new Pet(petId);
        PetSpecialConditions petSpecialConditions = new PetSpecialConditions();

        petSpecialConditions.setPet(pet);
        model.addAttribute("condition", petSpecialConditions);

        return "addPetSpecialCondition";
    }

    @PostMapping("")
    public String addSpecialCondition(@ModelAttribute("condition") @Valid PetSpecialConditions petSpecialConditions,
                                   BindingResult bindingResult, Model model){
        System.out.println(petSpecialConditions);
        if (bindingResult.hasErrors()) {
            return "addPetSpecialCondition";
        }
        try{
            petSpecialConditionsService.addCondition(petSpecialConditions);
        }catch (Exception exception){
            bindingResult.reject("globalError", exception.getMessage());
            return "addPetSpecialCondition";
        }
        return "redirect:" + Constants.PETS_BASE_URL_PART_1 + ip + Constants.PETS_BASE_URL_PART_2 + Constants.GET_PET_BY_ID_URL + petSpecialConditions.getPet().getPetId() ;
    }

    @PatchMapping("/editBE/{conditionId}")
    public ResponseEntity<PetSpecialConditions> editConditionBE(@PathVariable Integer conditionId, @RequestBody PetSpecialConditions petSpecialConditions){
        return ResponseEntity.ok(petSpecialConditionsService.editCondition(conditionId, petSpecialConditions.getName(), petSpecialConditions.getDescription(), petSpecialConditions.getObservations()));
    }

    @RequestMapping("/edit/{conditionId}")
    public String conditionEditForm(Model model, @PathVariable Integer conditionId) {

        PetSpecialConditions petSpecialConditions = petSpecialConditionsService.getById(conditionId);

        model.addAttribute("condition", petSpecialConditions);

        return "editPetSpecialCondition";
    }


    @PostMapping("/editCondition/{conditionId}")
    public String editCondition(@PathVariable Integer conditionId ,
                                 @ModelAttribute("condition") @Valid PetSpecialConditions petSpecialConditions,
                                 BindingResult bindingResult){

        PetSpecialConditions old = petSpecialConditionsService.getById(conditionId);
        petSpecialConditions.setConditionId(old.getConditionId());
        petSpecialConditions.setPet(old.getPet());

        if (bindingResult.hasErrors()) {
            return "editPetSpecialCondition";
        }
        try{
            petSpecialConditionsService.editCondition(conditionId, petSpecialConditions.getName(), petSpecialConditions.getDescription(), petSpecialConditions.getObservations());
        }catch (Exception exception){
            bindingResult.reject("globalError", exception.getMessage());
            return "editPetSpecialCondition";
        }
        return "redirect:" + Constants.PETS_BASE_URL_PART_1 + ip + Constants.PETS_BASE_URL_PART_2 + Constants.GET_PET_BY_ID_URL + petSpecialConditions.getPet().getPetId() ;

    }

    @RequestMapping("/delete/{conditionId}")
    public String deleteCondition(@PathVariable Integer conditionId){
        Integer petId = petSpecialConditionsService.deleteCondition(conditionId);
        return "redirect:" + Constants.PETS_BASE_URL_PART_1 + ip + Constants.PETS_BASE_URL_PART_2 + Constants.GET_PET_BY_ID_URL + petId ;
    }
}
