package com.health.controller;

import com.health.entity.Pet;
import com.health.entity.PetSpecialConditions;
import com.health.service.PetSpecialConditionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

    @PostMapping("condition/add")
    public ResponseEntity<PetSpecialConditions> addConditionBE(@RequestBody PetSpecialConditions petSpecialConditions){
        return ResponseEntity.ok(petSpecialConditionsService.addCondition(petSpecialConditions));
    }
    @RequestMapping("/add/{petId}")
    public String addCondition(Model model, @PathVariable Integer petId){
        Pet pet = new Pet(petId);
        PetSpecialConditions petSpecialConditions = new PetSpecialConditions();

        petSpecialConditions.setPet(pet);
        System.out.println(petSpecialConditions);
        model.addAttribute("condition", petSpecialConditions);

        return "/addPetSpecialCondition";
    }

    @PostMapping("")
    public String addSpecialCondition(@ModelAttribute("condition") PetSpecialConditions petSpecialConditions,
                                   BindingResult bindingResult, Model model){
        System.out.println(petSpecialConditions);
        if (bindingResult.hasErrors()) {
            return "/addPetSpecialCondition";
        }
        try{
            petSpecialConditionsService.addCondition(petSpecialConditions);
        }catch (Exception exception){
            bindingResult.reject("globalError", exception.getMessage());
            return "/addPetSpecialCondition";
        }
        return "redirect:/pets" ; //metoda din controller nu din html
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
