package com.health.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "pet_veterinary_visits")
public class PetVeterinaryVisits {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "visit_id")
    private int id;

    @Column(name = "clinic")
    private String clinic;

    @Column(name = "visit_date")
    private Date date;

    @Column(name = "cause")
    private String cause;

    @Column(name = "result")
    private String result;

    @ManyToOne(fetch = FetchType.EAGER)
    private Pet pet;

}