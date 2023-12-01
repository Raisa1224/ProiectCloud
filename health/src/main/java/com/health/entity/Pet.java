package com.health.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "pet")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "medication_id")
    private int id;

    @Column(name = "pet_name")
    private String name;

    @Column(name = "gender")
    private String gender;

}
