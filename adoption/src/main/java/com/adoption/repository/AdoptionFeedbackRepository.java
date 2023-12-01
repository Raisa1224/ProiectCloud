package com.adoption.repository;

import com.adoption.entity.AdoptionFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdoptionFeedbackRepository extends JpaRepository<AdoptionFeedback, Integer> {

    @Query("SELECT af FROM AdoptionFeedback af WHERE af.adoptionRequest.adoptionRequestId = :adoptionRequestId")
    List<AdoptionFeedback> findByAdoptionRequestId(Integer adoptionRequestId);
}
