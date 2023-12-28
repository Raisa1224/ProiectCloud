package com.adoption.service;

import com.adoption.entity.AdoptionRequest;
import com.adoption.entity.Pet;
import com.adoption.exception.AdoptionNotFoundException;
import com.adoption.exception.PetAlreadyAdoptedException;
import com.adoption.exception.PetNotFoundException;
import com.adoption.exception.UserNotFoundException;
import com.adoption.repository.AdoptionRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AdoptionRequestService {
    @Autowired
    private AdoptionRequestRepository adoptionRequestRepository;

    @Autowired
    private RedisService redisService;

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

    public List<AdoptionRequest> getAdoptionRequestByOwner(Integer ownerId){
        return adoptionRequestRepository.findByOwnerId(ownerId);
    }

    public AdoptionRequest addAdoptionRequest(AdoptionRequest adoptionRequest){
        if (adoptionRequest.getPet() == null) {
            throw new PetNotFoundException("Pet must be present for adoption");
        }
        if (adoptionRequest.getClient() == null) {
            throw new UserNotFoundException("Client must be present for adoption");
        }
        if (!getAdoptionRequestByPet(adoptionRequest.getPet().getPetId()).isEmpty()) {
            throw new PetAlreadyAdoptedException(String.format("Pet %s is already adopted", adoptionRequest.getPet().getPetId()));
        }
        String userId = redisService.getData("userId");
        if (Objects.equals(adoptionRequest.getPet().getOwner().getUserId(), Integer.valueOf(userId))) {
            throw new PetAlreadyAdoptedException("As an owner, you cannot adopt your own pet.");
        }
        RestTemplate restTemplate = new RestTemplate();
        // Change the available field in pet
        Pet newPet = adoptionRequest.getPet();
        newPet.setAvailable(false);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Pet> requestEntity = new HttpEntity<>(newPet, headers);
        String petUrl = "http://pet:8081/pet/editPetId/" + adoptionRequest.getPet().getPetId();
        ResponseEntity<Pet> editedPet = restTemplate.exchange(
                petUrl,
                HttpMethod.PUT,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });
        adoptionRequest.setAdoptionDate(new Date());
        adoptionRequest.setPet(editedPet.getBody());
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
