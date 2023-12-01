package com.health.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "pet_special_conditions")
public class PetSpecialConditions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "condition_id")
    private int id;

    @Column(name = "condition_name")
    private String name;

    @Column(name = "condition_description")
    private String description;

    @Column(name = "observations")
    private String observations;


    @ManyToOne(fetch = FetchType.EAGER)
    private Pet pet;

}
