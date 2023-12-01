package com.adoption.controller;

import com.adoption.entity.AdoptionFeedback;
import com.adoption.service.AdoptionFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feedback")
public class AdoptionFeedbackController {

    @Autowired
    private AdoptionFeedbackService adoptionFeedbackService;

    @GetMapping("/getAllFeedbacks")
    public ResponseEntity<List<AdoptionFeedback>> getAllFeedbacks(){
        return ResponseEntity.ok(adoptionFeedbackService.getAllFeedbacks());
    }

    @GetMapping("/getFeedbackById/{feedbackId}")
    public ResponseEntity<AdoptionFeedback> getAdoptionFeedbackById(@PathVariable Integer feedbackId){
        return ResponseEntity.ok(adoptionFeedbackService.getAdoptionFeedbackById(feedbackId));
    }

    @GetMapping("/getFeedbacksForAdoption/{adoptionId}")
    public ResponseEntity<List<AdoptionFeedback>> getFeedbacksForAdoption(@PathVariable Integer adoptionId){
        return ResponseEntity.ok(adoptionFeedbackService.getFeedbacksForAdoption(adoptionId));
    }

    @PostMapping("/addFeedback")
    public ResponseEntity<AdoptionFeedback> addAdoptionFeedback(@RequestBody AdoptionFeedback adoptionFeedback){
        return ResponseEntity.ok(adoptionFeedbackService.addAdoptionFeedback(adoptionFeedback));
    }

    @PutMapping("/editFeedback/{feedbackId}")
    public ResponseEntity<AdoptionFeedback> editAdoptionFeedback(@PathVariable Integer feedbackId, @RequestBody AdoptionFeedback newAdoptionFeedback){
        return ResponseEntity.ok(adoptionFeedbackService.editAdoptionFeedback(feedbackId, newAdoptionFeedback));
    }

    @DeleteMapping("/deleteFeedback/{feedbackId}")
    public ResponseEntity<AdoptionFeedback> deleteAdoptionFeedback(@PathVariable Integer feedbackId){
        return ResponseEntity.ok(adoptionFeedbackService.deleteAdoptionFeedback(feedbackId));
    }
}
