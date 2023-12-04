package com.pet.controller;

import com.pet.entity.Breed;
import com.pet.service.BreedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/breed")
public class BreedController {

    @Autowired
    private BreedService breedService;


    @GetMapping("/getAllBreeds")
    public ResponseEntity<List<Breed>> getAllBreeds(){
        return ResponseEntity.ok(breedService.getAllBreeds());
    }

    @GetMapping("/getBreedById/{breedId}")
    public ResponseEntity<Breed> getBreedById(@PathVariable Integer breedId){
        return ResponseEntity.ok(breedService.getBreedById(breedId));
    }

    @PostMapping("/addBreed")
    public ResponseEntity<Breed> addBreed(@RequestBody Breed breed){
        return ResponseEntity.ok(breedService.addBreed(breed));
    }

    @DeleteMapping("/deleteBreed/{breedId}")
    public ResponseEntity<Breed> deleteBreed(@PathVariable Integer breedId){
        return ResponseEntity.ok(breedService.deleteBreed(breedId));
    }

    @PutMapping("/editBreed/{breedId}")
    public ResponseEntity<Breed> editBreed(@PathVariable Integer breedId, @RequestBody Breed newBreed){
        return ResponseEntity.ok(breedService.editBreed(breedId, newBreed));
    }

}
