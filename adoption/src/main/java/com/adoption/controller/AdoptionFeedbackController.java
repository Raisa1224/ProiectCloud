package com.adoption.controller;

import com.adoption.entity.AdoptionFeedback;
import com.adoption.entity.AdoptionRequest;
import com.adoption.service.AdoptionFeedbackService;
import com.adoption.service.AdoptionRequestService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/feedback")
public class AdoptionFeedbackController {

    @Autowired
    private AdoptionFeedbackService adoptionFeedbackService;

    @Autowired
    private AdoptionRequestService adoptionRequestService;
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

    @PostMapping("/{adoptionId}")
    public String addFeedbackFromForm(@PathVariable Integer adoptionId, @RequestParam String content, @RequestParam Integer rating, RedirectAttributes redirectAttributes){
        if (content == null || content.isEmpty()) {
            redirectAttributes.addFlashAttribute("contentErrorMessage", "Content cannot be empty");
            return "redirect:/adoption/" + adoptionId;
        }
        AdoptionFeedback adoptionFeedback = new AdoptionFeedback();
        adoptionFeedback.setFeedbackDate(new Date());
        adoptionFeedback.setRating(rating);
        adoptionFeedback.setContent(content);
        adoptionFeedback.setAdoptionRequest(adoptionRequestService.getAdoptionRequestById(adoptionId));
        adoptionFeedbackService.addAdoptionFeedback(adoptionFeedback);
        return "redirect:/adoption/" + adoptionId;
    }

    @RequestMapping("/deleteFeedbackById/{adoptionId}/{feedbackId}")
    public String deleteFeedbackById(@PathVariable Integer adoptionId, @PathVariable Integer feedbackId){
        adoptionFeedbackService.deleteAdoptionFeedback(feedbackId);
        return "redirect:/adoption/" + adoptionId;
    }
}
