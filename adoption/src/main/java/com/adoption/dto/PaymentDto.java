package com.adoption.dto;

import com.adoption.entity.AdoptionRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;

@Builder
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto {

    @Pattern(regexp = "\\d{4}-\\d{4}-\\d{4}-\\d{4}", message = "The format must be xxxx-xxxx-xxxx-xxxx")
    private String cardNumber;

    @NotEmpty(message = "Cardholder Name is required")
    private String cardHolderName;

    @NotEmpty(message = "Email is required")
    private String email;

    @NotEmpty(message = "Address is required")
    private String address;

    @Pattern(regexp = "\\d\\d/\\d\\d", message = "The format must be MM/DD")
    private String expirationDate;

    @Size(min = 3, max = 3, message = "CVV must have 3 digits")
    @Pattern(regexp = "[0-9]+", message = "CVV must contain only digits")
    private String cvv;

    private Integer paymentId;
    private Date paymentDate;
    private Double amount;
    private AdoptionRequest adoptionRequest;
}
