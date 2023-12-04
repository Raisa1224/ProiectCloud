package com.pet.service;

import com.pet.entity.Species;
import com.pet.exceptions.SpeciesNotFloundException;
import com.pet.repository.SpeciesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SpeciesService {

    @Autowired
    private SpeciesRepository speciesRepository;

    public List<Species> getAllSpecies(){
        List<Species> species = speciesRepository.findAll();
        if(species == null){
            species = new ArrayList<>();
        }
        return species;
    }

    public Species getSpeciesById(Integer speciesId){
        Optional<Species> speciesOptional= speciesRepository.findById(speciesId);
        if (speciesOptional.isEmpty()) {
            throw new SpeciesNotFloundException(String.format("Not found species with id %s", speciesId));
        }
        return speciesOptional.get();
    }

    public Species addSpecies(Species species){
        return speciesRepository.save(species);
    }

    public Species deleteSpecies(Integer speciesId){
        Optional<Species> speciesOptional= speciesRepository.findById(speciesId);
        if (speciesOptional.isEmpty()) {
            throw new SpeciesNotFloundException(String.format("Not found species with id %s", speciesId));
        }
        speciesRepository.deleteById(speciesId);
        return speciesOptional.get();
    }

    public Species editSpecies(Integer speciesId, Species newSpecies){
        Optional<Species> speciesOptional= speciesRepository.findById(speciesId);
        if (speciesOptional.isEmpty()) {
            throw new SpeciesNotFloundException(String.format("Not found species with id %s", speciesId));
        }
        newSpecies.setSpecies_id(speciesOptional.get().getSpecies_id());
        return speciesRepository.save(newSpecies);
    }

}
