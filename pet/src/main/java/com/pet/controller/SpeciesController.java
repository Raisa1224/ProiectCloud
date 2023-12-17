package com.pet.controller;

import com.pet.entity.Species;
import com.pet.service.SpeciesService;
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

@RequiredArgsConstructor
@Controller
@RequestMapping("/species")
public class SpeciesController {

    @Autowired
    private SpeciesService speciesService;

    @RequestMapping("/getAllSpecies")
    public String getAllSpecies(Model model,
                                   @RequestParam("page") Optional<Integer> page,
                                   @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(20);

        Page<Species> speciesPage = speciesService.findPaginated(PageRequest.of(currentPage - 1, pageSize));

        model.addAttribute("speciesPage", speciesPage);

        return "speciesList";
    }

    @RequestMapping("/add")
    public String addSpeciesForm(Model model) {
        model.addAttribute("speciesAdd", new Species());
        return "/speciesForm";
    }

    @PostMapping("/addSpecies")
    public String addSpecies(@ModelAttribute("speciesAdd") @Valid Species species,
                           BindingResult result,
                           Model model) {
        if (result.hasErrors()) {
            return "/speciesForm";
        } else {
            speciesService.addSpecies(species);
            return "redirect:/species/getAllSpecies";
        }
    }

    @RequestMapping("/deleteSpecies/{speciesId}")
    public String deleteSpecies(@PathVariable Integer speciesId){
        speciesService.deleteSpecies(speciesId);
        return "redirect:/species/getAllSpecies";
    }

    @GetMapping("/edit/{speciesId}")
    public String showEditSpeciesForm(@PathVariable("speciesId") Integer speciesId, Model model) {
        Species species = speciesService.getSpeciesById(speciesId);
        model.addAttribute("species", species);
        return "speciesUpdate";
    }

    @PostMapping("/editSpecies/{speciesId}")
    public String editSpecies(@PathVariable("speciesId") Integer speciesId,
                              @ModelAttribute Species newSpecies,
                              Model model) {
        Species updatedSpecies = speciesService.editSpecies(speciesId, newSpecies);
        model.addAttribute("species", updatedSpecies);
        return "redirect:/species/getAllSpecies";
    }

}
