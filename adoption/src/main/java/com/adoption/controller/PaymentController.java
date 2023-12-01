package com.adoption.controller;

import com.adoption.entity.Payment;
import com.adoption.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/getAllPayments")
    public ResponseEntity<List<Payment>> getAllPayments(){
        return ResponseEntity.ok(paymentService.getAllPayments());
    }

    @GetMapping("/getPaymentById/{paymentId}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable Integer paymentId){
        return ResponseEntity.ok(paymentService.getPaymentById(paymentId));
    }

    @GetMapping("/getPaymentByAdoptionId/{adoptionId}")
    public ResponseEntity<Payment> getPaymentByAdoptionId(@PathVariable Integer adoptionId){
        return ResponseEntity.ok(paymentService.getPaymentByAdoptionRequestId(adoptionId));
    }

    @PostMapping("/addPayment")
    public ResponseEntity<Payment> addPayment(@RequestBody Payment payment){
        return ResponseEntity.ok(paymentService.addPayment(payment));
    }

}
