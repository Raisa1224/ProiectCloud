package com.adoption.service;

import com.adoption.entity.AdoptionRequest;
import com.adoption.exception.AdoptionNotFoundException;
import com.adoption.exception.PetNotFoundException;
import com.adoption.exception.UserNotFoundException;
import com.adoption.repository.AdoptionRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdoptionRequestService {
    @Autowired
    private AdoptionRequestRepository adoptionRequestRepository;

    public List<AdoptionRequest> getAllAdoptionRequests(){
        return adoptionRequestRepository.findAll();
    }

    public AdoptionRequest getAdoptionRequestById(Integer adoptionId){
        Optional<AdoptionRequest> adoptionRequestOptional= adoptionRequestRepository.findById(adoptionId);
        if (adoptionRequestOptional.isEmpty()) {
            throw new AdoptionNotFoundException(String.format("Adoption request with id %s not found", adoptionId));
        }
        return adoptionRequestOptional.get();
    }

    public List<AdoptionRequest> getAdoptionRequestByClient(Integer clientId){
       return adoptionRequestRepository.findByClientId(clientId);
    }

    public List<AdoptionRequest> getAdoptionRequestByPet(Integer petId){
        return adoptionRequestRepository.findByPetId(petId);
    }

    public AdoptionRequest addAdoptionRequest(AdoptionRequest adoptionRequest){
        if (adoptionRequest.getPet() == null) {
            throw new PetNotFoundException("Pet must be present for adoption");
        }
        if (adoptionRequest.getClient() == null) {
            throw new UserNotFoundException("Client must be present for adoption");
        }
        return adoptionRequestRepository.save(adoptionRequest);
    }

    public AdoptionRequest deleteAdoptionRequest(Integer adoptionId){
        Optional<AdoptionRequest> adoptionRequestOptional= adoptionRequestRepository.findById(adoptionId);
        if (adoptionRequestOptional.isEmpty()) {
            throw new AdoptionNotFoundException(String.format("Adoption request with id %s not found", adoptionId));
        }
        adoptionRequestRepository.deleteById(adoptionId);
        return adoptionRequestOptional.get();
    }

    public AdoptionRequest editAdoptionRequest(Integer adoptionId, AdoptionRequest newAdoptionRequest){
        Optional<AdoptionRequest> adoptionRequestOptional= adoptionRequestRepository.findById(adoptionId);
        if (adoptionRequestOptional.isEmpty()) {
            throw new AdoptionNotFoundException(String.format("Adoption request with id %s not found", adoptionId));
        }
        newAdoptionRequest.setAdoptionRequestId(adoptionRequestOptional.get().getAdoptionRequestId());
        return adoptionRequestRepository.save(newAdoptionRequest);
    }
}
