package com.adoption.repository;

import com.adoption.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    @Query("SELECT p FROM Payment p WHERE p.adoptionRequest.adoptionRequestId = :adoptionRequestId")
    Payment findByAdoptionRequestId(Integer adoptionRequestId);

}
