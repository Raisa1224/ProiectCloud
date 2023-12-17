package com.health.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "pet_vaccinations")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PetVaccinations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int vaccinationId;

    @Column(name = "vaccination_name")
    private String name;

    @Column(name = "vaccination_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @Column(name = "dose")
    private int dose;

    @Column(name = "total_doses")
    private int totalDoses;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pet_id")
    private Pet pet;

}
