package com.health.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "pet_vaccinations")
public class PetVaccinations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vaccination_id")
    private int id;

    @Column(name = "vaccination_name")
    private String name;

    @Column(name = "vaccination_date")
    private Date date;

    @Column(name = "dose")
    private int dose;

    @Column(name = "total_doses")
    private int totalDoses;

    @ManyToOne(fetch = FetchType.EAGER)
    private Pet pet;

}
