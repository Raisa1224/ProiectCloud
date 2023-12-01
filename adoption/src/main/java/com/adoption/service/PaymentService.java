package com.adoption.service;

import com.adoption.entity.Payment;
import com.adoption.exception.AdoptionNotFoundException;
import com.adoption.exception.PaymentNotFoundException;
import com.adoption.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public List<Payment> getAllPayments(){
        return paymentRepository.findAll();
    }

    public Payment getPaymentById(Integer paymentId){
        Optional<Payment> paymentOptional = paymentRepository.findById(paymentId);
        if (paymentOptional.isEmpty()) {
            throw new PaymentNotFoundException(String.format("Payment with id %s not found", paymentId));
        }
        return paymentOptional.get();
    }

    public Payment getPaymentByAdoptionRequestId(Integer adoptionRequestId){
        return paymentRepository.findByAdoptionRequestId(adoptionRequestId);
    }

    public Payment addPayment(Payment payment){
        if (payment.getAdoptionRequest() == null) {
            throw new AdoptionNotFoundException("Adoption request must be present for payment");
        }
        return paymentRepository.save(payment);
    }
}
