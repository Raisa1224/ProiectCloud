package com.adoption.service;

import com.adoption.entity.Breed;
import com.adoption.repository.BreedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BreedService {
    @Autowired
    private BreedRepository breedRepository;

    public Breed addBreed(Breed breed){
        Optional<Breed> old = findById(breed.getBreed_id());
        if(old.isPresent()){
            return breed;
        }
        else{
            return breedRepository.save(breed);
        }
    }

    public Optional<Breed> findById(Integer id){
        return breedRepository.findById(id);
    }
}
