package com.pet.controller;

import com.pet.entity.Pet;
import com.pet.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    private PetService petService;

    @GetMapping("/getAllPets")
    public ResponseEntity<List<Pet>> getAllPets(){
        return ResponseEntity.ok(petService.getAllPets());
    }

    @GetMapping("/getPetsByName/{petName}")
    public ResponseEntity<List<Pet>> getPetsById(@PathVariable String petName){
        return ResponseEntity.ok(petService.getPetsByName(petName));
    }

    @GetMapping("/getPetsByOwner/{ownerId}")
    public ResponseEntity<List<Pet>> getPetsByOwner(@PathVariable Integer ownerId){
        return ResponseEntity.ok(petService.getPetsByOwner(ownerId));
    }

    @GetMapping("/getPetsBySpecies/{speciesId}")
    public ResponseEntity<List<Pet>> getPetsBySpecies(@PathVariable Integer speciesId){
        return ResponseEntity.ok(petService.getPetsBySpecies(speciesId));
    }

    @GetMapping("/getPetsByBreed/{breedId}")
    public ResponseEntity<List<Pet>> getPetsByBreed(@PathVariable Integer breedId){
        return ResponseEntity.ok(petService.getPetsByBreed(breedId));
    }

    @GetMapping("/getPetById/{petId}")
    public ResponseEntity<Pet> getPetById(@PathVariable Integer petId){
        return ResponseEntity.ok(petService.getPetById(petId));
    }

    @PostMapping("/addPet")
    public ResponseEntity<Pet> addPet(@RequestBody Pet pet){
        return ResponseEntity.ok(petService.addPet(pet));
    }

    @DeleteMapping("/deletePet/{petId}")
    public ResponseEntity<Pet> deletePet(@PathVariable Integer petId){
        return ResponseEntity.ok(petService.deletePet(petId));
    }

    @PutMapping("/editPet/{petId}")
    public ResponseEntity<Pet> editPet(@PathVariable Integer petId, @RequestBody Pet newPet){
        return ResponseEntity.ok(petService.editPet(petId, newPet));
    }
}
