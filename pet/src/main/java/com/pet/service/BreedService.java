package com.pet.service;

import com.pet.entity.Breed;
import com.pet.exceptions.BreedNotFoundException;
import com.pet.repository.BreedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BreedService implements BreedServiceImpl {

    @Autowired
    private BreedRepository breedRepository;

    public List<Breed> getAllBreeds(){
        List<Breed> breeds = breedRepository.findAll();
        if(breeds == null){
            breeds = new ArrayList<>();
        }
        return breeds;
    }

    public Breed getBreedById(Integer breedId){
        Optional<Breed> breedOptional= breedRepository.findById(breedId);
        if (breedOptional.isEmpty()) {
            throw new BreedNotFoundException(String.format("Breed with id %s not found", breedId));
        }
        return breedOptional.get();
    }

    public Breed addBreed(Breed breed){
        return breedRepository.save(breed);
    }

    public Breed deleteBreed(Integer breedId){
        Optional<Breed> breedOptional= breedRepository.findById(breedId);
        if (breedOptional.isEmpty()) {
            throw new BreedNotFoundException(String.format("Not found breed with id %s", breedId));
        }
        breedRepository.deleteById(breedId);
        return breedOptional.get();
    }

    public Breed editBreed(Integer breedId, Breed newBreed){
        Optional<Breed> breedOptional= breedRepository.findById(breedId);
        if (breedOptional.isEmpty()) {
            throw new BreedNotFoundException(String.format("Not found breed with id %s", breedId));
        }
        newBreed.setBreed_id(breedOptional.get().getBreed_id());
        return breedRepository.save(newBreed);
    }

    @Override
    public Page<Breed> findPaginated(Pageable pageable) {
        return breedRepository.findAll(pageable);
    }

}
