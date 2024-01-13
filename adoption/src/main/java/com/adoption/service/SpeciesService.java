package com.adoption.service;

import com.adoption.entity.Species;
import com.adoption.repository.SpeciesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SpeciesService {
    @Autowired
    private SpeciesRepository speciesRepository;

    public Species addSpecies(Species species){
        Optional<Species> old = findById(species.getSpecies_id());
        if(old.isPresent()){
            return species;
        }
        else{
            return speciesRepository.save(species);
        }
    }

    public Optional<Species> findById(Integer id){
        return speciesRepository.findById(id);
    }
}
