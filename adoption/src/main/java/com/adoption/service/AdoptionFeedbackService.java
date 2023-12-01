package com.adoption.service;

import com.adoption.entity.AdoptionFeedback;
import com.adoption.exception.AdoptionFeedbackNotFoundException;
import com.adoption.exception.AdoptionNotFoundException;
import com.adoption.repository.AdoptionFeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdoptionFeedbackService {
    @Autowired
    private AdoptionFeedbackRepository adoptionFeedbackRepository;

    public List<AdoptionFeedback> getAllFeedbacks(){
        return adoptionFeedbackRepository.findAll();
    }

    public AdoptionFeedback getAdoptionFeedbackById(Integer adoptionId){
        Optional<AdoptionFeedback> adoptionFeedbackOptional= adoptionFeedbackRepository.findById(adoptionId);
        if (adoptionFeedbackOptional.isEmpty()) {
            throw new AdoptionFeedbackNotFoundException(String.format("Adoption feedback with id %s not found", adoptionId));
        }
        return adoptionFeedbackOptional.get();
    }

    public List<AdoptionFeedback> getFeedbacksForAdoption(Integer adoptionId){
       return adoptionFeedbackRepository.findByAdoptionRequestId(adoptionId);
    }

    public AdoptionFeedback addAdoptionFeedback(AdoptionFeedback adoptionFeedback){
        if (adoptionFeedback.getAdoptionRequest() == null) {
            throw new AdoptionNotFoundException("Adoption request must be present for feedback");
        }
        return adoptionFeedbackRepository.save(adoptionFeedback);
    }

    public AdoptionFeedback deleteAdoptionFeedback(Integer adoptionId){
        Optional<AdoptionFeedback> adoptionFeedbackOptional= adoptionFeedbackRepository.findById(adoptionId);
        if (adoptionFeedbackOptional.isEmpty()) {
            throw new AdoptionNotFoundException(String.format("Adoption feedback with id %s not found", adoptionId));
        }
        adoptionFeedbackRepository.deleteById(adoptionId);
        return adoptionFeedbackOptional.get();
    }

    public AdoptionFeedback editAdoptionFeedback(Integer adoptionId, AdoptionFeedback newAdoptionFeedback){
        Optional<AdoptionFeedback> adoptionFeedbackOptional= adoptionFeedbackRepository.findById(adoptionId);
        if (adoptionFeedbackOptional.isEmpty()) {
            throw new AdoptionNotFoundException(String.format("Adoption feedback with id %s not found", adoptionId));
        }
        newAdoptionFeedback.setFeedbackId(adoptionFeedbackOptional.get().getFeedbackId());
        return adoptionFeedbackRepository.save(newAdoptionFeedback);
    }
}
