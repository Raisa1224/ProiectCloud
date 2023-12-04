package com.pet.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "species")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Species {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer species_id;

    @Column(name = "species_name")
    private String speciesName;

    @Column(name = "species_description")
    private String speciesDescription;
}
