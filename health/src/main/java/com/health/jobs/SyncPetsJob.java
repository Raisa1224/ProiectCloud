package com.health.jobs;

import com.health.service.PetService;
import com.health.constants.Constants;
import com.health.entity.Pet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class SyncPetsJob {

    @Autowired
    PetService petService;

    @Scheduled(fixedRate = 60000) // 60000 milliseconds = 1 minute
    public void executeEveryMinute() {
        System.out.println("Sync for pets started...");
        //Get all pets
        RestTemplate restTemplate = new RestTemplate();
        String getAllPetsURL = Constants.PETS_BASE_URL_CONTAINER + Constants.GET_ALL_PETS_URL;
        ResponseEntity<List<Pet>> responseEntity = restTemplate.exchange(
                getAllPetsURL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Pet>>() {});

        List<Pet> pets = responseEntity.getBody();
        for(Pet pet : pets){
            petService.save(pet);
        }
        System.out.println("Sync for pets ended...");
    }
}
