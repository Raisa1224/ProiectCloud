package com.pet.controller;

import com.pet.entity.Breed;
import com.pet.service.BreedService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("/breed")
@RequiredArgsConstructor
@Controller
public class BreedController {

    @Autowired
    private BreedService breedService;

    @RequestMapping("/getAllBreeds")
    public String getAllBreedsPage(Model model,
                                   @RequestParam("page") Optional<Integer> page,
                                   @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(20);

        Page<Breed> breedPage = breedService.findPaginated(PageRequest.of(currentPage - 1, pageSize));

        model.addAttribute("breedPage", breedPage);

        return "breedList";
    }

    @RequestMapping("/add")
    public String addBreedForm(Model model) {
        model.addAttribute("breedAdd", new Breed());
        return "/breedForm";
    }

    @PostMapping("/addBreed")
    public String addBreed(@ModelAttribute("breedAdd") @Valid Breed breed,
                             BindingResult result,
                             Model model) {
        if (result.hasErrors()) {
            return "/breedForm";
        } else {
            breedService.addBreed(breed);
            return "redirect:/breed/getAllBreeds";
        }
    }

    @RequestMapping("/deleteBreed/{breedId}")
    public String deleteBreed(@PathVariable Integer breedId){
        breedService.deleteBreed(breedId);
        return "redirect:/breed/getAllBreeds";
    }

    @GetMapping("/edit/{breedId}")
    public String showEditBreedForm(@PathVariable("breedId") Integer breedId, Model model) {
        Breed breed = breedService.getBreedById(breedId);
        model.addAttribute("breed", breed);
        return "breedUpdate";
    }

    @PostMapping("/editBreed/{breedId}")
    public String updateBreed(@PathVariable("breedId") Integer breedId,
                              @ModelAttribute ("breed") @Valid Breed breed,
                              BindingResult result,
                              Model model) {

        Breed old = breedService.getBreedById(breedId);
        breed.setBreed_id(old.getBreed_id());

        if (result.hasErrors()) {
            return "breedUpdate";
        }

        try{
            Breed updatedBreed = breedService.editBreed(breedId, breed);
            model.addAttribute("breed", updatedBreed);
        }catch (Exception exception){
            result.reject("globalError", exception.getMessage());
            return "breedUpdate";
        }
        return "redirect:/breed/getAllBreeds";

    }
}
