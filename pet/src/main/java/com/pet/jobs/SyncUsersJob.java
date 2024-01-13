package com.pet.jobs;

import com.pet.entity.User;
import com.pet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class SyncUsersJob {
    @Autowired
    UserService userService;
    @Scheduled(fixedRate = 60000) // 60000 milliseconds = 1 minute
    public void executeEveryMinute() {
        System.out.println("Sync for pets started...");
        //Get all pets
        RestTemplate restTemplate = new RestTemplate();
        String getAllPetsURL = "http://users:8083/users";
        ResponseEntity<List<User>> responseEntity = restTemplate.exchange(
                getAllPetsURL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<User>>() {});

        List<User> users = responseEntity.getBody();
        for(User user : users){
            userService.addUsers(user);
        }
        System.out.println("Sync for pets ended...");
    }
}
