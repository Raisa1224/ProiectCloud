package com.health.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Entity
@Table(name = "pet_medications")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PetMedications {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer medicationId;
    @Column(name = "medication_name")
    @NotEmpty(message = "Name is mandatory")
    private String name;

    @Column(name = "reason")
    @NotEmpty(message = "Reason is mandatory")
    private String reason;

    @Column(name = "dosage")
    private int dosage;

    @Column(name = "frequency_days")
    private int frequencyDays;

    @Column(name = "observations")
    private String observations;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pet_id")
    private Pet pet;



}
