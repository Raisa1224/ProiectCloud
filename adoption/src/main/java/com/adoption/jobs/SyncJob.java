package com.adoption.jobs;

import com.adoption.entity.Breed;
import com.adoption.entity.Pet;
import com.adoption.entity.Species;
import com.adoption.entity.User;
import com.adoption.service.BreedService;
import com.adoption.service.PetService;
import com.adoption.service.SpeciesService;
import com.adoption.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class SyncJob {

    @Autowired
    UserService userService;

    @Autowired
    PetService petService;

    @Autowired
    BreedService breedService;

    @Autowired
    SpeciesService speciesService;

    @Scheduled(fixedRate = 60000) // 60000 milliseconds = 1 minute
    public void executeEveryMinuteForSpecies() {
        System.out.println("Sync for species started...");
        //Get all speciess
        RestTemplate restTemplate = new RestTemplate();
        String getAlSpeciesURL = "http://pet:8081/species/getAllSpeciesForSync";
        ResponseEntity<List<Species>> responseEntity = restTemplate.exchange(
                getAlSpeciesURL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });

        List<Species> speciesList = responseEntity.getBody();
        for (Species species : speciesList) {
            speciesService.addSpecies(species);
        }
        System.out.println("Sync for species ended...");

        executeEveryMinuteForBreeds();

        executeEveryMinuteForUsers();

        executeEveryMinuteForPets();
    }

    public void executeEveryMinuteForBreeds() {
        System.out.println("Sync for breeds started...");
        //Get all breeds
        RestTemplate restTemplate = new RestTemplate();
        String getAlBreedsURL = "http://pet:8081/breed/getAllBreedForSync";
        ResponseEntity<List<Breed>> responseEntityBreed = restTemplate.exchange(
                getAlBreedsURL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });

        List<Breed> breeds = responseEntityBreed.getBody();
        for (Breed breed : breeds) {
            breedService.addBreed(breed);
        }
        System.out.println("Sync for breeds ended...");
    }

    @Scheduled(fixedRate = 60000) // 60000 milliseconds = 1 minute
    public void executeEveryMinuteForUsers() {
        System.out.println("Sync for users started...");
        //Get all users
        RestTemplate restTemplate = new RestTemplate();
        String getAllUsersURL = "http://users:8083/users";
        ResponseEntity<List<User>> responseEntityUser = restTemplate.exchange(
                getAllUsersURL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });

        List<User> users = responseEntityUser.getBody();
        for (User user : users) {
            userService.addUsers(user);
        }
        System.out.println("Sync for users ended...");
    }

    @Scheduled(fixedRate = 60000) // 60000 milliseconds = 1 minute
    public void executeEveryMinuteForPets() {
        System.out.println("Sync for pets started...");
        //Get all pets
        RestTemplate restTemplate = new RestTemplate();
        String getAlPetsURL = "http://pet:8081/pet/getAll";
        ResponseEntity<List<Pet>> responseEntityPet = restTemplate.exchange(
                getAlPetsURL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });

        List<Pet> pets = responseEntityPet.getBody();
        for(Pet pet : pets){
            petService.save(pet);
        }
        System.out.println("Sync for pets ended...");
    }



}
