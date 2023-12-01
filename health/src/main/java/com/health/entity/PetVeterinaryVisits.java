package com.health.entity;

import jakarta.persistence.*;
import lombok.*;

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