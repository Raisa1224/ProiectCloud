package com.health.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Entity
@Table(name = "pet_special_conditions")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PetSpecialConditions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int conditionId;

    @Column(name = "condition_name")
    @NotEmpty(message = "Name is mandatory")
    private String name;

    @Column(name = "condition_description")
    private String description;

    @Column(name = "observations")
    private String observations;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pet_id")
    private Pet pet;

}
