package com.health.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "pet_medications")
public class PetMedications {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "medication_id")
    private int id;

    @Column(name = "medication_name")
    private String name;

    @Column(name = "reason")
    private String reason;

    @Column(name = "dosage")
    private int dosage;

    @Column(name = "frequency_days")
    private int frequencyDays;

    @Column(name = "observations")
    private String observations;


    @ManyToOne(fetch = FetchType.EAGER)
    private Pet pet;

}
