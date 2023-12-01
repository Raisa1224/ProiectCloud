package com.adoption.controller;

import com.adoption.entity.AdoptionRequest;
import com.adoption.service.AdoptionRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/adoption")
public class AdoptionRequestController {

    @Autowired
    private AdoptionRequestService adoptionRequestService;

    @GetMapping("/getAllAdoptions")
    public ResponseEntity<List<AdoptionRequest>> getAllAdoptionRequests(){
        return ResponseEntity.ok(adoptionRequestService.getAllAdoptionRequests());
    }

    @GetMapping("/getAdoptionRequestById/{adoptionId}")
    public ResponseEntity<AdoptionRequest> getAdoptionRequestById(@PathVariable Integer adoptionId){
        return ResponseEntity.ok(adoptionRequestService.getAdoptionRequestById(adoptionId));
    }

    @GetMapping("/getAdoptionsByClient/{clientId}")
    public ResponseEntity<List<AdoptionRequest>> getAdoptionRequestByClient(@PathVariable Integer clientId){
        return ResponseEntity.ok(adoptionRequestService.getAdoptionRequestByClient(clientId));
    }

    @GetMapping("/getAdoptionsByPet/{petId}")
    public ResponseEntity<List<AdoptionRequest>> getAdoptionRequestByPet(@PathVariable Integer petId){
        return ResponseEntity.ok(adoptionRequestService.getAdoptionRequestByPet(petId));
    }

    @PostMapping("/addAdoption")
    public ResponseEntity<AdoptionRequest> addAdoptionRequest(@RequestBody AdoptionRequest adoptionRequest){
        return ResponseEntity.ok(adoptionRequestService.addAdoptionRequest(adoptionRequest));
    }

    @PutMapping("/editAdoption/{adoptionId}")
    public ResponseEntity<AdoptionRequest> editAdoptionRequest(@PathVariable Integer adoptionId, @RequestBody AdoptionRequest newAdoptionRequest){
        return ResponseEntity.ok(adoptionRequestService.editAdoptionRequest(adoptionId, newAdoptionRequest));
    }

    @DeleteMapping("/deleteAdoption/{adoptionId}")
    public ResponseEntity<AdoptionRequest> deleteAdoptionRequest(@PathVariable Integer adoptionId){
        return ResponseEntity.ok(adoptionRequestService.deleteAdoptionRequest(adoptionId));
    }
}
