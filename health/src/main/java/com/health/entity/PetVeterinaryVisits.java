package com.health.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "pet_veterinary_visits")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PetVeterinaryVisits {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int visitId;

    @Column(name = "clinic")
    @NotEmpty(message = "Clinic is mandatory")
    private String clinic;

    @Column(name = "visit_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @Column(name = "cause")
    private String cause;

    @Column(name = "result")
    private String result;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pet_id")
    private Pet pet;

}