package com.pet.controller;

import com.pet.entity.Species;
import com.pet.service.SpeciesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/species")
public class SpeciesController {

    @Autowired
    private SpeciesService speciesService;

    @GetMapping("/getAllSpecies")
    public ResponseEntity<List<Species>> getAllSpecies(){
        return ResponseEntity.ok(speciesService.getAllSpecies());
    }

    @GetMapping("/getSpeciesById/{speciesId}")
    public ResponseEntity<Species> getSpeciesById(@PathVariable Integer speciesId){
        return ResponseEntity.ok(speciesService.getSpeciesById(speciesId));
    }

    @PostMapping("/addSpecies")
    public ResponseEntity<Species> addSpecies(@RequestBody Species species){
        return ResponseEntity.ok(speciesService.addSpecies(species));
    }

    @DeleteMapping("/deleteSpecies/{speciesId}")
    public ResponseEntity<Species> deleteSpecies(@PathVariable Integer speciesId){
        return ResponseEntity.ok(speciesService.deleteSpecies(speciesId));
    }

    @PutMapping("/editSpecies/{speciesId}")
    public ResponseEntity<Species> editSpecies(@PathVariable Integer speciesId, @RequestBody Species newSpecies){
        return ResponseEntity.ok(speciesService.editSpecies(speciesId, newSpecies));
    }
}
