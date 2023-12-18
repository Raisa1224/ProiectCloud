package com.pet.controller;

import com.pet.entity.Pet;
import com.pet.entity.User;
import com.pet.service.BreedService;
import com.pet.service.PetService;
import com.pet.service.RedisService;
import com.pet.service.SpeciesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@RequiredArgsConstructor
@Controller
@RequestMapping("/pet")
public class PetController {

    @Autowired
    private RedisService redisService;

    @Autowired
    private PetService petService;

    @Autowired
    private BreedService breedService;

    @Autowired
    private SpeciesService speciesService;

    @GetMapping("/getOwnerIdForPet/{petId}")
    public ResponseEntity<Integer> getOwnerIdForPet(@PathVariable Integer petId){
        Pet pet = petService.getPetById(petId);
        System.out.println(pet);
        return ResponseEntity.ok(pet.getOwner().getUserId());
    }
    @RequestMapping("/getAllPets")
    public String getAllPets(Model model,
                                @RequestParam("page") Optional<Integer> page,
                                @RequestParam("size") Optional<Integer> size) {


        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);

        Page<Pet> petPage = petService.findPaginated(PageRequest.of(currentPage - 1, pageSize));
        String id = redisService.getData("userId");
        RestTemplate restTemplate = new RestTemplate();
        String userUrlLogged = "http://localhost:8083/users/" + id;
        ResponseEntity<User> loggedUser = restTemplate.exchange(
                userUrlLogged,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
        model.addAttribute("loggedUser", loggedUser.getBody());
        model.addAttribute("petPage", petPage);

        return "petList";
    }

    @GetMapping("/getByIdPet/{petId}")
    public ResponseEntity<Pet> getByIdPet(@PathVariable Integer petId){
        return ResponseEntity.ok(petService.getPetById(petId));
    }

    @GetMapping("/getPetById/{petId}")
    public String getPetById(@PathVariable Integer petId, Model model){
        RestTemplate restTemplate = new RestTemplate();
        String medicationUrl = "http://localhost:8080/medications/" + petId;
        ResponseEntity<String> tableMedication = restTemplate.exchange(
                medicationUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
        model.addAttribute("medicationTable",tableMedication.getBody());

        String specialConditionsUrl = "http://localhost:8080/conditions/" + petId;
        ResponseEntity<String> tableSpecialConditions = restTemplate.exchange(
                specialConditionsUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
        model.addAttribute("specialConditionsTable",tableSpecialConditions.getBody());

        String vaccinationUrl = "http://localhost:8080/vaccinations/" + petId;
        ResponseEntity<String> tableVaccination = restTemplate.exchange(
                vaccinationUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
        model.addAttribute("vaccinationTable",tableVaccination.getBody());

        String veterinaryVisitsUrl = "http://localhost:8080/veterinaryvisits/" + petId;
        ResponseEntity<String> tableVeterinaryVisits = restTemplate.exchange(
                veterinaryVisitsUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
        model.addAttribute("veterinaryVisitsTable",tableVeterinaryVisits.getBody());

        String idString = redisService.getData("userId");
        Integer loggedInUser = Integer.valueOf(idString);

        Pet idPet = petService.getPetById(petId);
        model.addAttribute("getPet",
                idPet);
        model.addAttribute("loggedInUser", loggedInUser);
        return "petDetails";
    }

    @RequestMapping("/add")
    public String addPetForm(Model model) {
        model.addAttribute("petAdd", new Pet());
        model.addAttribute("species", speciesService.getAllSpecies());
        model.addAttribute("breed", breedService.getAllBreeds());
        String id = redisService.getData("userId");
        RestTemplate restTemplate = new RestTemplate();
        String userUrlLogged = "http://localhost:8083/users/" + id;
        ResponseEntity<User> loggedUser = restTemplate.exchange(
                userUrlLogged,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
        Pet pet = new Pet();
        pet.setOwner(loggedUser.getBody());
        model.addAttribute("pet",pet);

        return "/petForm";
    }

    @PostMapping("/addPet")
    public String addPet(@ModelAttribute("petAdd") @Valid Pet pet,
                             BindingResult result,
                             Model model) {
        model.addAttribute("species", speciesService.getAllSpecies());
        model.addAttribute("breed", breedService.getAllBreeds());

        //user for add
        RestTemplate restTemplate = new RestTemplate();
        String id = redisService.getData("userId");
        String userUrlLogged = "http://localhost:8083/users/" + id;
        ResponseEntity<User> loggedUser = restTemplate.exchange(
                userUrlLogged,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
        pet.setOwner(loggedUser.getBody());
        pet.setAvailable(true);
        model.addAttribute("pet",pet);

        if (result.hasErrors()) {
            return "/petForm";
        } else {
            petService.addPet(pet);
            return "redirect:/pet/getAllPets";
        }
    }

    @RequestMapping("/deletePet/{petId}")
    public String deletePet(@PathVariable Integer petId){
        petService.deletePet(petId);
        return "redirect:/pet/getAllPets";
    }

    @PutMapping("/editPetId/{petId}")
    public ResponseEntity<Pet> editPetId(@PathVariable Integer petId, @RequestBody @Valid Pet newPet){
        return ResponseEntity.ok(petService.editPet(petId, newPet));
    }

    @PatchMapping("/editFE/{petId}")
    public ResponseEntity<Pet> editPetFE(@PathVariable Integer petId, @RequestBody Pet pet){
        return ResponseEntity.ok(petService.editPetFE(petId, pet.getPetName(), pet.getYearOfBirth(), pet.getGender(), pet.getColor(), pet.getPrice(), pet.getAvailable()));
    }

    @GetMapping("/edit/{petId}")
    public String showEditPetForm(@PathVariable Integer petId, Model model) {
        Pet pet = petService.getPetById(petId);
        model.addAttribute("pet", pet);
        return "petUpdate";
    }

    // functionality must be checked
    @PostMapping("/editPet/{petId}")
    public String editPet(@PathVariable("petId") Integer petId,
                              @ModelAttribute("pet") @Valid Pet pet,
                          BindingResult bindingResult) {

        Pet old = petService.getPetById(petId);
        pet.setPetId(old.getPetId());
        pet.setSpecies(old.getSpecies());
        pet.setBreed(old.getBreed());
        pet.setOwner(old.getOwner());

        if (bindingResult.hasErrors()) {
            return "petUpdate";
        }

        try{
            petService.editPetFE(petId, pet.getPetName(), pet.getYearOfBirth(), pet.getGender(), pet.getColor(), pet.getPrice(), pet.getAvailable());
        }catch (Exception exception){
            bindingResult.reject("globalError", exception.getMessage());
            return "petUpdate";
        }
        return "redirect:/pet/getAllPets";
    }
}
