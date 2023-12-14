package com.adoption.controller;

import com.adoption.dto.PaymentDto;
import com.adoption.entity.AdoptionRequest;
import com.adoption.entity.Payment;
import com.adoption.service.AdoptionRequestService;
import com.adoption.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private AdoptionRequestService adoptionRequestService;

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

    @RequestMapping("/addPaymentForm/{adoptionRequestId}")
    public String addPaymentForm(Model model, @PathVariable Integer adoptionRequestId) {
       AdoptionRequest adoptionRequest = adoptionRequestService.getAdoptionRequestById(adoptionRequestId);
       PaymentDto paymentDto = new PaymentDto();
       paymentDto.setAddress(adoptionRequest.getClient().getAddress());
       paymentDto.setEmail(adoptionRequest.getClient().getEmail());
       paymentDto.setAdoptionRequest(adoptionRequest);
       paymentDto.setAmount(adoptionRequest.getPet().getPrice());
       model.addAttribute("paymentDto", paymentDto);
       return "/paymentTemplates/addPaymentForm";
    }

    @PostMapping
    public String payForAdoption(@ModelAttribute("paymentDto") @Valid  PaymentDto paymentDto,
                           BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            return "/paymentTemplates/addPaymentForm";
        }
        try{
            Payment payment = new Payment();
            payment.setPaymentDate(new Date());
            payment.setAdoptionRequest(paymentDto.getAdoptionRequest());
            payment.setAmount(paymentDto.getAmount());
            paymentService.addPayment(payment);
        }catch (Exception exception){
            bindingResult.reject("globalError", exception.getMessage());
            return "/paymentTemplates/addPaymentForm";
        }
        return "redirect:/adoptionListAsClient";
    }
}
