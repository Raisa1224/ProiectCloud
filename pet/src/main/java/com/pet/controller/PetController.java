package com.pet.controller;

import com.pet.entity.Pet;
import com.pet.service.BreedService;
import com.pet.service.PetService;
import com.pet.service.SpeciesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequiredArgsConstructor
@Controller
@RequestMapping("/pet")
public class PetController {

    @Autowired
    private PetService petService;

    @Autowired
    private BreedService breedService;

    @Autowired
    private SpeciesService speciesService;

//    @GetMapping("/getAllPets")
//    public ResponseEntity<List<Pet>> getAllPets(){
//        return ResponseEntity.ok(petService.getAllPets());
//    }

    @RequestMapping("/getAllPets")
    public String getAllPets(Model model,
                                @RequestParam("page") Optional<Integer> page,
                                @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(20);

        Page<Pet> petPage = petService.findPaginated(PageRequest.of(currentPage - 1, pageSize));

        model.addAttribute("petPage", petPage);

        return "petList";
    }

//    @GetMapping("/getPetsByName/{petName}")
//    public ResponseEntity<List<Pet>> getPetsById(@PathVariable String petName){
//        return ResponseEntity.ok(petService.getPetsByName(petName));
//    }
//
//    @GetMapping("/getPetsByOwner/{ownerId}")
//    public ResponseEntity<List<Pet>> getPetsByOwner(@PathVariable Integer ownerId){
//        return ResponseEntity.ok(petService.getPetsByOwner(ownerId));
//    }
//
//    @GetMapping("/getPetsBySpecies/{speciesId}")
//    public ResponseEntity<List<Pet>> getPetsBySpecies(@PathVariable Integer speciesId){
//        return ResponseEntity.ok(petService.getPetsBySpecies(speciesId));
//    }
//
//    @GetMapping("/getPetsByBreed/{breedId}")
//    public ResponseEntity<List<Pet>> getPetsByBreed(@PathVariable Integer breedId){
//        return ResponseEntity.ok(petService.getPetsByBreed(breedId));
//    }


    @GetMapping("/getByIdPet/{petId}")
    public ResponseEntity<Pet> getByIdPet(@PathVariable Integer petId){
        return ResponseEntity.ok(petService.getPetById(petId));
    }

    @GetMapping("/getPetById/{petId}")
    public String getPetById(@PathVariable Integer petId, Model model){

        Pet pet = petService.getPetById(petId);
        model.addAttribute("getPet",
                petId);
        return "petDetails";
    }

//    @PostMapping("/addPet")
//    public ResponseEntity<Pet> addPet(@RequestBody Pet pet){
//        return ResponseEntity.ok(petService.addPet(pet));
//    }

    @RequestMapping("/add")
    public String addPetForm(Model model) {
        model.addAttribute("petAdd", new Pet());
        model.addAttribute("species", speciesService.getAllSpecies());
        model.addAttribute("breed", breedService.getAllBreeds());
        //user service for add
        return "/petForm";
    }

    @PostMapping("/addSpecies")
    public String addPet(@ModelAttribute("petAdd") @Valid Pet pet,
                             BindingResult result,
                             Model model) {
        model.addAttribute("species", speciesService.getAllSpecies());
        model.addAttribute("breed", breedService.getAllBreeds());
        // user service for add
        if (result.hasErrors()) {
            return "/petForm";
        } else {
            petService.addPet(pet);
            return "redirect:/pet/getAllPets";
        }
    }

//    @DeleteMapping("/deletePet/{petId}")
//    public ResponseEntity<Pet> deletePet(@PathVariable Integer petId){
//        return ResponseEntity.ok(petService.deletePet(petId));
//    }

    @RequestMapping("/deletePet/{petId}")
    public String deletePet(@PathVariable Integer petId){
        petService.deletePet(petId);
        return "redirect:/pet/getAllPets";
    }

    @PutMapping("/edit/{petId}")
    public ResponseEntity<Pet> editPetId(@PathVariable Integer petId, @RequestBody Pet newPet){
        return ResponseEntity.ok(petService.editPet(petId, newPet));
    }

    @GetMapping("/edit/{petId}")
    public String showEditPetForm(@PathVariable("petId") Integer petId, Model model) {
        Pet pet = petService.getPetById(petId);
        model.addAttribute("pet", pet);
        return "petUpdate";
    }

    // functionality must be checked
    @PostMapping("/editPet/{petId}")
    public String editPet(@PathVariable("petId") Integer petId,
                              @ModelAttribute Pet newPet,
                              Model model) {
        Pet updatedPet = petService.editPet(petId, newPet);
        model.addAttribute("pet", updatedPet);
        return "redirect:/pet/getAllPets";
    }
}
