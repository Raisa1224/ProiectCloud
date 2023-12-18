package com.health.controller;

import com.health.constants.Constants;
import com.health.entity.Pet;
import com.health.entity.PetVaccinations;
import com.health.service.PetVaccinationsService;
import com.health.service.RedisService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
@RequestMapping("/vaccinations")
public class PetVaccinationsController {
    @Autowired
    PetVaccinationsService petVaccinationsService;

    @Autowired
    RedisService redisService;

    @GetMapping
    public ResponseEntity<List<PetVaccinations>> getAllVaccinations(){
        return ResponseEntity.ok(petVaccinationsService.getAll());
    }
    @GetMapping("BE/{petId}")
    public ResponseEntity<List<PetVaccinations>> getAllVaccinationsForASpecificPet(@PathVariable Integer petId){
        List<PetVaccinations> petVaccinations = petVaccinationsService.getAllVaccinationsForPet(petId);
        return ResponseEntity.ok(petVaccinations);
    }

    @GetMapping("/vaccination/{vaccinationId}")
    public ResponseEntity<PetVaccinations> getVaccination(@PathVariable Integer vaccinationId){
        return ResponseEntity.ok(petVaccinationsService.getById(vaccinationId));
    }

    @PostMapping("/add")
    public ResponseEntity<PetVaccinations> addVaccinationBE(@RequestBody PetVaccinations petVaccinations){
        return ResponseEntity.ok(petVaccinationsService.addVaccination(petVaccinations));
    }

    @GetMapping("/{petId}")
    public String getVaccinationsForPet(Model model, @PathVariable Integer petId){
        List<PetVaccinations> petVaccinations = petVaccinationsService.getAllVaccinationsForPet(petId);

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

        model.addAttribute("vaccinations", petVaccinations);
        model.addAttribute("HEALTH", Constants.HEALTH_BASE_URL);
        model.addAttribute("loggedInUser", logged);

        return "/getAllPetVaccinationsForPet";
    }
    @RequestMapping("/add/{petId}")
    public String addVaccination(Model model, @PathVariable Integer petId){
        Pet pet = new Pet(petId);
        PetVaccinations petVaccinations = new PetVaccinations();

        petVaccinations.setPet(pet);
        model.addAttribute("vaccination", petVaccinations);

        return "/addPetVaccination";
    }

    @PostMapping("")
    public String addPetVaccination(@ModelAttribute("vaccination") @Valid PetVaccinations petVaccinations,
                                      BindingResult bindingResult, Model model){
        System.out.println(petVaccinations);
        if (bindingResult.hasErrors()) {
            return "/addPetVaccination";
        }
        try{
            petVaccinationsService.addVaccination(petVaccinations);
        }catch (Exception exception){
            bindingResult.reject("globalError", exception.getMessage());
            return "/addPetVaccination";
        }
        return "redirect:" + Constants.PETS_BASE_URL + Constants.GET_PET_BY_ID_URL + petVaccinations.getPet().getPetId() ;
    }

    @PatchMapping("/editBE/{vaccinationId}")
    public ResponseEntity<PetVaccinations> editVaccinationBE(@PathVariable Integer vaccinationId, @RequestBody PetVaccinations petVaccinations){
        return ResponseEntity.ok(petVaccinationsService.editVaccination(vaccinationId, petVaccinations.getName(), petVaccinations.getDate(), petVaccinations.getDose(), petVaccinations.getTotalDoses()));
    }

    @RequestMapping("/edit/{vaccinationId}")
    public String vaccinationEditForm(Model model, @PathVariable Integer vaccinationId) {

        PetVaccinations petVaccinations = petVaccinationsService.getById(vaccinationId);

        model.addAttribute("vaccination", petVaccinations);

        return "/editPetVaccination";
    }


    @PostMapping("/editVaccination/{vaccinationId}")
    public String editVaccination(@PathVariable Integer vaccinationId ,
                                @ModelAttribute("vaccination") @Valid PetVaccinations petVaccinations,
                                BindingResult bindingResult){

        PetVaccinations old = petVaccinationsService.getById(vaccinationId);
        petVaccinations.setVaccinationId(old.getVaccinationId());
        petVaccinations.setPet(old.getPet());

        if (bindingResult.hasErrors()) {
            return "/editPetVaccination";
        }
        try{
            petVaccinationsService.editVaccination(vaccinationId, petVaccinations.getName(), petVaccinations.getDate(), petVaccinations.getDose(), petVaccinations.getTotalDoses());
        }catch (Exception exception){
            bindingResult.reject("globalError", exception.getMessage());
            return "/editPetVaccination";
        }
        return "redirect:" + Constants.PETS_BASE_URL + Constants.GET_PET_BY_ID_URL + petVaccinations.getPet().getPetId() ;

    }

    @RequestMapping("/delete/{vaccinationId}")
    public String deleteVaccination(@PathVariable Integer vaccinationId){
        Integer petId = petVaccinationsService.deleteVaccination(vaccinationId);
        return "redirect:"+ Constants.PETS_BASE_URL + Constants.GET_PET_BY_ID_URL + petId ;
    }
}
