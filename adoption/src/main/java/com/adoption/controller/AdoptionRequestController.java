package com.adoption.controller;

import com.adoption.entity.AdoptionFeedback;
import com.adoption.entity.AdoptionRequest;
import com.adoption.entity.Pet;
import com.adoption.entity.User;
import com.adoption.service.AdoptionFeedbackService;
import com.adoption.service.AdoptionRequestService;
import com.adoption.service.RedisService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/adoption")
public class AdoptionRequestController {

    @Autowired
    private AdoptionRequestService adoptionRequestService;

    @Autowired
    private AdoptionFeedbackService adoptionFeedbackService;

    @Autowired
    private RedisService redisService;

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

    @GetMapping("/getAdoptionsByOwner/{ownerId}")
    public ResponseEntity<List<AdoptionRequest>> getAdoptionsByOwner(@PathVariable Integer ownerId){
        return ResponseEntity.ok(adoptionRequestService.getAdoptionRequestByOwner(ownerId));
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

    @RequestMapping("/addAdoptionForm/{petId}")
    public String addAdoptionForm(Model model, @PathVariable Integer petId) {
        RestTemplate restTemplate = new RestTemplate();
        String petUrl = "http://pet:8081/pet/getByIdPet/" + petId;
        ResponseEntity<Pet> responsePet = restTemplate.exchange(
                petUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });
        AdoptionRequest adoptionRequest = new AdoptionRequest();
        adoptionRequest.setPet(responsePet.getBody());
        String id = redisService.getData("userId");
        String loggedUserUrl = "http://users:8083/users/" + id;
        ResponseEntity<User> responseLoggedUser = restTemplate.exchange(
                loggedUserUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });
        adoptionRequest.setClient(responseLoggedUser.getBody());
        model.addAttribute("adoptionRequest", adoptionRequest);
        return "adoptionRequestTemplates/addAdoptionForm";
    }

    @PostMapping
    public String adoptPet(@ModelAttribute("adoptionRequest") @Valid AdoptionRequest adoptionRequest,
                           BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            return "adoptionRequestTemplates/addAdoptionForm";
        }
        try{
            adoptionRequestService.addAdoptionRequest(adoptionRequest);
        }catch (Exception exception){
            bindingResult.reject("globalError", exception.getMessage());
            return "adoptionRequestTemplates/addAdoptionForm";
        }
        return "redirect:/payment/addPaymentForm/" + adoptionRequest.getAdoptionRequestId();
    }

    @GetMapping("/adoptionListAsClient")
    public ModelAndView getAllAdoptionRequestByClient(){
        ModelAndView modelAndView = new ModelAndView("adoptionRequestTemplates/getAllAdoptedPetsForClient");
        String id = redisService.getData("userId");
        List<AdoptionRequest> adoptionRequestList = adoptionRequestService.getAdoptionRequestByClient(Integer.valueOf(id));
        modelAndView.addObject("adoptionRequests",adoptionRequestList);
        return modelAndView;
    }

    @GetMapping("/{adoptionId}")
    public ModelAndView getAdoptionDetails(@PathVariable Integer adoptionId){
        ModelAndView modelAndView = new ModelAndView("adoptionRequestTemplates/adoptionDetails");
        AdoptionRequest adoptionRequest = adoptionRequestService.getAdoptionRequestById(adoptionId);
        List<AdoptionFeedback> adoptionFeedbacks = adoptionFeedbackService.getFeedbacksForAdoption(adoptionId);
        modelAndView.addObject("feedbacks",adoptionFeedbacks);
        modelAndView.addObject("content","");
        modelAndView.addObject("adoption", adoptionRequest);
        return modelAndView;
    }

    @GetMapping("/adoptionListAsOwner")
    public ModelAndView getAllAdoptionRequestByOwner(){
        ModelAndView modelAndView = new ModelAndView("adoptionRequestTemplates/getAllAdoptedPetsForOwner");
        String id = redisService.getData("userId");
        List<AdoptionRequest> adoptionRequestList = adoptionRequestService.getAdoptionRequestByOwner(Integer.valueOf(id));
        modelAndView.addObject("adoptionRequests",adoptionRequestList);
        return modelAndView;
    }

    @GetMapping("/getAdoptionForOwner/{adoptionId}")
    public ModelAndView getAdoptionForOwner(@PathVariable Integer adoptionId){
        ModelAndView modelAndView = new ModelAndView("adoptionRequestTemplates/adoptionDetailsOwner");
        AdoptionRequest adoptionRequest = adoptionRequestService.getAdoptionRequestById(adoptionId);
        List<AdoptionFeedback> adoptionFeedbacks = adoptionFeedbackService.getFeedbacksForAdoption(adoptionId);
        modelAndView.addObject("feedbacks",adoptionFeedbacks);
        modelAndView.addObject("adoption", adoptionRequest);
        return modelAndView;
    }
}
