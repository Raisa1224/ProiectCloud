package com.health.controller;

import com.health.entity.PetSpecialConditions;
import com.health.service.PetSpecialConditionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/conditions")
public class PetSpecialConditionsController {
    @Autowired
    PetSpecialConditionsService petSpecialConditionsService;

    @GetMapping("/{petId}")
    public ResponseEntity<List<PetSpecialConditions>> getAllSpecialConditionsForPet(@PathVariable Integer petId){
        List<PetSpecialConditions> petSpecialConditions = petSpecialConditionsService.getAllSpecialConditionsForPet(petId);
        return ResponseEntity.ok(petSpecialConditions);
    }

    @GetMapping("/condition/{conditionId}")
    public ResponseEntity<PetSpecialConditions> getCondition(@PathVariable Integer conditionId){
        return ResponseEntity.ok(petSpecialConditionsService.getById(conditionId));
    }

    @PostMapping("/add")
    public ResponseEntity<PetSpecialConditions> addCondition(@RequestBody PetSpecialConditions petSpecialConditions){
        return ResponseEntity.ok(petSpecialConditionsService.addCondition(petSpecialConditions));
    }

    @PatchMapping("/edit/{conditionId}")
    public ResponseEntity<PetSpecialConditions> editCondition(@PathVariable Integer conditionId, @RequestBody PetSpecialConditions petSpecialConditions){
        return ResponseEntity.ok(petSpecialConditionsService.editCondition(conditionId, petSpecialConditions));
    }

    @DeleteMapping("/delete/{conditionId}")
    public ResponseEntity<PetSpecialConditions> deleteCondition(@PathVariable Integer conditionId){
        return ResponseEntity.ok(petSpecialConditionsService.deleteCondition(conditionId));
    }
}
