package com.adoption.dto;

import com.adoption.entity.AdoptionRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.Date;

@Builder
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto {

    @NotEmpty(message = "Card Number is required")
    private String cardNumber;

    @NotEmpty(message = "Cardholder Name is required")
    private String cardHolderName;

    @NotEmpty(message = "Email is required")
    private String email;

    @NotEmpty(message = "Address is required")
    private String address;

    @NotEmpty(message = "Expiration Date is required")
    private String expirationDate;

    @NotEmpty(message = "CVV is required")
    private String cvv;

    private Integer paymentId;
    private Date paymentDate;
    private Double amount;
    private AdoptionRequest adoptionRequest;
}
